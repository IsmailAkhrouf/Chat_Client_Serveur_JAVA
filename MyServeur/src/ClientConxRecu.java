import java.io.*;
import java.net.*;

public class ClientConxRecu extends Thread{
    Socket client;
    protected String msg;
    public ClientConxRecu(Socket s){
        this.client=s;
        super.start(); 
        
    }

    
   
    @Override
    public void run(){
        try {
            BufferedReader fluxLecture;
            fluxLecture = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            msg= fluxLecture.readLine();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
