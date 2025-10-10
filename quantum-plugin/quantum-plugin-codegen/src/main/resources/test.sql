-- 接口信息
drop table if exists test_info;
create table if not exists test_info
(
    `id`        bigint                             not null auto_increment comment '主键' primary key,
    `name`      varchar(256)                       not null comment 'type:List<String> 名称',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '接口信息';