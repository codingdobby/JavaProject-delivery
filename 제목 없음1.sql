set serveroutput on format wrapped;

begin
    DBMS_OUTPUT.put_line('===============================');
end;
/

 

drop sequence seq_ds_id;
drop sequence seq_sales_id;
drop sequence seq_loc_id;
drop sequence seq_st_no;

drop table sales;
drop table gisa cascade constraints;
drop table loc cascade constraints;
drop table admin cascade constraints;
drop table seller cascade constraints;
drop table customer cascade constraints;
drop table delivery_select cascade constraints;


--지역 테이블
create table loc(
    l_no number primary key,
    loc varchar2(20) not null
);

create sequence seq_loc_id increment by 1;

insert into loc values(seq_loc_id.nextval, '서울');
insert into loc values(seq_loc_id.nextval, '대전');
insert into loc values(seq_loc_id.nextval, '대구');
insert into loc values(seq_loc_id.nextval, '부산');
insert into loc values(seq_loc_id.nextval, '인천');
insert into loc values(seq_loc_id.nextval, '울산');
insert into loc values(seq_loc_id.nextval, '제주도');

--회원테이블
create table customer(
    cus_id varchar2(20) primary key, 
    pwd varchar2(20) not null,
    name varchar2(10) not null,
    addr varchar2(50) not null,
    tel varchar2(15) unique,
    birth date, 
    joinedDate date, --가입일
    point number,
    l_no_cus_fk number constraint l_no_cus_fk references loc(l_no) not null --지역
);
select * from customer;
insert into customer values('customer', '12345678', '김회원', '강남구', '010-0001-0000', '98/01/01', '19/08/22' ,0, 1);
insert into customer values('customer1', '12345678', '이회원', '성북구', '010-0001-0001', '00/04/01', '15/07/12', 0, 1);
insert into customer values('customer2', '12345678', '박회원', '중구','010-0001-0002', '04/03/12', '15/08/23', 0, 2);
insert into customer values('customer3', '12345678', '최회원', '동성로' ,'010-0001-0003', '91/12/11', '16/08/29', 10000, 3);
insert into customer values('customer4', '12345678', '조회원', '서면' ,'010-0001-0004', '94/12/24', '17/08/22', 1000, 4);
insert into customer values('customer5', '12345678', '정회원', '송도' ,'010-0001-0005', '79/06/13', '18/08/22', 500, 5);
insert into customer values('customer6', '12345678', '조회원', '북구','010-0001-0006', '70/08/29', '19/08/22', 1000, 6);
insert into customer values('customer7', '12345678', '송회원', '서귀포', '010-0001-0007','85/10/09', '15/10/30', 0, 7);
insert into customer values('customer8', '12345678', '손회원', '광진구','010-0001-0008', '81/02/22', '18/03/29', 0, 1);

--판매자 테이블
create table seller(
    sell_id varchar2(20) primary key, --판매자 아이디
    pwd varchar2(20) not null, --비밀번호
    name varchar2(20) not null, --이름
    addr varchar2(50) not null, --주소
    tel varchar2(15) unique, --전화번호
    joinedDate date, --가입일
    freeticketcnt number default 0 not null --정기권 남은 숫자
);

insert into seller values('seller', '1234' , '이판매', '서울', '010-0002-0000', '19/05/15' , 0);
insert into seller values('seller1', '1234' , '김판매', '서울', '010-0002-0001', '18/03/27' , 0);
insert into seller values('seller2', '1234' , '박판매', '울산', '010-0002-0002', '17/11/06' , 0);
insert into seller values('seller3', '1234' , '최판매', '대구', '010-0002-0003', '18/05/19' , 0);
insert into seller values('seller4', '1234' , '오판매', '대전', '010-0002-0004', '16/10/14' , 0);
insert into seller values('seller5', '1234' , '곽판매', '부산', '010-0002-0005', '15/05/13' , 0);
insert into seller values('seller6', '1234' , '조판매', '제주도', '010-0002-0006', '17/07/29' , 0);
insert into seller values('seller7', '1234' , '정판매', '인천', '010-0002-0007', '19/03/21' , 0);

--기사 테이블
create table gisa(
    gisa_id varchar2(20) primary key, --아이디
    pwd varchar2(20) not null, --비밀번호
    name varchar2(10) not null, --이름
    tel varchar2(15) unique, --전화번호
    hiredate date, --입사일
    regdate date, --퇴사일
    l_no_gisa_fk number constraint l_no_gisa_fk references loc(l_no) not null --기사가 일하는 지역 번호
);

insert into gisa values('gisa1','1234','김기사', '010-0003-0000', '15/01/01', null, 1);
insert into gisa values('gisa11','1234','이기사', '010-0003-0001', '16/06/06', null, 1);
insert into gisa values('gisa12','1234','박기사', '010-0003-0002', '17/09/09', null, 2);
insert into gisa values('gisa13','1234','최기사', '010-0003-0003', '19/04/03', null, 3);
insert into gisa values('gisa14','1234','조기사', '010-0003-0004', '18/03/04', null, 4);
insert into gisa values('gisa15','1234','정기사', '010-0003-0005', '17/08/23', null, 5);
insert into gisa values('gisa16','1234','배기사', '010-0003-0006', '15/11/11', null, 6);
insert into gisa values('gisa17','1234','김기사', '010-0003-0007', '16/12/12', null, 7);
insert into gisa values('gisa18','1234','곽기사', '010-0003-0008', '15/05/13', null, 1);
insert into gisa values('gisa19','1234','남기사', '010-0003-0009', '15/09/23', sysdate, 7);
insert into gisa values('gisa110','1234','라기사', '010-0003-0010', '15/11/25', '17/06/03', 2);
insert into gisa values('gisa111','1234','유기사', '010-0003-0011', '15/07/04', '19/01/05', 1);

--관리자 테이블
create table admin(
    ad_id varchar2(20) primary key,
    pwd varchar2(20) not null,
    name varchar2(10) not null
);

insert into admin values('admin', '1234', '관리자');

--배송 조회 테이블
create table delivery_select(
    order_no number primary key, --송장번호
    object_name varchar2(30) not null, --물건 이름
    name varchar2(20) not null, --받을 사람 이름
     tel varchar2(15) not null, --받을 사람 전화번호
     addr varchar2(50) not null, --받을 사람 주소
     start_date date, --출발일
    end_date date, --도착일
    seller_id_ds_fk varchar2(20) constraint seller_id_ds_fk references seller(sell_id) not null, --판매자 아이디
    gisa_id_ds_fk varchar2(20) constraint gisa_id_ds_fk references gisa(gisa_id), --기사아이디(같은 지역번호로 배정);
    l_no_ds_fk number constraint l_no_ds_fk references loc(l_no) not null--지역아이디 (받는 사람의 주소와 별도로 지역 아이디 받기)
);

create sequence seq_ds_id increment by 1;


insert into delivery_select values(seq_ds_id.nextval, '김치', '김받음', '010-0044-4444', '서울', sysdate, null, 'seller', 'gisa1', 1);
insert into delivery_select values(seq_ds_id.nextval, '의류', '이받음', '010-0044-4445', '대전', '17/06/05', '17/06/06', 'seller', 'gisa12', 2);
insert into delivery_select values(seq_ds_id.nextval, '음식', '최받음', '010-0044-4446', '대구', '18/10/10', '18/10/13', 'seller3', 'gisa13', 3);
insert into delivery_select values(seq_ds_id.nextval, '신발', '유받음', '010-0044-4410', '부산', '16/04/23', '16/04/30', 'seller3', 'gisa14', 4);
insert into delivery_select values(seq_ds_id.nextval, '물', '곽받음', '010-0044-4449', '인천', sysdate, null, 'seller', 'gisa15', 5);
insert into delivery_select values(seq_ds_id.nextval, '노트북', '곽받음', '010-0044-4448', '울산', sysdate, null, 'seller2', 'gisa16', 6);
insert into delivery_select values(seq_ds_id.nextval, '휴대폰', '박받음', '010-0044-4447', '제주도', '19/07/05', '19/07/07', 'seller2', 'gisa17', 7);
insert into delivery_select values(seq_ds_id.nextval, '전자제품', '김회원', '010-0001-0000', '서울', sysdate, null, 'seller1', 'gisa1', 1);
insert into delivery_select values(seq_ds_id.nextval, '티비', '최회원', '010-0001-0003', '대구', sysdate, null, 'seller1', 'gisa13', 3);
insert into delivery_select values(seq_ds_id.nextval, '카드', '김회원', '010-0001-0000', '서울', sysdate, null, 'seller2', 'gisa111', 1);
insert into delivery_select values(seq_ds_id.nextval, '티켓', '이회원', '010-0001-0001', '서울', '16/01/01', '16/01/05', 'seller', 'gisa110', 1);
insert into delivery_select values(seq_ds_id.nextval, '배터리', '이회원', '010-0001-0001', '서울', sysdate, null, 'seller', 'gisa110', 1);
insert into delivery_select values(seq_ds_id.nextval, '마우스', '최회원', '010-0001-0000', '서울', sysdate, null, 'seller5', 'gisa110', 1);
insert into delivery_select values(seq_ds_id.nextval, '볼펜', '김회원', '010-0001-0000', '서울', '19/04/27', '19/04/28', 'seller4', 'gisa110', 1);
insert into delivery_select values(seq_ds_id.nextval, '안경', '이회원', '010-0001-0001', '서울', sysdate, null, 'seller4', null, 1);
insert into delivery_select values(seq_ds_id.nextval, '모자', '조회원', '010-0002-0004', '부산', sysdate, null, 'seller4', null, 4);
insert into delivery_select values(seq_ds_id.nextval, '키보드', '최회원', '010-0001-0003', '대구', sysdate, null, 'seller6', null, 3);
insert into delivery_select values(seq_ds_id.nextval, '컵', '최회원', '010-0001-0003', '대구', sysdate, null, 'seller', null, 3);
insert into delivery_select values(seq_ds_id.nextval, '가방', '이회원', '010-0001-0001', '서울', sysdate, null, 'seller7', null, 1);
insert into delivery_select values(seq_ds_id.nextval, '팔찌', '김회원', '010-0001-0000', '서울', sysdate, null, 'seller7', null, 1);
insert into delivery_select values(seq_ds_id.nextval, '머리끈', '김회원', '010-0001-0000', '서울', sysdate, null, 'seller', null, 1);
insert into delivery_select values(seq_ds_id.nextval, '컵홀더', '이회원', '010-0001-0001', '서울', sysdate, null, 'seller', null, 1);
insert into delivery_select values(seq_ds_id.nextval, '컵홀더', '이회원', '010-0001-0001', '서울', null, null, 'seller', null, 1);

--매출 테이블
create table sales(
    s_no number primary key, --번호
    fee number not null, --금액
    s_name varchar2(20) not null, --매출이 보통 매출인지 정기권인지 표시
    o_no_sales_fk number constraint o_no_sales_fk references delivery_select(order_no) not null, --주문번호
    c_id_sales_fk varchar2(20) constraint c_id_sales_fk references customer(cus_id), --사용자 아이디
    p_use char default 'x' 
);

create sequence seq_sales_id increment by 1;

insert into sales values(seq_sales_id.nextval, 2500, '매출', 1, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 2, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 3, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 4, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 5, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 6, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 7, null,'x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 8, 'customer','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 9, 'customer3','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 10,'customer','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 11, 'customer1','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 12, 'customer1','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 13, 'customer3','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 14, 'customer','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 15, 'customer1','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 16, 'customer4','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 17, 'customer3','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 18, 'customer3','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 19, 'customer1','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 20, 'customer','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 21, 'customer','x');
insert into sales values(seq_sales_id.nextval, 2500, '매출', 22, 'customer1','x');

create sequence seq_st_no increment by 1;

commit;