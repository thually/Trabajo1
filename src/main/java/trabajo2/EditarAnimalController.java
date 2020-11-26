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

public class EditarAnimalController implements Initializable {

    @FXML
    private TextField idTextField;
    @FXML
    private TextField especieTextField;
    @FXML
    private ChoiceBox<Integer> agresividadCB;
    @FXML
    private TextField alimentacionTextField;
    @FXML
    private ChoiceBox<Integer> idAnimalesDisp;
    @FXML
    private Label warning;

    @FXML
    private void editar(ActionEvent event) throws IOException{
        if (idTextField.getText().trim().isEmpty() && especieTextField.getText().trim().isEmpty() && agresividadCB.getValue() == null
                && alimentacionTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: Todos los campos se encuentran vacíos.");
            return;
        }
        if (idAnimalesDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona el ID del Animal que desea editar.");
            return;
        }

        Animal animalEditado = Animal.animalesPorID.get(idAnimalesDisp.getValue());

        int id=0;
        if (!idTextField.getText().trim().isEmpty()) {
            try{
                id = Integer.parseInt(idTextField.getText().trim());
                if (Animal.animalesPorID.containsKey(id)){
                    warning.setText("Ya existe un Animal con este ID registrado en el sistema");
                    return;
                }
            }catch (Exception e){
                warning.setText("El ID del Animal debe que ser un número entero");
                return;
            }
        }

        String especie = especieTextField.getText().trim().toLowerCase();
        String alimentacion = alimentacionTextField.getText().trim().toLowerCase();

        if (idTextField.getText().trim().isEmpty()){}
        else {
            Animal.animalesPorID.remove(animalEditado.id);
            animalEditado.id = id;
            Animal.animalesPorID.put(id, animalEditado);
        }
        if (agresividadCB.getValue() == null){}
        else {
            int nivelAgrecividad = agresividadCB.getValue();
            Animal.animalesPorAgresividad.get(animalEditado.nivelAgresividad).remove(animalEditado);
            animalEditado.nivelAgresividad = nivelAgrecividad;
            if (!Animal.animalesPorAgresividad.containsKey(nivelAgrecividad)) {
                Animal.animalesPorAgresividad.put(nivelAgrecividad, new LinkedList<>());
            }
            Animal.animalesPorAgresividad.get(nivelAgrecividad).add(animalEditado);
        }
        if (especie.isEmpty()){}
        else {
            Animal.animalesPorEspecie.get(animalEditado.especie).remove(animalEditado);
            animalEditado.especie = especie;
            if (!Animal.animalesPorEspecie.containsKey(especie)) {
                Animal.animalesPorEspecie.put(especie, new LinkedList<>());
            }
            Animal.animalesPorEspecie.get(especie).add(animalEditado);
        }
        if (alimentacion.isEmpty()){}
        else animalEditado.alimentacion = alimentacion;

        warning.setText("");

        idTextField.setText("");
        especieTextField.setText("");
        alimentacionTextField.setText("");
        idAnimalesDisp.setValue(null);
        agresividadCB.setValue(null);
        idAnimalesDisp.setItems(FXCollections.observableList(new ArrayList<>(Animal.animalesPorID.keySet())));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Edición satisfactoria");
        alert.setContentText("Se ha editado el Animal satisfactoriamente:\n" + animalEditado);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarAnimal");
    }

    @FXML
    private void rutaEditarRelaciones(ActionEvent event) throws IOException {
        App.setRoot("editarRelacionesAnimal");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idAnimalesDisp.setItems(FXCollections.observableList(new ArrayList<>(Animal.animalesPorID.keySet())));
        agresividadCB.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList(1,2,3,4,5))));
    }
}
