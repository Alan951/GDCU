/*
    Esta clase ayudara a analizar una lista de archivos
    y checar cuantos archivos comprimidos hay, cuantos archivos con contraseña hay, etc... (Siempre y cuando sean archivos que ya fueron verificados)
*/

package mx.jalan.gdcu.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mx.jalan.gdcu.Modelo.Archivo;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Analizador {
    
    private ArrayList<Archivo> archivos;
    
    private HashMap<String, Integer> extensiones;
    
    private int archivosComprimidos;
    private int archivosConContraseñaCorrecta;
    private int archivosConContraseña;
    private int archivosZip;
    private int archivosZipContraseña;
    private int archivosRar;
    private int archivosRarContraseña;
    private int archivosNormales;
    private int archivosInvalidos;
    private int archivosHasheados;
    
    
    
    public Analizador(ArrayList<Archivo> archivos, boolean soloExistentes){
        this.archivos = archivos;
        
        if(soloExistentes){
            eliminarArchivosNoExistentes();
        }
        
        analizar();
    }
    
    public void analizar(){
        archivosConContraseña = cuantosArchivosConContraseñaHay();
        archivosComprimidos = cuantosArchivosComprimidosHay();
        
        archivosInvalidos = cuantosArchivosInvalidosHay();
        
        archivosHasheados = cuantosArchivosHasheadosHay();
        
        System.out.println(toString());
    }
    
    public int cuantosArchivosConContraseñaHay(){
        int archivosConContraseña = 0;
        
        for(Archivo archivo : archivos){
            if(archivo.getEstaCifrado()){
                archivosConContraseña++;
                
                if(Utils.esRar(archivo))
                    archivosRarContraseña++;
                if(Utils.esZip(archivo))
                    archivosZipContraseña++;
                
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
    
    public void appendArchivos(ArrayList<Archivo> archivos){
        /*for(Archivo archiv : archivos ){
            this.archivos.addAll(archivos);
        }*/
        
        this.archivos.addAll(archivos);
    }
    
    public void analizarExtensiones(){
        if(extensiones == null)
            extensiones = new HashMap<String, Integer>();
        
        String extension = null;
        
        for(Archivo archivo : archivos){
            extension = Utils.getExtension(archivo);
            
            if(extensiones.containsKey(extension)){
                int cantidad = extensiones.get(extension);
                
                extensiones.replace(extension, cantidad);
            }else{
                extensiones.put(extension, 1);
            }
        }
    }
    
    public int cuantosArchivosInvalidosHay(){
        int invalidos = 0;
        
        for(Archivo archivo : archivos){
            if(!archivo.getEsValido()){
                invalidos++;
            }
        }
        
        return invalidos;
    }
    
    public void eliminarArchivosNoExistentes(){
        archivos.removeIf(f -> !f.exists());
        System.out.println(archivos.size());
        /*for(Archivo archivo : archivos){
            if(!archivo.exists()){
                
                archivos.remove(archivo);
                System.out.println("Archivo eliminado: "+archivo.getNombre());
            }
        }*/
    }
    
    public int cuantosArchivosHasheadosHay(){
        int archivosHasheados = 0;
        
        for(Archivo archivo : archivos){
            if(archivo.getHash() != null){
                archivosHasheados++;
            }
        }
        
        return archivosHasheados;
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

    public int getArchivosZipContraseña() {
        return archivosZipContraseña;
    }

    public int getArchivosRarContraseña() {
        return archivosRarContraseña;
    }

    public int getArchivosInvalidos() {
        return archivosInvalidos;
    }
    
    public int getArchivosHasheados(){
        return archivosHasheados;
    }
    
    public ArrayList<Archivo> getLista(){
        return archivos;
    }
    
    @Override
    public String toString() {
        return "Analizador{" + "archivosComprimidos=" + archivosComprimidos + ", archivosConContrase\u00f1aCorrecta=" + archivosConContraseñaCorrecta + ", archivosConContrase\u00f1a=" + archivosConContraseña + ", archivosZip=" + archivosZip + ", archivosZipContrase\u00f1a=" + archivosZipContraseña + ", archivosRar=" + archivosRar + ", archivosRarContrase\u00f1a=" + archivosRarContraseña + ", archivosNormales=" + archivosNormales + '}';
    }
}
