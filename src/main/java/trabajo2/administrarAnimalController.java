package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class administrarAnimalController {
    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrar");
    }
}
