import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cat implements SQLObject{
    int id;
    String name;
    int type_id;
    int age;
    double weight;

    public Cat(int id, String name, int type_id, int age, double weight) {
        this.id = id;
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

    }

    public void delete_from_DB(Statement stmt) {
        this.delete_from_DB(this.id, "cats", stmt);
    }

    @Override
    public void update_in_DB(Statement stmt) {

    }
}
