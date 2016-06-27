/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Gestor;

import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.FileManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Jorge
 */
public class AgregarArchivo extends javax.swing.JDialog {

    private Archivo archivo;
    
    private boolean okPressed;
    private boolean archivoEncriptado;
    
    private FileManager fM;
    
    public AgregarArchivo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            fM = new FileManager();
        } catch (URISyntaxException ex) {
            Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblPass.setVisible(false);
        txtPass.setVisible(false);
        
        //Listeners
        initBtnListeners();
        
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void initBtnListeners(){
        btnSeleccionarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arcivos rar y zip", "zip", "rar");
                fileChooser.setFileFilter(filtro);
                int res = fileChooser.showOpenDialog(null);
                
                if(res == JFileChooser.APPROVE_OPTION){
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    
                    
                    fM.setPathFile(archivoSeleccionado.getAbsolutePath());
                    try {
                        Object [] datos = fM.analizarArchivo();
                        
                        if(!(boolean)datos[0]){
                            JOptionPane.showMessageDialog(null, "El archivo no es valido\nVerifique que el archivo seleccionado es un archivo rar o zip valido", "Archivo no valido", JOptionPane.ERROR_MESSAGE);
                            archivoSeleccionado = null;
                        }else if((boolean)datos[2]){
                            lblPass.setVisible(true);
                            txtPass.setVisible(true);
                            archivoEncriptado = true;
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ZipException ex) {
                        Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    archivo = new Archivo(archivoSeleccionado.getAbsolutePath(), archivoEncriptado);
                    txtArchivoPath.setText(archivoSeleccionado.getAbsolutePath());
                    
                }
            }
        });
        
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(archivo == null){
                    JOptionPane.showMessageDialog(null, "No haz seleccionado ningun archivo", "Selecciona un archivo", JOptionPane.ERROR_MESSAGE);
                }else{
                    //Verificar contraseña
                    if(archivoEncriptado){
                        boolean contraseñaValida = false;
                        
                        try {
                            fM.setPathFile(archivo.getAbsolutePath(), txtPass.getText());
                            
                            contraseñaValida = fM.validarContraseña();
                        } catch (IOException ex) {
                            Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ZipException ex) {
                            Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if(contraseñaValida == false){
                            JOptionPane.showMessageDialog(null, "La contraseña no es valida, intentalo de nuevo.", "Error en la contraseña", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else{
                            archivo.setPassword(txtPass.getText());
                        }
                    }
                    
                    okPressed = true;
                    dispose();
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        });
    }
    
    public void setArchivoSeleccionado(Archivo archivo){
        this.archivo = archivo;
    }
    
    public Archivo getArchivoSeleccionado(){
        return archivo;
    }
    
    public boolean getOkPressed(){
        return okPressed;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtArchivoPath = new javax.swing.JTextField();
        btnSeleccionarArchivo = new javax.swing.JButton();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar archivo al gestor");
        setResizable(false);

        btnAceptar.setText("Aceptar");

        btnCancelar.setText("Cancelar");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addFileIcon.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Selecciona un archivo zip o rar");

        txtArchivoPath.setText("  ");
        txtArchivoPath.setToolTipText("");
        txtArchivoPath.setEnabled(false);
        txtArchivoPath.setFocusable(false);

        btnSeleccionarArchivo.setText("Seleccionar archvio");

        lblPass.setText("Contraseña");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAceptar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCancelar))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblPass)
                                .addComponent(btnSeleccionarArchivo))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtArchivoPath, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                .addComponent(txtPass)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSeleccionarArchivo)
                            .addComponent(txtArchivoPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAceptar)
                            .addComponent(btnCancelar))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblPass;
    private javax.swing.JTextField txtArchivoPath;
    private javax.swing.JTextField txtPass;
    // End of variables declaration//GEN-END:variables
}
