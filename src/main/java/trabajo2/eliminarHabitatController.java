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

public class eliminarHabitatController implements Initializable {

    @FXML
    private Label warning;

    @FXML
    private ChoiceBox<Integer> idHabitatsDisp;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarHabitat");
    }

    @FXML
    private void eliminar(ActionEvent event) throws IOException {
        if (idHabitatsDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona el ID del Habitat que desea eliminar.");
            return;
        }
        if (warning.getText().equals("ADVERTENCIA: Si está seguro que desea eliminar este habitat, vuelva a oprimir el botón 'Eliminar'.")) {
            Habitat habitatEliminado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
            App.sistemaZoo.removeVertex(habitatEliminado);
            Habitat.habitatsPorID.remove(habitatEliminado.id);
            Habitat.habitatsPorSuelo.get(habitatEliminado.tipoSuelo).remove(habitatEliminado);
            Habitat.habitatsPorTemperatura.get(habitatEliminado.temperatura).remove(habitatEliminado);
            warning.setText(" ");
            idHabitatsDisp.setValue(null);
            idHabitatsDisp.setItems(FXCollections.observableList(new ArrayList<>(Habitat.habitatsPorID.keySet())));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Eliminación satisfactoria");
            alert.setContentText("Se ha eliminado el Habitat satisfactoriamente:\n" + habitatEliminado);
            alert.showAndWait();
        } else {
            warning.setText("ADVERTENCIA: Si está seguro que desea eliminar este habitat, vuelva a oprimir el botón 'Eliminar'.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idHabitatsDisp.setItems(FXCollections.observableList(new ArrayList<>(Habitat.habitatsPorID.keySet())));
    }
}
