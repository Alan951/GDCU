/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Utils;

import mx.jalan.gdcu.Modelo.Archivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Jorge
 */
public class GeneradorHashes {
    
    private ArrayList<Archivo> archivos;
    
    private JTable tabla;
    
    private AbstractTableModel modelo;
    
    private int posColumnHash;
    
    public GeneradorHashes(){}
    
    /*
        Dado que el proceso de generación de hashes se hace mientras el usuario ve la tabla
        entonces es necesario modificar en vivo la celda del hash del archivo que se genero
        es por esta razón que se requiere la tabla y el modelo de la tabla para poder
        actualizar la celda.
    */
    public GeneradorHashes(ArrayList<Archivo> archivos, JTable tabla, AbstractTableModel modelo){
        this.archivos = archivos;
        
        this.tabla = tabla;
        
        this.modelo = modelo;
        
        //initEjecutador();
    }
    
    public void GeneradorHashes(ArrayList<Archivo> archivos){
        this.archivos = archivos;
        
        //initEjecutador();
    }
    
    public void initEjecutador(){
        ExecutorService ejecutador = Executors.newFixedThreadPool(20);
        
        posColumnHash = Utils.getPosColumn(tabla, "Hash MD5");
        
        for(int x = 0 ; x < archivos.size() ; x++){
            ejecutador.execute(threadGetHash(archivos.get(x)));
            System.out.println("Thread "+x);
        }
    }
    
    public Runnable threadGetHash(Archivo a){
        Runnable work = new Runnable() {
            @Override
            public void run() {
                try{
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    FileInputStream fis = new FileInputStream(a);

                    byte[] dataB = new byte[1024];

                    int nread = 0;
                    while((nread = fis.read(dataB)) != -1){
                        md.update(dataB, 0 , nread);
                    };

                    byte[] mdbytes = md.digest();

                    StringBuffer sb = new StringBuffer();
                    for(int i = 0 ; i < mdbytes.length ; i++){
                        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    
                    System.out.println("Hash "+a.getName()+": "+sb.toString());
                    
                    fis.close();
                    
                    a.setHash(sb.toString());
                    if(tabla != null)
                        modelo.fireTableCellUpdated(archivos.indexOf(a), posColumnHash);
                    
                }catch(NoSuchAlgorithmException s){
                
                }catch(FileNotFoundException f){
                    System.out.println("No encontro archivo");
                }catch(IOException e){
                    
                }
                
                
            }
        };
        
        
        return work;
        
    }
}
