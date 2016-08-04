/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Propiedades {
    
    private static Propiedades prop;
    
    private static Properties propiedades;
    
    private final String CONFIG_PATH = "config.prop";
    
    public static final String WINRAR_PATH = "WinrarPath";
    public static final String LAST_PATH = "LastPath";
    
    protected Propiedades(){
        File archivo = new File(CONFIG_PATH);
        
        if(!archivo.exists()){
            initPropiedades(archivo);
        }else{
            cargarPropiedades(archivo);
        }
    }
    
    /*
        Singleton for the win
    */
    public static Propiedades getInstance(){
        if(prop == null)
            prop = new Propiedades();
        
        return prop;
    }
    
    public void initPropiedades(File archivo){
        propiedades = new Properties();
        
        try {
            archivo.createNewFile();
            
            OutputStream out = new FileOutputStream(CONFIG_PATH);
            
            propiedades.setProperty(WINRAR_PATH, "");
            propiedades.setProperty(LAST_PATH, System.getProperty("user.home"));
            
            propiedades.store(out, null);
            System.out.println("guardo las propiedades!");
        } catch (FileNotFoundException ex) {
            System.out.println("[DG] No encontro el archivo properties");
        } catch (IOException ex) {
            System.out.println("[DG] No pudo guardar el archivo o realizar modificaciones en el");
        }
    }
    
    public void cargarPropiedades(File archivo){
        InputStream in = null;
        
        propiedades = new Properties();
        
        try {
            in = new FileInputStream(archivo);
            propiedades.load(in);
        } catch (FileNotFoundException ex) {
            System.out.println("[DG] El archivo properties no fue encontrado");
        } catch (IOException ex) {
            System.out.println("[DG] No pudo cargar las propiedades del archivo properties");
        }
        
    }
    
    public void guardarPropiedades(){
        OutputStream out = null;
        
        try {
            out = new FileOutputStream(CONFIG_PATH);
            
            propiedades.store(out, null);
            
            JOptionPane.showMessageDialog(null, "Opciones guardadas con exito");
        } catch (FileNotFoundException ex) {
            System.out.println("[DG] No pudo encontrar el archivo properties");
        } catch (IOException ex){
            System.out.println("[DG] No pudo guardar el archivo properties");
        }
    }
    
    public void setPropertie(String key, String value){
        propiedades.setProperty(key, value);
    }
    
    public String getPropertie(String key){
        return propiedades.getProperty(key);
    }
    
    
    
}
