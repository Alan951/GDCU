/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColorRender;

/**
 *
 * @author Jorge
 */
public class FechaRenderStyle extends ColorRender{
    SimpleDateFormat sdf;
    
    public FechaRenderStyle(){super();}
    
    public void setValue(Object value){
        if(sdf == null){
            sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        }
        
        setText((value == null) ? "null" : sdf.format((Date)value));
    }
}
