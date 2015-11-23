package com.letv.portal.service.letvcloud;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letv.common.exception.CommonException;
import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.dao.letvcloud.BillRechargeRecordMapper;
import com.letv.portal.dao.letvcloud.BillUserAmountMapper;
import com.letv.portal.model.letvcloud.BillDeduction;
import com.letv.portal.model.letvcloud.BillRechargeRecord;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.model.message.Message;
import com.letv.portal.service.message.IMessageProxyService;

/**
 * Created by wanglei14 on 2015/6/28.
 */
@Service
public class BillUserAmountServiceImpl implements BillUserAmountService {
	
	private static final Logger logger = LoggerFactory.getLogger(BillUserAmountServiceImpl.class);

    private static final BigDecimal ZERO = new BigDecimal("0");
    @Autowired
    BillUserAmountMapper billUserAmountMapper;
    @Autowired
    BillRechargeRecordMapper billRechargeRecordMapper;
    @Autowired
    IMessageProxyService messageProxyService;

    @Override
    public void createUserAmount(Long userId) throws CommonException {
        billUserAmountMapper.insertUserAmountDefault(userId);
    }

    @Override
    public String recharge(long userId, BigDecimal amount,int type) {
    	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    String tradeNum = format.format(System.currentTimeMillis());
	    String random = PasswordRandom.genStr(6);
	    String trade = tradeNum +random;
	    BillRechargeRecord record = new BillRechargeRecord();
        record.setTradeNum(trade);
        record.setAmount(amount);
        record.setUserId(userId);
        record.setRechargeType(type);
        billRechargeRecordMapper.insert(record);
        return trade;
    }
    
    @Override
	public String recharge(long userId, BigDecimal amount, String orderCode,
			int type) {
    	List<BillRechargeRecord> records = this.billRechargeRecordMapper.getAmountByOrderCode(orderCode);
    	if(records == null || records.size()==0) {
    		BillRechargeRecord record = new BillRechargeRecord();
    		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    	    String tradeNum = format.format(System.currentTimeMillis());
    	    String random = PasswordRandom.genStr(6);
    	    String trade = tradeNum +random;
    		record.setTradeNum(trade);
    		record.setAmount(amount);
    		record.setUserId(userId);
    		record.setRechargeType(type);
    		record.setOrderCode(orderCode);
    		billRechargeRecordMapper.insert(record);
    	} else {
    		Map<String, Object> recordParam = new HashMap<String, Object>();
            recordParam.put("amount", amount);
            recordParam.put("orderCode", orderCode);
    		this.billRechargeRecordMapper.updateAmount(recordParam);
    	}
        return orderCode;
	}

    @Override
    @Transactional
    public long rechargeSuccess(Long userId, String tradeNum, String orderNum, BigDecimal amount) {
        Map<String, Object> recordSuc = new HashMap<String, Object>();
        recordSuc.put("tradeNum", tradeNum);
        recordSuc.put("orderNum", orderNum);
        recordSuc.put("amount", amount);
        billRechargeRecordMapper.updateSuc(recordSuc);

        //充值业务逻辑
        int ret;
        int count = 0;
        do {
            ret = this.addUserAmount(userId, amount);
            count++;
        } while (ret < 1 && count < 10);
        //充值失败次数
        return ret;
    }
    @Override
    public long rechargeSuccessByOrderCode(Long userId, String orderCode, String orderNum, BigDecimal amount) {
    	Map<String, Object> recordSuc = new HashMap<String, Object>();
    	recordSuc.put("orderCode", orderCode);
    	recordSuc.put("orderNum", orderNum);
    	recordSuc.put("amount", amount);
    	billRechargeRecordMapper.updateSucByOrderCode(recordSuc);
    	
    	//充值业务逻辑
    	int ret;
    	int count = 0;
    	do {
    		ret = this.addUserAmount(userId, amount);
    		count++;
    	} while (ret < 1 && count < 10);
    	//充值失败次数
    	return ret;
    }
    @Override
	public long rechargeSuccess(Long userId, String tradeNum, String orderNum,
			BigDecimal amount, boolean isUpdateAmount) {
    	Map<String, Object> recordSuc = new HashMap<String, Object>();
        recordSuc.put("tradeNum", tradeNum);
        recordSuc.put("orderNum", orderNum);
        recordSuc.put("amount", amount);
        billRechargeRecordMapper.updateSuc(recordSuc);
        int ret = 0;
        if(isUpdateAmount) {
        	//充值业务逻辑
        	int count = 0;
        	do {
        		ret = this.addUserAmount(userId, amount);
        		count++;
        	} while (ret < 1 && count < 10);
        	//充值失败次数
        	
        }
        return ret;
	}

    //充值业务逻辑
    private int addUserAmount(Long userId, BigDecimal amount) {
        BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);

        //账户余额
        BigDecimal availableAmount = billUserAmount.getAvailableAmount();
        //账户总额
        BigDecimal totalAmount = availableAmount.add(amount);
        //设置用户余额为总金额
        billUserAmount.setAvailableAmount(totalAmount);
        //如果
        if (ZERO.compareTo(availableAmount) == 1 && ZERO.compareTo(totalAmount) == 0) {
            billUserAmountMapper.updateArrearageTime(userId);
        }
        int ret = billUserAmountMapper.addAmount(billUserAmount);
        
        //充值成功后保存消息通知
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append("您在");
        buffer.append(sdf.format(d));
        buffer.append("成功充值");
        buffer.append(amount);
        buffer.append("元，当前账户余额为[￥");
        buffer.append(billUserAmount.getAvailableAmount());
        buffer.append("]，如有问题，可拨打客服电话。");
        Message msg = new Message();
        msg.setMsgTitle("成功充值"+amount+"元");
        msg.setMsgContent(buffer.toString());
        msg.setMsgStatus("0");//未读
        msg.setMsgType("2");//个人消息
        msg.setCreatedTime(d);
        Map<String,Object> msgRet = this.messageProxyService.saveMessage(userId, msg);
        if(!(Boolean) msgRet.get("result")) {
        	logger.error("充值成功后保存消息通知失败，失败原因:"+msgRet.get("message"));
        }

        return ret;

    }

    @Override
    public Map<Long, Date> getAllUserArrears(String serviceCode) {
        List<Long> users = null;//billServiceOrderDao.getUserByServiceCode(serviceCode);
        Map<Long, Date> result = new HashMap<Long, Date>();
        for (Long userId : users) {
            BillUserAmount amount = billUserAmountMapper.getUserArrears(userId);
            if (amount != null) {
                result.put(amount.getUserId(), amount.getArrearageTime());
            } else {
                continue;
            }
        }
        return result;
    }

    @Override
    public BillUserAmount getUserAmount(Long userId) {
        return billUserAmountMapper.getUserAmout(userId);
    }

    @Override
    public Page getUserAmountRecord(Page pageInfo, Long userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("offset", pageInfo.getStartRowPosition());
        params.put("rows", pageInfo.getRecordsPerPage());
        List<BillRechargeRecord> records = billRechargeRecordMapper.getUserAmountRecord(params);
        if (records != null && records.size() > 0) {
        	pageInfo.setData(records);
        } else {
        	pageInfo.setData(null);
        }
        int addCnt = billRechargeRecordMapper.getAddRecordCnt(userId);
        pageInfo.setTotalRecords(addCnt);
        return pageInfo;
    }

    @Override
    public BillRechargeRecord getRechargeRecord(String tradeNum) {
        return billRechargeRecordMapper.getAmount(tradeNum);
    }

    @Override
    public Map<String, Object> getUserAmountState(long userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
        result.put("userAccount", billUserAmount.getAvailableAmount());
        result.put("arrearageTime", billUserAmount.getArrearageTime());
        if (billUserAmount.getAvailableAmount().compareTo(new BigDecimal(0)) > 0) {
            result.put("arrearageState", "1");
        } else {
            result.put("arrearageState", "0");
        }
        return result;
    }
    
    private Object obj = new Object();

	@Override
	public boolean updateUserAmountFromAvailableToFreeze(long userId, BigDecimal price) {
		logger.info("开始转移可用余额到冻结余额,用户id:"+userId+",金额："+price);
		BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
		synchronized(obj) {
			if(billUserAmount.getAvailableAmount().compareTo(price)>=0) {
				billUserAmount.setAvailableAmount(billUserAmount.getAvailableAmount().subtract(price));
				billUserAmount.setFreezeAmount(billUserAmount.getFreezeAmount().add(price));
				billUserAmountMapper.updateUserAmountFromAvailableToFreeze(billUserAmount);
				logger.info("转移可用余额到冻结余额成功,用户id:"+userId+",金额："+price);
				return true;
			} else {
				logger.error("账户可用余额小于需要转移金额,用户id:"+userId+",金额："+price);
				throw new MatrixException("账户可用余额小于需要转移金额,用户id:"+userId+",金额："+price);
			}
		}
	}
	
	@Override
	public boolean updateUserAmountFromFreezeToAvailable(long userId, BigDecimal price, String productName, String productType) {
		boolean ret = false;
		logger.info("开始转移冻结余额到可用余额,用户id:"+userId+",金额："+price);
		BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
		synchronized(obj) {
			if(billUserAmount.getFreezeAmount().compareTo(price)>=0) {
				billUserAmount.setAvailableAmount(billUserAmount.getAvailableAmount().add(price));
				billUserAmount.setFreezeAmount(billUserAmount.getFreezeAmount().subtract(price));
				billUserAmountMapper.updateUserAmountFromAvailableToFreeze(billUserAmount);
				logger.info("转移冻结余额到可用余额成功,用户id:"+userId+",金额："+price);
				ret = true;
			} else {
				logger.error("账户冻结余额小于需要转移金额,用户id:"+userId+",金额："+price);
				throw new MatrixException("账户冻结余额小于需要转移金额,用户id:"+userId+",金额："+price);
			}
		}
		//服务创建失败后保存回退金额消息通知
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append("您在");
        buffer.append(sdf.format(d));
        buffer.append("购买的");
        buffer.append(productType);
        buffer.append("【");
        buffer.append(productName);
        buffer.append("】");
        buffer.append("创建失败，系统退回账户");
        buffer.append(price);
        buffer.append("元，当前账户余额为[￥");
        buffer.append(billUserAmount.getAvailableAmount());
        buffer.append("]，如有问题，可拨打客服电话。");
        Message msg = new Message();
        msg.setMsgTitle("退款"+price+"元");
        msg.setMsgContent(buffer.toString());
        msg.setMsgStatus("0");//未读
        msg.setMsgType("2");//个人消息
        msg.setCreatedTime(d);
        Map<String,Object> msgRet = this.messageProxyService.saveMessage(userId, msg);
        if(!(Boolean) msgRet.get("result")) {
        	logger.error("服务创建失败后保存回退金额消息通知，失败原因:"+msgRet.get("message"));
        }
        return ret;
	}
	
	@Override
	public boolean reduceAvailableAmount(long userId, BigDecimal price, String productName, String productType, String date) {
		boolean ret = false;
		logger.info("开始扣除可用余额,用户id:"+userId+",金额："+price);
		BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
		synchronized(obj) {
			if(billUserAmount.getAvailableAmount().compareTo(price)>=0) {
				billUserAmount.setAvailableAmount(billUserAmount.getAvailableAmount().subtract(price));
				billUserAmountMapper.reduceAvailableAmount(billUserAmount);
				logger.info("扣除可用余额成功,用户id:"+userId+",金额："+price);
				ret = true;
			} else {
				logger.error("账户可用余额小于扣除金额,用户id:"+userId+",金额："+price);
				throw new MatrixException("账户可用余额小于扣除金额,用户id:"+userId+",金额："+price);
			}
		}
		
		//续费成功后保存金额消息通知
        StringBuffer buffer = new StringBuffer();
        Message msg = new Message();
    	buffer.setLength(0);
        buffer.append("您在");
        buffer.append(date);
        buffer.append("续费");
        buffer.append(productType);
        buffer.append("【");
        buffer.append(productName);
        buffer.append("】成功，");
        buffer.append("消费");
        buffer.append(price);
        buffer.append("元，当前账户余额为[￥");
        buffer.append(billUserAmount.getAvailableAmount());
        buffer.append("]，如有问题，可拨打客服电话。");
        msg.setMsgTitle("续费成功，消费金额"+price+"元");
        msg.setMsgContent(buffer.toString());
        msg.setMsgStatus("0");//未读
        msg.setMsgType("2");//个人消息
        msg.setCreatedTime(new Date());
        Map<String,Object> saveRet = this.messageProxyService.saveMessage(userId, msg);
        if(!(Boolean) saveRet.get("result")) {
        	logger.error("续费成功后保存金额消息通知，失败原因:"+saveRet.get("message"));
        }
        
        return ret;
	}

	@Override
	public boolean reduceFreezeAmount(long userId, BigDecimal price, String productName, String productType) {
		boolean ret = false;
		logger.info("开始扣除冻结金额,用户id:"+userId+",金额："+price);
		BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
		synchronized(obj) {
			if(billUserAmount.getFreezeAmount().compareTo(price)>=0) {
				billUserAmount.setFreezeAmount(billUserAmount.getFreezeAmount().subtract(price));
				billUserAmountMapper.reduceFreezeAmount(billUserAmount);
				logger.info("扣除冻结金额成功,用户id:"+userId+",金额："+price);
				ret = true;
			} else {
				logger.error("冻结金额小于需要扣除金额,用户id:"+userId+",金额："+price);
				throw new MatrixException("冻结金额小于需要扣除金额,用户id:"+userId+",金额："+price);
			}
		}
		//服务创建成功后保存扣减金额消息通知
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append("您在");
        buffer.append(sdf.format(d));
        buffer.append("购买的");
        buffer.append(productType);
        buffer.append("【");
        buffer.append(productName);
        buffer.append("】");
        buffer.append("消费");
        buffer.append(price);
        buffer.append("元，当前账户余额为[￥");
        buffer.append(billUserAmount.getAvailableAmount());
        buffer.append("]，如有问题，可拨打客服电话。");
        Message msg = new Message();
        msg.setMsgTitle("消费金额"+price+"元");
        msg.setMsgContent(buffer.toString());
        msg.setMsgStatus("0");//未读
        msg.setMsgType("2");//个人消息
        msg.setCreatedTime(d);
        Map<String,Object> msgRet = this.messageProxyService.saveMessage(userId, msg);
        if(!(Boolean) msgRet.get("result")) {
        	logger.error("服务创建成功后保存扣减金额消息通知，失败原因:"+msgRet.get("message"));
        }
        return ret;
	}

	@Override
	public boolean dealFreezeAmount(long userId, BigDecimal succPrice,
			BigDecimal failPrice, String productName, String productType) {
		boolean ret = false;
		logger.info("开始处理冻结金额,用户id:{},成功金额：{},失败金额：{}", new Object[]{userId, succPrice, failPrice});
		BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
		synchronized(obj) {
			if(billUserAmount.getFreezeAmount().compareTo(succPrice.add(failPrice))>=0) {
				billUserAmount.setFreezeAmount(billUserAmount.getFreezeAmount().subtract(succPrice.add(failPrice)));
				billUserAmount.setAvailableAmount(billUserAmount.getAvailableAmount().add(failPrice));
				billUserAmountMapper.updateUserAmountFromAvailableToFreeze(billUserAmount);
				logger.info("处理冻结金额成功");
				ret = true;
			} else {
				logger.error("冻结金额小于需要处理金额,用户id:{},成功金额：{},失败金额：{}", new Object[]{userId, succPrice, failPrice});
				throw new MatrixException(MessageFormat.format("冻结金额小于需要处理金额,用户id:{1},成功金额：{2},失败金额：{3}", userId, succPrice, failPrice));
			}
		}
		
		//服务创建失败后保存回退金额消息通知
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date();
        StringBuffer buffer = new StringBuffer();
        Message msg = new Message();
        
        if(succPrice.doubleValue()!=0) {
        	buffer.setLength(0);
            buffer.append("您在");
            buffer.append(sdf.format(d));
            buffer.append("购买的");
            buffer.append(productType);
            buffer.append("【");
            buffer.append(productName);
            buffer.append("】");
            buffer.append("消费");
            buffer.append(succPrice);
            buffer.append("元，当前账户余额为[￥");
            buffer.append(billUserAmount.getAvailableAmount());
            buffer.append("]，如有问题，可拨打客服电话。");
            msg.setMsgTitle("消费金额"+succPrice+"元");
            msg.setMsgContent(buffer.toString());
            msg.setMsgStatus("0");//未读
            msg.setMsgType("2");//个人消息
            msg.setCreatedTime(d);
            Map<String,Object> saveRet = this.messageProxyService.saveMessage(userId, msg);
            if(!(Boolean) saveRet.get("result")) {
            	logger.error("服务创建成功后保存扣减金额消息通知，失败原因:"+saveRet.get("message"));
            }
        }
        
        if(failPrice.doubleValue()!=0) {
        	buffer.append("您在");
            buffer.append(sdf.format(d));
            buffer.append("购买的");
            buffer.append(productType);
            buffer.append("【");
            buffer.append(productName);
            buffer.append("】");
            buffer.append("创建失败，系统退回账户");
            buffer.append(failPrice);
            buffer.append("元，当前账户余额为[￥");
            buffer.append(billUserAmount.getAvailableAmount());
            buffer.append("]，如有问题，可拨打客服电话。");
            
            msg.setMsgTitle("退款"+failPrice+"元");
            msg.setMsgContent(buffer.toString());
            msg.setMsgStatus("0");//未读
            msg.setMsgType("2");//个人消息
            msg.setCreatedTime(d);
            Map<String,Object> msgRet = this.messageProxyService.saveMessage(userId, msg);
            if(!(Boolean) msgRet.get("result")) {
            	logger.error("服务创建失败后保存回退金额消息通知，失败原因:"+msgRet.get("message"));
            }
        }
		
		return ret;
	}

}
