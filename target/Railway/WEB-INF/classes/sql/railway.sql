-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema railway
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `railway` ;

-- -----------------------------------------------------
-- Schema railway
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway` DEFAULT CHARACTER SET utf8 ;
USE `railway` ;

-- -----------------------------------------------------
-- Table `railway`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`user` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `email` VARCHAR(255) NOT NULL,
                                                `password` VARCHAR(64) NOT NULL,
                                                `first_name` VARCHAR(60) NOT NULL,
                                                `last_name` VARCHAR(60) NOT NULL,
                                                `phone` VARCHAR(25) NOT NULL,
                                                `birth_date` DATE NOT NULL,
                                                `role` VARCHAR(45) NOT NULL,
                                                `blocked` BOOLEAN NOT NULL DEFAULT FALSE,
                                                PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`booking` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                               `booking_date` TIMESTAMP(4) NOT NULL,
                                               `route_id` INT NOT NULL,
                                               `dispatch_station` VARCHAR(64) NOT NULL,
                                               `dispatch_date` TIMESTAMP(4) NOT NULL,
                                               `arrival_station` VARCHAR(64) NOT NULL,
                                               `arrival_date` TIMESTAMP(4) NOT NULL,
                                               `travel_time` VARCHAR(128) NOT NULL,
                                               `train_number` VARCHAR(64) NOT NULL,
                                               `carriage_number` INT NOT NULL,
                                               `carriage_type` VARCHAR(32) NOT NULL,
                                               `seat_count` INT NOT NULL,
                                               `seat_number` INT NOT NULL,
                                               `seat_id` INT NOT NULL,
                                               `user_id` INT NOT NULL,
                                               `price` DECIMAL(2) NOT NULL,
                                               `status` VARCHAR(45) NULL,
                                                PRIMARY KEY (`id`),
                                               INDEX `fk_booking_user_idx` (`user_id` ASC) VISIBLE,
                                               CONSTRAINT `fk_booking_user`
                                                   FOREIGN KEY (`user_id`)
                                                       REFERENCES `railway`.`user` (`id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway`.`train`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`train` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `number` VARCHAR(64) NOT NULL,
                                                 PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway`.`wagon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`carriage` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `type` VARCHAR(45) NOT NULL,
                                                 `number` VARCHAR(45) NOT NULL,
                                                 `train_id` INT NOT NULL,
                                                 PRIMARY KEY (`id`,`number`,`train_id`),
                                                 INDEX `fk_carriage_train_idx` (`train_id` ASC) VISIBLE,
                                                 CONSTRAINT `carriage_number_train_id_uq` UNIQUE(`number`,`train_id`),
                                                 CONSTRAINT `fk_carriage_train`
                                                     FOREIGN KEY (`train_id`)
                                                         REFERENCES `railway`.`train` (`id`)
                                                         ON DELETE NO ACTION
                                                         ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`route` (
                                                 `id` INT NOT NULL,
                                                 `name` VARCHAR(64) NOT NULL,
                                                 `number` INT NOT NULL,
                                                 `train_id` INT NOT NULL,
                                                 PRIMARY KEY (`id`),
                                                 INDEX `fk_route_train_idx` (`train_id` ASC) VISIBLE,
                                                 CONSTRAINT `fk_route_train`
                                                     FOREIGN KEY (`train_id`)
                                                         REFERENCES `railway`.`train` (`id`)
                                                         ON DELETE NO ACTION
                                                         ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`seat` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `seat_number` varchar(45) NOT NULL,
                                                `busy` BOOLEAN NOT NULL DEFAULT false,
                                                `carriage_id` INT NOT NULL,
                                                PRIMARY KEY (`id`),
                                                INDEX `fk_seat_carriage_idx` (`carriage_id` ASC) VISIBLE,
                                                CONSTRAINT `fk_seat_carriage`
                                                    FOREIGN KEY (`carriage_id`)
                                                        REFERENCES `railway`.`carriage` (`id`)
                                                        ON DELETE NO ACTION
                                                        ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `railway`.`station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`station` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `station` VARCHAR(100) NOT NULL,
                                                   PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway`.`station_has_route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `railway`.`station_has_route` (
                                                             `station_id` INT NOT NULL,
                                                             `route_id` INT NOT NULL,
                                                             `station_arrival` TIMESTAMP(4) NOT NULL,
                                                             `station_dispatch` TIMESTAMP(4) NOT NULL,
                                                             `pnr` INT NOT NULL,
                                                             PRIMARY KEY (`station_id`, `route_id`),
                                                             INDEX `fk_station_has_route_route1_idx` (`route_id` ASC) VISIBLE,
                                                             INDEX `fk_station_has_route_station1_idx` (`station_id` ASC) VISIBLE,
                                                             CONSTRAINT `fk_station_has_route_station1`
                                                                 FOREIGN KEY (`station_id`)
                                                                     REFERENCES `railway`.`station` (`id`)
                                                                     ON DELETE CASCADE
                                                                     ON UPDATE NO ACTION,
                                                             CONSTRAINT `fk_station_has_route_route1`
                                                                 FOREIGN KEY (`route_id`)
                                                                     REFERENCES `railway`.`route` (`id`)
                                                                     ON DELETE CASCADE
                                                                     ON UPDATE NO ACTION);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
