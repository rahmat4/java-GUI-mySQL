package belajar.java.mysql;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class javaCRUD {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader reader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(reader);
    
    public static void main(String[] args){
        try{
            // register drivaer
            Class.forName(JDBC_DRIVER);
            
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            JOptionPane.showMessageDialog(null, "Berhasil Koneksi");
            while(!conn.isClosed()){
                showMenu();
            }
            stmt.close();
            conn.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Koneksi");
            
        }
        
    }
    static void showMenu(){
        System.out.println("\n======== MENU UTAMA ========");
        System.out.println(" 1. Insert Data");
        System.out.println(" 2. Show Data");
        System.out.println(" 3. Edit Data");
        System.out.println(" 4. Delete Data");
        System.out.println(" 0. keluar");
        System.out.println("");
        System.out.print("Pilihan -> ");
        
        try{
            int pilihan = Integer.parseInt(input.readLine());
            
            switch(pilihan){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertBuku();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateBuku();
                    break;
                case 4:
                    deleteBuku();
                    break;
                default:
                    System.out.println("pilihan anda tidak ada");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    static void showData(){
        String sql = "SELECT * FROM buku";
        
        try{
            rs = stmt.executeQuery(sql);
            
            System.out.println("+----------------------------+");
            System.out.println("|   DATA BUKU PERPUSTAKAAN   |");
            System.out.println("+----------------------------+");
        
            while(rs.next()){
                int idBuku = rs.getInt("id_buku");
                String judul = rs.getString("judul");
                String pengarang = rs.getString("pengarang");
            
                System.out.printf(String.format("%d. %s -- (%s)\n",idBuku, judul, pengarang));
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    static void insertBuku(){
        try{
            // ambil input dari user
            System.out.print("Judul     : ");
            String judul = input.readLine().trim();
            System.out.print("Pengarang : ");
            String pengarang = input.readLine().trim();
            
            // query simpan
            String sql = "INSERT INTO buku (judul, pengarang) VALUE('%s','%s')";
            sql = String.format(sql, judul, pengarang);
            
            // simpan dulu
            stmt.execute(sql);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    static void updateBuku(){
        // ambil input dari user
        try{
            System.out.print("ID yang mau di edit : ");
            int idBuku = Integer.parseInt(input.readLine());
            System.out.print("judul               : ");
            String judul = input.readLine().trim();
            System.out.print("pengarang           : ");
            String pengarang = input.readLine().trim();
            
            // query update
            String sql = "UPDATE buku SET judul='%s', pengarang='%s' WHERE id_buku=%d";
            sql = String.format(sql, judul, pengarang, idBuku);
            
            // update data buku
            stmt.execute(sql);
                    
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    static void deleteBuku(){
        try{
            // ambil input dari user
            System.out.print("ID yang mau di hapus : ");
            int idBuku = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM buku WHERE id_buku=%d",idBuku);
            
            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah di hapus......");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}