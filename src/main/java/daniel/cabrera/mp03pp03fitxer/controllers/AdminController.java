package daniel.cabrera.mp03pp03fitxer.controllers;

import com.google.zxing.WriterException;
import daniel.Cabrera.Fitxers.Fitxers;
import daniel.cabrera.mp03pp03fitxer.classes.GenerateEAN13Barcode;
import daniel.cabrera.mp03pp03fitxer.classes.User;
import daniel.cabrera.mp03pp03fitxer.classes.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static daniel.cabrera.mp03pp03fitxer.Main.rutaAdmin;
import static daniel.cabrera.mp03pp03fitxer.Main.rutaUsuari;
import static daniel.cabrera.mp03pp03fitxer.Main.nomDir;

public class AdminController {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Declaracions de variables

    private static Utils utils = new Utils();

    private Fitxers f;

    @FXML
    private VBox VBoxIniciSessio;

    @FXML
    private TextField nomUsuariTextField;

    @FXML
    private PasswordField contrasenyaPasswordField;

    @FXML
    private Button botoCopiar;

    // Declaracions dels camps de la interfície gràfica
    @FXML
    private VBox VBoxCrear;

    @FXML
    private TextField usuariNouUsuariTextField;

    @FXML
    private TextField nomNouUsuariTextField;

    @FXML
    private TextField cognomsNouUsuariTextField;

    @FXML
    private ImageView imatgeCodiBarres;

    @FXML
    private TextField codiBarresTextField;

    @FXML
    private TextField dniNouUsuariTextField;

    @FXML
    private PasswordField contrasenyaNouUsuariPasswordField;

    @FXML
    private TextField emailNouUsuariTextField;

    @FXML
    private CheckBox adminCheckBox;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètodes">

    //** Mètode per inicialitzar la finestra de l'aplicació i comprovar si hi ha un usuari admin a la base de dades
    //
    //  @autor daniel.cabrera
    @FXML
    public void initialize() {
        Fitxers f = new Fitxers();
        // Desactivar la secció de creació d'usuari en iniciar l'aplicació
        VBoxCrear.setDisable(true);
        if (!f.existeix(nomDir + "/" + rutaAdmin)) {
            // Habilita la secció de creació d'usuari si no hi ha cap usuari admin a la base de dades
            VBoxCrear.setDisable(false);
            // Desactiva la secció d'inici de sessió
            VBoxIniciSessio.setDisable(true);
            adminCheckBox.setSelected(true);
            adminCheckBox.setDisable(true);
        }
    }

    //** Mètode per crear un nou usuari
    //
    //  @autor daniel.cabrera
    //  @throws IOException
    //  @throws ClassNotFoundException
    @FXML
    public void crearUsuari() throws IOException, ClassNotFoundException {
        Fitxers f = new Fitxers();

        // Comprova si el directori existeix i si no, el crea
        if (!f.existeix(nomDir))
            f.creaDirectori(nomDir);

        // Obtenir les dades del nou usuari dels camps d'entrada
        String usuari = usuariNouUsuariTextField.getText();
        String nom = nomNouUsuariTextField.getText();
        String cognoms = cognomsNouUsuariTextField.getText();
        String dni = dniNouUsuariTextField.getText();
        String contrasenya = contrasenyaNouUsuariPasswordField.getText();
        contrasenya = utils.HashContrasenya(contrasenya);
        String email = emailNouUsuariTextField.getText();
        boolean admin = adminCheckBox.isSelected();
        boolean entrada = false;
        String dataHora = "";

        // Generar un codi de barres EAN-13 per al nou usuari
        String codiBarra = GenerateEAN13Barcode.generateEAN13Code();

        // Crear un nou usuari amb les dades obtingudes
        User nouUsuari = new User(nom, cognoms, codiBarra, dni, contrasenya, email, usuari, admin, entrada, dataHora);

        int fitxersExisteixen = 0;

        // Obtenir llista d'usuaris per verificar unicitat
        List<User> usuaris = null;
        if (f.existeix(nomDir + "/" + rutaAdmin)) {
            fitxersExisteixen++;
            usuaris = utils.llegeixObjectesFitxer(nomDir + "/" + rutaAdmin);
            if (usuaris == null) {
                usuaris = new ArrayList<>();
            }
        } else if (f.existeix(nomDir + "/" + rutaUsuari)) {
            fitxersExisteixen++;
            usuaris = utils.llegeixObjectesFitxer(nomDir + "/" + rutaUsuari);
            if (usuaris == null) {
                usuaris = new ArrayList<>();
            }
        }

        if (fitxersExisteixen > 0) {
            for (User user : usuaris) {
                if (user != null) {
                    if (user.getCodiBarra().equals(codiBarra) || user.getUsuari().equals(usuari) || user.getDNI().equals(dni) || user.getCorreu().equals(email)) {
                        utils.Alerta("El nom d'usuari, DNI, email o codi de barres ja existeix", "Error", "ERROR");
                        return;
                    }
                }
            }
        }

        // Crear un nou usuari i inserir-lo al fitxer .dat de admin o usuaris segons si és admin o no
        if (f.existeix(nomDir + "/" + rutaAdmin)) {
            if (nouUsuari.isAdmin())
                f.escriuObjecteFitxer(nouUsuari, nomDir + "/" + rutaAdmin, true);
        } else {
            if (nouUsuari.isAdmin())
                f.escriuObjecteFitxer(nouUsuari, nomDir + "/" + rutaAdmin, false);
        }

        if (f.existeix(nomDir + "/" + rutaUsuari)) {
            if (!nouUsuari.isAdmin())
                f.escriuObjecteFitxer(nouUsuari, nomDir + "/" + rutaUsuari, true);
        } else {
            if (!nouUsuari.isAdmin())
                f.escriuObjecteFitxer(nouUsuari, nomDir + "/" + rutaUsuari, false);
        }

        // Mostrar codi de barres del nou usuari creat automàticament
        try {
            BufferedImage barcodeImage = GenerateEAN13Barcode.generateEAN13BarcodeImage(codiBarra);
            Image imatge = SwingFXUtils.toFXImage(barcodeImage, null);
            imatgeCodiBarres.setImage(imatge);
            codiBarresTextField.setText(codiBarra);
            utils.copiaAlPortapapers(codiBarra);
            utils.Alerta("Usuari creat correctament, s'ha copiat el códi de barres al Portapapers", "Usuari creat", "CONFIRMATION");
        } catch (WriterException e) {
            // Gestionar l'excepció
            utils.Alerta("Error al generar el codi de barres", "Error", "ERROR");
        }

        botoCopiar.setOnAction(e -> {
            utils.copiaAlPortapapers(codiBarra);
        });

        // Netejar els camps després de crear l'usuari
        netejarCamps();
    }

    //** Mètode per iniciar sessió com a administrador de l'aplicació
    //
    //  @autor daniel.cabrera
    //  @throws IOException
    //  @throws ClassNotFoundException
    @FXML
    public void iniciarSessio() throws IOException, ClassNotFoundException {
        // Connectar a la base de dades i comprovar l'inici de sessió
        Fitxers f = new Fitxers();
        String usuari = nomUsuariTextField.getText();
        String contrasenya = contrasenyaPasswordField.getText();
        // Xifrar la contrasenya abans de comprovar-la
        if (utils.comprovarLogin(usuari, contrasenya)) {
            // Habilitar la secció de creació d'usuari si l'inici de sessió és correcte
            VBoxCrear.setDisable(false); // Habilitar la secció de creació d'usuari
            VBoxIniciSessio.setDisable(true); // Desactivar la secció d'inici de sessió
        } else {
            // Si és incorrecte, mostrar un missatge d'error i no habilitar la secció de creació d'usuari
            Utils utils = new Utils();
            utils.Alerta("Usuari o contrasenya incorrectes, torna a intentar-ho...", "Error", "ERROR");
        }
        // Tancar la connexió amb la base de dades
    }

    //** Mètode per netejar els camps d'entrada després de crear un usuari
    //
    //  @autor daniel.cabrera
    private void netejarCamps() {
        usuariNouUsuariTextField.clear();
        nomNouUsuariTextField.clear();
        cognomsNouUsuariTextField.clear();
        dniNouUsuariTextField.clear();
        contrasenyaNouUsuariPasswordField.clear();
        emailNouUsuariTextField.clear();
        adminCheckBox.setSelected(false); // Desmarcar la casella d'admin
    }

    //** Mètode per obrir el panell d'usuari des de l'aplicació d'administrador
    //
    //  @autor daniel.cabrera
    @FXML
    public void obrirPanellUsuari() {
        // Obrir el panell d'usuari
        utils.FGeneric("formularis/user.fxml", "Control de Presència");
    }

    // </editor-fold>

}