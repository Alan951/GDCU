/*
    Fecha de creación: 1/Junio/2016
    Ultima modificación: 1/Junio/2016
*/

package mx.jalan.gdcu.Utils;

import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import java.io.File;
import java.util.ArrayList;
import mx.jalan.gdcu.Vista.Buscador.BusquedaArchivosDialog;
/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Buscador {
    
    ArrayList<File> carpetas;
    ArrayList<String> extensiones;
    ArrayList<File> archivosEncontrados;
    
    FiltroBusqueda filtro;
    
    private boolean revisarSubCarpetas;
    private boolean estaBuscando;
    private boolean abortado;
    
    private int carpetasAnalizadas;
    private int archivosAnalizados;
    
    private int archivosZipEncontrados;
    private int archivosRarEncontrados;
    private int otroArchivosEncontrados;
    
    private BusquedaArchivosDialog view;
    
    private Filtrador filtrador;
    
    Thread search;
    
    public Buscador(FiltroBusqueda filtro, ArrayList<File> carpetas, boolean revisarSubCarpetas){
        this.carpetas = carpetas;
        this.filtro = filtro;
        this.extensiones = filtro.getExtensiones();
        this.revisarSubCarpetas = revisarSubCarpetas;
        
        filtrador = new Filtrador(filtro);
        
        archivosEncontrados = new ArrayList<File>();
    }
        
    public void buscar(){  
        search = new Thread(new Runnable(){
            @Override
            public void run() {
                archivosZipEncontrados = 0;
                archivosRarEncontrados = 0;
                carpetasAnalizadas = 0;
                archivosAnalizados = 0;

                estaBuscando = true;
                for(File carpeta : carpetas){
                    carpetasAnalizadas++;
                    try{
                        creeper(carpeta);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                estaBuscando = false;
                //Termino de buscar
                if(view != null)
                    view.getCheckStatus().setStatus("FINISH");
            }
        });
        
        search.start();
    }
    
    public void creeper(File carpetaPadre) throws InterruptedException{
        while(abortado){
            Thread.sleep(3000);
        }
        
        File[] archivos = carpetaPadre.listFiles();

        if(archivos == null){
            return;
        }

        for(File archivo : archivos){
            if(archivo.isDirectory() && revisarSubCarpetas){
                if(!filtro.carpetaPuedeEstarOculta()){ //Verifica el filtro si puede ser oculto
                    if(archivo.isHidden()){
                        continue;
                    }
                }

                carpetasAnalizadas++;

                //Actualizar view
                if(view != null){
                    view.getLblCarpetasCount().setText(carpetasAnalizadas+"");
                    view.getConsoleStatus().append(archivo.getAbsolutePath()+"\n");

                }

                creeper(archivo); //recursividad for the win!!!!
            }else{ //Si es un archivo
                agregarArchivo(archivo);
            }
        }
            
    }
    
    /*
        Analiza el archivo y en caso de que el formato sea compatible
        y también lo pasa por una serie de filtros y al final
        lo agrega al arraylist de los archivos buscados.
        Filtrador se encarga de filtrar los archivos,
        si regresa true quiere decir que paso con exito los filtros
        si regresa false quiere decir que no cumplio con exito los filtros.
    */
    public void agregarArchivo(File archivo){
        archivosAnalizados++;
        
        if(filtrador.filtrarArchivo(archivo)){
            actualizarLabels(archivo);
            archivosEncontrados.add(archivo);
        }
    }
    
    public void actualizarLabels(File archivo){
        String extension = Utils.getExtension(archivo);
        
        boolean considerarTodas = false;
        
        if(Utils.esteArrayTieneEsteStr(extensiones, "*"))  considerarTodas = true;
        
        for(String ext : extensiones){
            if(ext.equalsIgnoreCase(extension) || considerarTodas){ //Si la extension del archivo es de las requeridas.
                if((ext.equalsIgnoreCase("zip") || considerarTodas) && extension.equalsIgnoreCase("zip")){
                    archivosZipEncontrados++;
                    if(view != null){
                        view.getLblZipCount().setText(archivosZipEncontrados+"");
                    }
                }else if((ext.equalsIgnoreCase("rar") || considerarTodas) && extension.equalsIgnoreCase("rar")){
                    archivosRarEncontrados++;
                    if(view != null){
                        view.getLblRarCount().setText(archivosRarEncontrados+"");
                    }
                }else{
                    otroArchivosEncontrados++;
                    if(view != null){
                        view.getLblOtrosArchivos().setText("Otros: "+otroArchivosEncontrados+"");
                    }
                }
                
                if(view != null)
                    view.getLblArchivosCount().setText(archivosEncontrados.size()+"");
                
                break;
            }
        }
    }
    
    public Thread getSearchThread(){
        return search;
    }
    
    public ArrayList<File> getArchivosEncontrados(){
        return archivosEncontrados;
    }
    
    public int getCarpetasAnalizadas() {
        return carpetasAnalizadas;
    }

    public void setCarpetasAnalizadas(int carpetasAnalizadas) {
        this.carpetasAnalizadas = carpetasAnalizadas;
    }

    public int getArchivosAnalizados() {
        return archivosAnalizados;
    }

    public void setArchivosAnalizados(int archivosAnalizados) {
        this.archivosAnalizados = archivosAnalizados;
    }

    public int getArchivosZipEncontrados() {
        return archivosZipEncontrados;
    }

    public void setArchivosZipEncontrados(int archivosZipEncontrados) {
        this.archivosZipEncontrados = archivosZipEncontrados;
    }

    public int getArchivosRarEncontrados() {
        return archivosRarEncontrados;
    }

    public void setArchivosRarEncontrados(int archivosRarEncontrados) {
        this.archivosRarEncontrados = archivosRarEncontrados;
    }
    
    public void setEstaBuscando(boolean estaBuscando){
        this.estaBuscando = estaBuscando;
    }
    
    public void setView(BusquedaArchivosDialog dialog){
        this.view = dialog;
    }    

    public void abortar() {
        abortado = true;
    }
    
    
    public void renaudar(){
        abortado = false;
    }
    
    public boolean getAbortStatus(){
        return abortado;
    }
    
    public FiltroBusqueda getFiltro(){
        return filtro;
    }
    
}
