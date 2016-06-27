package mx.jalan.gdcu.Controlador;

import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Presistencia.ListaArchivosGestor;
import mx.jalan.gdcu.Vista.Gestor.AgregarArchivo;
import mx.jalan.gdcu.Vista.Gestor.Gestor;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import mx.jalan.gdcu.Utils.Utils;
import mx.jalan.gdcu.Vista.VistaModelos.Render;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class CtrlGestor {
    
    private Gestor vistaGestor;
    
    private TablaModeloArchivoDetalles tablaModelo;
    
    private final String pathListaArchivos = "GestorArchivoLista.dat";
    
    public void abrirGestor(){
        vistaGestor = new Gestor(this);
        
        try {
            initTabla();
        } catch (FileNotFoundException ex) {}
        
        //--initListeners--
        initBtnListeners();
    }
    
    private void initTabla() throws FileNotFoundException{
        //Carga la lista de archivos si es que la hay y lo envia al modelo de la tabla
        tablaModelo = new TablaModeloArchivoDetalles(cargarListaArchivosGestor());
        
        vistaGestor.getTablaArchivos().setModel(tablaModelo);
        
        TableColumnModel m = vistaGestor.getTablaArchivos().getColumnModel();
        
        m.getColumn(Utils.getPosColumn(vistaGestor.getTablaArchivos(), "Fecha de creacion")).setCellRenderer(Render.getFechaRender());
        m.getColumn(Utils.getPosColumn(vistaGestor.getTablaArchivos(), "Tamaño")).setCellRenderer(Render.getTamañoRender());
        
        vistaGestor.getTablaArchivos().validate();
        vistaGestor.getTablaArchivos().repaint();
    }
    
    private void initBtnListeners(){
        vistaGestor.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarArchivo agregarArchivo = new AgregarArchivo(vistaGestor, true);
                if(agregarArchivo.getOkPressed()){
                    tablaModelo.agregarArchivo(agregarArchivo.getArchivoSeleccionado());
                }
            }
        });
        
        vistaGestor.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = vistaGestor.getTablaArchivos().getSelectedRow();
                
                if(row != -1){
                    Archivo archivo = tablaModelo.getTodosLosArchivos().get(row);
                    int r = JOptionPane.showConfirmDialog(vistaGestor, "Seguro que deseas eliminar el archivo \""+archivo.getName()+"\" del gestor de contraseñas?", "Seguro que deseas eliminar?", JOptionPane.YES_NO_OPTION);
                    if(r == JOptionPane.YES_OPTION){
                        tablaModelo.getTodosLosArchivos().remove(row);
                        tablaModelo.update();
                    }   
                }
            }
        });
        
        vistaGestor.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File listaArchivo = new File(pathListaArchivos);
                try {
                    if(!listaArchivo.exists()){
                        listaArchivo.createNewFile();
                    }
                    
                    ListaArchivosGestor lag = new ListaArchivosGestor(listaArchivo.getAbsolutePath());
                    
                    lag.guardarListaEnArchivo(tablaModelo.getTodosLosArchivos());
                    JOptionPane.showMessageDialog(vistaGestor, "Guardado satisfactoriamente", "Listo!", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vistaGestor, "No pudo crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("No pudo crear el archivo");
                }     
            }
        });
        
        //TODO: implementar la funcion de exportar la lista del gestor a json y permitir importar
        vistaGestor.getBtnExportar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vistaGestor, "Estoy trabajando en esto aun :/", "Ops!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    public ArrayList<Archivo> cargarListaArchivosGestor() throws FileNotFoundException{
        File file = new File(pathListaArchivos);
        
        if(file.exists()){
            ListaArchivosGestor lag = new ListaArchivosGestor(pathListaArchivos);
            
            ArrayList<Archivo> lista = null;
            try {
                lista = lag.cargarListaDeArchivo();
            } catch (IOException ex) {
                Logger.getLogger(CtrlGestor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                return null;
            }
            
            if(lista != null){
                return lista;
            }
        }
        
        return null;
    }
    
    public boolean isVisible(){
        return vistaGestor.isVisible();
    }

    public void traerAdelante() {
        vistaGestor.toFront();
    }
    
}
