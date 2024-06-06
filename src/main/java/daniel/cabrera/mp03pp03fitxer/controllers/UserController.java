package daniel.cabrera.mp03pp03fitxer.controllers;

import daniel.Cabrera.Fitxers.Fitxers;
import daniel.cabrera.mp03pp03fitxer.classes.Utils;
import daniel.cabrera.mp03pp03fitxer.classes.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static daniel.cabrera.mp03pp03fitxer.Main.*;

public class UserController {

    @FXML
    private TextField codiBarresTextField;

    @FXML
    private Label usuariLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label cognomsLabel;

    @FXML
    private Label entradaSortidaLabel;

    @FXML
    private Label dataHoraLabel;

    private final Fitxers f = new Fitxers();
    private final Utils utils = new Utils();

    // Método para fichar la entrada o salida de un usuario y actualizar los datos en el archivo correspondiente
    @FXML
    public void fitxar() throws IOException, ClassNotFoundException {
        String codiBarres = codiBarresTextField.getText();
        boolean usuariExisteix = false;

        // Verificar usuarios en el archivo de administradores
        usuariExisteix = verificarYActualizarUsuario(nomDir + "/" + rutaAdmin, codiBarres);

        // Si no se encontró en administradores, verificar en el archivo de usuarios
        if (!usuariExisteix) {
            usuariExisteix = verificarYActualizarUsuario(nomDir + "/" + rutaUsuari, codiBarres);
        }

        // Si no se encontró el usuario, mostrar un mensaje de error y limpiar las etiquetas
        if (!usuariExisteix) {
            utils.Alerta("No s'ha trobat cap usuari amb el codi de barres especificat.", "Usuari no trobat", "ERROR");
            limpiarEtiquetas();
        }
    }

    // Método para verificar y actualizar la entrada o salida de un usuario
    private boolean verificarYActualizarUsuario(String ruta, String codiBarres) throws IOException, ClassNotFoundException {
        if (f.existeix(ruta)) {
            List<User> usuaris = utils.llegeixFitxerBinariComUser(ruta);
            if (usuaris != null) {
                for (User user : usuaris) {
                    if (user != null && user.getCodiBarra().equals(codiBarres)) {
                        if (user.isEntrada()) {
                            user.setEntrada(false);
                        } else {
                            user.setEntrada(true);
                        }
                        actualizarDatosUsuario(user);
                        utils.modificarObjecte(ruta, user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Método para actualizar los datos del usuario en el formulario FXML
    private void actualizarDatosUsuario(User user) {
        boolean entradaSortida = user.isEntrada();
        usuariLabel.setText(user.getUsuari());
        nomLabel.setText(user.getNom());
        cognomsLabel.setText(user.getCognoms());
        entradaSortidaLabel.setText(entradaSortida ? "Entrada" : "Sortida");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatejada = now.format(formatter);
        dataHoraLabel.setText(dataHoraFormatejada);

        user.setDataHora(dataHoraFormatejada);
    }

    // Método para limpiar las etiquetas del formulario FXML
    private void limpiarEtiquetas() {
        usuariLabel.setText("");
        nomLabel.setText("");
        cognomsLabel.setText("");
        entradaSortidaLabel.setText("");
        dataHoraLabel.setText("");
    }

    // Método para abrir el formulario de login de administrador
    @FXML
    public void obrirAdminLogin() {
        utils.FGeneric("formularis/admin.fxml", "Login d'administrador");
    }
}
