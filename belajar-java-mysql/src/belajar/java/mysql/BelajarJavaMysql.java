package belajar.java.mysql;

// import bumbu bumbu yang di perlukan
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

// ini juga bisa
// import java.sql.*;

public class BelajarJavaMysql {
    
    // Menyiapkan objek yang di perlukan untuk mengelola database
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    
    public static void main(String[] args) {
        // Melakukan koneksi ke database
        // harus di bungkus dalam try/catch
        
        try{
            // register driver yang akan di pakai
            Class.forName("com.mysql.jdbc.Driver");
            
            // buat koneksi ke database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan","root","");
            
            // buat objek statement
            stmt = conn.createStatement();
            JOptionPane.showMessageDialog(null, "Berhasil Koneksi");
            
            // buat query ke database
            String sql = "SELECT * FROM buku";
            
            // eksekusi query dan simpan hasilnya di obj ResultSet
            rs = stmt.executeQuery(sql);
            
            // tampil hasil query
            while(rs.next()){
                System.out.println("ID buku   : " + rs.getInt("id_buku"));
                System.out.println("judul     : " + rs.getString("judul"));
                System.out.println("pengarang : " + rs.getString("pengarang") + "\n");
            }
            
            stmt.close();
            conn.close();
            
        }catch(Exception e){
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal Koneksi");
        }
    }
    
}
