drop table MATRIX_BILLING_BASE_ELEMENT;
CREATE TABLE
    MATRIX_BILLING_BASE_ELEMENT
    (
        ID bigint unsigned NOT NULL AUTO_INCREMENT,
        NAME VARCHAR(50) COLLATE utf8_unicode_ci,
        DESCN VARCHAR(200) COLLATE utf8_unicode_ci,
        DELETED TINYINT,
        CREATE_TIME DATETIME,
        UPDATE_TIME DATETIME,
        CREATE_USER bigint unsigned,
        UPDATE_USER bigint unsigned,
        PRIMARY KEY (ID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
drop table MATRIX_BILLING_BASE_PRICE;
CREATE TABLE
    MATRIX_BILLING_BASE_PRICE
    (
        ID bigint unsigned NOT NULL AUTO_INCREMENT,
        ELEMENT_ID bigint unsigned,
        ELEMENT_STANDARD VARCHAR(10),
        ELEMENT_QUALITY VARCHAR(20),
        BILLING_TYPE TINYINT,
        BY_TIME VARCHAR(10),
        BY_USEDLADDER VARCHAR(20),
        BASE_PRICE  float(30,3),
       
        DELETED TINYINT,
        CREATE_TIME DATETIME,
        UPDATE_TIME DATETIME,
        CREATE_USER bigint unsigned,
        UPDATE_USER bigint unsigned,
        PRIMARY KEY (ID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
 