/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Utils;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColorRender;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.Render;

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
                //super.mouseReleased(e);
                int r = rowAtPoint(e.getPoint());
                
                int rowIndex = getSelectedRow();
                
                if(rowIndex == -1)  return;
                
                if(getSelectedRows().length == 1 && SwingUtilities.isRightMouseButton(e)){
                    setRowSelectionInterval(r, r);
                }
                
                if(e.isPopupTrigger() && e.getComponent() instanceof JTable){
                    ArrayList<Archivo> archivos = new ArrayList<Archivo>();
                    for(int row : getSelectedRows()){
                        archivos.add(getModelo().getArchivo(convertRowIndexToModel(row)));
                    }
                    
                    menu.mostrarMenu(archivos, e);
                }
            }
        });
    }
    
    /*
        Al utilizar el setAutoCreateRowSorter del jtable para poder organizar por orden
        las distintas columnas, al intentar poner en orden la columna de fecha o tamaño del archivo
        no tomaba en cuenta que fuese un date o un tamaño, por lo tanto lo consideraba como un simple numero
        y obviamente esto hacía que lo organizara mal. Para esto cambie el tipo de clase de la columna desde
        el modelo de la tabla (Vista.VistaModelos.TablaModeloArchivoDetalles) utilizando el metodo
        getColumnClass y dependiendo del nombre de la cabecera de la columna retornara tal clase, por ejemplo
        cuando java le pida el nombre de la clase de la columna fecha, en ves de que regrese la clase String, regresara
        la clase Date y ahora si lo considerara como fecha.
    
        El renderer sirve para que en ves de que se vea en la celda como un Date, se vea ya formateado como la fecha String
        Conclusión, tu lo vés la fecha como string con tu formato preferido pero por dentro es un date y esto se hace para que se pueda
        organizar correctamente.
    */
    public void activarRenderizados(int type){
        TableColumnModel columnModel = getColumnModel();
        Render render = new Render(type);
        
        columnModel.getColumn(Utils.getPosColumn(this, "Ruta")).setCellRenderer(render.getRutaRender());
        columnModel.getColumn(Utils.getPosColumn(this, "Fecha de creación")).setCellRenderer(render.getFechaRender());
        columnModel.getColumn(Utils.getPosColumn(this, "Tamaño")).setCellRenderer(render.getTamañoRender());
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
