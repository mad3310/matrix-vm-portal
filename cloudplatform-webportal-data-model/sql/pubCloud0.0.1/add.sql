
/*
 * 账户系统相关表
 */

CREATE TABLE `bill_deduction_fail_task` (
  `deductionFailId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务订单的主键，自增id',
  `userId` bigint(20) NOT NULL COMMENT '用户的id',
  `orderId` bigint(64) NOT NULL COMMENT '业务订单的主键，自增id',
  `serviceCode` bigint(20) NOT NULL COMMENT '参考《业务编码词典》',
  `featherCode` varchar(20) NOT NULL COMMENT '参考《功能点编码词典》',
  `deductionDate` varchar(20) NOT NULL COMMENT '扣费的时间',
  `fee` decimal(10,0) DEFAULT '0' COMMENT '费用',
  `useAmount` bigint(20) DEFAULT '0' COMMENT '使用量',
  `discount` varchar(5) DEFAULT NULL COMMENT '折扣',
  `price` varchar(10) DEFAULT NULL COMMENT '单价',
  `lossValue` varchar(5) NOT NULL DEFAULT '0.1' COMMENT '损耗值',
  `p2pValue` varchar(5) DEFAULT '0' COMMENT 'p2p损耗值',
  `period` tinyint(2) DEFAULT NULL COMMENT '计费周期 0:小时，1:日，2：月',
  `featherType` varchar(5) NOT NULL COMMENT '功能点类型',
  `feeMode` varchar(50) DEFAULT NULL,
  `feeCode` char(3) NOT NULL COMMENT '计费方式',
  `error` varchar(10) DEFAULT NULL COMMENT '异常原因',
  `testEndTime` datetime DEFAULT NULL,
  `testStartTime` datetime DEFAULT NULL,
  `orderStage` tinyint(2) DEFAULT NULL,
  `createdTime` datetime NOT NULL COMMENT '该任务的创建时间',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '该记录最后一次更新的时间',
  PRIMARY KEY (`deductionFailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bill_deduction_record` (
  `deductionId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deductionDate` varchar(20) NOT NULL COMMENT '扣费的时间',
  `orderId` bigint(64) NOT NULL COMMENT '业务订单的主键，自增id',
  `featherCode` varchar(20) NOT NULL COMMENT '参考《功能点编码词典》',
  `userId` bigint(20) NOT NULL COMMENT '用户的id',
  `serviceCode` bigint(20) NOT NULL COMMENT '参考《业务编码词典》',
  `fee` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '费用',
  `useAmount` bigint(20) DEFAULT NULL COMMENT '使用量',
  `discount` varchar(5) DEFAULT NULL COMMENT '折扣',
  `price` varchar(10) DEFAULT NULL COMMENT '单价',
  `lossValue` varchar(5) NOT NULL DEFAULT '0.1' COMMENT '损耗值',
  `p2pValue` varchar(5) DEFAULT '0' COMMENT 'p2p损耗值',
  `state` tinyint(2) DEFAULT '1' COMMENT '1:交易成功',
  `period` tinyint(2) DEFAULT NULL COMMENT '计费周期 0:小时，1:日，2：月',
  `featherType` varchar(5) NOT NULL COMMENT '功能点类型',
  `createdTime` datetime NOT NULL COMMENT '该任务的创建时间',
  `lastUpdateTime` datetime NOT NULL COMMENT '该记录最后一次更新的时间',
  PRIMARY KEY (`deductionId`),
  UNIQUE KEY `record` (`deductionDate`,`orderId`,`featherCode`),
  KEY `orderId` (`orderId`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bill_recharge_record` (
  `tradeNum` varchar(32) NOT NULL COMMENT '交易号码',
  `userId` bigint(20) NOT NULL COMMENT '外键关联',
  `amount` decimal(10,2) DEFAULT NULL,
  `rechargeType` int(11) NOT NULL COMMENT '充值类型',
  `createdTime` datetime NOT NULL COMMENT '创建日期',
  `lastUpdateTime` datetime NOT NULL COMMENT '修改日期',
  `orderNum` varchar(50) DEFAULT NULL COMMENT '回调生成唯一流水号',
  `success` tinyint(2) NOT NULL DEFAULT '0' COMMENT '支付成功标记',
  PRIMARY KEY (`tradeNum`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bill_user_amount` (
  `userId` bigint(20) NOT NULL,
  `availableAmount` decimal(10,2) DEFAULT NULL,
  `freezeAmount` decimal(10,2) DEFAULT NULL,
  `createdTime` datetime NOT NULL,
  `lastUpdateTime` datetime NOT NULL,
  `arrearageTime` datetime DEFAULT NULL COMMENT '欠费时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bill_user_billing` (
  `billingId` varchar(64) NOT NULL COMMENT '账单编号',
  `orderId` bigint(64) NOT NULL COMMENT '订单编号',
  `billingMonth` varchar(20) NOT NULL COMMENT '账单月',
  `billingMoney` varchar(10) NOT NULL COMMENT '账单金额',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `serviceCode` bigint(20) NOT NULL COMMENT '业务编码',
  PRIMARY KEY (`billingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bill_user_invoice` (
  `billingId` varchar(64) NOT NULL COMMENT '账单编号',
  `orderId` bigint(64) NOT NULL COMMENT '订单编号',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `billingMonth` varchar(20) NOT NULL COMMENT '账单日期',
  `billingMoney` varchar(10) NOT NULL COMMENT '账单金额',
  `billingContents` varchar(200) DEFAULT NULL COMMENT '账单内容',
  `issueType` int(2) DEFAULT NULL COMMENT '开具类型',
  `invoiceTitle` varchar(50) DEFAULT NULL COMMENT '发票抬头',
  `invoiceType` int(2) DEFAULT NULL COMMENT '发票类型。1：增值税发票',
  `receiveName` varchar(50) DEFAULT NULL COMMENT '收件人姓名',
  `areaLevel1` varchar(20) DEFAULT NULL COMMENT '所在地区 一级',
  `areaLevel2` varchar(20) DEFAULT NULL COMMENT '所在地区 二级',
  `areaLevel3` varchar(20) DEFAULT NULL COMMENT '所在地区 三级',
  `address` varchar(50) DEFAULT NULL COMMENT '街道地址',
  `zipCode` varchar(50) DEFAULT NULL COMMENT '邮政编码',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `createdTime` datetime DEFAULT NULL COMMENT '创建时间',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`billingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table bill_user_amount modify column availableAmount decimal(10,2);
alter table bill_user_amount modify column freezeAmount decimal(10,2);
alter table bill_recharge_record modify column amount decimal(10,2);

/*add by lisuxiao*/
alter table bill_recharge_record add column orderCode varchar(50) DEFAULT NULL COMMENT '订单编号';
CREATE TABLE `WEBPORTAL_OPERATE` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '最近操作表主键',
  `ACTION` varchar(100) DEFAULT NULL COMMENT '动作',
  `CONTENT` varchar(128) DEFAULT NULL COMMENT '内容',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL COMMENT '创建用户',
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='最近操作表'

