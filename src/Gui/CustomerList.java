package Gui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

public class CustomerList extends  JFrame{
    private JTable table;
    private JPanel jPanelMain;
    private JPanel jPanelTable;
    private JTextPane textPane1;
    private JPanel panel1;

    public CustomerList(){
    }
    protected void DrawCustomer(){
        JSONArray jsonArrayResponse = new JSONArray();
        try {
            Socket socket = new Socket("127.0.0.1", 1345);
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("GuiName", "CustomerList");

            writer.write(jsonObject.toString() + "\n");
            writer.flush();

            String line = reader.readLine();
            JSONParser jsonParser = new JSONParser();
            jsonArrayResponse = (JSONArray) jsonParser.parse(line);
        }
        catch (Exception ex){

        }

        Vector<Vector<String>> dataList = new Vector<>();
        JSONObject jsonObject;

        int jsonArraySize = jsonArrayResponse.size();
        int i = 0;

        while (i < jsonArraySize) {

            Vector<String> data = new Vector<>();

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("cust_id"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("cust_name"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("cust_tel"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("cust_type"));

            dataList.add(data);
        }

        Vector<String> columnNames = new Vector<>();
        columnNames.add("CustomerList ID");
        columnNames.add("CustomerList Name");
        columnNames.add("CustomerList Phone");
        columnNames.add("CustomerList Type");

        this.setTitle("Branch storage");
        this.setSize(800,800);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanelMain = new JPanel();
        jPanelTable = new JPanel();

        jPanelTable.setPreferredSize(new Dimension(700, 500));

        this.setContentPane(jPanelMain);
        jPanelMain.add(jPanelTable);

        table = new JTable(dataList, columnNames);
        JScrollPane tableContainer = new JScrollPane(table);
        jPanelTable.add(tableContainer, BorderLayout.CENTER);
        jPanelMain.add(jPanelTable);
        this.setContentPane(jPanelMain);
    }
    public static void main(String[] args) {
        CustomerList customerList = new CustomerList();
        customerList.DrawCustomer();
        customerList.setVisible(true);
        customerList.setLocationRelativeTo(null);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JPanel getjPanelMain() {
        return jPanelMain;
    }

    public void setjPanelMain(JPanel jPanelMain) {
        this.jPanelMain = jPanelMain;
    }

    public JPanel getjPanelTable() {
        return jPanelTable;
    }

    public void setjPanelTable(JPanel jPanelTable) {
        this.jPanelTable = jPanelTable;
    }
}
