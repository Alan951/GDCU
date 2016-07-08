/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import Properties.Propiedades;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class MenuTabla {
    
    private JPopupMenu popupMenu;
    
    private JMenuItem abrirConExplorer;
    private JMenuItem abrirConWinrar;
    
    private JTable tabla;
    private TablaModeloArchivoDetalles modelo;
    
    private String winrarPath = null;
    
    public MenuTabla(JTable tabla, TablaModeloArchivoDetalles modelo){
        this.tabla = tabla;
        this.modelo = modelo;
        
        popupMenu = new JPopupMenu();
        
        abrirConExplorer = new JMenuItem("Abrir con explorer");
        abrirConWinrar = new JMenuItem("Abrir con winrar");
        
        popupMenu.add(abrirConExplorer);
        popupMenu.add(abrirConWinrar);
        
        //--listeners--
        initListeners();
        
        //--winrarPath
        Propiedades prop = Propiedades.getInstance();
        winrarPath = prop.getPropertie(Propiedades.WINRAR_PATH);
        if(!(winrarPath != null && !winrarPath.equals(""))){
            winrarPath = null;
        }
        
    }
    
    public void noVisible(int item){
        switch(item){
            case 0:
                abrirConExplorer.setVisible(false);
                break;
            case 1:
                abrirConWinrar.setVisible(false);
                break;
        }
    }
    
    public void initListeners(){
        abrirConExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //table.convertRowIndexToModel(selectedRow)
                int row = tabla.getSelectedRow();
                row = tabla.convertRowIndexToModel(row);
                if(row != -1){
                    if(Desktop.isDesktopSupported()){
                        try {
                            Desktop.getDesktop().open(modelo.getArchivo(row).getParentFile());
                        } catch (IOException ex) {
                            //TODO: problema si el archivo no tiene padre
                        }
                    }
                }
            }
        });
        
        abrirConWinrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                row = tabla.convertRowIndexToModel(row);
                if(row != -1){
                    Archivo archivo = modelo.getArchivo(row);
                    
                    if(winrarPath == null){
                        JOptionPane.showMessageDialog(null, "Debes de ir a las opciones e introducir la ruta de WinRAR de tu sistema para poder útilizar esta opción.", "Ops", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if(Utils.esArchivoComprimido(archivo)){
                        String args[] = {winrarPath, archivo.getAbsolutePath()};
                        
                        ProcessBuilder pb = new ProcessBuilder(args);
                        
                        try {
                            Process p = pb.start();
                        } catch (IOException ex) {
                            System.out.println("[DG] Hubo un error en la ejecución del archivo");
                            
                            JOptionPane.showMessageDialog(null, "Hubo un error con la ruta de winrar\n\""+winrarPath+"\"\nVerifica que la ruta sea la adecuada en las opciones de GDCU.", "Error al abrir con winrar", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        
    }
    
    public JPopupMenu getMenu(){
        return popupMenu;
    }

    public void limpiar() {
        for(Component component : getMenu().getComponents()){
            if(component instanceof JMenuItem)
                component.setVisible(true);
        }
    }
    
}
