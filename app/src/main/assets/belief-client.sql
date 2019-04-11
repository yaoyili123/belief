/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/9 20:15:02                            */
/*==============================================================*/


drop table if exists food;

drop table if exists recipe;

drop table if exists recipe_type;

drop table if exists sport_class;

--/*==============================================================*/
--/* Table: food                                                  */
--/*==============================================================*/
--create table food
--(
--   fid                  integer not null,
--   name                 text,
--   photo_id             integer,
--   detail               text,
--   ingredient           text,
--   primary key (fid)
--);
--
--/*==============================================================*/
--/* Table: recipe                                                */
--/*==============================================================*/
--create table recipe
--(
--   rid                  integer not null,
--   photo_id             integer,
--   tid                  integer,
--   ingredient           text,
--   name                 text,
--   primary key (rid)
--);
--
--/*==============================================================*/
--/* Table: recipe_type                                           */
--/*==============================================================*/
--create table recipe_type
--(
--   tid                  integer not null,
--   name                 text,
--   photo_id             integer,
--   primary key (tid)
--);
--
--/*==============================================================*/
--/* Table: sport_class                                           */
--/*==============================================================*/
--create table sport_class
--(
--   scid                 integer not null,
--   name                 text,
--   kcal                 integer,
--   time                 integer,
--   level                integer,
--   type                 integer,
--   video_url            text,
--   detail               text,
--   primary key (scid)
--);
--
