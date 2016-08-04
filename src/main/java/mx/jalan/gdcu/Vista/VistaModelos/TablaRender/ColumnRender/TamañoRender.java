package mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender;

import javax.swing.table.DefaultTableCellRenderer;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColorRender;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jorge
 */
public class TamañoRender extends DefaultTableCellRenderer{
    
    public TamañoRender(){super();}
    
    public void setValue(Object value){
        setText((value == null) ? "null" : FileUtils.byteCountToDisplaySize((long)value));
    }
    
}
