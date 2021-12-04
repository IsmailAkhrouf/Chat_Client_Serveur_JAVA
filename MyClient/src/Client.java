
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.util.*;
public class Client extends javax.swing.JFrame {
    Friend F;
    String id;
    String nom;
    Socket client;
    DefaultListModel dlm,dlm1;
    PrintStream fluxEcriture;
   
    
    public Client() {
        initComponents();
    }

    Client(String id, Socket s,String nom) throws SQLException {
       this.id=id;
       this.client=s;
       this.nom=nom;
       this.F= new Friend();
       try {
           initComponents();
           
           dlm1 = new DefaultListModel();
           UL1.setModel(dlm1);
           idlabel.setText(this.nom);
           fluxEcriture = new PrintStream(this.client.getOutputStream());
           new Extraire_Message_Bd_To_Csv(this.id,this.nom);
           new preparerListIdNomStatusContact(this.client,this.id);
           new Read(this.client,this.F,this.id,this.nom).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    class Read extends Thread{
        Socket C;
        Friend F;
        String id;
        String nom;
        
        public Read(Socket s,Friend F,String id,String nom){
          this.C=s;
          this.F=F;
          this.id=id;
          this.nom=nom;
          
        }
        public void run(){
            while(true){
               try{ 
               BufferedReader fluxLecture;
               fluxLecture = new BufferedReader(new InputStreamReader(this.C.getInputStream())); 
               String msg= fluxLecture.readLine(); 
                if(msg.contains("reponse")){
                     msg=msg.substring(7);
                     StringTokenizer st=new StringTokenizer(msg,":"); 
                     String idsend=st.nextToken();
                     msg=st.nextToken();
                     if(idsend.equals(this.F.Ami)){
                         msgBox.append("<"+idsend+"--To--"+"me"+">"+msg+"\n");
                         new Send_Message_To_Csv(this.nom,idsend,this.id,msg);
                     }
                     else{
                        new Send_Message_To_Csv(this.nom,idsend,this.id,msg); 
                     }
                }
                
               }catch(Exception e){
                  e.printStackTrace();
               }
            }
        }
    }
    class preparerListIDContact extends Thread{
        Socket C;
        String IDClient;
        public preparerListIDContact(Socket s,String idc){
           this.C=s;
           this.IDClient=idc;
        }
        @Override
        public void run(){
           try{
              dlm = new DefaultListModel();
              UL.setModel(dlm);
              PrintStream fluxEcriture1; 
              fluxEcriture1 = new PrintStream(this.C.getOutputStream());
              String m="IDS";
              String message="Friends"+this.IDClient+":"+m;
              System.out.println(message);
              fluxEcriture1.println(message);
              BufferedReader fluxLecture1;
              fluxLecture1 = new BufferedReader(new InputStreamReader(this.C.getInputStream())); 
              String msg= fluxLecture1.readLine();
              System.out.println(msg);
              String[] str= msg.split(";");
              if(str[0].equals("FriendsIDS")){
                 String[] IDCONTACTS = str[1].split(",");
                 for (int i=0;i < IDCONTACTS.length;i++){
                    dlm.addElement(IDCONTACTS[i]); 
                 }
              }
           }
           catch(Exception e){
               e.printStackTrace();
           }
        }
    }
    class preparerListIdNomStatusContact {
        Socket CC;
        String IDClient;
       
        public preparerListIdNomStatusContact(Socket s,String idc){
           this.CC=s;
           this.IDClient=idc;
           try{
                dlm = new DefaultListModel();
                UL.setModel(dlm);
                dlm1 = new DefaultListModel();
                UL1.setModel(dlm1);
                PrintStream fluxEcriture2; 
                fluxEcriture2 = new PrintStream(this.CC.getOutputStream()); 
                String m="ID/NOM/STATUS";
                String message="INS"+this.IDClient+":"+m;
                System.out.println(message);
                fluxEcriture2.println(message);
                BufferedReader fluxLecture2;
                fluxLecture2 = new BufferedReader(new InputStreamReader(this.CC.getInputStream())); 
                String msg1= fluxLecture2.readLine(); 
                System.out.println(msg1);
                String[] str= msg1.split(";");
                System.out.println(str);
                if(str[0].equals("FriendsIDNOMSTATUS")){
                  String[] FriendsINFO = str[1].split("/"); 
                  for (int i=0;i < FriendsINFO.length;i++){
                      String[] INFO=FriendsINFO[i].split(",");
                      dlm.addElement(INFO[0]);
                      dlm1.addElement(INFO[1]+":"+INFO[2]);
                  }
                }
             }
             catch(Exception e){
                  e.printStackTrace();
             }
        }   
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idlabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        sendTest = new javax.swing.JTextField();
        sendmsg = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        UL = new javax.swing.JList();
        AC = new javax.swing.JButton();
        DECONNECTER = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        UL1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 0));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel1.setText("HI CLIENT:");

        idlabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idlabel.setText("..........................................................................................................................................................");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idlabel))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 51, 255));

        jLabel3.setFont(new java.awt.Font("Levenim MT", 1, 18)); // NOI18N
        jLabel3.setText("CONTACT");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        msgBox.setColumns(20);
        msgBox.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        msgBox.setRows(5);
        jScrollPane2.setViewportView(msgBox);

        sendTest.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sendTest.setText("Tapper message");

        sendmsg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sendmsg.setText("send msg");
        sendmsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendmsgActionPerformed(evt);
            }
        });

        UL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UL.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ULValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(UL);

        AC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AC.setText("ajouter ami");
        AC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACActionPerformed(evt);
            }
        });

        DECONNECTER.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        DECONNECTER.setText("quitter");
        DECONNECTER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DECONNECTERActionPerformed(evt);
            }
        });

        UL1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UL1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                UL1ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(UL1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(sendTest)
                                .addGap(18, 18, 18)
                                .addComponent(sendmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(AC, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DECONNECTER, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DECONNECTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendTest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addContainerGap())
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

    private void ULValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ULValueChanged
        
        this.F.Ami=(String)UL.getSelectedValue() ;
        try{ 
          new RecupererMessageContact(this.F.Ami,this.id,this.nom,msgBox);
        }
        catch(Exception e){
          e.printStackTrace();
        }
    }//GEN-LAST:event_ULValueChanged

    private void sendmsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmsgActionPerformed
        try{
            String message=sendTest.getText(),m=message;
            String CI= this.F.Ami;
            new SendMessage(this.client,message,CI,this.id,this.nom,msgBox,sendTest);
        }
        catch(Exception e){
          e.printStackTrace();
        }
    }//GEN-LAST:event_sendmsgActionPerformed

    private void DECONNECTERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DECONNECTERActionPerformed
         String D="QUITTER" ;
         fluxEcriture.println(D);
         this.dispose();
         new Login(this.client).setVisible(true);
    }//GEN-LAST:event_DECONNECTERActionPerformed

    private void ACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACActionPerformed
        new AjouterContact(this.id,this.client).setVisible(true);
    }//GEN-LAST:event_ACActionPerformed

    private void UL1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_UL1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_UL1ValueChanged

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AC;
    private javax.swing.JButton DECONNECTER;
    private javax.swing.JList UL;
    private javax.swing.JList UL1;
    private javax.swing.JLabel idlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JTextField sendTest;
    private javax.swing.JButton sendmsg;
    // End of variables declaration//GEN-END:variables
}
