package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class atributosAnimalController {
    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("busqueda");
    }
}
