package atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static InputStreamReader reader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(reader);
    static int saldo;
    
    public static void main(String[] args) throws IOException{
        try {
            Statement stmt;
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")) {
                stmt = conn.createStatement();
                show();
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void show() throws IOException{
        while(true){
            //clearScreen();
            System.out.println("\n\n+-------------------------------+");
            System.out.println("|          PROGRAM ATM          |");
            System.out.println("+-------------------------------+");
            System.out.println("   1. Daftar nasabah");
            System.out.println("   2. Ubah no_rekening");
            System.out.println("   3. Menabung");
            System.out.println("   4. Ambil Tunai");
            System.out.println("   5. Cek daftar nasabah");
            System.out.println("   6. Cari nasabah");
            System.out.println("   7. Hapus nasabah");
            System.out.println("   8. Keluar");
            System.out.println("+-------------------------------+\n");
            System.out.print("   Pilih --> ");
            
            try{
                int pilih = Integer.parseInt(input.readLine());
            
                switch(pilih){
                    case 1 :
                        daftar();
                    break;
                    case 2 :
                        ubah();
                    break;
                    case 3 :
                        nabung();
                    break;
                    case 4 :
                        ambil();
                    break;
                    case 5 :
                        cek();
                    break;
                    case 6 :
                        cari();
                    break;
                    case 7 :
                        hapus();
                    break;
                    case 8 :
                        System.exit(0);
                    break;
                    default:
                        System.out.println("pilihan anda tidak ada!!!");
                }
            }catch(IOException | NumberFormatException e){
                System.out.println(e);
            }
            
//            System.out.print("\n\nMasukan sembarang angka untuk kembali ke menu utama : ");
//            int kembali = Integer.parseInt(input.readLine());
        }
    }
    
    private static void ubah() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("no_rekening yang mau di ubah : ");
            int no_rekening = Integer.parseInt(input.readLine());
            System.out.print("no_rekening baru             : ");
            int no_rekening1 = Integer.parseInt(input.readLine());
            
            conn.createStatement().execute("UPDATE nasabah SET no_rekening='"+no_rekening1+"' WHERE no_rekening='"+no_rekening+"'");
            System.out.println("no_rekening berhasil di ubah.....");
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void hapus() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("no_rekening  : ");
            int no_rekening = Integer.parseInt(input.readLine());
            
            conn.createStatement().execute("DELETE FROM nasabah WHERE no_rekening='"+no_rekening+"'");
            System.out.println("Data berhasil di hapus.....");
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void cari() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("no_rekening  : ");
            int no_rekening = Integer.parseInt(input.readLine());
            
            ResultSet res = conn.createStatement().executeQuery("SELECT * FROM nasabah WHERE no_rekening='"+no_rekening+"'");
            
            System.out.println("\n+----------------------------------------------------------------------+");
            System.out.println("|        no_rekening        |        nama        |        saldo        |");
            System.out.println("+----------------------------------------------------------------------+");
            while(res.next()){
                no_rekening = res.getInt("no_rekening");
                String nama = res.getString("nama");
                saldo = res.getInt("saldo");
                
                System.out.printf(String.format("         %d                 %s              Rp.%d         \n",no_rekening,nama,saldo));
            }
            System.out.println("|                           |                    |                     |");
            System.out.println("+----------------------------------------------------------------------+");
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void ambil() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("No_rekening   : ");
            int no_rekening = Integer.parseInt(input.readLine());
            System.out.print("Tarik tunai Rp.");
            int saldo1 = Integer.parseInt(input.readLine());
            
            if(saldo >= saldo1){
                saldo = (saldo - saldo1);
                conn.createStatement().execute("UPDATE nasabah SET saldo='"+saldo+"' WHERE no_rekening='"+no_rekening+"'");
            }else{
                System.out.println("Uang anda tidak cukup!!!");
            }
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void nabung() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("No_rekening    : ");
            int no_rekening = Integer.parseInt(input.readLine());
            System.out.print("Tambah saldo Rp.");
            int saldo1 = Integer.parseInt(input.readLine());
            saldo = (saldo + saldo1);
            
            conn.createStatement().execute("UPDATE nasabah SET saldo='"+saldo+"' WHERE no_rekening='"+no_rekening+"'");
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void daftar() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            System.out.print("no_rekening  : ");
            int no_rekening = Integer.parseInt(input.readLine());
            System.out.print("nama         : ");
            String nama = input.readLine().trim();
            System.out.print("saldo awal Rp.");
            saldo = Integer.parseInt(input.readLine());
            
            conn.createStatement().execute("INSERT INTO nasabah values " + "('"+no_rekening+"','"+nama+"','"+saldo+"')");
   
        }catch(IOException | NumberFormatException | SQLException e){
            System.out.println(e);
        }
    }
    
    private static void cek() throws IOException{
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/atm","root","")){
            ResultSet res = conn.createStatement().executeQuery("SELECT * FROM nasabah");
            
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|        no_rekening        |        nama        |        saldo        |");
            System.out.println("+----------------------------------------------------------------------+");
            while(res.next()){
                int no_rekening = res.getInt("no_rekening");
                String nama = res.getString("nama");
                saldo = res.getInt("saldo");
                
                System.out.printf(String.format("         %d                 %s              Rp.%d         \n",no_rekening,nama,saldo));
            }
            System.out.println("|                           |                    |                     |");
            System.out.println("+----------------------------------------------------------------------+");
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    private static void clearScreen() throws IOException{
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex){
            System.err.println("tidak bisa clear screen");
        }
    }
    
}