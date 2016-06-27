package mx.jalan.gdcu.Presistencia;

import mx.jalan.gdcu.Modelo.Archivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class ListaArchivosGestor {
    
    private String path;
        

    public ListaArchivosGestor(String pathname){
        path = pathname;
        

    }
    
    public void guardarListaEnArchivo(ArrayList<Archivo> archivos) throws FileNotFoundException, IOException{
        File archivo = new File(path);
        FileOutputStream fos = new FileOutputStream(archivo);
        
        FSTObjectOutput out = new FSTObjectOutput(fos);
        
        ListaGestorEnvoltorio lge = new ListaGestorEnvoltorio();
        lge.setArchivos(archivos);
        
        out.writeObject(lge);
        out.close();
    }
    
    public ArrayList<Archivo> cargarListaDeArchivo()throws FileNotFoundException, IOException, ClassNotFoundException{
        File archivo = new File(path);
        FileInputStream fis = new FileInputStream(archivo);
        FSTObjectInput in = new FSTObjectInput(fis);
        
        ListaGestorEnvoltorio lge = null;
        
        System.out.println(in.available());
        
        lge = (ListaGestorEnvoltorio)in.readObject();
        
        in.close();
        
        return lge.getArchivos();
    }
    
}
