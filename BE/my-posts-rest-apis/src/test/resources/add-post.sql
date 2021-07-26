insert into posts(id, title, description, content, created_date)
values
(1,'Post 1', 'Desc Post 1','Content Post 1','2021-07-10 12:21:01'),
(2,'Post 2', 'Desc Post 2','Content Post 2','2021-07-11 13:21:01'),
(3,'Post 3', 'Desc Post 3','Content Post 3','2021-07-12 14:21:01'),
(4,'Post 4', 'Desc Post 4','Content Post 4','2021-07-13 15:21:01'),
(5,'Post 5', 'Desc Post 5','Content Post 5','2021-07-14 16:21:01'),
(6,'Post 6', 'Desc Post 6','Content Post 6','2021-07-15 17:21:01'),
(7,'Post 7', 'Desc Post 7','Content Post 7','2021-07-16 18:21:01'),
(8,'Post 8', 'Desc Post 8','Content Post 8','2021-07-17 19:21:01'),
(9,'Post 9', 'Desc Post 9','Content Post 9','2021-07-18 20:21:01'),
(10,'Post 10', 'Desc Post 10','Content Post 10','2021-07-19 21:21:01'),
(11,'Post 11', 'Desc Post 11','Content Post 11','2021-07-20 22:21:01'),
(12,'Post 12', 'Desc Post 12','Content Post 12','2021-07-21 23:21:01')
;


insert into authors(id, username, password, email)
values
(1, 'admin', 'admin@123', 'admin@gmail.com' ),
( 2,'author', 'author@123', 'author@gmail.com');


insert into authorities(id,author_id,authority)
values
(1,1,'ADMIN'),
(2,2,'AUTHOR');