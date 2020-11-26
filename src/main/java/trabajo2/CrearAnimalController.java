package trabajo2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CrearAnimalController implements Initializable {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField especieTextField;
    @FXML
    private ChoiceBox<Integer> agresividadCB;
    @FXML
    private TextField alimentacionTextField;

    @FXML
    private Label warning;

    @FXML
    private void crear(ActionEvent event) throws IOException {
        if (idTextField.getText().trim().isEmpty() || especieTextField.getText().trim().isEmpty() || agresividadCB.getValue() == null
                || alimentacionTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: No pueden haber campos vacíos.");
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idTextField.getText().trim());
        }catch (Exception e){
            warning.setText("El ID del Animal debe que ser un número entero");
            return;
        }
        if (Animal.animalesPorID.containsKey(id)){
            warning.setText("Ya existe un Animal con este ID registrado en el sistema");
            return;
        }

        int nivelAgrecividad = agresividadCB.getValue();
        String especie = especieTextField.getText().trim().toLowerCase();
        String alimentacion = alimentacionTextField.getText().trim().toLowerCase();

        Animal nuevoAnimal = new Animal(id, especie, nivelAgrecividad, alimentacion);
        App.sistemaZoo.addVertex(nuevoAnimal);
        Animal.animalesPorID.put(nuevoAnimal.id, nuevoAnimal);
        if (!Animal.animalesPorEspecie.containsKey(nuevoAnimal.especie)){
            Animal.animalesPorEspecie.put(nuevoAnimal.especie, new LinkedList<>());
        }
        Animal.animalesPorEspecie.get(nuevoAnimal.especie).add(nuevoAnimal);
        if (!Animal.animalesPorAgresividad.containsKey(nuevoAnimal.nivelAgresividad)){
            Animal.animalesPorAgresividad.put(nuevoAnimal.nivelAgresividad, new LinkedList<>());
        }
        Animal.animalesPorAgresividad.get(nuevoAnimal.nivelAgresividad).add(nuevoAnimal);

        warning.setText("");

        idTextField.setText("");
        alimentacionTextField.setText("");
        especieTextField.setText("");
        agresividadCB.setValue(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creacion satisfactoria");
        alert.setContentText("Se ha ingresado el Animal satisfactoriamente:\n" + nuevoAnimal);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarAnimal");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        agresividadCB.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList(1,2,3,4,5))));
    }
}
