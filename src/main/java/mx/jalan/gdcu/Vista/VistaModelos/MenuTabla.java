/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import Properties.Propiedades;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Analizador;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class MenuTabla {
    
    private JPopupMenu popupMenu;
    
    private JMenuItem abrirConExplorer;
    private JMenuItem abrirConWinrar;
    private JMenuItem copiarContraseña;
    private JMenuItem copiarContraseñaYabrir;
    private JMenuItem generarHash;
    private JMenuItem generarHashYcopiar;
    
    private final String ABRIR_CON_WINRAR = "Abrir con winrar";
    private final String ABRIR_CON_EXPLORER = "Abrir con explorer";
    private final String COPIAR_CONTRASEÑA = "Copiar contraseña";
    private final String COPIAR_CONTRASEÑA_Y_ABRIR = "Copiar contraseña y abrir con winrar";
    
    private TablaArchivoDetalles tabla;
    private TablaModeloArchivoDetalles modelo;
    
    private String winrarPath = null;
    
    public MenuTabla(TablaArchivoDetalles tabla){
        this.tabla = tabla;
        this.modelo = tabla.getModelo();
        
        popupMenu = new JPopupMenu();
        
        abrirConExplorer = new JMenuItem(ABRIR_CON_EXPLORER);
        abrirConWinrar = new JMenuItem(ABRIR_CON_WINRAR);
        copiarContraseña = new JMenuItem(COPIAR_CONTRASEÑA);
        copiarContraseñaYabrir = new JMenuItem(COPIAR_CONTRASEÑA_Y_ABRIR);
        
        
        popupMenu.add(abrirConExplorer);
        popupMenu.add(abrirConWinrar);
        popupMenu.add(copiarContraseña);
        popupMenu.add(copiarContraseñaYabrir);
        //--listeners--
        initListeners();
        
        //--winrarPath
        Propiedades prop = Propiedades.getInstance();
        winrarPath = prop.getPropertie(Propiedades.WINRAR_PATH);
        if(!(winrarPath != null && !winrarPath.equals(""))){
            winrarPath = null;
        }   
    }
    
    public void initListeners(){
        abrirConExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = tabla.getSelectedRows();
                
                for(int row : rows){
                    row = tabla.convertRowIndexToModel(row);
                    abrirArchivoConExplorer(tabla.getModelo().getArchivo(row));
                }
            }
        });
        
        abrirConWinrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = tabla.getSelectedRows();
                
                for(int row : rows){
                    row = tabla.convertRowIndexToModel(row);
                    abrirArchivoConWinrar(tabla.getModelo().getArchivo(row));
                }
            }
        });
        
        copiarContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                
                row = tabla.convertRowIndexToModel(row);
                
                copiarContraseña(tabla.getModelo().getArchivo(row));
            }
        });
        
        copiarContraseñaYabrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                
                row = tabla.convertRowIndexToModel(row);                
                copiarContraseñaYabrir(tabla.getModelo().getArchivo(row));
            }
        });
    }
    
    public boolean abrirArchivoConWinrar(Archivo archivo){    
        if(winrarPath == null){
            JOptionPane.showMessageDialog(null, "Debes de ir a las opciones e introducir la ruta de WinRAR de tu sistema para poder útilizar esta opción.", "Ops", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String args[] = {winrarPath, archivo.getAbsolutePath()};

        ProcessBuilder pb = new ProcessBuilder(args);

        try {
            Process p = pb.start();
        } catch (IOException ex) {
            System.out.println("[DG] Hubo un error en la ejecución del archivo");

            JOptionPane.showMessageDialog(null, "Hubo un error con la ruta de winrar\n\""+winrarPath+"\"\nVerifica que la ruta sea la adecuada en las opciones de GDCU.", "Error al abrir con winrar", JOptionPane.ERROR_MESSAGE);
        }
        
        return true;
    }
    
    public boolean abrirArchivoConExplorer(Archivo archivo){
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().open(archivo.getParentFile());
            } catch (IOException ex) {
                //TODO: problema si el archivo no tiene padre
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    
    public void copiarContraseña(Archivo archivo){
        String contraseña = archivo.getPassword();
        
        StringSelection strSelection = new StringSelection(contraseña);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        clpbrd.setContents(strSelection, null);
    }
    
    public boolean copiarContraseñaYabrir(Archivo archivo){
        copiarContraseña(archivo);
        return abrirArchivoConWinrar(archivo);
    }
    
    public void activar(int item){
        switch(item){
            case 0:
                abrirConExplorer.setVisible(true);
                break;
            case 1:
                abrirConWinrar.setVisible(true);
                break;
            case 2:
                copiarContraseña.setVisible(true);
                break;
            case 3:
                copiarContraseñaYabrir.setVisible(true);
                break;
        }
    }
    
    public void mostrarMenu(ArrayList<Archivo> archivos, MouseEvent e){
        Analizador analizador = new Analizador(archivos);
        
        limpiar();
        
        if(analizador.getTodosLosArchivos() >= 1){
            activar(0);
        }
        
        if(analizador.getArchivosComprimidos() >= 1){
            activar(1);
        }
        
        if(analizador.getArchivosComprimidos() == 1 && analizador.getArchivosConContraseña() == 1 && analizador.getArchivosConContraseñaCorrecta() == 1){
            activar(2);
            activar(3);
        }
        
        if(analizador.getTodosLosArchivos() > 1){
            abrirConExplorer.setText(ABRIR_CON_EXPLORER + " " + analizador.getTodosLosArchivos()+ " elementos");
            abrirConWinrar.setText(ABRIR_CON_WINRAR+ " " + analizador.getArchivosComprimidos() +  " elementos");
        }else{
            abrirConExplorer.setText(ABRIR_CON_EXPLORER);
            abrirConWinrar.setText(ABRIR_CON_WINRAR);
        }
        
        
        getMenu().show(e.getComponent(), e.getX(), e.getY());
        

    }
    
    public JPopupMenu getMenu(){
        return popupMenu;
    }

    public void limpiar() {
        for(Component component : getMenu().getComponents()){
            if(component instanceof JMenuItem)
                component.setVisible(false);
        }
    }
    
}
