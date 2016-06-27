/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Jorge
 */
public class FechaRender extends DefaultTableCellRenderer{
    SimpleDateFormat sdf;
    
    public FechaRender(){super();}
    
    public void setValue(Object value){
        if(sdf == null){
            sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        }
        
        setText((value == null) ? "null" : sdf.format((Date)value));
    }
}
