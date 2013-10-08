import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class TestBean {
    private RichInputText code;

    public TestBean() {
    }

    public void getNextAction(ActionEvent actionEvent) {
        System.out.println("NEXT CLICKED!");
    }

    public void setCode(RichInputText code) {
        this.code = code;
    }

    public RichInputText getCode() {
        return code;
    }
}
