import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cat implements SQLObject{
    int id;
    String name;
    int type_id;
    int age;
    double weight;

    public Cat(String name, int type_id, int age, double weight) {
        this.id = -1;
        this.name = name;
        this.type_id = type_id;
        this.age = age;
        this.weight = weight;
    }

    public Cat(ResultSet result) {
        try {
            id = result.getInt("id");
            name = result.getString("name");
            type_id = result.getInt("type_id");
            age = result.getInt("age");
            weight = result.getDouble("weight");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static public Cat create_random_cat(){
        int types_count = 61;
        int name_i = (int) (Math.random() * Const.names.length);
        int type_id = (int) (Math.random() * types_count);
        int age = (int) (Math.random() * 20);
        double weight =  Math.round(Math.random() * 3000) / 100.0;
        String name = Const.names[name_i];
        return new Cat(name, type_id, age, weight);
    }

    public Object[] toArray(){
        return new Object[]{id, name, type_id, age, weight};
    }

    public void updateWithArray(Object[] arr){
        id = (int) arr[0];
        name = (String) arr[1];
        type_id = (int) arr[2];
        age = (int) arr[3];
        weight = (double) arr[4];
    }

    static public Cat get_for_id(int id, Statement stmt){
        String query = "SELECT * FROM cats "+
                "WHERE id = " + id;
        try {
            ResultSet res = stmt.executeQuery(query);
            return new Cat(res);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type_id=" + type_id +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }

    @Override
    public void add_to_DB(Statement stmt) {
        String query = "INSERT INTO 'cats' ('name', 'type_id', 'age', weight)"+
                "VALUES ('"+name+"', '"+type_id+"', '"+
                age+"', '"+weight+"')";
        //System.out.println(query);
        try {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete_from_DB(Statement stmt) {
        this.delete_from_DB(this.id, "cats", stmt);
    }

    @Override
    public void update_in_DB(Statement stmt) {
        String query = "UPDATE 'cats' "+
                "SET name='" + name + "', type_id=" + type_id + ", "+
                "age=" + age + ", weight=" + weight + " " +
                "WHERE id=" + id;
        //System.out.println(query);
        try {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
