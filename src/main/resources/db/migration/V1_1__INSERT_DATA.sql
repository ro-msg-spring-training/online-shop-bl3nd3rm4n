INSERT INTO customer(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, EMAIL)
VALUES ('Max', 'Alder', 'maxx', 'x', 'alder.max@gmail.com'),
       ('Marianne', 'Burch', 'mburch', 'x', 'm.burch@yahoo.com'),
       ('Denise', 'Huber', 'fraughub', 'x', 'fraughub@abx.ch');

INSERT INTO supplier(NAME)
VALUES ('Napolact'),
       ('Pilos'),
       ('Milka'),
       ('Dole');

INSERT INTO product_category(NAME, DESCRIPTION)
VALUES ('dairy', 'milk, cheese, yoghurt...'),
       ('sweets', 'chocolate, cookies...'),
       ('fruit', 'fruit');


INSERT INTO address(COUNTRY, CITY, COUNTY, STREET_ADDRESS)
VALUES ('Romania', 'Cluj-Napoca', 'Cluj', 'Bucuresti 12'),
       ('Romania', 'Sibiu', 'Sibiu', 'Gorjului 7'),
       ('Switzerland', 'Churwalden', 'Chur', 'Hauptstrasse 42'),
       ('Romania', 'Cluj-Napoca', 'Cluj', 'Gorunului 4');

INSERT INTO product(NAME, DESCRIPTION, PRICE, WEIGHT, PRODUCT_CATEGORY_ID, SUPPLIER_ID, IMAGE_URL)
VALUES ('Milk 3.5%', 'napolact description', 2, 1, 1, 1,
        'https://napolact.ro/uploads/produse/lapte-din-inima-ardealului-1L-35-grasime.png'),
       ('Milk 3.5%', 'pilos description', 1.5, 1, 1, 2,
        'https://ro.cat-ret.assets.lidl/catalog4media/ro/article/6801724/gallery/zoom/6801724_99.jpg'),
       ('Chocolate', 'milka description', 2, 1, 2, 3,
        'https://s13emagst.akamaized.net/products/697/696389/images/res_f831528c5298ee48295a6017103a8884.jpg'),
       ('Chocolate x2', 'milka description', 4, 2, 2, 3,
        'https://s13emagst.akamaized.net/products/697/696389/images/res_f831528c5298ee48295a6017103a8884.jpg'),
       ('Bananas', '', 3, 0.5, 3, 4,
        'https://lh3.googleusercontent.com/proxy/cJKY7wS1CiaJjMD0CubsWSTggWukr3kAAta2W5Gksz63E9zFHsN_pQRuUnzD3DzR68z70cFqNYsj1qJmjcUC8a2-AmRKDuqZGdOOfYQ_nklVfnnsB6AI6CBifN_UJHw'),
       ('Nothing', 'Out of Stock', 0, 100, 3, 4, '');

INSERT INTO location(ADDRESS_ID, NAME)
VALUES (1, 'Warehouse 1'),
       (2, 'Warehouse 2');

INSERT INTO stock(PRODUCT_ID, LOCATION_ID, QUANTITY)
VALUES (1, 1, 1230),
       (2, 1, 234),
       (3, 1, 345),
       (4, 1, 456),
       (1, 2, 567),
       (2, 2, 678),
       (3, 2, 789),
       (5, 2, 890);