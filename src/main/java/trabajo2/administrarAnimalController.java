package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class administrarAnimalController {

    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrar");
    }

    @FXML
    private void rutaVerAnimal(ActionEvent event) throws IOException {
        if (Animal.animalesPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
        } else {
            warning.setText("");
            App.setRoot("verAnimal");
        }
    }
    @FXML
    private void rutaCrearAnimal(ActionEvent event) throws IOException {
        App.setRoot("crearAnimal");
    }
    @FXML
    private void rutaEditarAnimal(ActionEvent event) throws IOException {
        if (Animal.animalesPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
        } else {
            warning.setText("");
            App.setRoot("editarAnimal");
        }
    }
    @FXML
    private void rutaEliminarAnimal(ActionEvent event) throws IOException {
        if (Animal.animalesPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
        } else {
            warning.setText("");
            App.setRoot("eliminarAnimal");
        }
    }
}
