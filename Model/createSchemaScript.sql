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

--CREATE TABLE  USERINFO  
--   ( GB_HP  NUMBER, 
--     PERCENTAGE_HP  NUMBER, 
--     PRICE_PER_GB_GP  NUMBER, 
--     GB_MP  NUMBER, 
--     PERCENTAGE_MP  NUMBER, 
--     PRICE_PER_GB_MP  NUMBER, 
--     GB_ROP  NUMBER, 
--     PERCENTAGE_ROP  NUMBER, 
--     PRICE_PER_GB_ROP  NUMBER, 
--     FACTOR  NUMBER, 
--     CREATION_DATE  TIMESTAMP (6) WITH LOCAL TIME ZONE DEFAULT CURRENT_TIMESTAMP, 
--     CONTACT_INFO_ID  VARCHAR2(20 BYTE),
--     ROW_NUMBER  NUMBER
--    );
  
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
-- TRIGGER
--------------------------------------------------------
CREATE OR REPLACE TRIGGER INSERT_ROW_NUMBER_TRIGGER BEFORE INSERT ON USERINFO FOR EACH ROW
BEGIN
    if ( :new.ROW_NUMBER IS NULL ) THEN
        SELECT REPORT_ROW_NUMBER_SEQUENCE.nextval INTO :new.ROW_NUMBER FROM dual;
    END IF;
END;   

--------------------------------------------------------
--  DDL for Index USERINFO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX  USERINFO_PK  ON  USERINFO  ( ID ,  CONTACT_INFO_ID );
--------------------------------------------------------
--  Constraints for Table USERINFO
--------------------------------------------------------

  ALTER TABLE  USERINFO  MODIFY ( ID  NOT NULL ENABLE);
 
  ALTER TABLE  USERINFO  MODIFY ( CONTACT_INFO_ID  NOT NULL ENABLE);
 
  ALTER TABLE  USERINFO  ADD CONSTRAINT  USERINFO_PK  PRIMARY KEY ( ID ,  CONTACT_INFO_ID );
--------------------------------------------------------
--  Ref Constraints for Table USERINFO
--------------------------------------------------------

  ALTER TABLE  USERINFO  ADD CONSTRAINT  USERINFO_CONTACTINFO_FK1  FOREIGN KEY ( CONTACT_INFO_ID )
      REFERENCES  CONTACTINFO  ( ID ) ENABLE;

--------------------------------------------------------
-- INFO_TEXT
--------------------------------------------------------
  CREATE TABLE  INFO_TEXT  (FIELD VARCHAR2(40), TEXT VARCHAR2(1000));

Insert into INFO_TEXT values('High_Performance_GB','Amount of high performance storage in Gigabytes. For storage devices like EMC (Symmetrix / DMX), SUN 9900, HP XP, Hitachi USP, etc.');
Insert into INFO_TEXT values('Mid_Range_GB','Amount of mid range storage in Gigabytes. For storage devices like SUN 6000, HP Eva, Hitachi AMS / AMS 2000, EMC CX, NTAP, etc.');
Insert into INFO_TEXT values('Low_Cost_GB','Amount of low cost storage in Gigabytes. For storage devices like EMC AX Clarion, HP MSA, Hitachi AMS / AMS 2000, etc.');
Insert into INFO_TEXT values('Advanced_Compression_Factor','This number is the approximate number of times we reduced the size of our storage. Common values are between 2 and 4. Note that every datatype has different compression properties, but at the end the storage should be approximate reduced about that factor.');
Insert into INFO_TEXT values('Total_Storage_Amount','This is the total amount of storage of your system.');

-------------------------------------------------------
-- CUSTOMER_REPORT
-------------------------------------------------------
CREATE TABLE CUSTOMER_REPORT 
(
  CUSTOMER_ID VARCHAR2(20 BYTE),
  DATA_INFO_ID VARCHAR2(20 BYTE),
  TIMESTAMP_COMMIT DATE DEFAULT CURRENT_TIMESTAMP 
) 

