package trabajo2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.jgrapht.Graphs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EditarRelacionesAnimalController implements Initializable {

    @FXML
    public Label Label1;

    @FXML
    private Button crearRelacion;

    @FXML
    public Button eliminarRelacion;

    @FXML
    private ChoiceBox<Integer> idAnimalesDisp;

    @FXML
    private ChoiceBox<String> accion;

    @FXML
    private ChoiceBox<Integer> idParaEditarDisp;

    @FXML
    private Label warning;

    @FXML
    private void elegirEliminarEditar(ActionEvent event) throws IOException {
        warning.setText("");

        if (accion.getValue() == null) {
            return;
        }
        Animal animalEditado = Animal.animalesPorID.get(idAnimalesDisp.getValue());
        if (accion.getValue().equals("Eliminar")){
            ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, animalEditado));
            ArrayList<Integer> IDparaEliminar = new ArrayList<>();
            for (Object obj : objAdyacentes){
                if (obj instanceof Habitat){
                    Habitat aux = (Habitat) obj;
                    IDparaEliminar.add(aux.id);
                }
            }
            if (IDparaEliminar.isEmpty()){
                warning.setText("MENSAJE: Este Animal no tiene relaciones existentes con ningún Habitat.");
                return;
            }

            Label1.setText("Finalmente, selecciones el ID del Animal con el cual quiere eliminar la relación:");
            idParaEditarDisp.setItems(FXCollections.observableList(IDparaEliminar));
            idParaEditarDisp.setVisible(true);
            eliminarRelacion.setVisible(true);
            crearRelacion.setVisible(false);


        } else if (accion.getValue().equals("Crear")){
            ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, animalEditado));
            ArrayList<Integer> IDparaEliminar = new ArrayList<>();
            for (Object obj : objAdyacentes){
                if (obj instanceof Habitat){
                    Habitat aux = (Habitat) obj;
                    IDparaEliminar.add(aux.id);
                }
            }

            ArrayList<Integer> IDDispParaCrear = new ArrayList<>();
            for (int idDis : Habitat.habitatsPorID.keySet()){
                if (!IDparaEliminar.contains(idDis)){
                    IDDispParaCrear.add(idDis);
                }
            }
            if (IDDispParaCrear.isEmpty()){
                warning.setText("MENSAJE: No hay Habitats disponibles para crear esta nueva relación.");
                return;
            }

            idParaEditarDisp.setItems(FXCollections.observableList(IDDispParaCrear));

            Label1.setText("Finalmente, selecciones el ID del Habita con el cual quiere crear la nueva relación:");
            idParaEditarDisp.setVisible(true);
            crearRelacion.setVisible(true);
            eliminarRelacion.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idAnimalesDisp.setItems(FXCollections.observableList(new ArrayList<>(Animal.animalesPorID.keySet())));
    }

    @FXML
    private void setAccionIDHab(ActionEvent event) throws IOException {
        accion.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("Crear", "Eliminar"))));
        warning.setText("");
        Label1.setText("");
        idParaEditarDisp.setVisible(false);
        eliminarRelacion.setVisible(false);
        crearRelacion.setVisible(false);
        warning.setText("");

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("editarAnimal");
    }

    @FXML
    public void AccionEliminarRelacion(ActionEvent event) throws IOException {
        if (idParaEditarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        int idHabitat = idParaEditarDisp.getValue();
        Habitat habitat =Habitat.habitatsPorID.get(idHabitat);
        Animal animaEditado = Animal.animalesPorID.get(idAnimalesDisp.getValue());
        App.sistemaZoo.removeEdge(habitat, animaEditado);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Eliminación satisfactoria");
        alert.setContentText("Se ha eliminado la relación de el Animal satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarAnimal");
    }

    @FXML
    public void AccionCrearRelacion(ActionEvent event) throws IOException {
        if (idParaEditarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        int idHabitat = idParaEditarDisp.getValue();
        Habitat habitat =Habitat.habitatsPorID.get(idHabitat);
        Animal animalEditado = Animal.animalesPorID.get(idAnimalesDisp.getValue());
        // El siguiente for es para asegurarnos que un animal solo puede estar relacionado con un habitat
        for (Habitat hab : Habitat.habitatsPorID.values()){
            if (App.sistemaZoo.containsEdge(animalEditado, hab)){
                App.sistemaZoo.removeEdge(animalEditado, hab);
            }
        }
        App.sistemaZoo.addEdge(habitat, animalEditado);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creación satisfactoria");
        alert.setContentText("Se ha creado la nueva relación con el Habitat satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarAnimal");
    }
}
