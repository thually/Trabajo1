package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class administrarTecnicoController {

    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrar");
    }

    @FXML
    private void rutaVerTecnico(ActionEvent event) throws IOException {
        if (Tecnico.tecnicosPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran Tecnicos registrados");
        } else {
            warning.setText("");
            App.setRoot("verTecnico");
        }
    }
    @FXML
    private void rutaCrearTecnico(ActionEvent event) throws IOException {
        App.setRoot("crearTecnico");
    }
    @FXML
    private void rutaEditarTecnico(ActionEvent event) throws IOException {
        if (Tecnico.tecnicosPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran tecnicos registrados");
        } else {
            warning.setText("");
            App.setRoot("editarTecnico");
        }
    }
    @FXML
    private void rutaEliminarTecnico(ActionEvent event) throws IOException {
        if (Tecnico.tecnicosPorID.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran tecnicos registrados");
        } else {
            warning.setText("");
            App.setRoot("eliminarTecnico");
        }
    }
}
