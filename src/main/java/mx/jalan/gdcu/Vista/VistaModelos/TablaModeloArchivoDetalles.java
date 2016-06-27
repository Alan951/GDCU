/*
    Informacion de interes:
    nombre | tiene contraseña | fecha de creacion | tamaño | hash
*/

package mx.jalan.gdcu.Vista.VistaModelos;

import java.io.File;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Utils.Filtrador;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class TablaModeloArchivoDetalles extends AbstractTableModel{
    
    private ArrayList<Archivo> archivos;
    
    private ArrayList<Archivo> archivosFiltrados;
    
    private Filtrador filtrador;
    
    
    public void aplicarFiltro(FiltroBusqueda filtro){    
        if(filtrador == null){
            filtrador = new Filtrador(filtro);
        }else{
            filtrador.setFiltro(filtro);
        }
        
        if(archivosFiltrados == null){
            archivosFiltrados = new ArrayList<Archivo>();
        }else{
            archivosFiltrados.clear();
        }
        
        for(File archivo : archivos){
            if(filtrador.filtrarArchivo(archivo)){
                archivosFiltrados.add(new Archivo(archivo.getAbsolutePath()));
            }
        }
        
        update();
    }
    
    public void agregarArchivo(Archivo archivo){
        archivos.add(archivo);
        update();
    }
    
    public ArrayList<Archivo> getTodosLosArchivos(){
        if(archivosFiltrados != null)
            return archivosFiltrados;
        else
            return archivos;
    }
    
    public TablaModeloArchivoDetalles(ArrayList<Archivo> archivos){
        if(archivos != null)
            this.archivos = archivos;
        else
            this.archivos = new ArrayList<Archivo>();
    }
    
    public void update(){
        this.fireTableDataChanged();
    }
    
    @Override
    public Class getColumnClass(int column){
        String columna = getColumnName(column);
        
        switch(columna){
            case "Fecha de creacion":
                return java.util.Date.class;
            case "Tamaño":
                return Long.class;
            default: return Object.class;
        }
    }

    @Override
    public int getRowCount() {
        if(archivosFiltrados != null){
            return archivosFiltrados.size();
        }else{
            return archivos.size();
        }
    }
    
    public String getColumnName(int columnIndex){
        switch(columnIndex){
            case 0: return "Nombre";
            case 1: return "Tamaño";
            case 2: return "Ruta";
            case 3: return "Fecha de creacion";
            case 4: return "Contrasña";
            case 5: return "Hash MD5"; 
            default: return "NA";
        }
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Archivo archivo = null;
        
        if(archivosFiltrados != null)
            archivo = archivosFiltrados.get(rowIndex);
        else
            archivo = archivos.get(rowIndex);
        
        switch(columnIndex){
            case 0: return archivo.getName();
            case 1: return archivo.length();
            case 2: return archivo.getAbsolutePath();
            case 3: 
                Date fecha = null;
                
                try{
                    fecha = Utils.getFechaDeCreacionDate(archivo);
                }catch(IOException e){
                    e.printStackTrace();
                }
                
                return fecha; 
            case 4: 
                if(archivo.getEstaCifrado()){
                    if(archivo.getPassword() != null){
                        return archivo.getPassword();
                    }
                    return "Si";
                }else{
                    return "No";
                }
            case 5:
                if(archivo.getHash() != null){
                    return archivo.getHash();
                }else{
                    return "No se ha generado hash";
                }
            default: return "NA";
        }
    }
    
}
