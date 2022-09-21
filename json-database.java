import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class JsonToDatabaseConv {
   public static Connection ConnectToDB() throws Exception {
      //Registering the Driver
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      //Getting the connection
      String mysqlUrl = "jdbc:mysql://localhost/____databasename____";// add the name of the database.
      Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
      System.out.println("Connection established......");
      return con;
   }
   public static void main(String args[]) {
      //Creating a JSONParser object
      JSONParser jsonParser = new JSONParser();
      try {
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/user_data.json"));
         //Retrieving the array
         JSONArray jsonArray = (JSONArray) jsonObject.get("user_data");
         Connection con = ConnectToDB();
         //Insert a row into the MyPlayers table
         PreparedStatement pstmt = con.prepareStatement("INSERT INTO MyUser values (?, ?, ?, ?, ?, ?, ?)");
         for(Object object : jsonArray) {
            JSONObject record = (JSONObject) object;
            int id = Integer.parseInt((String) record.get("ID"));
            String name = (String) record.get("Name");
            String role = (String) record.get("Role");
            Long telephone= (long) record.get("Telephone");
            String email = (String) record.get("Email");
            String username = (String) record.get("username");
            String password = (String) record.get("password");
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, role);
            pstmt.setLong(4, telephone);
            pstmt.setString(5, email);
            pstmt.setString(6, username);
            pstmt.setString(7, password);
            pstmt.executeUpdate();
         }  
         System.out.println("Records inserted.....");
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}