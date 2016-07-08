package mx.jalan.gdcu.Vista.VistaModelos.TablaRender;

import javax.swing.table.DefaultTableCellRenderer;
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
