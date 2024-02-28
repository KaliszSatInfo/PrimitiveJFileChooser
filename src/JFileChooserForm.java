import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class JFileChooserForm extends JFrame {
    private JPanel panel;
    private JTextArea txt;
    private File selectedFile;

    public JFileChooserForm() {
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);

        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);

        JMenuItem loadItem = new JMenuItem("Load");
        jMenuBar.add(loadItem);
        loadItem.addActionListener(e -> ChooseFile());

        JMenuItem saveItem = new JMenuItem("Save");
        jMenuBar.add(saveItem);
        saveItem.addActionListener(e -> SaveToFile(selectedFile));

        JMenuItem saveAsItem = new JMenuItem("Save As");
        jMenuBar.add(saveAsItem);
        saveAsItem.addActionListener(e -> SaveAs());
    }

    public void ChooseFile() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))) {
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                txt.setText(String.valueOf(content));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "File not found: " + e.getLocalizedMessage());
            }
        }
    }

    public void SaveToFile(File file) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.write(txt.getText());
            JOptionPane.showMessageDialog(this, "File saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problem with writing into the file: " + e.getLocalizedMessage());
        }
    }

    public void SaveAs() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            SaveToFile(selectedFile);
        }
    }

    public static void main(String[] args) {
        JFileChooserForm j = new JFileChooserForm();
        j.setVisible(true);
    }
}