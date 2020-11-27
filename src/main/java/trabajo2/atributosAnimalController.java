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
    @FXML
    private void rutaBusquedaEspecie(ActionEvent event) throws IOException {
        if (Animal.animalesPorAgresividad.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
            return;
        } else {
            App.setRoot("busquedaEspecie");
        }
    }
    @FXML
    private void rutaBusquedaAgresividad(ActionEvent event) throws IOException {
        if (Animal.animalesPorAgresividad.isEmpty()){
            warning.setText("MENSAJE: Aún no se encuentran animales registrados");
            return;
        } else {
            App.setRoot("busquedaAgresividad");
        }
    }


}
