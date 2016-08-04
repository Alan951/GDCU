package mx.jalan.gdcu.Modelo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.jalan.gdcu.Utils.Utils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jorge Alan Villalon Perez
 */
public class Archivo extends File{
    
    private boolean estaCifrado;
    private boolean esValido;
    
    private String errorMensaje;
    private String password;
    
    private String ruta;
    private String nombre;
    private long tamaño;
    private Date fechaCreacion;
    
    private String linkFuente;
    private String linkDescarga;
    private String comentarios;
    
    private String hash;
    
    public Archivo(String pathname) {
        super(pathname);
    }
    
    public Archivo(String pathname, boolean estaCifrado){
        super(pathname);
        setEstaCifrado(estaCifrado);
    }
    
    public Archivo(String pathname, String password){
        super(pathname);
        setEstaCifrado(true);
        setPassword(password);
    }
    
    public Archivo(String pathname, boolean isValid, String mensaje){
        super(pathname);
        setEsValido(isValid);
        setErrorMensaje(mensaje);
    }
    
    public void setDetalles(String linkFuente, String linkDescarga, String comentarios){
        setLinkFuente(linkFuente);
        setLinkDescarga(linkDescarga);
        setComentarios(comentarios);
    }
    
    /*
        Estos datos se siguiran mostrando aunque los archivos ya no esten en la ruta original cunado se registro
        en el gestor de contraseñas.
    */
    public void respaldarInformacion()throws IOException{
        this.ruta = getAbsolutePath();
        this.tamaño = length();
        this.nombre = getName();
        
        
        this.fechaCreacion = Utils.getFechaDeCreacionDate(this);
    }
    
    public boolean getEsValido() {
        return esValido;
    }

    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }

    public String getErrorMensaje() {
        return errorMensaje;
    }

    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setEstaCifrado(boolean estaCifrado){
        this.estaCifrado = estaCifrado;
    }
    
    public boolean getEstaCifrado(){
        return estaCifrado;
    }
    
    public long getTamaño(){
        return tamaño;
    }
    
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public void setLinkFuente(String linkFuente){
        this.linkFuente = linkFuente;
    }
    
    public String getLinkFuente(){
        return linkFuente;
    }
    
    public void setLinkDescarga(String linkDescarga){
        this.linkDescarga = linkDescarga;
    }
    
    public String getLinkDescarga(){
        return linkDescarga;
    }
    
    public void setComentarios(String comentarios){
        this.comentarios = comentarios;
    }
    
    public String getComentarios(){
        return comentarios;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getRuta(){
        return ruta;
    }
    
    public Date getFechaCreacion(){
        return fechaCreacion;
    }
    
    /*
    public long getTamaño(){
        return tamaño;
    }*/

    @Override
    public String toString() {
        return "Archivo{" + "estaCifrado=" + estaCifrado + ", esValido=" + esValido + ", errorMensaje=" + errorMensaje + ", password=" + password + ", ruta=" + ruta + ", nombre=" + nombre + ", tama\u00f1o=" + tamaño + ", fechaCreacion=" + fechaCreacion + ", linkFuente=" + linkFuente + ", linkDescarga=" + linkDescarga + ", comentarios=" + comentarios + ", hash=" + hash + '}';
    }
    
    
}
