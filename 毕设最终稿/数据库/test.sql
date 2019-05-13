/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 47.102.127.27:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 12/05/2019 15:29:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `aname` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `aresult` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `aid_tid` int(11) NOT NULL,
  `startDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `endDate` datetime(0) NULL DEFAULT NULL,
  `astate` tinyint(1) NULL DEFAULT NULL,
  `acontent` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `aid_tid`(`aid_tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '0001activity1', '提交json文档', 7, '2019-04-30 17:58:41', '2019-04-19 16:06:40', 2, '完成测试');
INSERT INTO `activity` VALUES (2, 'activity2', '提交json文档', 7, '2019-05-12 09:29:03', '2019-05-14 16:06:40', 1, '完成测试');
INSERT INTO `activity` VALUES (3, 'activity3', '提交json文档', 7, '2019-04-30 17:58:51', '2019-04-19 16:06:40', 2, '完成测试');
INSERT INTO `activity` VALUES (4, 'activity4', '提交json文档', 7, '2019-04-30 17:58:53', '2019-04-19 16:06:40', 2, '完成测试');
INSERT INTO `activity` VALUES (15, '活动1', 'json', 38, '2019-04-30 18:26:17', '2019-04-12 08:04:21', 2, '完成测试');
INSERT INTO `activity` VALUES (16, '活动1', 'json', 39, '2019-04-30 16:37:06', '2019-04-12 08:04:33', 3, '完成测试');
INSERT INTO `activity` VALUES (17, '活动1', 'json', 39, '2019-04-30 16:37:06', '2019-04-12 09:04:30', 3, '完成测试');
INSERT INTO `activity` VALUES (18, 'yes ', 'thanks', 43, '2019-05-17 13:02:00', '2019-06-19 13:02:00', 0, 'thank');
INSERT INTO `activity` VALUES (19, 'thanks', 'thanks', 44, '2019-04-21 05:23:06', '2019-05-19 05:23:00', 1, 'thanks');
INSERT INTO `activity` VALUES (39, 'thanks', 'thanks', 57, '2019-05-08 03:59:06', '2019-05-09 03:59:00', 1, 'thanks');
INSERT INTO `activity` VALUES (40, 'thanks', 'thanks', 57, '2019-05-06 14:22:25', '2019-05-09 03:59:00', 1, 'thanks');
INSERT INTO `activity` VALUES (41, 'ty ', 'thanks', 59, '2019-05-12 09:51:29', '2019-05-15 06:21:00', 1, 'thanks');
INSERT INTO `activity` VALUES (42, '任务42', 'thanks', 59, '2019-05-12 09:53:47', '2019-05-15 06:21:00', 1, 'thanks');
INSERT INTO `activity` VALUES (43, '需求分析', '需求分析报告', 60, '2019-05-12 13:04:44', '2019-06-12 04:32:00', 1, '获取需求');
INSERT INTO `activity` VALUES (44, '系统设计', '系统设计分案', 60, '2019-05-12 13:07:06', '2019-07-10 04:32:00', 1, '系统设计');
INSERT INTO `activity` VALUES (45, '需求分析', '需求分析报告', 61, '2019-05-14 04:41:00', '2019-06-12 04:41:00', 0, '完成需求分析');

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user`  (
  `aid` int(11) NOT NULL,
  `accid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`accid`, `aid`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  CONSTRAINT `activity_user_ibfk_2` FOREIGN KEY (`accid`) REFERENCES `userinfo` (`accid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_user
-- ----------------------------
INSERT INTO `activity_user` VALUES (1, '10000');
INSERT INTO `activity_user` VALUES (1, '10001');
INSERT INTO `activity_user` VALUES (1, '10002');
INSERT INTO `activity_user` VALUES (1, '10004');
INSERT INTO `activity_user` VALUES (2, '10001');
INSERT INTO `activity_user` VALUES (2, '10002');
INSERT INTO `activity_user` VALUES (2, '10004');
INSERT INTO `activity_user` VALUES (3, '10002');
INSERT INTO `activity_user` VALUES (39, '10001');
INSERT INTO `activity_user` VALUES (40, '10001');
INSERT INTO `activity_user` VALUES (41, '10002');
INSERT INTO `activity_user` VALUES (42, '10000');
INSERT INTO `activity_user` VALUES (42, '10002');
INSERT INTO `activity_user` VALUES (43, '10001');
INSERT INTO `activity_user` VALUES (43, '10002');
INSERT INTO `activity_user` VALUES (43, '10004');
INSERT INTO `activity_user` VALUES (44, '10001');
INSERT INTO `activity_user` VALUES (44, '10002');
INSERT INTO `activity_user` VALUES (44, '10004');
INSERT INTO `activity_user` VALUES (45, '10001');
INSERT INTO `activity_user` VALUES (45, '10002');

-- ----------------------------
-- Table structure for apn_user
-- ----------------------------
DROP TABLE IF EXISTS `apn_user`;
CREATE TABLE `apn_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_date` datetime(0) NULL DEFAULT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isRead` tinyint(1) NULL DEFAULT NULL,
  `mid_aid` int(11) NULL DEFAULT NULL,
  `mid_tid` int(11) NULL DEFAULT NULL,
  `mtype` tinyint(1) NOT NULL DEFAULT 0 COMMENT '为0表示是任务消息，表示为1表示的活动消息',
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `tip_message_ibfk_1`(`receiver`) USING BTREE,
  INDEX `tip_message_ibfk_2`(`mid_aid`) USING BTREE,
  INDEX `tip_message_ibfk_3`(`mid_tid`) USING BTREE,
  CONSTRAINT `tip_message_ibfk_1` FOREIGN KEY (`receiver`) REFERENCES `userinfo` (`accid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tip_message_ibfk_2` FOREIGN KEY (`mid_aid`) REFERENCES `activity` (`aid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tip_message_ibfk_3` FOREIGN KEY (`mid_tid`) REFERENCES `task` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, '10000', '测试', 1, 18, 15, 0);
INSERT INTO `message` VALUES (15, '10001', '有新的任务待完成', 0, 39, 58, 0);
INSERT INTO `message` VALUES (16, '10001', '有新的任务待完成', 0, 40, 58, 0);
INSERT INTO `message` VALUES (17, '10002', '有新的任务待完成', 0, 41, 59, 0);
INSERT INTO `message` VALUES (18, '10002', '有新的任务待完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (19, '10000', '有新的任务待完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (20, '10000', '您的任务将要结束请尽快完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (21, '10002', '您的任务将要结束请尽快完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (22, '10000', '您的任务将要结束请尽快完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (23, '10002', '您的任务将要结束请尽快完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (24, '10000', '您的任务将要结束请尽快完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (25, '10002', '您的任务将要结束请尽快完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (26, '10000', '您的任务将要结束请尽快完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (27, '10002', '您的任务将要结束请尽快完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (28, '10001', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (29, '10002', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (30, '10004', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (31, '10002', '您的任务将要结束请尽快完成', 0, 41, 59, 0);
INSERT INTO `message` VALUES (32, '10001', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (33, '10002', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (34, '10004', '您的任务将要结束请尽快完成', 0, 2, 7, 0);
INSERT INTO `message` VALUES (35, '10000', '您的任务将要结束请尽快完成', 1, 42, 59, 0);
INSERT INTO `message` VALUES (36, '10002', '您的任务将要结束请尽快完成', 0, 42, 59, 0);
INSERT INTO `message` VALUES (37, '10001', '有新的任务待完成', 0, 43, 60, 0);
INSERT INTO `message` VALUES (38, '10002', '有新的任务待完成', 0, 43, 60, 0);
INSERT INTO `message` VALUES (39, '10004', '有新的任务待完成', 0, 43, 60, 0);
INSERT INTO `message` VALUES (40, '10001', '有新的任务待完成', 0, 44, 60, 0);
INSERT INTO `message` VALUES (41, '10002', '有新的任务待完成', 0, 44, 60, 0);
INSERT INTO `message` VALUES (42, '10004', '有新的任务待完成', 0, 44, 60, 0);
INSERT INTO `message` VALUES (43, '10002', '有新的任务待完成', 0, 45, 61, 0);

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcreator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcreateDate` datetime(0) NULL DEFAULT NULL,
  `tstartDate` datetime(0) NULL DEFAULT NULL,
  `tendDate` datetime(0) NULL DEFAULT NULL,
  `tcontent` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tresult` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ttype` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tstate` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tid`) USING BTREE,
  INDEX `FK_Reference_2`(`tcreator`) USING BTREE,
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`tcreator`) REFERENCES `userinfo` (`accid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (7, 'test7', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-05-15 11:31:20', 'test1', 'json ', 'teat', 1);
INSERT INTO `task` VALUES (8, 'yytest7', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (10, 'task8', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (11, '10', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (12, '11', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (13, '12', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (14, '13', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (15, '14', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (16, '15', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (17, '16', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (18, '17', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (20, '19', '10000', '2019-03-01 11:31:13', '2019-04-26 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (21, '20', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (22, '21', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (23, '22', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (24, '23', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (25, '24', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (26, '25', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (27, '26', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (28, '任务', '10000', '2019-03-30 11:31:13', '2019-03-21 11:31:16', '2019-04-07 11:31:20', 'test1', 'json ', 'teat', 3);
INSERT INTO `task` VALUES (38, '测试', '10000', '2019-04-12 08:04:25', '2019-04-16 08:04:00', '2019-04-20 08:04:00', 'thanks', 'thank', '', 3);
INSERT INTO `task` VALUES (39, '测试', '10000', '2019-04-12 08:04:44', '2019-04-14 08:04:00', '2019-04-30 08:04:00', 'thanks', 'thank', '', 3);
INSERT INTO `task` VALUES (40, '测试', '10000', '2019-04-12 08:04:44', '2019-04-14 08:04:00', '2019-04-30 08:04:00', 'thanks', 'thank', '', 2);
INSERT INTO `task` VALUES (41, '测试', '10000', '2019-04-12 08:04:44', '2019-04-14 08:04:00', '2019-04-30 08:04:00', 'thanks', 'thank', '', 2);
INSERT INTO `task` VALUES (42, '测试', '10000', '2019-04-12 08:04:44', '2019-04-14 08:04:00', '2019-05-17 08:04:00', 'thanks', 'thank', '', 1);
INSERT INTO `task` VALUES (43, 'thanks', '10000', '2019-04-17 13:04:48', '2019-04-18 13:04:00', '2019-07-17 13:07:00', 'thanks', 'thank', '', 1);
INSERT INTO `task` VALUES (44, 'thanks', '10000', '2019-04-19 05:04:18', '2019-04-20 05:04:00', '2019-06-19 05:06:00', 'thanks', 'thanks so', '', 1);
INSERT INTO `task` VALUES (58, 'task ', '10000', '2019-05-06 03:05:58', '2019-05-08 03:05:00', '2019-05-14 07:05:00', 'thanks', 'I will', '', 1);
INSERT INTO `task` VALUES (59, 'thanks', '10000', '2019-05-06 06:05:45', '2019-05-06 07:05:00', '2019-05-15 06:05:00', 'thanks', 'we', '', 1);
INSERT INTO `task` VALUES (60, '微信小程序', '10000', '2019-05-12 04:05:31', '2019-05-12 06:05:00', '2019-10-12 04:10:00', 'complete a wechat application', '一个微信小程序', '', 1);
INSERT INTO `task` VALUES (61, 'web任务', '10000', '2019-05-12 04:05:55', '2019-05-13 04:05:00', '2019-10-12 04:10:00', '完成web开发与部署', 'web网站', '', 0);

-- ----------------------------
-- Table structure for user_task
-- ----------------------------
DROP TABLE IF EXISTS `user_task`;
CREATE TABLE `user_task`  (
  `tid` int(11) NOT NULL,
  `accid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`tid`, `accid`) USING BTREE,
  INDEX `FK_Reference_3`(`tid`) USING BTREE,
  INDEX `FK_Reference_4`(`accid`) USING BTREE,
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`tid`) REFERENCES `task` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`accid`) REFERENCES `userinfo` (`accid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_task
-- ----------------------------
INSERT INTO `user_task` VALUES (7, '10000');
INSERT INTO `user_task` VALUES (7, '10001');
INSERT INTO `user_task` VALUES (7, '10002');
INSERT INTO `user_task` VALUES (7, '10003');
INSERT INTO `user_task` VALUES (7, '10004');
INSERT INTO `user_task` VALUES (7, '10005');
INSERT INTO `user_task` VALUES (7, '10006');
INSERT INTO `user_task` VALUES (7, '10007');
INSERT INTO `user_task` VALUES (7, '10008');
INSERT INTO `user_task` VALUES (7, '10009');
INSERT INTO `user_task` VALUES (8, '10000');
INSERT INTO `user_task` VALUES (8, '10001');
INSERT INTO `user_task` VALUES (10, '10001');
INSERT INTO `user_task` VALUES (20, '10000');
INSERT INTO `user_task` VALUES (21, '10000');
INSERT INTO `user_task` VALUES (22, '10000');
INSERT INTO `user_task` VALUES (23, '10000');
INSERT INTO `user_task` VALUES (24, '10000');
INSERT INTO `user_task` VALUES (25, '10000');
INSERT INTO `user_task` VALUES (26, '10000');
INSERT INTO `user_task` VALUES (27, '10000');
INSERT INTO `user_task` VALUES (28, '10000');
INSERT INTO `user_task` VALUES (61, '10001');
INSERT INTO `user_task` VALUES (61, '10002');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `accid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `upassword` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sign` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birth` date NULL DEFAULT NULL,
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ex` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`accid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('10000', '666666', 'lee', '', '1047662425@qq.com', 'this is atest sign', '2019-03-14', '12348567892', '2', '');
INSERT INTO `userinfo` VALUES ('10001', '656656', 'tx', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10002', '656656', '李四', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10003', '656656', '小猪', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10004', '656656', '王二', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10005', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10006', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10007', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10008', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10009', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');
INSERT INTO `userinfo` VALUES ('10010', '656656', 'lee', '', '56562356@qq.com', 'tttttt', '2019-03-20', '235689415', '1', '');

-- ----------------------------
-- Event structure for event_minute_activity_state
-- ----------------------------
DROP EVENT IF EXISTS `event_minute_activity_state`;
delimiter ;;
CREATE EVENT `event_minute_activity_state`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2019-04-13 16:24:06'
COMMENT '每分钟更新一次activity表中的状态'
DO update activity a

set a.astate = (

case 

  when a.startDate>now() AND a.endDate>now() then 0  -- 未开始

--   when a.endDate<now()   then 3    -- 过期

  when a.startDate<now() AND a.endDate>now() AND a.astate!=2 THEN 1  -- 进行中
	else a.astate

end)
;;
delimiter ;

-- ----------------------------
-- Event structure for event_minute_task_state
-- ----------------------------
DROP EVENT IF EXISTS `event_minute_task_state`;
delimiter ;;
CREATE EVENT `event_minute_task_state`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2019-04-13 16:53:39'
COMMENT '每分钟更新一次task表中的状态'
DO update task t

set t.tstate = (

case 

  when t.tstartDate>now() AND t.tendDate>now() then 0  -- 未开始

--   when t.tendDate<now() then 3    -- 过期

  when t.tstartDate<now() AND t.tendDate>now() AND t.tstate!=2 THEN 1  -- 进行中
	else t.tstate

end)
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
