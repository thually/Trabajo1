package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class menuPrincipalController {
    public Button closeButton;
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void routeAdministrar(ActionEvent event) throws IOException {
        trabajo2.App.setRoot("administrar");
    }

    @FXML
    private void routeBusqueda(ActionEvent event) throws IOException {
        trabajo2.App.setRoot("busqueda");
    }
}
