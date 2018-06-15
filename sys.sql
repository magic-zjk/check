/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : ad

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-07-17 16:44:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `PERM_ID` int(4) NOT NULL AUTO_INCREMENT,
  `PERM_NAME` varchar(32) DEFAULT NULL,
  `PERMISSION` varchar(128) DEFAULT NULL,
  `PERM_DESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PERM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '所有', '*', null);
INSERT INTO `sys_permission` VALUES ('2', '查询', 'view', null);
INSERT INTO `sys_permission` VALUES ('3', '添加', 'create', '');
INSERT INTO `sys_permission` VALUES ('4', '修改', 'update', null);
INSERT INTO `sys_permission` VALUES ('5', '删除', 'delete', null);

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `RESC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RESC_NAME` varchar(50) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `IDENTITY` varchar(64) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `TYPE` int(2) DEFAULT NULL,
  `ICON` varchar(64) DEFAULT NULL,
  `SORT` int(2) DEFAULT NULL,
  `RESC_DESC` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`RESC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '系统管理', '0', '', '', '1', 'icon-desktop', '1', '系统管理');
INSERT INTO `sys_resource` VALUES ('2', '用户管理', '1', 'user', 'user/listUsers', '1', null, '1', '用户管理');
INSERT INTO `sys_resource` VALUES ('3', '角色管理', '1', 'role', 'role/listRoles', '1', null, '2', null);
INSERT INTO `sys_resource` VALUES ('4', '资源管理', '1', 'resc', 'resc/listRescs', '1', null, '3', null);
INSERT INTO `sys_resource` VALUES ('5', '基础管理', '0', null, null, '1', null, '2', '基础管理');
INSERT INTO `sys_resource` VALUES ('35', 'BASE管理', '5', 'base', 'base/list', '1', null, '6', '游戏数据管理');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(4) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  `ROLE_KEY` varchar(64) DEFAULT NULL,
  `DESC` varchar(200) DEFAULT NULL,
  `STATUS` int(2) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'super-manager', null, '0');

-- ----------------------------
-- Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `ROLE_ID` int(8) NOT NULL,
  `RESC_ID` int(8) NOT NULL,
  `PERMISSION_IDS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`,`RESC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1', '1,2,3,4,5,1,2,3,4,5');
INSERT INTO `sys_role_resource` VALUES ('1', '2', '1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5');
INSERT INTO `sys_role_resource` VALUES ('1', '3', '1,2,3,4,5,1,2,3,4,5,1,2,3,4,5');
INSERT INTO `sys_role_resource` VALUES ('1', '4', '1,2,3,4,5');
INSERT INTO `sys_role_resource` VALUES ('1', '5', '1,2,3,4,5');
INSERT INTO `sys_role_resource` VALUES ('1', '35', '1,2,3,4,5');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(9) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `REAL_NAME` varchar(64) DEFAULT NULL,
  `SALT` varchar(32) DEFAULT NULL,
  `AGE` int(4) DEFAULT NULL,
  `SEX` int(2) DEFAULT NULL,
  `PHONE` varchar(32) DEFAULT NULL,
  `STATUS` int(2) DEFAULT NULL,
  `DEDUCT` double(8,2) DEFAULT '0.00' COMMENT '扣量%',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '85409e7259c5dff73c277f5f520f1835', '超级管理员', 'd180f2629d6251a284c6f37a5bd176fd', '1', '0', '11111111111', '0', '0.00');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  `ROLE_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
