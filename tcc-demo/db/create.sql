create database tcc;

CREATE TABLE `tcc_tx` (
  `xid` varchar(40) NOT NULL COMMENT '',
  `status` TINYINT DEFAULT 0 COMMENT '0 :begin,1:finish,2:try success,3 try fail,4:confirm fail,5:rollback fail',
  `begin_time` DATETIME not null DEFAULT CURRENT_TIMESTAMP COMMENT '事务开始时间',
  `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '事务结束时间',
  `duration` int DEFAULT 0 COMMENT '事务执行时间',
  PRIMARY KEY (`xid`),
  KEY `idx_begin_time` (`begin_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '分布式事务表（热点数据）。为补偿任务提供接口原始数据 ';


CREATE TABLE `tcc_tx_child` (
  `child_xid` varchar(40) NOT NULL COMMENT '',
  `xid` varchar(40) NOT NULL COMMENT '',
  `status` TINYINT DEFAULT 0 COMMENT '0 :begin,1:finish,2:try success ,4:confirm fail,5:rollback fail',
  `begin_time` DATETIME not null DEFAULT CURRENT_TIMESTAMP COMMENT '事务开始时间',
  `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '事务结束时间',
  `duration` int DEFAULT 0 COMMENT '事务执行时间',
  `cls_name` VARCHAR(300) DEFAULT null COMMENT 'facade 接口',
  `commit_method` VARCHAR(120) DEFAULT null COMMENT 'commit 方法名',
  `rollback_method` VARCHAR(120) DEFAULT null COMMENT 'rollback 方法名',
  `parames_types` VARCHAR(1024) DEFAULT null COMMENT '请求参数类型列表',
  `parames_values` VARCHAR(8192) DEFAULT null COMMENT '请求参数值 序列化',
  PRIMARY KEY (`child_xid`),
  KEY `idx_xid` (`xid`) USING BTREE,
  KEY `idx_begin_time` (`begin_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '子布式事务表（热点数据）。为补偿任务提供接口原始数据 ';

CREATE TABLE `tcc_tx_archive` (
  `xid` varchar(40) NOT NULL COMMENT '',
  `status` TINYINT DEFAULT 0 COMMENT '0 :begin,1:finish,2:try success,3 try fail,4:confirm fail,5:rollback fail',
  `begin_time` DATETIME not null DEFAULT CURRENT_TIMESTAMP COMMENT '事务开始时间',
  `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '事务结束时间',
  `duration` int DEFAULT 0 COMMENT '事务执行时间',
  PRIMARY KEY (`xid`),
  KEY `idx_begin_time` (`begin_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '分布式事务表（冷数据）。为补偿任务提供接口原始数据 ';


CREATE TABLE `tcc_tx_child_archive` (
  `child_xid` varchar(40) NOT NULL COMMENT '',
  `xid` varchar(40) NOT NULL COMMENT '',
  `status` TINYINT DEFAULT 0 COMMENT '0 :begin,1:finish,2:try success,4:confirm fail,5:rollback fail',
  `begin_time` DATETIME not null DEFAULT CURRENT_TIMESTAMP COMMENT '事务开始时间',
  `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '事务结束时间',
  `duration` int DEFAULT 0 COMMENT '事务执行时间',
  `cls_name` VARCHAR(300) DEFAULT null COMMENT 'facade 接口',
  `commit_method` VARCHAR(120) DEFAULT null COMMENT 'commit 方法名',
  `rollback_method` VARCHAR(120) DEFAULT null COMMENT 'rollback 方法名',
  `parames_types` VARCHAR(1024) DEFAULT null COMMENT '请求参数类型列表',
  `parames_values` VARCHAR(8192) DEFAULT null COMMENT '请求参数值 序列化',
  PRIMARY KEY (`child_xid`),
  KEY `idx_xid` (`xid`) USING BTREE,
  KEY `idx_begin_time` (`begin_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '子布式事务表（冷数据）。为补偿任务提供接口原始数据 ';
