/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Modelo;

import java.util.ArrayList;
import java.util.Date;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge
 */
public class FiltroBusqueda {
    
    private ArrayList<String> extensiones;
    
    private boolean carpetaOculta;
    private boolean archivoOculto;
    
    private Date fechaInf;
    private Date fechaSup;
    
    private double tamañoInf;
    private double tamañoSup;
    
    private String unidadInf;
    private String unidadSup;
    
    public FiltroBusqueda(double tamañoInf, double tamañoSup, Date fechaInf, Date fechaSup, boolean carpetaOculta, boolean archivoOcultoculto, ArrayList<String> extensiones){
        setTamañoInf(tamañoInf);
        setTamañoSup(tamañoSup);
        setFechaInf(fechaInf);
        setFechaSup(fechaSup);
        setCarpetaOculta(carpetaOculta);
        setArchivoOculto(archivoOcultoculto);
        
        ArrayList<String> exts = null;
        exts = new ArrayList<String>();
        //Cuando extensiones incluye el comodin "*" para considerar todas las extensiones, lo que hace es limpiar el array en caso de que haya dejado otras extensiones
        if(Utils.esteArrayTiene(extensiones, "*")){
        //if(extensiones.contains("<*")){
            if(extensiones.size() > 1){
                extensiones.clear();
                
            }
            exts.add("*"); //Y solo deja el comodin
        }else{
            /*
                Todas las extensiones lo cambia a minusculas para mi comodidad aunque 
                no deberia de haber ningun problema si estan en mayusculas o una combinacion de ambos.
            */
            
            for(String ex : extensiones){
                exts.add(ex.toLowerCase());
            }
        }
        
        setExtensiones(exts);
    }
    
    public ArrayList<String> getExtensiones() {
        return extensiones;
    }

    public void setExtensiones(ArrayList<String> extensiones) {
        this.extensiones = extensiones;
    }

    public boolean carpetaPuedeEstarOculta(){
        return carpetaOculta;
    }
    
    public void setCarpetaOculta(boolean carpetaOculta){
        this.carpetaOculta = carpetaOculta;
    }
    
    public boolean archivoPuedeSerOcuto(){
        return archivoOculto;
    }
    
    public void setArchivoOculto(boolean archivoOculto){
        this.archivoOculto = archivoOculto;
    }
    
    public void setTamañoInf(double tamañoInf){
        this.tamañoInf = tamañoInf;
    }
    
    public double getTamañoInf(){
        return tamañoInf;
    }
    
    public void setTamañoSup(double tamañoSup){
        this.tamañoSup = tamañoSup;
    }
    
    public double getTamañoSup(){
        return tamañoSup;
    }
    
    public void setUnidadInf(String unidadInf){
        this.unidadInf = unidadInf;
    }
    
    public String getUnidadInf(){
        return unidadInf;
    }
    
    public void setUnidadSup(String unidadSup){
        this.unidadSup = unidadSup;
    }
    
    public String getUnidadSup(){
        return unidadSup;
    }
    
    public void setFechaInf(Date fechaInf){
        this.fechaInf = fechaInf;
    }
    
    public Date getFechaInf(){
        return fechaInf;
    }
    
    public void setFechaSup(Date fechaSup){
        this.fechaSup = fechaSup;
    }
    
    public Date getFechaSup(){
        return fechaSup;
    }

    @Override
    public String toString() {
        return "FiltroBusqueda{" + "extensiones=" + extensiones + ", carpetaOculta=" + carpetaOculta + ", archivoOculto=" + archivoOculto + ", fechaInf=" + fechaInf + ", fechaSup=" + fechaSup + ", tama\u00f1oInf=" + tamañoInf + ", tama\u00f1oSup=" + tamañoSup + ", unidadInf=" + unidadInf + ", unidadSup=" + unidadSup + '}';
    }
}
