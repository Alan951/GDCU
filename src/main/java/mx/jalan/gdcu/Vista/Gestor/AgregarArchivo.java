/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Gestor;

import Properties.Propiedades;
import java.awt.Color;
import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.FileManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import mx.jalan.gdcu.Presistencia.PresistenciaManager;
import mx.jalan.gdcu.Utils.FuerzaBruta;
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
    
    private AgregarArchivo esteDialog = this;
    
    private LineBorder borderRed;
    private Border borderDefault;
    
    private String lastPath = Propiedades.getInstance().getPropertie(Propiedades.LAST_PATH);
    
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
        
        borderRed = new LineBorder(Color.RED);
        borderDefault = UIManager.getBorder("TextField.border");
        
        //Listeners
        initBtnListeners();
        initTxtListeners();
        
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
        
        
    }
    
    public void initTxtListeners(){
        txtArchivoPath.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            
            public void warn(){
                if(!txtArchivoPath.getText().trim().isEmpty()){
                    Archivo f = null;
                    
                    
                    f = new Archivo(txtArchivoPath.getText());

                    if(!f.exists()){
                        //JOptionPane.showMessageDialog(esteDialog, "La ruta ingresada no es correcta", "Ruta incorrecta", JOptionPane.ERROR_MESSAGE);
                        txtArchivoPath.setBorder(borderRed);
                        btnDetalles.setVisible(false);
                        
                        lblPass.setVisible(false);
                        txtPass.setVisible(false);
                        
                        archivo = null;
                    }else{
                        if(!f.isFile()){
                            txtArchivoPath.setBorder(borderRed);    
                            btnDetalles.setVisible(false);
                            
                            archivo = null;
                        }else{
                            
                            txtArchivoPath.setBorder(borderDefault);
                            
                            verificarArchivo(f);
                            
                            lblPass.setVisible(archivoEncriptado);
                            txtPass.setVisible(archivoEncriptado);
                            
                            //Intenta buscar la contraseña a partir de los archivos ya registrados
                            if(archivoEncriptado){
                                try {
                                    FuerzaBruta fb = new FuerzaBruta(archivo);
                                    ArrayList<String> contraseñas = new ArrayList<String>();
                                    for(Archivo archivo : PresistenciaManager.getInstance().getArchivosGestor()){
                                        //System.out.println(archivo);
                                        if(archivo.getPassword() != null){
                                            contraseñas.add(archivo.getPassword());
                                        }
                                    }
                                    
                                    fb.setListaContraseñas(contraseñas);
                                    
                                    fb.go();
                                    
                                    archivo = fb.getArchivos().get(0);
                                    
                                    System.out.println("Pass archivo: "+archivo.getPassword());
                                    
                                    if(archivo.getPassword() != null){
                                        JOptionPane.showMessageDialog(esteDialog, "Contraseña encontrada: "+archivo.getPassword(), "Contraseña encontrada", JOptionPane.INFORMATION_MESSAGE);
                                        txtPass.setText(archivo.getPassword());
                                    }
                                } catch (URISyntaxException ex) {
                                    ex.printStackTrace();
                                } catch (IOException ex) {
                                    Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ZipException ex) {
                                    Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                
                            }
                            
                            btnDetalles.setVisible(true);
                        }
                    }
                }
            }
        });
    }
    
    private void initBtnListeners(){
        btnSeleccionarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                
                fileChooser.setCurrentDirectory(new File(lastPath));
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos rar y zip", "zip", "rar");
                fileChooser.setFileFilter(filtro);
                int res = fileChooser.showOpenDialog(null);
                
                if(res == JFileChooser.APPROVE_OPTION){
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    
                    //verificarArchivo(new Archivo(archivoSeleccionado.getAbsolutePath()));
                    
                    //archivo = new Archivo(archivoSeleccionado.getAbsolutePath(), archivoEncriptado);
                    txtArchivoPath.setText(archivoSeleccionado.getAbsolutePath());      
                    //prefs.put("LAST_PATH", archivoSeleccionado.getAbsolutePath());
                    Propiedades.getInstance().setPropertie(Propiedades.LAST_PATH, archivoSeleccionado.getPath());
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
                    
                    try {
                        archivo.respaldarInformacion();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        if(!archivo.exists()){
                            JOptionPane.showMessageDialog(null, "Parece ser que el archivo no existe", "Hubó un problema", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else{
                            JOptionPane.showMessageDialog(null, "No pudo generar fecha del archivo.\nEsto puede ocurrir por que el archivo esta protegido por el sistema", "Hubó un problema", JOptionPane.ERROR_MESSAGE);
                            return;
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
    
    public void verificarArchivo(Archivo archivoSeleccionado){
        fM.setPathFile(archivoSeleccionado.getAbsolutePath());
        try {
            Object [] datos = fM.analizarArchivo();

            if(!(boolean)datos[0]){
                JOptionPane.showMessageDialog(null, "El archivo no es valido\nVerifique que el archivo seleccionado es un archivo rar o zip valido", "Archivo no valido", JOptionPane.ERROR_MESSAGE);
                archivoSeleccionado = null;
            }else if((boolean)datos[2]){
                //lblPass.setVisible(true);
                //txtPass.setVisible(true);
                
            }
            
            archivoSeleccionado.setEstaCifrado((boolean)datos[2]);
            archivoEncriptado = (boolean)datos[2];
            

        } catch (IOException ex) {
            Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ZipException ex) {
            Logger.getLogger(AgregarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        archivo = archivoSeleccionado;
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
        btnDetalles = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar archivo al gestor");
        setResizable(false);

        btnAceptar.setText("Aceptar");

        btnCancelar.setText("Cancelar");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addFileIcon.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Selecciona un archivo zip o rar");

        txtArchivoPath.setToolTipText("");

        btnSeleccionarArchivo.setText("Seleccionar archvio");

        lblPass.setText("Contraseña");

        btnDetalles.setText("Agregar mas detalles");
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

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
                            .addComponent(btnDetalles)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                            .addComponent(btnCancelar)
                            .addComponent(btnDetalles))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        Detalles det = new Detalles(null, true);
        if(det.pressOk()){
            archivo.setDetalles(det.getLinkFuente(), det.getLinkDescarga(), det.getComentarios());
        }
    }//GEN-LAST:event_btnDetallesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblPass;
    private javax.swing.JTextField txtArchivoPath;
    private javax.swing.JTextField txtPass;
    // End of variables declaration//GEN-END:variables
}
