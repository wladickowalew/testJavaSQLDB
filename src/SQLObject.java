import java.sql.SQLException;
import java.sql.Statement;

public interface SQLObject {
    public void add_to_DB(Statement stmt);
    default public void delete_from_DB(int id, String table, Statement stmt){
        String query = "DELETE FROM '"+table+"' "+
                       "WHERE id = " + id;
        try {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    };
    public void update_in_DB(Statement stmt);
}
