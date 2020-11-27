package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class atributosTecnicoController {
    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("busqueda");
    }
    @FXML
    private void rutaBusquedaArea(ActionEvent event) throws IOException {
        if (Tecnico.tecnicosPorArea.isEmpty()) {
            warning.setText("MENSAJE: Aún no se encuentran tecnicos registrados");
            return;
        } else {
            App.setRoot("busquedaArea");
        }
    }

    @FXML
    private void rutaBusquedaSueldo(ActionEvent event) throws IOException {
        if (Tecnico.tecnicosPorSueldo.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
            return;
        } else {
            App.setRoot("busquedaSueldo");
        }
    }


}
