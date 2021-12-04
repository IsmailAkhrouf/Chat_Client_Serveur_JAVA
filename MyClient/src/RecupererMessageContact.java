import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;
import java.util.*;

public class RecupererMessageContact {
    String idAmi;
    String id;
    String nom;
    javax.swing.JTextArea msgBox;
    
    public RecupererMessageContact(String idami,String id,String nom,javax.swing.JTextArea msgBox){
        this.idAmi=idami;
        this.id=id;
        this.nom=nom;
        this.msgBox=msgBox;
          try{
             this.msgBox.setText("");
             String l; 
             BufferedReader br;
             br= new BufferedReader(new FileReader("C:\\Users\\dell\\Desktop\\Conversation\\"+""+this.nom+""+".csv")); 
             while((l=br.readLine())!=null){
                 String[] Data= l.split(",");
                 if(this.idAmi.equals(Data[0]) && this.id.equals(Data[1])){
                    this.msgBox.append("<"+Data[0]+"--To--"+"me"+">"+Data[2]+"\n");  
                 }
                 if(this.id.equals(Data[0]) && this.idAmi.equals(Data[1])){
                    this.msgBox.append("<You--To--"+Data[1]+">"+Data[2]+"\n"); 
                 }
             }
          }
          catch(Exception e){
            e.printStackTrace();
          }
    }
}
