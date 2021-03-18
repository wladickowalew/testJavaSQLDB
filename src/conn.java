import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TestJava.db");
        statmt = conn.createStatement();
        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt.execute("CREATE TABLE if not exists 'types' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "'type' VARCHAR (100) NOT NULL)");
        statmt.execute("CREATE TABLE if not exists 'cats' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "'name' VARCHAR (20) NOT NULL, " +
                "'type_id' INTEGER NOT NULL REFERENCES types (id), " +
                "'age' INTEGER NOT NULL, " +
                "'weight' DOUBLE)");
        System.out.println("Таблица создана или уже существует.");
    }

    public static void WriteTypes() throws SQLException
    {
        for (String type: Const.types)
            statmt.execute("INSERT INTO 'types' ('type') VALUES ('"+type+"'); ");
        System.out.println("Таблица заполнена");
    }

    // --------Заполнение таблицы--------
    public static void addCats() throws SQLException
    {
        for (int i = 0; i < 100; i++){
            Cat cat = Cat.create_random_cat();
            cat.add_to_DB(statmt);
        }
    }

    // -------- Вывод таблицы--------
//    public static void ReadDB() throws ClassNotFoundException, SQLException
//    {
//        Scanner sc = new Scanner(System.in);
//        String name1 = sc.nextLine();
//        String query = "SELECT * FROM cats "+
//                       "WHERE name =' "+name1+"'"+
//                       "and age > 15 and weight < 5 LIMIT 10";
//        resSet = statmt.executeQuery(query);
//        int j = 0;
//        while(resSet.next())
//        {
//            Cat cat = new Cat(resSet);
//            System.out.println(cat);
//            j++;
//        }
//        System.out.println("total: " + j);
//        resSet.close();
//        System.out.println("Таблица выведена");
//    }

    public static ArrayList<Cat> ReadDB() throws SQLException
    {
        Scanner sc = new Scanner(System.in);
        String query = "SELECT * FROM cats "+
                "WHERE age > 15 and weight < 1 LIMIT 1000";
        resSet = statmt.executeQuery(query);
        ArrayList<Cat> cats = new ArrayList<>();
        while(resSet.next())
            cats.add(new Cat(resSet));
        resSet.close();
        return cats;
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();

        System.out.println("Соединения закрыты");
    }

}
