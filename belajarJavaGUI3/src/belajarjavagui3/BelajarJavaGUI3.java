package belajarjavagui3;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BelajarJavaGUI3 {
    private static Connection MySQLConfig;
    public static Connection configDB() throws SQLException{
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //Class.forName("com.mysql.jdbc.Driver");
            MySQLConfig = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/perusahaan","root","");
            //JOptionPane.showMessageDialog(null, "Berhasil Koneksi");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Koneksi");
        }
        return MySQLConfig;
    }
}
