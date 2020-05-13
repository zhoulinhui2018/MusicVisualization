DROP TABLE IF EXISTS `music`;
CREATE TABLE `music` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(50) ,
`url` varchar(300),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(100) unique ,
     `password` varchar(100),
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;