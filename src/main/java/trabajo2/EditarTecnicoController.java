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

public class EditarTecnicoController implements Initializable {

    @FXML
    private ChoiceBox<Integer> idTecnicosDisp;
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
    private void editar(ActionEvent event) throws IOException {
        if (cedulaTextField.getText().trim().isEmpty() && sueldoTextField.getText().trim().isEmpty() && areaTrabajoCB.getValue() == null
                && HoraInicioTextField.getText().trim().isEmpty() && HoraSalidaTextField.getText().trim().isEmpty()
                && MinInicioTextField.getText().trim().isEmpty() && MinSalidaTextField.getText().trim().isEmpty()){
            warning.setText("MENSAJE: Todos los campos se encuentran vacíos.");
            return;
        }

        if (idTecnicosDisp.getValue() == null){
            warning.setText("MENSAJE: Aún no selecciona la cédula del Tecnico que desea editar.");
            return;
        }

        Tecnico tecnicoEditado = Tecnico.tecnicosPorID.get(idTecnicosDisp.getValue());

        int cedula=0;
        if (!cedulaTextField.getText().trim().isEmpty()) {
            try{
                cedula = Integer.parseInt(cedulaTextField.getText().trim());
                if (Tecnico.tecnicosPorID.containsKey(cedula)){
                    warning.setText("Ya existe un Tecnico con esta cédula registrado en el sistema");
                    return;
                }
            }catch (Exception e){
                warning.setText("La cedula del Tecnico debe que ser un número entero");
                return;
            }
        }

        double sueldo=0;
        if (!sueldoTextField.getText().trim().isEmpty()) {
            try{
                sueldo = Double.parseDouble(sueldoTextField.getText().trim());
            }catch (Exception e){
                warning.setText("Valor ingresado como sueldo no permitido.");
                return;
            }
        }

        String area = areaTrabajoCB.getValue();

        int HoraInicio=0;
        int MinInicio=0;
        String HoraInicioTec = "";
        if (!HoraInicioTextField.getText().trim().isEmpty() && !MinInicioTextField.getText().trim().isEmpty()) {
            try{
                HoraInicio = Integer.parseInt(HoraInicioTextField.getText().trim());
                MinInicio = Integer.parseInt(MinInicioTextField.getText().trim());
                if (HoraInicio < 0 || MinInicio < 0 || HoraInicio > 24 || MinInicio > 60){
                    warning.setText("La hora de inicio ingresada no es válida");
                    return;
                }
                if (HoraInicio < 10) HoraInicioTec = "0" + HoraInicio;
                else HoraInicioTec = String.valueOf(HoraInicio);
                HoraInicioTec = HoraInicioTec + ":";
                if (MinInicio < 10) HoraInicioTec = HoraInicioTec + "0" + MinInicio;
                else HoraInicioTec = HoraInicioTec + MinInicio;

            }catch (Exception e){
                warning.setText("La hora de inicio ingresada no es válida");
                return;
            }
        }

        int HoraSalida=0;
        int MinSalida=0;
        String HoraSalidaTec = "";
        if (!HoraSalidaTextField.getText().trim().isEmpty() && !MinSalidaTextField.getText().trim().isEmpty()) {
            try{
                HoraSalida = Integer.parseInt(HoraSalidaTextField.getText().trim());
                MinSalida = Integer.parseInt(MinSalidaTextField.getText().trim());
                if (HoraSalida < 0 || MinSalida < 0 || HoraSalida > 24 || MinSalida > 60){
                    warning.setText("La hora de salida ingresada no es válida");
                    return;
                }
                if (HoraSalida < 10) HoraSalidaTec = "0" + HoraSalida;
                else HoraSalidaTec = String.valueOf(HoraSalida);
                HoraSalidaTec = HoraSalidaTec + ":";
                if (MinSalida < 10) HoraSalidaTec = HoraSalidaTec + "0" + MinSalida;
                else HoraSalidaTec = HoraSalidaTec + MinSalida;
            }catch (Exception e){
                warning.setText("La hora de salida ingresada no es válida");
                return;
            }
        }

        // Editar tecnico...
        if (cedulaTextField.getText().trim().isEmpty()){}
        else {
            Tecnico.tecnicosPorID.remove(tecnicoEditado.cedula);
            tecnicoEditado.cedula = cedula;
            Tecnico.tecnicosPorID.put(cedula,tecnicoEditado);
        }
        if (sueldoTextField.getText().trim().isEmpty()){}
        else {
            Tecnico.tecnicosPorSueldo.get(tecnicoEditado.sueldo).remove(tecnicoEditado);
            tecnicoEditado.sueldo = sueldo;
            if (!Tecnico.tecnicosPorSueldo.containsKey(sueldo)){
                Tecnico.tecnicosPorSueldo.put(sueldo, new LinkedList<>());
            }
            Tecnico.tecnicosPorSueldo.get(sueldo).add(tecnicoEditado);
        }
        if (areaTrabajoCB.getValue()==null){}
        else {
            Tecnico.tecnicosPorArea.get(tecnicoEditado.area).remove(tecnicoEditado);
            tecnicoEditado.area = area;
            if (!Tecnico.tecnicosPorArea.containsKey(area)){
                Tecnico.tecnicosPorArea.put(area, new LinkedList<>());
            }
            Tecnico.tecnicosPorArea.get(area).add(tecnicoEditado);
        }

        if (HoraInicioTec.isEmpty()){}
        else tecnicoEditado.horaInicio = HoraInicioTec;
        if (HoraSalidaTec.isEmpty()){}
        else tecnicoEditado.horaSalida = HoraSalidaTec;

        warning.setText("");

        cedulaTextField.setText("");
        HoraInicioTextField.setText("");
        MinInicioTextField.setText("");
        sueldoTextField.setText("");
        areaTrabajoCB.setValue(null);
        idTecnicosDisp.setValue(null);
        HoraSalidaTextField.setText("");
        MinSalidaTextField.setText("");
        idTecnicosDisp.setItems(FXCollections.observableList(new ArrayList<>(Tecnico.tecnicosPorID.keySet())));



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Edición satisfactoria");
        alert.setContentText("Se ha editado el Tecnico satisfactoriamente:\n" + tecnicoEditado);
        alert.showAndWait();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarTecnico");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaTrabajoCB.setItems(FXCollections.observableList(new ArrayList<>(Arrays.asList("guardia", "aseo", "guia"))));
        idTecnicosDisp.setItems(FXCollections.observableList(new ArrayList<>(Tecnico.tecnicosPorID.keySet())));

    }

    @FXML
    private void rutaEditarRelaciones(ActionEvent event) throws IOException {
        App.setRoot("editarRelacionesTecnico");
    }
}
