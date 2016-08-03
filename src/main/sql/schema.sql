-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill (
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Product Id',
  `NAME` VARCHAR (120) NOT NULL COMMENT 'Product NAME ',
  `NUMBER` INT NOT NULL COMMENT 'Numbers IN Stock',
  `start_time` TIMESTAMP NOT NULL COMMENT ' START TIME ',
  `end_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT ' END TIME ',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' CREATE TIME of the Record',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
-- 支持事务的引擎是InnoDB, 自增, 字符集, 表的注释

-- 初始化数据
INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('499 NZD iPhone 6S', 100, '2016-08-03 00:00:00', '2016-08-04 00:00:00'),
  ('399 NZD Bose Audio Equipment', 99, '2016-08-03 00:00:00', '2016-08-04 00:00:00'),
  ('299 NZD iWatch', 88, '2016-08-03 00:00:00', '2016-08-04 00:00:00'),
  ('199 NZD Kindle PaperWhite', 77, '2016-08-03 00:00:00', '2016-08-04 00:00:00'),
  ('99 NZD GPS Navigator', 66, '2016-08-03 00:00:00', '2016-08-04 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed (
  `seckill_id` bigint NOT NULL COMMENT 'Product Id',
  `user_mobile` bigint NOT NULL COMMENT ' USER Mobile NO.',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT ' STATUS :-1 - failure 0 -success; 1 -Paid; 2 -Delivered;',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time of the Record',
  PRIMARY KEY (seckill_id, user_mobile), /* 联合主键: */
  KEY idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 连接数据库的控制台
mysql -uroot -pPassw0rd
