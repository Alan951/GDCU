/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.jalan.gdcu.Modelo.Archivo;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Jorge
 */
public class FuerzaBruta {
    
    private ArrayList<String> listaContraseñas;
    private ArrayList<Archivo> archivos;
    
    private FileManager fM;
    
    public FuerzaBruta(){}
    
    public FuerzaBruta(Archivo archivo) throws URISyntaxException{
        archivos = new ArrayList<Archivo>();
        
        archivos.add(archivo);
        
        fM = new FileManager();
    }
    
    public FuerzaBruta(ArrayList<Archivo> archivos) throws URISyntaxException{
        this.archivos = archivos;
        
        fM = new FileManager();
    }
    
    public void setArchivos(ArrayList<Archivo> archivos){
        this.archivos = archivos;
    }
    
    public void setListaContraseñas(ArrayList<String> listaContraseñas){
        this.listaContraseñas = listaContraseñas;
    }
    
    public boolean verificarArchivoCifrado(Archivo archivo){
        boolean archivoCifrado = false;
        
        fM.setPathFile(archivo.getAbsolutePath());
        
        try {
            
            archivoCifrado = (boolean)fM.analizarArchivo()[2];
            if(archivoCifrado && archivo.getPassword() != null){
                fM.setPassword(archivo.getPassword());
                if(!fM.validarContraseña()){
                    archivoCifrado = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FuerzaBruta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ZipException ex) {
            Logger.getLogger(FuerzaBruta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return archivoCifrado;
    }
    
    public void go() throws IOException, ZipException{
        for(Archivo archivo : archivos){
            if(!verificarArchivoCifrado(archivo)){
                continue;
            }
            
            fM.setPathFile(archivo.getAbsolutePath());
            
            //for(int x = 0 ; x < listaContraseñas.size() ; x++)
            for(String pass : listaContraseñas){
                fM.setPassword(pass);
                if(fM.validarContraseña()){
                    archivo.setPassword(pass);
                    break;
                }
            }
        }
    }
    
    public ArrayList<Archivo> getArchivos(){
        return archivos;
    }
    
}
