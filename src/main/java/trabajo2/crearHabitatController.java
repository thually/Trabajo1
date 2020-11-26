package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.LinkedList;

public class crearHabitatController {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField tipoSueloTextField;
    @FXML
    private TextField vegetacionTextField;
    @FXML
    private TextField tipoJaulaTextField;
    @FXML
    private TextField temperaturaTextField;
    @FXML
    private Label warning;

    @FXML
    private void crear(ActionEvent event) throws IOException {
        if (idTextField.getText().trim().isEmpty() || tipoSueloTextField.getText().trim().isEmpty() || vegetacionTextField.getText().trim().isEmpty() 
                || tipoJaulaTextField.getText().trim().isEmpty() || temperaturaTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: No pueden haber campos vacíos.");
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idTextField.getText().trim());
        }catch (Exception e){
            warning.setText("El ID del Habitat tiene que ser un número entero");
            return;
        }
        if (Habitat.habitatsPorID.containsKey(id)){
            warning.setText("Ya existe un Habitat con este ID registrado en el sistema");
            return;
        }
        double temperatura;
        try{
            temperatura = Double.parseDouble(temperaturaTextField.getText().trim());
        }catch (Exception e){
            warning.setText("Valor ingresado como temperatura no permitido.");
            return;
        }
        String tipoSuelo = tipoSueloTextField.getText().trim().toLowerCase();
        String tipoJaula = tipoJaulaTextField.getText().trim().toLowerCase();
        String vegetacion = vegetacionTextField.getText().trim().toLowerCase();

        Habitat nuevoHabitat = new Habitat(id,temperatura, tipoSuelo, vegetacion, tipoJaula);
        App.sistemaZoo.addVertex(nuevoHabitat);
        Habitat.habitatsPorID.put(nuevoHabitat.id, nuevoHabitat);
        if (!Habitat.habitatsPorTemperatura.containsKey(nuevoHabitat.temperatura)) {
            Habitat.habitatsPorTemperatura.put(nuevoHabitat.temperatura, new LinkedList<>());
        }
        Habitat.habitatsPorTemperatura.get(nuevoHabitat.temperatura).add(nuevoHabitat);
        if (!Habitat.habitatsPorSuelo.containsKey(nuevoHabitat.tipoSuelo)) {
            Habitat.habitatsPorSuelo.put(nuevoHabitat.tipoSuelo, new LinkedList<>());
        }
        Habitat.habitatsPorSuelo.get(nuevoHabitat.tipoSuelo).add(nuevoHabitat);

        warning.setText("");

        idTextField.setText("");
        tipoJaulaTextField.setText("");
        tipoSueloTextField.setText("");
        vegetacionTextField.setText("");
        temperaturaTextField.setText("");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creacion satisfactoria");
        alert.setContentText("Se ha ingresado el Habitat satisfactoriamente:\n" + nuevoHabitat);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarHabitat");
    }
}
