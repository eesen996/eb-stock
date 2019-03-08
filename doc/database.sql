#创建数据库
create database ebstock;

use ebstock;

#用户表
create table user_info(
user_id int primary key auto_increment,
login_name varchar(32),
login_pass varchar(32),
user_name varchar(32)
);

#物品类型表
create table product_type(
prod_type_id int primary key auto_increment,
prod_type_name varchar(64),
parent_id int
);

#物品表
create table product(
prod_id int primary key auto_increment,
prod_name varchar(512),
prod_no varchar(128),
prod_type_id int,
prod_state int
);

#物品规格参数表
create table product_parameter(
prod_param_id int PRIMARY key auto_increment,
prod_param_name varchar(512),
prod_id int
);

#待收货库存
create table product_receive_stock(
prod_rece_stock_id int primary key auto_increment,
prod_id int,
prod_param_id int,
stock_num int
);

#现有库存
create table product_stock(
prod_stock_id int primary key auto_increment,
prod_id int,
prod_param_id int,
stock_num int
);

#待收货进货退货表
create table product_receive_trade(
prod_rece_trade_id int PRIMARY key auto_increment,
trade_date datetime,
prod_id int,
prod_param_id int,
begin_num int,
trade_add int,
trade_del int,
end_num int,
oper_id int
);

#现有库存进货退货表
create table product_trade(
prod_trade_id int PRIMARY key auto_increment,
trade_date datetime,
prod_id int,
prod_param_id int,
begin_num int,
trade_add int,
trade_del int,
end_num int,
oper_id int
);

#mysqldump -uroot -p123456 ebstock >d:/ebstock.sql
