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

    public void getNextAction(ActionEvent actionEvent) {
        System.out.println("CLICKED1!");
      /*  
        ADFUtils.invokeEL("#{bindings.ExecuteWithParams.execute}", new Class[]{String.class}, new Object[]{code.getValue()});
        System.out.println("CLICKED2!");
        
        ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getNext}");
        System.out.println("CLICKED3!");
      */
    }

    public void setCode(RichInputText code) {
        this.code = code;
    }

    public RichInputText getCode() {
        return code;
    }
    
    public String next_action() {
        String res = "";
        System.out.println("next_action");
        if (isValidCode((String)code.getValue())){
            res = (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getNext}");
        }else{
            // Send to error page or launch pop-up error message.
            res = null;
        }
        
        return res;
    }

    private boolean isValidCode(String code) {
        /*
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        Object result = operationBinding.execute();

        System.out.println("\tisValidCode("+code+"): "+!(result == null));

        return !(result == null);
        */
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer)bindings;
        DCIteratorBinding dciter =
            bindingsImpl.findIteratorBinding("ContactNameByCode1Iterator"); //access the iterator by its ID value in the PageDef file
        ViewObject vo = dciter.getViewObject();
        vo.setNamedWhereClauseParam("P_CODE", code); //enter your value
        vo.executeQuery();
        
        System.out.println(vo.getRowSet().first());
        
        return vo.getRowSet().first() != null;
    }
}
