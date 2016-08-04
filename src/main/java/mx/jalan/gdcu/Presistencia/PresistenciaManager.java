/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Presistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.jalan.gdcu.Modelo.Archivo;

/**
 *
 * @author Jorge
 */
public class PresistenciaManager {
    private static PresistenciaManager instance;
    
    protected PresistenciaManager(){};
    
    private final String PATH_LISTA_ARCHIVOS_GESTOR = "GestorArchivoLista.dat";
    
    ArrayList<Archivo> archivosGestor;
    
    public static PresistenciaManager getInstance(){
        if(instance == null){
            instance = new PresistenciaManager();
        }
        
        return instance;
    }
    
    public ArrayList<Archivo> getArchivosGestor(){
        if(archivosGestor == null){
            ListaArchivosGestor lag = new ListaArchivosGestor(PATH_LISTA_ARCHIVOS_GESTOR);
            
            try {
                archivosGestor = lag.cargarListaDeArchivo();
            } catch (IOException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PresistenciaManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return archivosGestor;
    }
    
    public void guardarArchivosGestor() throws IOException{
        Archivo presist = new Archivo(PATH_LISTA_ARCHIVOS_GESTOR);
        
        if(!presist.exists()){
            presist.createNewFile();
        }
        
        ListaArchivosGestor lag = new ListaArchivosGestor(PATH_LISTA_ARCHIVOS_GESTOR);
        
        lag.guardarListaEnArchivo(archivosGestor);
    }
    
    public void cargarArchivosGestor() throws IOException, FileNotFoundException, ClassNotFoundException{
        Archivo presist = new Archivo(PATH_LISTA_ARCHIVOS_GESTOR);
        
        if(!presist.exists()){
            archivosGestor = null;
        }else{
            ListaArchivosGestor lag = new ListaArchivosGestor(PATH_LISTA_ARCHIVOS_GESTOR);
        
            archivosGestor = lag.cargarListaDeArchivo();
        }
        
        
    }
        
    
}
