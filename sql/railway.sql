-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema railway
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway` DEFAULT CHARACTER SET utf8 ;
USE `railway` ;

-- -----------------------------------------------------
-- Table `railway`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`user` ;

CREATE TABLE IF NOT EXISTS `railway`.`user` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `email` VARCHAR(255) NOT NULL,
                                                `password` VARCHAR(256) NOT NULL,
                                                `first_name` VARCHAR(60) NOT NULL,
                                                `last_name` VARCHAR(60) NOT NULL,
                                                `phone` VARCHAR(25) NOT NULL,
                                                `birth_date` DATE NOT NULL,
                                                `role` VARCHAR(45) NOT NULL,
                                                `blocked` TINYINT(1) NOT NULL DEFAULT '0',
                                                `log_in_token` VARCHAR(256) NULL DEFAULT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`train` ;

CREATE TABLE IF NOT EXISTS `railway`.`train` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `number` VARCHAR(64) NOT NULL,
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 25
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`route` ;

CREATE TABLE IF NOT EXISTS `railway`.`route` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `name` VARCHAR(64) NOT NULL,
                                                 `number` INT NOT NULL,
                                                 `train_id` INT NOT NULL,
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE INDEX `name_number_train_id_uq` (`name` ASC, `number` ASC, `train_id` ASC) VISIBLE,
                                                 INDEX `fk_route_train_idx` (`train_id` ASC) VISIBLE,
                                                 CONSTRAINT `fk_route_train`
                                                     FOREIGN KEY (`train_id`)
                                                         REFERENCES `railway`.`train` (`id`)
                                                         ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 25
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`station`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`station` ;

CREATE TABLE IF NOT EXISTS `railway`.`station` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `station` VARCHAR(100) NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE INDEX `station_UNIQUE` (`station` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 48
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`carriage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`carriage` ;

CREATE TABLE IF NOT EXISTS `railway`.`carriage` (
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `number` VARCHAR(45) NOT NULL,
                                                    `type` VARCHAR(45) NOT NULL,
                                                    `train_id` INT NOT NULL,
                                                    `num_seats` INT NOT NULL,
                                                    PRIMARY KEY (`id`),
                                                    UNIQUE INDEX `carriage_number_train_id_uq` (`number` ASC, `train_id` ASC) VISIBLE,
                                                    INDEX `fk_carriage_train_idx` (`train_id` ASC) VISIBLE,
                                                    CONSTRAINT `fk_carriage_train`
                                                        FOREIGN KEY (`train_id`)
                                                            REFERENCES `railway`.`train` (`id`)
                                                            ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`booking` ;

CREATE TABLE IF NOT EXISTS `railway`.`booking` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `uuid` CHAR(36) NOT NULL,
                                                   `booking_date` TIMESTAMP NOT NULL,
                                                   `dispatch_date` TIMESTAMP NOT NULL,
                                                   `arrival_date` TIMESTAMP NOT NULL,
                                                   `travel_time` VARCHAR(128) NOT NULL,
                                                   `price` DECIMAL(10,2) NOT NULL,
                                                   `status` VARCHAR(45) NULL DEFAULT NULL,
                                                   `user_id` INT NOT NULL,
                                                   `route_id1` INT NOT NULL,
                                                   `train_id` INT NOT NULL,
                                                   `dispatch_station_id` INT NOT NULL,
                                                   `arrival_station_id` INT NOT NULL,
                                                   `carriage_id` INT NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   INDEX `fk_booking_user_idx` (`user_id` ASC) VISIBLE,
                                                   INDEX `fk_booking_route1_idx` (`route_id1` ASC) VISIBLE,
                                                   INDEX `fk_booking_train1_idx` (`train_id` ASC) VISIBLE,
                                                   INDEX `fk_booking_station1_idx` (`dispatch_station_id` ASC) VISIBLE,
                                                   INDEX `fk_booking_station2_idx` (`arrival_station_id` ASC) VISIBLE,
                                                   INDEX `fk_booking_carriage1_idx` (`carriage_id` ASC) VISIBLE,
                                                   CONSTRAINT `fk_booking_user`
                                                       FOREIGN KEY (`user_id`)
                                                           REFERENCES `railway`.`user` (`id`)
                                                           ON DELETE CASCADE,
                                                   CONSTRAINT `fk_booking_route1`
                                                       FOREIGN KEY (`route_id1`)
                                                           REFERENCES `railway`.`route` (`id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `fk_booking_train1`
                                                       FOREIGN KEY (`train_id`)
                                                           REFERENCES `railway`.`train` (`id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `fk_booking_station1`
                                                       FOREIGN KEY (`dispatch_station_id`)
                                                           REFERENCES `railway`.`station` (`id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `fk_booking_station2`
                                                       FOREIGN KEY (`arrival_station_id`)
                                                           REFERENCES `railway`.`station` (`id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `fk_booking_carriage1`
                                                       FOREIGN KEY (`carriage_id`)
                                                           REFERENCES `railway`.`carriage` (`id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`seat` ;

CREATE TABLE IF NOT EXISTS `railway`.`seat` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `seat_number` VARCHAR(45) NOT NULL,
                                                `busy` TINYINT(1) NOT NULL DEFAULT '0',
                                                `carriage_id` INT NOT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX `seat_number_carriage_id_uq` (`seat_number` ASC, `carriage_id` ASC) VISIBLE,
                                                INDEX `fk_seat_carriage_idx` (`carriage_id` ASC) VISIBLE,
                                                CONSTRAINT `fk_seat_carriage`
                                                    FOREIGN KEY (`carriage_id`)
                                                        REFERENCES `railway`.`carriage` (`id`)
                                                        ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 78
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`station_has_route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`station_has_route` ;

CREATE TABLE IF NOT EXISTS `railway`.`station_has_route` (
                                                             `station_arrival` TIMESTAMP(4) NOT NULL,
                                                             `station_dispatch` TIMESTAMP(4) NOT NULL,
                                                             `station_order` INT NOT NULL,
                                                             `route_id` INT NOT NULL,
                                                             `station_id` INT NOT NULL,
                                                             PRIMARY KEY (`route_id`, `station_id`),
                                                             INDEX `fk_station_has_route_route1_idx` (`route_id` ASC) VISIBLE,
                                                             INDEX `fk_station_has_route_station1_idx` (`station_id` ASC) VISIBLE,
                                                             CONSTRAINT `fk_station_has_route_route1`
                                                                 FOREIGN KEY (`route_id`)
                                                                     REFERENCES `railway`.`route` (`id`)
                                                                     ON DELETE CASCADE,
                                                             CONSTRAINT `fk_station_has_route_station1`
                                                                 FOREIGN KEY (`station_id`)
                                                                     REFERENCES `railway`.`station` (`id`)
                                                                     ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`booking_has_seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`booking_has_seat` ;

CREATE TABLE IF NOT EXISTS `railway`.`booking_has_seat` (
                                                            `booking_id` INT NOT NULL,
                                                            `seat_id` INT NOT NULL,
                                                            PRIMARY KEY (`booking_id`, `seat_id`),
                                                            INDEX `fk_booking_has_seat_seat1_idx` (`seat_id` ASC) VISIBLE,
                                                            INDEX `fk_booking_has_seat_booking1_idx` (`booking_id` ASC) VISIBLE,
                                                            CONSTRAINT `fk_booking_has_seat_booking1`
                                                                FOREIGN KEY (`booking_id`)
                                                                    REFERENCES `railway`.`booking` (`id`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE NO ACTION,
                                                            CONSTRAINT `fk_booking_has_seat_seat1`
                                                                FOREIGN KEY (`seat_id`)
                                                                    REFERENCES `railway`.`seat` (`id`)
                                                                    ON DELETE NO ACTION
                                                                    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `railway`.`reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway`.`reservation` ;

CREATE TABLE IF NOT EXISTS `railway`.`reservation` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `status` VARCHAR(255) NOT NULL,
                                                       `station_id` INT NOT NULL,
                                                       `seat_id` INT NOT NULL,
                                                       `train_id` INT NOT NULL,
                                                       `route_id` INT NOT NULL,
                                                       `sequence_number` INT NOT NULL,
                                                       PRIMARY KEY (`id`),
                                                       UNIQUE KEY `uniq_seat_train_route` (`seat_id`, `train_id`, `route_id`, `sequence_number`),
                                                       INDEX `fk_reservation_station_idx` (`station_id` ASC) VISIBLE,
                                                       INDEX `fk_reservation_seat_idx` (`seat_id` ASC) VISIBLE,
                                                       INDEX `fk_reservation_train_idx` (`train_id` ASC) VISIBLE,
                                                       INDEX `fk_reservation_route_idx` (`route_id` ASC) VISIBLE,
                                                       CONSTRAINT `fk_reservation_station`
                                                           FOREIGN KEY (`station_id`)
                                                               REFERENCES `railway`.`station` (`id`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION,
                                                       CONSTRAINT `fk_reservation_seat`
                                                           FOREIGN KEY (`seat_id`)
                                                               REFERENCES `railway`.`seat` (`id`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION,
                                                       CONSTRAINT `fk_reservation_train`
                                                           FOREIGN KEY (`train_id`)
                                                               REFERENCES `railway`.`train` (`id`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION,
                                                       CONSTRAINT `fk_reservation_route`
                                                           FOREIGN KEY (`route_id`)
                                                               REFERENCES `railway`.`route` (`id`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION
) ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;