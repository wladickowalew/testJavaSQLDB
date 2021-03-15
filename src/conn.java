import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        int types_count = 61;
        for (int i = 0; i < 10000; i++) {
            int name_i = (int) (Math.random() * Const.names.length);
            int type_id = (int) (Math.random() * types_count);
            int age = (int) (Math.random() * 20);
            double weight =  Math.round(Math.random() * 3000) / 100.0;
            String name = Const.names[name_i];
            String query = "INSERT INTO 'cats' ('name', 'type_id', 'age', weight)"+
                           "VALUES ('"+name+"', '"+type_id+"', '"+
                            age+"', '"+weight+"')";
            //System.out.println(query);
            statmt.execute(query);
        }
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        Scanner sc = new Scanner(System.in);
        String name1 = sc.nextLine();
        String query = "SELECT * FROM cats "+
                       "WHERE name =' "+name1+"'"+
                       "and age > 15 and weight < 5";
        resSet = statmt.executeQuery(query);
        int j = 0;
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            int type = resSet.getInt("type_id");
            int age = resSet.getInt("age");
            double weight = resSet.getDouble("weight");
            System.out.println(name+" "+age+" "+weight);
            System.out.println();
            j++;
        }
        System.out.println("total: " + j);
        resSet.close();
        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();

        System.out.println("Соединения закрыты");
    }

}
