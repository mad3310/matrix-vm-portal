package com.letv.portal.service.letvcloud;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letv.common.exception.CommonException;
import com.letv.common.paging.impl.Page;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.dao.letvcloud.BillRechargeRecordMapper;
import com.letv.portal.dao.letvcloud.BillUserAmountMapper;
import com.letv.portal.model.letvcloud.BillRechargeRecord;
import com.letv.portal.model.letvcloud.BillUserAmount;

/**
 * Created by wanglei14 on 2015/6/28.
 */
@Service
public class BillUserAmountServiceImpl implements BillUserAmountService {

    private static final BigDecimal ZERO = new BigDecimal("0");
    @Autowired
    BillUserAmountMapper billUserAmountMapper;
    @Autowired
    BillRechargeRecordMapper billRechargeRecordMapper;

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

}
