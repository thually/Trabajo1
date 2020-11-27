package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class atributosHabitatController {
    @FXML
    private Label warning;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("busqueda");
    }

    @FXML
    private void rutaBuscarTemperatura(ActionEvent event) throws IOException {
        if (Habitat.habitatsPorTemperatura.isEmpty()) {
            warning.setText("MENSAJE: Aún no se encuentran habitats registrados");
        } else {
            warning.setText("");
            App.setRoot("busquedaTemperatura");
        }
    }
    @FXML
    private void rutaBuscarSueloH(ActionEvent event) throws IOException {

        if (Habitat.habitatsPorSuelo.isEmpty()) {
            warning.setText("MENSAJE: Aún no se encuentran habitats registrados");
        } else {
            App.setRoot("busquedaSueloH");
        }
    }

}

