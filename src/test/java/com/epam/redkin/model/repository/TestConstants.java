package com.epam.redkin.model.repository;

public interface TestConstants {
    String REMOVE_TRAIN_TABLE = "DROP TABLE IF EXISTS `train`";
    String REMOVE_CARRIAGE_TABLE = "DROP TABLE IF EXISTS `carriage`";

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

    String INSERT_TRAIN1 = "INSERT INTO `train` (`number`) VALUES ('17(Skovoroda Ekspress)');";
    String INSERT_TRAIN2 = "INSERT INTO `train` (`number`) VALUES ('NEW');";
    String INSERT_TRAIN3 = "INSERT INTO `train` (`number`) VALUES ('FAST');";
}
