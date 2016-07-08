package mx.jalan.gdcu.Vista.Gestor;

import mx.jalan.gdcu.Controlador.CtrlGestor;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Gestor extends javax.swing.JFrame {
    
    private CtrlGestor controlador;
    
    public Gestor(CtrlGestor controlador) {
        this.controlador = controlador;
        
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnFiltrar() {
        return btnFiltrar;
    }
    
    public JButton getBtnGuardar(){
        return btnGuardar;
    }
    
    public JButton getBtnExportar(){
        return btnExportar;
    }

    public TablaArchivoDetalles getTablaArchivos() {
        return tablaArchivos;
    }
    
    public JMenuItem getMenuOpciones(){
        return menuOpciones;
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenus = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        btnFiltrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArchivos = new mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuOpciones = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestor de contraseñas");

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/add.png"))); // NOI18N
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/del.png"))); // NOI18N
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/explortar.png"))); // NOI18N
        btnExportar.setBorderPainted(false);
        btnExportar.setContentAreaFilled(false);

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/filter.png"))); // NOI18N
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setContentAreaFilled(false);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/guardar.png"))); // NOI18N
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);

        javax.swing.GroupLayout panelMenusLayout = new javax.swing.GroupLayout(panelMenus);
        panelMenus.setLayout(panelMenusLayout);
        panelMenusLayout.setHorizontalGroup(
            panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenusLayout.createSequentialGroup()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        panelMenusLayout.setVerticalGroup(
            panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenusLayout.createSequentialGroup()
                .addGroup(panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar)
                    .addComponent(btnExportar)
                    .addComponent(btnFiltrar)
                    .addComponent(btnGuardar))
                .addGap(2, 2, 2))
        );

        tablaArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaArchivos);

        jMenu1.setText("Archivo");

        menuOpciones.setText("Opciones");
        jMenu1.add(menuOpciones);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(panelMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuOpciones;
    private javax.swing.JPanel panelMenus;
    private mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles tablaArchivos;
    // End of variables declaration//GEN-END:variables
}
