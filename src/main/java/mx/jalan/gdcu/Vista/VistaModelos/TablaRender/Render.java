package mx.jalan.gdcu.Vista.VistaModelos.TablaRender;

import javax.swing.table.DefaultTableCellRenderer;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.TamañoRender;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.RutaRender;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.FechaRender;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.FechaRenderStyle;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.RutaRenderStyle;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColumnRender.TamañoRenderStyle;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Render{
    
    private int type;
    //1 - crea renderizados para gestor de contraseñas
    //2 - crea renderizados para la tabla buscador detalles
        
    public Render(){};
    
    public Render(int type){
        this.type = type;
    }
    
    public DefaultTableCellRenderer getFechaRender(){
        if(type == 2)
            return new FechaRender();
        else if(type == 1)
            return new FechaRenderStyle();
        
        
        return null;
    }
    
    public DefaultTableCellRenderer getTamañoRender(){
        if(type == 2)
            return new TamañoRender();
        else if(type == 1)
            return new TamañoRenderStyle();
        
        return null;
    }
    
    public DefaultTableCellRenderer getRutaRender(){
        if(type == 2)
            return new RutaRender();
        else if(type == 1)
            return new RutaRenderStyle();
        
        return null;
    }
}
