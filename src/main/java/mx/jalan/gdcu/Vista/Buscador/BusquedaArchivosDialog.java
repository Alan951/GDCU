/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Buscador;

import mx.jalan.gdcu.Modelo.Archivo;
import mx.jalan.gdcu.Utils.Buscador;
import mx.jalan.gdcu.Utils.FileManager;
import mx.jalan.gdcu.Utils.Utils;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Jorge
 */
public class BusquedaArchivosDialog extends javax.swing.JDialog {
    //Variable para identificar si se aborta la operación.
    private CheckStatus checkStatus;
    private Buscador buscador;
    
    private final String zipLabel = " archivos zip protegidos";
    private final String rarLabel = " archivos rar protegidos";
    private final String invalidosLabel = " archivos invalidos";
    
    private boolean verDetallesPressed;
    
    private FiltroBusqueda filtro;
    
    private FileManager fM;
    
    private ArrayList<Archivo> archivos; //Lista de archivos que se mostrara en los detalles.
    
    private int archivoNoValidos;
    
    public BusquedaArchivosDialog(java.awt.Frame parent, boolean modal, Buscador buscador) {
        super(parent, modal);
        initComponents();
        initComp();
        this.buscador = buscador;
        
        this.filtro = buscador.getFiltro();
        
        checkStatus.setStatus("START");
        
        archivos = new ArrayList<Archivo>();
        
        
        //--listener cierre de la ventana--
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                if(buscador.getAbortStatus()){
                    buscador.getSearchThread().interrupt();
                }
            }
        });
    }
    
    public void initComp(){
        btnVerDetalles.setEnabled(false);
        btnVerDetalles.setVisible(false);
        btnRegresar.setVisible(false);
        btnSalir.setVisible(false);
        
        lblRarCount.setText("0");
        lblZipCount.setText("0");
        
        checkStatus = new CheckStatus();
        
        CheckStatusListener listenerCheck = new CheckStatusListener();
        checkStatus.addPropertyChangeListener(listenerCheck);
        
        DefaultCaret caret = (DefaultCaret)getConsoleStatus().getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
    }
    
    public void comenzar_a_buscar(){
        buscador.setView(this);
        buscador.buscar();
    }    
    
    public void verificarArchivos() throws URISyntaxException{        
        fM = new FileManager();
        
        Object[] data = null;
        boolean valido = false;
        String mensaje = null;
        boolean isEncrypted = false;
        
        try{
            for(File f : buscador.getArchivosEncontrados()){
                //Pausar
                while(checkStatus.status.equals("ABORT")){
                    System.out.println("Pausado!");
                    Thread.sleep(3000);
                }
                
                if(!Utils.getExtension(f).equalsIgnoreCase("zip") && !Utils.getExtension(f).equalsIgnoreCase("rar")){
                    archivos.add(new Archivo(f.getAbsolutePath()));
                    continue;
                }
                
                fM.setPathFile(f.getAbsolutePath());
                consoleStatus.append("Archivo: "+f.getName()+" | "+f.getAbsolutePath()+".\n");
                
                /*
                if(fM.getExtension().equalsIgnoreCase("zip")){
                    data = fM.analizarZip();
                }else if(fM.getExtension().equalsIgnoreCase("rar")){
                    data = fM.analizarRar();
                }*/
                data = fM.analizarArchivo();
                
                valido = (boolean)data[0];
                mensaje = (String) data[1];
                isEncrypted = (boolean) data[2];
                
                if(valido){
                    if(isEncrypted){
                        consoleStatus.append("**Cifrado\n");
                        lblRarPass.setText(fM.getArchivosRarConContraseña() + rarLabel);
                        lblZipPass.setText(fM.getArchivosZipConContraseña() + zipLabel);
                    }
                    
                    archivos.add(new Archivo(f.getAbsolutePath(), isEncrypted));
                }else{
                    archivoNoValidos++;
                    lblInvalidos.setText(archivoNoValidos + invalidosLabel);
                    archivos.add(new Archivo(f.getAbsolutePath(), false, mensaje));
                    consoleStatus.append("No valido: "+mensaje+" \n");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ZipException e){
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(BusquedaArchivosDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkStatus.setStatus("FINISHVER");
    }
    
    public void mostrarOpcionesFinales(int opciones){
        switch(opciones){
            case 1: //Cuando encuentre los archivos, le dara la opción de ver los detalles de los archivos encontrados
                btnVerDetalles.setVisible(true);
                btnVerDetalles.setEnabled(true);
                btnAbort.setVisible(false);
                break;
            case 2: //Caundo no se haya encontrado ningun archivo solo le permitira salir del programa o regresar
                btnAbort.setVisible(false);
                btnRegresar.setVisible(true);
                btnSalir.setVisible(true);
                break;
        }
        
        
    }
    /*
        Dado que durante la busqueda de archivos se pueden presentar distintos eventos
        como por ejemplo, cuando el usuario intente pausar la busqueda de archivos o
        cuando finalize la busqueda de los archivos, este proceso se correo en un hilo aparte
        y mi idea mas rapida que se me vino a la mente, seria una variable de tipo string
        en el cual se almacenara el flujo actual del programa y un listener el cual este a la escucha
        de cambios en este valor
    
        Dicho String tiene como variable "checkStatus" y es de tipo objeto CheckStatus.
    */
    public class CheckStatus{
        protected PropertyChangeSupport propertyChangeSupport;
        private String status;
        
        public CheckStatus(){
            propertyChangeSupport = new PropertyChangeSupport(this);
        }
        
        public void setStatus(String status){
            String oldStatus = this.status;
            this.status = status;
            propertyChangeSupport.firePropertyChange("CheckStatusProperty", oldStatus, status);
        }
        
        public void addPropertyChangeListener(PropertyChangeListener listener){
            propertyChangeSupport.addPropertyChangeListener(listener);
        }  
    }
    
    /*
        Listener en la escucha de nuevos valores para el checkstatus y aqui se decide que va
        hacer el programa en distintas situaciones.
    */
    private class CheckStatusListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals("CheckStatusProperty")){
                System.out.println("[DG] Entro al evento: "+evt.getNewValue());
                if(evt.getNewValue().equals("START")){ //Comienza con la busqueda de los archivos
                    comenzar_a_buscar();
                }else if(evt.getNewValue().equals("ABORT")){ //La busqueda es pausada
                    buscador.abortar();
                    actualizarImagen("ABORT");
                }else if(evt.getNewValue().equals("RESUME")){ //La busqueda es retomada
                    buscador.renaudar();
                    actualizarImagen("RESUME");
                }else if(evt.getNewValue().equals("FINISH")){ //La busqueda finalizo (despues sigue con la verificacion de los archivos)
                    consoleStatus.append("===== VERIFICANDO ARCHIVOS =====\n");
                    try {
                        verificarArchivos();
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(BusquedaArchivosDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if (evt.getNewValue().equals("FINISHVER")){ //La verificacion de los archivos es finalizada.
                    consoleStatus.append("===== FINALIZADO =====\n");
                    actualizarImagen("FINISH");
                    
                    if(archivos.size() == 0){
                        JOptionPane.showMessageDialog(null, "No se encontro ningún archivo.", "No se encontro coincidencia.", JOptionPane.INFORMATION_MESSAGE);
                        mostrarOpcionesFinales(2);
                    }else{
                        mostrarOpcionesFinales(1);
                    }
                    
                    
                }
            }
        }
    }
    
    /*
        Distintos estados que puede tener la imagen del buscador
    */
    public void actualizarImagen(String status){
        switch(status){
            case "RESUME":
                imgLoad.setIcon(new ImageIcon(getClass().getResource("/img/hexLoad.gif")));
                break;
            case "ABORT":
                imgLoad.setIcon(new ImageIcon(getClass().getResource("/img/hexLoad-naranja.png")));
                break;
            case "FINISH":
                imgLoad.setIcon(new ImageIcon(getClass().getResource("/img/hexLoad-verde.png")));
                break;
        }
    }
    
    public ArrayList<Archivo> getArchivos(){
        return archivos;
    }
    
    public boolean getVerDetallesPressed(){
        return verDetallesPressed;
    }
    
    public JTextArea getConsoleStatus() {
        return consoleStatus;
    }

    public JLabel getLblCarpetasCount() {
        return lblCarpetasCount;
    }

    public JLabel getLblRarCount() {
        return lblRarCount;
    }

    public JLabel getLblZipCount() {
        return lblZipCount;
    }

    public JLabel getLblArchivosCount() {
        return lblArchivosCount;
    }
    
    public CheckStatus getCheckStatus(){
        return this.checkStatus;
    }
    
    public JLabel getLblOtrosArchivos(){
        return this.lblOtrosArchivos;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgLoad = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleStatus = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnVerDetalles = new javax.swing.JButton();
        btnAbort = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblArchivosCount = new javax.swing.JLabel();
        lblZipCount = new javax.swing.JLabel();
        lblRarCount = new javax.swing.JLabel();
        lblRarPass = new javax.swing.JLabel();
        lblZipPass = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblZip = new javax.swing.JLabel();
        lblRar = new javax.swing.JLabel();
        lblCarpetas = new javax.swing.JLabel();
        lblCarpetasCount = new javax.swing.JLabel();
        lblInvalidos = new javax.swing.JLabel();
        lblOtrosArchivos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscando archivos");

        imgLoad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hexLoad.gif"))); // NOI18N

        consoleStatus.setBackground(new java.awt.Color(0, 0, 0));
        consoleStatus.setColumns(20);
        consoleStatus.setForeground(new java.awt.Color(0, 255, 0));
        consoleStatus.setRows(5);
        jScrollPane1.setViewportView(consoleStatus);

        btnVerDetalles.setText("Ver detalles");
        btnVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetallesActionPerformed(evt);
            }
        });

        btnAbort.setText("Cancelar operacion");
        btnAbort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbortActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVerDetalles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAbort))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbort, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerDetalles)))
        );

        lblArchivosCount.setText(" ");

        lblZipCount.setText(" ");

        lblRarCount.setText(" ");

        lblRarPass.setText(" ");

        lblZipPass.setText(" ");

        jLabel1.setText("Archivos encontrados:");

        lblZip.setText("Zip:");

        lblRar.setText("Rar:");

        lblCarpetas.setText("Carpetas:");

        lblCarpetasCount.setText("       ");

        lblInvalidos.setText("  ");

        lblOtrosArchivos.setText("  ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblArchivosCount, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInvalidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblZip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblZipCount, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblZipPass, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCarpetas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCarpetasCount, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblRar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRarCount, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRarPass, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblOtrosArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarpetas)
                    .addComponent(lblCarpetasCount))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblArchivosCount)
                    .addComponent(lblInvalidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZip)
                    .addComponent(lblZipCount)
                    .addComponent(lblZipPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRar)
                    .addComponent(lblRarCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRarPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOtrosArchivos))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgLoad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbortActionPerformed
        if(buscador.getAbortStatus()){
            btnAbort.setText("Abortar");
            checkStatus.setStatus("RESUME");
        }else{
            btnAbort.setText("Renaudar");
            checkStatus.setStatus("ABORT");
        }
    }//GEN-LAST:event_btnAbortActionPerformed

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        this.dispose();
        verDetallesPressed = true;
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
        verDetallesPressed = false;
    }//GEN-LAST:event_btnRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbort;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JTextArea consoleStatus;
    private javax.swing.JLabel imgLoad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArchivosCount;
    private javax.swing.JLabel lblCarpetas;
    private javax.swing.JLabel lblCarpetasCount;
    private javax.swing.JLabel lblInvalidos;
    private javax.swing.JLabel lblOtrosArchivos;
    private javax.swing.JLabel lblRar;
    private javax.swing.JLabel lblRarCount;
    private javax.swing.JLabel lblRarPass;
    private javax.swing.JLabel lblZip;
    private javax.swing.JLabel lblZipCount;
    private javax.swing.JLabel lblZipPass;
    // End of variables declaration//GEN-END:variables
}
