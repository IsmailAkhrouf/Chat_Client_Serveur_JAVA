
import java.sql.*;
import javax.swing.JOptionPane;

public class ABD {
    public static Connection Getconnect(){
      Connection cn=null;
      try{
        Class.forName("com.mysql.jdbc.Driver");
        cn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/appchat1","root","");
      }
      catch(Exception e){
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
      return cn;
    }
    public static int mise_ajour(String sql){
      int t=0;
      try{
       Connection cn= Getconnect();
       Statement st=cn.createStatement();
       t=st.executeUpdate(sql); 
      }
      catch(Exception e){
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
      return t;
    }
    public static ResultSet selection(String sql){
       ResultSet rs=null;
       try{
       Connection cn= Getconnect();
       Statement st=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
       rs=st.executeQuery(sql);
      }
      catch(Exception e){
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
       return rs;
    }
    public static int rechercher(String req,String cond){
       int i=0;
       try{
       ResultSet rs=selection(req);
       while(rs.next()){
           if(rs.getString(1).equals(cond)){
              i=1; 
              return i;
           }
       }
      }
      catch(Exception e){
          JOptionPane.showMessageDialog(null, e.getMessage());
      } 
       return i;
    }
}
