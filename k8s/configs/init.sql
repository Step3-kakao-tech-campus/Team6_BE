CREATE SCHEMA IF NOT EXISTS `tripko` DEFAULT CHARACTER SET utf8mb4;

/*
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_root_password by 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
*/


GRANT ALL ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;
GRANT ALL ON tripko.* TO 'root'@'localhost';
FLUSH PRIVILEGES;



USE tripko;

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
                                         `id` bigint not null AUTO_INCREMENT,
                                         `created_date` timestamp,
                                         `modified_date` timestamp,
                                         `building_name` varchar(100) not null,
    `road_name` varchar(255) not null,
    `zip_code` varchar(20) not null,
    `address_category_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `address_category`;
CREATE TABLE IF NOT EXISTS `address_category`  (
                                                   `id` bigint not null AUTO_INCREMENT,
                                                   `created_date` timestamp,
                                                   `modified_date` timestamp,
                                                   `emd_name` varchar(100) not null,
    `sido_name` varchar(100) not null,
    `sigg_name` varchar(100) not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `contents`;
CREATE TABLE IF NOT EXISTS `contents` (
                                          `id` bigint not null AUTO_INCREMENT,
                                          `created_date` timestamp,
                                          `modified_date` timestamp,
                                          `description` varchar(1000) not null,
    `page` bigint not null,
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `contents_has_file`;
CREATE TABLE IF NOT EXISTS `contents_has_file` (
                                                   `id` bigint not null AUTO_INCREMENT,
                                                   `created_date` timestamp,
                                                   `modified_date` timestamp,
                                                   `contents_id` bigint not null,
                                                   `file_id` bigint not null,
                                                   primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `contents_menu`;
CREATE TABLE IF NOT EXISTS `contents_menu` (
                                               `id` bigint not null AUTO_INCREMENT,
                                               `created_date` timestamp,
                                               `modified_date` timestamp,
                                               `characteristic` varchar(255),
    `description` varchar(1000),
    `name` varchar(100) not null,
    `price` bigint,
    `contents_id` bigint not null,
    `file_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `file`;
CREATE TABLE IF NOT EXISTS `file` (
                                      `id` bigint not null AUTO_INCREMENT,
                                      `created_date` timestamp,
                                      `modified_date` timestamp,
                                      `name` varchar(50) not null,
                                      `url` varchar(500) not null,
    `type` varchar(100) not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `food`;
CREATE TABLE IF NOT EXISTS `food` (
                                      `id` bigint not null AUTO_INCREMENT,
                                      `description` varchar(1000),
    `food_category` varchar(255),
    `keyword` varchar(1000) not null,
    `name` varchar(100) not null,
    `summary` varchar(1000) not null,
    `view` integer,
    `file_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `food_has_file`;
CREATE TABLE IF NOT EXISTS `food_has_file` (
                                               `id` bigint not null AUTO_INCREMENT,
                                               `file_id` bigint not null,
                                               `food_id` bigint not null,
                                               primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `food_has_place_restaurants`;
CREATE TABLE IF NOT EXISTS `food_has_place_restaurants` (
                                                            `id` bigint not null AUTO_INCREMENT,
                                                            `food_id` bigint not null,
                                                            `place_restaurant_id` bigint not null,
                                                            primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `food_ingredients`;
CREATE TABLE IF NOT EXISTS `food_ingredients` (
                                                  `food_id` bigint not null,
                                                  `ingredients_name` varchar(255)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
                                        `id` bigint not null AUTO_INCREMENT,
                                        `created_date` timestamp,
                                        `modified_date` timestamp,
                                        `birthday` varchar(20) not null,
    `email_address` varchar(100) not null,
    `member_id` varchar(25) not null,
    `nationality` varchar(50),
    `nick_name` varchar(25) not null,
    `password` varchar(100) not null,
    `real_name` varchar(35) not null,
    `role` varchar(100) not null,
    `file_id` bigint,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `member_reservation_info`;
CREATE TABLE IF NOT EXISTS `member_reservation_info` (
                                                         `id` bigint not null AUTO_INCREMENT,
                                                         `head_count` bigint not null,
                                                         `message` varchar(255),
    `reservation_date` varchar(100) not null,
    `reservation_time` varchar(100) not null,
    `status` varchar(100) not null,
    `member_id` bigint not null,
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `place`;
CREATE TABLE IF NOT EXISTS `place` (
                                       `id` bigint not null AUTO_INCREMENT,
                                       `created_date` timestamp,
                                       `modified_date` timestamp,
                                       `average_rating` float,
                                       `count` integer,
                                       `name` varchar(100) not null,
    `place_type` varchar(100),
    `review_numbers` integer not null,
    `summary` varchar(1000) not null,
    `address_id` bigint not null,
    `file_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `place_festival`;
CREATE TABLE IF NOT EXISTS `place_festival` (
                                                `id` bigint not null AUTO_INCREMENT,
                                                `created_date` timestamp,
                                                `modified_date` timestamp,
                                                `category` varchar(100),
    `price` integer not null,
    `end_date` varchar(100) not null,
    `reservation_available` boolean not null,
    `start_date` varchar(100) not null,
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `place_restaurant`;
CREATE TABLE IF NOT EXISTS `place_restaurant` (
                                                  `id` bigint not null AUTO_INCREMENT,
                                                  `created_date` timestamp,
                                                  `modified_date` timestamp,
                                                  `break_end_time` varchar(100),
    `break_start_time` varchar(100),
    `category` varchar(100),
    `closing_time` varchar(100),
    `contact_info` varchar(100),
    `holiday` integer,
    `opening_time` varchar(100),
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `place_tourist_spot`;
CREATE TABLE IF NOT EXISTS `place_tourist_spot` (
                                                    `id` bigint not null AUTO_INCREMENT,
                                                    `created_date` timestamp,
                                                    `modified_date` timestamp,
                                                    `category` varchar(100),
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review` (
                                        `id` bigint not null AUTO_INCREMENT,
                                        `description` varchar(255),
    `is_available` boolean not null,
    `score` integer not null,
    `type` varchar(255) not null,
    `usage_date` varchar(255) not null,
    `member_id` bigint not null,
    `place_id` bigint not null,
    primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `review_has_file`;
CREATE TABLE IF NOT EXISTS `review_has_file` (
                                                 `id` bigint not null AUTO_INCREMENT,
                                                 `file_id` bigint not null,
                                                 `review_id` bigint not null,
                                                 primary key (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- cocamome mangmi
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('1', '0', '325', 'cocamome Mangmi', 'RESTAURANT', '0',
        'It is a restaurant specializing in tempura rice bowls located in mangmi. The restaurant is popular among customers of all ages for their satisfying meals. All ingredients are sourced domestically and exhausted on the same day.',
        '1', '1');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('1', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/CocamomeMangmi/main.png', 'main.png','image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('1', '14', 'Mangmibaesan-ro 10beon-gil', '48210', '1');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('1', 'Mangmi-dong', 'Suyeong-gu', 'Busan');

INSERT INTO place_restaurant(`id`, `break_end_time`, `break_start_time`, `closing_time`, `contact_info`, `holiday`, `opening_time`, `place_id`)
VALUES ('1', '15:00', '17:30', '20:30', '+82 051-711-1754', '6', '11:00', '1');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('1',
        'Located in Mangmi-dong, Suyeong-gu, Busan, Cocamome, which means "little seagull," specializes in tendon. Unlike traditional Japanese-style tendon, which has a fluffy texture, this restaurant fries the tendon as crispy as possible to suit Korean tastes. The menu is divided into Cocamome, ebi, and special, depending on the composition of the tempura, and the refreshing Cocamome tomato cuts through the overpowering flavor of the tempura.'
           , '1', '1');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('1', '1', '2');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('2', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/CocamomeMangmi/CocamomeContents1.png', 'CocamomeContents1.png','image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('1', 'Cocamome Tendon', 'Cocamome signature menu', 'This signature dish features shrimp, eggplant, mushrooms, and pumpkin.', '11000', '1', '3');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('3', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/CocamomeMangmi/CocamomeTendon.png', 'CocamomeTendon.png','image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('2', 'Ebi Tendon', 'Menu with a lot of fried shrimp', 'Added 2 fried shrimp at Cocamome Tendon', '13000', '1', '4');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('4', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/CocamomeMangmi/EbiTendon.png', 'EbiTendon.png','image/png');

-- lee jaemo pizza
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('2', '0', '214', 'lee Jaemo Pizza', 'RESTAURANT','0',
        'This is a 30-year-old pizzeria, which has a very long tradition considering when pizza actually became popular in Busan. Characterized by generous toppings and Imsil cheese, the pizza is as tasty as ever.',
        '2', '5');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('5', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/LeeJaemoPizza/main.png', 'main.png','image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('2', '21', 'Jeonpo-daero 209beon-gil', '47294', '2');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('2', 'Jeonpo-dong', 'Busanjin-gu', 'Busan');

INSERT INTO place_restaurant(`id`, `closing_time`, `contact_info`, `holiday`, `opening_time`, `place_id`)
VALUES ('2', '21:30', '+82 051-809-1478', '6', '11:00', '2');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('2',
        'Lee Jaemo Pizza, a local favorite in Jeonpo that always has a wait.  The thick, crispy dough and the use of Imsil natural cheese give it a rich flavor. There are robots that serve it, making it a unique experience.'
           , '1', '2');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('2', '2', '6');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('6', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/LeeJaemoPizza/content1.png', 'content1.png','image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('3', 'Bulgogi Pizza', 'Lee Jaemo Pizza signature menu', 'Bulgogi pizza can only be found in Korea! It''s topped with bulgogi, a Korean beef dish.', '24000', '2', '7');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('7', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/LeeJaemoPizza/BulgogiPizza.png', 'BulgogiPizza.png','image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('4', 'Lee Jaemo Crust Pizza', 'A menu with lots of cheese for rich flavor', 'This one has cheese at the edge of the dough, so you can taste the cheese all the way to the end.', '25000', '2', '8');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('8', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/LeeJaemoPizza/CrustPizza.png', 'CrustPizza.png','image/png');

-- WildCatBrewing
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('3', '0', '523', 'Wildcat Brewing', 'RESTAURANT','0',
        'Wildcat Brewing is a brewpub with GUEST TAPs from 20+ renowned international & domestic craft breweries.',
        '3', '9');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('9', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/WildcatBrewing/main.png', 'main.png', 'image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('3', '1F 101ho, 2', 'Sincheon-daero 62beon-gil', '47233', '3');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('3', 'Bujeon-dong', 'Busanjin-gu', 'Busan');

INSERT INTO place_restaurant(`id`, `closing_time`, `contact_info`, `opening_time`, `place_id`)
VALUES ('3', '24:00', '+82 051-809-4321', '17:00', '3');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('3',
        'Amid the salty breezes and amber sunsets of California, I, a Korean American, discovered my love for craft beer—a taste that evoked stories and dreams. This Golden State taught me that brewing wasn’t just an act, but a lifestyle; an art that mirrored the soul of its creator. Driven by a longing to merge my dual identities, I took this West Coast ethos to Korea. My brewery now stands as a testament to this blend, an embodiment of Californian sunsets and Korean traditions, where every sip is a journey between two worlds.'
           , '1', '3');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('3', '3', '10');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('10', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/WildcatBrewing/content1.png', 'content1.png','image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('5', 'Bliss IPA', 'A signature west-coast IPA with grapefruit, floral, and citrus notes.', 'Both American and West Coast IPAs are hop-forward in character, with the West Coast variety being especially so. They often have brilliant clarity and are dry with minimal malt character. Being highly hopped, West Coast / American IPAs are typically very bitter, between 40-70 IBUs.', '8000', '3', '11');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('11', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/WildcatBrewing/BlissIPA.png', 'BlissIPA.png','image/png');

-- Eomyongbaek dwaejigukbap
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('4', '0', '241', 'Eomyongbaek dwaejigukbap', 'RESTAURANT','0',
        'Eomyongbaek dwaejigukbap(pork soup), which has a light flavor.',
        '4', '12');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('12', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/eybDwajigukbap/main.png', 'main.png','image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('4', '39', 'Suyeong-ro 680beon-gil', '48266', '4');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('4', 'Gwangan-dong', 'Suyeong-gu', 'Busan');

INSERT INTO place_restaurant(`id`, `closing_time`, `contact_info`, `opening_time`, `place_id`)
VALUES ('4', '21:00', '+82 051-809-4142', '11:00', '4');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('4',
        'Unlike the traditional Busan dwaejigukbap restaurants, this is a casual dwaejigukbap that you can enjoy.'
           , '1', '4');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('4', '4', '13');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('13', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/eybDwajigukbap/content1.png', 'content1.png', 'image/png');

INSERT INTO contents_menu (`id`, `name`, `characteristic`, `description`, `price`, `contents_id`, `file_id`)
VALUES ('6', 'Busan Style Dwajigukbap', 'Five different cuts of meat are topped with a clear broth of lean meat.', 'It tastes even better with kimchi and somen.', '12000', '4', '14');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('14', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/restaurant/eybDwajigukbap/menu1.png', 'menu1.png','image/png');

-- Busan Fireworks Festival
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('5', '0', '1232', 'Busan Fireworks Festival', 'FESTIVAL','0',
        'Autumn in Busan blooms with colorful lights. It’s the time for the Busan Fireworks Festival!',
        '5', '15');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('15', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanFirework/main.png', 'main.png','image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('5', '219', 'Gwanganhaebyeon-ro', '48303', '4');

INSERT INTO place_festival(`id`/*, `category`*/, `start_date`, `reservation_available`, `end_date`, `price`, `place_id`)
VALUES ('1'/*, 'Beach'*/, 'November 4, 2023', '0', 'November 4, 2023', '80000', '5');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('5',
        'The shining Gwangandaegyo Bridge begins coming into view. As the Gwangan Bridge gets closer, it becomes so crowded you can’t even find a gap to set your foot in. However, the people seem bright like the fireworks they’re waiting for, and you enjoy feeling the chilly sea breeze on this pleasant autumn night.'
           , '1', '5');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('5', '5', '16');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('16', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanFirework/content1.png', 'content1.png', 'image/png');

-- Busan International Rock Festival
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('6', '0', '843', 'Busan International Rock Festival', 'FESTIVAL','0',
        'In the middle of summer, when it’s scorching hot, more and more people gather in a quiet and spacious park called Samnak Eco Park.',
        '6', '17');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('17', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanRock/main.png', 'main.png', 'image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('6', '29-46', ' ', '41232', '5');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('5', 'Samnak-dong', 'Sasang-gu', 'Busan');

INSERT INTO place_festival(`id`/*, `category`*/, `start_date`, `reservation_available`, `end_date`, `price`, `place_id`)
VALUES ('2'/*, 'Music'*/, 'October 7, 2023', '0', 'October 8, 2023', '70000', '6');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('6',
        'The Busan International Rock Festival, which began in 2000, has been recognized as the oldest rock music festival in South Korea. Indie rock bands from all over the country, including Busan and top-notch international and local music bands, participate in the festival. As a result, local and international rock enthusiasts look forward to this event. Why not come and join the two-day music festival? As soon as you enter the extensive, green festival venue of the park, you’ll begin your fantastic music tour.'
           , '1', '6');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('6', '6', '17');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('18', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanRock/content1.png', 'content1.png','image/png');

-- Haeundae Lighting Festival
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('7', '0', '613', 'Haeundae Lighting Festival', 'FESTIVAL','0',
        'The lighting festival transforms the cold winter street into a romantic world that even seems to warm up the cold season.',
        '7', '19');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('19', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanLighting/main.png', 'main.png', 'image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('7', '280', 'Haeundaehaebyeon-ro', '48099', '6');

INSERT INTO address_category (`id`, `emd_name`, `sigg_name`, `sido_name`)
VALUES ('6', 'Jung-dong', 'Haeundae-gu', 'Busan');

INSERT INTO place_festival(`id`/*, `category`*/, `start_date`, `reservation_available`, `end_date`, `price`, `place_id`)
VALUES ('3'/*, 'Beach'*/, 'December 7, 2023', '0', 'January 31, 2024', '0', '7');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('7',
        'e dark night sea of Haeundae, in contrast with the sea of lights, makes the scene more phenomenal. In addition to the sparkling illuminations on the streets, the smile on people’s faces further brightens up Haeundae Lighting Festival.'
           , '1', '7');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('7', '7', '20');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('20', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/festival/BusanLighting/content1.png', 'content1.png', 'image/png');

-- F1963
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('8', '0', '732', 'F1963', 'TOURIST_SPOT','0',
        'An urban space where nature, arts, and culture blend',
        '8', '21');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('21', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/spot/F1963/main.png', 'main.png', 'image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('8', '20', 'Gurak-ro 123beon-gil', '48212', '1');

INSERT INTO place_tourist_spot(`id`/*, `category`*/, `place_id`)
VALUES ('1'/*, 'CultureComplex'*/, '8');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('8',
        'The name F1963 combines the first letter from the word “factory” and the year “1963” when the first factory of Kiswire was built. The company’s wire factory, operated until 2008, was transferred, and the empty site transformed into a complex culture space.'
           , '1', '8');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('8', '8', '22');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('22', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/spot/F1963/content1.png', 'content1.png', 'image/png');

-- Millak Waterside Park
INSERT INTO place (`id`,`average_rating`,`count`,`name`,`place_type`, `review_numbers`,`summary`,`address_id`,`file_id`)
VALUES ('9', '0', '634', 'Millak Waterside Park', 'TOURIST_SPOT','0',
        'Night Sea of Gwangalli, a tourist hot place',
        '9', '23');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('23', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/spot/MillakPark/main.png', 'main.png','image/png');

INSERT INTO address (`id`, `building_name`, `road_name`, `zip_code`, `address_category_id`)
VALUES ('9', '361', 'Gwanganhaebyeon-ro', '48212', '4');

INSERT INTO place_tourist_spot(`id`/*, `category`*/, `place_id`)
VALUES ('2'/*, 'Beach'*/, '9');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('9',
        'The first place to catch the sea breeze.
The closest place to encounter the spectacular night view of the Marine City and Gwangandaegyo Bridge.
Millak Waterside Park, a popular summer retreat for the public.
Opened in 1997, the Millak Waterside Park has been loved by Busan’s citizens since even before the establishment of the Marine City and Gwangandaegyo Bridge. With an open view of the endless sea and blue skies, the park has served as a relaxation space for everyone while the surrounding environment has kept changing with the times.'
           , '1', '9');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('9', '9', '24');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('24', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/spot/MillakPark/content1.png', 'content1.png', 'image/png');

INSERT INTO contents (`id`, `description`, `page`, `place_id`)
VALUES ('10',
        'The Millak Waterside Park boasts the food town as well as various convenient facilities. Visitors can enjoy a relaxing time with a coffee at a café against the backdrop of the sea or taste fresh seafood in the food stall town. Some people like to buy packed raw fish and seafood from the nearby fresh fish town or the raw fish market and then enjoy it at the park while having a romantic, memorable time with their friends at the seafront.'
           , '2', '9');

INSERT INTO contents_has_file (`id`, `contents_id`, `file_id`)
VALUES ('10', '10', '25');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('25', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/spot/MillakPark/content2.png', 'content2.png', 'image/png');

-- bulgogi
INSERT INTO food (`id`,`summary`,`food_category`,`keyword`,`name`, `description`,`view`,`file_id`)
VALUES ('1', 'Bulgogi is a made of thin, marinated slices of meat, most commonly beef, grilled on a barbecue or on a stove-top griddle',
        'KOREAN', 'KoreanGrilledBeef KoreanMarinatedBeef Yakiniku', 'Bulgogi','Bulgogi is a delicious Korean dish made with thinly sliced marinated beef, typically grilled or stir-fried. The marinade usually consists of soy sauce, sugar, sesame oil, garlic, and pepper, giving the beef a sweet and savory flavor.',
        '23', '26');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('26', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/food/Bulgogi.png', 'Bulgogi.png', 'image/png');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'Beef');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'Pork');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'Soy Sauce');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'GochuJang(Korean red chili paste)');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'Green Onion');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'cellophane noodles');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('1', 'cellophane noodles');

INSERT INTO food_has_place_restaurants (`id`, `food_id`, `place_restaurant_id`)
VALUES ('1', '1', '2');

INSERT INTO food_has_file (`id`, `food_id`, `file_id`)
VALUES ('1', '1', '27');

INSERT INTO food_has_file (`id`, `food_id`, `file_id`)
VALUES ('2', '1', '28');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('27', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/food/Bulgogi1.png', 'Bulgogi1.png', 'image/png');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('28', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/food/Bulgogi2.png', 'Bulgogi2.png', 'image/png');

-- Dwaejigukbap
INSERT INTO food (`id`,`summary`,`food_category`,`keyword`,`name`, `description`,`view`,`file_id`)
VALUES ('2', 'Dwaeji gukbap is a South Korean soup especially popular in Busan made with pork, soy sauce, miso, rice wine, sesame oil, and bone broth.',
        'KOREAN', 'KoreanPorkSoup BusanStylePorkSoup PorkAndRiceSoup', 'Dwaji gukbap(Pork Soup)','It originated during the Korean War in the 1950s as poverty food. It eventually grew in popularity. The dish spread from Busan to the rest of the Gyeongsang province and eventually the rest of the country. It is served various with sauces, garnishes, and side dishes. Variations exist in the exact preparation. It can be served with rice either already in the soup or on the side, and it can also be served with noodles. Other accompaniments like green onions, fermented shrimp, red pepper paste, white onions, garlic, and different types of kimchi, can also be added.',
        '63', '27');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('29', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/food/dwaejigukbap.png', 'dwaejigukbap.png', 'image/png');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('2', 'Pork');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('2', 'Salted Shrimp');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('2', 'noodle');

INSERT INTO food_ingredients (`food_id`, `ingredients_name`)
VALUES ('2', 'spice Seasoning');

INSERT INTO food_has_place_restaurants (`id`, `food_id`, `place_restaurant_id`)
VALUES ('2', '2', '4');

INSERT INTO food_has_file (`id`, `food_id`, `file_id`)
VALUES ('3', '2', '30');

INSERT INTO file (`id`, `url`, `name`, `type`)
VALUES ('30', 'https://tripko-be6.s3.ap-northeast-2.amazonaws.com/contents/food/dwaejigukbap.png', 'dwaejigukbap.png', 'image/png');


