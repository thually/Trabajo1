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
    private void rutaBuscarAnilar(ActionEvent event) throws IOException {
        App.setRoot("buscarAnimal");
    }
    @FXML
    private void rutaAdministrarHabitat(ActionEvent event) throws IOException {
        App.setRoot("administrHabitat");
    }
    @FXML
    private void rutaAdministrarTecnico(ActionEvent event) throws IOException {
        App.setRoot("administrarTecnico");
    }

}
