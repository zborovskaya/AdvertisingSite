CREATE DATABASE `advertisements_db` DEFAULT CHARACTER SET utf8;

-- drop user adv_user@localhost;
-- flush privileges;
create user 'adv_user'@localhost
    identified by '12345';

GRANT SELECT,INSERT,UPDATE,DELETE
    ON `advertisements_db`.*
    TO 'adv_user'@localhost;
create user 'advertisement_user'@'%';
GRANT SELECT,INSERT,UPDATE,DELETE
    ON `advertisements_db`.*
    TO 'advertisement_user'@'%';
