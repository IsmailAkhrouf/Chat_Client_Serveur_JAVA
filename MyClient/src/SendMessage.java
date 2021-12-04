import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;
import java.util.*;

public class SendMessage {
    Socket C;
    String msg;
    String idAmi;
    String id;
    String nom;
    javax.swing.JTextArea msgBox;
    javax.swing.JTextField sendTest;
    PrintStream fluxEcriture;
    
    public SendMessage(Socket s,String msg,String idami,String id,String nom,javax.swing.JTextArea msgBox,javax.swing.JTextField sendTest){
        this.C=s;
        this.msg=msg;
        this.idAmi=idami;
        this.id=id;
        this.nom=nom;
        this.msgBox=msgBox;
        this.sendTest=sendTest;
        try{
            fluxEcriture = new PrintStream(this.C.getOutputStream()); 
            String message=this.msg,m=message;
            String CI= this.idAmi;
             if(!this.idAmi.isEmpty()){
                message="Message"+CI+":"+m;
                fluxEcriture.println(message);
                this.sendTest.setText("");
                this.msgBox.append("<You--To--"+CI+">"+m+"\n");
                new Send_Message_To_Csv(this.nom,this.id,CI,m);
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"User does not exist anymore"); 
        }
    }
}
