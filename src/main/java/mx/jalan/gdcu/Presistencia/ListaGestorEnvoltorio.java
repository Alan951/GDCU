/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Presistencia;

import java.io.Serializable;
import mx.jalan.gdcu.Modelo.Archivo;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class ListaGestorEnvoltorio implements Serializable{
    private ArrayList<Archivo> archivos;
    
    public void setArchivos(ArrayList<Archivo> archivos){
        this.archivos = archivos;
    }
    
    public ArrayList<Archivo> getArchivos(){
        return archivos;
    }
}
