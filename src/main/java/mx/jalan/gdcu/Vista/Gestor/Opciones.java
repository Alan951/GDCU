/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Gestor;

import Properties.Propiedades;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import mx.jalan.gdcu.Utils.Utils;

/**
 *
 * @author Jorge
 */
public class Opciones extends javax.swing.JDialog {
    
    /**
     * Creates new form Opciones
     */
    public Opciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        initConfig();
    }
    
    public void initConfig(){
        Propiedades prop = Propiedades.getInstance();
        
        txtRarPath.setText(prop.getPropertie(Propiedades.WINRAR_PATH));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtRarPath = new javax.swing.JTextField();
        btnSelectRar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones");
        setResizable(false);

        jLabel1.setText("Ruta de winrar");

        txtRarPath.setEnabled(false);

        btnSelectRar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/selecFile.png"))); // NOI18N
        btnSelectRar.setBorderPainted(false);
        btnSelectRar.setContentAreaFilled(false);
        btnSelectRar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectRarActionPerformed(evt);
            }
        });

        btnAceptar.setText("Guardar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Opciones");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRarPath, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelectRar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSelectRar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtRarPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectRarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectRarActionPerformed
        String filesAndPrograms = null;
        
        try {
            filesAndPrograms = System.getenv("ProgramFiles");
        } catch (Exception e) {
            filesAndPrograms = System.getenv("ProgramFiles(X86)");
        }
        
        JFileChooser fileChooser = new JFileChooser(filesAndPrograms);
        
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos exe", "exe"));
        int res = fileChooser.showOpenDialog(this);
        
        if(res == JFileChooser.APPROVE_OPTION){
            txtRarPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnSelectRarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String mensaje = "";
        
        String winrarPath = null;
        
        if(!txtRarPath.getText().trim().isEmpty()){
            if(!Utils.isPathValid(txtRarPath.getText())){
                mensaje += "La ruta de winrar no es valido\n";
            }else{
                winrarPath = txtRarPath.getText();
            }
        }
        
        if(!mensaje.equals("")){
            JOptionPane.showMessageDialog(this, mensaje, "Verifica los campos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Propiedades prop = Propiedades.getInstance();
        
        if(winrarPath != null){
            prop.setPropertie(prop.WINRAR_PATH, winrarPath);
        }
        
        prop.guardarPropiedades();
        
        this.dispose();
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelectRar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtRarPath;
    // End of variables declaration//GEN-END:variables
}
