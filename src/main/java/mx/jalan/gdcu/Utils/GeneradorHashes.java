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
import mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge
 */
public class GeneradorHashes {
    
    private ArrayList<Archivo> archivos;
    
    private ArrayList<Archivo> archivosHasheando = new ArrayList<Archivo>();
    
    private TablaArchivoDetalles tabla;
    
    private int posColumnHash;
    
    private ExecutorService ejecutador = Executors.newFixedThreadPool(3);
    
    private static GeneradorHashes instance;
    
    public static GeneradorHashes getInstance(){
        if(instance == null){
            instance = new GeneradorHashes();
        }
        
        return instance;
    }
    
    public GeneradorHashes(){}
    
    public void setTabla(TablaArchivoDetalles tabla){
        this.tabla = tabla;
        
        //ejecutador.shutdownNow();
    }
    
    public TablaArchivoDetalles getTabla(){
        return tabla;
    }
    
    public void generarHashLista(ArrayList<Archivo> archivos){
        this.archivos = archivos;
    }
    
    /*
        Dado que el proceso de generación de hashes se hace mientras el usuario ve la tabla
        entonces es necesario modificar en vivo la celda del hash del archivo que se genero
        es por esta razón que se requiere la tabla y el modelo de la tabla para poder
        actualizar la celda.
    */
    public GeneradorHashes(ArrayList<Archivo> archivos, TablaArchivoDetalles tabla){
        this.archivos = archivos;
        
        this.tabla = tabla;
        
        //initEjecutador();
    }
    
    public void GeneradorHashes(ArrayList<Archivo> archivos){
        this.archivos = archivos;
        
        //initEjecutador();
    }
    
    public void initEjecutador(){
        //ExecutorService ejecutador = Executors.newFixedThreadPool(20);
        
        posColumnHash = Utils.getPosColumn(tabla, "Hash MD5");
        
        //Elimina los archivos que ya sea estan hasheando
        ArrayList<Archivo> paraEliminar = new ArrayList<Archivo>();
        for(Archivo archivo : archivos){
            if(archivosHasheando.contains(archivo) || archivo.getHash() != null){
                System.out.println("El archivo: "+archivo.getName()+ " ya se esta hasheando o ya fue hasheado");
                paraEliminar.add(archivo);
            }
        }
        
        archivos.removeAll(paraEliminar);
        
        for(int x = 0 ; x < archivos.size() ; x++){
            ejecutador.execute(threadGetHash(archivos.get(x)));
            archivosHasheando.add(archivos.get(x));
            System.out.println("Thread "+x);
        }
    }
    
    public void generarHash(Archivo archivo){
        //ExecutorService ejecutador = Executors.newFixedThreadPool(20);
        
        posColumnHash = Utils.getPosColumn(tabla, "Hash MD5");
        
        if(archivosHasheando.contains(archivo)){
            System.out.println("El archivo: "+archivo.getName()+ " ya se esta hasheando");
            return;
        }else{
            archivosHasheando.add(archivo);
        }
        
        ejecutador.execute(threadGetHash(archivo));
    }
    
    public Runnable threadGetHash(Archivo a){
        Runnable work = new Runnable() {
            @Override
            public void run() {
                try{
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    FileInputStream fis = new FileInputStream(a);

                    byte[] dataB = new byte[1024*1024];

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
                    
                    archivosHasheando.remove(a);
                    
                    a.setHash(sb.toString());
                    if(tabla != null)
                        tabla.getModelo().fireTableCellUpdated(tabla.getModelo().getTodosLosArchivos().indexOf(a), posColumnHash);
                    
                }catch(NoSuchAlgorithmException s){
                }catch(FileNotFoundException f){
                    System.out.println("No encontro archivo");
                }catch(IOException e){}
            }
        };
 
        return work;
        
    }
    
    public void apagarThreads(){
        if(archivos != null)
            archivos.clear();
        archivosHasheando.clear();
        ejecutador.shutdownNow();
        newEjecutador();
        
    }
    
    public void newEjecutador(){
        ejecutador = null;
        ejecutador = Executors.newFixedThreadPool(3);
    }
}
