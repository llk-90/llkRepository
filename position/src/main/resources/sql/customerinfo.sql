/*
Navicat MySQL Data Transfer

Source Server         : 172.29.139.10_3306
Source Server Version : 50628
Source Host           : 172.29.139.10:3306
Source Database       : llk

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-07-19 11:27:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customerinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerinfo`;
CREATE TABLE `customerinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `key_id` varchar(50) NOT NULL COMMENT 'openid',
  `lng` varchar(20) NOT NULL COMMENT '经度',
  `lat` varchar(20) NOT NULL COMMENT '纬度',
  `cus_name` varchar(50) NOT NULL COMMENT '客户姓名',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `icon` text COMMENT '图片地址',
  `visits_count` varchar(50) NOT NULL DEFAULT '0' COMMENT '拜访次数 (0:预约 1：1次 2：2次  3:2次以上 4:成单 )',
  `create_time` datetime NOT NULL COMMENT '建立时间',
  `baifang_time` datetime NOT NULL COMMENT '拜访时间',
  `notes` text COMMENT '位置信息的解释',
  `phone` varchar(11) NOT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
