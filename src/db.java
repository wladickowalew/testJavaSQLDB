import java.sql.SQLException;

public class db {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        conn.Conn();
        Window window = new Window();
        window.run();
//        Cat cat = Cat.get_for_id(2, conn.statmt);
//        System.out.println(cat);
//        cat.name = "Суета";
//        System.out.println(cat);
//        cat.update_in_DB(conn.statmt);
        //cat.delete_from_DB(conn.statmt);
        //conn.CreateDB();
        //conn.WriteTypes();
        //conn.addCats();
        //conn.ReadDB();
        //conn.CloseDB();
    }
}