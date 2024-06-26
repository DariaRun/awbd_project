insert into client(clinet_id, name, email, address)  values (1, 'Andrei', 'andrei@test.com', 'str.Primaverii, nr.7');
insert into client(clinet_id, name, email, address)  values (2, 'Ana', 'ana@test.com', 'str.Lalelelor, nr.10');
insert into client(clinet_id, name, email, address)  values  (3, 'Mihai', 'mihai@test.com', 'str.Soarelui, nr.12');

insert into dish (dish_id, name,  price) values (1, 'Pizza',  14.99);
insert into dish (dish_id, name,  price) values (2, 'Paste', 20.99);
insert into dish (dish_id, name,  price) values (3, 'Inghetata', 15.99);


insert into orders (id,  client_id, status) values (1, 1, 'processed');
insert into orders (id,  client_id, status) values (2,  1, 'processed');
insert into orders (id,  client_id, status) values (3,  2,  'processed');


insert into order_dish (order_dish_id, order_id, dish_id, quantity) values (1, 1, 1, 2);
insert into order_dish (order_dish_id, order_id, dish_id, quantity) values (2, 1, 2, 1);
insert into order_dish (order_dish_id, order_id, dish_id, quantity) values (3, 2, 2, 1);
insert into order_dish (order_dish_id, order_id, dish_id, quantity) values (4, 3, 3, 4);