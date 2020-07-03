package util;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;

public class MySQLConnectionTest {
   private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String URL = "jdbc:mysql://localhost:3306/ns";
   private static final String USER ="ns";
   private static final String PW = "1004";

   @Test
   public void testConnection() throws Exception{
      Class.forName(DRIVER);
      try (Connection con = DriverManager.getConnection(URL, USER, PW))   {
         System.out.println(con);
      }catch(Exception e){
         System.err.println(e);
      }
   }
}
