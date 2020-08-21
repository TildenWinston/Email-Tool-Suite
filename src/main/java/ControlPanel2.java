import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ControlPanel2 extends JFrame{
    private JFrame frame;
    private JPanel panel1;
    private JButton countButton;
    private JButton sizeButton;
    private JButton autoReaderButton;
    private JTextField daysTextField;
    private JComboBox statusComboBox;
    private JTextArea emailsTextArea;
    private JTextArea queriesTextArea;
    private JButton saveChangesButton;
    private JLabel title;
    private JLabel statusLabel;
    private JLabel daysLabel;
    private JLabel emailsLabel;
    private JLabel queriesLabel;

    public ControlPanel2() throws IOException {
        try {
            doTheThings();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        countButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Count");
//
//            }
//        });
//        sizeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Size");
//            }
//        });
//        autoReaderButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("AutoRead");
//            }
//        });
//        saveChangesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Save");
//                System.out.println(a90TextField.getText());
//                a90TextField.setText("a");
//                title.setText("New stuff");
//            }
//        });
    }

    public void doTheThings() throws IOException, InterruptedException {

        frame = new JFrame("Email Tools Control Panel");
        //frame.setBounds(100, 100, 730, 489);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        //frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        title = new JLabel("Email Tools Control Panel");
        title.setMaximumSize(new Dimension(300, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        //title.setAlignmentX(CENTER_ALIGNMENT);

        frame.getContentPane().add(title);

        statusLabel = new JLabel("Type of emails affected: ");
        statusLabel.setMaximumSize(new Dimension(300, 30));
        //statusLabel.setAlignmentX(LEFT_ALIGNMENT);
        statusLabel.setHorizontalAlignment(JLabel.LEFT);
        frame.getContentPane().add(statusLabel);

        statusComboBox = new JComboBox<>();
        statusComboBox.addItem("Unread");
        statusComboBox.addItem("Read");
        statusComboBox.addItem("Both");
        statusComboBox.setMaximumSize(new Dimension(100, 30));
        statusComboBox.setSelectedItem("Both");


        frame.getContentPane().add(statusComboBox);

        daysLabel = new JLabel("Number of days:");



        frame.setVisible(true);

        //title.setText("Another title");









//        File file = new File(".\\src\\main\\resources\\input.txt");
//
//        BufferedReader br = new BufferedReader(new FileReader(file));
//
//        String platform = br.readLine();
//        Long timeBefore = Long.parseLong(br.readLine());
//        int days = ((int)(long)timeBefore) / 86400000;
//        System.out.println(a90TextField.getText());
//        a90TextField.setText("a");
//
//        //frame.paintImmediately(a90TextField.getVisibleRect());
//        frame.revalidate();
//        a90TextField.getText();
//        //setFieldVal("69");
//        textArea2.setText("Hello this is longer");
//       // textArea2.setText("Hello this is longer");
//        noreplyYoutubeComTextArea.setText("aaaa");
//
//        title.setText("New stuff");
//
//
//        frame.repaint();
//
//        br.readLine();
//
//        //unread
//        br.readLine();
//        String readOrUnread = br.readLine();
//
//        //Addresses
//        br.readLine();
//        br.readLine();
//
//        ArrayList<String> searchQueries = new ArrayList<>();
//
//        String tempAdds = "";
//        tempAdds = br.readLine();
//        while(!tempAdds.equals("")){
//            tempAdds = br.readLine();
//        }
//
//        //Search strings
//        br.readLine();
//        String tempSearch;
//        tempSearch = br.readLine();
//        while(!tempSearch.equals("")){
//            searchQueries.add(tempSearch);
//            tempSearch= br.readLine();
//        }
//
//        br.close();

        //   title.paintImmediately(title.getVisibleRect());

    }

    public static void main(String[] args) {

        try {
            ControlPanel2 form = new ControlPanel2();
            form.frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hi");
    }

}
