/*
 Navicat Premium Data Transfer

 Source Server         : TestDB
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : kob

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 15/12/2023 01:10:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bot
-- ----------------------------
DROP TABLE IF EXISTS `bot`;
CREATE TABLE `bot` (
  `user_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `modifytime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of bot
-- ----------------------------
BEGIN;
INSERT INTO `bot` VALUES (7, 9, '最强', '这个用户很懒，什么都没留下～', 'public class Main(){\n  \n  public static void main(String[]ages){\n    System.out.println(\"Hello World\");\n  }\n}', '2023-10-14 21:34:04', '2023-10-14 21:34:04');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `photo` varchar(1000) NOT NULL,
  `rating` int NOT NULL DEFAULT '1500',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_pk` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'gragon', '{noop}p1', '1000000', 1500);
INSERT INTO `user` VALUES (2, 'aoliao', '$2a$10$HrEemIicfVcMv60G0Tb.6uVawWpCpdRp7L0S.9cjCaTOUaYNBJPla', '', 1500);
INSERT INTO `user` VALUES (3, 'test1', '$2a$10$Y3OmmaK8aoZt1ddaG1LJDuapnGPsp5hb4fGYegMZxS1kRw539hmou', '', 1500);
INSERT INTO `user` VALUES (4, 'test2', '$2a$10$8lcmPt85vYFsGTS8jwIkyuExinlew8/y0tFBWpbcbQSZmC9fUymoq', '', 1500);
INSERT INTO `user` VALUES (5, 'test3', '$2a$10$ooexgXyoo4ioHegwmkJWqueDi./V.4s1lmLU7RlWpHxHWFFhOx2y2', 'https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp', 1500);
INSERT INTO `user` VALUES (6, '1', '$2a$10$hNvwOcFFPhIErWP7ZUgk3.O3J6Ni9Ud7DdQNmNGu8MGb1wIz4hJh2', 'https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp', 1500);
INSERT INTO `user` VALUES (7, '2', '$2a$10$vXkfan59WEFZMQJphxzpPeQ5Uq7j0aDGhqynbYhzZ6uC/PemIj5O2', 'https://cdn.acwing.com/media/user/profile/photo/311408_lg_f71c60850d.webp', 1500);
INSERT INTO `user` VALUES (8, 'w', '$2a$10$1VU2L2ixTFJLqLm/1wco2eI/QyURZ1h0ZXxCRgJQbMGFsGYQP3Kau', 'https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp', 1500);
INSERT INTO `user` VALUES (9, '11', '$2a$10$p.D88LkNK6nnetQGqTgR7uXPYX5dovJhhFBLNPbC4XM1c6hN86dGm', 'https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp', 1500);
INSERT INTO `user` VALUES (10, 'test4', 'test4', 'https://cdn.acwing.com/media/user/profile/photo/197053_lg_7ce5da07df.webp', 1500);
INSERT INTO `user` VALUES (11, 'test5', 'e3d704f3542b44a621ebed70dc0efe13', '/uploads/efcae374b2b7e5eaac7d576734759cf7.jpg', 1500);
INSERT INTO `user` VALUES (12, 'test6', '4cfad7076129962ee70c36839a1e3e15', '/uploads/74c01c75ce4318263b934b59b307a726.jpg', 1500);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
