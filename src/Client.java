import Classes.Employee;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
        /*    Socket socket = new Socket("127.0.0.1", 1345);
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            JSONObject jsonObject = new JSONObject();
            Employee employee = new Employee("47600", "vad", "32156", "050", "654213", "1", "0");
            ObjectMapper objectMapper = new ObjectMapper();
            String empStr = objectMapper.writeValueAsString(employee);*/

            /*jsonObject.put("GuiName","Register");
            jsonObject.put("employee",empStr);

            writer.write(jsonObject.toString()+"\n");
            writer.flush();

            socket.close();*/
        }
        catch (Exception ex){
            System.out.print(ex);
        }
    }
}
