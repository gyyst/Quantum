-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';

drop table if exists user;
create table if not exists user
(
    id          varchar(64) comment '用户id' primary key,
    account     varchar(64)                        not null comment '账号',
    password    varchar(256)                       not null comment '密码',
    email       varchar(64)                        null comment '用户邮箱',
    phone       varchar(16)                        null comment '手机号',
    name        varchar(32)                        null comment '用户昵称',
    avatar      varchar(256)                       null comment '用户头像',
    profile     varchar(512)                       null comment '用户简介',
    state       tinyint                            null comment 'enum:UserState(NORMAL:0:正常, BANNED:1:禁用) 用户状态',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;

create table if not exists user_role
(
    user_id     varchar(64) comment '用户id' primary key,
    role_list   varchar(256)                       null comment 'type:List<String> 用户角色',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户角色关系' collate = utf8mb4_unicode_ci;

create table if not exists user_open_key
(
    user_id     varchar(64) comment '用户id' primary key,
    access_key  varchar(128)                       null comment 'access_key',
    secret_key  varchar(128)                       null comment 'secret_key，存储sha256加密后的字符串',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_ak_sk (access_key, secret_key),
    unique uk_ak (access_key)
) comment '用户openKey' collate = utf8mb4_unicode_ci;

create table if not exists user_coin
(
    user_id     varchar(64) comment '用户id' primary key,
    coin_num    decimal(10, 2) default 0                 not null comment '硬币数量',
    create_time datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint        default 0                 not null comment '是否删除'
) comment '用户硬币' collate = utf8mb4_unicode_ci;