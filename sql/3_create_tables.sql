CREATE TABLE `users` (
                         `id_user` INTEGER NOT NULL AUTO_INCREMENT,
                         `login` VARCHAR(20) NOT NULL UNIQUE,
                         `password` NCHAR(32) NOT NULL,
                         `is_active` BOOL NOT NULL,
    /*
     * 0 - administrator (Role.ADMINISTRATOR)
     * 1 - client (Role.CLIENT)
     */
                         `role` TINYINT NOT NULL CHECK (`role` IN (0, 1)),
                          CONSTRAINT PK_users PRIMARY KEY  (`id_user`)
);
CREATE TABLE `cities` (
                             `id_city` INTEGER NOT NULL AUTO_INCREMENT,
                             `name` VARCHAR(60),
                             CONSTRAINT PK_city PRIMARY KEY(id_city)
);

CREATE TABLE `clients` (
                             `user_id` INTEGER NOT NULL,
                             `email` VARCHAR(255) NOT NULL,
                             `first_name` VARCHAR(25),
                             `last_name` VARCHAR(25),
                             `phone` INT UNSIGNED,
                             `city_id` INT,
                             CONSTRAINT UN_clients_email UNIQUE(email),
                             CONSTRAINT PK_clients PRIMARY KEY (`user_id`),
                             CONSTRAINT FK_clients_user FOREIGN KEY (`user_id`)
                                 REFERENCES `users` (`id_user`)
                                 ON DELETE RESTRICT,
                             CONSTRAINT FK_clients_country FOREIGN KEY (`city_id`)
                                 REFERENCES `cities` (`id_city`)
                                 ON DELETE RESTRICT

);
CREATE TABLE `category` (
                             `id_category` INTEGER NOT NULL AUTO_INCREMENT,
                             `name` VARCHAR(100) NOT NULL,
                             CONSTRAINT PK_topics PRIMARY KEY (`id_category`)
);
CREATE TABLE `advertisements` (
                          `id_advertisement` INTEGER NOT NULL AUTO_INCREMENT,
                          `user_id` INT NOT NULL ,
                          `category_id` INT NOT NULL,
                          `title` VARCHAR(50) NULL,
                          `text` VARCHAR(500),
                          `date` DATETIME,
                          `expiry` DATETIME,
                          `is_active` BOOL NOT NULL,
                          CONSTRAINT PK_advertisements PRIMARY KEY (id_advertisement),
                          CONSTRAINT FK_advertisements_user FOREIGN KEY (`user_id`)
                              REFERENCES `users` (`id_user`)
                              ON DELETE RESTRICT,
                          CONSTRAINT FK_advertisements_topic FOREIGN KEY (`category_id`)
                              REFERENCES `category` (`id_category`)
                              ON DELETE RESTRICT
);
CREATE TABLE `likes` (
                          `advertisement_id` INT,
                          `user_id` INT,
                          CONSTRAINT PK_likes PRIMARY KEY(advertisement_id, user_id),
                          CONSTRAINT FK_likes_user FOREIGN KEY (user_id)
                              REFERENCES `users` (`id_user`)
                              ON DELETE RESTRICT,
                          CONSTRAINT FK_likes_advertisements FOREIGN KEY (advertisement_id)
                              REFERENCES `advertisements` (`id_advertisement`)
                              ON DELETE CASCADE
);
CREATE TABLE `messages` (
                          `id_message` INTEGER NOT NULL AUTO_INCREMENT,
                          #`advertisement_id` INT,
                          `user_id_from` INT,
                          `user_id_to` INT,
                          `message` VARCHAR(500),
                          `time` DATETIME,
                          CONSTRAINT PK_messages PRIMARY KEY(id_message),
                          CONSTRAINT FK_messages_user_from FOREIGN KEY (`user_id_from`)
                              REFERENCES `users` (`id_user`)
                              ON DELETE RESTRICT,
                          CONSTRAINT FK_messages_user_to FOREIGN KEY (`user_id_to`)
                              REFERENCES `users` (`id_user`)
                              ON DELETE RESTRICT
#                           CONSTRAINT FK_messages_advertisements FOREIGN KEY (advertisement_id)
#                               REFERENCES `advertisements` (`id_advertisement`)
#                               ON DELETE CASCADE

);

CREATE TABLE `sessions` (
                            `user_id` INT,
                            `start_time` DATETIME,
                            `end_time` DATETIME,
                            CONSTRAINT PK_sessions PRIMARY KEY(user_id,start_time),
                            CONSTRAINT FK_session_user FOREIGN KEY (`user_id`)
                                REFERENCES `users` (`id_user`)
                                ON DELETE RESTRICT
);





