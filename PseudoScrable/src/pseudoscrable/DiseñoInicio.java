/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudoscrable;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import listasimplediccionario.ListaPalabras;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author estre
 */
public class DiseñoInicio extends javax.swing.JFrame {

            FileInputStream txteditor;
            FileOutputStream salida;
            File archivo ; 
            
            DiseñoInicio2 diseñoInicio2= new DiseñoInicio2();  
            ListaPalabras listaPalabras = new ListaPalabras();    
            
            Tablero tablero;
                    
    public DiseñoInicio() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        btnAbrirArchivo = new javax.swing.JButton();
        btnJugar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Diseño Uno"); // NOI18N

        btnAbrirArchivo.setText("LEER ARCHIVO");
        btnAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirArchivoActionPerformed(evt);
            }
        });

        btnJugar.setText("JUGAR");
        btnJugar.setEnabled(false);
        btnJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(btnAbrirArchivo)
                .addGap(84, 84, 84)
                .addComponent(btnJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrirArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnAbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirArchivoActionPerformed
//abre cuadro de dialogo para selecionar archivo de usuarios
        
        if(jFileChooser1.showDialog(null, "ABRIR ARCHIVO") == JFileChooser.APPROVE_OPTION){
            archivo = jFileChooser1.getSelectedFile();
            if(archivo.canRead()){
                if(archivo.getName().endsWith("xml")){                       
                    try {
                        //Abre el archivo xml 
                        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
                        builder.setIgnoringComments(true);
                        builder.setIgnoringElementContentWhitespace(true);
                        DocumentBuilder dbuilder = builder.newDocumentBuilder();
                        Document document = dbuilder.parse(archivo);
                        document.getDocumentElement().normalize();
                        
                        //selecciona el nodo raiz
                        System.out.println("root element"+ document.getDocumentElement().getNodeName());
                        System.out.println("----------------");   
                        //Obtiene valor de etiqueta dimension
                        Node etiquetaDimension = (Node)(XPathFactory.newInstance().newXPath().evaluate("/scrabble/dimension", document, XPathConstants.NODE));  
                        if (etiquetaDimension!=null ){  
                          System.out.println("Dimension : " + etiquetaDimension.getTextContent()); 
                        }  
                       // Obtiene valor de los hijos de etiqueta dobles y triples
                       //<casilla>
				//<x>2</x>
				//<y>5</y>
			//</casilla>
                       NodeList doblesList = (NodeList)(Node)(XPathFactory.newInstance().newXPath().evaluate("/scrabble/dobles", document, XPathConstants.NODE));  
                       NodeList triplesList = (NodeList)(XPathFactory.newInstance().newXPath().evaluate("/scrabble/triples", document, XPathConstants.NODE));  
                       for (int i = 0; i < doblesList.getLength(); i++) {                          
                            Node nNode =  doblesList.item(i);
                            NodeList casillas= nNode.getChildNodes();                              
                                     for(int j = 0 ; j < casillas.getLength(); j++){
                                         Node node =  casillas.item(j);
                                         if(node.getNodeType()== Node.ELEMENT_NODE ){
                                             System.out.println("Coordenada doble: " + node.getTextContent());
                                             }
                                     } 
                            
                        }
                       for (int i = 0; i < triplesList.getLength(); i++) {                          
                            Node nNode =  triplesList.item(i);
                            NodeList casillas= nNode.getChildNodes();                              
                                     for(int j = 0 ; j < casillas.getLength(); j++){
                                         Node node =  casillas.item(j);
                                         if(node.getNodeType()== Node.ELEMENT_NODE ){
                                             System.out.println("Coordenada triple: " + node.getTextContent());
                                             }
                                     } 
                            
                        }
                       //Obtiene lista de palabras de la etiqueta diccionario
                        NodeList palabrasList = (NodeList)(Node)(XPathFactory.newInstance().newXPath().evaluate("/scrabble/diccionario", document, XPathConstants.NODE)); 
                        for(int j = 0 ; j <palabrasList.getLength(); j++){
                            Node nNode =  palabrasList.item(j);
                            if (nNode.getNodeType()== Node.ELEMENT_NODE){  
                                //System.out.println(nNode.getTextContent()); 
                                listaPalabras.addLast(nNode.getTextContent());
                            }
                        } 
                        diseñoInicio2.setVisible(true);
                        btnJugar.setEnabled(true);
                    } catch (IOException io) {
                    System.out.println(io.getMessage());
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(DiseñoInicio.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(DiseñoInicio.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(DiseñoInicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //String contenido = this.abrir_XML_DOM(archivo);
                    //editor.setText(contenido);                                      
                }else {                 
                        JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo de usuarios");
                    
                }
            }
        }            // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirArchivoActionPerformed

    private void btnJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugarActionPerformed
        //listaPalabras.imprimir();    
        listaPalabras.generarGrafoTxt();
        diseñoInicio2.listaJugadores.grafoJugador();
        
        Tablero tablero = new Tablero (listaPalabras, diseñoInicio2.listaJugadores);
        Reportes reportes = new Reportes();
        reportes.setVisible(true);
        tablero.setVisible(true);
         Hilo hilo1 = new Hilo( "Hilo-1" , reportes);
    hilo1.start();
    }//GEN-LAST:event_btnJugarActionPerformed

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
            java.util.logging.Logger.getLogger(DiseñoInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiseñoInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiseñoInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiseñoInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiseñoInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirArchivo;
    private javax.swing.JButton btnJugar;
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables

    private boolean isNumeric(String letra) {
        try {
    		Integer.parseInt(letra);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
}
