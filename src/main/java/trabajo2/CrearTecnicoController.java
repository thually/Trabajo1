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

public class CrearTecnicoController implements Initializable {
    @FXML
    private TextField cedulaTextField;
    @FXML
    private TextField sueldoTextField;
    @FXML
    private ChoiceBox<String> areaTrabajoCB;
    @FXML
    private TextField HoraInicioTextField;
    @FXML
    private TextField HoraSalidaTextField;
    @FXML
    private TextField MinInicioTextField;
    @FXML
    private TextField MinSalidaTextField;
    @FXML
    private Label warning;

    @FXML
    private void crear(ActionEvent event) throws IOException {
        if (cedulaTextField.getText().trim().isEmpty() || sueldoTextField.getText().trim().isEmpty() || areaTrabajoCB.getValue() == null
                || HoraInicioTextField.getText().trim().isEmpty() || HoraSalidaTextField.getText().trim().isEmpty()
                || MinInicioTextField.getText().trim().isEmpty() || MinSalidaTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: No pueden haber campos vacíos.");
            return;
        }
        int cedula;
        try{
            cedula = Integer.parseInt(cedulaTextField.getText().trim());
        }catch (Exception e){
            warning.setText("La cedula del Tecnico debe que ser un número entero");
            return;
        }
        if (Tecnico.tecnicosPorID.containsKey(cedula)){
            warning.setText("Ya existe un Tecnico con esta cédula registrado en el sistema");
            return;
        }
        double sueldo;
        try{
            sueldo = Double.parseDouble(sueldoTextField.getText().trim());
        }catch (Exception e){
            warning.setText("Valor ingresado como sueldo no permitido.");
            return;
        }
        String area = areaTrabajoCB.getValue();

        int HoraInicio;
        try{
            HoraInicio = Integer.parseInt(HoraInicioTextField.getText().trim());
        }catch (Exception e){
            warning.setText("La hora de inicio ingresada no es válida");
            return;
        }
        int MinInicio;
        try{
            MinInicio = Integer.parseInt(MinInicioTextField.getText().trim());
        }catch (Exception e){
            warning.setText("La hora de inicio ingresada no es válida");
            return;
        }
        int HoraSalida;
        try{
            HoraSalida = Integer.parseInt(HoraSalidaTextField.getText().trim());
        }catch (Exception e){
            warning.setText("La hora de salida ingresada no es válida");
            return;
        }
        int MinSalida;
        try{
            MinSalida = Integer.parseInt(MinSalidaTextField.getText().trim());
        }catch (Exception e){
            warning.setText("La hora de salida ingresada no es válida");
            return;
        }
        if (HoraInicio < 0 || MinInicio < 0 || HoraInicio > 24 || MinInicio > 60){
            warning.setText("La hora de inicio ingresada no es válida");
            return;
        }
        if (HoraSalida < 0 || MinSalida < 0 || HoraSalida > 24 || MinSalida > 60){
            warning.setText("La hora de salida ingresada no es válida");
            return;
        }
        if (HoraInicio > HoraSalida){
            warning.setText("Las horas ingresadas no son válidas");
            return;
        }

        String HoraInicioTec = "";
        if (HoraInicio < 10) HoraInicioTec = "0" + HoraInicio;
        else HoraInicioTec = String.valueOf(HoraInicio);
        HoraInicioTec = HoraInicioTec + ":";
        if (MinInicio < 10) HoraInicioTec = HoraInicioTec + "0" + MinInicio;
        else HoraInicioTec = HoraInicioTec + MinInicio;

        String HoraSalidaTec = "";
        if (HoraSalida < 10) HoraSalidaTec = "0" + HoraSalida;
        else HoraSalidaTec = String.valueOf(HoraSalida);
        HoraSalidaTec = HoraSalidaTec + ":";
        if (MinSalida < 10) HoraSalidaTec = HoraSalidaTec + "0" + MinSalida;
        else HoraSalidaTec = HoraSalidaTec + MinSalida;

        Tecnico nuevoTecnico = new Tecnico(cedula, sueldo, area, HoraInicioTec, HoraSalidaTec);
        App.sistemaZoo.addVertex(nuevoTecnico);
        Tecnico.tecnicosPorID.put(nuevoTecnico.cedula, nuevoTecnico);
        if (!Tecnico.tecnicosPorSueldo.containsKey(nuevoTecnico.sueldo)){
            Tecnico.tecnicosPorSueldo.put(nuevoTecnico.sueldo, new LinkedList<>());
        }
        Tecnico.tecnicosPorSueldo.get(nuevoTecnico.sueldo).add(nuevoTecnico);
        if (!Tecnico.tecnicosPorArea.containsKey(nuevoTecnico.area)){
            Tecnico.tecnicosPorArea.put(nuevoTecnico.area, new LinkedList<>());
        }
        Tecnico.tecnicosPorArea.get(nuevoTecnico.area).add(nuevoTecnico);

        warning.setText("");

        cedulaTextField.setText("");
        HoraInicioTextField.setText("");
        MinInicioTextField.setText("");
        sueldoTextField.setText("");
        areaTrabajoCB.setValue(null);
        HoraSalidaTextField.setText("");
        MinSalidaTextField.setText("");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Creacion satisfactoria");
        alert.setContentText("Se ha ingresado el Tecnico satisfactoriamente:\n" + nuevoTecnico);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarTecnico");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaTrabajoCB.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("guardia", "aseo", "guia"))));
    }
}
