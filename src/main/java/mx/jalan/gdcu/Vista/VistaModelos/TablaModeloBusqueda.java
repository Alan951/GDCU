/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.io.File;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jorge
 */
public class TablaModeloBusqueda extends AbstractTableModel{

    private ArrayList<File> carpetas;
    
    public TablaModeloBusqueda(){
        carpetas = new ArrayList<File>();
    }
    
    public ArrayList<File> getCarpetas(){
        return carpetas;
    }
    
    public void eliminarCarpeta(int row){
        carpetas.remove(row);
        update();
    }
    
    public void agregarCarpeta(File file){
        carpetas.add(file);
        update();
    }
    
    public void update(){
        this.fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Ruta";
            default: return "NA";
        }
    }
    
    @Override
    public int getRowCount() {
        return carpetas.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        File carpeta = carpetas.get(rowIndex);
        switch(columnIndex){
            case 0: return carpeta.getAbsolutePath();
            default: return "NA";
        }
    }
    
}
