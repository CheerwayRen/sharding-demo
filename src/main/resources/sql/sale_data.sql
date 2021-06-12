/*
Navicat MySQL Data Transfer

Source Server         : local库
Source Server Version : 80024
Source Host           : localhost:3306
Source Database       : scpg

Target Server Type    : MYSQL
Target Server Version : 80024
File Encoding         : 65001

Date: 2021-06-12 12:35:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mng_sale_amount_day_202012
-- ----------------------------
DROP TABLE IF EXISTS `mng_sale_amount_day_202012`;
CREATE TABLE `mng_sale_amount_day_202012` (
  `ID` bigint NOT NULL COMMENT '主键',
  `MONTH_ID` bigint DEFAULT NULL COMMENT '月销售额ID',
  `SALE_YMD` varchar(8) DEFAULT NULL COMMENT '年月日'
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of mng_sale_amount_day_202012
-- ----------------------------
INSERT INTO `mng_sale_amount_day_202012` VALUES ('20201201', '2020', '20201201');
INSERT INTO `mng_sale_amount_day_202012` VALUES ('20201213', '2020', '20201213');

-- ----------------------------
-- Table structure for mng_sale_amount_day_202101
-- ----------------------------
DROP TABLE IF EXISTS `mng_sale_amount_day_202101`;
CREATE TABLE `mng_sale_amount_day_202101` (
  `ID` bigint NOT NULL COMMENT '主键',
  `MONTH_ID` bigint DEFAULT NULL COMMENT '月销售额ID',
  `SALE_YMD` varchar(8) DEFAULT NULL COMMENT '年月日'
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of mng_sale_amount_day_202101
-- ----------------------------
INSERT INTO `mng_sale_amount_day_202101` VALUES ('20210101', '2020', '20210101');
INSERT INTO `mng_sale_amount_day_202101` VALUES ('20210111', '2020', '20210111');

-- ----------------------------
-- Table structure for mng_sale_amount_month
-- ----------------------------
DROP TABLE IF EXISTS `mng_sale_amount_month`;
CREATE TABLE `mng_sale_amount_month` (
  `ID` bigint NOT NULL,
  `MALL_ID` bigint NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of mng_sale_amount_month
-- ----------------------------
INSERT INTO `mng_sale_amount_month` VALUES ('2020', '2020');
INSERT INTO `mng_sale_amount_month` VALUES ('2021', '2021');
