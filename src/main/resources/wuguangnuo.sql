/*
Navicat MySQL Data Transfer

Source Server         : wgn70
Source Server Version : 50729
Source Host           : 114.215.145.70:3306
Source Database       : wuguangnuo

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-05-10 10:22:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wu_blog
-- ----------------------------
DROP TABLE IF EXISTS `wu_blog`;
CREATE TABLE `wu_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_title` text NOT NULL COMMENT '文章标题',
  `post_author` varchar(64) NOT NULL DEFAULT '佚名' COMMENT '文章作者',
  `post_type` text COMMENT '文章分类',
  `post_content` longtext NOT NULL COMMENT '文章内容',
  `post_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期',
  `post_from` varchar(64) DEFAULT NULL COMMENT '文章来源',
  `post_link` varchar(255) DEFAULT NULL COMMENT '原链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='諾的博客';

-- ----------------------------
-- Table structure for wu_call
-- ----------------------------
DROP TABLE IF EXISTS `wu_call`;
CREATE TABLE `wu_call` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lk` varchar(255) DEFAULT NULL COMMENT 'link',
  `ip` int(4) DEFAULT NULL COMMENT 'ip',
  `ag` varchar(1023) DEFAULT NULL COMMENT 'agent',
  `tm` datetime DEFAULT NULL COMMENT 'datetime',
  `us` varchar(20) DEFAULT NULL COMMENT 'user',
  PRIMARY KEY (`id`),
  KEY `lk` (`lk`),
  KEY `ag` (`ag`(255)),
  KEY `tm` (`tm`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='访客统计';

-- ----------------------------
-- Table structure for wu_demo
-- ----------------------------
DROP TABLE IF EXISTS `wu_demo`;
CREATE TABLE `wu_demo` (
  `id` bigint(20) NOT NULL,
  `demo_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo名称',
  `demo_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'demo作者',
  `demo_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo链接',
  `demo_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的DEMO';

-- ----------------------------
-- Table structure for wu_diary
-- ----------------------------
DROP TABLE IF EXISTS `wu_diary`;
CREATE TABLE `wu_diary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `diary_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日记标题',
  `diary_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '日记关键词',
  `diary_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日记内容',
  `diary_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='諾的日记';

-- ----------------------------
-- Table structure for wu_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `wu_dictionary`;
CREATE TABLE `wu_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_key` varchar(32) NOT NULL COMMENT '字典类型',
  `code_index` varchar(64) NOT NULL COMMENT '字典索引',
  `code_value` varchar(255) NOT NULL COMMENT '字典值',
  `code_note` varchar(64) NOT NULL COMMENT '字典注释',
  PRIMARY KEY (`id`),
  KEY `group_key` (`group_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Table structure for wu_doc
-- ----------------------------
DROP TABLE IF EXISTS `wu_doc`;
CREATE TABLE `wu_doc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doc_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档标题',
  `doc_price` decimal(18,2) DEFAULT NULL COMMENT '文档价格',
  `doc_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档链接',
  `doc_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='开发文档';

-- ----------------------------
-- Table structure for wu_game
-- ----------------------------
DROP TABLE IF EXISTS `wu_game`;
CREATE TABLE `wu_game` (
  `id` bigint(20) NOT NULL,
  `game_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏名称',
  `game_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '游戏作者',
  `game_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏链接',
  `game_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的H5游戏';

-- ----------------------------
-- Table structure for wu_novel
-- ----------------------------
DROP TABLE IF EXISTS `wu_novel`;
CREATE TABLE `wu_novel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `novel_title` varchar(255) DEFAULT NULL,
  `novel_author` varchar(255) DEFAULT NULL,
  `novel_type` varchar(32) DEFAULT NULL,
  `novel_content` longtext,
  `create_tm` datetime DEFAULT NULL,
  `update_tm` datetime DEFAULT NULL,
  `state` varchar(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='小说表';

-- ----------------------------
-- Table structure for wu_role
-- ----------------------------
DROP TABLE IF EXISTS `wu_role`;
CREATE TABLE `wu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_status` int(1) NOT NULL COMMENT '状态：1有效；2删除',
  `role_ct` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ut` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for wu_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `wu_role_permission`;
CREATE TABLE `wu_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限表',
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `permission_id` varchar(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for wu_tool
-- ----------------------------
DROP TABLE IF EXISTS `wu_tool`;
CREATE TABLE `wu_tool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tool_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具名称',
  `tool_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工具作者',
  `tool_from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原链接',
  `tool_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具链接',
  `tool_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='諾的工具箱';

-- ----------------------------
-- Table structure for wu_user
-- ----------------------------
DROP TABLE IF EXISTS `wu_user`;
CREATE TABLE `wu_user` (
  `id` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '登陆名',
  `realname` varchar(32) DEFAULT NULL,
  `password` char(32) NOT NULL COMMENT '密码MD5',
  `roleid` int(1) NOT NULL DEFAULT '0' COMMENT '权限默认0',
  `headimg` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `login_at` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  KEY `password` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for wu_vistor
-- ----------------------------
DROP TABLE IF EXISTS `wu_vistor`;
CREATE TABLE `wu_vistor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lk` varchar(255) DEFAULT NULL COMMENT 'link',
  `ip` int(4) DEFAULT NULL COMMENT 'ip',
  `ag` varchar(1023) DEFAULT NULL COMMENT 'agent',
  `tm` datetime DEFAULT NULL COMMENT 'datetime',
  PRIMARY KEY (`id`),
  KEY `lk` (`lk`) USING BTREE,
  KEY `ag` (`ag`) USING BTREE,
  KEY `tm` (`tm`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='访客统计';

-- ----------------------------
-- Table structure for bot_biliol
-- ----------------------------
DROP TABLE IF EXISTS `bot_biliol`;
CREATE TABLE `bot_biliol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `uid` varchar(255) DEFAULT NULL COMMENT '作者id',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `play_num` varchar(255) DEFAULT NULL COMMENT '播放量',
  `dm_num` varchar(255) DEFAULT NULL COMMENT '弹幕量',
  `ol_num` varchar(255) DEFAULT NULL COMMENT '当前在线量',
  `create_tm` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='爬虫_bili在线列表';

-- ----------------------------
-- Table structure for bot_bilirk
-- ----------------------------
DROP TABLE IF EXISTS `bot_bilirk`;
CREATE TABLE `bot_bilirk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `uid` varchar(255) DEFAULT NULL COMMENT '作者id',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `play_num` varchar(255) DEFAULT NULL COMMENT '播放量',
  `dm_num` varchar(255) DEFAULT NULL COMMENT '弹幕量',
  `score` varchar(255) DEFAULT NULL COMMENT '综合得分',
  `create_tm` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='爬虫_bili排行榜';

-- ----------------------------
-- Table structure for wu_job
-- ----------------------------
DROP TABLE IF EXISTS `wu_job`;
CREATE TABLE `wu_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_tm` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_tm` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Table structure for wu_job_log
-- ----------------------------
DROP TABLE IF EXISTS `wu_job_log`;
CREATE TABLE `wu_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `start_Tm` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_Tm` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';
