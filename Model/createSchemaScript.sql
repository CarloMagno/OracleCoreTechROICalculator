--------------------------------------------------------
--  COMPANYINFO
--------------------------------------------------------

CREATE TABLE  COMPANYINFO  
   (     ID  VARCHAR2(20 BYTE), 
     NAME  VARCHAR2(20 BYTE), 
     PHONE  VARCHAR2(20 BYTE), 
     ADDRESS1  VARCHAR2(100 CHAR), 
     ADDRESS2  VARCHAR2(100 CHAR), 
     ZIPCODE  VARCHAR2(20 BYTE), 
     CITY  VARCHAR2(20 BYTE), 
     COUNTRY  VARCHAR2(20 BYTE)
   ) ;

--------------------------------------------------------
--  Inserting data into COMPANYINFO
--------------------------------------------------------

Insert into COMPANYINFO (ID,NAME,PHONE,ADDRESS1,ADDRESS2,ZIPCODE,CITY,COUNTRY) values ('1','Oracle Corp',' +1 650 506 7000','Parkway Redwood Shores',null,'94065','California','US');
--------------------------------------------------------
--  DDL for Index COMPANYINFO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX  COMPANYINFO_PK  ON  COMPANYINFO  ( ID );
--------------------------------------------------------
--  Constraints for Table COMPANYINFO
--------------------------------------------------------

  ALTER TABLE  COMPANYINFO  ADD CONSTRAINT  COMPANYINFO_PK  PRIMARY KEY ( ID );
 
  ALTER TABLE  COMPANYINFO  MODIFY ( ID  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( NAME  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( PHONE  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( ADDRESS1  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( ZIPCODE  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( CITY  NOT NULL ENABLE);
 
  ALTER TABLE  COMPANYINFO  MODIFY ( COUNTRY  NOT NULL ENABLE);

--------------------------------------------------------
--  CONTACTINFO
--------------------------------------------------------

CREATE TABLE  CONTACTINFO  
   (     ID  VARCHAR2(20 BYTE), 
     NAME  VARCHAR2(20 CHAR), 
     FIRST_NAME  VARCHAR2(20 BYTE), 
     CODE  VARCHAR2(20 BYTE), 
     EMAIL  VARCHAR2(40 CHAR), 
     ADDRESS1  VARCHAR2(100 CHAR), 
     PHONE  VARCHAR2(40 BYTE), 
     ZIPCODE  VARCHAR2(20 BYTE), 
     CITY  VARCHAR2(20 BYTE), 
     COUNTRY  VARCHAR2(20 BYTE), 
     COMPANY_ID  VARCHAR2(20 BYTE)
   ) ;

Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('0','Juan Carlos','Ruiz','000000','juan.carlos.ruiz.rico@oracle.com','C/ Severo Ochoa 55','722708963','29590','Malaga','Spain','1');
Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('1','Laurent','Gonzalez','123456','laurent.gonzalez@oracle.com','C/ Severo Ochoa 55','698471341','29590','Malaga','Spain','1');
Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('2','Joaquin','Carballo','654321','joaquin.carballo@oracle.com','C/ Jos?chegaray 6B Las Rozas','664341541','29830','Madrid','Spain','1');
Insert into CONTACTINFO (ID,NAME,FIRST_NAME,CODE,EMAIL,ADDRESS1,PHONE,ZIPCODE,CITY,COUNTRY,COMPANY_ID) values ('3','Oliver','Guenther','753159','oliver.guenther@oracle.com','C/ Severo Ochoa 55','610708952','29590','Malaga','Spain','1');

--------------------------------------------------------
--  DDL for Index CONTACTOINFO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX  CONTACTOINFO_PK  ON  CONTACTINFO  ( ID );
--------------------------------------------------------
--  DDL for Index CONTACTINFO_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX  CONTACTINFO_UK1  ON  CONTACTINFO  ( CODE );
--------------------------------------------------------
--  Constraints for Table CONTACTINFO
--------------------------------------------------------

  ALTER TABLE  CONTACTINFO  ADD CONSTRAINT  CONTACTINFO_UK1  UNIQUE ( CODE );
 
  ALTER TABLE  CONTACTINFO  ADD CONSTRAINT  CONTACTOINFO_PK  PRIMARY KEY ( ID );
 
  ALTER TABLE  CONTACTINFO  MODIFY ( ID  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( NAME  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( FIRST_NAME  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( CODE  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( EMAIL  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( ADDRESS1  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( ZIPCODE  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( CITY  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( COUNTRY  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( COMPANY_ID  NOT NULL ENABLE);
 
  ALTER TABLE  CONTACTINFO  MODIFY ( PHONE  NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table CONTACTINFO
--------------------------------------------------------

  ALTER TABLE  CONTACTINFO  ADD CONSTRAINT  CONTACTINFO_COMPANYINFO_FK1  FOREIGN KEY ( COMPANY_ID )
      REFERENCES  COMPANYINFO  ( ID ) ENABLE;

--------------------------------------------------------
--  USERINFO
--------------------------------------------------------
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
, ROW_NUMBER NUMBER 
, CONSTRAINT USERINFO_PK PRIMARY KEY 
  (
    CONTACT_INFO_ID 
  , CREATION_DATE 
  )
  ENABLE 
);
 
------------------------------------------------
--
------------------------------------------------
 
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
 
--------------------------------------------------------
-- SEQUENCE
--------------------------------------------------------
CREATE SEQUENCE REPORT_ROW_NUMBER_SEQUENCE INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

--------------------------------------------------------
-- INFO_TEXT
--------------------------------------------------------
CREATE TABLE  INFO_TEXT  (FIELD VARCHAR2(40), TEXT VARCHAR2(2000));

Insert into INFO_TEXT values('High_Performance_GB','Tier 1 or High Performance storage usually refers to SSD nowadays but in essence is your fastest or most expensive/gb storage.');
Insert into INFO_TEXT values('Mid_Range_GB','Tier 2 or Mid-Range usually applies to HDD based storage that would normally be sufficient for all use.');
Insert into INFO_TEXT values('Low_Cost_GB','Tier 3 or Low Cost applies to the remaining storage for example old disks or even integrated tape (by integration we mean storage solution that is seen as equivalent to all other storage by the application/host using it).');
Insert into INFO_TEXT values('Advanced_Compression_Factor','The compression factor is determined by the type of data being stored. Some data such as compressed images cannot be further compress and hence their compression factor would be 1 (a compression factor less than 1 would indicate expansion). Other data such as text like this description have a very high compression factor. This text, as an example, would have a compression factor of about 6. To determine the correct amount use the following guide lines for the different compression ratios: 1.0: We only store compressed data such as images and de-duplicate these through our application. 1.5: We store mainly compressed data 2: We store compressed and other data, most of this is unique (such as time based data like financial prices of call stats) 3: we store a mix of unique and recurring data (data such as product codes, order numbers, adresses, names etc) and a bit of compressed data 4: we store mainly recurring data 5: we store some recurring data but a lot of textual data examples: Compressed Image Store: 1 Financial trading platform without customer order tracking: 1.5 Financial trading platform with customer data: 2 Online store: 3 Back office order processing or ERP/CRM:4 School Reports System: 5');
Insert into INFO_TEXT values('Total_Storage_Amount','This is the total amount of storage of your system.');

-------------------------------------------------------
-- CUSTOMER_REPORT
-------------------------------------------------------
CREATE TABLE CUSTOMER_REPORT 
(
  CUSTOMER_ID VARCHAR2(20 BYTE),
  DATA_INFO_ID VARCHAR2(20 BYTE),
  TIMESTAMP_COMMIT DATE DEFAULT CURRENT_TIMESTAMP 
) ;

