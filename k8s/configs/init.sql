CREATE SCHEMA IF NOT EXISTS `tripko` DEFAULT CHARACTER SET utf8mb4;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_root_password by `root`;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;


/*
GRANT ALL ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;
GRANT ALL ON tripko.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
*/

USE `tripko`;

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
                                      `name` varchar(500) not null,
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


