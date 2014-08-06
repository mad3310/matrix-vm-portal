/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/12/24 17:09:48                          */
/*==============================================================*/


DROP TABLE IF EXISTS CON_CONTENT_INFO;

DROP TABLE IF EXISTS CON_CONTENT_RELATIONSHIP_INFO;

DROP TABLE IF EXISTS CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_CATEGORY_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_CHECK_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_INFO;

DROP TABLE IF EXISTS CON_PROPERTY_RESTRICT_INFO;

DROP TABLE IF EXISTS CON_SUBCONTENT_DISTRIBUTE_LOG;

DROP TABLE IF EXISTS CON_SUBCONTENT_INFO;

DROP TABLE IF EXISTS DICTIONARY_INFO;

DROP TABLE IF EXISTS DICTIONARY_TYPE_INFO;

DROP TABLE IF EXISTS LOG_OBJECT_CHANGE_DETAIL_INFO;

DROP TABLE IF EXISTS LOG_OBJECT_CHANGE_INFO;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_ALL;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_DATETIME_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_FLOAT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_INT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_LONG_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_TEXT_INDEX;

DROP TABLE IF EXISTS PPV_PROPERTY_VALUE_VARCHAR_INDEX;

DROP TABLE IF EXISTS 内容发布流水;

DROP TABLE IF EXISTS 子内容发布流水;

DROP TABLE IF EXISTS 平台权限信息;

DROP TABLE IF EXISTS 平台配置信息;

DROP TABLE IF EXISTS 栏目信息表;

DROP TABLE IF EXISTS 编排关系信息表;

/*==============================================================*/
/* Table: CON_CONTENT_INFO                                      */
/*==============================================================*/
CREATE TABLE CON_CONTENT_INFO
(
   CONTENT_ID           BIGINT NOT NULL AUTO_INCREMENT COMMENT '内容编号',
   CONTENT_VERSION      INT NOT NULL,
   NAME_CN              VARCHAR(200) NOT NULL COMMENT '中文名称',
   NAME_EN              VARCHAR(200) COMMENT '英文名称',
   CONTENT_BASE_TYPE    TINYINT(4) COMMENT '1.目录（越狱）2.父剧集（越狱第一季）3.子剧集（越狱第一集）4.视频（单视频、片花等）',
   CATEGORY             INT(4) COMMENT '电影、电视剧、动漫、娱乐',
   CHANNEL_ID           INT COMMENT '1.网站,2.大咔，3.第三方',
   USER_ID              VARCHAR(200),
   STATUS               INT(5) COMMENT '审核状态： 0,未审核 1正在审核2审核通过',
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (CONTENT_ID, CONTENT_VERSION)
);

/*==============================================================*/
/* Table: CON_CONTENT_RELATIONSHIP_INFO                         */
/*==============================================================*/
CREATE TABLE CON_CONTENT_RELATIONSHIP_INFO
(
   CONTENT_ID_A         BIGINT DEFAULT NULL,
   CONTENT_VERSION_A    INTEGER(11) DEFAULT 0,
   CONTENT_ID_B         BIGINT DEFAULT NULL,
   CONTENT_VERSION_B    INTEGER(11) DEFAULT 0,
   RELATIONSHIP         INTEGER(11) DEFAULT 0,
   IS_DELETE            TINYINT(4),
   KEY CONTENT_ID_A (CONTENT_ID_A),
   KEY CONTENT_VERSION_A (CONTENT_VERSION_A),
   KEY CONTENT_ID_B (CONTENT_ID_B),
   KEY CONTENT_VERSION_B (CONTENT_VERSION_B),
   KEY RELATIONSHIP (RELATIONSHIP)
)
ENGINE=INNODB
ROW_FORMAT=FIXED CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO              */
/*==============================================================*/
CREATE TABLE CON_CONTENT_SUBCONTENT_RELATIONSHIP_INFO
(
   CONTENT_ID           BIGINT NOT NULL,
   CONTENT_VERSION      INT,
   SUBCONTENT_ID        BIGINT,
   SUBCONTENT_VERSION   INT,
   RELATIONSHIP         INT,
   IS_DELETE            TINYINT(4)
);

/*==============================================================*/
/* Table: CON_PROPERTY_CATEGORY_INFO                            */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_CATEGORY_INFO
(
   PROPERTY_CATEGORY_ID INT NOT NULL AUTO_INCREMENT COMMENT '扩展属性分类',
   PROPERTY_CATEGORY_OBJECT TINYINT(4) NOT NULL COMMENT '内容、子内容、栏目、编排关系',
   PROPERTY_CATEGORY_NAME VARCHAR(50) NOT NULL COMMENT '分类名称',
   DESCRIPTION          VARCHAR(100) COMMENT '描述',
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (PROPERTY_CATEGORY_ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_CHECK_INFO                               */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_CHECK_INFO
(
   PROPERTY_CHECK_ID    INT NOT NULL,
   PROPERTY_ID          SMALLINT(6) COMMENT '属性编号',
   CHECK_EXPRESSION     VARCHAR(1000),
   PROMPT               VARCHAR(1000),
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (PROPERTY_CHECK_ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_INFO                                     */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_INFO
(
   PROPERTY_ID          SMALLINT(6) NOT NULL AUTO_INCREMENT COMMENT '属性编号',
   PROPERTY_CATEGORY_ID INT COMMENT '扩展属性分类1.内容基础信息，2.内容分类信息，3.内容版权信息，4.视频媒体社会属性，5.音频媒体社会属性',
   TYPE_ID              INT COMMENT '字典表的分类',
   PROPERTY_NAME        VARCHAR(100) COMMENT '属性名称',
   DESCRIPTION          VARCHAR(200) COMMENT '描述',
   PROPERTY_CODE        VARCHAR(100) COMMENT '例如  属性：内容ID， 属性名称是内容ID， 属性编码是content_id',
   PROPERTY_OBJECT      TINYINT(4) COMMENT '1.内容 2子内容 3编排关系 4栏目',
   DATA_TYPE            TINYINT(4) COMMENT '代码表AF',
   DATA_LENGTH          INT(4) COMMENT '描述数据类型的长度',
   DATA_PRECISION       TINYINT(4) COMMENT '描述float型数据的小数点后位数',
   IF_SEARCH            TINYINT(4) COMMENT '代码表AA',
   IF_LIST              TINYINT(4) COMMENT '代码表AA',
   IF_NOTNULL           TINYINT(4) COMMENT '代码表AA',
   IF_MULTI             TINYINT(4),
   CREATE_TIME          DATETIME NOT NULL COMMENT '创建时间',
   BACKUP               VARCHAR(200),
   TABLENAME            VARCHAR(255),
   PARENT_PROPERTY_ID   INT,
   SEQUENCE             INT,
   INTERFACE_ADDRESS    VARCHAR(1000),
   DISPLAY_TYPE         INT,
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (PROPERTY_ID)
);

/*==============================================================*/
/* Table: CON_PROPERTY_RESTRICT_INFO                            */
/*==============================================================*/
CREATE TABLE CON_PROPERTY_RESTRICT_INFO
(
   PROPERTY_RESTRICT_ID INT NOT NULL,
   PROPERTY_ID          SMALLINT(6) COMMENT '属性编号',
   RESTRICT_DESCRIPTION VARCHAR(1000),
   RESTRICT_OBJECT_TYPE TINYINT COMMENT '限制在内容基础类型或是内容分类或是地区或是语言上',
   RESTRICT_VALUE_TYPE  TINYINT COMMENT '单一取值或可取多个值',
   RESTRICT_VALUE       VARCHAR(1000) COMMENT '具体取值',
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (PROPERTY_RESTRICT_ID)
);

/*==============================================================*/
/* Table: CON_SUBCONTENT_DISTRIBUTE_LOG                         */
/*==============================================================*/
CREATE TABLE CON_SUBCONTENT_DISTRIBUTE_LOG
(
   DISTRIBUTE_ID        BIGINT NOT NULL,
   SUBCONTENT_ID        BIGINT,
   SUBCONTENT_VERSION   INT,
   DISTRIBUTE_STATUS    INT,
   CDN_ID               INT,
   DISTRIBUTE_TIME      DATETIME,
   UPDATE_TIME          DATETIME,
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (DISTRIBUTE_ID)
);

/*==============================================================*/
/* Table: CON_SUBCONTENT_INFO                                   */
/*==============================================================*/
CREATE TABLE CON_SUBCONTENT_INFO
(
   SUBCONTENT_ID        BIGINT NOT NULL AUTO_INCREMENT,
   SUBCONTENT_VERSION   INT NOT NULL,
   SUBCONTENT_NAME      VARCHAR(255),
   SUBCONTENT_TYPE      TINYINT(4) COMMENT '1. 视频文件 2. 图片文件 3. 字幕文件',
   VIDEO_CODE_VERSION   VARCHAR(255),
   STATUS               INT(5) COMMENT '审核状态： 0,未审核 1正在审核2审核通过',
   IS_DELETE            TINYINT(4),
   CDN_PATH_LETV_DEFAULT VARCHAR(1000),
   PRIMARY KEY (SUBCONTENT_ID, SUBCONTENT_VERSION)
);

/*==============================================================*/
/* Table: DICTIONARY_INFO                                       */
/*==============================================================*/
CREATE TABLE DICTIONARY_INFO
(
   VALUE_ID             INT NOT NULL,
   VALUE                VARCHAR(255) NOT NULL,
   TYPE_ID              INT,
   PARENT_VALUE_ID      INT,
   LEVEL                INT,
   STATUS               INT,
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (VALUE_ID)
);

/*==============================================================*/
/* Table: DICTIONARY_TYPE_INFO                                  */
/*==============================================================*/
CREATE TABLE DICTIONARY_TYPE_INFO
(
   ID                   INT NOT NULL,
   NAME                 VARCHAR(100),
   CODE                 VARCHAR(100),
   TYPE                 INT,
   LEVEL                INT,
   STAUS                INT,
   IS_REFERENCE         INT,
   DATA_STRUCTURE       VARCHAR(1000),
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LOG_OBJECT_CHANGE_DETAIL_INFO                         */
/*==============================================================*/
CREATE TABLE LOG_OBJECT_CHANGE_DETAIL_INFO
(
   DETAIL_ID            BIGINT NOT NULL,
   CHANGE_ID            BIGINT,
   DETAIL_TYPE          INT,
   OBJECT_TYPE          INT,
   OBJECT_ID            BIGINT,
   OBJECT_VERSION       INT,
   STATUS_BEFORE_CHANGE INT,
   STATUS_AFTER_CHANGE  INT,
   DETAIL_RECORD        VARCHAR(1000),
   NOTE                 VARCHAR(1000),
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (DETAIL_ID)
);

/*==============================================================*/
/* Table: LOG_OBJECT_CHANGE_INFO                                */
/*==============================================================*/
CREATE TABLE LOG_OBJECT_CHANGE_INFO
(
   CHANGE_ID            BIGINT NOT NULL,
   CHANGE_TYPE          INT,
   OPERATOR             INT,
   CHANGE_TIME          DATETIME,
   CHANGE_RECORE        VARCHAR(1000),
   NOTE                 VARCHAR(1000),
   IS_DELETE            TINYINT(4),
   PRIMARY KEY (CHANGE_ID)
);

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_ALL                                */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_ALL
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                VARCHAR(10240),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_DATETIME_INDEX                     */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_DATETIME_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                DATETIME DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY VALUE (VALUE),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_FLOAT_INDEX                        */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_FLOAT_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                FLOAT(16,6) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY VALUE (VALUE),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_INT_INDEX                          */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_INT_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                INTEGER(11) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY VALUE (VALUE),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_LONG_INDEX                         */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_LONG_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                BIGINT(20) DEFAULT NULL,
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY VALUE (VALUE),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_TEXT_INDEX                         */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_TEXT_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                VARCHAR(10240),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: PPV_PROPERTY_VALUE_VARCHAR_INDEX                      */
/*==============================================================*/
CREATE TABLE PPV_PROPERTY_VALUE_VARCHAR_INDEX
(
   OBJECT_ID            BIGINT UNSIGNED DEFAULT 0 COMMENT '对象ID',
   OBJECT_VERSION       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '对象版本',
   OBJECT_TYPE_ID       TINYINT(4) UNSIGNED DEFAULT 0 COMMENT '1.内容 2子内容 3编排关系 4栏目',
   PROPERTY_ID          SMALLINT(6) UNSIGNED DEFAULT 0 COMMENT '属性编号',
   VALUE                VARCHAR(255),
   NOTES                VARCHAR(50),
   IS_DELETE            TINYINT(4),
   KEY VALUE (VALUE),
   KEY OBJECT_ID (OBJECT_ID),
   KEY OBJECT_VERSION (OBJECT_VERSION)
)
ENGINE=INNODB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

/*==============================================================*/
/* Table: 内容发布流水                                                */
/*==============================================================*/
CREATE TABLE 内容发布流水
(
   内容发布流水ID             CHAR(10) NOT NULL,
   内容编码                 CHAR(10),
   内容版本号                CHAR(10),
   内容发布状态               CHAR(10),
   PRIMARY KEY (内容发布流水ID)
);

/*==============================================================*/
/* Table: 子内容发布流水                                               */
/*==============================================================*/
CREATE TABLE 子内容发布流水
(
   子内容发布流水              CHAR(10) NOT NULL,
   内容发布流水ID             CHAR(10),
   子内容编码                CHAR(10),
   子内容版本号               CHAR(10),
   PRIMARY KEY (子内容发布流水)
);

/*==============================================================*/
/* Table: 平台权限信息                                                */
/*==============================================================*/
CREATE TABLE 平台权限信息
(
   权限ID                 CHAR(10) NOT NULL,
   平台ID                 CHAR(10),
   是否默认                 CHAR(10),
   PRIMARY KEY (权限ID)
);

/*==============================================================*/
/* Table: 平台配置信息                                                */
/*==============================================================*/
CREATE TABLE 平台配置信息
(
   平台ID                 CHAR(10) NOT NULL,
   平台类型                 CHAR(10),
   PRIMARY KEY (平台ID)
);

/*==============================================================*/
/* Table: 栏目信息表                                                 */
/*==============================================================*/
CREATE TABLE 栏目信息表
(
   栏目ID                 CHAR(10) NOT NULL,
   栏目名称                 CHAR(10),
   父栏目名称                CHAR(10),
   栏目描述                 CHAR(10),
   栏目类型                 CHAR(10),
   栏目层级                 CHAR(10),
   适用终端                 CHAR(10),
   适用码流                 CHAR(10),
   创建时间                 CHAR(10),
   更新时间                 CHAR(10),
   栏目状态                 CHAR(10),
   PRIMARY KEY (栏目ID)
);

/*==============================================================*/
/* Table: 编排关系信息表                                               */
/*==============================================================*/
CREATE TABLE 编排关系信息表
(
   编排关系ID               CHAR(10) NOT NULL,
   栏目ID                 CHAR(10),
   内容编号                 CHAR(10),
   内容版本号                CHAR(10),
   创建时间                 CHAR(10),
   更新时间                 CHAR(10),
   排序号                  CHAR(10),
   PRIMARY KEY (编排关系ID)
);

ALTER TABLE CON_PROPERTY_CHECK_INFO ADD CONSTRAINT FK_REFERENCE_10 FOREIGN KEY (PROPERTY_ID)
      REFERENCES CON_PROPERTY_INFO (PROPERTY_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE CON_PROPERTY_INFO ADD CONSTRAINT FK_REFERENCE_5 FOREIGN KEY (TYPE_ID)
      REFERENCES DICTIONARY_TYPE_INFO (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE CON_PROPERTY_INFO ADD CONSTRAINT FK_REFERENCE_54 FOREIGN KEY (PROPERTY_CATEGORY_ID)
      REFERENCES CON_PROPERTY_CATEGORY_INFO (PROPERTY_CATEGORY_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE CON_PROPERTY_RESTRICT_INFO ADD CONSTRAINT FK_REFERENCE_11 FOREIGN KEY (PROPERTY_ID)
      REFERENCES CON_PROPERTY_INFO (PROPERTY_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DICTIONARY_INFO ADD CONSTRAINT FK_REFERENCE_4 FOREIGN KEY (TYPE_ID)
      REFERENCES DICTIONARY_TYPE_INFO (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE LOG_OBJECT_CHANGE_DETAIL_INFO ADD CONSTRAINT FK_REFERENCE_9 FOREIGN KEY (CHANGE_ID)
      REFERENCES LOG_OBJECT_CHANGE_INFO (CHANGE_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE 子内容发布流水 ADD CONSTRAINT FK_REFERENCE_7 FOREIGN KEY (内容发布流水ID)
      REFERENCES 内容发布流水 (内容发布流水ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE 平台权限信息 ADD CONSTRAINT FK_REFERENCE_8 FOREIGN KEY (平台ID)
      REFERENCES 平台配置信息 (平台ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE 编排关系信息表 ADD CONSTRAINT FK_REFERENCE_6 FOREIGN KEY (栏目ID)
      REFERENCES 栏目信息表 (栏目ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

