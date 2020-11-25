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

public class EliminarTecnicoController implements Initializable {

    @FXML
    private Label warning;

    @FXML
    private ChoiceBox<Integer> idTecnicosDisp;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarTecnico");
    }

    @FXML
    private void eliminar(ActionEvent event) throws IOException {
        if (idTecnicosDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona la cédula del Tecnico que desea eliminar.");
            return;
        }
        if (warning.getText().equals("ADVERTENCIA: Si está seguro que desea eliminar este Tecnico, vuelva a oprimir el botón 'Eliminar'.")) {
            Tecnico tecnicoEliminado = Tecnico.tecnicosPorID.get(idTecnicosDisp.getValue());
            App.sistemaZoo.removeVertex(tecnicoEliminado);
            Tecnico.tecnicosPorID.remove(tecnicoEliminado.cedula);
            Tecnico.tecnicosPorArea.get(tecnicoEliminado.area).remove(tecnicoEliminado);
            Tecnico.tecnicosPorSueldo.get(tecnicoEliminado.sueldo).remove(tecnicoEliminado);
            warning.setText(" ");
            idTecnicosDisp.setValue(null);
            idTecnicosDisp.setItems(FXCollections.observableList(new ArrayList<>(Tecnico.tecnicosPorID.keySet())));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Eliminación satisfactoria");
            alert.setContentText("Se ha eliminado el Tecnico satisfactoriamente:\n" + tecnicoEliminado);
            alert.showAndWait();
        } else {
            warning.setText("ADVERTENCIA: Si está seguro que desea eliminar este Tecnico, vuelva a oprimir el botón 'Eliminar'.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTecnicosDisp.setItems(FXCollections.observableList(new ArrayList<>(Tecnico.tecnicosPorID.keySet())));
    }
}
