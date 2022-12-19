insert into member(id,name,password,role) values("lss1545","lss","1234","ADMIN");
insert into member(id,name,password,role) values("admin","admin","1234","ROLE_ADMIN");
insert into member(id,name,password,role) values("user","user","1234","ROLE_USER");
insert into member(id,name,password,role) values("user2","user","1234","ROLE_USER");
insert into member(id,name,password,role) values("user3","user","1234","ROLE_USER");
insert into member(id,name,password,role) values("user4","user","1234","ROLE_USER");
insert into member(id,name,password,role) values("user5","user","1234","ROLE_USER");

-- 리스트 테스트
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김1",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김2",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김3",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김4",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김5",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김6",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김7",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김8",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김9",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김10",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김11",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김12",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김13",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김14",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김15",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김16",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김17",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김18",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김19",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김20",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김21",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김22",9900,1000,1);
insert into item(code,description,state,name,price,quantity,member_idx) values ("A002","M사",10,"감자튀김23",9900,1000,1);

insert into item_img(img_path,file_name,origin_file_name,size,extension,item_idx) values ("/images/real/","croissant.jpg","croissant.jpg",100,"jpg",1);

insert into review(comment, score,item_idx,member_idx,member_id) values ("좋아요",5,1,1,"lss1545");
insert into review(comment, score,item_idx,member_idx,member_id) values ("괜찮아요",4,1,2,"admin");
insert into review(comment, score,item_idx,member_idx,member_id) values ("굳",4,1,3,"user");
insert into review(comment, score,item_idx,member_idx,member_id) values ("나이스",4,1,4,"user2");
insert into review(comment, score,item_idx,member_idx,member_id) values ("이게 좋냐",1,1,5,"user3");
insert into review(comment, score,item_idx,member_idx,member_id) values ("중간",3,1,6,"user4");
insert into review(comment, score,item_idx,member_idx,member_id) values ("별로... 추천 안함",1,1,7,"user5");
insert into review(comment, score,item_idx,member_idx,member_id) values ("굳.",5,2,2,"admin");