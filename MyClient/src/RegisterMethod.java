import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;
import java.util.*;
public class RegisterMethod {
    Socket s;
    String ID;
    String NOM;
    PrintStream fluxEcriture;
    BufferedReader fluxLecture4;       
    public RegisterMethod(Socket s,String id,String nom){
      this.s=s;  
      this.ID=id;
      this.NOM=nom;
      try{
        fluxEcriture = new PrintStream(this.s.getOutputStream()); 
        String status="deconnecte";
        String message="register"+this.ID+":"+this.NOM;
        fluxEcriture.println(message);
        fluxLecture4 = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        String rep1= fluxLecture4.readLine();
        if(rep1.equals("inscrit")){
            JOptionPane.showMessageDialog(null,"Client registreted successfuly");
        }
        if(rep1.equals("noninscrit")){
            JOptionPane.showMessageDialog(null,"Erreur! ");
        }
      }
      catch(Exception e){
          e.printStackTrace();
      }
    }
    
}
