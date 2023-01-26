
/*  Insert Users */


/* Insert trains */
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('1', '17(СКОВОРОДА ЕКСПРЕС)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('2', '772(ПОДІЛЬСЬКІЙ ЕКСПРЕС)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('3', '53(СКІФІЯ)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('4', '731(ІНТЕРСІТІ+)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('5', '284');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('6', '79(ДНІПРО)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('7', '131(ОБЕРІГ)');
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
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('18', '43(СТЕФАНІЯ ЕКСПРЕС)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('19', '257');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('20', '1');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('21', '259');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('22', '21');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('23', '745(ІНТЕРСІТІ+)');
INSERT INTO `railway`.`train` (`id`, `number`) VALUES ('24', '743(ІНТЕРСІТІ+)');

/* Insert Routes */
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('1', 'Дніпро-Одеса', '1', '3');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('2', 'Івано-Франківськ-Харків', '2', '20');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('3', 'Івано-Франківськ-Київ', '3', '18');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('4', 'Чернігів-Харків', '4', '13');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('5', 'Харків-Ужгород', '5', '1');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('6', 'Київ-Харків', '6', '2');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('7', 'Дніпро-Київ', '7', '4');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('8', 'Дніпро-Покровськ', '8', '5');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('9', 'Дніпро-Київ', '9', '6');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('10', 'Дніпро-Львів', '10', '7');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('11', 'Дніпро-Трускавець', '11', '8');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('12', 'Київ-Львів', '12', '9');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('13', 'Умань-Харків', '13', '10');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('14', 'Черкаси-Київ', '14', '11');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('15', 'Чернігів-Київ', '15', '12');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('16', 'Чернігів-Івано-Франківськ', '16', '14');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('17', 'Чернівці-Полтава', '17', '15');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('18', 'Одеса-Харків', '18', '16');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('19', 'Чоп-Київ', '19', '17');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('20', 'Івано-Франківськ-Чернігів', '20', '19');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('21', 'Київ-Славське', '21', '23');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('22', 'Київ-Львів', '22', '24');
INSERT INTO `railway`.`route` (`id`, `name`, `number`, `train_id`) VALUES ('24', 'Дніпро-Львів', '24', '7');


/* STATION */
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('1', 'Дніпро');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('2', 'Камʼянське');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('3', 'Пʼятихатки');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('4', 'Олександрія');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('5', 'Знамʼянка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('6', 'Ім.Тараса Шевченка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('7', 'Київ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('8', 'Запоріжжя');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('9', 'Одеса');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('10', 'Верхівцеве');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('11', 'Кропивницький');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('12', 'Новоукраїнка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('13', 'Помічна');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('14', 'Первомайськ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('15', 'Любашівка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('16', 'Балта');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('17', 'Подільськ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('18', 'Роздільна');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('19', 'Житомир');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('20', 'Коростень');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('21', 'Білокоровичі');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('22', 'Олевськ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('23', 'Остки');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('24', 'Рокитне-Волинське');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('25', 'Томашгород');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('26', 'Клесів');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('27', 'Сарни');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('28', 'Антонівка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('29', 'Рафалівка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('30', 'Вараш');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('31', 'Чорторийськ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('32', 'Маневічі');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('33', 'Ковель');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('34', 'Турійськ');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('35', 'Володимир');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('36', 'Іваничі');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('37', 'Сокаль');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('38', 'Червоноград');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('39', 'Соснівка');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('40', 'Підзамче');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('41', 'Львів');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('42', 'Святошин');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('44', 'Дубно');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('45', 'Стрий');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('46', 'Сколе');
INSERT INTO `railway`.`station` (`id`, `station`) VALUES ('47', 'Славське');




/* ROUTE HAS STATION */
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('1', '7', '2023-02-06 15:00:00', '2023-02-06 15:20:00', '1');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('2', '7', '2023-02-06 15:00:00', '2023-02-06 15:48:00', '2');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('3', '7', '2023-02-06 16:41:00', '2023-02-06 16:42:00', '3');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('4', '7', '2023-02-06 17:25:00', '2023-02-06 17:26:00', '4');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('5', '7', '2023-02-06 17:59:00', '2023-02-06 18:00:00', '5');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('6', '7', '2023-02-06 19:01:00', '2023-02-06 19:02:00', '6');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('7', '7', '2023-02-06 21:23:00', '2023-02-06 21:38:00', '7');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('7', '21', '2023-02-06 05:29:00', '2023-02-06 05:59:00', '1');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('42', '21', '2023-02-06 06:15:00', '2023-02-06 06:16:00', '2');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('20', '21', '2023-02-06 07:37:00', '2023-02-06 07:38:00', '3');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('44', '21', '2023-02-06 10:10:00', '2023-02-06 10:11:00', '4');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('40', '21', '2023-02-06 11:31:00', '2023-02-06 11:32:00', '5');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('41', '21', '2023-02-06 11:43:00', '2023-02-06 11:53:00', '6');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('45', '21', '2023-02-06 12:53:00', '2023-02-06 12:55:00', '7');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('46', '21', '2023-02-06 13:24:00', '2023-02-06 13:26:00', '8');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('47', '21', '2023-02-06 13:55:00', '2023-02-06 14:00:00', '9');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('7', '22', '2023-02-06 06:00:00', '2023-02-06 06:20:00', '1');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('42', '22', '2023-02-06 06:36:00', '2023-02-06 06:37:00', '2');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('20', '22', '2023-02-06 08:08:00', '2023-02-06 08:09:00', '3');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('44', '22', '2023-02-06 10:31:00', '2023-02-06 10:32:00', '4');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('41', '22', '2023-02-06 11:58:00', '2023-02-06 12:08:00', '5');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('1', '24', '2023-02-06 06:30:00', '2023-02-06 07:02:00', '1');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('2', '24', '2023-02-06 07:34:00', '2023-02-06 07:36:00', '2');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('4', '24', '2023-02-06 10:03:00', '2023-02-06 10:05:00', '3');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('5', '24', '2023-02-06 10:52:00', '2023-02-06 10:57:00', '4');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('6', '24', '2023-02-06 12:22:00', '2023-02-06 12:27:00', '5');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('7', '24', '2023-02-06 15:40:00', '2023-02-06 16:00:00', '6');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('19', '24', '2023-02-06 18:27:00', '2023-02-06 18:47:00', '7');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('20', '24', '2023-02-06 20:35:00', '2023-02-06 20:55:00', '8');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('21', '24', '2023-02-06 21:46:00', '2023-02-06 21:48:00', '9');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('22', '24', '2023-02-06 22:31:00', '2023-02-06 22:33:00', '10');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('23', '24', '2023-02-06 22:56:00', '2023-02-06 22:58:00', '11');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('24', '24', '2023-02-06 23:12:00', '2023-02-06 23:14:00', '12');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('25', '24', '2023-02-06 23:26:00', '2023-02-06 23:28:00', '13');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('26', '24', '2023-02-06 23:42:00', '2023-02-06 23:44:00', '14');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('27', '24', '2023-02-07 00:10:00', '2023-02-07 00:20:00', '15');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('28', '24', '2023-02-07 00:48:00', '2023-02-07 00:50:00', '16');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('29', '24', '2023-02-07 01:12:00', '2023-02-07 01:14:00', '17');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('30', '24', '2023-02-07 01:20:00', '2023-02-07 01:21:00', '18');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('31', '24', '2023-02-07 01:27:00', '2023-02-07 01:29:00', '19');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('32', '24', '2023-02-07 01:49:00', '2023-02-07 01:51:00', '20');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('33', '24', '2023-02-07 02:30:00', '2023-02-07 02:40:00', '21');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('34', '24', '2023-02-07 03:24:00', '2023-02-07 03:26:00', '22');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('35', '24', '2023-02-07 03:55:00', '2023-02-07 03:58:00', '23');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('36', '24', '2023-02-07 04:24:00', '2023-02-07 04:27:00', '24');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('37', '24', '2023-02-07 04:52:00', '2023-02-07 04:55:00', '25');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('38', '24', '2023-02-07 05:05:00', '2023-02-07 05:08:00', '26');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('39', '24', '2023-02-07 05:38:00', '2023-02-07 05:40:00', '27');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('40', '24', '2023-02-07 06:50:00', '2023-02-07 06:52:00', '28');
INSERT INTO `railway`.`station_has_route` (`station_id`, `route_id`, `station_arrival`, `station_dispatch`, station_order) VALUES ('41', '24', '2023-02-07 07:04:00', '2023-02-07 07:52:00', '29');




/* CARRIAGE */
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('1', 'FIRST_CLASS', '01', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('2', 'SECOND_CLASS', '02', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('3', 'SECOND_CLASS', '03', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('4', 'SECOND_CLASS', '04', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('5', 'SECOND_CLASS', '05', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('6', 'SECOND_CLASS', '06', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('7', 'FIRST_CLASS', '07', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('8', 'FIRST_CLASS', '08', '4');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('9', 'COMPARTMENT', '01', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('10', 'COMPARTMENT', '02', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('11', 'COMPARTMENT', '06', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('12', 'COMPARTMENT', '08', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('13', 'COMPARTMENT', '11', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('14', 'BERTH', '12', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('15', 'BERTH', '13', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('16', 'BERTH', '03', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('17', 'BERTH', '04', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('18', 'BERTH', '09', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('19', 'BERTH', '14', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('20', 'DE_LUXE', '07', '7');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('21', 'FIRST_CLASS', '01', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('22', 'FIRST_CLASS', '02', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('23', 'FIRST_CLASS', '03', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('24', 'FIRST_CLASS', '04', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('25', 'SECOND_CLASS', '05', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('26', 'SECOND_CLASS', '06', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('27', 'SECOND_CLASS', '07', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('28', 'SECOND_CLASS', '08', '23');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('29', 'SECOND_CLASS', '01', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('30', 'SECOND_CLASS', '02', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('31', 'SECOND_CLASS', '03', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('32', 'SECOND_CLASS', '04', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('33', 'FIRST_CLASS', '05', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('34', 'FIRST_CLASS', '06', '24');
INSERT INTO `railway`.`carriage` (`id`, `type`, `number`, `train_id`) VALUES ('35', 'FIRST_CLASS', '07', '24');

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


