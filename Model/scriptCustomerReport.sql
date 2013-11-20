CREATE TABLE CUSTOMER_REPORT 
(
  CUSTOMER_ID VARCHAR2(20 BYTE) 
, DATA_INFO_ID VARCHAR2(20 BYTE) 
, TIMESTAMP_COMMIT DATE DEFAULT CURRENT_TIMESTAMP 
) 
LOGGING 
TABLESPACE "USERS" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS 2147483645 
  BUFFER_POOL DEFAULT 
);
