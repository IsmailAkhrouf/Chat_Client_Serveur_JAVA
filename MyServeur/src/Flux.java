import java.io.*; 
import java.net.*;
//un mot sur import 
public class Flux extends Thread{
  protected BufferedReader fluxLecture = null;
  protected PrintStream fluxEcriture = null;
  protected String msg ;
  public Flux( InputStream fl, OutputStream fe){
    fluxLecture = new BufferedReader(new InputStreamReader(fl));
    fluxEcriture = new PrintStream(fe);
    this.msg = null;
    super.start();
  }
  public void run() {
	     while(true){
          try{
            this.msg = fluxLecture.readLine();
            if(this.msg != null){
              fluxEcriture.println(""+ this.msg);
            }
          }catch(IOException e){
            System.err.println(e);
          }
      }
	} 
}
