package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class administrarHabitatController {

    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrar");
    }

    @FXML
    private void rutaVerHabitat(ActionEvent event) throws IOException {
        if (Habitat.habitatsPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran habitats registrados");
        } else {
            warning.setText("");
            App.setRoot("verHabitat");
        }
    }
    @FXML
    private void rutaCrearHabitat(ActionEvent event) throws IOException {
        App.setRoot("crearHabitat");
    }
    @FXML
    private void rutaEditarHabitat(ActionEvent event) throws IOException {
        if (Habitat.habitatsPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran habitats registrados");
        } else {
            warning.setText("");
            App.setRoot("editarHabitat");
        }
    }
    @FXML
    private void rutaEliminarHabitat(ActionEvent event) throws IOException {
        if (Habitat.habitatsPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran habitats registrados");
        } else {
            warning.setText("");
            App.setRoot("eliminarHabitat");
        }
    }
}
