import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ControlPanel {
    private JPanel panel1;
    private JButton countButton;
    private JButton sizeButton;
    private JButton autoReaderButton;
    private JTextField a90TextField;
    private JComboBox comboBox1;
    private JTextArea noreplyYoutubeComTextArea;
    private JTextArea textArea2;
    private JButton saveChangesButton;
    private JLabel title;

    public ControlPanel() {
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Count");

            }
        });
        sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Size");
            }
        });
        autoReaderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("AutoRead");
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
                System.out.println(a90TextField.getText());
                a90TextField.setText("a");
                title.setText("New stuff");

            }
        });
    }

    public void setFieldVal(String val){
        a90TextField.setText(val);

    }

    public void doTheThings() throws IOException {

        JFrame frame = new JFrame("ControlPanel");
        frame.setContentPane(new ControlPanel().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        title = new JLabel("hello");

        title.setText("hello");

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

    public static void main(String[] args) throws IOException {
        new ControlPanel().doTheThings();
        System.out.println("Hi");
    }

}
