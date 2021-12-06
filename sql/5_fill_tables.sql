INSERT INTO `users` (
    `login`,
    `password`,
    `is_active`,
    `role`
) VALUES (
             'admin',
             '21232F297A57A5A743894A0E4A8', /* MD5 хэш пароля "admin" */
             true,
             0
         ),
         (
             'user1',
             '21232F297A57A5A7678', /* MD5 хэш пароля "admin" */
             true,
             1
         ),(
             'user2',
             '21232F297A57A12390', /* MD5 хэш пароля "admin" */
             true,
             1
         ),(
             'user3',
             '21232F297A86342', /* MD5 хэш пароля "admin" */
             true,
             1
         );
INSERT cities(name)
VALUES
    ('Минск'),
    ('Могилёв'),
    ('Брест'),
    ('Гомель'),
    ('Гродно'),
    ('Витебск'),
    ('Барановичи'),
    ('Белоозерск'),
    ('Белыничи'),
    ('Бобруйск'),
    ('Борисов'),
    ('Быхов'),
    ('Вилейка'),
    ('Волковыск'),
    ('Воложин'),
    ('Высокое'),
    ('Ганцевичи'),
    ('Горки'),
    ('Жабинка'),
    ('Жлобин'),
    ('Жодино'),
    ('Лида'),
    ('Мозырь'),
    ('Новополоцк'),
    ('Орша'),
    ('Пинск'),
    ('Полоцк'),
    ('Солигорск');
INSERT clients(user_id,email, first_name,last_name,phone,city_id)
VALUES
    (1,'zborovskaya@gmail.com', 'Анна','Зборовская','293456734',1),
    (2,'alex@gmail.com', 'Алекей','Варфоломеев','293456734',2),
    (3,'diana@gmail.com', 'Диана','Никифорова','291230965',1),
    (4,'pchel@gmail.com', 'Алекей','Пчелкин','294560923',3);

INSERT category(name)
VALUES
    ('Недвижимость'),
    ('Авто и транспорт'),
    ('Бытовая техника'),
    ('Компьютерная техника'),
    ('Телефоны и планшеты'),
    ('Электроника'),
    ('Женский гардероб'),
    ('Мужской гардероб'),
    ('Красота и здоровье'),
    ('Всё для детей и мам'),
    ('Мебель'),
    ('Всё для дома'),
    ('Ремонт и стройка'),
    ('Сад и огород'),
    ('Хобби, спорт и туризм'),
    ('Свадьбы и праздники'),
    ('Животные'),
    ('Прочее');
INSERT advertisements(user_id, category_id,title,text,date,expiry,is_active)
VALUES
    (3, 17,'Продаются котята',
     'Прекрасные малыши ищут новый дом.',
     CAST('2015-12-25 15:32:06.427' AS DateTime),
     CAST('2020-12-25 15:32:06.427' AS DateTime),true);
INSERT likes(advertisement_id,user_id)
VALUES
    (1, 3);
INSERT messages(user_id_from,user_id_to,message,time)
VALUES
    (1, 4,'Здравствуйте',CAST('2020-12-25 15:38:06.427' AS DateTime));
INSERT sessions(user_id,start_time,end_time)
VALUES
    ( 2,CAST('2020-9-20 11:30:05.428' AS DateTime),
     CAST('2020-9-20 12:10:05.428' AS DateTime));

