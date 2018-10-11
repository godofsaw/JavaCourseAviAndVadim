package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuCashier extends MainMenu{

    private JPanel jPanelMid;
    private JButton jButtonCustomer;
    private JButton jButtonSell;

    public MainMenuCashier(String userNum,String userType,String branchNum,String userName){
        super(userNum,userType,branchNum,userName);
    }

    protected  void initializeActions(){
        jButtonCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerList customerList = new CustomerList();
                customerList.DrawCustomer();
                customerList.setVisible(true);
                customerList.setLocationRelativeTo(null);
            }
        });
    }
    @Override
    protected void DrawMainMenu() {
        super.DrawMainMenu();

        final int leftPad = 10;
        final int topPad = 15;
        final int topPadComp = 25;

        setTitle("Cashier");

        jPanelMid = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        jPanelMid.setLayout(springLayout);

        jPanelMid.setPreferredSize(new Dimension(300,400));

        jButtonCustomer = new JButton("CustomerList");

        jPanelMid.add(jButtonCustomer);

        springLayout.putConstraint(SpringLayout.WEST,jButtonCustomer,leftPad,SpringLayout.WEST,jPanelMid);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonCustomer,topPadComp,SpringLayout.NORTH,jPanelMid);

        jButtonSell = new JButton("Sell Item");

        jPanelMid.add(jButtonSell);

        springLayout.putConstraint(SpringLayout.WEST,jButtonSell,leftPad,SpringLayout.WEST,jPanelMid);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonSell,topPadComp,SpringLayout.NORTH,jButtonCustomer);

        getjPanelMain().add(jPanelMid);
    }

    public static void main(String[] args) {
        MainMenuCashier mainMenuCashier = new MainMenuCashier("47600","1","1","vadim");
        mainMenuCashier.DrawMainMenu();
        mainMenuCashier.initializeActions();
        mainMenuCashier.setVisible(true);
        mainMenuCashier.setLocationRelativeTo(null);
    }

    public JPanel getjPanelMid() {
        return jPanelMid;
    }

    public void setjPanelMid(JPanel jPanelMid) {
        this.jPanelMid = jPanelMid;
    }

    public JButton getjButtonCustomer() {
        return jButtonCustomer;
    }

    public void setjButtonCustomer(JButton jButtonCustomer) {
        this.jButtonCustomer = jButtonCustomer;
    }

    public JButton getjButtonSell() {
        return jButtonSell;
    }

    public void setjButtonSell(JButton jButtonSell) {
        this.jButtonSell = jButtonSell;
    }
}
