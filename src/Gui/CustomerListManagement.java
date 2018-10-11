package Gui;

import javax.swing.*;
import java.awt.*;

public class CustomerListManagement extends CustomerList {

    private JPanel jPanelManage;
    private JButton jButtonAddCustom;
    private JButton jButtonManageCustom;

    public CustomerListManagement(){ }

    @Override
    protected void DrawCustomer() {
        super.DrawCustomer();

        final int leftPad = 10;
        final int topPad = 15;
        final int topPadComp = 25;

        setTitle("CustomerList Management");

        jPanelManage = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        jPanelManage.setLayout(springLayout);

        jPanelManage.setPreferredSize(new Dimension(700,250));

        jButtonAddCustom = new JButton("Register CustomerList");

        jPanelManage.add(jButtonAddCustom);

        springLayout.putConstraint(SpringLayout.WEST,jButtonAddCustom,leftPad,SpringLayout.WEST,jPanelManage);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonAddCustom,topPadComp,SpringLayout.NORTH,jPanelManage);

        jButtonManageCustom = new JButton("Edit CustomerList");

        jPanelManage.add(jButtonManageCustom);

        springLayout.putConstraint(SpringLayout.WEST,jButtonManageCustom,leftPad,SpringLayout.EAST,jButtonAddCustom);
        springLayout.putConstraint(SpringLayout.NORTH,jButtonManageCustom,topPadComp,SpringLayout.NORTH,jPanelManage);

        getjPanelMain().add(jPanelManage);
    }
    public static void main(String[] args) {
        CustomerListManagement customerManagement = new CustomerListManagement();
        customerManagement.DrawCustomer();
        customerManagement.setVisible(true);
        customerManagement.setLocationRelativeTo(null);
    }
}
