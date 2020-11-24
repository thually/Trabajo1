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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class editarHabitatController implements Initializable {
    @FXML
    private ChoiceBox<Integer> idHabitatsDisp;
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
    private void editar(ActionEvent event) throws IOException {
        if (idTextField.getText().trim().isEmpty() && tipoSueloTextField.getText().trim().isEmpty() && vegetacionTextField.getText().trim().isEmpty()
                && tipoJaulaTextField.getText().trim().isEmpty() && temperaturaTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: Todos los campos se encuentran vacíos.");
            return;
        }
        if (idHabitatsDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona el ID del Habitat que desea editar.");
            return;
        }

        Habitat habitatEditado = Habitat.habitatsPorID.get(idHabitatsDisp.getValue());
        

        int id = 0;
        if (!idTextField.getText().trim().isEmpty()) {
            try{
                id = Integer.parseInt(idTextField.getText().trim());
                if (Habitat.habitatsPorID.containsKey(id)){
                    warning.setText("Ya existe un Habitat con este ID registrado en el sistema");
                    return;
                }
            }catch (Exception e){
                warning.setText("El ID del Habitat tiene que ser un número entero");
                return;
            }
        }

        double temperatura = 0;
        if (!temperaturaTextField.getText().trim().isEmpty()) {
            try{
                temperatura = Double.parseDouble(temperaturaTextField.getText().trim());
            }catch (Exception e){
                warning.setText("Valor ingresado como temperatura no permitido.");
                return;
            }
        }

        String tipoSuelo = tipoSueloTextField.getText().trim();
        String tipoJaula = tipoJaulaTextField.getText().trim();
        String vegetacion = vegetacionTextField.getText().trim();

        if (idTextField.getText().trim().isEmpty()){}
        else habitatEditado.id = id;
        if (temperaturaTextField.getText().trim().isEmpty()){}
        else habitatEditado.temperatura = temperatura;
        if (tipoSuelo.isEmpty()){}
        else habitatEditado.tipoSuelo = tipoSuelo;
        if (vegetacion.isEmpty()){}
        else habitatEditado.vegetacion = vegetacion;
        if (tipoJaula.isEmpty()){}
        else habitatEditado.tipoJaula = tipoJaula;

        warning.setText("");

        idTextField.setText("");
        tipoJaulaTextField.setText("");
        tipoSueloTextField.setText("");
        vegetacionTextField.setText("");
        temperaturaTextField.setText("");
        idHabitatsDisp.setValue(null);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Edición satisfactoria");
        alert.setContentText("Se ha editado el Habitat satisfactoriamente:\n" + habitatEditado);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarHabitat");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idHabitatsDisp.setItems(FXCollections.observableList(new ArrayList<>(Habitat.habitatsPorID.keySet())));
    }
}
