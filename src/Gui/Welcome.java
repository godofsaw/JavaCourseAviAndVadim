package Gui;

import org.apache.log4j.BasicConfigurator;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Welcome extends JFrame {
    static Logger logger = Logger.getLogger(Welcome.class.getName());
    public Welcome(){

    }

    public static void main(String[] args) throws IOException {
        Welcome welcome = new Welcome();
        welcome.DrawWelcome();
        welcome.setVisible(true);
        welcome.setLocationRelativeTo(null);

        Properties properties = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = Welcome.class.getClassLoader().getResourceAsStream(propFileName);
        properties.load(inputStream);
        String logPath = properties.getProperty("LogPath");

      /*  String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.xml";
        DOMConfigurator.configure(log4jConfigFile);*/

        //BasicConfigurator.configure();

        FileHandler fh;
        fh = new FileHandler(logPath);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        // the following statement is used to log any messages
        logger.info("My first log");

       /* File logFile=new File(logPath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));

        writer.write ("Application Started");
        writer.close();*/
    }

    private void DrawWelcome(){
        setTitle("Welcome");
        setSize(300,300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        JButton jButton = new JButton();

        jButton.setText("Log in");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login =new Login();
                login.DrawLogin();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });

        jPanel.add(jButton);
        add(jPanel);
    }
}
