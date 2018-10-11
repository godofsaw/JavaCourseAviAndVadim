package Gui;

import Classes.Employee;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RegisterEmployee extends Register{
    private JTextField bankAcc_text;
    private JTextField brachNum_text;
    private JPasswordField password_text;
    private JLabel bankAcc_label;
    private JLabel branchNum_label;
    private JLabel password_label;
    private int passLength;
    private char[] pass;

    public RegisterEmployee() {
    }

    protected void DrawRegister(){
        super.DrawRegister();

        setTitle("Register Employee");
        Employee employee = new Employee();
        employee.setEmpType("Seller");

        final int leftPad = 10;
        final int leftBorderPad = 100;
        final int topPad = 15;
        final int topPadComp = 25;
        final int textLength = 20;

        bankAcc_text = new JTextField(textLength);
        brachNum_text = new JTextField(textLength);
        password_text = new JPasswordField(textLength);

        bankAcc_label = new JLabel("Bank Account:");
        branchNum_label = new JLabel("Branch Number:");
        password_label = new JLabel("Password");

        getjPanelTopFields().add(bankAcc_label);
        getjPanelTopFields().add(branchNum_label);
        getjPanelTopFields().add(bankAcc_text);
        getjPanelTopFields().add(brachNum_text);
        getjPanelTopFields().add(password_text);
        getjPanelTopFields().add(password_label);

        springLayoutFields.putConstraint(SpringLayout.WEST,bankAcc_label,leftBorderPad,SpringLayout.WEST,getjPanelTopFields());
        springLayoutFields.putConstraint(SpringLayout.NORTH,bankAcc_label,topPad,SpringLayout.SOUTH,getPhoneNum_label());
        springLayoutFields.putConstraint(SpringLayout.WEST,bankAcc_text,leftPad,SpringLayout.EAST,bankAcc_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,bankAcc_text,topPad,SpringLayout.SOUTH,getPhoneNum_text());
        springLayoutFields.putConstraint(SpringLayout.WEST,branchNum_label,leftBorderPad,SpringLayout.EAST,bankAcc_text);
        springLayoutFields.putConstraint(SpringLayout.NORTH,branchNum_label,topPad,SpringLayout.SOUTH,getType_label());
        springLayoutFields.putConstraint(SpringLayout.WEST,brachNum_text,leftPad,SpringLayout.EAST,branchNum_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,brachNum_text,topPad,SpringLayout.SOUTH,getType_box());
        springLayoutFields.putConstraint(SpringLayout.WEST,password_label,leftBorderPad,SpringLayout.WEST,getjPanelTopFields());
        springLayoutFields.putConstraint(SpringLayout.NORTH,password_label,topPad,SpringLayout.SOUTH,bankAcc_label);
        springLayoutFields.putConstraint(SpringLayout.WEST,password_text,leftPad,SpringLayout.EAST,password_label);
        springLayoutFields.putConstraint(SpringLayout.NORTH,password_text,topPad,SpringLayout.SOUTH,bankAcc_text);

        bankAcc_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                employee.setEmpBank(bankAcc_text.getText());
            }
        });

        brachNum_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                employee.setEmpBranch(brachNum_text.getText());
            }
        });

        getComboBoxModel().addElement("Seller");
        getComboBoxModel().addElement("Cashier");
        getComboBoxModel().addElement("Admin");

        getType_box().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee.setEmpType((String) getType_box().getSelectedItem());
            }
        });

        password_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                passLength = password_text.getPassword().length;
                pass = password_text.getPassword();
                employee.setPassword(password_text.getPassword().toString());
            }
        });

        getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    employee.setEmpTel(getPhone());
                    employee.setEmpName(getFullName());
                    employee.setEmpId(getId());

                    if(!CheckEmpFields(employee,passLength,pass)){
                        return;
                    }

                    Socket socket = new Socket("127.0.0.1", 1345);
                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    JSONObject jsonObject = new JSONObject();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String empStr = objectMapper.writeValueAsString(employee);

                    jsonObject.put("GuiName", "Register");
                    jsonObject.put("employee", empStr);

                    writer.write(jsonObject.toString() + "\n");
                    writer.flush();

                    String line = reader.readLine();
                    JSONObject jsonObjectResponse;
                    JSONParser jsonParser = new JSONParser();
                    jsonObjectResponse = (JSONObject) jsonParser.parse(line);
                    String response = (String) jsonObjectResponse.get("Failed");

                    if(!response.equals("")){
                        JOptionPane.showMessageDialog(null, response);
                    }
                    else{
                        response = (String) jsonObjectResponse.get("emp sn");
                        JOptionPane.showMessageDialog(null, "Registered successfully, you're S/N is:"+response);
                    }

                    socket.close();
                }
                catch (Exception ex){
                    System.out.print(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        RegisterEmployee registerEmployee = new RegisterEmployee();
        registerEmployee.DrawRegister();
        registerEmployee.setVisible(true);
        registerEmployee.setLocationRelativeTo(null);
    }

    private static boolean CheckEmpFields(Employee employee,int passLength,char[] pass){

        if (!(employee.getEmpId().length() <= 9 && employee.getEmpId().length()>=1)) {
            JOptionPane.showMessageDialog(null, "ID: Your Id length is incorrect");
            return false;
        }
        if (!(employee.getEmpId().matches("[0-9]+"))) {
            JOptionPane.showMessageDialog(null, "ID: Please insert only digits");
            return false;
        }

        if (!(employee.getEmpName().length() <= 20 && employee.getEmpName().length() >= 5)) {
            JOptionPane.showMessageDialog(null, "Full Name: Legal length name  is between 5 to 20 letters");
            return false;
        }
        if (!(onlyLettersSpaces(employee.getEmpName()))) {
            JOptionPane.showMessageDialog(null, "Full Name: Please insert only Letters and Spaces");
            return false;
        }

        if (!(employee.getEmpTel().length() <= 10 && employee.getEmpTel().length() >= 9)) {
            JOptionPane.showMessageDialog(null, "Phone: Your phone number length is incorrect");
            return false;
        }
        if (!(employee.getEmpTel().matches("[0-9]+"))) {
            JOptionPane.showMessageDialog(null, "Phone: Please enter only digits");
            return false;
        }

        if (!(employee.getEmpBank().length() <= 10 && employee.getEmpBank().length() >=5)) {
            JOptionPane.showMessageDialog(null, "Bank Account: Your account number length is incorrect");
            return false;
        }
        if (!(employee.getEmpBank().matches("[0-9]+"))) {
            JOptionPane.showMessageDialog(null, "Bank Account: Please insert only digits");
            return false;
        }

        if (!(employee.getEmpBranch().length() == 1)) {
            JOptionPane.showMessageDialog(null, "Branch Number: Please insert only one digit");
            return false;
        }
        if (!(employee.getEmpBranch().matches("[0-9]+"))) {
            JOptionPane.showMessageDialog(null, "Branch Number: Please insert only digits");
            return false;
        }
        if (!(employee.getEmpBranch().charAt(0) >= '1' && employee.getEmpBranch().charAt(0) <= '9')) {
            JOptionPane.showMessageDialog(null, "Branch Number: Please insert branch number between 1 to 9");
            return false;
        }

        if (!(passLength >=8 && passLength <=12)) {
            JOptionPane.showMessageDialog(null, "Password: Your password length is incorrect");
            return false;
        }
        if (!(PassCheck(pass))) {
            JOptionPane.showMessageDialog(null, "Password: you're password is incorrect");
            return false;
        }

        return true;
    }

    private static boolean onlyLettersSpaces(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static boolean PassCheck(char[] pass){
        boolean smallLeter=false,capitalLeter=false,numbers=false;
        for(int i =0; i<pass.length;i++){
            if(!(pass[i] >='a' && pass[i]<= 'z')) {
                if(!(pass[i] >='A' && pass[i] <='Z')){
                    if(!(pass[i] >='0' && pass[i] <='9')){
                        return false;
                    }
                    else{
                        numbers = true;
                    }
                }
                else {
                    capitalLeter = true;
                }
            }
            else{
                smallLeter = true;
            }
        }
        if(smallLeter&&capitalLeter&&numbers)
            return true;
        else
            return false;
    }
}
