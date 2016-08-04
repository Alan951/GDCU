package mx.jalan.gdcu.Vista.VistaModelos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge
 */
public class TablaModeloArchivoGestor extends TablaModeloArchivoDetalles{
    
    public TablaModeloArchivoGestor(ArrayList<Archivo> archivos) {
        super(archivos);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Archivo archivo = null;
        
        if(super.archivosFiltrados != null)
            archivo = archivosFiltrados.get(rowIndex);
        else
            archivo = archivos.get(rowIndex);
        
        switch(columnIndex){
            case 0: return archivo.getNombre();
            case 1: return archivo.getTamaño();
            case 2: return archivo;
            case 3: 
                Date fecha = null;
                
                //try{
                fecha = archivo.getFechaCreacion();
                //}catch(IOException e){
                //    e.printStackTrace();
                    
                //    System.out.println("[DG] No pudo generar fecha del archivo \""+archivo.getName()+"\".");
                //}
                
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
