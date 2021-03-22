import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Window extends JFrame implements TableModelListener {
    Object[] columns = new String[]{"id", "name", "type_id", "age", "weight"};
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<Cat> cats;
    JTable table;
    JTextField queryTF;

    public Window() throws SQLException {
        setTitle("Test JTable");
        setBounds(300, 100, 800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        addVidgets(panel);
        getContentPane().add(panel);
    }

    public void run(){
        this.setVisible(true);
    }

    public void addVidgets(JPanel panel) throws SQLException {
        JLabel wherelbl= new JLabel("WHERE");
        wherelbl.setBounds(10, 565, 90, 40);
        wherelbl.setFont(new Font("Helvetica", 0, 20));
        panel.add(wherelbl);

        queryTF = new JTextField();
        queryTF.setBounds(100, 565, 600, 40);
        queryTF.setFont(new Font("Helvetica", 0, 20));
        panel.add(queryTF);

        JButton load_btn = new JButton("Загрузить");
        load_btn.setBounds(100, 520, 150, 30);
        load_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cats = conn.ReadDB(queryTF.getText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Object[][] data = new Object[cats.size()][];
                for (int i = 0; i < cats.size(); i++)
                    data[i] = cats.get(i).toArray();
                System.out.println(cats.size());
                model.setDataVector(data, columns);
            }
        });
        panel.add(load_btn);

        JButton add_btn = new JButton("Добавить");
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cat cat = Cat.create_random_cat();
                Cat cat = Cat.add_new_cat();
                cats.add(cat);
                model.addRow(cat.toArray());
            }
        });
        add_btn.setBounds(300, 520, 150, 30);
        panel.add(add_btn);

        JButton remove_btn = new JButton("Удалить");
        remove_btn.setBounds(500, 520, 150, 30);
        remove_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = table.getSelectedRow();
                if (ind == -1)
                    return;
                model.removeRow(ind);
                cats.get(ind).delete_from_DB(conn.statmt);
                cats.remove(ind);
            }
        });
        panel.add(remove_btn);
        Object[][] data = new Object[0][];
        model = new DefaultTableModel(data, columns);
        model.addTableModelListener(this);
        table = new JTable(model);
        JScrollPane tableWithScroll = new JScrollPane(table);
        tableWithScroll.setBounds(5, 5, 740, 500);
        panel.add(tableWithScroll);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("Table cell changed");
        int ind = table.getSelectedRow();
        if (ind == -1)
            return;
        Object[] cat_arr = model.getDataVector().elementAt(ind).toArray();
        Cat cat = cats.get(ind);
        cat.updateWithArray(cat_arr);
        cat.update_in_DB(conn.statmt);
    }
}
