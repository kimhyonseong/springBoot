insert into food_category(category_code,category_name) values (1,"한식");
insert into food_category(category_code,category_name) values (2,"양식");
insert into food_category(category_code,category_name) values (3,"중식");
insert into food_category(category_code,category_name) values (4,"일식");

insert into food(country, country_code,display,name,state, category_code) values('배고픔국',1,10,'크로와상',0,2);
insert into food_img(img_url,name,size,food_idx) values ('/images/food/bread.jpg','크로와상',1234,1);

insert into member(member_id,member_pw,name,state) values ('lss1545','1234','이순신',10);
insert into review(comment,score,food_idx,member_idx,member_id) values ('조선시대때 먹었던 빵맛... 잊을 수 없다',5,1,1,'lss1545');

insert into member(member_id,member_pw,name,state) values ('lss1','1234','이순신1',30);
insert into review(comment,score,food_idx,member_idx,member_id) values ('1',5,1,2,'lss1');

insert into member(member_id,member_pw,name,state) values ('lss2','1234','이순신2',40);
insert into review(comment,score,food_idx,member_idx,member_id) values ('2',5,1,3,'lss2');

insert into member(member_id,member_pw,name,state) values ('lss3','1234','이순신3',50);
insert into review(comment,score,food_idx,member_idx,member_id) values ('3',5,1,4,'lss3');

insert into member(member_id,member_pw,name,state) values ('lss4','1234','이순신4',10);
insert into review(comment,score,food_idx,member_idx,member_id) values ('4',5,1,5,'lss4');

insert into member(member_id,member_pw,name,state,upd_date) values ('lss5','1234','이순신5',90,"2022-06-17 18:11:24.603217");
insert into review(comment,score,food_idx,member_idx,member_id) values ('5',5,1,6,'lss5');