package com.epam.redkin.model.repository;

public interface TestConstants {
    String REMOVE_TRAIN_TABLE = "DROP TABLE IF EXISTS `train`";
    String REMOVE_CARRIAGE_TABLE = "DROP TABLE IF EXISTS `carriage`";
    String REMOVE_SEAT_TABLE = "DROP TABLE IF EXISTS `seat`";
    String REMOVE_STATION_TABLE = "DROP TABLE IF EXISTS `station`";
    String REMOVE_USER_TABLE = "DROP TABLE IF EXISTS `user`";

    String CREATE_TRAIN_TABLE =
            "CREATE TABLE IF NOT EXISTS `train` (\n" +
                    "                                                 `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                                                 `number` VARCHAR(64) NOT NULL,\n" +
                    "                                                  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,\n" +
                    "                                                 PRIMARY KEY (`id`));\n";
    String CREATE_CARRIAGE_TABLE =
            "CREATE TABLE IF NOT EXISTS `carriage` (\n" +
                    "                                                 `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                                                 `type` VARCHAR(45) NOT NULL,\n" +
                    "                                                 `number` VARCHAR(45) NOT NULL,\n" +
                    "                                                 `train_id` INT NOT NULL,\n" +
                    "                                                 PRIMARY KEY (`id`,`number`,`train_id`),\n" +
                    "                                                 INDEX `fk_carriage_train_idx` (`train_id` ASC) VISIBLE,\n" +
                    "                                                 CONSTRAINT `carriage_number_train_id_uq` UNIQUE(`number`,`train_id`),\n" +
                    "                                                 CONSTRAINT `fk_carriage_train`\n" +
                    "                                                     FOREIGN KEY (`train_id`)\n" +
                    "                                                         REFERENCES `train` (`id`)\n" +
                    "                                                         ON DELETE NO ACTION\n" +
                    "                                                         ON UPDATE NO ACTION);";

    String CREATE_SEAT_TABLE = "CREATE TABLE IF NOT EXISTS `seat` (\n" +
            "                                                `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "                                                `seat_number` varchar(45) NOT NULL,\n" +
            "                                                `busy` BOOLEAN NOT NULL DEFAULT false,\n" +
            "                                                `carriage_id` INT NOT NULL,\n" +
            "                                                PRIMARY KEY (`id`),\n" +
            "                                                INDEX `fk_seat_carriage_idx` (`carriage_id` ASC) VISIBLE,\n" +
            "                                                CONSTRAINT `seat_number_carriage_id_uq` UNIQUE(`seat_number`,`carriage_id`),\n" +
            "                                                CONSTRAINT `fk_seat_carriage`\n" +
            "                                                    FOREIGN KEY (`carriage_id`)\n" +
            "                                                        REFERENCES `carriage` (`id`)\n" +
            "                                                        ON DELETE CASCADE \n" +
            "                                                        ON UPDATE NO ACTION);";

    String CREATE_STATION_TABLE = "CREATE TABLE IF NOT EXISTS `station` (\n" +
            "                                                   `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "                                                   `station` VARCHAR(100) NOT NULL,\n" +
            "                                                   UNIQUE INDEX `station_UNIQUE` (`station` ASC) VISIBLE,\n" +
            "                                                   PRIMARY KEY (`id`));";

    String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS `user` (\n" +
            "                                                `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "                                                `email` VARCHAR(255) NOT NULL,\n" +
            "                                                `password` VARCHAR(64) NOT NULL,\n" +
            "                                                `first_name` VARCHAR(60) NOT NULL,\n" +
            "                                                `last_name` VARCHAR(60) NOT NULL,\n" +
            "                                                `phone` VARCHAR(25) NOT NULL,\n" +
            "                                                `birth_date` DATE NOT NULL,\n" +
            "                                                `role` VARCHAR(45) NOT NULL,\n" +
            "                                                `blocked` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
            "                                                UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,\n" +
            "                                                PRIMARY KEY (`id`));";

    String INSERT_TRAIN1 = "INSERT INTO `train` (`number`) VALUES ('17(Skovoroda Ekspress)');";
    String INSERT_TRAIN2 = "INSERT INTO `train` (`number`) VALUES ('NEW');";
    String INSERT_TRAIN3 = "INSERT INTO `train` (`number`) VALUES ('FAST');";

    String INSERT_CARRIAGE1 = "INSERT INTO `carriage` (`id`, `type`, `number`, `train_id`) VALUES ('1', 'FIRST_CLASS', '101', '3');";
    String INSERT_CARRIAGE2 = "INSERT INTO `carriage` (`id`, `type`, `number`, `train_id`) VALUES ('2', 'FIRST_CLASS', '102', '3');";
    String INSERT_CARRIAGE3 = "INSERT INTO `carriage` (`id`, `type`, `number`, `train_id`) VALUES ('3', 'FIRST_CLASS', '103', '3');";
    String INSERT_SEAT1 = "INSERT INTO `seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('1', '1055', '0', '1');";
    String INSERT_SEAT2 = "INSERT INTO `seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('2', '2055', '0', '2');";
    String INSERT_SEAT3 = "INSERT INTO `seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('3', '3055', '0', '3');";
    String INSERT_SEAT4 = "INSERT INTO `seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('4', '4055', '0', '2');";

    String INSERT_STATION1 = "INSERT INTO `station` (`station`) VALUES ('Zaporizhzhia');";
    String INSERT_STATION2 = "INSERT INTO `station` (`station`) VALUES ('Kyiv');";
    String INSERT_STATION3 = "INSERT INTO `station` (`station`) VALUES ('Lviv');";

    String INSERT_USER1 = "INSERT INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('1', 'admin@mail.com', 'admin', 'Тарас', 'Шевченко', '+380637872234', '1982-09-01', 'user', '0');";
    String INSERT_USER2 = "INSERT INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('2', 'user@mail.com', 'user', 'Петро', 'Алексеев', '+380627872234', '1984-09-01', 'user', '0');";
    String INSERT_USER3 = "INSERT INTO `user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('3', 'test@mail.com', 'test', 'Test', 'Test', '+380607872234', '1998-01-01', 'user', '1');";

}
