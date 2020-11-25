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

public class editarRelacionesHabitatController implements Initializable {

    @FXML
    private Label eliminarLabel;

    @FXML
    private Label crearLabel;

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
    private Button crearRelacion;

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
            crearLabel.setVisible(false);
            AnimalOTecnico.setValue(null);
            idParaEliminarDisp.setVisible(false);
            warning.setText(" ");
            Label1.setText(" ");
            crearRelacion.setVisible(false);
            warning.setText(" ");
            Eliminar();
        } else if (accion.getValue().equals("Crear")){
            AnimalOTecnico.setValue(null);
            eliminarLabel.setVisible(false);
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
        if (AnimalOTecnico.getValue() == null){
            return;
        }

        if (accion.getValue().equals("Eliminar")) {
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
        } else if (accion.getValue().equals("Crear")){
            if (AnimalOTecnico.getValue().equals("Animal")){
                ArrayList<Integer> IDDispParaEliminar = IDParaEliminarDisponibles("Animal");
                ArrayList<Integer> IDDispParaCrear = new ArrayList<>();
                for (int idDis : Animal.animalesPorID.keySet()){
                    if (!IDDispParaEliminar.contains(idDis)){
                        IDDispParaCrear.add(idDis);
                    }
                }
                if (IDDispParaCrear.isEmpty()){
                    warning.setText("MENSAJE: No hay Animales disponibles para crear esta nueva relación.");
                    return;
                }
                idParaEliminarDisp.setItems(FXCollections.observableList(IDDispParaCrear));
                idParaEliminarDisp.setVisible(true);
                Label1.setText("Finalmente, elija el ID del Animal con el cual quiere crear la relación:");
                crearRelacion.setVisible(true);


            } else if (AnimalOTecnico.getValue().equals("Tecnico")){
                ArrayList<Integer> IDDispParaEliminar = IDParaEliminarDisponibles("Tecnico");
                ArrayList<Integer> IDDispParaCrear = new ArrayList<>();
                for (int idDis : Tecnico.tecnicosPorID.keySet()){
                    if (!IDDispParaEliminar.contains(idDis)){
                        IDDispParaCrear.add(idDis);
                    }
                }
                if (IDDispParaCrear.isEmpty()){
                    warning.setText("MENSAJE: No hay Tecnicos disponibles para crear esta nueva relación.");
                    return;
                }
                idParaEliminarDisp.setItems(FXCollections.observableList(IDDispParaCrear));
                idParaEliminarDisp.setVisible(true);
                Label1.setText("Finalmente, elija el ID del Tecnico con el cual quiere crear la relación:");
                crearRelacion.setVisible(true);

            }
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

    @FXML
    private void AccionCrearRelacion(ActionEvent event) throws IOException {
        if (idParaEliminarDisp.getValue() == null){
            warning.setText("MENSAJE: Último campo vacío.");
            return;
        }
        if (AnimalOTecnico.getValue().equals("Animal")){
            int idAnimal = idParaEliminarDisp.getValue();
            Animal animal = Animal.animalesPorID.get(idAnimal);
            Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());

            // El siguiente for es para asegurarnos que un animal solo puede estar relacionado con un habitat
            for (Habitat hab : Habitat.habitatsPorID.values()){
                if (App.sistemaZoo.containsEdge(animal, hab)){
                    App.sistemaZoo.removeEdge(animal, hab);
                }
            }

            App.sistemaZoo.addEdge(habitadEditado, animal);


        } else if (AnimalOTecnico.getValue().equals("Tecnico")){
            int idTecnico = idParaEliminarDisp.getValue();
            Tecnico tecnico = Tecnico.tecnicosPorID.get(idTecnico);
            Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
            App.sistemaZoo.addEdge(habitadEditado, tecnico);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creación satisfactoria");
        alert.setContentText("Se ha creado la nueva relación con el Habitat satisfactoriamente:\n");
        alert.showAndWait();
        App.setRoot("editarHabitat");
    }

    private ArrayList<Integer> IDParaEliminarDisponibles (String AnimalOTecnico){
        ArrayList<Integer> IDparaEliminar = new ArrayList<>();
        Habitat habitadEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
        ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, habitadEditado));

        if (AnimalOTecnico.equals("Animal")) {
            for (Object currObj : objAdyacentes){
                if (currObj instanceof Animal){
                    Animal currAni = (Animal) currObj;
                    IDparaEliminar.add(currAni.id);
                }
            }
        } else if (AnimalOTecnico.equals("Tecnico")){
            for (Object currObj : objAdyacentes){
                if (currObj instanceof Tecnico){
                    Tecnico currTec = (Tecnico) currObj;
                    IDparaEliminar.add(currTec.cedula);
                }
            }
        }

        return IDparaEliminar;
    }

    private void Crear(){
        crearLabel.setVisible(true);
        AnimalOTecnico.setVisible(true);
    }

    @FXML
    private void setAccionIDHab(ActionEvent event) throws IOException {
        clean();
        accion.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("Crear", "Eliminar"))));
        warning.setText("");
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
        crearLabel.setVisible(false);
        AnimalOTecnico.setVisible(false);
        Label1.setText("");
        idParaEliminarDisp.setVisible(false);
        eliminarRelacion.setVisible(false);
        crearRelacion.setVisible(false);
        warning.setText(" ");

    }
}
