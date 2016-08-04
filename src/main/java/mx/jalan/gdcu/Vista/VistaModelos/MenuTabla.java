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
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Analizador;
import mx.jalan.gdcu.Utils.GeneradorHashes;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class MenuTabla {
    
    private ArrayList<Archivo> archivos;
    
    private JPopupMenu popupMenu;
    
    private JMenuItem abrirConExplorer;
    private JMenuItem abrirConWinrar;
    private JMenuItem copiarContraseña;
    private JMenuItem copiarContraseñaYabrir;
    private JMenuItem generarHash;
    private JMenuItem copiarHash;
    
    private final String ABRIR_CON_WINRAR = "Abrir con winrar";
    private final String ABRIR_CON_EXPLORER = "Abrir con explorer";
    private final String COPIAR_CONTRASEÑA = "Copiar contraseña";
    private final String COPIAR_CONTRASEÑA_Y_ABRIR = "Copiar contraseña y abrir con winrar";
    private final String GENERAR_HASH = "Generar hash";
    private final String COPIAR_HASH = "Copiar hash";
    
    private TablaArchivoDetalles tabla;
    private TablaModeloArchivoDetalles modelo;
    
    Propiedades prop;
    
    public MenuTabla(TablaArchivoDetalles tabla){
        this.tabla = tabla;
        this.modelo = tabla.getModelo();
        
        popupMenu = new JPopupMenu();
        
        abrirConExplorer = new JMenuItem(ABRIR_CON_EXPLORER);
        abrirConWinrar = new JMenuItem(ABRIR_CON_WINRAR);
        copiarContraseña = new JMenuItem(COPIAR_CONTRASEÑA);
        copiarContraseñaYabrir = new JMenuItem(COPIAR_CONTRASEÑA_Y_ABRIR);
        generarHash = new JMenuItem(GENERAR_HASH);
        copiarHash = new JMenuItem(COPIAR_HASH);
        
        popupMenu.add(abrirConExplorer);
        popupMenu.add(abrirConWinrar);
        popupMenu.add(copiarContraseña);
        popupMenu.add(copiarContraseñaYabrir);
        popupMenu.add(generarHash);
        popupMenu.add(copiarHash);

        //--listeners--
        initListeners();
        
        //--winrarPath
        prop = Propiedades.getInstance(); 
    }
    
    public void initListeners(){
        
        abrirConExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = tabla.getSelectedRows();
                
                //if(rows.length >= 50){
                if(archivos.size() >= 50){
                    String opciones[] = {"La vida es un riesgo carnal", "Cancelar"};
                    
                    int r = JOptionPane.showOptionDialog(null, "Abrir más de 50 archivos con el explorador de windows puede alentar el sistema operativo.\nDeseas continuar?", "¿Seguro?", 0, JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
                
                    System.out.println("Respuesta: "+r);
                    
                    if(r == 1){
                        return;
                    }
                }
                
                //for(int row : rows){
                for(Archivo archivo : archivos){
                    //row = tabla.convertRowIndexToModel(row);
                    //abrirArchivoConExplorer(tabla.getModelo().getArchivo(row));
                    abrirArchivoConExplorer(archivo);
                }
            }
        });
        
        abrirConWinrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = tabla.getSelectedRows();
                
                //for(int row : rows){
                for(Archivo archivo : archivos){
                    abrirArchivoConWinrar(archivo);
                    //row = tabla.convertRowIndexToModel(row);
                    //abrirArchivoConWinrar(tabla.getModelo().getArchivo(row));
                }
            }
        });
        
        copiarContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int row = tabla.getSelectedRow();
                
                //row = tabla.convertRowIndexToModel(row);
                copiarContraseña(archivos.get(0));
                //copiarContraseña(tabla.getModelo().getArchivo(row));
            }
        });
        
        copiarContraseñaYabrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int row = tabla.getSelectedRow();
                
                //row = tabla.convertRowIndexToModel(row);    
                copiarContraseñaYabrir(archivos.get(0));
                //copiarContraseñaYabrir(tabla.getModelo().getArchivo(row));
            }
        });
        
        generarHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int []rows = tabla.getSelectedRows();
                /*
                for(int row : rows){
                    generarHash(tabla.getModelo().getArchivo(row));
                } */
                
                //if(rows.length == 1){
                if(archivos.size() == 1){
                    //int row = tabla.convertRowIndexToModel(rows[0]);
                    
                    generarHash(archivos.get(0));
                    //generarHash(tabla.getModelo().getArchivo(row));
                }else{
                    /*
                    ArrayList<Archivo> archivos = new ArrayList<Archivo>();
                    
                    for(int row : rows){
                        row = tabla.convertRowIndexToModel(row);
                        
                        archivos.add(tabla.getModelo().getArchivo(row));
                    }*/
                    
                    generarHash(archivos);
                }
            }
        });
        
        copiarHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                
                row = tabla.convertRowIndexToModel(row);
                
                copiarHash(tabla.getModelo().getArchivo(row));
            }
        });
    }
    
    public void generarHash(Archivo archivo){
        GeneradorHashes generadorHashes = GeneradorHashes.getInstance();
        if(generadorHashes.getTabla() == null){
            generadorHashes.setTabla(tabla);
        }
        
        generadorHashes.generarHash(archivo);
    }
    
    public void generarHash(ArrayList<Archivo> archivos){
        GeneradorHashes generadorHashes = GeneradorHashes.getInstance();
        if(generadorHashes.getTabla() == null){
            generadorHashes.setTabla(tabla);
        }
        
        generadorHashes.generarHashLista(archivos);
        generadorHashes.initEjecutador();
    }
    
    public boolean abrirArchivoConWinrar(Archivo archivo){    
        String winrarPath = prop.getPropertie(Propiedades.WINRAR_PATH);
        
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
    
    public void copiarHash(Archivo archivo){
        String hash = archivo.getHash();
        
        StringSelection strSelection = new StringSelection(hash);
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
            case 4:
                generarHash.setVisible(true);
                break;
            case 5:
                copiarHash.setVisible(true);
        }
    }
    
    public void mostrarMenu(ArrayList<Archivo> archivos, MouseEvent e){
        if(archivos.size() == 1){
            if(!archivos.get(0).exists()){
                JOptionPane.showMessageDialog(null, "El archivo seleccionado no pudo ser encontrado", "Archivo no localizado", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }else{
            int archivosInexistentes = 0;
            for(Archivo archivo : archivos){
                if(!archivo.exists()){
                    archivosInexistentes++;
                }
            }
            
            if(archivosInexistentes == archivos.size()){
                JOptionPane.showMessageDialog(null, "Los archivos seleccionados no se encontraron", "Archivos no localizados", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                archivos.removeIf(file -> !file.exists());
            }
        }
        
        this.archivos = archivos;
        
        Analizador analizador = new Analizador(archivos, true);
        
        archivos = analizador.getLista();
        
        //TODO: Hacer que los metodos del menu tomen los archivos desde el arraylist y no desde la tabla.
        
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
        
        if(analizador.cuantosArchivosHasheadosHay() < analizador.getTodosLosArchivos()){
            activar(4);
        }
        
        if(analizador.getTodosLosArchivos() == 1 && analizador.getArchivosHasheados() == 1){
            activar(5);
        }
        
        if(analizador.getTodosLosArchivos() > 1){
            abrirConExplorer.setText(ABRIR_CON_EXPLORER + " " +analizador.getTodosLosArchivos()+ " elementos");
            abrirConWinrar.setText(ABRIR_CON_WINRAR+ " " +analizador.getArchivosComprimidos()+ " elementos");
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
