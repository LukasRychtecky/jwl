SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

drop database seamwiki;
CREATE database `seamwiki` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `seamwiki`; 

CREATE  TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  `login` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `role` VARCHAR(45) ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
ROW_FORMAT = DEFAULT;

SHOW WARNINGS; 

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS; 

SET AUTOCOMMIT=0;


INSERT INTO `user` (`id`, `name`, `surname`, `login`, `password`, `email`, `role`) VALUES (1, 'John', 'Lenon', 'john', 'john', 'john@lenon.bt', 'admin');
INSERT INTO `user` (`id`, `name`, `surname`, `login`, `password`, `email`, `role`) VALUES (2, 'Paul', 'McCartney', 'paul', 'paul', 'paul@mccartney.bt', 'editor');
INSERT INTO `user` (`id`, `name`, `surname`, `login`, `password`, `email`, `role`) VALUES (3, 'Ringo', 'Star', 'ringo', 'ringo', 'ringo@star.bt', 'visitor, writer');
INSERT INTO `user` (`id`, `name`, `surname`, `login`, `password`, `email`, `role`) VALUES (4, 'George', 'Harrison', 'george', 'george', 'george@harrison.bt', 'visitor');
INSERT INTO `user` (`id`, `name`, `surname`, `login`, `password`, `email`, `role`) VALUES (5, 'Pete', 'Best', 'pete', 'pete', 'pete@best.bt', null);
COMMIT;

