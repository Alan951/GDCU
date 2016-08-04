/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos.TablaRender;

import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge
 */
public class GestorModelo {
    
    private static TablaModeloArchivoDetalles modelo;
    private static GestorModelo instancia = null;
    
    public GestorModelo(){}
    
    public static GestorModelo getInstance(){
        if(instancia == null){
            instancia = new GestorModelo();
        }
        
        return instancia;
    }
    
    public void setModelo(TablaModeloArchivoDetalles modelo){
        this.modelo = modelo;
    }
    
    public TablaModeloArchivoDetalles getModelo(){
        if(modelo != null)
            return modelo;
        else
            return null;
    }
    
}
