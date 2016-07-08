package mx.jalan.gdcu.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Filtrador {
    
    //private File file;
    //private Archivo archivo;
    private FiltroBusqueda filtro;
    
    public Filtrador(FiltroBusqueda filtro){
        setFiltro(filtro);
    }
    
    public boolean filtrarArchivo(File archivo){
        //TODO corregir metodo contains
        //Filtro archivos por extension
        if(!Utils.esteArrayTieneEsteStr(filtro.getExtensiones(), "*")){
        //if(!filtro.getExtensiones().contains("<*")){ //En caso de que se consideren todas las extensiones entonces no sera necesario analizar las extensiones.
            String extension = Utils.getExtension(archivo);
            boolean extensionCorrecta = false;
            
            for(String ext : filtro.getExtensiones()){
                if(ext.equalsIgnoreCase(extension)){ //Si la extension del archivo es de las requeridas.
                    extensionCorrecta = true;
                    break;
                }
            }
            if(!extensionCorrecta) return false; 
        }
        
        //Filtro de fecha
        Date fechaInf = filtro.getFechaInf();
        Date fechaSup = filtro.getFechaSup();
        Date fechaArchivo = null;
        try{
            fechaArchivo = Utils.getFechaDeCreacionDate(archivo);
            
        }catch(IOException e){
            // TODO : determinar como le dire al usuario que hubo un problema al recuperar la fecha de este archivo.
            System.out.println("Error al generar fecha del archivo: "+archivo.getAbsolutePath());
            //e.printStackTrace();
            return false;
        }
        if(fechaInf != null || fechaSup != null){
            if(fechaInf != null && fechaSup != null){
                if(fechaArchivo.after(fechaInf) || fechaArchivo.before(fechaSup)){
                    return false;
                }
            }else if(fechaInf != null){
                if(!(fechaArchivo.before(fechaInf))){
                    return false;
                }
            }else if(fechaSup != null){
                if(!(fechaArchivo.after(fechaSup))){
                    return false;
                }
            }
        }
        
        //Filtro de archivo por atributo <visibilidad>
        if(!filtro.archivoPuedeSerOcuto()){
            if(archivo.isHidden()){
                return false;
            }
        }
        
        //Filtro por tamaño del archivo
        long inf = -1;
        long sup = -1;
        
        if(filtro.getUnidadInf() != null)
            inf = Utils.getByte(filtro.getTamañoInf(), filtro.getUnidadInf());
        
        if(filtro.getUnidadSup() != null)
            sup = Utils.getByte(filtro.getTamañoSup(), filtro.getUnidadSup());
        
        if(inf != -1 || sup != -1){
            if(inf != -1 && sup != -1){
                if(archivo.length() > inf || archivo.length() < sup){
                    return false;
                }
            }else if(inf != -1){
                if(!(archivo.length() < inf)){
                    return false;
                }
            }else if(sup != -1){
                if(!(archivo.length() > sup)){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void setFiltro(FiltroBusqueda filtro){
        this.filtro = filtro;
    }
}
