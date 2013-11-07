import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracleroicoretech.utils.ADFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.event.ClientListenerSet;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer; 
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;


public class TestBean {
    private RichInputText code;
    private RichInputText totalStorage;
    private RichShowDetailItem amountsTab;
    private RichShowDetailItem percentagesTab;
    private RichInputText advanceCompressionFactor;

    private RichInputText highPerformanceGB_perc;
    private RichInputText midRangeGB_perc;
    private RichInputText lowCostGB_perc;
    
    private RichInputText highPerformanceGB_amount;
    private RichInputText midRangeGB_amount;
    private RichInputText lowCostGB_amount;
    
    private RichInputText highPerformancePercentage_amount;
    private RichInputText midRangePercentage_amount;
    private RichInputText lowCostPercentage_amount;
    
    private RichInputText highPerformancePercentage_perc;
    private RichInputText midRangePercentage_perc;
    private RichInputText lowCostPercentage_perc;
    private RichOutputText infoText;

    public TestBean() {
        
    }

    public void setCode(RichInputText code) {
        this.code = code;
    }

    public RichInputText getCode() {
        return code;
    }
    
    public Object invokeMethodExpression(String expr, Class returnType,
                                         Class[] argTypes, Object[] args) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory =
            fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr =
            elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }
    
    public void createInsertAndCommit(ActionEvent actionEvent) {
        System.out.println("createInsertAndCommit():");
    
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();

        try{
            ValueExpression veGbhp =
            elFactory.createValueExpression(elContext, "#{bindings.GbHp1.inputValue}", Object.class); 
            if(highPerformanceGB_amount.getValue() == null){                
                veGbhp.setValue(elContext, new Number(highPerformanceGB_perc.getValue()));
            }else{
                veGbhp.setValue(elContext, highPerformanceGB_amount.getValue());  
            }

            ValueExpression veGbmp =
            elFactory.createValueExpression(elContext, "#{bindings.GbMp1.inputValue}", Object.class);
            if(midRangeGB_amount.getValue() == null){                
                veGbmp.setValue(elContext, new Number(midRangeGB_perc.getValue()));
            }else{
                veGbmp.setValue(elContext, midRangeGB_amount.getValue());  
            }
    
            ValueExpression veGbrop =
            elFactory.createValueExpression(elContext, "#{bindings.GbRop1.inputValue}", Object.class);
            if(lowCostGB_amount.getValue() == null){                
                veGbrop.setValue(elContext, new Number(lowCostGB_perc.getValue()));
            }else{
                veGbrop.setValue(elContext, lowCostGB_amount.getValue());  
            }
    
            ValueExpression vePercHp =
            elFactory.createValueExpression(elContext, "#{bindings.PercentageHp1.inputValue}", Object.class);
            if(highPerformancePercentage_amount.getValue() == null){                
                vePercHp.setValue(elContext, new Number(highPerformancePercentage_perc.getValue()));
            }else{
                vePercHp.setValue(elContext, new Number(highPerformancePercentage_amount.getValue()));  
            }
    
            ValueExpression vePercMp =
            elFactory.createValueExpression(elContext, "#{bindings.PercentageMp1.inputValue}", Object.class);
            if(midRangePercentage_amount.getValue() == null){                
                vePercMp.setValue(elContext, new Number(midRangePercentage_perc.getValue()));
            }else{
                vePercMp.setValue(elContext, new Number(midRangePercentage_amount.getValue()));  
            }
    
            ValueExpression vePercRop =
            elFactory.createValueExpression(elContext, "#{bindings.PercentageRop1.inputValue}", Object.class);
            if(lowCostPercentage_amount.getValue() == null){                
                vePercRop.setValue(elContext, new Number(lowCostPercentage_perc.getValue()));
            }else{
                vePercRop.setValue(elContext, new Number(lowCostPercentage_amount.getValue()));  
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        String expr = "#{bindings.Commit.execute}";
        invokeMethodExpression(expr, null, new Class[]{}, null);
        
        //context.getApplication().evaluateExpressionGet(context, "#{bindings.Commit.execute}", Object.class);
        System.out.println("> Commited");
    }
    
    public String checkCode() {
        String res = "";
        System.out.println("checkCode:");
        if (isValidCode((String)code.getValue())){
            String expr = "#{bindings.CreateInsert2.execute}";  
            System.out.println("* Created *");
            invokeMethodExpression(expr, null, new Class[]{}, null);
            res = (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getNext}");
        }else{
            code.setValue("");
            showErrorMessage("Code not valid.");
            res = null;
        }
        return res;
    }

    private String showErrorMessage(String messageText) {
        FacesMessage fm = new FacesMessage(messageText);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        return null;
    }

    private boolean isValidCode(String code) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer)bindings;
        DCIteratorBinding dciter = bindingsImpl.findIteratorBinding("ContactNameByCode1Iterator");
        ViewObject vo = dciter.getViewObject();
        vo.setNamedWhereClauseParam("P_CODE", code);
        vo.executeQuery();        
        return vo.getRowSet().first() != null;
    }

    public String backAndCreate() {
        String expr = "#{bindings.CreateInsert.execute}";  
        System.out.println("* Created *");
        invokeMethodExpression(expr, null, new Class[]{}, null);
        
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();
/*
        ValueExpression veGbhp =
        elFactory.createValueExpression(elContext, "#{bindings.GbHp1.inputValue}", Object.class); 
        Object value = veGbhp.getValue(elContext);
        veGbhp.setValue(elContext, value);
*/              
        return (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getPrevious}");
    }

    public void setTotalStorage(RichInputText totalStorage) {
        this.totalStorage = totalStorage;
    }

    public RichInputText getTotalStorage() {
        return totalStorage;
    }

    public void setAdvanceCompressionFactor(RichInputText advanceCompressionFactor) {
        this.advanceCompressionFactor = advanceCompressionFactor;
    }

    public RichInputText getAdvanceCompressionFactor() {
        return advanceCompressionFactor;
    }

    public void setAmountsTab(RichShowDetailItem amountsTab) {
        this.amountsTab = amountsTab;
    }

    public RichShowDetailItem getAmountsTab() {
        return amountsTab;
    }

    public void setPercentagesTab(RichShowDetailItem percentagesTab) {
        this.percentagesTab = percentagesTab;
    }

    public RichShowDetailItem getPercentagesTab() {
        return percentagesTab;
    }

    public void setHighPerformanceGB_perc(RichInputText highPerformanceGB_perc) {
        this.highPerformanceGB_perc = highPerformanceGB_perc;
    }

    public RichInputText getHighPerformanceGB_perc() {
        return highPerformanceGB_perc;
    }

    public void setMidRangeGB_perc(RichInputText midRangeGB_perc) {
        this.midRangeGB_perc = midRangeGB_perc;
    }

    public RichInputText getMidRangeGB_perc() {
        return midRangeGB_perc;
    }

    public void setLowCostGB_perc(RichInputText lowCostGB_perc) {
        this.lowCostGB_perc = lowCostGB_perc;
    }

    public RichInputText getLowCostGB_perc() {
        return lowCostGB_perc;
    }

    public void setHighPerformancePercentage_perc(RichInputText highPerformancePercentage_perc) {
        this.highPerformancePercentage_perc = highPerformancePercentage_perc;
    }

    public RichInputText getHighPerformancePercentage_perc() {
        return highPerformancePercentage_perc;
    }

    public void setMidRangePercentage_perc(RichInputText midRangePercentage_perc) {
        this.midRangePercentage_perc = midRangePercentage_perc;
    }

    public RichInputText getMidRangePercentage_perc() {
        return midRangePercentage_perc;
    }

    public void setLowCostPercentage_perc(RichInputText lowCostPercentage_perc) {
        this.lowCostPercentage_perc = lowCostPercentage_perc;
    }

    public RichInputText getLowCostPercentage_perc() {
        return lowCostPercentage_perc;
    }

    public void setHighPerformancePercentage_amount(RichInputText highPerformancePercentage_amount) {
        this.highPerformancePercentage_amount = highPerformancePercentage_amount;
    }

    public RichInputText getHighPerformancePercentage_amount() {
        return highPerformancePercentage_amount;
    }

    public void setMidRangePercentage_amount(RichInputText midRangePercentage_amount) {
        this.midRangePercentage_amount = midRangePercentage_amount;
    }

    public RichInputText getMidRangePercentage_amount() {
        return midRangePercentage_amount;
    }

    public void setLowCostPercentage_amount(RichInputText lowCostPercentage_amount) {
        this.lowCostPercentage_amount = lowCostPercentage_amount;
    }

    public RichInputText getLowCostPercentage_amount() {
        return lowCostPercentage_amount;
    }

    public void setHighPerformanceGB_amount(RichInputText highPerformanceGB_amount) {
        this.highPerformanceGB_amount = highPerformanceGB_amount;
    }

    public RichInputText getHighPerformanceGB_amount() {
        return highPerformanceGB_amount;
    }

    public void setMidRangeGB_amount(RichInputText midRangeGB_amount) {
        this.midRangeGB_amount = midRangeGB_amount;
    }

    public RichInputText getMidRangeGB_amount() {
        return midRangeGB_amount;
    }

    public void setLowCostGB_amount(RichInputText lowCostGB_amount) {
        this.lowCostGB_amount = lowCostGB_amount;
    }

    public RichInputText getLowCostGB_amount() {
        return lowCostGB_amount;
    }

    public void setInfoText(RichOutputText infoText) {
        this.infoText = infoText;
    }

    public RichOutputText getInfoText() {
        return infoText;
    }
    
    public void testActionListener(ClientEvent event){
        System.out.println("testActionListener clicked!");
        infoText.setValue("Action event text.");
    }

    public void introEvent(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Intro Event");
    }
}
