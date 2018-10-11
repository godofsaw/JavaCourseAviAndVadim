package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuAdmin extends MainMenuCashier{

    private JButton jButtonReport;
    private JButton jButtonEmployee;
    private JPanel jPanelRight;

    public MainMenuAdmin(String userNum,String userType,String branchNum,String userName){
        super(userNum,userType,branchNum,userName);
    }

    @Override
    protected void initializeActions() {
        getjButtonCustomer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerListManagement customerManagement = new CustomerListManagement();
                customerManagement.DrawCustomer();
                customerManagement.setVisible(true);
                customerManagement.setLocationRelativeTo(null);
            }
        });
    }

    @Override
    protected void DrawMainMenu() {
        super.DrawMainMenu();

        final int leftPad = 10;
        final int topPad = 15;
        final int topPadComp = 25;

        setTitle("Shift Manager");

        jPanelRight = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        jPanelRight.setLayout(springLayout);

        jPanelRight.setPreferredSize(new Dimension(300,400));

        jButtonReport = new JButton("Reports");

        jPanelRight.add(jButtonReport);

        springLayout.putConstraint(SpringLayout.WEST,jButtonReport,leftPad,SpringLayout.WEST,jPanelRight);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonReport,topPadComp,SpringLayout.NORTH,jPanelRight);

        jButtonEmployee = new JButton("Employee Management");

        jPanelRight.add(jButtonEmployee);

        springLayout.putConstraint(SpringLayout.WEST,jButtonEmployee,leftPad,SpringLayout.WEST,jPanelRight);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonEmployee,topPadComp,SpringLayout.NORTH,jButtonReport);

        getjPanelMain().add(jPanelRight);
    }

    public static void main(String[] args) {
        MainMenuAdmin mainMenuAdmin = new MainMenuAdmin("47600","1","1","vadim");
        mainMenuAdmin.DrawMainMenu();
        mainMenuAdmin.initializeActions();
        mainMenuAdmin.setVisible(true);
        mainMenuAdmin.setLocationRelativeTo(null);
    }
}
