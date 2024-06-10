/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.rudyreyes.javacraft.vista;

import com.rudyreyes.javacraft.controlador.analisis.parser;
import com.rudyreyes.javacraft.controlador.analisis.scanner;
import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.vista.util.NumeroDeLinea;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rudyo
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        
        NumeroDeLinea lineaConsola = new NumeroDeLinea(areaConsola);
        scrollConsola.setRowHeaderView(lineaConsola);
        
        File carpeta = new File("archivosJC");
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crea los directorios necesarios si no existen
        }
        cerrarPestanias();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiTitulo = new javax.swing.JLabel();
        btnAbrirArchivo = new javax.swing.JButton();
        btnPestania = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        etiEntrada = new javax.swing.JLabel();
        etiConsola = new javax.swing.JLabel();
        scrollConsola = new javax.swing.JScrollPane();
        areaConsola = new javax.swing.JTextArea();
        areaCodigo = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaCraft");
        setMinimumSize(new java.awt.Dimension(400, 400));

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));

        etiTitulo.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        etiTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiTitulo.setText("JavaCraft");

        btnAbrirArchivo.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnAbrirArchivo.setText("Abrir Archivo");
        btnAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirArchivoActionPerformed(evt);
            }
        });

        btnPestania.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnPestania.setText("Nueva Pestaña");
        btnPestania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPestaniaActionPerformed(evt);
            }
        });

        btnEjecutar.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        btnReportes.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnReportes.setText("Reportes");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        etiEntrada.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiEntrada.setText("Area de Codigo:");

        etiConsola.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        etiConsola.setText("Consola:");

        areaConsola.setColumns(20);
        areaConsola.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        areaConsola.setRows(5);
        scrollConsola.setViewportView(areaConsola);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(etiTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAbrirArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPestania, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEjecutar, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addGap(257, 257, 257))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(areaCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollConsola, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(etiEntrada, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiConsola, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiTitulo)
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrirArchivo)
                    .addComponent(btnPestania)
                    .addComponent(btnEjecutar)
                    .addComponent(btnReportes))
                .addGap(18, 18, 18)
                .addComponent(etiEntrada)
                .addGap(18, 18, 18)
                .addComponent(areaCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiConsola)
                .addGap(18, 18, 18)
                .addComponent(scrollConsola)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirArchivoActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt, *.py, *.xml, *.csv, *.jc)", "txt", "py", "xml", "csv","jc");
        fc.setFileFilter(filtro);

        int seleccion = fc.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                reader.close();
                
                JTextArea textArea = new JTextArea(contenido.toString());
                textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
                
                
                NumeroDeLinea areaC = new NumeroDeLinea(textArea);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setRowHeaderView(areaC);
                areaCodigo.addTab(fichero.getName(), scrollPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
        
    }//GEN-LAST:event_btnAbrirArchivoActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        // TODO add your handling code here:
        String contenido="";
        int selectedIndex = areaCodigo.getSelectedIndex();
        if (selectedIndex != -1) {
            JScrollPane scrollPane = (JScrollPane) areaCodigo.getComponentAt(selectedIndex);
            JViewport viewport = scrollPane.getViewport();
            JTextArea textArea = (JTextArea) viewport.getView();
            contenido = textArea.getText();

            //areaConsola.setText(contenido);
            //TENGO QUE MODIFICARLO PARA QUE HAGA LA COMPILACION
            try {
                scanner s = new scanner(new BufferedReader(new StringReader(contenido)));
                parser p = new parser(s);
                var resultado = p.parse();
                
                if (resultado != null) {
                    var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
                    var tabla = new TablaSimbolos();
                    tabla.setNombre("GLOBAL");
                    ast.setConsola("");
                    for (var a : ast.getInstrucciones()) {
                        var res = a.interpretar(ast, tabla);
                    }
                    areaConsola.setText(ast.getConsola());
                }
                
                
            } catch (Exception ex) {
                System.out.println("Algo salio mal");
                System.out.println(ex);
            }
        } else {
            areaConsola.setText("No hay pestañas abiertas");
        }
        
         
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void btnPestaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPestaniaActionPerformed
        // TODO add your handling code here:
         String nombrePestania = JOptionPane.showInputDialog(this, "Ingrese el nombre de la nueva pestaña:");

        if (nombrePestania != null && !nombrePestania.trim().isEmpty()) {
            JTextArea textArea = new JTextArea();
            textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            NumeroDeLinea areaC = new NumeroDeLinea(textArea);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setRowHeaderView(areaC);
            areaCodigo.addTab(nombrePestania, scrollPane);
        } else {
            JOptionPane.showMessageDialog(this, "El nombre de la pestaña no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }//GEN-LAST:event_btnPestaniaActionPerformed

    private void cerrarPestanias(){
        areaCodigo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) { // Clic derecho
                    int tabIndex = areaCodigo.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex >= 0) {
                        // Obtener el nombre de la pestaña
                        String nombrePestana = areaCodigo.getTitleAt(tabIndex);
                        Component tabComponent = areaCodigo.getComponentAt(tabIndex);

                        if (tabComponent instanceof JScrollPane) {
                            JScrollPane scrollPane = (JScrollPane) tabComponent;
                            JTextArea areaContenido = (JTextArea) scrollPane.getViewport().getView();

                            // Obtener el texto del JTextArea
                            String textoContenido = areaContenido.getText();

                            //AQUI DEBERIA DE GUARDAR LA INFOR
                            int guadarDatos = JOptionPane.showConfirmDialog(null,
                                    "¿Guardar los datos antes de cerrar '" + nombrePestana + "'?", "Guardar Datos",
                                    JOptionPane.YES_NO_OPTION);
                            if (guadarDatos == JOptionPane.YES_OPTION) {
                                //GUARDAR ARCHIVOS
                                boolean realizado = true;

                                try {

                                    String rutaCompleta = "archivosJC" + File.separator + nombrePestana; // Construir la ruta completa del archivo

                                    BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta));
                                    writer.write(textoContenido);
                                    writer.close();
                                    realizado = true;
                                } catch (IOException ex) {
                                    realizado = false;
                                }

                                if (realizado) {
                                    JOptionPane.showMessageDialog(null, "Se guardo correctamente");
                                }

                                areaCodigo.removeTabAt(tabIndex);
                            } else {
                                areaCodigo.removeTabAt(tabIndex);
                            }
                        }

                    }
                }
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane areaCodigo;
    private javax.swing.JTextArea areaConsola;
    private javax.swing.JButton btnAbrirArchivo;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnPestania;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel etiConsola;
    private javax.swing.JLabel etiEntrada;
    private javax.swing.JLabel etiTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JScrollPane scrollConsola;
    // End of variables declaration//GEN-END:variables
}
