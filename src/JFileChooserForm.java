import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class JFileChooserForm extends JFrame{
    private JButton button1;
    private JPanel panel1;

    public JFileChooserForm(){
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,300);
        button1.addActionListener(e -> {
            JFileChooser fc = new JFileChooser(".");
            fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
            int vysledek = fc.showOpenDialog(this);
            if (vysledek == JFileChooser.APPROVE_OPTION) {
                File zvolenySoubor = fc.getSelectedFile();
                StringBuilder content = new StringBuilder();
                try(Scanner sc = new Scanner(new BufferedReader(new FileReader(zvolenySoubor)))){
                    String line;
                    while (sc.hasNextLine()) {
                        content.append(sc.nextLine()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, content.toString());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFileChooserForm j = new JFileChooserForm();
        j.setVisible(true);
    }
}
