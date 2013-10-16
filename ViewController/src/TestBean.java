import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracleroicoretech.utils.ADFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer; 
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;


public class TestBean {
    private RichInputText code;

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
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("CLICKED!");

        String expr = "#{bindings.CreateInsert.execute}";  
        invokeMethodExpression(expr, null, new Class[]{}, null);  
        System.out.println("> Created");
        
        expr = "#{bindings.Commit.execute}";
        invokeMethodExpression(expr, null, new Class[]{}, null);
        
        //context.getApplication().evaluateExpressionGet(context, "#{bindings.Commit.execute}", Object.class);
        System.out.println("> Commited");
    }
    
    public String checkCode() {
        String res = "";
        System.out.println("next_action");
        if (isValidCode((String)code.getValue())){
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
}
