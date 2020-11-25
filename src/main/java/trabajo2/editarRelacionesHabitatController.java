package trabajo2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private ChoiceBox<String> AnimalOTecnico;

    @FXML
    private Label Label1;

    @FXML
    private ChoiceBox<Integer> idParaEliminarDisp;

    @FXML
    private Button eliminarRelacion;

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

        if (accion.getValue().equals("Eliminar")){
            Eliminar();
        } else if (accion.getValue().equals("Crear")){
            eliminarLabel.setVisible(false);
            AnimalOTecnico.setVisible(false);
            idParaEliminarDisp.setVisible(false);
            warning.setText(" ");
            Label1.setText(" ");
            eliminarRelacion.setVisible(false);
            warning.setText(" ");

            Crear();


        }
    }

    private void Eliminar(){
        eliminarLabel.setVisible(true);
        AnimalOTecnico.setVisible(true);

    }

    @FXML
    private void AccionTecnicoOAnimal(ActionEvent event) throws IOException {
        warning.setText(" ");

        if (AnimalOTecnico.getValue().equals("Animal")){
            ArrayList<Integer> IDDispParaEliminar = IDParaEliminarDisponibles("Animal");
            if (IDDispParaEliminar.isEmpty()){
                warning.setText("MENSAJE: Este Habitat no tiene relaciones existentes con ningún Animal.");
                return;
            }
            idParaEliminarDisp.setItems(FXCollections.observableList(IDDispParaEliminar));
            idParaEliminarDisp.setVisible(true);
            Label1.setText("Finalmente, elija el ID del Animal con el cual existe la relación que desea eliminar:");
            eliminarRelacion.setVisible(true);


        } else if (AnimalOTecnico.getValue().equals("Tecnico")){
            ArrayList<Integer> IDDispParaEliminar = IDParaEliminarDisponibles("Tecnico");
            if (IDDispParaEliminar.isEmpty()){
                warning.setText("MENSAJE: Este Habitat no tiene relaciones existentes con ningún Tecnico.");
                return;
            }
            idParaEliminarDisp.setItems(FXCollections.observableList(IDDispParaEliminar));
            idParaEliminarDisp.setVisible(true);
            Label1.setText("Finalmente, elija la cédula del Tecnico con el cual existe la relación que desea eliminar:");
            eliminarRelacion.setVisible(true);

        }
    }

    @FXML
    private void AccionEliminarRelacion(ActionEvent event) throws IOException {
        if (idParaEliminarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        if (AnimalOTecnico.getValue().equals("Animal")){
            int idAnimal = idParaEliminarDisp.getValue();
            Animal animal = Animal.animalesPorID.get(idAnimal);
            Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
            App.sistemaZoo.removeEdge(animal, habitadEditado);

        } else if (AnimalOTecnico.getValue().equals("Tecnico")){
            int idTecnico = idParaEliminarDisp.getValue();
            Tecnico tecnico = Tecnico.tecnicosPorID.get(idTecnico);
            Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
            App.sistemaZoo.removeEdge(tecnico, habitadEditado);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Eliminación satisfactoria");
        alert.setContentText("Se ha eliminado la relación de el Habitat satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarHabitat");
    }

    private ArrayList<Integer> IDParaEliminarDisponibles (String AnimalOTecnico){
        ArrayList<Integer> IDparaEliminar = new ArrayList<>();
        Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
        Set<DefaultEdge> relaciones = App.sistemaZoo.edgesOf(habitadEditado);
        if (AnimalOTecnico.equals("Animal")) {
            for (DefaultEdge edge : relaciones){
                Object currObj = App.sistemaZoo.getEdgeTarget(edge);
                if (currObj instanceof Animal){
                    Animal currAni = (Animal) currObj;
                    IDparaEliminar.add(currAni.id);
                }
            }
        } else if (AnimalOTecnico.equals("Tecnico")){
            for (DefaultEdge edge : relaciones){
                Object currObj = App.sistemaZoo.getEdgeTarget(edge);
                if (currObj instanceof Tecnico){
                    Tecnico currTec = (Tecnico) currObj;
                    IDparaEliminar.add(currTec.cedula);
                }
            }
        }

        return IDparaEliminar;
    }

    private void Crear(){
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
        AnimalOTecnico.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("Animal", "Tecnico"))));

    }

    public void clean(){
        accion.setValue(null);
        accion.setItems(FXCollections.observableList(new ArrayList<>()));
        eliminarLabel.setVisible(false);
        AnimalOTecnico.setVisible(false);
        Label1.setText("");
        idParaEliminarDisp.setVisible(false);
        eliminarRelacion.setVisible(false);
        warning.setText(" ");

    }
}
