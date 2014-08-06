/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/12/24 17:09:48                          */
/*==============================================================*/
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS CON_CONTENT_RELATIONSHIP_INFO;

DROP TABLE IF EXISTS CON_CONTENT_INFO;

DROP TABLE IF EXISTS CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO;

DROP TABLE IF EXISTS CON_SUBCONTENT_INFO;

DROP TABLE IF EXISTS DICTIONARY_INFO;

DROP TABLE IF EXISTS DICTIONARY_TYPE_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_CHECK_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_RESTRICT_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_CATEGORY_INFO;

DROP TABLE IF EXISTS CON_SUBCONTENT_DISTRIBUTE_LOG;

DROP TABLE IF EXISTS LOG_OBJECT_CHANGE_DETAIL_INFO;

DROP TABLE IF EXISTS LOG_OBJECT_CHANGE_INFO;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_ALL;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_DATETIME_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_FLOAT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_INT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_LONG_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_TEXT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_VARCHAR_INDEX;

SET FOREIGN_KEY_CHECKS = 1;

/*==============================================================*/
/* Table: CON_CONTENT_INFO                                      */
/*==============================================================*/
CREATE TABLE CON_CONTENT_INFO
(
   ID           		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CODE					VARCHAR(200) NOT NULL COMMENT '内容编号',
   VERSION      		INT(11) UNSIGNED NOT NULL,
   NAME_CN              VARCHAR(200) NOT NULL COMMENT '中文名称',
   NAME_EN              VARCHAR(200) COMMENT '英文名称',
   CONTENT_BASE_TYPE    TINYINT(4) COMMENT '1.目录（越狱）2.父剧集（越狱第一季）3.子剧集（越狱第一集）4.视频（单视频、片花等）',
   CATEGORY             TINYINT(4) COMMENT '电影、电视剧、动漫、娱乐',
   CHANNEL_ID           TINYINT(3) COMMENT '1.网站,2.大咔，3.第三方',
   USER_ID              VARCHAR(200),
   STATUS               TINYINT(2) COMMENT '审核状态： 0,未审核 1正在审核2审核通过',
   IS_DELETE            TINYINT(1) DEFAULT 0,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_CONTENT_RELATIONSHIP_INFO                         */
/*==============================================================*/
CREATE TABLE CON_CONTENT_RELATIONSHIP_INFO
(
   ID           		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CONTENT_ID_A         BIGINT UNSIGNED NOT NULL,
   CONTENT_ID_B         BIGINT UNSIGNED NOT NULL,
   RELATIONSHIP         TINYINT(2) DEFAULT 0,
   IS_DELETE            TINYINT(1) DEFAULT 0,
   PRIMARY KEY (ID),
   KEY CONTENT_REL (CONTENT_ID_A,CONTENT_ID_B,IS_DELETE),
   KEY RELATIONSHIP (RELATIONSHIP),
   KEY DELETED (IS_DELETE)
);

/*==============================================================*/
/* Table: CON_SUBCONTENT_INFO                                   */
/*==============================================================*/
CREATE TABLE CON_SUBCONTENT_INFO
(
   ID        			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CODE					VARCHAR(200) NOT NULL COMMENT '子内容编号',
   VERSION   			INT(11) UNSIGNED NOT NULL,
   SUBCONTENT_NAME      VARCHAR(255),
   SUBCONTENT_TYPE      TINYINT(4) COMMENT '1. 视频文件 2. 图片文件 3. 字幕文件',
   VIDEO_CODE			TINYINT(4) NOT NULL COMMENT '码率标示',
   VIDEO_CODE_VERSION   VARCHAR(255),
   STATUS               TINYINT(2) COMMENT '审核状态： 0,未审核 1正在审核2审核通过',
   IS_DELETE            TINYINT(1),
   CDN_PATH 			VARCHAR(1000),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO              */
/*==============================================================*/
CREATE TABLE CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO
(
   ID        			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CONTENT_ID           BIGINT UNSIGNED NOT NULL,
   SUBCONTENT_ID        BIGINT UNSIGNED NOT NULL,
   RELATIONSHIP         TINYINT(2) DEFAULT 0,
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY CONTENT_SUBCONTENT_REL (CONTENT_ID,SUBCONTENT_ID,IS_DELETE),
   KEY RELATIONSHIP (RELATIONSHIP),
   KEY DELETED (IS_DELETE)
);

/*==============================================================*/
/* Table: CON_PROPERTY_CATEGORY_INFO                            */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_CATEGORY_INFO
(
   ID 						BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '扩展属性分类',
   BELONGING_OBJECT 		TINYINT(4) NOT NULL COMMENT '内容、子内容、栏目、编排关系',
   PROPERTY_CATEGORY_NAME 	VARCHAR(50) NOT NULL COMMENT '分类名称',
   DESCRIPTION          	VARCHAR(100) COMMENT '描述',
   IS_DELETE            	TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_CHECK_INFO                               */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_CHECK_INFO
(
   ID    				BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   CHECK_EXPRESSION     VARCHAR(1000) NOT NULL,
   PROMPT               VARCHAR(1000) NOT NULL,
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_INFO                                     */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_INFO
(
   ID          			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '属性编号',
   PROPERTY_CATEGORY_ID BIGINT UNSIGNED NOT NULL COMMENT '扩展属性分类1.内容基础信息，2.内容分类信息，3.内容版权信息，4.视频媒体社会属性，5.音频媒体社会属性',
   TYPE_ID              BIGINT UNSIGNED COMMENT '字典表的分类',
   PROPERTY_NAME        VARCHAR(100) NOT NULL COMMENT '属性名称',
   DESCRIPTION          VARCHAR(200) NOT NULL COMMENT '描述',
   PROPERTY_CODE        VARCHAR(100) NOT NULL COMMENT '例如  属性：内容ID， 属性名称是内容ID， 属性编码是content_id',
   BELONGING_OBJECT     TINYINT(4) COMMENT '1.内容 2子内容 3编排关系 4栏目',
   DATA_TYPE            TINYINT(4) COMMENT '代码表AF',
   DATA_LENGTH          TINYINT(4) COMMENT '描述数据类型的长度',
   DATA_PRECISION       TINYINT(2) COMMENT '描述float型数据的小数点后位数',
   IF_SEARCH            TINYINT(1) COMMENT '代码表AA',
   IF_LIST              TINYINT(1) COMMENT '代码表AA',
   IF_NOTNULL           TINYINT(1) COMMENT '代码表AA',
   IF_MULTI             TINYINT(1),
   CREATE_TIME          DATETIME NOT NULL COMMENT '创建时间',
   BACKUP               VARCHAR(200),
   TABLENAME            VARCHAR(255),
   PARENT_ID   			BIGINT UNSIGNED,
   SEQUENCE             INT,
   INTERFACE_ADDRESS    VARCHAR(1000),
   DISPLAY_TYPE         TINYINT(2),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_RESTRICT_INFO                            */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_RESTRICT_INFO
(
   ID 					BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   RESTRICT_DESCRIPTION VARCHAR(1000),
   RESTRICT_OBJECT_TYPE TINYINT(4) COMMENT '限制在内容基础类型或是内容分类或是地区或是语言上',
   RESTRICT_VALUE_TYPE  TINYINT(1) COMMENT '单一取值或可取多个值',
   RESTRICT_VALUE       VARCHAR(1000) COMMENT '具体取值',
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CON_SUBCONTENT_DISTRIBUTE_LOG                         */
/*==============================================================*/
CREATE TABLE CON_SUBCONTENT_DISTRIBUTE_LOG
(
   ID        			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   SUBCONTENT_ID        BIGINT UNSIGNED NOT NULL,
   DISTRIBUTE_STATUS    TINYINT(2) NOT NULL,
   CDN_ID               INT,
   DISTRIBUTE_TIME      DATETIME NOT NULL,
   UPDATE_TIME          DATETIME,
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: DICTIONARY_INFO                                       */
/*==============================================================*/
CREATE TABLE DICTIONARY_INFO
(
   ID             		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   VALUE                VARCHAR(255) NOT NULL,
   TYPE_ID              BIGINT UNSIGNED NOT NULL,
   PARENT_ID      		BIGINT UNSIGNED,
   LEVEL                INT(2),
   STATUS               TINYINT(1) NOT NULL,
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: DICTIONARY_TYPE_INFO                                  */
/*==============================================================*/
CREATE TABLE DICTIONARY_TYPE_INFO
(
   ID                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(100),
   CODE                 VARCHAR(100),
   TYPE                 TINYINT(1),
   LEVEL                INT(2),
   STAUS                TINYINT(1) NOT NULL,
   IS_REFERENCE         INT,
   DATA_STRUCTURE       VARCHAR(1000),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LOG_OBJECT_CHANGE_DETAIL_INFO                         */
/*==============================================================*/
CREATE TABLE LOG_OBJECT_CHANGE_DETAIL_INFO
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CHANGE_ID            BIGINT UNSIGNED NOT NULL,
   DETAIL_TYPE          TINYINT(1),
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   STATUS_BEFORE_CHANGE TINYINT(1) NOT NULL,
   STATUS_AFTER_CHANGE  TINYINT(1) NOT NULL,
   DETAIL_RECORD        VARCHAR(1000),
   NOTE                 VARCHAR(1000),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LOG_OBJECT_CHANGE_INFO                                */
/*==============================================================*/
CREATE TABLE LOG_OBJECT_CHANGE_INFO
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   CHANGE_TYPE          TINYINT(1) NOT NULL,
   OPERATOR             INT(11),
   CHANGE_TIME          DATETIME,
   CHANGE_RECORE        VARCHAR(1000),
   NOTE                 VARCHAR(1000),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_ALL                                */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_ALL
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                VARCHAR(10240),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_DATETIME_INDEX                     */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_DATETIME_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                DATETIME DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_FLOAT_INDEX                        */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_FLOAT_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                FLOAT(16,6) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_INT_INDEX                          */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_INT_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                INTEGER(11) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_LONG_INDEX                         */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_LONG_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                BIGINT(20) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_TEXT_INDEX                         */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_TEXT_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                VARCHAR(10240),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_VARCHAR_INDEX                      */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_VARCHAR_INDEX
(
   ID            		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对象ID',
   BELONGING_OBJECT     TINYINT(4) NOT NULL COMMENT '1.内容 2子内容 3编排关系 4栏目',
   OBJECT_ID            BIGINT UNSIGNED NOT NULL,
   PROPERTY_ID          BIGINT UNSIGNED NOT NULL COMMENT '属性编号',
   VALUE                VARCHAR(255),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(1),
   PRIMARY KEY (ID),
   KEY VALUE (VALUE),
   KEY BELONG_OBJECT_PRO_ID (BELONGING_OBJECT,OBJECT_ID,PROPERTY_ID)
);

ALTER TABLE CON_PROPERTY_CHECK_INFO ADD CONSTRAINT FK_REFERENCE_10 FOREIGN KEY (PROPERTY_ID)
      REFERENCES CON_PROPERTY_INFO (ID);

ALTER TABLE CON_PROPERTY_INFO ADD CONSTRAINT FK_REFERENCE_5 FOREIGN KEY (TYPE_ID)
      REFERENCES DICTIONARY_TYPE_INFO (ID);

ALTER TABLE CON_PROPERTY_INFO ADD CONSTRAINT FK_REFERENCE_54 FOREIGN KEY (PROPERTY_CATEGORY_ID)
      REFERENCES CON_PROPERTY_CATEGORY_INFO (ID);

ALTER TABLE CON_PROPERTY_RESTRICT_INFO ADD CONSTRAINT FK_REFERENCE_11 FOREIGN KEY (PROPERTY_ID)
      REFERENCES CON_PROPERTY_INFO (ID);

ALTER TABLE DICTIONARY_INFO ADD CONSTRAINT FK_REFERENCE_4 FOREIGN KEY (TYPE_ID)
      REFERENCES DICTIONARY_TYPE_INFO (ID);

ALTER TABLE LOG_OBJECT_CHANGE_DETAIL_INFO ADD CONSTRAINT FK_REFERENCE_9 FOREIGN KEY (CHANGE_ID)
      REFERENCES LOG_OBJECT_CHANGE_INFO (ID);
