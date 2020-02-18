-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2020-02-15 13:01:42
-- 服务器版本： 10.1.37-MariaDB
-- PHP 版本： 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `wuguangnuo`
--

-- --------------------------------------------------------

--
-- 表的结构 `wu_blog`
--

CREATE TABLE `wu_blog` (
  `id` bigint(20) NOT NULL,
  `post_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章标题',
  `post_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文章作者',
  `post_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '文章分类',
  `post_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章内容',
  `post_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期',
  `post_from` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '文章来源',
  `post_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '原链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的博客';

-- --------------------------------------------------------

--
-- 表的结构 `wu_demo`
--

CREATE TABLE `wu_demo` (
  `id` bigint(20) NOT NULL,
  `demo_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo名称',
  `demo_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'demo作者',
  `demo_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo链接',
  `demo_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo图片'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的DEMO';

-- --------------------------------------------------------

--
-- 表的结构 `wu_diary`
--

CREATE TABLE `wu_diary` (
  `id` bigint(20) NOT NULL,
  `diary_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日记标题',
  `diary_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '日记关键词',
  `diary_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日记内容',
  `diary_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的日记';

-- --------------------------------------------------------

--
-- 表的结构 `wu_dictionary`
--

CREATE TABLE `wu_dictionary` (
  `id` bigint(20) NOT NULL,
  `group_key` varchar(32) NOT NULL COMMENT '字典类型',
  `code_index` varchar(64) NOT NULL COMMENT '字典索引',
  `code_value` varchar(255) NOT NULL COMMENT '字典值',
  `code_note` varchar(64) NOT NULL COMMENT '字典注释'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- --------------------------------------------------------

--
-- 表的结构 `wu_doc`
--

CREATE TABLE `wu_doc` (
  `id` bigint(20) NOT NULL,
  `doc_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档标题',
  `doc_price` decimal(18,2) DEFAULT NULL COMMENT '文档价格',
  `doc_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档链接',
  `doc_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档图片'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开发文档';

-- --------------------------------------------------------

--
-- 表的结构 `wu_game`
--

CREATE TABLE `wu_game` (
  `id` bigint(20) NOT NULL,
  `game_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏名称',
  `game_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '游戏作者',
  `game_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏链接',
  `game_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏图片'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的H5游戏';

-- --------------------------------------------------------

--
-- 表的结构 `wu_tool`
--

CREATE TABLE `wu_tool` (
  `id` bigint(20) NOT NULL,
  `tool_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具名称',
  `tool_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工具作者',
  `tool_from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原链接',
  `tool_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具链接',
  `tool_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工具图片'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的工具箱';

-- --------------------------------------------------------

--
-- 表的结构 `wu_user`
--

CREATE TABLE `wu_user` (
  `id` int(12) UNSIGNED NOT NULL,
  `username` varchar(32) NOT NULL COMMENT '登陆名',
  `password` char(32) NOT NULL COMMENT '密码MD5',
  `roleid` int(1) NOT NULL DEFAULT '0' COMMENT '权限默认0',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `login_at` datetime DEFAULT NULL COMMENT '最后一次登陆时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- --------------------------------------------------------

--
-- 表的结构 `wu_vistor`
--

CREATE TABLE `wu_vistor` (
  `id` bigint(20) NOT NULL,
  `lk` varchar(255) DEFAULT NULL COMMENT 'link',
  `ip` int(4) DEFAULT NULL COMMENT 'ip',
  `ag` varchar(255) DEFAULT NULL COMMENT 'agent',
  `tm` datetime DEFAULT NULL COMMENT 'datetime'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客统计';

--
-- 转储表的索引
--

--
-- 表的索引 `wu_blog`
--
ALTER TABLE `wu_blog`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_demo`
--
ALTER TABLE `wu_demo`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_diary`
--
ALTER TABLE `wu_diary`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_dictionary`
--
ALTER TABLE `wu_dictionary`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_key` (`group_key`);

--
-- 表的索引 `wu_doc`
--
ALTER TABLE `wu_doc`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_game`
--
ALTER TABLE `wu_game`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_tool`
--
ALTER TABLE `wu_tool`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `wu_user`
--
ALTER TABLE `wu_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`),
  ADD KEY `password` (`password`);

--
-- 表的索引 `wu_vistor`
--
ALTER TABLE `wu_vistor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lk` (`lk`),
  ADD KEY `ag` (`ag`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `wu_blog`
--
ALTER TABLE `wu_blog`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_demo`
--
ALTER TABLE `wu_demo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_diary`
--
ALTER TABLE `wu_diary`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_dictionary`
--
ALTER TABLE `wu_dictionary`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_doc`
--
ALTER TABLE `wu_doc`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_game`
--
ALTER TABLE `wu_game`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_tool`
--
ALTER TABLE `wu_tool`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_user`
--
ALTER TABLE `wu_user`
  MODIFY `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `wu_vistor`
--
ALTER TABLE `wu_vistor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


-- Add by 2020年2月18日 wgn

-- ----------------------------
-- Table structure for wu_role
-- ----------------------------
DROP TABLE IF EXISTS `wu_role`;
CREATE TABLE `wu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_status` int(1) NOT NULL COMMENT '状态：1有效；2删除',
  `role_ct` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ut` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for wu_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `wu_role_permission`;
CREATE TABLE `wu_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限表',
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `permission_id` varchar(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=870 DEFAULT CHARSET=utf8 COMMENT='角色权限表';
