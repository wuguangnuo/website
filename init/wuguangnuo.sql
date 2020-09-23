/*
 注意：建表语句必须包含以下字段（爬虫表除外）
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
`create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
`create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
`modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
`modified_time` datetime DEFAULT NULL COMMENT '修改时间',
`remark` varchar(255) DEFAULT NULL COMMENT '备注',
`logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
*/

-- ----------------------------
-- Table structure for bot_acfunrk
-- ----------------------------
DROP TABLE IF EXISTS `bot_acfunrk`;
CREATE TABLE `bot_acfunrk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `douga_id` varchar(255) DEFAULT NULL COMMENT '动画id(ac号)',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `content_desc` varchar(1023) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '视频简介',
  `user_id` varchar(255) DEFAULT NULL COMMENT '作者id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '作者',
  `channel` varchar(255) DEFAULT NULL COMMENT '分类频道',
  `contribute_time` datetime DEFAULT NULL COMMENT '投稿时间',
  `fans_count` varchar(255) DEFAULT NULL COMMENT '粉丝数',
  `contribution_count` varchar(255) DEFAULT NULL COMMENT '投稿量',
  `view_count` varchar(255) DEFAULT NULL COMMENT '播放量',
  `danmu_count` varchar(255) DEFAULT NULL COMMENT '弹幕量',
  `comment_count` varchar(255) DEFAULT NULL COMMENT '评论数',
  `stow_count` varchar(255) DEFAULT NULL COMMENT '收藏数',
  `banana_count` varchar(255) DEFAULT NULL COMMENT '香蕉数',
  `share_count` varchar(255) DEFAULT NULL COMMENT '分享数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='a站全站综合排行榜';

-- ----------------------------
-- Table structure for bot_biliol
-- ----------------------------
DROP TABLE IF EXISTS `bot_biliol`;
CREATE TABLE `bot_biliol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `author` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '作者',
  `uid` varchar(255) DEFAULT NULL COMMENT '作者id',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `play_num` varchar(255) DEFAULT NULL COMMENT '播放量',
  `dm_num` varchar(255) DEFAULT NULL COMMENT '弹幕量',
  `ol_num` varchar(255) DEFAULT NULL COMMENT '当前在线量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫哔哩在线列表';

-- ----------------------------
-- Table structure for bot_bilirk
-- ----------------------------
DROP TABLE IF EXISTS `bot_bilirk`;
CREATE TABLE `bot_bilirk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `author` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '作者',
  `uid` varchar(255) DEFAULT NULL COMMENT '作者id',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `play_num` varchar(255) DEFAULT NULL COMMENT '播放量',
  `dm_num` varchar(255) DEFAULT NULL COMMENT '弹幕量',
  `score` varchar(255) DEFAULT NULL COMMENT '综合得分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫哔哩排行榜';

-- ----------------------------
-- Table structure for bot_weibotop
-- ----------------------------
DROP TABLE IF EXISTS `bot_weibotop`;
CREATE TABLE `bot_weibotop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `score` varchar(255) DEFAULT NULL COMMENT '作者',
  `state` varchar(255) DEFAULT NULL COMMENT '作者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫微博排行';

-- ----------------------------
-- Table structure for bot_zhihuhot
-- ----------------------------
DROP TABLE IF EXISTS `bot_zhihuhot`;
CREATE TABLE `bot_zhihuhot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `score` varchar(255) DEFAULT NULL COMMENT '分数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫知乎热搜';

-- ----------------------------
-- Table structure for bot_baidutop
-- ----------------------------
DROP TABLE IF EXISTS `bot_baidutop`;
CREATE TABLE `bot_baidutop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ranking` varchar(255) DEFAULT NULL COMMENT '排名',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `score` varchar(255) DEFAULT NULL COMMENT '搜索指数',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  `trend` varchar(255) DEFAULT NULL COMMENT '趋势',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='百度风云榜实时热点排行榜';

-- ----------------------------
-- Table structure for bot_clav
-- ----------------------------
DROP TABLE IF EXISTS `bot_clav`;
CREATE TABLE `bot_clav` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `part` varchar(255) DEFAULT NULL COMMENT '分区',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `upload_time` varchar(255) DEFAULT NULL COMMENT '上传时间',
  `url` varchar(255) DEFAULT NULL COMMENT '详情页地址',
  `bt_path` varchar(255) DEFAULT NULL COMMENT '种子路径',
  `status` char(1) DEFAULT NULL COMMENT '执行状态（0正常 1失败）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `url` (`url`) USING BTREE,
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='草榴社區';

-- ----------------------------
-- Table structure for wu_blog
-- ----------------------------
DROP TABLE IF EXISTS `wu_blog`;
CREATE TABLE `wu_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章标题',
  `post_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文章作者',
  `post_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '文章分类',
  `post_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章内容',
  `post_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布日期',
  `post_from` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '文章来源',
  `post_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '原链接',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的博客';

-- ----------------------------
-- Table structure for wu_demo
-- ----------------------------
DROP TABLE IF EXISTS `wu_demo`;
CREATE TABLE `wu_demo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `demo_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo名称',
  `demo_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'demo作者',
  `demo_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo链接',
  `demo_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'demo图片',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
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
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的日记';

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
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `group_key` (`group_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

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
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开发文档';

-- ----------------------------
-- Table structure for wu_game
-- ----------------------------
DROP TABLE IF EXISTS `wu_game`;
CREATE TABLE `wu_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏名称',
  `game_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '游戏作者',
  `game_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏链接',
  `game_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏图片',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
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
  `status` varchar(4) NOT NULL DEFAULT '0',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小说表';

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
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '多租户ID',
  `create_by_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_by_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_by_id` bigint(20) DEFAULT NULL COMMENT '修改者ID',
  `modified_by_name` varchar(32) DEFAULT NULL COMMENT '修改者姓名',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `logic_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='諾的工具箱';
