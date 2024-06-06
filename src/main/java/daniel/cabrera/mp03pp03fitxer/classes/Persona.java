package daniel.cabrera.mp03pp03fitxer.classes;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    private String nom;
    private String cognoms;
    private String codiBarra;
    private String DNI;
    private String contrasenya;
    private String correu;
    private String usuari;
    private boolean admin;
    private boolean entrada;
    private String dataHora;

    // No-argument constructor
    public Persona() {

    }

    // Constructor amb paràmetres
    public Persona(String nom, String cognoms, String codiBarra, String DNI, String contrasenya, String correu, String usuari, boolean admin, boolean entrada, String dataHora) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.codiBarra = codiBarra;
        this.DNI = DNI;
        this.contrasenya = contrasenya;
        this.correu = correu;
        this.usuari = usuari;
        this.admin = admin;
        this.entrada = entrada;
        this.dataHora = dataHora;
    }

    // Getters i setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getCodiBarra() {
        return codiBarra;
    }

    public void setCodiBarra(String codiBarra) {
        this.codiBarra = codiBarra;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}