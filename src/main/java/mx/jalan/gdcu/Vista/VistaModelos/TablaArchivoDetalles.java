/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge
 */
public class TablaArchivoDetalles extends JTable{
    
    private MenuTabla menu;
    private TablaModeloArchivoDetalles modelo;
    
    public void setMenu(MenuTabla menu){
        this.menu = menu;
        //THANKS clamp | http://stackoverflow.com/questions/3558293/java-swing-jtable-right-click-menu-how-do-i-get-it-to-select-aka-highlight-t
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                int r = rowAtPoint(e.getPoint());
                if(r >= 0 && r < getRowCount()){
                    setRowSelectionInterval(r, r);
                }else{
                    clearSelection();
                }
                
                int rowIndex = getSelectedRow();
                if(rowIndex < 0)
                    return;
                if(e.isPopupTrigger() && e.getComponent() instanceof JTable){
                    
                    if(!Utils.esArchivoComprimido(modelo.getArchivo(convertRowIndexToModel(rowIndex))))
                        menu.noVisible(1);
                    else
                        menu.limpiar();
                    
                    menu.getMenu().show(e.getComponent(), e.getX(), e.getY());
                    
                }
            }
        });
    }
    
    public MenuTabla getMenu(){
        return menu;
    }
    
    public void setModelo(TablaModeloArchivoDetalles modelo){
        this.modelo = modelo;
        
        setModel(modelo);
    }
    
    public TablaModeloArchivoDetalles getModelo(){
        return modelo;
    }
}
