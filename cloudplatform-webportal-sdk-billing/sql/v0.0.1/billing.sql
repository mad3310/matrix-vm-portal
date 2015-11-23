CREATE TABLE `MATRIX_BILLING_BASE_ELEMENT` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MATRIX_BILLING_BASE_PRICE` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '基础价格表主键',
  `BASE_STANDARD_ID` bigint(20) unsigned DEFAULT NULL COMMENT '规格表主键',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '计费类型：0-基础价格，1-阶梯，2-线性,3-云主机双线性',
  `AMOUNT` varchar(30) DEFAULT NULL COMMENT '使用量',
  `UNIT` varchar(20) DEFAULT NULL COMMENT '单位',
  `PRICE` decimal(30,3) DEFAULT NULL COMMENT '单位价格',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础价格表主键';

CREATE TABLE `MATRIX_BILLING_BASE_REGION` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '地域表主键',
  `CODE` varchar(30) DEFAULT NULL COMMENT '编号',
  `NAME` varchar(20) DEFAULT NULL COMMENT '地域名称',
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地域表';

CREATE TABLE `MATRIX_BILLING_BASE_STANDARD` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '规格表主键',
  `BASE_ELEMENT_ID` bigint(20) DEFAULT NULL COMMENT '基础元素ID',
  `FATHER_ID` bigint(20) DEFAULT NULL COMMENT '父节点ID',
  `STANDARD` varchar(30) DEFAULT NULL COMMENT '产品规格',
  `TYPE` varchar(30) DEFAULT NULL COMMENT '产品类型',
  `VALUE` varchar(30) DEFAULT NULL COMMENT '产品类型值',
  `UNIT` varchar(10) DEFAULT NULL COMMENT '单位',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规格表';

CREATE TABLE `MATRIX_BILLING_ORDER` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单表主键',
  `ORDER_NUMBER` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '订单状态：0-未付款，1-失效，2-已付款',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(500) DEFAULT NULL COMMENT '描述',
  `PAY_NUMBER` varchar(30) DEFAULT NULL COMMENT '支付订单号',
  `PAY_TIME` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `MATRIX_BILLING_ORDER_SUB` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '子订单表主键',
  `ORDER_ID` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `SUBSCRIPTION_ID` bigint(20) DEFAULT NULL COMMENT '订阅表ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `PRICE` decimal(30,3) unsigned DEFAULT NULL COMMENT '价格',
  `DISCOUNT_PRICE` decimal(30,3) unsigned DEFAULT NULL COMMENT '折扣价',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子订单表';

CREATE TABLE `MATRIX_BILLING_ORDER_SUB_DETAIL` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '子订单详情表主键',
  `ORDER_SUB_ID` bigint(20) unsigned DEFAULT NULL COMMENT '子订单主表主键',
  `SUBSCRIPTION_DETAIL_ID` bigint(20) unsigned DEFAULT NULL COMMENT '订阅详情表主键',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `AMOUNT` varchar(30) DEFAULT NULL COMMENT '使用量',
  `PRICE` decimal(30,3) unsigned DEFAULT NULL COMMENT '产品元素单价*购买时长*数量',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子订单详情表';

CREATE TABLE `MATRIX_BILLING_PRODUCT` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品表主键',
  `NAME` varchar(30) DEFAULT NULL COMMENT '产品名称',
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

CREATE TABLE `MATRIX_BILLING_PRODUCT_ELEMENT` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品元素关系表主键',
  `PRODUCT_ID` bigint(20) DEFAULT NULL COMMENT '产品ID',
  `BASE_ELEMENT_ID` bigint(20) DEFAULT NULL COMMENT '基础元素ID',
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品元素关系表';

CREATE TABLE `MATRIX_BILLING_PRODUCT_PRICE` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品规格价格表主键',
  `PRODUCT_ID` bigint(20) DEFAULT NULL COMMENT '产品ID',
  `BASE_REGION_ID` bigint(20) DEFAULT NULL COMMENT '地域ID',
  `BASE_PRICE_ID` bigint(20) DEFAULT NULL COMMENT '基础价格ID',
  `PRICE` decimal(30,3) DEFAULT NULL COMMENT '产品元素价格',
  `VERSION` varchar(30) DEFAULT NULL COMMENT '产品版本',
  `USED` tinyint(1) DEFAULT NULL COMMENT '是否使用：0-不使用，1-使用',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `PRIORITY` int(10) unsigned DEFAULT NULL COMMENT '优先级',
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品规格价格表';

CREATE TABLE `MATRIX_BILLING_PRODUCT_REGION` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品地域维护表主键',
  `PRODUCT_ID` bigint(20) DEFAULT NULL COMMENT '产品ID',
  `BASE_REGION_ID` bigint(20) DEFAULT NULL COMMENT '地域ID',
  `HCLUSTER_ID` bigint(20) DEFAULT NULL COMMENT '机房机群ID',
  `DESCN` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品地域维护表';

CREATE TABLE `MATRIX_BILLING_SUBSCRIPTION` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订阅表主键',
  `SUBSCRIPTION_NUMBER` varchar(30) DEFAULT NULL COMMENT '订阅编号',
  `PRODUCT_ID` bigint(20) unsigned DEFAULT NULL COMMENT '产品表主键',
  `BASE_REGION_ID` bigint(20) unsigned DEFAULT NULL COMMENT '地域ID',
  `BUY_TYPE` tinyint(4) DEFAULT '0' COMMENT '购买类型：0-新购，1-续费',
  `CHARGE_TYPE` tinyint(4) DEFAULT NULL COMMENT '计费类型：0-包年包月，1-按量',
  `ORDER_TIME` int(10) DEFAULT NULL COMMENT '购买时长，单位：月',
  `VALID` tinyint(1) DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `USER_ID` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `PRODUCT_INFO_RECORD_ID` bigint(20) unsigned DEFAULT NULL COMMENT '产品信息记录表ID',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订阅表';


CREATE TABLE `MATRIX_BILLING_SUBSCRIPTION_DETAIL` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订阅详情表主键',
  `SUBSCRIPTION_ID` bigint(20) unsigned DEFAULT NULL COMMENT '订阅主表主键',
  `STANDARD_NAME` varchar(30) DEFAULT NULL COMMENT '规格名称',
  `STANDARD_VALUE` varchar(30) DEFAULT NULL COMMENT '规格值',
  `ORDER_TIME` int(10) DEFAULT NULL COMMENT '购买时长',
  `VALID` tinyint(1) DEFAULT NULL COMMENT '是否有效：0-无效，1-有效',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `USER_ID` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订阅详情表';

CREATE TABLE `MATRIX_PRODUCT_INFO_RECORD` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品信息记录表主键',
  `PARAMS` varchar(1000) DEFAULT NULL COMMENT '参数',
  `PRODUCT_ID` bigint(20) unsigned DEFAULT NULL COMMENT '商品ID，2-云主机',
  `BATCH` tinyint(4) DEFAULT NULL COMMENT '批次，一次创建',
  `INVOKE_TYPE` tinyint(4) DEFAULT NULL COMMENT '调用类型，1-去调用，0-不调用',
  `DELETED` tinyint(4) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` bigint(20) unsigned DEFAULT NULL,
  `UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
  `DESCN` varchar(500) DEFAULT NULL COMMENT '描述',
  `INSTANCE_ID` varchar(100) DEFAULT NULL COMMENT '实例ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信息记录表';