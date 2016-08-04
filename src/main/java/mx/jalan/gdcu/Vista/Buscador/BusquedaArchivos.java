/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Buscador;

import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloBusqueda;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Jorge
 */
public class BusquedaArchivos extends javax.swing.JFrame {

    private JXLabel xlblInfo;
    
    private boolean mostrarInformacion = false;
    
    private TablaModeloBusqueda modeloTabla;
    
    final String info = "Para busqueda de archivos comprimidos como zip, rar y otros debes de indicar cuales son las "
            + "rutas del sistema en donde se examinara en busqueda de los archivos.\nPuedes utilizar las distintas opciones "
            + "que existen en la parte inferior de esta ventana para un resultado más completo.";
    
    public BusquedaArchivos() {
        initComponents();
        initComp();
    }
    
    public void initComp(){
        getPanelInstrucciones().setVisible(false);
        xlblInfo = new JXLabel(info);
        xlblInfo.setLineWrap(true);
        
        getPanelInstrucciones().setLayout(new GridLayout(1, 1));
        
        getPanelInstrucciones().add(xlblInfo);
        
        modeloTabla = new TablaModeloBusqueda();
        
        tablaRutas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tablaRutas.setModel(modeloTabla);
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbSubCarpetas = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaRutas = new javax.swing.JTable();
        panelInstrucciones = new javax.swing.JPanel();
        btnDel = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnScan = new javax.swing.JButton();
        btnMasOpciones = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busquedade archivos");

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Busqueda de archivos comprimidos zip, rar y otros.");

        cbSubCarpetas.setText("Inlcuir subcarpetas");

        tablaRutas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Rutas de busqueda"
            }
        ));
        jScrollPane2.setViewportView(tablaRutas);

        panelInstrucciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelInstruccionesLayout = new javax.swing.GroupLayout(panelInstrucciones);
        panelInstrucciones.setLayout(panelInstruccionesLayout);
        panelInstruccionesLayout.setHorizontalGroup(
            panelInstruccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInstruccionesLayout.setVerticalGroup(
            panelInstruccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/del.png"))); // NOI18N
        btnDel.setContentAreaFilled(false);

        btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/question.png"))); // NOI18N
        btnInfo.setContentAreaFilled(false);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/add.png"))); // NOI18N
        btnAdd.setContentAreaFilled(false);

        btnScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/scan.png"))); // NOI18N
        btnScan.setContentAreaFilled(false);

        btnMasOpciones.setText("Mas opciones");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInstrucciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbSubCarpetas)
                            .addComponent(btnMasOpciones))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnScan, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(btnInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInstrucciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnDel)
                        .addComponent(btnAdd)
                        .addComponent(btnScan))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbSubCarpetas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMasOpciones)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnMasOpciones;
    private javax.swing.JButton btnScan;
    private javax.swing.JCheckBox cbSubCarpetas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelInstrucciones;
    private javax.swing.JTable tablaRutas;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnMostrarOpciones(){
        return btnMasOpciones;
    }
    
    /**
     * @return the descripcion
     */
    public JXLabel getLblInfo() {
        return xlblInfo;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(JXLabel xlblInfo) {
        this.xlblInfo = xlblInfo;
    }

    /**
     * @return the mostrarInformacion
     */
    public boolean getMostrarInformacion() {
        return mostrarInformacion;
    }

    /**
     * @param mostrarInformacion the mostrarInformacion to set
     */
    public void setMostrarInformacion(boolean mostrarInformacion) {
        this.mostrarInformacion = mostrarInformacion;
    }

    /**
     * @return the btnAdd
     */
    public javax.swing.JButton getBtnAdd() {
        return btnAdd;
    }

    /**
     * @param btnAdd the btnAdd to set
     */
    public void setBtnAdd(javax.swing.JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    /**
     * @return the btnDel
     */
    public javax.swing.JButton getBtnDel() {
        return btnDel;
    }

    /**
     * @param btnDel the btnDel to set
     */
    public void setBtnDel(javax.swing.JButton btnDel) {
        this.btnDel = btnDel;
    }

    /**
     * @return the btnInfo
     */
    public javax.swing.JButton getBtnInfo() {
        return btnInfo;
    }

    /**
     * @param btnInfo the btnInfo to set
     */
    public void setBtnInfo(javax.swing.JButton btnInfo) {
        this.btnInfo = btnInfo;
    }

    /**
     * @return the btnScan
     */
    public javax.swing.JButton getBtnScan() {
        return btnScan;
    }

    /**
     * @param btnScan the btnScan to set
     */
    public void setBtnScan(javax.swing.JButton btnScan) {
        this.btnScan = btnScan;
    }

    /**
     * @return the cbSubCarpetas
     */
    public javax.swing.JCheckBox getCbSubCarpetas() {
        return cbSubCarpetas;
    }

    /**
     * @param cbSubCarpetas the cbSubCarpetas to set
     */
    public void setCbSubCarpetas(javax.swing.JCheckBox cbSubCarpetas) {
        this.cbSubCarpetas = cbSubCarpetas;
    }

    /**
     * @return the panelInstrucciones
     */
    public javax.swing.JPanel getPanelInstrucciones() {
        return panelInstrucciones;
    }

    /**
     * @param panelInstrucciones the panelInstrucciones to set
     */
    public void setPanelInstrucciones(javax.swing.JPanel panelInstrucciones) {
        this.panelInstrucciones = panelInstrucciones;
    }

    /**
     * @return the tablaRutas
     */
    public javax.swing.JTable getTablaRutas() {
        return tablaRutas;
    }

    /**
     * @param tablaRutas the tablaRutas to set
     */
    public void setTablaRutas(javax.swing.JTable tablaRutas) {
        this.tablaRutas = tablaRutas;
    }
    
    public TablaModeloBusqueda getModeloTabla(){
        return modeloTabla;
    }
    
    
}