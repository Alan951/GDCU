/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos.TablaRender;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge
 */
public class ColorRender extends DefaultTableCellRenderer {
    
    private static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    
    //private static final ARCHIVO_NO_ENCONTRADO = 
            
    public ColorRender(){super();}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Archivo archivo = null;
        if(GestorModelo.getInstance().getModelo() != null)
            archivo = GestorModelo.getInstance().getModelo().getArchivo(row);
        //Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //table.getModel().getValueAt(row, column);
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if(archivo == null){
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
        
        if(isSelected){
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
        
        if(!archivo.exists()){
            setBackground(new Color(255, 255, 0));
        }else{
            setBackground(DEFAULT_RENDERER.getBackground());
        }
        
        return this;
    }
    
}
