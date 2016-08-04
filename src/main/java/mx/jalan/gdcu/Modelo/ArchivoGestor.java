/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Modelo;

/**
 *
 * @author Jorge
 */
public class ArchivoGestor extends Archivo{
    
    public ArchivoGestor(String pathname){
        super(pathname);
    }
    
    public ArchivoGestor(String pathname, String password) {
        super(pathname, password);
    }
    
}
