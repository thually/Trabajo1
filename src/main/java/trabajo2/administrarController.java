package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class administrarController {
    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("menuPrincipal");
    }
    @FXML
    private void rutaAdministrarAnimal(ActionEvent event) throws IOException {
        App.setRoot("administrarAnimal");
    }
    @FXML
    private void rutaAdministrarHabitat(ActionEvent event) throws IOException {
        App.setRoot("administrarHabitat");
    }
    @FXML
    private void rutaAdministrarTecnico(ActionEvent event) throws IOException {
        App.setRoot("administrarTecnico");
    }

}
