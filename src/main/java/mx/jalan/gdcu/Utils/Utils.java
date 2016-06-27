/*
    Fecha de creación: 1/Junio/2016
    Ultima modificación: 17/Junio/2016
*/

package mx.jalan.gdcu.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Utils {
    
    public static String getExtension(File archivo){
        String[] data = archivo.getName().split("\\.");
        
        if(data.length == 2){
            return data[1];
        }else if(data.length > 2){
            return data[data.length-1];
        }else{
            return null;
        }
    }
    
    public static String getExtension(String path){
        String[] data = path.split("\\.");
        
        if(data.length == 2){
            return data[1];
        }else if(data.length > 2){
            return data[data.length-1];
        }else if(data[0].equals("*")){
            return data[0];
        }else if(data.length == 1){
            return data[0];
        }else{
            return null;
        }
    }
    
    /*
        Obtiene el tamaño de un archivo con su unidad, por ejemplo, si se utiliza el metdo
        f.length() y este devuelve 17179869184 entonces este metodo retornara 16 GB
    */
    public static String getTamaño(File f){
        return FileUtils.byteCountToDisplaySize(f.length());
    }
    
    /*
        Hace justo lo contrario al metodo anterior, necesita tanto el numero como su unidad
        por ejemplo, si numero = 16 y la unidad = GB entonces regresara 17179869184
        Posdata: El algoritmo es mio, por lo tanto, puede ser un poco feo a la vista.
    */
    public static long getByte(String numero, String unidad){
        if(numero.isEmpty()){
            return -1L;
        }
        
        BigDecimal bd =new BigDecimal(numero);
        
        long numeroL = bd.longValue();
        
        switch(unidad){
            case "KB":  return numeroL * (long)Math.pow(1024, 1);
            case "MB":  return numeroL * (long)Math.pow(1024, 2);
            case "GB":  return numeroL * (long)Math.pow(1024, 3);
            default: return -1L;
        }
    }
    
    public static long getByte(double numero, String unidad){
        return getByte(Double.toString(numero), unidad);
    }
    
    /*
        Obtiene la fecah de creación de un archivo y formatea la opcion para que se vea bonito
    */
    public static String getFechaDeCreacionStr(File f)throws IOException{
        Path path = f.toPath();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        BasicFileAttributes atributos = null;
    
        atributos = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        
        String fecha = df.format(atributos.creationTime().toMillis());
        
        return fecha; 
    }
    
    /*
        Hace exactamente lo anterior pero no lo formatea y devuelve el Date
    */
    public static Date getFechaDeCreacionDate(File f)throws IOException{
        Path path = f.toPath();
        BasicFileAttributes atributos = null;
        
        atributos = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        
        return new Date(atributos.creationTime().toMillis());
    }
    
    /*
        Convierte un date a un String ya con formato
    */
    public static String dateToStr(Date dFecha){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        String fecha = null;
        
        fecha = df.format(dFecha);
        
        return fecha;
        
    }
    
    /*
        Obtiene la posición de una columna a partir de su nombre
        Pienso que este metodo me servira de mucho cuando le de la posibilidad
        al usuario de poder agregar o quitar columnas en algunas tablas para su comodidad
    */
    public static int getPosColumn(JTable tabla, String nombreColumna){
        int columnas = tabla.getColumnCount();
        
        for(int x = 0 ; x < columnas ; x++){
            if(nombreColumna.equals(tabla.getColumnModel().getColumn(x).getHeaderValue()))
                return x;
        }
        
        return -1;
    }
}
