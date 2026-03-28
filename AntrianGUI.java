import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class AntrianGUI extends JFrame {

    Queue<String[]> queue = new LinkedList<>();
    int nomor = 1;

    JTextField inputNama;
    DefaultListModel<String> listModel;
    JList<String> listAntrian;

    public AntrianGUI() {
        setTitle("Queue Simulation");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // input
        add(new JLabel("Name"));
        inputNama = new JTextField(15);
        add(inputNama);

        // tombol ambil
        JButton btnAmbil = new JButton("Get Queue Number");
        add(btnAmbil);

        // tombol panggil
        JButton btnPanggil = new JButton("Call Next");
        add(btnPanggil);

        // list
        listModel = new DefaultListModel<>();
        listAntrian = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listAntrian);
        scrollPane.setPreferredSize(new Dimension(250, 200));
        add(scrollPane);

        // ambil antrian
        btnAmbil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nama = inputNama.getText();

                if (nama.equals("")) return;

                String[] data = {String.valueOf(nomor), nama};
                queue.add(data);
                nomor++;

                inputNama.setText("");
                updateList();
            }
        });

        // panggil antrian
        btnPanggil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (queue.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Queue is empty");
                    return;
                }

                String[] data = queue.poll();
                String teks = "Queue number " + data[0] + ", " + data[1] + ", please proceed to the counter";

                JOptionPane.showMessageDialog(null, teks);

                speak(teks);

                updateList();
            }
        });
    }

    public void updateList() {
        listModel.clear();
        for (String[] data : queue) {
            listModel.addElement(data[0] + " - " + data[1]);
        }
    }

    public void speak(String text) {
        try {
            String safeText = text.replace("'", "");

            String command = "PowerShell -Command \"Add-Type -AssemblyName System.Speech; " +
                    "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                    "$speak.Speak('" + safeText + "');\"";

            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AntrianGUI().setVisible(true);
    }
}