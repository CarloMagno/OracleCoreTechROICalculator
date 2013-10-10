import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracleroicoretech.utils.ADFUtils;

public class TestBean {
    private RichInputText code;

    public TestBean() {
    }

    public void getNextAction(ActionEvent actionEvent) {
        System.out.println("CLICKED1!");
        
        ADFUtils.invokeEL("#{bindings.ExecuteWithParams.execute}", new Class[]{String.class}, new Object[]{code.getValue()});
        System.out.println("CLICKED2!");
        
        ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getNext}");
        System.out.println("CLICKED3!");
    }

    public void setCode(RichInputText code) {
        this.code = code;
    }

    public RichInputText getCode() {
        return code;
    }

    public String fromStartGoNext() {
  
        return null;
    }
}
