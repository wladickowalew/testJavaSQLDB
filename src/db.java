import java.sql.SQLException;

public class db {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        conn.Conn();
        conn.CreateDB();
        //conn.WriteTypes();
        conn.ReadDB();
        conn.CloseDB();
    }
}