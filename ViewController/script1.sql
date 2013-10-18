CREATE TABLE USERINFO 
(
  GB_HP NUMBER 
, PERCENTAGE_HP NUMBER 
, PRICE_PER_GB_GP NUMBER 
, GB_MP NUMBER 
, PERCENTAGE_MP NUMBER 
, PRICE_PER_GB_MP NUMBER 
, GB_ROP NUMBER 
, PERCENTAGE_ROP NUMBER 
, PRICE_PER_GB_ROP NUMBER 
, FACTOR NUMBER 
, CREATION_DATE TIMESTAMP(6) WITH LOCAL TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL 
, CONTACT_INFO_ID VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT USERINFO_PK PRIMARY KEY 
  (
    CONTACT_INFO_ID 
  , CREATION_DATE 
  )
  ENABLE 
) ;

CREATE TABLE CONTACTINFO 
(
  ID VARCHAR2(20 BYTE) NOT NULL 
, NAME VARCHAR2(20 CHAR) NOT NULL 
, FIRST_NAME VARCHAR2(20 BYTE) NOT NULL 
, CODE VARCHAR2(20 BYTE) NOT NULL 
, EMAIL VARCHAR2(40 CHAR) NOT NULL 
, ADDRESS1 VARCHAR2(100 CHAR) NOT NULL 
, PHONE VARCHAR2(40 BYTE) NOT NULL 
, ZIPCODE VARCHAR2(20 BYTE) NOT NULL 
, CITY VARCHAR2(20 BYTE) NOT NULL 
, COUNTRY VARCHAR2(20 BYTE) NOT NULL 
, COMPANY_ID VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT CONTACTOINFO_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
) ;

CREATE TABLE COMPANYINFO 
(
  ID VARCHAR2(20 BYTE) NOT NULL 
, NAME VARCHAR2(20 BYTE) NOT NULL 
, PHONE VARCHAR2(20 BYTE) NOT NULL 
, ADDRESS1 VARCHAR2(100 CHAR) NOT NULL 
, ADDRESS2 VARCHAR2(100 CHAR) 
, ZIPCODE VARCHAR2(20 BYTE) NOT NULL 
, CITY VARCHAR2(20 BYTE) NOT NULL 
, COUNTRY VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT COMPANYINFO_PK PRIMARY KEY 
  (
    ID 
  )
  ENABLE 
) ;

ALTER TABLE CONTACTINFO
ADD CONSTRAINT CONTACTINFO_UK1 UNIQUE 
(
  CODE 
)
ENABLE;

ALTER TABLE USERINFO
ADD CONSTRAINT USERINFO_CONTACTINFO_FK1 FOREIGN KEY
(
  CONTACT_INFO_ID 
)
REFERENCES CONTACTINFO
(
  ID 
)
ENABLE;

ALTER TABLE CONTACTINFO
ADD CONSTRAINT CONTACTINFO_COMPANYINFO_FK1 FOREIGN KEY
(
  COMPANY_ID 
)
REFERENCES COMPANYINFO
(
  ID 
)
ENABLE;

-- Insert data
Insert into COMPANYINFO (ID,NAME,PHONE,ADDRESS1,ADDRESS2,ZIPCODE,CITY,COUNTRY) values ('1','Oracle Corp',' +1 650 506 7000','Parkway Redwood Shores',null,'94065','California','US');

Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('0','Juan Carlos','Ruiz','000000','juan.carlos.ruiz.rico@oracle.com','C/ Severo Ochoa 55','722708963','29590','Malaga','Spain','1');
Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('1','Laurent','Gonzalez','123456','laurent.gonzalez@oracle.com','C/ Severo Ochoa 55','698471341','29590','Malaga','Spain','1');
Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('2','Joaquin','Carballo','654321','joaquin.carballo@oracle.com','C/ Jos� Echegaray 6B Las Rozas','664341541','29830','Madrid','Spain','1');

Insert into USERINFO (GB_HP,PERCENTAGE_HP,PRICE_PER_GB_GP,GB_MP,PERCENTAGE_MP,PRICE_PER_GB_MP,GB_ROP,PERCENTAGE_ROP,PRICE_PER_GB_ROP,FACTOR,CREATION_DATE,CONTACT_INFO_ID) values (100,5,72,3500,35,14,6000,60,7,2,to_timestamp('07-OCT-13 03.40.26.756000000 PM','DD-MON-RR HH.MI.SSXFF AM'),'0');
Insert into USERINFO (GB_HP,PERCENTAGE_HP,PRICE_PER_GB_GP,GB_MP,PERCENTAGE_MP,PRICE_PER_GB_MP,GB_ROP,PERCENTAGE_ROP,PRICE_PER_GB_ROP,FACTOR,CREATION_DATE,CONTACT_INFO_ID) values (50,5,72,1750,35,14,3000,60,7,3,to_timestamp('09-OCT-13 12.40.17.969000000 PM','DD-MON-RR HH.MI.SSXFF AM'),'1');
Insert into USERINFO (GB_HP,PERCENTAGE_HP,PRICE_PER_GB_GP,GB_MP,PERCENTAGE_MP,PRICE_PER_GB_MP,GB_ROP,PERCENTAGE_ROP,PRICE_PER_GB_ROP,FACTOR,CREATION_DATE,CONTACT_INFO_ID) values (25,5,72,875,35,14,1500,60,7,4,to_timestamp('09-OCT-13 12.40.18.000000000 PM','DD-MON-RR HH.MI.SSXFF AM'),'2');
Insert into USERINFO (GB_HP,PERCENTAGE_HP,PRICE_PER_GB_GP,GB_MP,PERCENTAGE_MP,PRICE_PER_GB_MP,GB_ROP,PERCENTAGE_ROP,PRICE_PER_GB_ROP,FACTOR,CREATION_DATE,CONTACT_INFO_ID) values (200,5,72,7000,35,14,9000,60,7,6,to_timestamp('17-OCT-13 04.11.44.141000000 PM','DD-MON-RR HH.MI.SSXFF AM'),'1');
Insert into USERINFO (GB_HP,PERCENTAGE_HP,PRICE_PER_GB_GP,GB_MP,PERCENTAGE_MP,PRICE_PER_GB_MP,GB_ROP,PERCENTAGE_ROP,PRICE_PER_GB_ROP,FACTOR,CREATION_DATE,CONTACT_INFO_ID) values (250,5,72,7050,35,14,9050,60,7,6,to_timestamp('17-OCT-13 04.12.30.936000000 PM','DD-MON-RR HH.MI.SSXFF AM'),'1');