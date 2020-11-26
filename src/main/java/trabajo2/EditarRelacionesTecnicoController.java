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

public class EditarRelacionesTecnicoController implements Initializable {

    @FXML
    public Label Label1;

    @FXML
    private Button crearRelacion;

    @FXML
    public Button eliminarRelacion;

    @FXML
    private ChoiceBox<Integer> idTecnicosDisp;

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
        Tecnico tecnicoEditado = Tecnico.tecnicosPorID.get(idTecnicosDisp.getValue());
        if (accion.getValue().equals("Eliminar")){
            ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, tecnicoEditado));
            ArrayList<Integer> IDparaEliminar = new ArrayList<>();
            for (Object obj : objAdyacentes){
                if (obj instanceof Habitat){
                    Habitat aux = (Habitat) obj;
                    IDparaEliminar.add(aux.id);
                }
            }
            if (IDparaEliminar.isEmpty()){
                warning.setText("MENSAJE: Este Tecnico no tiene relaciones existentes con ningún Habitat.");
                return;
            }

            Label1.setText("Finalmente, selecciones el ID del Habita con el cual quiere eliminar la relación:");
            idParaEditarDisp.setItems(FXCollections.observableList(IDparaEliminar));
            idParaEditarDisp.setVisible(true);
            eliminarRelacion.setVisible(true);
            crearRelacion.setVisible(false);


        } else if (accion.getValue().equals("Crear")){
            ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, tecnicoEditado));
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
        idTecnicosDisp.setItems(FXCollections.observableList(new ArrayList<>(Tecnico.tecnicosPorID.keySet())));
    }

    @FXML
    private void setAccionIDTec(ActionEvent event) throws IOException {
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
        App.setRoot("editarTecnico");
    }

    @FXML
    public void AccionEliminarRelacion(ActionEvent event) throws IOException {
        if (idParaEditarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        int idHabitat = idParaEditarDisp.getValue();
        Habitat habitat =Habitat.habitatsPorID.get(idHabitat);
        Tecnico tecnicoEditado = Tecnico.tecnicosPorID.get(idTecnicosDisp.getValue());
        App.sistemaZoo.removeEdge(habitat, tecnicoEditado);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Eliminación satisfactoria");
        alert.setContentText("Se ha eliminado la relación de el Tecnico satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarTecnico");
    }

    @FXML
    public void AccionCrearRelacion(ActionEvent event) throws IOException {
        if (idParaEditarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        int idHabitat = idParaEditarDisp.getValue();
        Habitat habitat =Habitat.habitatsPorID.get(idHabitat);
        Tecnico tecnicoEditado = Tecnico.tecnicosPorID.get(idTecnicosDisp.getValue());
        App.sistemaZoo.addEdge(habitat, tecnicoEditado);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creación satisfactoria");
        alert.setContentText("Se ha creado la nueva relación con el Habitat satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarTecnico");
    }
}
