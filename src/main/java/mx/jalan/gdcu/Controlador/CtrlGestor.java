package mx.jalan.gdcu.Controlador;

import java.awt.Point;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Vista.Gestor.AgregarArchivo;
import mx.jalan.gdcu.Vista.Gestor.Gestor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Presistencia.PresistenciaManager;
import mx.jalan.gdcu.Utils.Utils;
import mx.jalan.gdcu.Vista.Buscador.FiltroBuscadorView;
import mx.jalan.gdcu.Vista.Gestor.Opciones;
import mx.jalan.gdcu.Vista.VistaModelos.MenuTabla;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoGestor;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.ColorRender;
import mx.jalan.gdcu.Vista.VistaModelos.TablaRender.GestorModelo;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class CtrlGestor {
    
    private Gestor vistaGestor;
    
    //private TablaModeloArchivoDetalles tablaModelo;
    private TablaModeloArchivoGestor tablaModelo;
    
    
    
    private FiltroBusqueda filtro;
    private FiltroBusqueda filtroOriginal;
    
    public void abrirGestor(){
        vistaGestor = new Gestor(this);
        //--initTabla
        initTabla();
        
        //--aplicarRenderizados
        GestorModelo modelo = GestorModelo.getInstance();
        modelo.setModelo(tablaModelo);
        ColorRender colorR = new ColorRender();
        vistaGestor.getTablaArchivos().setDefaultRenderer(Object.class, colorR);
        
        
        //--initListeners--
        initBtnListeners();
        initMenuListeners();
        initCloseListener();
        //initDobleClickListener();
        
        //--filtro--
        filtroOriginal = new FiltroBusqueda(-1, -1, null, null, 0, false, false, Utils.getExtensionesPorDefecto());
        
        //--initMenuTabla
        vistaGestor.getTablaArchivos().setMenu(new MenuTabla(vistaGestor.getTablaArchivos()));
    }
    
    private void initTabla(){
        
            //Carga la lista de archivos si es que la hay y lo envia al modelo de la tabla
        tablaModelo = new TablaModeloArchivoGestor(cargarListaArchivosGestor());
        
        //vistaGestor.getTablaArchivos().setModel(tablaModelo);
        
        vistaGestor.getTablaArchivos().setAutoCreateRowSorter(true);
        
        vistaGestor.getTablaArchivos().setModelo(tablaModelo);
        
        vistaGestor.getTablaArchivos().activarRenderizados(1);
        
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
                int rows[] = vistaGestor.getTablaArchivos().getSelectedRows();
                
                if(vistaGestor.getTablaArchivos().getSelectedRow() == -1) return;
                
                String mensaje = null;
                
                if(rows.length > 1){
                    mensaje = "Seguro que deseas eliminar los \""+rows.length+"\" elementos del gestor de contraseñas?";
                }else{
                    String nombre = vistaGestor.getTablaArchivos().getModelo().getArchivo(vistaGestor.getTablaArchivos().convertRowIndexToModel(rows[0])).getName();
                    
                    mensaje = "Seguro que deseas eliminar el archivo \""+nombre+"\" del gestor de contraseñas?";
                }

                int r = JOptionPane.showConfirmDialog(vistaGestor, mensaje, "Seguro que deseas eliminar?", JOptionPane.YES_NO_OPTION);
                
                if(r == JOptionPane.YES_OPTION){
                    for(int x = rows.length ; x > 0 ; x--){
                        int row = vistaGestor.getTablaArchivos().convertRowIndexToModel(rows[x-1]);
                        
                        tablaModelo.getTodosLosArchivos().remove(row);
                        tablaModelo.update();
                    }
                    
                }
                /*
                if(row != -1){
                    Archivo archivo = tablaModelo.getTodosLosArchivos().get(row);
                    int r = JOptionPane.showConfirmDialog(vistaGestor, "Seguro que deseas eliminar el archivo \""+archivo.getName()+"\" del gestor de contraseñas?", "Seguro que deseas eliminar?", JOptionPane.YES_NO_OPTION);
                    if(r == JOptionPane.YES_OPTION){
                        tablaModelo.getTodosLosArchivos().remove(row);
                        tablaModelo.update();
                    }   
                }*/
            }
        });
        
        vistaGestor.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    PresistenciaManager.getInstance().guardarArchivosGestor();
                    JOptionPane.showMessageDialog(vistaGestor, "Guardado satisfactoriamente", "Listo!", JOptionPane.INFORMATION_MESSAGE);
                    /*
                    File listaArchivo = new File(pathListaArchivos);
                    try {
                    if(!listaArchivo.exists()){
                    listaArchivo.createNewFile();
                    }
                    
                    ListaArchivosGestor lag = new ListaArchivosGestor(listaArchivo.getAbsolutePath());
                    
                    lag.guardarListaEnArchivo(tablaModelo.getTodosLosArchivos());
                    
                    } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vistaGestor, "No pudo crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("No pudo crear el archivo");
                    }     */
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vistaGestor, "No pudo crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("No pudo crear el archivo");
                }
                
            }
        });
        
        vistaGestor.getBtnFiltrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(filtro == null){
                    filtro = new FiltroBusqueda(-1, -1, null, null, 0, false, false, Utils.getExtensionesPorDefecto());
                }
                
                FiltroBuscadorView fv = new FiltroBuscadorView(vistaGestor, true, "filtro", filtroOriginal, filtro);
                
                if(fv.getPressOk()){
                    filtro = fv.getFiltroBusqueda();
                    tablaModelo.aplicarFiltro(filtro);
                    
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
    
    public void initMenuListeners(){
        vistaGestor.getMenuOpciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opciones op = new Opciones(vistaGestor, true);
                op.setLocationRelativeTo(null);
                op.setVisible(true);
            }
        });
        
    }
    
    public ArrayList<Archivo> cargarListaArchivosGestor() {
        PresistenciaManager pM = PresistenciaManager.getInstance();
        try {
            pM.cargarArchivosGestor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return pM.getArchivosGestor();
        
        /*File file = new File(pathListaArchivos);
        
        if(file.exists()){
            ListaArchivosGestor lag = new ListaArchivosGestor(pathListaArchivos);
            
            ArrayList<Archivo> lista = null;
            try {
                lista = lag.cargarListaDeArchivo();
            } catch (IOException ex) {
                Logger.getLogger(CtrlGestor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                System.out.println("[DG] No encontro la clase \"Archivo\"");
                return null;
            }
            
            if(lista != null){
                return lista;
            }
        }
        
        return null;*/
    }
    
    public void initDobleClickListener(){
        vistaGestor.getTablaArchivos().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me){
                JTable tabla = (JTable)me.getSource();
                Point p = me.getPoint();
                int row = tabla.rowAtPoint(p);
                if(me.getClickCount() == 2){
                    System.out.println("2 veces!");
                }
            }
        });
    }
    
    public void initCloseListener(){
        vistaGestor.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event){
                GestorModelo.getInstance().setModelo(null);
                System.out.println("El modelo se ha limpiado!");
            }
        });
        
    }
    
    public boolean isVisible(){
        return vistaGestor.isVisible();
    }

    public void traerAdelante() {
        vistaGestor.toFront();
    }
    
}
