/*
 Navicat Premium Data Transfer

 Source Server         : alicloud
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 47.96.250.239:3306
 Source Schema         : dormitory

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 15/06/2020 20:58:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for institute
-- ----------------------------
DROP TABLE IF EXISTS `institute`;
CREATE TABLE `institute`  (
  `institute_id` tinyint(2) UNSIGNED NOT NULL,
  `institute_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`institute_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学院信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of institute
-- ----------------------------
INSERT INTO `institute` VALUES (1, '信息工程学院');
INSERT INTO `institute` VALUES (2, '机电学院');
INSERT INTO `institute` VALUES (3, '文法学院');

-- ----------------------------
-- Table structure for profession
-- ----------------------------
DROP TABLE IF EXISTS `profession`;
CREATE TABLE `profession`  (
  `profession_id` tinyint(3) UNSIGNED NOT NULL,
  `profession_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `institute_id` tinyint(2) UNSIGNED NOT NULL,
  `profession_rank` tinyint(2) UNSIGNED NOT NULL COMMENT '这个专业在该学院的排序',
  PRIMARY KEY (`profession_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profession
-- ----------------------------
INSERT INTO `profession` VALUES (1, '通信工程', 1, 1);
INSERT INTO `profession` VALUES (2, '信息工程', 1, 2);
INSERT INTO `profession` VALUES (3, '计算机科学与技术', 1, 3);
INSERT INTO `profession` VALUES (4, '物联网工程', 1, 4);
INSERT INTO `profession` VALUES (5, '机械自动化', 2, 1);
INSERT INTO `profession` VALUES (6, '教育', 1, 5);
INSERT INTO `profession` VALUES (7, '电子工程', 2, 2);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `room_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_name` smallint(3) UNSIGNED NOT NULL,
  `building` tinyint(2) UNSIGNED NOT NULL,
  `used_people` tinyint(2) UNSIGNED NOT NULL,
  `max_people` tinyint(2) UNSIGNED NOT NULL DEFAULT 6,
  PRIMARY KEY (`room_id`, `building`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房间信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1, 322, 1, 2, 6);
INSERT INTO `room` VALUES (2, 222, 1, 2, 6);
INSERT INTO `room` VALUES (3, 101, 1, 3, 6);
INSERT INTO `room` VALUES (4, 102, 1, 1, 6);
INSERT INTO `room` VALUES (5, 101, 2, 2, 6);
INSERT INTO `room` VALUES (6, 201, 2, 2, 6);
INSERT INTO `room` VALUES (7, 301, 2, 2, 6);

-- ----------------------------
-- Table structure for studorm
-- ----------------------------
DROP TABLE IF EXISTS `studorm`;
CREATE TABLE `studorm`  (
  `studorm_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生宿舍分配表主键id',
  `stu_number` bigint(11) UNSIGNED NOT NULL COMMENT '学生学号',
  `room_id` smallint(3) UNSIGNED NOT NULL COMMENT '房间号id',
  PRIMARY KEY (`studorm_id`) USING BTREE,
  UNIQUE INDEX `stuinfo_number_UNIQUE`(`stu_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生所在宿舍' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studorm
-- ----------------------------
INSERT INTO `studorm` VALUES (1, 20171544103, 1);
INSERT INTO `studorm` VALUES (2, 20171544110, 1);
INSERT INTO `studorm` VALUES (3, 20171111111, 2);
INSERT INTO `studorm` VALUES (4, 20182132131, 2);
INSERT INTO `studorm` VALUES (5, 20175513465, 3);
INSERT INTO `studorm` VALUES (6, 20176542147, 4);
INSERT INTO `studorm` VALUES (7, 20176542746, 5);
INSERT INTO `studorm` VALUES (8, 21015545414, 7);
INSERT INTO `studorm` VALUES (9, 21015545411, 5);
INSERT INTO `studorm` VALUES (11, 20171544101, 3);
INSERT INTO `studorm` VALUES (12, 20111122222, 3);
INSERT INTO `studorm` VALUES (13, 20171543548, 6);
INSERT INTO `studorm` VALUES (14, 20181543212, 6);
INSERT INTO `studorm` VALUES (15, 20181543222, 7);

-- ----------------------------
-- Table structure for stuinfo
-- ----------------------------
DROP TABLE IF EXISTS `stuinfo`;
CREATE TABLE `stuinfo`  (
  `stu_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生基本信息的主键id',
  `stu_name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生基本信息的名称',
  `stu_number` bigint(11) UNSIGNED NOT NULL COMMENT '学生基本信息的学号',
  `profession_id` tinyint(2) UNSIGNED NOT NULL COMMENT '专业id',
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE INDEX `stu_info_number_UNIQUE`(`stu_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存放学生基础信息——姓名，性别，学号，院系id，专业id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stuinfo
-- ----------------------------
INSERT INTO `stuinfo` VALUES (1, '王55', 20171555103, 1);
INSERT INTO `stuinfo` VALUES (2, '安55', 20171555110, 1);
INSERT INTO `stuinfo` VALUES (3, '申66', 20171111111, 3);
INSERT INTO `stuinfo` VALUES (4, '付44', 20182132131, 5);
INSERT INTO `stuinfo` VALUES (5, '刘33', 20175513465, 6);
INSERT INTO `stuinfo` VALUES (6, '李22', 20176542147, 2);
INSERT INTO `stuinfo` VALUES (7, '嘿嘿嘿', 20176542746, 4);
INSERT INTO `stuinfo` VALUES (9, '啊萨', 21015545414, 1);
INSERT INTO `stuinfo` VALUES (10, '啊撒的', 21015545411, 1);
INSERT INTO `stuinfo` VALUES (11, '王公公', 20171544101, 2);
INSERT INTO `stuinfo` VALUES (13, '任55', 20171543548, 7);
INSERT INTO `stuinfo` VALUES (14, '赵55', 20181543212, 5);
INSERT INTO `stuinfo` VALUES (15, '李55', 20181543222, 5);
INSERT INTO `stuinfo` VALUES (16, '龚55', 2017203333, 1);
INSERT INTO `stuinfo` VALUES (32, '王明2', 20171544113, 2);
INSERT INTO `stuinfo` VALUES (33, '王明3', 20171544114, 5);
INSERT INTO `stuinfo` VALUES (35, '王明4', 20171544116, 5);

SET FOREIGN_KEY_CHECKS = 1;
