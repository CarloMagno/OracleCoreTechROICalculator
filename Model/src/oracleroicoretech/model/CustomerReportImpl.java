package oracleroicoretech.model;

import oracle.jbo.AttributeList;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.RowID;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 19 16:41:45 CET 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CustomerReportImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CustomerId {
            public Object get(CustomerReportImpl obj) {
                return obj.getCustomerId();
            }

            public void put(CustomerReportImpl obj, Object value) {
                obj.setCustomerId((String)value);
            }
        }
        ,
        DataInfoId {
            public Object get(CustomerReportImpl obj) {
                return obj.getDataInfoId();
            }

            public void put(CustomerReportImpl obj, Object value) {
                obj.setDataInfoId((String)value);
            }
        }
        ,
        TimestampCommit {
            public Object get(CustomerReportImpl obj) {
                return obj.getTimestampCommit();
            }

            public void put(CustomerReportImpl obj, Object value) {
                obj.setTimestampCommit((Date)value);
            }
        }
        ,
        RowID {
            public Object get(CustomerReportImpl obj) {
                return obj.getRowID();
            }

            public void put(CustomerReportImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CustomerReportImpl object);

        public abstract void put(CustomerReportImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int CUSTOMERID = AttributesEnum.CustomerId.index();
    public static final int DATAINFOID = AttributesEnum.DataInfoId.index();
    public static final int TIMESTAMPCOMMIT = AttributesEnum.TimestampCommit.index();
    public static final int ROWID = AttributesEnum.RowID.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CustomerReportImpl() {
    }

    /**
     * Gets the attribute value for CustomerId, using the alias name CustomerId.
     * @return the CustomerId
     */
    public String getCustomerId() {
        return (String)getAttributeInternal(CUSTOMERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CustomerId.
     * @param value value to set the CustomerId
     */
    public void setCustomerId(String value) {
        setAttributeInternal(CUSTOMERID, value);
    }

    /**
     * Gets the attribute value for DataInfoId, using the alias name DataInfoId.
     * @return the DataInfoId
     */
    public String getDataInfoId() {
        return (String)getAttributeInternal(DATAINFOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DataInfoId.
     * @param value value to set the DataInfoId
     */
    public void setDataInfoId(String value) {
        setAttributeInternal(DATAINFOID, value);
    }

    /**
     * Gets the attribute value for TimestampCommit, using the alias name TimestampCommit.
     * @return the TimestampCommit
     */
    public Date getTimestampCommit() {
        return (Date)getAttributeInternal(TIMESTAMPCOMMIT);
    }

    /**
     * Sets <code>value</code> as the attribute value for TimestampCommit.
     * @param value value to set the TimestampCommit
     */
    public void setTimestampCommit(Date value) {
        setAttributeInternal(TIMESTAMPCOMMIT, value);
    }

    /**
     * Gets the attribute value for RowID, using the alias name RowID.
     * @return the RowID
     */
    public RowID getRowID() {
        return (RowID)getAttributeInternal(ROWID);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("oracleroicoretech.model.CustomerReport");
        }
        return mDefinitionObject;
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}
