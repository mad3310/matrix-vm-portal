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