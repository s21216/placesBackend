INSERT INTO business VALUES (1, 'hlrNMzmkYchokxFlLgwJrBqPVt92', 'BAR RASCAL',               null, 'rascal@rascal.com',             '767384943', 'Moliera 6', '00-076',           NULL, 'Warsaw',  NULL, 'We are a winery that serves natural wine and seasonal food.', 'Wine Bar',     'MODERATE',    null, 52.2442, 21.0117, '2023-09-18');
INSERT INTO business VALUES (2, 'z82ya85oYeTH2RF42b2xvGWTMm33', 'Pizzaiolo',                null, 'pizzaiolo@pizzaiolo.com',       '867384943', 'Krucza 16/22', '00-526',        NULL, 'Warsaw',  NULL, 'Neapolitan style pizza, drinks and vibes.',                   'Pizza',        'MODERATE',    null, 52.2258, 21.0192, '2023-09-18');
INSERT INTO business VALUES (3, '3PWYIeqNmkeld0zqBdOLOt7Uqus1', 'Señor Lucas taquería',     null, 'senorlucas@senorlucas.com',     '888313543', 'Poznańska 26', '00-684',        NULL, 'Warsaw',  NULL, 'Come try our burritos and tacos',                             'Mexican',      'MODERATE',    null, 52.2268, 21.0112, '2023-09-18');
INSERT INTO business VALUES (4, 'ZF0etDfSgoSSKO7ldRkla80UPMG2', 'MOD',                      null, 'mod@mod.com',                   '720384943', 'Oleandrów 8', '00-629',         NULL, 'Warsaw',  NULL, 'Come for our ramen and donuts',                               'Restaurant',   'MODERATE',    null, 52.2167, 21.0179, '2023-09-18');
INSERT INTO business VALUES (5, 'bFGBxxOBQbW2k3pksjFaCpueq963', 'POLLYPIZZA NEAPOLITAN',    null, 'pollypizza@pollypizza.com',     '636384333', 'Puławska 24', '02-512',         NULL, 'Warsaw',  NULL, 'Neapolitan style pizza',                                      'Pizza',        'MODERATE',    null, 52.2069, 21.0219, '2023-09-18');
INSERT INTO business VALUES (6, 'j7BcUvoe1bOfzsuCrjmOaoAYqVp1', 'Efes',                     null, 'efes@efes.com',                 '767384222', 'Niepodległości 80', '02-626',   NULL, 'Warsaw',  NULL, 'Turkish kebab',                                               'Kebab',        'MODERATE',    null, 52.1963, 21.0140, '2023-09-18');
INSERT INTO business VALUES (7, 's69plW7XqiXzJKEjDZROIsQonyM2', 'Izumi Sushi Biały Kamień', null, 'izumisushi@izumisushi.com',     '767384555', 'Biały Kamień 4', '02-593',      NULL, 'Warsaw',  NULL, 'Best of sushi',                                               'Sushi',        'EXPENSIVE',   null, 52.2057, 20.9973, '2023-09-18');
INSERT INTO business VALUES (8, 'x329m1TruxOgY7aoBzAdiwCVd4p1', 'doskoi',                   null, 'doskoi@doskoi.com',             '753384466', 'Karmelicka 54/2', '31-128',     NULL, 'Cracow',  NULL, 'Traditional japanese food',                                   'Japanese',     'INEXPENSIVE', null, 50.0677, 19.9279, '2023-09-18');
INSERT INTO business VALUES (9, 'Xr9tE7j42KW6Nb202sMie9WBnck1', 'Czarna Kaczka',            null, 'czarnakaczka@czarnakaczka.com', '762334243', 'Poselska 22', '31-002',         NULL, 'Cracow',  NULL, 'Traditional polish food',                                     'Polish',       'EXPENSIVE',   null, 50.0582, 19.9392, '2023-09-18');
INSERT INTO business VALUES (10, 'RLGrMxsWzJOU4tGb90CuozLZVOs2', 'happa to mame',           null, 'happa@happa.com',               '575482121', 'Hoża 43/49', '00-681',          NULL, 'Warsaw',  NULL, 'Matcha drinks and japanese sweets',                           'Matcha bar',   'EXPENSIVE',   null, 52.2255, 21.0114, '2023-09-18');
SELECT setval('business_id_seq', (SELECT MAX(id) from "business"));

INSERT INTO category VALUES(1, 'pizza');
INSERT INTO category VALUES(2, 'wine');
INSERT INTO category VALUES(3, 'italian');
INSERT INTO category VALUES(4, 'matcha');
INSERT INTO category VALUES(5, 'japanese');
INSERT INTO category VALUES(6, 'polish');
INSERT INTO category VALUES(7, 'kebab');
INSERT INTO category VALUES(8, 'ramen');
INSERT INTO category VALUES(9, 'mexican');
INSERT INTO category VALUES(10, 'sushi');
SELECT setval('business_id_seq', (SELECT MAX(id) from "business"));


INSERT INTO business_category VALUES (1, 1, 2);
INSERT INTO business_category VALUES (2, 2, 1);
INSERT INTO business_category VALUES (3, 3, 9);
INSERT INTO business_category VALUES (4, 4, 8);
INSERT INTO business_category VALUES (5, 5, 1);
INSERT INTO business_category VALUES (6, 6, 7);
INSERT INTO business_category VALUES (7, 7, 10);
INSERT INTO business_category VALUES (8, 8, 5);
INSERT INTO business_category VALUES (9, 9, 6);
INSERT INTO business_category VALUES (10, 10, 4);
SELECT setval('business_category_id_seq', (SELECT MAX(id) from "business_category"));

INSERT INTO "user" VALUES (1, 'kMw6T9Dy6oZHAi4OvCWDnkUyVec2', 'test@gmail.com', 'test', 'test', null, '2023-11-10');
SELECT setval('user_id_seq', (SELECT MAX(id) from "user"));
