import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.net.Socket;
public class Extraire_Message_Bd_To_Csv {
    String id;
    String nom;
    ExportFichierCSV e;
    public Extraire_Message_Bd_To_Csv(String ID,String NOM) throws SQLException{
        this.id=ID;
        this.nom=NOM;
         try {
            e=new ExportFichierCSV("C:\\Users\\dell\\Desktop\\Conversation\\",this.nom);
            e.creerfichier();
            String del=",";
            String sep="\n";
            FileWriter file=new FileWriter("C:\\Users\\dell\\Desktop\\Conversation\\"+""+this.nom+""+".csv",true);
            ResultSet rs= ABD.selection("select IDSend,IDReceve,Message from "
                          + "conversation where IDReceve='"+this.id+"'");
            while(rs.next()){
                     file.append(rs.getString(1));
                     file.append(del);
                     file.append(rs.getString(2));
                     file.append(del);
                     file.append(rs.getString(3));
                     file.append(sep);  
            }
            file.close();
            int nl4=ABD.mise_ajour("delete from conversation where IDReceve=('"+this.id+"')");
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
    }     
}
