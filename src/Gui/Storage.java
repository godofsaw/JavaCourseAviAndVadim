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

public class Storage extends JFrame {
    private JTable table;
    private JPanel jPanelMain;
    private JPanel jPanelTable;
    private String branchNum;

    Storage(String branchNum){
        this.branchNum = branchNum;
    }

    protected void DrawStorage(){
        JSONArray jsonArrayResponse = new JSONArray();
        try {
            Socket socket = new Socket("127.0.0.1", 1345);
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("GuiName", "Storage");
            jsonObject.put("branch", branchNum);

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
            data.add((String)jsonObject.get("item_type"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_size"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_branch"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_amount"));

            dataList.add(data);
        }

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Item Type");
        columnNames.add("Item Size");
        columnNames.add("Item Branch");
        columnNames.add("Item Amount");

        this.setTitle("Branch storage");
        this.setSize(800,800);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanelMain = new JPanel();
        jPanelTable = new JPanel();

        jPanelTable.setPreferredSize(new Dimension(700, 800));

        this.setContentPane(jPanelMain);
        jPanelMain.add(jPanelTable);

        table = new JTable(dataList, columnNames);
        JScrollPane tableContainer = new JScrollPane(table);
        jPanelTable.add(tableContainer, BorderLayout.CENTER);
        jPanelMain.add(jPanelTable);
        this.setContentPane(jPanelMain);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Storage storage = new Storage("1");
        storage.DrawStorage();
        storage.setVisible(true);
        storage.setLocationRelativeTo(null);
    }
}
