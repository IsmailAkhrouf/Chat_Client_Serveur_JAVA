import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClientConx extends Thread{
    Socket Client;
    String ID;
    public ClientConx(String id,Socket s){
        this.Client=s;
        this.ID=id;
        super.start(); 
    }
    @Override
    public void run(){
        try {
            PrintStream fluxEcriture ;
            fluxEcriture = new PrintStream(this.Client.getOutputStream());
            fluxEcriture.println(this.ID);
          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
