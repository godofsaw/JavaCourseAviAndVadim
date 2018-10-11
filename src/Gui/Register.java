package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Register extends JFrame {
    private JTextField fullName_text;
    private JTextField id_text;
    private JTextField phoneNum_text;
    private JComboBox type_box;
    private DefaultComboBoxModel comboBoxModel;
    private JLabel id_label;
    private JLabel fullName_label;
    private JLabel phoneNum_label;
    private JLabel type_label;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel jPanelMain;
    private JPanel jPanelButtons;
    private JPanel jPanelTopFields;
    private String fullName = "";
    private String id = "";
    private String phone = "";
    protected SpringLayout springLayoutFields;

    public Register() { }

    protected void DrawRegister() {
        final int leftPad = 10;
        final int leftBorderPad = 100;
        final int topPad = 15;
        final int topPadComp = 25;
        final int textLength = 20;

        setTitle("Register");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        springLayoutFields = new SpringLayout();
        SpringLayout springLayoutButtons = new SpringLayout();

        jPanelTopFields = new JPanel();
        jPanelTopFields.setLayout(springLayoutFields);
        jPanelTopFields.setPreferredSize(new Dimension(800,500));

        jPanelButtons = new JPanel();
        jPanelButtons.setLayout(springLayoutButtons);
        jPanelButtons.setPreferredSize(new Dimension(800,100));

        id_label = new JLabel("ID :");
        fullName_label = new JLabel("Full Name :");
        phoneNum_label = new JLabel("Phone :");
        type_label = new JLabel("Type :");

        id_text = new JTextField(textLength);
        fullName_text = new JTextField(textLength);
        phoneNum_text = new JTextField(textLength);
        comboBoxModel = new DefaultComboBoxModel();
        type_box = new JComboBox(comboBoxModel);

        registerButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        jPanelTopFields.add(id_label);
        jPanelTopFields.add(fullName_label);
        jPanelTopFields.add(phoneNum_label);
        jPanelTopFields.add(type_label);

        jPanelTopFields.add(id_text);
        jPanelTopFields.add(fullName_text);
        jPanelTopFields.add(phoneNum_text);
        jPanelTopFields.add(type_box);

        jPanelButtons.add(registerButton);
        jPanelButtons.add(cancelButton);

        springLayoutFields.putConstraint(SpringLayout.WEST,id_label,leftBorderPad,SpringLayout.WEST,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.NORTH,id_label,topPad,SpringLayout.NORTH,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.WEST,id_text,leftPad,SpringLayout.EAST,id_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,id_text,topPad,SpringLayout.NORTH,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.WEST,fullName_label,leftBorderPad,SpringLayout.EAST,id_text);
        springLayoutFields.putConstraint(SpringLayout.NORTH,fullName_label,topPad,SpringLayout.NORTH,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.WEST,fullName_text,leftPad,SpringLayout.EAST,fullName_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,fullName_text,topPad,SpringLayout.NORTH,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.WEST,phoneNum_label,leftBorderPad,SpringLayout.WEST,jPanelTopFields);
        springLayoutFields.putConstraint(SpringLayout.NORTH,phoneNum_label,topPad,SpringLayout.SOUTH,id_label);
        springLayoutFields.putConstraint(SpringLayout.WEST,phoneNum_text,leftPad,SpringLayout.EAST,phoneNum_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,phoneNum_text,topPad,SpringLayout.SOUTH,id_text);
        springLayoutFields.putConstraint(SpringLayout.WEST,type_label,leftBorderPad,SpringLayout.EAST,phoneNum_text);
        springLayoutFields.putConstraint(SpringLayout.NORTH,type_label,topPad,SpringLayout.SOUTH,fullName_label);
        springLayoutFields.putConstraint(SpringLayout.WEST,type_box,leftPad,SpringLayout.EAST,type_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,type_box,topPad,SpringLayout.SOUTH,fullName_text);

        springLayoutButtons.putConstraint(SpringLayout.WEST,registerButton,leftBorderPad,SpringLayout.WEST,jPanelButtons);
        springLayoutButtons.putConstraint(SpringLayout.NORTH,registerButton,topPad,SpringLayout.NORTH,jPanelButtons);
        springLayoutButtons.putConstraint(SpringLayout.WEST,cancelButton,leftBorderPad,SpringLayout.EAST,registerButton);
        springLayoutButtons.putConstraint(SpringLayout.NORTH,cancelButton,topPad,SpringLayout.NORTH,jPanelTopFields);

        fullName_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                fullName = fullName_text.getText();
            }
        });

        id_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                id = id_text.getText();
            }
        });

        phoneNum_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                phone = phoneNum_text.getText();
            }
        });

        jPanelMain = new JPanel();
        jPanelMain.add(jPanelTopFields);
        jPanelMain.add(jPanelButtons);
        setContentPane(jPanelMain);

    }

    public static void main(String[] args) {
        Register register = new Register();
        register.DrawRegister();
        register.setVisible(true);
        register.setLocationRelativeTo(null);
    }

    public JTextField getFullName_text() {
        return fullName_text;
    }

    public void setFullName_text(JTextField fullName_text) {
        this.fullName_text = fullName_text;
    }

    public JTextField getId_text() {
        return id_text;
    }

    public void setId_text(JTextField id_text) {
        this.id_text = id_text;
    }

    public JTextField getPhoneNum_text() {
        return phoneNum_text;
    }

    public void setPhoneNum_text(JTextField phoneNum_text) {
        this.phoneNum_text = phoneNum_text;
    }

    public JLabel getId_label() {
        return id_label;
    }

    public void setId_label(JLabel id_label) {
        this.id_label = id_label;
    }

    public JLabel getFullName_label() {
        return fullName_label;
    }

    public void setFullName_label(JLabel fullName_label) {
        this.fullName_label = fullName_label;
    }

    public JLabel getPhoneNum_label() {
        return phoneNum_label;
    }

    public void setPhoneNum_label(JLabel phoneNum_label) {
        this.phoneNum_label = phoneNum_label;
    }

    public JLabel getType_label() {
        return type_label;
    }

    public void setType_label(JLabel type_label) {
        this.type_label = type_label;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(JButton registerButton) {
        this.registerButton = registerButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JPanel getjPanelMain() {
        return jPanelMain;
    }

    public void setjPanelMain(JPanel jPanelMain) {
        this.jPanelMain = jPanelMain;
    }

    public JPanel getjPanelButtons() {
        return jPanelButtons;
    }

    public void setjPanelButtons(JPanel jPanelButtons) {
        this.jPanelButtons = jPanelButtons;
    }

    public JPanel getjPanelTopFields() {
        return jPanelTopFields;
    }

    public void setjPanelTopFields(JPanel jPanelTopFields) {
        this.jPanelTopFields = jPanelTopFields;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SpringLayout getSpringLayoutFields() {
        return springLayoutFields;
    }

    public void setSpringLayoutFields(SpringLayout springLayoutFields) {
        this.springLayoutFields = springLayoutFields;
    }

    public JComboBox getType_box() {
        return type_box;
    }

    public void setType_box(JComboBox type_box) {
        this.type_box = type_box;
    }

    public DefaultComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }

    public void setComboBoxModel(DefaultComboBoxModel comboBoxModel) {
        this.comboBoxModel = comboBoxModel;
    }
}
