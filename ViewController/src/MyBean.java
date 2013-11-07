import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.render.ClientEvent;

public class MyBean {

    private String inputValue;

    public MyBean() {
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void handleServerEvent(ClientEvent clientEvent) {
         // print the message to the console 
         System.out.println("intro!"); 

    }

    public void myMethod(ValueChangeEvent valueChangeEvent) {
        System.out.println("Intro");
    }

    public void it1_validator(FacesContext facesContext,
                              UIComponent uIComponent, Object object) {
        System.out.println("Intro");
    }
}
