
import java.io.*; 
import java.net.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chikri
 */

public class Serveur extends javax.swing.JFrame {

    /**
     * Creates new form Serveur
     */
    ServerSocket serveur;
    HashMap clientcoll = new HashMap();
    PrintStream fluxEcriture1;
    public Serveur() {
        try{
           initComponents(); 
           serveur= new ServerSocket(14); 
           this.serveur_status.setText("Serveur Started");
           new ClientAccept().start(); 
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    class ClientAccept extends Thread {

        public void run() {
            while (true) {
                try {
                 Socket c = serveur.accept();
                 BufferedReader fluxLecture;
                 fluxLecture = new BufferedReader(new InputStreamReader(c.getInputStream()));
                 fluxEcriture1 = new PrintStream(c.getOutputStream());
                 String i= fluxLecture.readLine();
                 if(i.contains("register")){
                     i=i.substring(8);
                     StringTokenizer st=new StringTokenizer(i,":"); 
                     String idregister=st.nextToken();
                     i=st.nextToken();
                     String nomregister=i;
                     String status="deconnecte";
                     try{
                        int nl=ABD.mise_ajour("insert into client values ('"+idregister+"','"+nomregister+"','"+status+"') ");
                        if(nl!=0){
                         String message="inscrit";
                         fluxEcriture1.println(message);
                        } 
                    }
                    catch(Exception e){
                      String message1="noninscrit";
                      fluxEcriture1.println(message1);
                    }    
                }
                 String id=i;
                 int rs=ABD.rechercher("select ID from client", id);
                 if(rs!=0){
                    String msg="trouverBDD";
                    fluxEcriture1.println(msg);
                    String status="connecte";
                    int nl=ABD.mise_ajour("update client set Status = '"+status+"'"+" where ID= '"+id+"' ");
                    clientcoll.put(id,c);
                    int nl1=ABD.mise_ajour("insert into listeconnecte values ('"+id+"') ");
                    msgBox.append(id+":"+"JOINED!\n");
                    new MsgRead(c,id).start();
                 }
                 if(rs==0){
                    String msg1="nontrouverBDD"; 
                    fluxEcriture1.println(msg1);
                 }
                 
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    class MsgRead extends Thread {
        Socket s;
        String ID;
        private MsgRead(Socket c, String id) {
            this.s=c;
            this.ID=id;
        }
        @Override
        public void run(){
            while(!clientcoll.isEmpty()){
                try{
                 BufferedReader fluxLecture1;
                 fluxLecture1 = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
                 String i= fluxLecture1.readLine(); 
                 if(i.contains("Message")){
                     i=i.substring(7);
                     StringTokenizer st=new StringTokenizer(i,":"); 
                     String idreceve=st.nextToken();
                     i=st.nextToken();
                     if(clientcoll.containsKey(idreceve)){
                        try{
                          PrintStream fluxEcriture ;
                          fluxEcriture = new PrintStream(((Socket)clientcoll.get(idreceve)).getOutputStream()); 
                          String reponse="reponse"+this.ID+":"+i;
                          fluxEcriture.println(reponse);
                     }
                     catch (Exception ex) {
                      ex.printStackTrace();
                     } 
                     }else{
                         try{
        
                        int nl=ABD.mise_ajour("insert into conversation values ('"+this.ID+"','"+idreceve+"','"+i+"') ");
                        if(nl!=0){
                          msgBox.append("Message registreted successfuly!\n");
                        }
                    }
                    catch(Exception e){
                    msgBox.append("Message not registreted dans bdd!\n");
                    }
                     }
                     
                 }
                 else if(i.contains("Q")){
                     clientcoll.remove(this.ID);
                     msgBox.append(this.ID+":"+"REMOVED!\n");
                     String status1="deconnecte";
                     int nl2=ABD.mise_ajour("update client set Status = '"+status1+"'"+" where ID= '"+this.ID+"' ");
                     int nl3=ABD.mise_ajour("delete from listeconnecte where IDConnect= ('"+this.ID+"') ");
                  }
                 else if(i.contains("ajouter")){
                       i=i.substring(7);
                       StringTokenizer st=new StringTokenizer(i,":");
                       String IDContact=st.nextToken();
                       i=st.nextToken();
                       String NomContact=i;
                        try{
                           PrintStream fluxEcriture4 ; 
                           fluxEcriture4 = new PrintStream(s.getOutputStream());
                           int nl=ABD.mise_ajour("insert into contact values ('"+this.ID+"','"+IDContact+"','"+NomContact+"') ");
                           if(nl!=0){
                            String message="succes";
                            fluxEcriture4.println(message);
                           }
                           else{
                            String message="echec";
                            fluxEcriture4.println(message);  
                           }
                        }
                       catch (Exception ex) {
                         ex.printStackTrace();
                       }  
                  }
                   else if(i.contains("Friends")){
                       i=i.substring(7);
                       StringTokenizer st=new StringTokenizer(i,":");
                       String IdClient=st.nextToken();
                       System.out.println(IdClient);
                       i=st.nextToken(); 
                       try{
                           PrintStream fluxEcriture5; 
                           fluxEcriture5 = new PrintStream(s.getOutputStream());
                           String ids="";
                           ResultSet rs= ABD.selection("select IDContact from contact where IDClient='"+IdClient+"'");
                           while(rs.next()){
                            ids+=rs.getString(1)+",";
                           }
                           System.out.println(ids);
                           String m="FriendsIDS";
                           String message=m+";"+ids;
                           fluxEcriture5.println(message);
                       }
                       catch (Exception ex) {
                         ex.printStackTrace();
                       } 
                    }
                    else if(i.contains("INS")){
                       System.out.println(i);
                       i=i.substring(3);
                       StringTokenizer st=new StringTokenizer(i,":");
                       String IdClient=st.nextToken();
                       System.out.println(IdClient);
                       i=st.nextToken(); 
                       try{
                           PrintStream fluxEcriture6; 
                           fluxEcriture6 = new PrintStream(s.getOutputStream()); 
                           String IdNomStatus="";
                           ResultSet rs1=ABD.selection("select contact.IDContact,client.Nom,client.Status from client,contact where contact.IDClient='"+IdClient+"' AND contact.IDContact=client.ID");
                           while(rs1.next()){
                            IdNomStatus+=rs1.getString(1)+","+rs1.getString(2)+","+rs1.getString(3)+"/";
                           }
                           System.out.println(IdNomStatus);
                           String m1="FriendsIDNOMSTATUS";
                           String message1=m1+";"+IdNomStatus;
                           fluxEcriture6.println(message1);
                       }
                       catch (Exception ex) {
                         ex.printStackTrace();
                       } 
                     }
                   
                     System.out.println(i);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        serveur_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));

        msgBox.setColumns(20);
        msgBox.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jLabel1.setBackground(new java.awt.Color(255, 255, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Serveur Status:");

        serveur_status.setBackground(new java.awt.Color(204, 255, 255));
        serveur_status.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        serveur_status.setText(".........................................................");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(serveur_status)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(serveur_status, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Serveur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Serveur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Serveur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Serveur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Serveur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel serveur_status;
    // End of variables declaration//GEN-END:variables
}
