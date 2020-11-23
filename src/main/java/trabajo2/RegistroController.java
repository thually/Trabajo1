package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {
    @FXML
    private Label saludoLabel;  //sera un Label en la interfaz grafica

    @FXML
    private Label errorLabel;  //sera un Label en la interfaz grafica

    @FXML
    private TextField usuarioTextField; //sera un TextField en la interfaz grafica

    @FXML
    private PasswordField contraTextField; //sera un TextField en la interfaz grafica, a pesar de que aparece como contraseña

    @FXML
    private void saludar(ActionEvent event){ //Como será un método para la acción de presionar el botón, es ActionEvent. Si fuera un click es MouseEvent, si fuera presionar una tecla sería KeyEvent.
        saludoLabel.setVisible(false);
        errorLabel.setVisible(false);

        String nombre = usuarioTextField.getText();
        String contra = contraTextField.getText();
        if (nombre.equals("Alejo") && contra.equals("hola")){
            saludoLabel.setText("Hola " + nombre);
            saludoLabel.setVisible(true);
        } else {
            errorLabel.setText("Datos incorrectos");
            errorLabel.setVisible(true);

        }
    }
}
