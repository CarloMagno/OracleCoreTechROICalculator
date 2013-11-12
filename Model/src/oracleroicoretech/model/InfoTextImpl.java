package oracleroicoretech.model;

import oracle.jbo.domain.RowID;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 11 13:31:29 CET 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class InfoTextImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Field {
            public Object get(InfoTextImpl obj) {
                return obj.getField();
            }

            public void put(InfoTextImpl obj, Object value) {
                obj.setField((String)value);
            }
        }
        ,
        Text {
            public Object get(InfoTextImpl obj) {
                return obj.getText();
            }

            public void put(InfoTextImpl obj, Object value) {
                obj.setText((String)value);
            }
        }
        ,
        RowID {
            public Object get(InfoTextImpl obj) {
                return obj.getRowID();
            }

            public void put(InfoTextImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(InfoTextImpl object);

        public abstract void put(InfoTextImpl object, Object value);

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
    public static final int FIELD = AttributesEnum.Field.index();
    public static final int TEXT = AttributesEnum.Text.index();
    public static final int ROWID = AttributesEnum.RowID.index();

    /**
     * This is the default constructor (do not remove).
     */
    public InfoTextImpl() {
    }

    /**
     * Gets the attribute value for Field, using the alias name Field.
     * @return the Field
     */
    public String getField() {
        return (String)getAttributeInternal(FIELD);
    }

    /**
     * Sets <code>value</code> as the attribute value for Field.
     * @param value value to set the Field
     */
    public void setField(String value) {
        setAttributeInternal(FIELD, value);
    }

    /**
     * Gets the attribute value for Text, using the alias name Text.
     * @return the Text
     */
    public String getText() {
        return (String)getAttributeInternal(TEXT);
    }

    /**
     * Sets <code>value</code> as the attribute value for Text.
     * @param value value to set the Text
     */
    public void setText(String value) {
        setAttributeInternal(TEXT, value);
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
            mDefinitionObject = EntityDefImpl.findDefObject("oracleroicoretech.model.InfoText");
        }
        return mDefinitionObject;
    }
}
