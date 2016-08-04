package mx.jalan.gdcu.Vista.Buscador;

import javax.swing.JButton;
import javax.swing.JLabel;
import mx.jalan.gdcu.Controlador.CtrlDetalles;
import mx.jalan.gdcu.Utils.GeneradorHashes;
import mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Detalles extends javax.swing.JFrame {

    private CtrlDetalles controlador;
    
    /**
     * Creates new form BusquedaDetalles
     */
    public Detalles(CtrlDetalles controlador) {
        initComponents();
        this.controlador = controlador;
        
    }
    
    public JButton getBtnFiltro(){
        return btnFiltrar;
    }
    
    public TablaArchivoDetalles getTabla(){
        return tablaArchivos;
    }
    
    public JLabel getLblElementos(){
        return lblElementos;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFiltrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblElementos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArchivos = new mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalles");

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/filter.png"))); // NOI18N
        btnFiltrar.setText("Filtrar");
        btnFiltrar.setContentAreaFilled(false);

        jLabel1.setText("Elementos en la tabla: ");

        lblElementos.setText(" ");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFiltrar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblElementos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnFiltrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblElementos))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblElementos;
    private mx.jalan.gdcu.Vista.VistaModelos.TablaArchivoDetalles tablaArchivos;
    // End of variables declaration//GEN-END:variables
}
