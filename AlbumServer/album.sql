create database album;
use album;
create table user(
id int not null primary key auto_increment,
username varchar(20) not null,
email varchar (20) not null,
password varchar(20) not null
)charset=utf8;
insert into user values(1,'user','1519503862@qq.com','123456');
insert into user values(2,'123456','13788583917@163.com','123456');

create table admin(
id int not null primary key auto_increment,
admin_name varchar(20) not null,
email varchar (20) not null,
password varchar(20) not null
)charset=utf8;
insert into admin values(1,'admin','1519503862@qq.com','123456');
insert into admin values(2,'123456','13788583917@163.com','123456');

create table album(
id int not null primary key auto_increment,
user_id int not null,
album_name varchar(20) not null,
thumbnail varchar(80),
date timestamp,
FOREIGN key(user_id) references user(id) on delete cascade on update cascade
)charset=utf8;

create table photo(
id int not null primary key auto_increment,
album_id int not null,
photo_name varchar(60) not null,
photo_url varchar(80) not null,
photo_date timestamp,
latitude float,
longitude float,
FOREIGN key(album_id) references album(id) on delete cascade on update cascade
)charset=utf8;

