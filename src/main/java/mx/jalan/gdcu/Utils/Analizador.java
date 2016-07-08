/*
    Esta clase ayudara a analizar una lista de archivos
    y checar cuantos archivos comprimidos hay, cuantos archivos con contraseña hay, etc...
*/

package mx.jalan.gdcu.Utils;

import java.util.ArrayList;
import mx.jalan.gdcu.Modelo.Archivo;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Analizador {
    
    private ArrayList<Archivo> archivos;
    
    private int archivosComprimidos;
    private int archivosConContraseñaCorrecta;
    private int archivosConContraseña;
    private int archivosZip;
    private int archivosRar;
    private int archivosNormales;
    
    public Analizador(ArrayList<Archivo> archivos){
        this.archivos = archivos;
        
        analizar();
    }
    
    public void analizar(){
        archivosConContraseña = cuantosArchivosConContraseñaHay();
        archivosComprimidos = cuantosArchivosComprimidosHay();
        
        System.out.println(toString());
    }
    
    public int cuantosArchivosConContraseñaHay(){
        int archivosConContraseña = 0;
        
        for(Archivo archivo : archivos){
            if(archivo.getEstaCifrado()){
                archivosConContraseña++;
                if(archivo.getPassword() != null){
                    archivosConContraseñaCorrecta++;
                }
            }
        }
        
        return archivosConContraseña;
        
    }
    
    public int cuantosArchivosComprimidosHay(){
        int archivosComprimidos = 0;
        int archivosZip = 0;
        int archivosRar = 0;
        
        for(Archivo archivo : archivos){
            if(Utils.esArchivoComprimido(archivo)){
                archivosComprimidos++;
                
                if(Utils.esZip(archivo)){
                    archivosZip++;
                }else if(Utils.esRar(archivo)){
                    archivosRar++;
                }
            }
        }
        
        this.archivosZip = archivosZip;
        this.archivosRar = archivosRar;
        this.archivosNormales = archivos.size() - archivosComprimidos;
        
        return archivosComprimidos;
    }
    
    public void setArchivos(ArrayList<Archivo> archivos){
        this.archivos = archivos;
        
        analizar();
    }

    public int getArchivosComprimidos() {
        return archivosComprimidos;
    }

    public int getArchivosConContraseña() {
        return archivosConContraseña;
    }

    public int getArchivosZip() {
        return archivosZip;
    }

    public int getArchivosRar() {
        return archivosRar;
    }

    public int getArchivosNormales() {
        return archivosNormales;
    }
    
    public int getTodosLosArchivos(){
        return archivos.size();
    }
    
    public int getArchivosConContraseñaCorrecta(){
        return archivosConContraseñaCorrecta;
    }
    
    @Override
    public String toString() {
        return "Analizador{" + "archivosComprimidos=" + archivosComprimidos + ", archivosConContrase\u00f1a=" + archivosConContraseña + ", archivosZip=" + archivosZip + ", archivosRar=" + archivosRar + ", archivosNormales=" + archivosNormales + '}';
    }
    
    
}
