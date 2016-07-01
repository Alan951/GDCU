/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
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
                    
                    if(Utils.esArchivoComprimido(archivo)){
                        String args[] = {"C:\\Program Files\\WinRAR\\WinRAR.exe", archivo.getAbsolutePath()};
                        
                        ProcessBuilder pb = new ProcessBuilder(args);
                        
                        try {
                            Process p = pb.start();
                        } catch (IOException ex) {
                            System.out.println("[DG] Hubo un error en la ejecución del archivo");
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
