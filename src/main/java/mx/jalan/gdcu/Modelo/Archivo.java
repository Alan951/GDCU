package mx.jalan.gdcu.Modelo;

import java.io.File;
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
    
    public String getTamaño(){
        return FileUtils.byteCountToDisplaySize(this.length());
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
    
}
