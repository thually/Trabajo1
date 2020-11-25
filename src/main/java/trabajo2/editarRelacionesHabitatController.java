package trabajo2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;

public class editarRelacionesHabitatController implements Initializable {

    @FXML
    private Label eliminarLabel;

    @FXML
    private Label warning;

    @FXML
    private ChoiceBox<Integer> idHabitatsDisp;

    @FXML
    private ChoiceBox<String> accion;

    @FXML
    private ChoiceBox<Integer> idTecnicosDisp;

    @FXML
    private ChoiceBox<Integer> idAnimalesDisp;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("editarHabitat");
    }

    @FXML
    private void elegirEliminarEditar(ActionEvent event) throws IOException {
        if (accion.getValue() == null){
            return;
        }
        Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());

        if (accion.getValue().equals("Eliminar")){
            eliminarLabel.setVisible(true);
        }
    }

    @FXML
    private void setAccionIDHab(ActionEvent event) throws IOException {
        Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
        Set<DefaultEdge> relaciones = App.sistemaZoo.edgesOf(habitadEditado);
        clean();

        if (relaciones.isEmpty()){
            warning.setText("MENSAJE: Este Habitat no tiene relaciones existentes.");

        } else {
            accion.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("Crear", "Eliminar"))));
            warning.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idHabitatsDisp.setItems(FXCollections.observableList(new ArrayList<>(Habitat.habitatsPorID.keySet())));
    }

    public void clean(){
        accion.setValue(null);
        accion.setItems(FXCollections.observableList(new ArrayList<>()));
        eliminarLabel.setVisible(false);
    }
}
