import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Window extends JFrame {

    DefaultTableModel model = new DefaultTableModel();
    ArrayList<Cat> cats;
    JTable table;

    public Window() throws SQLException {
        setTitle("Test JTable");
        setBounds(300, 300, 800, 600);
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
        JButton load_btn = new JButton("Загрузить");
        load_btn.setBounds(100, 520, 150, 30);
        panel.add(load_btn);

        JButton add_btn = new JButton("Добавить");
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cat cat = Cat.create_random_cat();
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
                model.removeRow(ind);
                cats.get(ind).delete_from_DB(conn.statmt);
                cats.remove(ind);
            }
        });
        panel.add(remove_btn);

        Object[] columns = new String[]{"id", "name", "type_id", "age", "weight"};
        cats = conn.ReadDB();
        Object[][] data = new Object[cats.size()][];
        for (int i = 0; i < cats.size(); i++)
            data[i] = cats.get(i).toArray();
        System.out.println(cats.size());
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        //table.setBounds(5, 5, 740, 500);
        JScrollPane tableWithScroll = new JScrollPane(table);
        tableWithScroll.setBounds(5, 5, 740, 500);
        panel.add(tableWithScroll);
    }
}
