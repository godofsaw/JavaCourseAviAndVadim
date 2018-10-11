package Gui;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Login extends JFrame  {

    private String userNum = "";
    private String userPass = "";
    private String branchNum = "1";

    public Login(){
    }

    public static void main(String[] args) {
      Login login = new Login();
      login.DrawLogin();
      login.setVisible(true);
      login.setLocationRelativeTo(null);
    }

    protected void DrawLogin(){
        setTitle("Login");
        setSize(900,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanelData = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        jPanelData.setLayout(springLayout);

        JLabel jLabelUser = new JLabel("Employee Number:");
        JTextField jTextFieldUser = new JTextField(20);
        JLabel jLabelPass = new JLabel("Password :");
        JPasswordField jTextFieldPass = new JPasswordField(20);

        jPanelData.add(jLabelUser);
        jPanelData.add(jTextFieldUser);
        jPanelData.add(jLabelPass);
        jPanelData.add(jTextFieldPass);

        springLayout.putConstraint(SpringLayout.WEST,jLabelUser,100,SpringLayout.WEST,jPanelData);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelUser,50,SpringLayout.NORTH,jPanelData);
        springLayout.putConstraint(SpringLayout.WEST,jTextFieldUser,10,SpringLayout.EAST,jLabelUser);
        springLayout.putConstraint(SpringLayout.NORTH,jTextFieldUser,50,SpringLayout.NORTH,jPanelData);
        springLayout.putConstraint(SpringLayout.WEST,jLabelPass,10,SpringLayout.EAST,jTextFieldUser);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelPass,50,SpringLayout.NORTH,jPanelData);
        springLayout.putConstraint(SpringLayout.WEST,jTextFieldPass,10,SpringLayout.EAST,jLabelPass);
        springLayout.putConstraint(SpringLayout.NORTH,jTextFieldPass,50,SpringLayout.NORTH,jPanelData);

        jTextFieldUser.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                userNum = jTextFieldUser.getText();
            }
        });

        jTextFieldPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                userPass = jTextFieldPass.getPassword().toString();
            }
        });

        JComboBox<String> jComboBoxBranch = new JComboBox<String>(new String[]{"1","2"});

        jPanelData.add(jComboBoxBranch);

        springLayout.putConstraint(SpringLayout.WEST,jComboBoxBranch,450,SpringLayout.WEST,jPanelData);
        springLayout.putConstraint(SpringLayout.NORTH,jComboBoxBranch,100,SpringLayout.NORTH,jPanelData);

        jComboBoxBranch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                branchNum = (String) jComboBoxBranch.getSelectedItem();
            }
        });

        JButton jButtonLogin = new JButton("Log in");

        jPanelData.add(jButtonLogin);

        springLayout.putConstraint(SpringLayout.WEST,jButtonLogin,450,SpringLayout.WEST,jPanelData);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonLogin,200,SpringLayout.NORTH,jPanelData);

        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(LoginDataCorrect(userNum,userPass)){
                    try {
                        Socket socket = new Socket("127.0.0.1", 1345);
                        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("GuiName", "Log in");
                        jsonObject.put("user number", userNum);
                        jsonObject.put("user pass",userPass);
                        jsonObject.put("user branch",branchNum);

                        writer.write(jsonObject.toString() + "\n");
                        writer.flush();

                        String line = reader.readLine();
                        JSONObject jsonObjectResponse;
                        JSONParser jsonParser = new JSONParser();
                        jsonObjectResponse = (JSONObject) jsonParser.parse(line);
                        String response = (String) jsonObjectResponse.get("Failed");
                        String userType = (String) jsonObjectResponse.get("user type");
                        String userName = (String) jsonObjectResponse.get("user name");

                        if(!response.equals("")){
                            JOptionPane.showMessageDialog(null, response);
                        }
                        else{
                            switch (userType){
                                case "2":{

                                    MainMenuCashier mainMenuCashier = new MainMenuCashier(userNum,"Cashier",branchNum,userName);
                                    mainMenuCashier.DrawMainMenu();
                                    mainMenuCashier.setVisible(true);
                                    mainMenuCashier.setLocationRelativeTo(null);
                                    break;
                                }
                            }
                        }

                        socket.close();
                    }
                    catch (Exception ex){
                        System.out.print(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Your Employee number or Password is incorrect !!!");
                }
            }
        });

        add(jPanelData);
    }

    private static boolean CheckPassSyntax(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    private boolean LoginDataCorrect(String userNum, String userPass) {
        if(userNum.length()==5) {
            if (userNum.matches("[0-9]+")) {
                if(userPass.length()>=8 && userPass.length()<=15){
                    if(CheckPassSyntax(userPass)){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
