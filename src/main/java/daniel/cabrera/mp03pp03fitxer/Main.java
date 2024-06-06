package daniel.cabrera.mp03pp03fitxer;

import daniel.Cabrera.Fitxers.Fitxers;
import daniel.cabrera.mp03pp03fitxer.classes.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Declaración de variables
    public static String nomDir = ".data";
    public static String rutaAdmin = "administradors.dat";
    public static String rutaUsuari = "usuaris.dat";

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètodes">

    // Inicialización de la aplicación y comprobación de la existencia de un administrador
    @Override
    public void start(Stage stage) throws Exception {
        Fitxers f = new Fitxers();
        if (!f.existeix(nomDir+"/"+rutaAdmin)) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("formularis/user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            stage.setTitle("Control de Presència");
            stage.setScene(scene);
            stage.show();
            Utils utils = new Utils();
            utils.Alerta("No hi ha cap administrador a la base de dades, crea'n un.", "Error", "ERROR");
            utils.FGeneric("formularis/admin.fxml", "Crear Administrador");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("formularis/user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            stage.setTitle("Control de Presència");
            stage.setScene(scene);
            stage.show();
        }
    }

    // Método principal de la aplicación
    public static void main(String[] args) {
        launch(args);
    }

    //</editor-fold>

}