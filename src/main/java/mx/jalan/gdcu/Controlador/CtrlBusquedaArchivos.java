/*
    Fecha de creacion: 31/Mayo/2016

    Notas:
    LISTO> Implementar la funcion para poder eliminar mas de 1 fila con seleccion mutiple.
*/
package mx.jalan.gdcu.Controlador;

import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Utils.Buscador;
import mx.jalan.gdcu.Utils.GeneradorHashes;
import mx.jalan.gdcu.Vista.Buscador.BusquedaArchivosDialog;
import mx.jalan.gdcu.Vista.Buscador.BusquedaArchivos;
import mx.jalan.gdcu.Vista.Buscador.FiltroBuscadorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class CtrlBusquedaArchivos {
    
    private BusquedaArchivos busquedaArchivos;
    
    private ArrayList<File> carpetas_a_analizar;
    
    private FiltroBusqueda filtro;
    
    public void abrirBusquedaArchivos(){
        busquedaArchivos = new BusquedaArchivos();
        busquedaArchivos.setLocationRelativeTo(null);
        busquedaArchivos.setVisible(true);
        
        busquedaArchivos.getCbSubCarpetas().setSelected(true);
        
        /*--InitFiltro--//
        Creación del filtro por defecto | Las extensiones por defecto activadas son zip y rar */
        ArrayList<String> extensionesPorDefecto = new ArrayList<String>();
        extensionesPorDefecto.add("zip");
        extensionesPorDefecto.add("rar");
        
        filtro = new FiltroBusqueda(-1, -1, null, null, 0, false, false, extensionesPorDefecto);
        
        //--InitListeners--//
        btnListeners();
    }
    
    /*
        En este metodo se establecen los eventos que tendran los botones.
    */
    public void btnListeners(){
        busquedaArchivos.getBtnInfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(busquedaArchivos.getMostrarInformacion())
                    ocultarInformacion();
                else
                    mostrarInformacion();

                busquedaArchivos.revalidate();
                busquedaArchivos.repaint();
                busquedaArchivos.getPanelInstrucciones().revalidate();
                busquedaArchivos.getPanelInstrucciones().repaint();
            }
        });
        
        busquedaArchivos.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File archivosElegidos[] = null;
                JFileChooser fc = null;
                
                String rutaInicial = System.getProperty("user.home");
                fc = new JFileChooser(new File(rutaInicial));
                
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setMultiSelectionEnabled(true);
                
                int respuesta = fc.showOpenDialog(busquedaArchivos);
                
                if(respuesta == JFileChooser.APPROVE_OPTION){
                    archivosElegidos = fc.getSelectedFiles();
                    
                    for(File archivo : archivosElegidos){
                        agregarRutaTabla(archivo);
                    }
                }
            }
        });
        
        busquedaArchivos.getBtnDel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = busquedaArchivos.getTablaRutas().getSelectedRow();
                if(row == -1){
                    JOptionPane.showMessageDialog(busquedaArchivos, "Debes de seleccionar una ruta de la tabla", "Selecciona una ruta", JOptionPane.ERROR_MESSAGE);
                }
                
                int []rows = busquedaArchivos.getTablaRutas().getSelectedRows();
                
                for(int x = rows.length ; x > 0 ; x--){
                    busquedaArchivos.getModeloTabla().eliminarCarpeta(rows[x-1]);
                }
            }
        });
        
        busquedaArchivos.getBtnMostrarOpciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                FiltroBuscadorView baoa = new FiltroBuscadorView(busquedaArchivos, true, "opav", filtro);
                if(baoa.getPressOk()){
                    filtro = baoa.getFiltroBusqueda();                    
                }
            }
        });
        
        busquedaArchivos.getBtnScan().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    escanear();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(CtrlBusquedaArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void escanear() throws URISyntaxException{
        if(busquedaArchivos.getModeloTabla().getCarpetas().size() == 0){
            JOptionPane.showMessageDialog(busquedaArchivos, "Debes de agregar almenos una ruta", "Agregar una ruta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        carpetas_a_analizar = busquedaArchivos.getModeloTabla().getCarpetas();

        Buscador buscador = new Buscador(filtro, carpetas_a_analizar, busquedaArchivos.getCbSubCarpetas().isSelected());

        BusquedaArchivosDialog bad = new BusquedaArchivosDialog(null, true, buscador);

        bad.setLocationRelativeTo(null);
        bad.setVisible(true);

        if(bad.getVerDetallesPressed()){
            busquedaArchivos.dispose();
            
            CtrlDetalles ctrlBusquedaDetalles = new CtrlDetalles(bad.getArchivos(), filtro);
            
            ctrlBusquedaDetalles.abrirDetalles();
        }
    }
    
    public void agregarRutaTabla(File file){
        busquedaArchivos.getModeloTabla().agregarCarpeta(file);
    }
    
    public void mostrarInformacion(){
        busquedaArchivos.setMostrarInformacion(true);
        busquedaArchivos.getPanelInstrucciones().setVisible(true);
    }
    
    public void ocultarInformacion(){
        busquedaArchivos.setMostrarInformacion(false);
        busquedaArchivos.getPanelInstrucciones().setVisible(false);
    }
    
    public BusquedaArchivos getBusquedaArchivos(){
        return busquedaArchivos;
    }
    
    public boolean isVisible(){
        return busquedaArchivos.isVisible();
    }
    
    public void traerAdelante(){
        busquedaArchivos.toFront();
    }
    
}
