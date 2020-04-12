DROP TABLE IF EXISTS `music`;
CREATE TABLE `music` (
`id` int(11) NOT NULL,
`name` varchar(50),
`url` varchar(300),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;