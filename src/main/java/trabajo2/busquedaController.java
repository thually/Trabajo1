package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class busquedaController {
    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("menuPrincipal");
    }
    @FXML
    private void rutaBuscarAnimal(ActionEvent event) throws IOException {
        App.setRoot("atributosAnimal");
    }
    @FXML
    private void rutaBuscarHabitat(ActionEvent event) throws IOException {
        App.setRoot("atributosHabitat");
    }
    @FXML
    private void rutaBuscarTecnico(ActionEvent event) throws IOException {
        App.setRoot("atributosTecnico");
    }

}
