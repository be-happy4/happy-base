
drop type if exists data_status cascade;
create type data_status as enum ('OK', 'DISABLE', 'DELETED');

-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint          generated always as identity,
  parent_id         bigint          default 0,
  ancestors         varchar(50)     default '',
  dept_name         varchar(30)     default '',
  order_num         int             default 0,
  leader            varchar(20)     default null,
  phone             varchar(11)     default null,
  email             varchar(50)     default null,
  status            data_status     default 'OK' not null,
  del_flag          bool            generated always as (status = 'DELETED') stored not null,
  create_by         varchar(64)     default '',
  create_time 	    timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  primary key (dept_id)
);
comment on table sys_dept is '部门表';
comment on column sys_dept.dept_id is '部门id';
comment on column sys_dept.parent_id is '父部门id';
comment on column sys_dept.ancestors is '祖级列表';
comment on column sys_dept.dept_name is '部门名称';
comment on column sys_dept.order_num is '显示顺序';
comment on column sys_dept.leader is '负责人';
comment on column sys_dept.phone is '联系电话';
comment on column sys_dept.email is '邮箱';
comment on column sys_dept.status is '部门状态';
comment on column sys_dept.del_flag is '删除标志';
comment on column sys_dept.create_by is '创建者';
comment on column sys_dept.create_time is '创建时间';
comment on column sys_dept.update_by is '更新者';
comment on column sys_dept.update_time is '更新时间';
select setval(pg_get_serial_sequence('sys_dept', 'dept_id'), 200) from sys_dept;

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept overriding system value values(100,  0,   '0',          '开心科技',   0, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(101,  100, '0,100',      '深圳总公司', 1, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(102,  100, '0,100',      '长沙分公司', 2, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(103,  101, '0,100,101',  '研发部门',   1, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(104,  101, '0,100,101',  '市场部门',   2, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(105,  101, '0,100,101',  '测试部门',   3, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(106,  101, '0,100,101',  '财务部门',   4, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(107,  101, '0,100,101',  '运维部门',   5, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(108,  102, '0,100,102',  '市场部门',   1, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);
insert into sys_dept overriding system value values(109,  102, '0,100,102',  '财务部门',   2, '开心', '15888888888', 'happy@qq.com', 'OK', default, 'admin', now(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop type if exists user_sex cascade;
create type user_sex as enum ('MALE', 'FEMALE', 'UNKNOWN');

drop table if exists sys_user;
create table sys_user (
  user_id           int8            not null generated always as identity,
  dept_id           int8            default null,
  user_name         varchar(30)     not null,
  nick_name         varchar(30)     not null,
  user_type         varchar(2)      default '00',
  email             varchar(50)     default '',
  phonenumber       varchar(11)     default '',
  sex               user_sex        default 'UNKNOWN',
  avatar            varchar(100)    default '',
  password          varchar(100)    default '',
  status            data_status     default 'OK',
  del_flag          bool            generated always as (status = 'DELETED') stored not null,
  login_ip          varchar(128)    default '',
  login_date        timestamp(0),
  pwd_update_date   timestamp(0),
  create_by         varchar(64)     default '',
  create_time       timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  remark            varchar(500)    default null,
  primary key (user_id)
);
comment on table sys_user is '用户信息表';
comment on column sys_user.user_id is '用户ID';
comment on column sys_user.dept_id is '部门ID';
comment on column sys_user.user_name is '用户账号';
comment on column sys_user.nick_name is '用户昵称';
comment on column sys_user.user_type is '用户类型（00系统用户）';
comment on column sys_user.email is '用户邮箱';
comment on column sys_user.phonenumber is '手机号码';
comment on column sys_user.sex is '用户性别';
comment on column sys_user.avatar is '头像地址';
comment on column sys_user.password is '密码';
comment on column sys_user.status is '账号状态';
comment on column sys_user.del_flag is '删除标志';
comment on column sys_user.login_ip is '最后登录IP';
comment on column sys_user.login_date is '最后登录时间';
comment on column sys_user.pwd_update_date is '密码最后更新时间';
comment on column sys_user.create_by is '创建者';
comment on column sys_user.create_time is '创建时间';
comment on column sys_user.update_by is '更新者';
comment on column sys_user.update_time is '更新时间';
comment on column sys_user.remark is '备注';
select setval(pg_get_serial_sequence('sys_user', 'user_id'), 106) from sys_user;

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user overriding system value values(1,  103, 'admin', '开心', '00', 'happy@163.com', '15888888888', 'MALE', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'OK', default, '127.0.0.1', now(), now(), 'admin', now(), '', null, '管理员');
insert into sys_user overriding system value values(2,  105, 'happy',    '开心', '00', 'happy@qq.com',  '15666666666', 'MALE', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'OK', default, '127.0.0.1', now(), now(), 'admin', now(), '', null, '测试员');


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       int8            generated always as identity,
  post_code     varchar(64)     not null,
  post_name     varchar(50)     not null,
  post_sort     int             not null,
  status        data_status     default 'OK' not null,
  create_by     varchar(64)     default '',
  create_time   timestamp(0),
  update_by     varchar(64)     default '',
  update_time   timestamp(0),
  remark        varchar(500)    default null,
  primary key (post_id)
);
comment on table sys_post is '岗位信息表';
comment on column sys_post.post_id is '岗位ID';
comment on column sys_post.post_code is '岗位编码';
comment on column sys_post.post_name is '岗位名称';
comment on column sys_post.post_sort is '显示顺序';
comment on column sys_post.status is '状态';
comment on column sys_post.create_by is '创建者';
comment on column sys_post.create_time is '创建时间';
comment on column sys_post.update_by is '更新者';
comment on column sys_post.update_time is '更新时间';
comment on column sys_post.remark is '备注';
select setval(pg_get_serial_sequence('sys_post', 'post_id'), 5) from sys_post;

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post overriding system value values(1, 'ceo',  '董事长',    1, 'OK', 'admin', now(), '', null, '');
insert into sys_post overriding system value values(2, 'se',   '项目经理',  2, 'OK', 'admin', now(), '', null, '');
insert into sys_post overriding system value values(3, 'hr',   '人力资源',  3, 'OK', 'admin', now(), '', null, '');
insert into sys_post overriding system value values(4, 'user', '普通员工',  4, 'OK', 'admin', now(), '', null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop type if exists data_scope_type cascade;
create type data_scope_type as enum ('ALL', 'CUSTOM', 'DEPT', 'DEPT_AND_CHILD', 'SELF');
drop table if exists sys_role;
create table sys_role (
  role_id              int8            generated always as identity,
  role_name            varchar(30)     not null,
  role_key             varchar(100)    not null,
  role_sort            int             not null,
  data_scope           data_scope_type default 'ALL' not null,
  menu_check_strictly  bool            default true,
  dept_check_strictly  bool            default true,
  status               data_status     default 'OK' not null,
  del_flag             bool            generated always as (status = 'DELETED') stored not null,
  create_by            varchar(64)     default '',
  create_time          timestamp(0),
  update_by            varchar(64)     default '',
  update_time          timestamp(0),
  remark               varchar(500)    default null,
  primary key (role_id)
);
comment on table sys_role is '角色信息表';
comment on column sys_role.role_id is '角色ID';
comment on column sys_role.role_name is '角色名称';
comment on column sys_role.role_key is '角色权限字符串';
comment on column sys_role.role_sort is '显示顺序';
comment on column sys_role.data_scope is '数据范围';
comment on column sys_role.menu_check_strictly is '菜单树选择项是否关联显示';
comment on column sys_role.dept_check_strictly is '部门树选择项是否关联显示';
comment on column sys_role.status is '角色状态';
comment on column sys_role.del_flag is '删除标志';
comment on column sys_role.create_by is '创建者';
comment on column sys_role.create_time is '创建时间';
comment on column sys_role.update_by is '更新者';
comment on column sys_role.update_time is '更新时间';
comment on column sys_role.remark is '备注';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values(default, '超级管理员',  'admin',  1, 'ALL', true, true, 'OK', default, 'admin', now(), '', null, '超级管理员');
insert into sys_role values(default, '普通角色',    'common', 2, 'CUSTOM', true, true, 'OK', default, 'admin', now(), '', null, '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop type if exists menu_type cascade;
create type menu_type as enum ('M', 'C', 'F');
drop table if exists sys_menu;
create table sys_menu (
  menu_id           int8            generated always as identity,
  menu_name         varchar(50)     not null,
  parent_id         int8            default 0,
  order_num         int             default 0,
  path              varchar(200)    default '',
  component         varchar(255)    default null,
  query             varchar(255)    default null,
  route_name        varchar(50)     default '',
  is_frame          bool            default false,
  is_cache          bool            default false,
  menu_type         menu_type       default 'M' not null,
  hidden            bool            default false,
  status            data_status     default 'OK' not null,
  perms             varchar(100)    default null,
  icon              varchar(100)    default '#',
  create_by         varchar(64)     default '',
  create_time       timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  remark            varchar(500)    default '',
  primary key (menu_id)
);
comment on table sys_menu is '菜单权限表';
comment on column sys_menu.menu_id is '菜单ID';
comment on column sys_menu.menu_name is '菜单名称';
comment on column sys_menu.parent_id is '父菜单ID';
comment on column sys_menu.order_num is '显示顺序';
comment on column sys_menu.path is '路由地址';
comment on column sys_menu.component is '组件路径';
comment on column sys_menu.query is '路由参数';
comment on column sys_menu.route_name is '路由名称';
comment on column sys_menu.is_frame is '是否为外链';
comment on column sys_menu.is_cache is '是否缓存';
comment on column sys_menu.menu_type is '菜单类型（M目录 C菜单 F按钮）';
comment on column sys_menu.hidden is '菜单状态';
comment on column sys_menu.status is '菜单状态';
comment on column sys_menu.perms is '权限标识';
comment on column sys_menu.icon is '菜单图标';
comment on column sys_menu.create_by is '创建者';
comment on column sys_menu.create_time is '创建时间';
comment on column sys_menu.update_by is '更新者';
comment on column sys_menu.update_time is '更新时间';
comment on column sys_menu.remark is '备注';
select setval(pg_get_serial_sequence('sys_menu', 'menu_id'), 2000) from sys_menu;

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu overriding system value values('1', '系统管理', '0', '1', 'system',           null, '', '', false, false, 'M', false, 'OK', '', 'system',   'admin', now(), '', null, '系统管理目录');
insert into sys_menu overriding system value values('2', '系统监控', '0', '2', 'monitor',          null, '', '', false, false, 'M', false, 'OK', '', 'monitor',  'admin', now(), '', null, '系统监控目录');
insert into sys_menu overriding system value values('3', '系统工具', '0', '3', 'tool',             null, '', '', false, false, 'M', false, 'OK', '', 'tool',     'admin', now(), '', null, '系统工具目录');
insert into sys_menu overriding system value values('4', '开心官网', '0', '4', 'http://happy.vip', null, '', '', true, false, 'M', false, 'OK', '', 'guide',    'admin', now(), '', null, '开心官网地址');
-- 二级菜单
insert into sys_menu overriding system value values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', false, false, 'C', false, 'OK', 'system:user:list',        'user',          'admin', now(), '', null, '用户管理菜单');
insert into sys_menu overriding system value values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', false, false, 'C', false, 'OK', 'system:role:list',        'peoples',       'admin', now(), '', null, '角色管理菜单');
insert into sys_menu overriding system value values('102',  '菜单管理', '1',   '3', 'menu',       'system/menu/index',        '', '', false, false, 'C', false, 'OK', 'system:menu:list',        'tree-table',    'admin', now(), '', null, '菜单管理菜单');
insert into sys_menu overriding system value values('103',  '部门管理', '1',   '4', 'dept',       'system/dept/index',        '', '', false, false, 'C', false, 'OK', 'system:dept:list',        'tree',          'admin', now(), '', null, '部门管理菜单');
insert into sys_menu overriding system value values('104',  '岗位管理', '1',   '5', 'post',       'system/post/index',        '', '', false, false, 'C', false, 'OK', 'system:post:list',        'post',          'admin', now(), '', null, '岗位管理菜单');
insert into sys_menu overriding system value values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        '', '', false, false, 'C', false, 'OK', 'system:dict:list',        'dict',          'admin', now(), '', null, '字典管理菜单');
insert into sys_menu overriding system value values('106',  '参数设置', '1',   '7', 'config',     'system/config/index',      '', '', false, false, 'C', false, 'OK', 'system:config:list',      'edit',          'admin', now(), '', null, '参数设置菜单');
insert into sys_menu overriding system value values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      '', '', false, false, 'C', false, 'OK', 'system:notice:list',      'message',       'admin', now(), '', null, '通知公告菜单');
insert into sys_menu overriding system value values('108',  '日志管理', '1',   '9', 'log',        '',                         '', '', false, false, 'M', false, 'OK', '',                        'log',           'admin', now(), '', null, '日志管理菜单');
insert into sys_menu overriding system value values('109',  '在线用户', '2',   '1', 'online',     'monitor/online/index',     '', '', false, false, 'C', false, 'OK', 'monitor:online:list',     'online',        'admin', now(), '', null, '在线用户菜单');
insert into sys_menu overriding system value values('110',  '定时任务', '2',   '2', 'job',        'monitor/job/index',        '', '', false, false, 'C', false, 'OK', 'monitor:job:list',        'job',           'admin', now(), '', null, '定时任务菜单');
insert into sys_menu overriding system value values('111',  '数据监控', '2',   '3', 'druid',      'monitor/druid/index',      '', '', false, false, 'C', false, 'OK', 'monitor:druid:list',      'druid',         'admin', now(), '', null, '数据监控菜单');
insert into sys_menu overriding system value values('112',  '服务监控', '2',   '4', 'server',     'monitor/server/index',     '', '', false, false, 'C', false, 'OK', 'monitor:server:list',     'server',        'admin', now(), '', null, '服务监控菜单');
insert into sys_menu overriding system value values('113',  '缓存监控', '2',   '5', 'cache',      'monitor/cache/index',      '', '', false, false, 'C', false, 'OK', 'monitor:cache:list',      'redis',         'admin', now(), '', null, '缓存监控菜单');
insert into sys_menu overriding system value values('114',  '缓存列表', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', false, false, 'C', false, 'OK', 'monitor:cache:list',      'redis-list',    'admin', now(), '', null, '缓存列表菜单');
insert into sys_menu overriding system value values('115',  '表单构建', '3',   '1', 'build',      'tool/build/index',         '', '', false, false, 'C', false, 'OK', 'tool:build:list',         'build',         'admin', now(), '', null, '表单构建菜单');
insert into sys_menu overriding system value values('116',  '代码生成', '3',   '2', 'gen',        'tool/gen/index',           '', '', false, false, 'C', false, 'OK', 'tool:gen:list',           'code',          'admin', now(), '', null, '代码生成菜单');
insert into sys_menu overriding system value values('117',  '系统接口', '3',   '3', 'swagger',    'tool/swagger/index',       '', '', false, false, 'C', false, 'OK', 'tool:swagger:list',       'swagger',       'admin', now(), '', null, '系统接口菜单');
-- 三级菜单
insert into sys_menu overriding system value values('500',  '操作日志', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', false, false, 'C', false, 'OK', 'monitor:operlog:list',    'form',          'admin', now(), '', null, '操作日志菜单');
insert into sys_menu overriding system value values('501',  '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', false, false, 'C', false, 'OK', 'monitor:logininfor:list', 'logininfor',    'admin', now(), '', null, '登录日志菜单');
-- 用户管理按钮
insert into sys_menu overriding system value values('1000', '用户查询', '100', '1',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1001', '用户新增', '100', '2',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1002', '用户修改', '100', '3',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1003', '用户删除', '100', '4',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:remove',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1004', '用户导出', '100', '5',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:export',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1005', '用户导入', '100', '6',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:import',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1006', '重置密码', '100', '7',  '', '', '', '', false, false, 'F', false, 'OK', 'system:user:resetPwd',       '#', 'admin', now(), '', null, '');
-- 角色管理按钮
insert into sys_menu overriding system value values('1007', '角色查询', '101', '1',  '', '', '', '', false, false, 'F', false, 'OK', 'system:role:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1008', '角色新增', '101', '2',  '', '', '', '', false, false, 'F', false, 'OK', 'system:role:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1009', '角色修改', '101', '3',  '', '', '', '', false, false, 'F', false, 'OK', 'system:role:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1010', '角色删除', '101', '4',  '', '', '', '', false, false, 'F', false, 'OK', 'system:role:remove',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1011', '角色导出', '101', '5',  '', '', '', '', false, false, 'F', false, 'OK', 'system:role:export',         '#', 'admin', now(), '', null, '');
-- 菜单管理按钮
insert into sys_menu overriding system value values('1012', '菜单查询', '102', '1',  '', '', '', '', false, false, 'F', false, 'OK', 'system:menu:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1013', '菜单新增', '102', '2',  '', '', '', '', false, false, 'F', false, 'OK', 'system:menu:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1014', '菜单修改', '102', '3',  '', '', '', '', false, false, 'F', false, 'OK', 'system:menu:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1015', '菜单删除', '102', '4',  '', '', '', '', false, false, 'F', false, 'OK', 'system:menu:remove',         '#', 'admin', now(), '', null, '');
-- 部门管理按钮
insert into sys_menu overriding system value values('1016', '部门查询', '103', '1',  '', '', '', '', false, false, 'F', false, 'OK', 'system:dept:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1017', '部门新增', '103', '2',  '', '', '', '', false, false, 'F', false, 'OK', 'system:dept:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1018', '部门修改', '103', '3',  '', '', '', '', false, false, 'F', false, 'OK', 'system:dept:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1019', '部门删除', '103', '4',  '', '', '', '', false, false, 'F', false, 'OK', 'system:dept:remove',         '#', 'admin', now(), '', null, '');
-- 岗位管理按钮
insert into sys_menu overriding system value values('1020', '岗位查询', '104', '1',  '', '', '', '', false, false, 'F', false, 'OK', 'system:post:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1021', '岗位新增', '104', '2',  '', '', '', '', false, false, 'F', false, 'OK', 'system:post:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1022', '岗位修改', '104', '3',  '', '', '', '', false, false, 'F', false, 'OK', 'system:post:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1023', '岗位删除', '104', '4',  '', '', '', '', false, false, 'F', false, 'OK', 'system:post:remove',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1024', '岗位导出', '104', '5',  '', '', '', '', false, false, 'F', false, 'OK', 'system:post:export',         '#', 'admin', now(), '', null, '');
-- 字典管理按钮
insert into sys_menu overriding system value values('1025', '字典查询', '105', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'system:dict:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1026', '字典新增', '105', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'system:dict:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1027', '字典修改', '105', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'system:dict:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1028', '字典删除', '105', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'system:dict:remove',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1029', '字典导出', '105', '5', '#', '', '', '', false, false, 'F', false, 'OK', 'system:dict:export',         '#', 'admin', now(), '', null, '');
-- 参数设置按钮
insert into sys_menu overriding system value values('1030', '参数查询', '106', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'system:config:query',        '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1031', '参数新增', '106', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'system:config:add',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1032', '参数修改', '106', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'system:config:edit',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1033', '参数删除', '106', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'system:config:remove',       '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1034', '参数导出', '106', '5', '#', '', '', '', false, false, 'F', false, 'OK', 'system:config:export',       '#', 'admin', now(), '', null, '');
-- 通知公告按钮
insert into sys_menu overriding system value values('1035', '公告查询', '107', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'system:notice:query',        '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1036', '公告新增', '107', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'system:notice:add',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1037', '公告修改', '107', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'system:notice:edit',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1038', '公告删除', '107', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'system:notice:remove',       '#', 'admin', now(), '', null, '');
-- 操作日志按钮
insert into sys_menu overriding system value values('1039', '操作查询', '500', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:operlog:query',      '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1040', '操作删除', '500', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:operlog:remove',     '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1041', '日志导出', '500', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:operlog:export',     '#', 'admin', now(), '', null, '');
-- 登录日志按钮
insert into sys_menu overriding system value values('1042', '登录查询', '501', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:logininfor:query',   '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1043', '登录删除', '501', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:logininfor:remove',  '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1044', '日志导出', '501', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:logininfor:export',  '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1045', '账户解锁', '501', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:logininfor:unlock',  '#', 'admin', now(), '', null, '');
-- 在线用户按钮
insert into sys_menu overriding system value values('1046', '在线查询', '109', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:online:query',       '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1047', '批量强退', '109', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:online:batchLogout', '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1048', '单条强退', '109', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:online:forceLogout', '#', 'admin', now(), '', null, '');
-- 定时任务按钮
insert into sys_menu overriding system value values('1049', '任务查询', '110', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:query',          '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1050', '任务新增', '110', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:add',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1051', '任务修改', '110', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:edit',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1052', '任务删除', '110', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:remove',         '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1053', '状态修改', '110', '5', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:changeStatus',   '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1054', '任务导出', '110', '6', '#', '', '', '', false, false, 'F', false, 'OK', 'monitor:job:export',         '#', 'admin', now(), '', null, '');
-- 代码生成按钮
insert into sys_menu overriding system value values('1055', '生成查询', '116', '1', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:query',             '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1056', '生成修改', '116', '2', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:edit',              '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1057', '生成删除', '116', '3', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:remove',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1058', '导入代码', '116', '4', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:import',            '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1059', '预览代码', '116', '5', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:preview',           '#', 'admin', now(), '', null, '');
insert into sys_menu overriding system value values('1060', '生成代码', '116', '6', '#', '', '', '', false, false, 'F', false, 'OK', 'tool:gen:code',              '#', 'admin', now(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint not null,
  role_id   bigint not null,
  primary key(user_id, role_id)
);
comment on table sys_user_role is '用户和角色关联表';
comment on column sys_user_role.user_id is '用户ID';
comment on column sys_user_role.role_id is '角色ID';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint not null,
  menu_id   bigint not null,
  primary key(role_id, menu_id)
);
comment on table sys_role_menu is '角色和菜单关联表';
comment on column sys_role_menu.role_id is '角色ID';
comment on column sys_role_menu.menu_id is '菜单ID';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '117');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint not null,
  dept_id   bigint not null,
  primary key(role_id, dept_id)
);
comment on table sys_role_dept is '角色和部门关联表';
comment on column sys_role_dept.role_id is '角色ID';
comment on column sys_role_dept.dept_id is '部门ID';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint not null,
  post_id   bigint not null,
  primary key (user_id, post_id)
);
comment on table sys_user_post is '用户与岗位关联表';
comment on column sys_user_post.user_id is '用户ID';
comment on column sys_user_post.post_id is '岗位ID';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop type if exists business_status cascade;
create type business_status as enum ('NORMAL', 'ABNORMAL');
drop type if exists business_type cascade;
create type business_type as enum ('OTHER', 'INSERT', 'UPDATE', 'DELETE', 'GRANT', 'EXPORT', 'IMPORT', 'FORCE', 'GENCODE', 'CLEAN');
drop type if exists operator_type cascade;
create type operator_type as enum ('OTHER', 'MANAGE', 'MOBILE');

drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint      generated always as identity,
  title             varchar(50)     default '',
  business_type     business_type   default 'OTHER',
  method            varchar(200)    default '',
  request_method    varchar(10)     default '',
  operator_type     operator_type   default 'OTHER',
  oper_name         varchar(50)     default '',
  dept_name         varchar(50)     default '',
  oper_url          varchar(255)    default '',
  oper_ip           varchar(128)    default '',
  oper_location     varchar(255)    default '',
  oper_param        varchar(2000)   default '',
  json_result       varchar(2000)   default '',
  status            business_status default 'NORMAL',
  error_msg         varchar(2000)   default '',
  oper_time         timestamp(0),
  cost_time         bigint      default 0,
  primary key (oper_id)
);
create index idx_sys_oper_log_bt on sys_oper_log (business_type);
create index idx_sys_oper_log_s on sys_oper_log  (status);
create index idx_sys_oper_log_ot on sys_oper_log (oper_time);
comment on table sys_oper_log is '操作日志记录';
comment on column sys_oper_log.oper_id is '日志主键';
comment on column sys_oper_log.title is '模块标题';
comment on column sys_oper_log.business_type is '业务类型';
comment on column sys_oper_log.method is '方法名称';
comment on column sys_oper_log.request_method is '请求方式';
comment on column sys_oper_log.operator_type is '操作类别';
comment on column sys_oper_log.oper_name is '操作人员';
comment on column sys_oper_log.dept_name is '部门名称';
comment on column sys_oper_log.oper_url is '请求URL';
comment on column sys_oper_log.oper_ip is '主机地址';
comment on column sys_oper_log.oper_location is '操作地点';
comment on column sys_oper_log.oper_param is '请求参数';
comment on column sys_oper_log.json_result is '返回参数';
comment on column sys_oper_log.status is '操作状态';
comment on column sys_oper_log.error_msg is '错误消息';
comment on column sys_oper_log.oper_time is '操作时间';
comment on column sys_oper_log.cost_time is '消耗时间';
select setval(pg_get_serial_sequence('sys_oper_log', 'oper_id'), 100) from sys_oper_log;

-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint      generated always as identity,
  dict_name        varchar(100)    default '',
  dict_type        varchar(100)    default '',
  status           data_status     default 'OK',
  create_by        varchar(64)     default '',
  create_time      timestamp(0),
  update_by        varchar(64)     default '',
  update_time      timestamp(0),
  remark           varchar(500)    default null,
  primary key (dict_id),
  unique (dict_type)
);
comment on table sys_dict_type is '字典类型表';
comment on column sys_dict_type.dict_id is '字典主键';
comment on column sys_dict_type.dict_name is '字典名称';
comment on column sys_dict_type.dict_type is '字典类型';
comment on column sys_dict_type.status is '状态';
comment on column sys_dict_type.create_by is '创建者';
comment on column sys_dict_type.create_time is '创建时间';
comment on column sys_dict_type.update_by is '更新者';
comment on column sys_dict_type.update_time is '更新时间';
comment on column sys_dict_type.remark is '备注';

insert into sys_dict_type overriding system value values(1,  '用户性别', 'sys_user_sex',        'OK', 'admin', now(), '', null, '用户性别列表');
insert into sys_dict_type overriding system value values(2,  '菜单状态', 'sys_show_hide',       'OK', 'admin', now(), '', null, '菜单状态列表');
insert into sys_dict_type overriding system value values(3,  '系统开关', 'sys_normal_disable',  'OK', 'admin', now(), '', null, '系统开关列表');
insert into sys_dict_type overriding system value values(4,  '任务状态', 'sys_job_status',      'OK', 'admin', now(), '', null, '任务状态列表');
insert into sys_dict_type overriding system value values(5,  '任务分组', 'sys_job_group',       'OK', 'admin', now(), '', null, '任务分组列表');
insert into sys_dict_type overriding system value values(6,  '系统是否', 'sys_yes_no',          'OK', 'admin', now(), '', null, '系统是否列表');
insert into sys_dict_type overriding system value values(7,  '通知类型', 'sys_notice_type',     'OK', 'admin', now(), '', null, '通知类型列表');
insert into sys_dict_type overriding system value values(8,  '通知状态', 'sys_notice_status',   'OK', 'admin', now(), '', null, '通知状态列表');
insert into sys_dict_type overriding system value values(9,  '操作类型', 'sys_oper_type',       'OK', 'admin', now(), '', null, '操作类型列表');
insert into sys_dict_type overriding system value values(10, '系统状态', 'sys_common_status',   'OK', 'admin', now(), '', null, '登录状态列表');

select setval(pg_get_serial_sequence('sys_dict_type', 'dict_id'), 100) from sys_dict_type;

-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint      generated always as identity,
  dict_sort        int             default 0,
  dict_label       varchar(100)    default '',
  dict_value       varchar(100)    default '',
  dict_type        varchar(100)    default '',
  css_class        varchar(100)    default null,
  list_class       varchar(100)    default null,
  is_default       bool            default false,
  status           data_status     default 'OK',
  create_by        varchar(64)     default '',
  create_time      timestamp(0),
  update_by        varchar(64)     default '',
  update_time      timestamp(0),
  remark           varchar(500)    default null,
  primary key (dict_code)
);
comment on table sys_dict_data is '字典数据表';
comment on column sys_dict_data.dict_code is '字典编码';
comment on column sys_dict_data.dict_sort is '字典排序';
comment on column sys_dict_data.dict_label is '字典标签';
comment on column sys_dict_data.dict_value is '字典键值';
comment on column sys_dict_data.dict_type is '字典类型';
comment on column sys_dict_data.css_class is '样式属性（其他样式扩展）';
comment on column sys_dict_data.list_class is '表格回显样式';
comment on column sys_dict_data.is_default is '是否默认';
comment on column sys_dict_data.status is '状态';
comment on column sys_dict_data.create_by is '创建者';
comment on column sys_dict_data.create_time is '创建时间';
comment on column sys_dict_data.update_by is '更新者';
comment on column sys_dict_data.update_time is '更新时间';
comment on column sys_dict_data.remark is '备注';
select setval(pg_get_serial_sequence('sys_dict_data', 'dict_code'), 100) from sys_dict_data;

insert into sys_dict_data overriding system value values(1,  1,  '男',       'MALE',    'sys_user_sex',        '',   '',        true, 'OK', 'admin', now(), '', null, '性别男');
insert into sys_dict_data overriding system value values(2,  2,  '女',       'FEMALE',  'sys_user_sex',        '',   '',        false, 'OK', 'admin', now(), '', null, '性别女');
insert into sys_dict_data overriding system value values(3,  3,  '未知',     'UNKNOWN', 'sys_user_sex',        '',   '',        false, 'OK', 'admin', now(), '', null, '性别未知');
insert into sys_dict_data overriding system value values(4,  1,  '显示',     'false',   'sys_show_hide',       '',   'primary', true, 'OK', 'admin', now(), '', null, '显示菜单');
insert into sys_dict_data overriding system value values(5,  2,  '隐藏',     'true',    'sys_show_hide',       '',   'danger',  false, 'OK', 'admin', now(), '', null, '隐藏菜单');
insert into sys_dict_data overriding system value values(6,  1,  '正常',     'OK',      'sys_normal_disable',  '',   'primary', true, 'OK', 'admin', now(), '', null, '正常状态');
insert into sys_dict_data overriding system value values(7,  2,  '停用',     'DISABLE', 'sys_normal_disable',  '',   'danger',  false, 'OK', 'admin', now(), '', null, '停用状态');
insert into sys_dict_data overriding system value values(8,  1,  '正常',     'false',   'sys_job_status',      '',   'primary', true, 'OK', 'admin', now(), '', null, '正常状态');
insert into sys_dict_data overriding system value values(9,  2,  '暂停',     'true',    'sys_job_status',      '',   'danger',  false, 'OK', 'admin', now(), '', null, '停用状态');
insert into sys_dict_data overriding system value values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        true, 'OK', 'admin', now(), '', null, '默认分组');
insert into sys_dict_data overriding system value values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        false, 'OK', 'admin', now(), '', null, '系统分组');
insert into sys_dict_data overriding system value values(12, 1,  '是',       'true',    'sys_yes_no',          '',   'primary', true, 'OK', 'admin', now(), '', null, '系统默认是');
insert into sys_dict_data overriding system value values(13, 2,  '否',       'false',   'sys_yes_no',          '',   'danger',  false, 'OK', 'admin', now(), '', null, '系统默认否');
insert into sys_dict_data overriding system value values(14, 1,  '通知',     'NOTICE',  'sys_notice_type',     '',   'warning', true, 'OK', 'admin', now(), '', null, '通知');
insert into sys_dict_data overriding system value values(15, 2,  '公告',     'ANNOUNCEMENT',       'sys_notice_type',     '',   'success', false, 'OK', 'admin', now(), '', null, '公告');
insert into sys_dict_data overriding system value values(16, 1,  '正常',     'NORMAL',  'sys_notice_status',   '',   'primary', true, 'OK', 'admin', now(), '', null, '正常状态');
insert into sys_dict_data overriding system value values(17, 2,  '关闭',     'CLOSED',  'sys_notice_status',   '',   'danger',  false, 'OK', 'admin', now(), '', null, '关闭状态');
insert into sys_dict_data overriding system value values(18, 99, '其他',     'OTHER',       'sys_oper_type',       '',   'info',    false, 'OK', 'admin', now(), '', null, '其他操作');
insert into sys_dict_data overriding system value values(19, 1,  '新增',     'INSERT',       'sys_oper_type',       '',   'info',    false, 'OK', 'admin', now(), '', null, '新增操作');
insert into sys_dict_data overriding system value values(20, 2,  '修改',     'UPDATE',       'sys_oper_type',       '',   'info',    false, 'OK', 'admin', now(), '', null, '修改操作');
insert into sys_dict_data overriding system value values(21, 3,  '删除',     'DELETE',       'sys_oper_type',       '',   'danger',  false, 'OK', 'admin', now(), '', null, '删除操作');
insert into sys_dict_data overriding system value values(22, 4,  '授权',     'GRANT',       'sys_oper_type',       '',   'primary', false, 'OK', 'admin', now(), '', null, '授权操作');
insert into sys_dict_data overriding system value values(23, 5,  '导出',     'EXPORT',       'sys_oper_type',       '',   'warning', false, 'OK', 'admin', now(), '', null, '导出操作');
insert into sys_dict_data overriding system value values(24, 6,  '导入',     'IMPORT',       'sys_oper_type',       '',   'warning', false, 'OK', 'admin', now(), '', null, '导入操作');
insert into sys_dict_data overriding system value values(25, 7,  '强退',     'FORCE',       'sys_oper_type',       '',   'danger',  false, 'OK', 'admin', now(), '', null, '强退操作');
insert into sys_dict_data overriding system value values(26, 8,  '生成代码', 'GENCODE',       'sys_oper_type',       '',   'warning', false, 'OK', 'admin', now(), '', null, '生成操作');
insert into sys_dict_data overriding system value values(27, 9,  '清空数据', 'CLEAN',       'sys_oper_type',       '',   'danger',  false, 'OK', 'admin', now(), '', null, '清空操作');
insert into sys_dict_data overriding system value values(28, 1,  '成功',     'false',   'sys_common_status',   '',   'primary', false, 'OK', 'admin', now(), '', null, '正常状态');
insert into sys_dict_data overriding system value values(29, 2,  '失败',     'true',    'sys_common_status',   '',   'danger',  false, 'OK', 'admin', now(), '', null, '停用状态');


-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int             generated always as identity,
  config_name       varchar(100)    default '',
  config_key        varchar(100)    default '',
  config_value      varchar(500)    default '',
  config_type       bool            default false,
  create_by         varchar(64)     default '',
  create_time       timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  remark            varchar(500)    default null,
  primary key (config_id)
);
comment on table sys_config is '参数配置表';
comment on column sys_config.config_id is '参数主键';
comment on column sys_config.config_name is '参数名称';
comment on column sys_config.config_key is '参数键名';
comment on column sys_config.config_value is '参数键值';
comment on column sys_config.config_type is '系统内置';
comment on column sys_config.create_by is '创建者';
comment on column sys_config.create_time is '创建时间';
comment on column sys_config.update_by is '更新者';
comment on column sys_config.update_time is '更新时间';
comment on column sys_config.remark is '备注';
select setval(pg_get_serial_sequence('sys_config', 'config_id'), 100) from sys_config;

insert into sys_config overriding system value values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',            'skin-blue',     true, 'admin', now(), '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config overriding system value values(2, '用户管理-账号初始密码',         'sys.user.initPassword',         '123456',        true, 'admin', now(), '', null, '初始化密码 123456' );
insert into sys_config overriding system value values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',           'theme-dark',    true, 'admin', now(), '', null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config overriding system value values(4, '账号自助-验证码开关',           'sys.account.captchaEnabled',    'true',          true, 'admin', now(), '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config overriding system value values(5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser',      'false',         true, 'admin', now(), '', null, '是否开启注册用户功能（true开启，false关闭）');
insert into sys_config overriding system value values(6, '用户登录-黑名单列表',           'sys.login.blackIPList',         '',              true, 'admin', now(), '', null, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
insert into sys_config overriding system value values(7, '用户管理-初始密码修改策略',     'sys.account.initPasswordModify',   '1',             true, 'admin', now(), '', null, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
insert into sys_config overriding system value values(8, '用户管理-账号密码更新周期',     'sys.account.passwordValidateDays', '0',             true, 'admin', now(), '', null, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop type if exists access_status cascade;
create type access_status as enum ('SUCCESS', 'FAIL');

drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint         generated always as identity,
  user_name      varchar(50)    default '',
  ipaddr         varchar(128)   default '',
  login_location varchar(255)   default '',
  browser        varchar(50)    default '',
  os             varchar(50)    default '',
  status         access_status  default 'SUCCESS',
  msg            varchar(255)   default '',
  login_time     timestamp(0),
  primary key (info_id)
);
create index idx_sys_logininfor_s on sys_logininfor  (status);
create index idx_sys_logininfor_lt on sys_logininfor (login_time);
comment on table sys_logininfor is '系统访问记录';
comment on column sys_logininfor.info_id is '访问ID';
comment on column sys_logininfor.user_name is '用户账号';
comment on column sys_logininfor.ipaddr is '登录IP地址';
comment on column sys_logininfor.login_location is '登录地点';
comment on column sys_logininfor.browser is '浏览器类型';
comment on column sys_logininfor.os is '操作系统';
comment on column sys_logininfor.status is '登录状态';
comment on column sys_logininfor.msg is '提示消息';
comment on column sys_logininfor.login_time is '访问时间';
select setval(pg_get_serial_sequence('sys_logininfor', 'info_id'), 100) from sys_logininfor;

-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop type if exists job_status cascade;
create type job_status as enum ('NORMAL', 'PAUSE');
drop type if exists misfire_policy cascade;
create type misfire_policy as enum ('DEFAULT', 'IGNORE_MISFIRES', 'FIRE_AND_PROCEED', 'DO_NOTHING');

drop table if exists sys_job;
create table sys_job (
  job_id              bigint    generated always as identity,
  job_name            varchar(64)     default '',
  job_group           varchar(64)     default 'DEFAULT',
  invoke_target       varchar(500)    not null,
  cron_expression     varchar(255)    default '',
  misfire_policy      misfire_policy  default 'DO_NOTHING',
  concurrent          bool            default false,
  status              job_status      default 'NORMAL',
  create_by           varchar(64)     default '',
  create_time         timestamp(0),
  update_by           varchar(64)     default '',
  update_time         timestamp(0),
  remark              varchar(500)    default '',
  primary key (job_id, job_name, job_group)
);
comment on table sys_job is '定时任务调度表';
comment on column sys_job.job_id is '任务ID';
comment on column sys_job.job_name is '任务名称';
comment on column sys_job.job_group is '任务组名';
comment on column sys_job.invoke_target is '调用目标字符串';
comment on column sys_job.cron_expression is 'cron执行表达式';
comment on column sys_job.misfire_policy is '计划执行错误策略';
comment on column sys_job.concurrent is '是否并发执行';
comment on column sys_job.status is '状态';
comment on column sys_job.create_by is '创建者';
comment on column sys_job.create_time is '创建时间';
comment on column sys_job.update_by is '更新者';
comment on column sys_job.update_time is '更新时间';
comment on column sys_job.remark is '备注信息';
select setval(pg_get_serial_sequence('sys_job', 'job_id'), 100) from sys_job;

insert into sys_job overriding system value values(1, '系统默认（无参）', 'DEFAULT', 'happyTask.ryNoParams',        '0/10 * * * * ?', 'DO_NOTHING', false, 'PAUSE', 'admin', now(), '', null, '');
insert into sys_job overriding system value values(2, '系统默认（有参）', 'DEFAULT', 'happyTask.ryParams(''happy'')',  '0/15 * * * * ?', 'DO_NOTHING', false, 'PAUSE', 'admin', now(), '', null, '');
insert into sys_job overriding system value values(3, '系统默认（多参）', 'DEFAULT', 'happyTask.ryMultipleParams(''happy'', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', 'DO_NOTHING', false, 'PAUSE', 'admin', now(), '', null, '');


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint     generated always as identity,
  job_name            varchar(64)    not null,
  job_group           varchar(64)    not null,
  invoke_target       varchar(500)   not null,
  job_message         varchar(500),
  status              job_status     default 'NORMAL',
  exception_info      varchar(2000)  default '',
  create_time         timestamp(0),
  primary key (job_log_id)
);
comment on table sys_job_log is '定时任务调度日志表';
comment on column sys_job_log.job_log_id is '任务日志ID';
comment on column sys_job_log.job_name is '任务名称';
comment on column sys_job_log.job_group is '任务组名';
comment on column sys_job_log.invoke_target is '调用目标字符串';
comment on column sys_job_log.job_message is '日志信息';
comment on column sys_job_log.status is '执行状态';
comment on column sys_job_log.exception_info is '异常信息';
comment on column sys_job_log.create_time is '创建时间';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop type if exists notice_type cascade;
create type notice_type as enum ('NOTICE', 'ANNOUNCEMENT');
drop type if exists notice_status cascade;
create type notice_status as enum ('NORMAL', 'CLOSED');

drop table if exists sys_notice;
create table sys_notice (
  notice_id         int             generated always as identity,
  notice_title      varchar(50)     not null,
  notice_type       notice_type     not null,
  notice_content    bytea           default null,
  status            notice_status   default 'NORMAL',
  create_by         varchar(64)     default '',
  create_time       timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  remark            varchar(255)    default null,
  primary key (notice_id)
);
comment on table sys_notice is '通知公告表';
comment on column sys_notice.notice_id is '公告ID';
comment on column sys_notice.notice_title is '公告标题';
comment on column sys_notice.notice_type is '公告类型';
comment on column sys_notice.notice_content is '公告内容';
comment on column sys_notice.status is '公告状态';
comment on column sys_notice.create_by is '创建者';
comment on column sys_notice.create_time is '创建时间';
comment on column sys_notice.update_by is '更新者';
comment on column sys_notice.update_time is '更新时间';
comment on column sys_notice.remark is '备注';
select setval(pg_get_serial_sequence('sys_notice', 'notice_id'), 10) from sys_notice;

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice overriding system value values('1', '温馨提醒：2018-07-01 开心新版本发布啦', 'ANNOUNCEMENT', '新版本内容', 'NORMAL', 'admin', now(), '', null, '管理员');
insert into sys_notice overriding system value values('2', '维护通知：2018-07-01 开心系统凌晨维护', 'NOTICE', '维护内容',   'NORMAL', 'admin', now(), '', null, '管理员');


-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
drop type if exists tpl_category cascade;
create type tpl_category as enum ('CRUD', 'TREE', 'SUB');
drop type if exists tpl_web_type cascade;
create type tpl_web_type as enum ('ELEMENT_UI', 'ELEMENT_PLUS');
drop type if exists gen_type cascade;
create type gen_type as enum ('ZIP', 'CUSTOM');

drop table if exists gen_table;
create table gen_table (
  table_id          bigint          generated always as identity,
  table_name        varchar(200)    default '',
  table_comment     varchar(500)    default '',
  sub_table_name    varchar(64)     default null,
  sub_table_fk_name varchar(64)     default null,
  class_name        varchar(100)    default '',
  tpl_category      tpl_category    default 'CRUD',
  tpl_web_type      tpl_web_type    default 'ELEMENT_UI',
  package_name      varchar(100),
  module_name       varchar(30),
  business_name     varchar(30),
  function_name     varchar(50),
  function_author   varchar(50),
  gen_type          gen_type        default 'ZIP',
  gen_path          varchar(200)    default '/',
  options           varchar(1000),
  create_by         varchar(64)     default '',
  create_time 	    timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  remark            varchar(500)    default null,
  primary key (table_id)
);
comment on table gen_table is '代码生成业务表';
comment on column gen_table.table_id is '编号';
comment on column gen_table.table_name is '表名称';
comment on column gen_table.table_comment is '表描述';
comment on column gen_table.sub_table_name is '关联子表的表名';
comment on column gen_table.sub_table_fk_name is '子表关联的外键名';
comment on column gen_table.class_name is '实体类名称';
comment on column gen_table.tpl_category is '使用的模板';
comment on column gen_table.tpl_web_type is '前端模板类型';
comment on column gen_table.package_name is '生成包路径';
comment on column gen_table.module_name is '生成模块名';
comment on column gen_table.business_name is '生成业务名';
comment on column gen_table.function_name is '生成功能名';
comment on column gen_table.function_author is '生成功能作者';
comment on column gen_table.gen_type is '生成代码方式';
comment on column gen_table.gen_path is '生成路径（不填默认项目路径）';
comment on column gen_table.options is '其它生成选项';
comment on column gen_table.create_by is '创建者';
comment on column gen_table.create_time is '创建时间';
comment on column gen_table.update_by is '更新者';
comment on column gen_table.update_time is '更新时间';
comment on column gen_table.remark is '备注';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop type if exists query_operator cascade;
create type query_operator as enum ('EQ', 'NE', 'GT', 'LT', 'LIKE', 'BETWEEN');
drop type if exists html_type cascade;
create type html_type as enum ('INPUT', 'TEXTAREA', 'SELECT', 'RADIO', 'CHECKBOX', 'DATETIME', 'IMAGE_UPLOAD', 'FILE_UPLOAD', 'EDITOR');

drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint      generated always as identity,
  table_id          bigint,
  column_name       varchar(200),
  column_comment    varchar(500),
  column_type       varchar(100),
  java_type         varchar(500),
  java_field        varchar(200),
  is_pk             bool,
  is_increment      bool,
  is_required       bool,
  is_insert         bool,
  is_edit           bool,
  is_list           bool,
  is_query          bool,
  query_type        query_operator  default 'EQ',
  html_type         html_type,
  dict_type         varchar(200)    default '',
  sort              int,
  create_by         varchar(64)     default '',
  create_time 	    timestamp(0),
  update_by         varchar(64)     default '',
  update_time       timestamp(0),
  primary key (column_id)
);
comment on table gen_table_column is '代码生成业务表字段';
comment on column gen_table_column.column_id is '编号';
comment on column gen_table_column.table_id is '归属表编号';
comment on column gen_table_column.column_name is '列名称';
comment on column gen_table_column.column_comment is '列描述';
comment on column gen_table_column.column_type is '列类型';
comment on column gen_table_column.java_type is 'JAVA类型';
comment on column gen_table_column.java_field is 'JAVA字段名';
comment on column gen_table_column.is_pk is '是否主键';
comment on column gen_table_column.is_increment is '是否自增';
comment on column gen_table_column.is_required is '是否必填';
comment on column gen_table_column.is_insert is '是否为插入字段';
comment on column gen_table_column.is_edit is '是否编辑字段';
comment on column gen_table_column.is_list is '是否列表字段';
comment on column gen_table_column.is_query is '是否查询字段';
comment on column gen_table_column.query_type is '查询方式';
comment on column gen_table_column.html_type is '显示类型';
comment on column gen_table_column.dict_type is '字典类型';
comment on column gen_table_column.sort is '排序';
comment on column gen_table_column.create_by is '创建者';
comment on column gen_table_column.create_time is '创建时间';
comment on column gen_table_column.update_by is '更新者';
comment on column gen_table_column.update_time is '更新时间';
