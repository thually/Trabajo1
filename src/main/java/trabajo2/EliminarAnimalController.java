package trabajo2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EliminarAnimalController implements Initializable {

    @FXML
    private Label warning;

    @FXML
    private ChoiceBox<Integer> idAnimalesDisp;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarAnimal");
    }

    @FXML
    private void eliminar(ActionEvent event) throws IOException {
        if (idAnimalesDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona el ID del Animal que desea eliminar.");
            return;
        }
        if (warning.getText().equals("ADVERTENCIA: Si está seguro que desea eliminar este Animal, vuelva a oprimir el botón 'Eliminar'.")) {
            Animal animalEliminado = Animal.animalesPorID.get(idAnimalesDisp.getValue());
            App.sistemaZoo.removeVertex(animalEliminado);
            Animal.animalesPorID.remove(animalEliminado.id);
            Animal.animalesPorAgresividad.get(animalEliminado.nivelAgresividad).remove(animalEliminado);
            Animal.animalesPorEspecie.get(animalEliminado.especie).remove(animalEliminado);
            warning.setText(" ");
            idAnimalesDisp.setValue(null);
            idAnimalesDisp.setItems(FXCollections.observableList(new ArrayList<>(Animal.animalesPorID.keySet())));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Eliminación satisfactoria");
            alert.setContentText("Se ha eliminado el Animal satisfactoriamente:\n" + animalEliminado);
            alert.showAndWait();
        } else {
            warning.setText("ADVERTENCIA: Si está seguro que desea eliminar este Animal, vuelva a oprimir el botón 'Eliminar'.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idAnimalesDisp.setItems(FXCollections.observableList(new ArrayList<>(Animal.animalesPorID.keySet())));
    }
}
