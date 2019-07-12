/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost
 Source Database       : dbshiro

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 12/27/2018 16:17:18 PM
*/

CREATE DATABASE `dbshiro` DEFAULT CHARACTER SET utf8 ;

USE `dbshiro`;



-- 权限表
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(30) NOT NULL COMMENT '权限名称',
  `createdatetime` datetime NOT NULL,
  `modifydatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `permission` VALUES ('1', 'school:create', '2018-12-06 10:41:51', '2018-12-06 10:41:53'), ('2', 'student:create', '2018-12-26 19:09:02', '2018-12-26 19:09:03');


-- 角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `createdatetime` datetime NOT NULL,
  `modifydatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '2018-12-06 10:39:07', '2018-12-06 10:39:10'), ('2', 'ROLE_SCHOOLADMIN', '2018-12-26 19:07:30', '2018-12-26 19:07:32');



-- 角色权限关系表
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `createdatetime` datetime NOT NULL,
  `modifydatetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_id` (`permission_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `permission_id_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `role_id_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `role_permission` VALUES ('1', '1', '1', '2018-12-06 10:42:09', '2018-12-06 10:42:11'), ('2', '2', '2', '2018-12-26 19:09:14', '2018-12-26 19:09:15');



--用户表(用于登录)
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL COMMENT '登录名',
  `password` varchar(30) NOT NULL COMMENT '密码',
  `createdatetime` datetime NOT NULL,
  `modifydatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'admin', '123', '2018-12-06 10:32:00', '2018-12-06 10:32:04'), ('2', 'lixinyu', '123', '2018-12-26 19:03:34', '2018-12-26 19:03:35');



-- 用户角色关系表
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `createdatetime` datetime NOT NULL,
  `modifydatetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_id_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `user_role` VALUES ('1', '1', '1', '2018-12-06 10:38:49', '2018-12-06 10:38:51'), ('2', '2', '2', '2018-12-27 11:45:10', '2018-12-27 11:45:12');

