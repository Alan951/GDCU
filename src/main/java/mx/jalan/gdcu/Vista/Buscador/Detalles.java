package mx.jalan.gdcu.Vista.Buscador;

import javax.swing.JButton;
import javax.swing.JTable;
import mx.jalan.gdcu.Controlador.CtrlDetalles;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Utils.GeneradorHashes;
import mx.jalan.gdcu.Vista.VistaModelos.TablaModeloArchivoDetalles;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Detalles extends javax.swing.JFrame {

    private CtrlDetalles controlador;
    
    private TablaModeloArchivoDetalles tMAD;
    
    private GeneradorHashes gH;
    
    /**
     * Creates new form BusquedaDetalles
     */
    public Detalles(CtrlDetalles controlador) {
        initComponents();
        this.controlador = controlador;
        initTable();
        
        gH = new GeneradorHashes(controlador.getArchivos(), tablaArchivos, tMAD);
        //gH.initEjecutador();
    }
    
    private void initTable(){
        tMAD = new TablaModeloArchivoDetalles(controlador.getArchivos());
        tablaArchivos.setModel(tMAD);
        
        tablaArchivos.setAutoCreateRowSorter(true);
    }
    
    public JButton getBtnFiltro(){
        return btnFiltrar;
    }
    
    public TablaModeloArchivoDetalles getModelo(){
        return tMAD;
    }
    
    public JTable getTabla(){
        return tablaArchivos;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        btnFiltrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalles");

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
        jScrollPane1.setViewportView(tablaArchivos);

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/filter.png"))); // NOI18N
        btnFiltrar.setText("Filtrar");
        btnFiltrar.setContentAreaFilled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFiltrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnFiltrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaArchivos;
    // End of variables declaration//GEN-END:variables
}
