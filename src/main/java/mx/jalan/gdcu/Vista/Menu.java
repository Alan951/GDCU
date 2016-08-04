package mx.jalan.gdcu.Vista;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import mx.jalan.gdcu.Controlador.CtrlBusquedaArchivos;
import mx.jalan.gdcu.Controlador.CtrlGestor;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Jorge Alan Villalón Pérez
 */
public class Menu extends javax.swing.JFrame {

    private JXLabel info;
    
    private CtrlBusquedaArchivos ctrlBus;
    private CtrlGestor ctrlGes;
    
    public Menu() {
        initComponents();
        initInfoLabel();
        initLabelListeners();
    }
    
    public void initInfoLabel(){
        info = new JXLabel();
        info.setText("Si te resulto útil la aplicación, puedes contarme a través de twitter como fue que te ayudo.\n\n"
                + "¿Tienes ideas o bugs? ¡Házmelas saber a través de mi correo electrónico!\n\n"
                + "Sigue de cerca el proyecto a través de mi GitHub");
        
        info.setLineWrap(true);
        panelInfo.setLayout(new GridLayout(1,1));
        
        panelInfo.add(info);
        
        lblEmail.setToolTipText("Mi correo: jorge_951-ck@hotmail.com");
        lblTwitter.setToolTipText("Mi twitter: @JorgeAlan951");
        lblGit.setToolTipText("Mi git: Alan951");
    }
    
    public void initLabelListeners(){
        if(!Desktop.isDesktopSupported())   return;
        
        Desktop d = Desktop.getDesktop();
        
        lblTwitter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    d.browse(new URI("https://twitter.com/JorgeAlan951"));
                } catch (IOException ex) {} catch (URISyntaxException ex) {}
            }
        });
        
        lblEmail.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    d.mail(new URI("mailto:jorge_951-ck@hotmail.com"));
                }catch(IOException ex) {} catch(URISyntaxException ex){}
            }
        });
        
        lblGit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    d.browse(new URI("https://github.com/Alan951/GDCU"));
                }catch(IOException ex) {} catch(URISyntaxException ex){}
            }
        });
        
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnGestorDeContraseñas = new javax.swing.JButton();
        btnBuscadorDeArchivos = new javax.swing.JButton();
        panelInfo = new javax.swing.JPanel();
        lblEmail = new javax.swing.JLabel();
        lblTwitter = new javax.swing.JLabel();
        lblGit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GDCU");

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel2.setText("Gestor de contraseñas y utilidades");

        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setText("Aplicación en desarrollo y en constantes modificaciones");

        btnGestorDeContraseñas.setText("Gestor de contraseñas");
        btnGestorDeContraseñas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestorDeContraseñasActionPerformed(evt);
            }
        });

        btnBuscadorDeArchivos.setText("Buscador de archivos");
        btnBuscadorDeArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscadorDeArchivosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );

        lblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/email flat60x60.png"))); // NOI18N
        lblEmail.setToolTipText("");

        lblTwitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/twitter flat60x60.png"))); // NOI18N
        lblTwitter.setToolTipText("");

        lblGit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Github-Grumpy60x60.png"))); // NOI18N
        lblGit.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestorDeContraseñas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscadorDeArchivos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(lblEmail)
                                .addGap(18, 18, 18)
                                .addComponent(lblTwitter)
                                .addGap(18, 18, 18)
                                .addComponent(lblGit)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGestorDeContraseñas, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscadorDeArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail)
                    .addComponent(lblTwitter)
                    .addComponent(lblGit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestorDeContraseñasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestorDeContraseñasActionPerformed
        if(ctrlGes != null){
            if(ctrlGes.isVisible())
                ctrlGes.traerAdelante();
            else
                ctrlGes.abrirGestor();
        }else{
            ctrlGes = new CtrlGestor();
            ctrlGes.abrirGestor();
        }
    }//GEN-LAST:event_btnGestorDeContraseñasActionPerformed

    private void btnBuscadorDeArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscadorDeArchivosActionPerformed
        if(ctrlBus != null){
            if(ctrlBus.isVisible())
                ctrlBus.traerAdelante();
            else
                ctrlBus.abrirBusquedaArchivos();
        }else{
            ctrlBus = new CtrlBusquedaArchivos();
            ctrlBus.abrirBusquedaArchivos();
        }
        
    }//GEN-LAST:event_btnBuscadorDeArchivosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscadorDeArchivos;
    private javax.swing.JButton btnGestorDeContraseñas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGit;
    private javax.swing.JLabel lblTwitter;
    private javax.swing.JPanel panelInfo;
    // End of variables declaration//GEN-END:variables
}
