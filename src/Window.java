import javax.swing.*;

public class Window extends JFrame {

    public Window(){
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

    public void addVidgets(JPanel panel){
        JButton load_btn = new JButton("Загрузить");
        load_btn.setBounds(100, 520, 150, 30);
        panel.add(load_btn);

        JButton add_btn = new JButton("Добавить");
        add_btn.setBounds(300, 520, 150, 30);
        panel.add(add_btn);

        JButton remove_btn = new JButton("Удалить");
        remove_btn.setBounds(500, 520, 150, 30);
        panel.add(remove_btn);
    }
}
