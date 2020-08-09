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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

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
  `exception_info` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '停止时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Table structure for wu_menu
-- ----------------------------
DROP TABLE IF EXISTS `wu_menu`;
CREATE TABLE `wu_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单表',
  `code` varchar(32) DEFAULT NULL COMMENT '代码',
  `icon` varchar(32) DEFAULT NULL COMMENT '图表',
  `url` varchar(32) DEFAULT NULL COMMENT '路径',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for wu_role
-- ----------------------------
DROP TABLE IF EXISTS `wu_role`;
CREATE TABLE `wu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_status` int(11) NOT NULL COMMENT '状态：1有效；2删除',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for wu_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `wu_role_menu`;
CREATE TABLE `wu_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限表',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '权限id',
  `menu_code` varchar(32) NOT NULL COMMENT '权限code',
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
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for wu_user
-- ----------------------------
DROP TABLE IF EXISTS `wu_user`;
CREATE TABLE `wu_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '登陆名',
  `realname` varchar(32) DEFAULT NULL,
  `password` char(32) NOT NULL COMMENT '密码MD5',
  `headimg` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
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
  KEY `username` (`username`) USING BTREE,
  KEY `password` (`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for wu_user_role
-- ----------------------------
DROP TABLE IF EXISTS `wu_user_role`;
CREATE TABLE `wu_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色表',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
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
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for wu_visitor
-- ----------------------------
DROP TABLE IF EXISTS `wu_visitor`;
CREATE TABLE `wu_visitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lk` varchar(255) DEFAULT NULL COMMENT 'link',
  `ip` int(4) DEFAULT NULL COMMENT 'ip',
  `ag` varchar(1023) DEFAULT NULL COMMENT 'agent',
  `tm` datetime DEFAULT NULL COMMENT 'datetime',
  `us` varchar(32) DEFAULT NULL COMMENT 'user',
  PRIMARY KEY (`id`),
  KEY `lk` (`lk`) USING BTREE,
  KEY `ag` (`ag`(255)) USING BTREE,
  KEY `tm` (`tm`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客统计';

-- Quartz.sql
-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
drop table if exists QRTZ_JOB_DETAILS;
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null,
    job_name             varchar(200)    not null,
    job_group            varchar(200)    not null,
    description          varchar(250)    null,
    job_class_name       varchar(250)    not null,
    is_durable           varchar(1)      not null,
    is_nonconcurrent     varchar(1)      not null,
    is_update_data       varchar(1)      not null,
    requests_recovery    varchar(1)      not null,
    job_data             blob            null,
    primary key (sched_name,job_name,job_group)
) engine=innodb;

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
drop table if exists QRTZ_TRIGGERS;
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    job_name             varchar(200)    not null,
    job_group            varchar(200)    not null,
    description          varchar(250)    null,
    next_fire_time       bigint(13)      null,
    prev_fire_time       bigint(13)      null,
    priority             integer         null,
    trigger_state        varchar(16)     not null,
    trigger_type         varchar(8)      not null,
    start_time           bigint(13)      not null,
    end_time             bigint(13)      null,
    calendar_name        varchar(200)    null,
    misfire_instr        smallint(2)     null,
    job_data             blob            null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group) references QRTZ_JOB_DETAILS(sched_name,job_name,job_group)
) engine=innodb;

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
drop table if exists QRTZ_SIMPLE_TRIGGERS;
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    repeat_count         bigint(7)       not null,
    repeat_interval      bigint(12)      not null,
    times_triggered      bigint(10)      not null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb;

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ----------------------------
drop table if exists QRTZ_CRON_TRIGGERS;
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    cron_expression      varchar(200)    not null,
    time_zone_id         varchar(80),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb;

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ----------------------------
drop table if exists QRTZ_BLOB_TRIGGERS;
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    blob_data            blob            null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb;

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ----------------------------
drop table if exists QRTZ_CALENDARS;
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null,
    calendar_name        varchar(200)    not null,
    calendar             blob            not null,
    primary key (sched_name,calendar_name)
) engine=innodb;

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ----------------------------
drop table if exists QRTZ_PAUSED_TRIGGER_GRPS;
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null,
    trigger_group        varchar(200)    not null,
    primary key (sched_name,trigger_group)
) engine=innodb;

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ----------------------------
drop table if exists QRTZ_FIRED_TRIGGERS;
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null,
    entry_id             varchar(95)     not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    instance_name        varchar(200)    not null,
    fired_time           bigint(13)      not null,
    sched_time           bigint(13)      not null,
    priority             integer         not null,
    state                varchar(16)     not null,
    job_name             varchar(200)    null,
    job_group            varchar(200)    null,
    is_nonconcurrent     varchar(1)      null,
    requests_recovery    varchar(1)      null,
    primary key (sched_name,entry_id)
) engine=innodb;

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ----------------------------
drop table if exists QRTZ_SCHEDULER_STATE;
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null,
    instance_name        varchar(200)    not null,
    last_checkin_time    bigint(13)      not null,
    checkin_interval     bigint(13)      not null,
    primary key (sched_name,instance_name)
) engine=innodb;

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ----------------------------
drop table if exists QRTZ_LOCKS;
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null,
    lock_name            varchar(40)     not null,
    primary key (sched_name,lock_name)
) engine=innodb;

drop table if exists QRTZ_SIMPROP_TRIGGERS;
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    str_prop_1           varchar(512)    null,
    str_prop_2           varchar(512)    null,
    str_prop_3           varchar(512)    null,
    int_prop_1           int             null,
    int_prop_2           int             null,
    long_prop_1          bigint          null,
    long_prop_2          bigint          null,
    dec_prop_1           numeric(13,4)   null,
    dec_prop_2           numeric(13,4)   null,
    bool_prop_1          varchar(1)      null,
    bool_prop_2          varchar(1)      null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb;

commit;