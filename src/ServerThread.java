import Classes.Employee;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ServerThread extends Thread{
    Socket socket;

    ServerThread(Socket socket) {
        this.socket = socket;
    }
    public void run(){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line = reader.readLine();
            JSONObject jsonObject;
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(line);

            String methodname = (String) jsonObject.get("GuiName");
            switch (methodname){

                case "Log in":{
                    LogInEmployee(jsonObject,writer);
                    break;
                }

                case "Register":{
                    RegisterEmployee(jsonObject,writer);
                    break;
                }

                case "Storage":{
                    StorageResponse(jsonObject,writer);
                    break;
                }

                case "CustomerList":{
                    CustomerResponse(jsonObject,writer);
                    break;
                }

                case "Report":{
                    ReportResponse(jsonObject,writer);
                    break;
                }

                case "Customer":{
                    //RegisterCustomer(jsonObject,writer);
                }

                case "CheckOnlineUsers":{
                    OnlineUsersResponse(jsonObject,writer);
                    break;
                }
                case "Chat":{
                    PleaseWork(jsonObject,writer,socket);
                    break;
                }
            }
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }

    private void PleaseWork(JSONObject jsonObject, OutputStreamWriter writer, Socket socket) throws IOException {
        ChatHandler c = new ChatHandler (socket);
        c.start ();
    }

    private void OnlineUsersResponse(JSONObject jsonObject, OutputStreamWriter writer) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "123456");
            Statement statement = conn.createStatement();
            String query = "select emp_name,emp_sn from store.employee where emp_status = '1'";
            ResultSet resultSet = statement.executeQuery(query);

            writer.write(ResultSetJson(resultSet).toString()+"\n");
            statement.close();
            writer.flush();
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }

    private static void CustomerResponse(JSONObject jsonObject, OutputStreamWriter writer) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "123456");
            Statement statement = conn.createStatement();
            String query = "select * from store.customer";
            ResultSet resultSet = statement.executeQuery(query);

            writer.write(ResultSetJson(resultSet).toString()+"\n");
            statement.close();
            writer.flush();
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }

    private static void StorageResponse(JSONObject jsonObject, OutputStreamWriter writer) {
        try {
            String branchNum = (String) jsonObject.get("branch");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "123456");
            Statement statement = conn.createStatement();
            String query = String.format("select * from store.storage where item_branch='%s'",branchNum);
            ResultSet resultSet = statement.executeQuery(query);

            writer.write(ResultSetJson(resultSet).toString()+"\n");
            statement.close();
            writer.flush();
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }

    private static JSONArray ResultSetJson(ResultSet resultSet) throws SQLException {
        JSONArray jsonArray = new JSONArray();

        while (resultSet.next()) {

            int total_rows = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < total_rows; i++) {

                JSONObject obj = new JSONObject();
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                jsonArray.add(obj);
            }
        }

        return jsonArray;
    }

    private static void LogInEmployee(JSONObject jsonObject, OutputStreamWriter writer) {
        try {
            String response = "";
            String userNum = (String) jsonObject.get("user number");
            String userPass = (String) jsonObject.get("user pass");
            String branchNum = (String) jsonObject.get("user branch");
            String userType = "";
            String userName = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
            Statement statement = conn.createStatement();
            String query = String.format("select emp_type,emp_name from store.employee where emp_sn='%s' and emp_pass='%s' and emp_branch='%s'",userNum,userPass,branchNum);
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                userType = resultSet.getString("emp_type");
                userName = resultSet.getString("emp_name");
            }
            else {
                response = "Your Employee number ,Password or Branch number is incorrect !!!";
            }

            statement.close();

            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("Failed",response);
            jsonObjectResponse.put("user type",userType);
            jsonObjectResponse.put("user name",userName);

            writer.write(jsonObjectResponse.toString()+"\n");
            writer.flush();
        }
        catch (Exception ex){

        }
    }

    private static void RegisterEmployee(JSONObject jsonObject,OutputStreamWriter writer){
        try {
            String response="";
            String maxSn="";
            String empStr = (String) jsonObject.get("employee");
            ObjectMapper objectMapper = new ObjectMapper();
            Employee employee = objectMapper.readValue(empStr, Employee.class);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");

            Statement statement = conn.createStatement();
            String query = "select max(emp_sn) as empSn from store.employee";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                maxSn = resultSet.getString("empSn");
                int max = Integer.parseInt(maxSn)+1;
                maxSn = ""+max;
                employee.setEmpSn(maxSn);
            }
            resultSet.close();

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO store.employee values(?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, employee.getEmpSn());
            preparedStatement.setString(2, employee.getEmpName());
            preparedStatement.setString(3, employee.getEmpId());
            preparedStatement.setString(4, employee.getEmpTel());
            preparedStatement.setString(5, employee.getEmpBank());
            preparedStatement.setString(6, employee.getEmpBranch());
            preparedStatement.setString(7, employee.getEmpType());
            preparedStatement.setString(8, employee.getPassword());

            try {
                preparedStatement.execute();
            }
            catch (Exception ex){
                if(ex.toString().contains("PRIMARY")){
                    response = "Employee already exists";
                }
            }

            preparedStatement.close();
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("Failed",response);
            jsonObjectResponse.put("emp sn",maxSn);
            writer.write(jsonObjectResponse.toString()+"\n");
            writer.flush();
        }

        catch (Exception ex){
            System.out.print(ex);
        }
    }

    private static void ReportResponse(JSONObject jsonObject, OutputStreamWriter writer){
        try {
            String branch = (String) jsonObject.get("branch");
            String type = (String) jsonObject.get("type");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STORE?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "123456");
            Statement statement = conn.createStatement();
            String query ;

            if(type.equals("all")) {
                query = String.format("select * from store.sells where item_branch='%s'",branch);
            }
            else{
                query = String.format("select * from store.sells where item_branch='%s' and item_type in ($s)",branch,type);
            }

            ResultSet resultSet = statement.executeQuery(query);

            writer.write(ResultSetJson(resultSet).toString()+"\n");
            statement.close();
            writer.flush();
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }
}
