insert into food(country, country_code,display,name,state) values('배고픔국',1,10,'빵',0);
insert into food_img(img_url,name,size,food_idx) values ('/images/food/bread.jpg','빵',1234,1);

insert into member(member_id,member_pw,name,state) values ('lss1545','1234','이순신',10);
insert into review(comment,score,food_idx,member_idx,member_id) values ('조선시대때 먹었던 빵맛... 잊을 수 없다',5,1,1,'lss1545');