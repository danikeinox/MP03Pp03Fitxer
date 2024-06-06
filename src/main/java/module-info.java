module daniel.cabrera.mp03pp03fitxer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.google.zxing;
    requires java.desktop;
    requires Fitxers;
    requires jbcrypt;

    opens daniel.cabrera.mp03pp03fitxer to javafx.fxml;
    opens daniel.cabrera.mp03pp03fitxer.controllers to javafx.fxml;
    exports daniel.cabrera.mp03pp03fitxer;
    exports daniel.cabrera.mp03pp03fitxer.controllers to javafx.fxml;
}