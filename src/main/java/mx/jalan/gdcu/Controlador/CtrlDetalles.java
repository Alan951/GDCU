/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Vista.Buscador.Detalles;
import java.util.ArrayList;
import javax.swing.table.TableColumnModel;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Utils.Utils;
import mx.jalan.gdcu.Vista.Buscador.FiltroBuscadorView;
import mx.jalan.gdcu.Vista.VistaModelos.Render;

/**
 *
 * @author Jorge
 */
public class CtrlDetalles {
    
    private Detalles bd;
    
    private ArrayList<Archivo> archivos;
    
    private FiltroBusqueda filtroOriginal;
    
    private FiltroBusqueda filtro;
    
    public CtrlDetalles(ArrayList<Archivo> archivos, FiltroBusqueda filtro){
        this.archivos = archivos;
        
        this.filtroOriginal = filtro;
        this.filtro = filtro;
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
    public void initColumnModel(){
        TableColumnModel m = bd.getTabla().getColumnModel();
        
        m.getColumn(Utils.getPosColumn(bd.getTabla(), "Fecha de creacion")).setCellRenderer(Render.getFechaRender());
        m.getColumn(Utils.getPosColumn(bd.getTabla(), "Tamaño")).setCellRenderer(Render.getTamañoRender());
    }
        
    public void abrirDetalles(){
        bd = new Detalles(this);
        bd.setLocationRelativeTo(null);
        bd.setVisible(true);
        
        //--initListenerBTN
        initBtnListener();
        
        //--render&model
        initColumnModel();
    }
    
    public void initBtnListener(){
        bd.getBtnFiltro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                FiltroBuscadorView fv = new FiltroBuscadorView(bd, true, "filtro", filtroOriginal, filtro);
        
                if(fv.getPressOk()){
                    filtro = fv.getFiltroBusqueda();
                    bd.getModelo().aplicarFiltro(filtro);
                }
            }
        });
    }
    
        public ArrayList<Archivo> getArchivos(){
        return archivos;
    }
}
