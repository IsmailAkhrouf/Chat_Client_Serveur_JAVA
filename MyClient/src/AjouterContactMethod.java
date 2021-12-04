import java.net.Socket;
import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;
import java.util.*;

public class AjouterContactMethod {
    Socket s;
    PrintStream fluxEcriture;
    BufferedReader fluxLecture4;
    String IDContact;
    String NomContact;
    
    public AjouterContactMethod(Socket s,String idc,String nomc){
       this.s=s;
       this.IDContact=idc;
       this.NomContact=nomc;
          try{
              fluxEcriture = new PrintStream(this.s.getOutputStream());
              String message="ajouter"+IDContact+":"+NomContact;
              fluxEcriture.println(message);
              fluxLecture4 = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
              String rep2= fluxLecture4.readLine();
              if(rep2.equals("succes")){
               JOptionPane.showMessageDialog(null,"Client Contact registreted successfuly");
              }
              if(rep2.equals("echec")){
                JOptionPane.showMessageDialog(null,"errour Client Contact not registreted ");
              }
          }
          catch(Exception e){
           e.printStackTrace();
          }
    }
}
