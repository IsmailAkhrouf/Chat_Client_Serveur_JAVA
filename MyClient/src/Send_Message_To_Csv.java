
import java.io.*;
import java.util.*;

public class Send_Message_To_Csv {
    String nom;
    String IDSEND;
    String IDRECEVE;
    String MESSAGE;
    public Send_Message_To_Csv(String nom,String IDS,String IDR,String MSG){
       this.nom=nom;
       this.IDSEND=IDS;
       this.IDRECEVE=IDR;
       this.MESSAGE=MSG;
       try{
            String del=",";
            String sep="\n";
            FileWriter file=new FileWriter("C:\\Users\\dell\\Desktop\\Conversation\\"+""+this.nom+""+".csv",true);  
            file.append(this.IDSEND);
            file.append(del);
            file.append(this.IDRECEVE);
            file.append(del);
            file.append(this.MESSAGE);
            file.append(sep);
            file.close();
       }
       catch (IOException ex) {
            ex.printStackTrace();
       }
    }
}
