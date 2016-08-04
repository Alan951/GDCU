/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender;

import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColorRender;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class RutaRender extends DefaultTableCellRenderer{
    
    public RutaRender(){super();}
    
    public void setValue(Object value){
        String ruta = null;
        
        Archivo f = (Archivo)value;
        
        setText((f == null) ? "No encontrada" : f.getAbsolutePath());
    }
    
}
