package Gui;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Vector;

public class Report {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
      /*  JSONArray jsonArrayResponse = new JSONArray();
        try {
            Socket socket = new Socket("127.0.0.1", 1345);
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("GuiName", "Report");
            jsonObject.put("branch", "Tel Aviv");
            jsonObject.put("type","all");//all or specific item

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
            data.add((String)jsonObject.get("item_part_number"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_type"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_size"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("item_price"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("sell_time"));

            dataList.add(data);
        }*/

        XWPFDocument document = new XWPFDocument();

        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "Report: " ;//+ branch +"-"+ type_item;
        ctHeader.setStringValue(headerText);

        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

        //create table
        XWPFTable table = document.createTable();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);

        tableRowOne.getCell(0).setText("Customer ID");
        tableRowOne.addNewTableCell().setText("Item Id");
        tableRowOne.addNewTableCell().setText("Type");
        tableRowOne.addNewTableCell().setText("Size");
        tableRowOne.addNewTableCell().setText("Price");
        tableRowOne.addNewTableCell().setText("Date");

        XWPFTable table2 = document.createTable();
        XWPFTableRow tableRowTwo = table2.getRow(0);
        int sumPrice=0;

    /*    for(int j =0; j<dataList.size();j++){
            sumPrice += Integer.parseInt(dataList.get(j).get(4));
        }
        tableRowTwo.getCell(0).setText("This is the total profit today: "+sumPrice);



      *//*  for(int j =0; j<dataList.size();j++){

            XWPFTableRow tempTableRow = table.createRow();

            for(int k =0;k<dataList.get(j).size();k++) {

                tempTableRow.getCell(k).setText(dataList.get(j).get(k));
            }
        }*//*

        int sumPrice=0;

        for(int j =0; j<dataList.size();j++){
            sumPrice += Integer.parseInt(dataList.get(j).get(4));
        }
*/
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        String footerText = "This is the total profit today: "+sumPrice;
        ctFooter.setStringValue(footerText);
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

        FileOutputStream out = new FileOutputStream(new File("Report.docx"));
        document.write(out);
        out.close();

        File file = new File("Report.docx");
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);

    }
}
