import java.io.*;
import java.util.*;
public class ExportFichierCSV {
    public String path;
    public String nomfichier;
    public String extention;
    
    public ExportFichierCSV(String path,String nomfichier){
        this.path=path;
        this.nomfichier=nomfichier;
        this.extention=".csv";
    }
    public void creerfichier() throws IOException {
        File f = new File(this.path+""+this.nomfichier+""+this.extention);
        f.createNewFile();
        
    }
}
