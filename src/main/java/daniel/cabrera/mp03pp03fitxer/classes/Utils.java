package daniel.cabrera.mp03pp03fitxer.classes;

import daniel.Cabrera.Fitxers.Fitxers;
import daniel.cabrera.mp03pp03fitxer.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static daniel.cabrera.mp03pp03fitxer.Main.*;

public class Utils {

    // <editor-fold defaultstate="collapsed" desc="Mètodes">

    // Método para copiar el código de barras al portapapeles una vez creado el usuario
    public void copiaAlPortapapers(String codiBarra) {
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(codiBarra);
        clipboard.setContents(stringSelection, null);
    }

    // Método para crear una contraseña hasheda con BCrypt
    public String HashContrasenya(String contrasenya) {
        return BCrypt.hashpw(contrasenya, BCrypt.gensalt());
    }

    // Método para comprobar la contraseña con BCrypt
    public boolean comprovaContrasenya(String contrasenya, String contrasenyaHashed) {
        return BCrypt.checkpw(contrasenya, contrasenyaHashed);
    }

    // Método para comprobar el login de un usuario
    public boolean comprovarLogin(String usuari, String contrasenya) throws IOException, ClassNotFoundException {
        if (usuari == null || contrasenya == null) {
            return false;
        }
        Fitxers f = new Fitxers();
        if (f.existeix(nomDir + "/" + rutaAdmin)) {
            List<User> admins = llegeixFitxerBinariComUser(nomDir + "/" + rutaAdmin);
            if (admins != null) {
                for (User admin : admins) {
                    if (admin != null) {
                        if (admin.getUsuari().equals(usuari) && comprovaContrasenya(contrasenya, admin.getContrasenya())) {
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    // Método para leer una lista de objetos desde un archivo
    public List<User> llegeixObjectesFitxer(String fitxer) throws IOException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException e) {
                    break; // Cuando se alcanza el final del archivo, se rompe el bucle
                }
            }
        }
        return users;
    }

    // Método para guardar una lista de objetos en un archivo
    public <T> void guardarObjecte(String rutaFitxer, List<T> objectes) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaFitxer))) {
            oos.writeObject(objectes);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para modificar un objeto en el archivo (sobrescribiendo el archivo completo)
    public void modificarObjecte(String ruta, Object objecte) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        User userObj = (User) objecte;
        Fitxers f = new Fitxers();
        if (f.existeix(ruta)) {
            List<User> users = llegeixFitxerBinariComUser(ruta);
            for (User user : users) {
                if (user != null) {
                    if (user.getCodiBarra().equals(userObj.getCodiBarra()) &&
                            user.getNom().equals(userObj.getNom()) &&
                            user.getCognoms().equals(userObj.getCognoms()) &&
                            user.getDNI().equals(userObj.getDNI()) &&
                            user.getContrasenya().equals(userObj.getContrasenya()) &&
                            user.getCorreu().equals(userObj.getCorreu()) &&
                            user.getUsuari().equals(userObj.getUsuari()) &&
                            user.isAdmin() == userObj.isAdmin()) {
                        if (user.isEntrada() != userObj.isEntrada()) {
                            user.setEntrada(userObj.isEntrada());
                            user.setDataHora(userObj.getDataHora());
                        } else {
                            return;
                        }
                    }
                } else {
                    // Si no existeix l'usuari, l'afegim al fitxer
                    if (userObj.isAdmin())
                        f.escriuObjecteFitxer(userObj, nomDir + "/" + rutaAdmin, true);
                    else {
                        f.escriuObjecteFitxer(userObj, nomDir + "/" + rutaUsuari, true);
                    }
                }
            }
            if (users != null) {
                List<User> userAdmins = new ArrayList<>();
                List<User> userNoAdmins = new ArrayList<>();
                for (User user1 : users) {
                    if (user1.isAdmin())
                        userAdmins.add(user1);
                    else
                        userNoAdmins.add(user1);
                }
                if (ruta.equals(nomDir + "/" + rutaAdmin))
                    escriuFitxerBinariComUser(nomDir + "/" + rutaAdmin, userAdmins);
                else
                    escriuFitxerBinariComUser(nomDir + "/" + rutaUsuari, userNoAdmins);

            }
        }
        ois.close();
    }

    // Método para leer un archivo binario y devolver una lista de objetos User
    public List<User> llegeixFitxerBinariComUser(String fitxer) {
        Fitxers f = new Fitxers();
        List<User> users = new ArrayList<>();
        List<User> admins = new ArrayList<>();
        try {
            byte[] data = f.llegeixFitxerBinari(fitxer);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            try {
                while (true) {
                    User user = (User) ois.readObject();
                    users.add(user);
                }
            } catch (EOFException e) {
                // Cuando se alcanza el final del archivo, se rompe el bucle
            } finally {
                ois.close();
                bis.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Método para escribir un archivo binario con una lista de objetos User
    public void escriuFitxerBinariComUser(String fitxer, List<User> users) {
        Fitxers f = new Fitxers();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            try {
                for (User user : users) {
                    oos.writeObject(user);
                }
                oos.flush();
                byte[] data = bos.toByteArray();
                f.escriuFitxerBinari(fitxer, data);
            } finally {
                oos.close();
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Método para mostrar una alerta
    public void Alerta(String missatge, String titol, String tipus) {
        Alert alert = new Alert(Alert.AlertType.valueOf(tipus));
        alert.setTitle(titol);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    // Método para abrir una nueva ventana
    public void FGeneric(String formulari, String titol) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(formulari));

            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle(titol);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            Alerta("No s'ha pogut obrir la finestra: " + formulari, "Error", "ERROR_MESSAGE");
        }
    }

    // </editor-fold>

}
