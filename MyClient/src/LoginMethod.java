import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LoginMethod {
    static String info;
    static String info1;
    Socket s;
    BufferedReader fluxLecture3;
    String ID;
    String NOM;
    
    public LoginMethod(Socket s,String id,String nom){
      this.s=s;  
      this.ID=id;
      this.NOM=nom;
       try{
        ClientConx c =new ClientConx(id,this.s);
        fluxLecture3 = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        String rep= fluxLecture3.readLine();
        if(rep.equals("trouverBDD")){
          try {
                new Client(id,s,nom).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        if(rep.equals("nontrouverBDD")){
          JOptionPane.showMessageDialog(null,"Not Found In BDD");  
        }  
       }
       catch(Exception e){
          e.printStackTrace();
       }
    }
}
