CREATE TABLE `WEBPORTAL_CLOUDVM_SERVER_LINK` (
	`ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`REGION` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
	`SERVER_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
	`RELATION` varchar(50) DEFAULT NULL,
	`TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
	`HREF` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
	`DELETED` tinyint(4) DEFAULT NULL,
	`CREATE_TIME` datetime DEFAULT NULL,
	`UPDATE_TIME` datetime DEFAULT NULL,
	`CREATE_USER` bigint(20) unsigned DEFAULT NULL,
	`UPDATE_USER` bigint(20) unsigned DEFAULT NULL,
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
