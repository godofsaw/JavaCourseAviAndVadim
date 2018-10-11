package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    private JPanel jPanelMain;
    private String userNum;
    private String userType;
    private String branchNum;
    private String userName ;
    private JPanel jPanelLeft;
    private JLabel jLabelEmpDetails;
    private JLabel jLabelUserNum;
    private JLabel jLabelUserName;
    private JLabel jLabelBranchNum;
    private JLabel jLabelUserType;
    private JButton jButtonStorage;
    private JButton jButtonChat;

    public MainMenu(String userNum,String userType,String branchNum,String userName){
        this.userName = userName;
        this.userType = userType;
        this.userNum = userNum;
        this.branchNum = branchNum;
    }

    public MainMenu(){
        this.userName = "";
        this.userType = "";
        this.userNum = "";
        this.branchNum = "";
    }

    protected void DrawMainMenu(){
        final int leftPad = 10;
        final int topPad = 15;
        final int topPadComp = 25;

        setTitle("Main Menu");
        setSize(1000,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanelLeft = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        jPanelLeft.setLayout(springLayout);

        jPanelLeft.setPreferredSize(new Dimension(300,400));

        jLabelEmpDetails = new JLabel("Employee Details");
        jLabelUserNum = new JLabel("Employee Number:"+userNum);
        jLabelUserName = new JLabel("Employee Name:"+userName);
        jLabelBranchNum = new JLabel("Employee Branch:"+branchNum);
        jLabelUserType = new JLabel("Employee Role:"+userType);

        jPanelLeft.add(jLabelUserNum);
        jPanelLeft.add(jLabelEmpDetails);
        jPanelLeft.add(jLabelUserName);
        jPanelLeft.add(jLabelBranchNum);
        jPanelLeft.add(jLabelUserType);

        springLayout.putConstraint(SpringLayout.WEST,jLabelEmpDetails,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelEmpDetails,topPad,SpringLayout.NORTH,jPanelLeft);
        springLayout.putConstraint(SpringLayout.WEST,jLabelUserNum,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelUserNum,topPadComp,SpringLayout.NORTH,jLabelEmpDetails);
        springLayout.putConstraint(SpringLayout.WEST,jLabelUserName,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelUserName,topPad,SpringLayout.NORTH,jLabelUserNum);
        springLayout.putConstraint(SpringLayout.WEST,jLabelBranchNum,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelBranchNum,topPad,SpringLayout.NORTH,jLabelUserName);
        springLayout.putConstraint(SpringLayout.WEST,jLabelUserType,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jLabelUserType,topPad,SpringLayout.NORTH,jLabelBranchNum);

        jButtonStorage = new JButton("Branch Storage");

        jPanelLeft.add(jButtonStorage);

        springLayout.putConstraint(SpringLayout.WEST,jButtonStorage,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonStorage,topPadComp,SpringLayout.NORTH,jLabelUserType);

        jButtonStorage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Storage storage = new Storage(branchNum);
                storage.DrawStorage();
                storage.setVisible(true);
                storage.setLocationRelativeTo(null);
            }
        });

        jButtonChat = new JButton("Chat");

        jPanelLeft.add(jButtonChat);

        springLayout.putConstraint(SpringLayout.WEST,jButtonChat,leftPad,SpringLayout.WEST,jPanelLeft);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonChat,topPadComp,SpringLayout.NORTH,jButtonStorage);

        jPanelMain = new JPanel();

        jPanelMain.add(jPanelLeft);

        setContentPane(jPanelMain);
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu("","","","");
        mainMenu.DrawMainMenu();
        mainMenu.setVisible(true);
        mainMenu.setLocationRelativeTo(null);
    }

    public JPanel getjPanelMain() {
        return jPanelMain;
    }

    public void setjPanelMain(JPanel jPanelMain) {
        this.jPanelMain = jPanelMain;
    }

    public JPanel getjPanelLeft() {
        return jPanelLeft;
    }

    public void setjPanelLeft(JPanel jPanelLeft) {
        this.jPanelLeft = jPanelLeft;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(String branchNum) {
        this.branchNum = branchNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public JLabel getjLabelEmpDetails() {
        return jLabelEmpDetails;
    }

    public void setjLabelEmpDetails(JLabel jLabelEmpDetails) {
        this.jLabelEmpDetails = jLabelEmpDetails;
    }

    public JLabel getjLabelUserNum() {
        return jLabelUserNum;
    }

    public void setjLabelUserNum(JLabel jLabelUserNum) {
        this.jLabelUserNum = jLabelUserNum;
    }

    public JLabel getjLabelUserName() {
        return jLabelUserName;
    }

    public void setjLabelUserName(JLabel jLabelUserName) {
        this.jLabelUserName = jLabelUserName;
    }

    public JLabel getjLabelBranchNum() {
        return jLabelBranchNum;
    }

    public void setjLabelBranchNum(JLabel jLabelBranchNum) {
        this.jLabelBranchNum = jLabelBranchNum;
    }

    public JLabel getjLabelUserType() {
        return jLabelUserType;
    }

    public void setjLabelUserType(JLabel jLabelUserType) {
        this.jLabelUserType = jLabelUserType;
    }

    public JButton getjButtonStorage() {
        return jButtonStorage;
    }

    public void setjButtonStorage(JButton jButtonStorage) {
        this.jButtonStorage = jButtonStorage;
    }

    public JButton getjButtonChat() {
        return jButtonChat;
    }

    public void setjButtonChat(JButton jButtonChat) {
        this.jButtonChat = jButtonChat;
    }
}
