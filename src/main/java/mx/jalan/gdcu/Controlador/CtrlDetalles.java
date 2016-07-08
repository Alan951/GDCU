/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Vista.Buscador.Detalles;
import java.util.ArrayList;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Vista.Buscador.FiltroBuscadorView;
import mx.jalan.gdcu.Vista.VistaModelos.MenuTabla;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge
 */
public class CtrlDetalles {

    private Detalles bd;

    private ArrayList<Archivo> archivos;

    private FiltroBusqueda filtroOriginal;

    private FiltroBusqueda filtro;

    private TablaModeloArchivoDetalles tMAD;

    public CtrlDetalles(ArrayList<Archivo> archivos, FiltroBusqueda filtro) {
        this.archivos = archivos;

        this.filtroOriginal = filtro;
        this.filtro = filtro;
    }

    public void abrirDetalles() {
        bd = new Detalles(this);
        bd.setLocationRelativeTo(null);
        bd.setVisible(true);

        //--initTable
        initTable();
        
        //--initListenerBTN
        initBtnListener();

        //--initMenu
        bd.getTabla().setMenu(new MenuTabla(bd.getTabla()));
        //bd.getTabla().add(popup.getMenu());
    }

    public void initTable() {
        tMAD = new TablaModeloArchivoDetalles(archivos);
        bd.getTabla().setModelo(tMAD);
        
        bd.getTabla().activarRenderizados();

        bd.getTabla().setAutoCreateRowSorter(true);
    }

    public void initBtnListener() {
        bd.getBtnFiltro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroBuscadorView fv = new FiltroBuscadorView(bd, true, "filtro", filtroOriginal, filtro);

                if (fv.getPressOk()) {
                    filtro = fv.getFiltroBusqueda();
                    tMAD.aplicarFiltro(filtro);
                    bd.getLblElementos().setText(tMAD.getElementosViendose() + "");
                }
            }
        });
    }
}
