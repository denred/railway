
/*  Insert Users */
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('1', 'admin@mail.com', 'admin', 'Тарас', 'Шевченко', '+380637872234', '1982-09-01', 'admin', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('2', 'user@mail.com', 'user', 'Serhey', 'Petrenko', '+30501238845', '2001-01-22', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('3', 'user1@mail.com', 'user1', 'Павел', 'Старченко', '+30661228897', '1993-02-12', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('4', 'semen@i.ua', 'semen', 'Владимир', 'Струй', '+30671818234', '1976-03-01', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('5', 'star@gmail.com', 'star', 'Stas', 'Potapov', '+30441237777', '1987-05-11', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('6', 'fill@mail.com', 'fill', 'Евгений', 'Новый', '+30562332233', '1976-08-10', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('7', 'yasno@yasno.ua', 'yasno', 'Владимир', 'Старый', '+38077334455', '1976-08-10', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('8', 'kost@mail.com', 'kost', 'Константин', 'Сидоренко', '+380663332211', '1976-08-10', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('9', 'mailer@i.ua', 'mailer', 'Ольга', 'Бест', '+38055887736', '1976-08-10', 'user', '0');
INSERT INTO `railway`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `birth_date`, `role`, `blocked`) VALUES ('10', 'foo@mail.ua', 'foo', 'Игорь', 'Кондратюк', '+38022222222', '1976-08-10', 'user', '1');

/* Insert trains */
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('1', '17(Skovoroda Ekspress)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('2', '772(Podilsky Ekspress)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('3', '53(Skifia)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('4', '731(Intercity)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('5', '284');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('6', '79(Dnipro)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('7', '131(Oberih)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('8', '41');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('9', '91');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('10', '65');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('11', '781');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('12', '704');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('13', '710');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('14', '258');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('15', '150');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('16', '8');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('17', '60');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('18', '43(Stefania Ekspress)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('19', '257');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('20', '1');

/* Insert Routes */
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('1', 'Dnipro-Odessa', '1', '3');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('2', 'Ivano-Frankivsk Kharkiv', '2', '20');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('3', 'Ivano-Frankivsk Kyiv', '3', '18');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('4', 'Chernihiv Kyiv', '4', '13');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('5', 'Kharkiv Uzhhorod', '5', '1');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('6', 'Kyiv Kharkiv', '6', '2');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('7', 'Dnipro Kyiv', '7', '4');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('8', 'Dnipro Pokrovsk', '8', '5');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('9', 'Dnipro Kyiv', '9', '6');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('10', 'Dnipro Lviv', '10', '7');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('11', 'Dnipro Truskavets', '11', '8');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('12', 'Kyiv Lviv', '12', '9');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('13', 'Uman Kharkiv', '13', '10');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('14', 'Cherkasy Kyiv', '14', '11');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('15', 'Chernihiv Kyiv', '15', '12');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('16', 'Chernihiv Ivano-Frankivsk', '16', '14');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('17', 'Chernivtsi Poltava', '17', '15');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('18', 'Odesa Kharkiv', '18', '16');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('19', 'Chop-Pas. Kyiv', '19', '17');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('20', 'Ivano-Frankivsk Chernihiv', '20', '19');


/* STATION */
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('1', 'Dnipro');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('2', 'Kam\'yans\'ke');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('3', 'Piatykhatky');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('4', 'Oleksandriia');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('5', 'Znamianka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('6', 'Im.tarasa Shevchenka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('7', 'Kyiv');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('8', 'Zaporizhzhia');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('9', 'Odesa');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('10', 'Verkhivtseve');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('11', 'Kropyvnyts\'kyi');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('12', 'Novoukrainka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('13', 'Pomichna');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('14', 'Pervomaisk');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('15', 'Liubashivka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('16', 'Balta');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('17', 'Podilsk');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('18', 'Rozdilna I');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('19', 'Zhytomyr');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('20', 'Korosten');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('21', 'Bilokorovychi');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('22', 'Olevs\'k');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('23', 'Ostky');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('24', 'Rokytne-Volynske');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('25', 'Tomashhorod');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('26', 'Klesiv');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('27', 'Sarny');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('28', 'Antonivka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('29', 'Rafalivka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('30', 'Varash');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('31', 'Chortoryisk');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('32', 'Manevychi');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('33', 'Kovel');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('34', 'Turiis\'k');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('35', 'Volodymyr-Volynskyi');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('36', 'Ivanychi');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('37', 'Sokal');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('38', 'Chervonohrad');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('39', 'Sosnivka');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('40', 'Pidzamche');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('41', 'Lviv');





/* ROUTE HAS STATION */
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('1', '7', '2022-12-06 15:00:00', '2022-12-06 15:20:00', '1');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('2', '7', '2022-12-06 15:00:00', '2022-12-06 15:48:00', '2');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('3', '7', '2022-12-06 16:41:00', '2022-12-06 16:42:00', '3');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('4', '7', '2022-12-06 17:25:00', '2022-12-06 17:26:00', '4');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('5', '7', '2022-12-06 17:59:00', '2022-12-06 18:00:00', '5');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('6', '7', '2022-12-06 19:01:00', '2022-12-06 19:02:00', '6');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, `pnr`) VALUES ('7', '7', '2022-12-06 21:23:00', '2022-12-06 21:38:00', '7');


/* CARRIAGE */
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('1', 'FIRST_CLASS', '01', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('2', 'SECOND_CLASS', '02', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('3', 'SECOND_CLASS', '03', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('4', 'SECOND_CLASS', '04', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('5', 'SECOND_CLASS', '05', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('6', 'SECOND_CLASS', '06', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('7', 'FIRST_CLASS', '07', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('8', 'FIRST_CLASS', '08', '4');

/* SEATS */
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('1', '81', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('2', '82', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('3', '83', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('4', '84', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('5', '85', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('6', '86', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('7', '71', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('8', '72', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('9', '73', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('10', '76', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('11', '61', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('12', '62', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('13', '63', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('14', '64', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('15', '65', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('16', '66', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('17', '51', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('18', '52', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('19', '53', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('20', '54', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('21', '55', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('22', '56', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('23', '42', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('24', '41', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('25', '43', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('26', '44', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('27', '45', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('28', '46', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('29', '31', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('30', '32', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('31', '33', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('32', '34', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('33', '35', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('34', '36', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('35', '21', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('36', '22', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('37', '23', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('38', '24', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('39', '25', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('40', '26', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('41', '16', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('42', '12', '1', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('43', '13', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('44', '14', '0', '1');
INSERT INTO `railway`.`seat` (`id`, `seat_number`, `busy`, `carriage_id`) VALUES ('45', '15', '0', '1');


