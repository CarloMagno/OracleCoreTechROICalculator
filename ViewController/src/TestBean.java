import javax.faces.event.ActionEvent;

public class TestBean {
    public TestBean() {
    }

    public void getNextAction(ActionEvent actionEvent) {
        System.out.println("NEXT CLICKED!");
    }
}
