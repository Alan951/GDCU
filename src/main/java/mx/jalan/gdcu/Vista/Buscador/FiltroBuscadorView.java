/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.Buscador;

import mx.jalan.gdcu.Modelo.FiltroBusqueda;
import mx.jalan.gdcu.Vista.VistaModelos.DateLabelFormatter;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import mx.jalan.gdcu.Utils.Utils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Jorge
 */
public class FiltroBuscadorView extends javax.swing.JDialog {

    private JDatePickerImpl dPickerInf;
    private JDatePickerImpl dPickerSup;
    
    private UtilDateModel modeloDateInf;
    private UtilDateModel modeloDateSup;
    
    private JLabel fechaInf = new JLabel("Fecha inferior a: ");
    private JLabel fechaSup = new JLabel("Fecha superior a: ");
    
    private boolean pressOK;
    
    private FiltroBusqueda filtro;
    private FiltroBusqueda filtroOriginal;
    
    public FiltroBuscadorView(java.awt.Frame parent, boolean modal, String modo, FiltroBusqueda filtro) {
        super(parent, modal);
        this.filtro = filtro;
        init(modo);
    }
    
    public FiltroBuscadorView(java.awt.Frame parent, boolean modal, String modo, FiltroBusqueda filtroOriginal, FiltroBusqueda filtro) {
        super(parent, modal);
        this.filtroOriginal = filtroOriginal;
        this.filtro = filtro;
        init(modo);
    }
    
    public void init(String modo){
        initComponents(); //Inicializacion de los componentes de la interfaz generados por netbeans
        initComp(modo); //Inicializacion de los otros componentes no generados por netbeans y personalizacion.
        initConfig(filtro); //Inicializacion de configuraciones en caso de filtro != null
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void initConfig(FiltroBusqueda filtro){
        System.out.println(filtro.toString());
        if(filtro != null){
            //Init Extensiones
            CBExt.removeAllItems();
            
            CBZip.setSelected(filtro.getExtensiones().contains("zip"));
            CBRar.setSelected(filtro.getExtensiones().contains("rar"));
            
            for(String ext : filtro.getExtensiones()){
                if(ext.equals("zip") || ext.equals("rar"))
                    continue;
                
                CBExt.addItem(ext);
            }
            
            //Init tamaño archivos
            if(filtro.getTamañoInf() != -1){
                txtTamañoInf.setText(filtro.getTamañoInf()+"");
                CBUnidadInf.setSelectedItem(filtro.getUnidadInf());
            }else{
                txtTamañoInf.setText("");
            }if(filtro.getTamañoSup() != -1){
                txtTamañoSup.setText(filtro.getTamañoSup()+"");
                CBUnidadSup.setSelectedItem(filtro.getUnidadSup());
            }else{
                txtTamañoSup.setText("");
            }
            
            //Init date
            if(filtro.getFechaInf() != null){
                modeloDateInf.setValue(filtro.getFechaInf());
            }if(filtro.getFechaSup() != null){
                modeloDateSup.setValue(filtro.getFechaSup());
            }
            
            //Init atributos ocultos
            if(filtro.archivoPuedeSerOcuto()){
                CBarchivos.setSelected(true);
            }if(filtro.carpetaPuedeEstarOculta()){
                CBcarpetas.setSelected(true);
            }
            
        }else{
            CBRar.setSelected(true);
            CBZip.setSelected(true);
        }
        
        
    }
    
    public void initComp(String modo){
        switch(modo){
            case "opav": //Configurar ventana en modo opciones avanzadas
                this.btnReset.setVisible(false);
                this.setTitle("Opciones avanzadas");
                break;
            case "filtro": //Configurar ventana en modo filtro
                this.btnReset.setVisible(true);
                this.setTitle("Filtrar archivos");
                break;
        }
        
        
        initCalendarPick();
        initCBExt();
    }
    
    public void initCBExt(){
        CBExt.removeAllItems();
    }
    
    public void initCalendarPick(){
        modeloDateInf = new UtilDateModel();
        modeloDateSup = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Dia");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        
        JDatePanelImpl datePanelInf = new JDatePanelImpl(modeloDateInf, p);
        JDatePanelImpl datePanelSup = new JDatePanelImpl(modeloDateSup, p);
        dPickerInf = new JDatePickerImpl(datePanelInf, new DateLabelFormatter());
        dPickerSup = new JDatePickerImpl(datePanelSup, new DateLabelFormatter());
        GridLayout gL = new GridLayout(2, 2);
        
        panelFecha.setLayout(gL);
        
        panelFecha.add(fechaInf);
        panelFecha.add(dPickerInf);
        panelFecha.add(fechaSup);
        panelFecha.add(dPickerSup);   
    }
    
    public boolean verificarExtension(String ext){
        if(ext.trim().isEmpty()){
            return false;
        }
        
        String extension = Utils.getExtension(ext);
        
        if(extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("rar")){
            if(extension.equalsIgnoreCase("zip")){
                if(!CBZip.isSelected()){
                    CBZip.setSelected(true);
                }
            }else if(extension.equalsIgnoreCase("rar")){
                if(!CBRar.isSelected()){
                    CBRar.setSelected(true);
                }
            } 
            return false;
        }
        
        for(int x = 0 ; x < CBExt.getItemCount(); x++){
            if(CBExt.getItemAt(x).equalsIgnoreCase(extension)){
                JOptionPane.showMessageDialog(null, "La extension ya esta agregada", "Ops!", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        
        if(extension.equals("*")){
            JOptionPane.showMessageDialog(null, "Se consideraran todos los archivos", "AJUA!", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return true;
    }
    
    public FiltroBusqueda getFiltroBusqueda(){
        return filtro;
    }
    
    public boolean getPressOk(){
        return pressOK;
    }
    
    public String verificarFiltroTamaño(){
        String result = "";
        String inf = txtTamañoInf.getText();
        String sup = txtTamañoSup.getText();
        
        String regex = "^[+]?\\d+([\\.]\\d+)?$";
        
        if(!inf.trim().isEmpty()){
            if(CBUnidadInf.getSelectedIndex() == 0)
                result += "Selecciona una unidad para el tamaño inferior\n";
            
            if(!inf.matches(regex))
                result += "Verifica el campo tamaño inferior\n";
        }else if(CBUnidadInf.getSelectedIndex() != 0){
            CBUnidadInf.setSelectedIndex(0);
        }
        
        if(!sup.trim().isEmpty()){
            if(CBUnidadSup.getSelectedIndex() == 0)
                result += "Selecciona una unidad para el tamaño superior\n";
            
            if(!sup.matches(regex))
                result += "Verifica el campo tamaño superior\n";
        }else if(CBUnidadSup.getSelectedIndex() != 0){
            CBUnidadSup.setSelectedIndex(0);
        }

        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTipoArchivo = new javax.swing.JPanel();
        CBRar = new javax.swing.JCheckBox();
        CBZip = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        CBExt = new javax.swing.JComboBox<>();
        btmAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        panelTamañoArchivo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTamañoInf = new javax.swing.JTextField();
        txtTamañoSup = new javax.swing.JTextField();
        CBUnidadInf = new javax.swing.JComboBox<>();
        CBUnidadSup = new javax.swing.JComboBox<>();
        panelFecha = new javax.swing.JPanel();
        panelOtros = new javax.swing.JPanel();
        CBcarpetas = new javax.swing.JCheckBox();
        CBarchivos = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones avanazadas");
        setResizable(false);

        panelTipoArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de archivo"));

        CBRar.setText(".rar");

        CBZip.setText(".zip");

        jLabel1.setText("Agregar otro");

        CBExt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btmAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/addsmall.png"))); // NOI18N
        btmAdd.setBorderPainted(false);
        btmAdd.setContentAreaFilled(false);
        btmAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btmAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmAddActionPerformed(evt);
            }
        });

        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttons/cross.png"))); // NOI18N
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTipoArchivoLayout = new javax.swing.GroupLayout(panelTipoArchivo);
        panelTipoArchivo.setLayout(panelTipoArchivoLayout);
        panelTipoArchivoLayout.setHorizontalGroup(
            panelTipoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CBRar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CBZip)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBExt, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btmAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTipoArchivoLayout.setVerticalGroup(
            panelTipoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTipoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDel)
                    .addComponent(btmAdd)
                    .addGroup(panelTipoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CBRar)
                        .addComponent(CBZip)
                        .addComponent(jLabel1)
                        .addComponent(CBExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelTamañoArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Tamaño del archivo"));

        jLabel2.setText("Archivos con tamaño superior a: ");

        jLabel3.setText("Archivos con tamaño inferior a: ");

        CBUnidadInf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "KB", "MB", "GB" }));

        CBUnidadSup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "KB", "MB", "GB" }));

        javax.swing.GroupLayout panelTamañoArchivoLayout = new javax.swing.GroupLayout(panelTamañoArchivo);
        panelTamañoArchivo.setLayout(panelTamañoArchivoLayout);
        panelTamañoArchivoLayout.setHorizontalGroup(
            panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTamañoArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTamañoArchivoLayout.createSequentialGroup()
                        .addComponent(txtTamañoSup, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBUnidadSup, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTamañoArchivoLayout.createSequentialGroup()
                        .addComponent(txtTamañoInf, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBUnidadInf, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        panelTamañoArchivoLayout.setVerticalGroup(
            panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTamañoArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTamañoInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBUnidadInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panelTamañoArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTamañoSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBUnidadSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFecha.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de creación"));

        javax.swing.GroupLayout panelFechaLayout = new javax.swing.GroupLayout(panelFecha);
        panelFecha.setLayout(panelFechaLayout);
        panelFechaLayout.setHorizontalGroup(
            panelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );
        panelFechaLayout.setVerticalGroup(
            panelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        panelOtros.setBorder(javax.swing.BorderFactory.createTitledBorder("Otros atributos"));

        CBcarpetas.setText("Incluir carpetas ocultas");

        CBarchivos.setText("Incluir archivos ocultos");

        javax.swing.GroupLayout panelOtrosLayout = new javax.swing.GroupLayout(panelOtros);
        panelOtros.setLayout(panelOtrosLayout);
        panelOtrosLayout.setHorizontalGroup(
            panelOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOtrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CBcarpetas)
                    .addComponent(CBarchivos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOtrosLayout.setVerticalGroup(
            panelOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOtrosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CBcarpetas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBarchivos))
        );

        btnAceptar.setText("Aceptar");
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

        btnReset.setText("Restablecer");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTipoArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTamañoArchivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTipoArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTamañoArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar)
                    .addComponent(btnReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        pressOK = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String comprobarTamaño = verificarFiltroTamaño();
        double inf = 0;
        double sup = 0;

        if(!comprobarTamaño.equals("")){
            JOptionPane.showMessageDialog(null, comprobarTamaño, "Verifica los campos", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            if(!txtTamañoInf.getText().trim().isEmpty()){
                inf = Double.parseDouble(txtTamañoInf.getText());
            }else{
                inf = -1;
            }if(!txtTamañoSup.getText().trim().isEmpty()){
                sup = Double.parseDouble(txtTamañoSup.getText());
            }else{
                sup = -1;
            }
        }

        boolean incluirArvosOcultos = false;
        boolean incluirCarpetasOcultas = false;
        ArrayList<String> extensiones = new ArrayList<String>();

        if(CBRar.isSelected()){
            extensiones.add("rar");
        }if(CBZip.isSelected()){
            extensiones.add("zip");
        }

        if(CBExt.getItemCount() != -1)
        for(int x = 0 ; x < CBExt.getItemCount() ; x++)
        extensiones.add(CBExt.getItemAt(x));

        if(CBarchivos.isSelected())
        incluirArvosOcultos = true;

        if(CBcarpetas.isSelected())
        incluirCarpetasOcultas = true;

        //Fechas
        Date dateInf = modeloDateInf.getValue();
        Date dateSup = modeloDateSup.getValue();

        filtro = new FiltroBusqueda(inf, sup, dateInf, dateSup, incluirCarpetasOcultas, incluirArvosOcultos, extensiones);

        //Set unidades
        filtro.setUnidadInf(CBUnidadInf.getSelectedItem().toString());
        filtro.setUnidadSup(CBUnidadSup.getSelectedItem().toString());

        pressOK = true;

        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int index = CBExt.getSelectedIndex();
        if(index != -1)
        CBExt.removeItemAt(index);
        else
        JOptionPane.showMessageDialog(null, "Debes de seleccionar una extensión", "Extensión no seleccionada", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnDelActionPerformed

    private void btmAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmAddActionPerformed

        String result = JOptionPane.showInputDialog(this, "Introduce la extensión Ej: \".iso\"", "Agregar extensión", JOptionPane.INFORMATION_MESSAGE);

        if(result != null){
            if(verificarExtension(result)){
                
                CBExt.addItem(Utils.getExtension(result));
            }
        }

    }//GEN-LAST:event_btmAddActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        initConfig(filtroOriginal);
    }//GEN-LAST:event_btnResetActionPerformed

    public JDatePickerImpl getdPickerInf() {
        return dPickerInf;
    }

    public JDatePickerImpl getdPickerSup() {
        return dPickerSup;
    }

    public UtilDateModel getModeloDateInf() {
        return modeloDateInf;
    }

    public UtilDateModel getModeloDateSup() {
        return modeloDateSup;
    }

    public boolean isPressOK() {
        return pressOK;
    }

    public JComboBox<String> getCBExt() {
        return CBExt;
    }

    public JCheckBox getCBRar() {
        return CBRar;
    }

    public JComboBox<String> getCBUnidadInf() {
        return CBUnidadInf;
    }

    public JComboBox<String> getCBUnidadSup() {
        return CBUnidadSup;
    }

    public JCheckBox getCBZip() {
        return CBZip;
    }

    public JCheckBox getCBarchivos() {
        return CBarchivos;
    }

    public JCheckBox getCBcarpetas() {
        return CBcarpetas;
    }

    public JButton getBtmAdd() {
        return btmAdd;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnDel() {
        return btnDel;
    }

    public JTextField getTxtTamañoInf() {
        return txtTamañoInf;
    }

    public JTextField getTxtTamañoSup() {
        return txtTamañoSup;
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBExt;
    private javax.swing.JCheckBox CBRar;
    private javax.swing.JComboBox<String> CBUnidadInf;
    private javax.swing.JComboBox<String> CBUnidadSup;
    private javax.swing.JCheckBox CBZip;
    private javax.swing.JCheckBox CBarchivos;
    private javax.swing.JCheckBox CBcarpetas;
    private javax.swing.JButton btmAdd;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelFecha;
    private javax.swing.JPanel panelOtros;
    private javax.swing.JPanel panelTamañoArchivo;
    private javax.swing.JPanel panelTipoArchivo;
    private javax.swing.JTextField txtTamañoInf;
    private javax.swing.JTextField txtTamañoSup;
    // End of variables declaration//GEN-END:variables
}
