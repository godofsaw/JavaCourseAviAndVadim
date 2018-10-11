package Gui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class Chat extends JFrame implements Runnable{
    private SpringLayout springLayoutPanelUsers,springLayoutPanelChat,springLayoutPanelMes;
    private JPanel jPanelMain,jPanelChat,jPanelSendMes,jPanelUsers;
    private JScrollPane jScrollPaneUsers;
    private Color colorChat,colorUsers,colorSendMes;
    private JTextField jTextFieldMess;
    private JButton jButtonSend;
    private JButton[] jButtonsUsers;
    private JTextArea[] jTextAreasChat;
    private HashMap<JButton,JTextArea> hashMap;
    private String message = "";
    private int currentChat=0;
    protected DataInputStream i;
    protected DataOutputStream o;
    protected Thread listener;
    Socket socket ;

    public Chat() throws IOException {
        setTitle("Chat");
        setSize(900,500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanelMain = new JPanel();
        setContentPane(jPanelMain);

        DrawChat();

        socket= new Socket("127.0.0.1", 1234);

        this.i = new DataInputStream(new BufferedInputStream( this.socket.getInputStream()));
        this.o = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));

        listener = new Thread(this);
        listener.start();
    }

    public void run () {
        try {
            while (true) {
                String line = i.readUTF ();
                //output.appendText (line + "\n");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                hashMap.get(jButtonsUsers[currentChat]).append(dateFormat.format(date)+" "+jButtonsUsers[currentChat].getText()+" : "+line +"\n");
                hashMap.get(jButtonsUsers[currentChat]).setCaretPosition(hashMap.get(jButtonsUsers[currentChat]).getText().length() - 1);
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        } finally {
            listener = null;
            //input.hide ();
            validate ();
            try {
                o.close ();
            } catch (IOException ex) {
                ex.printStackTrace ();
            }
        }
    }

    public boolean handleEvent (Event e) {

            try {
                o.writeUTF (message);
                o.flush ();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                listener.stop ();
            }
            //input.setText ("");

         if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            if (listener != null)
                listener.stop ();
            hide ();
            return true;
        }
        return super.handleEvent (e);
    }

    protected void DrawChat(){

        createChatPanel();
        createUsersPanel();
        createMesPanel();

        GetOnlineUsers();

        ShowChat();

        SendMessage();
    }

    private void SendMessage() {
        jTextFieldMess.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                message = jTextFieldMess.getText();
            }
        });

        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                hashMap.get(jButtonsUsers[currentChat]).append(dateFormat.format(date)+" "+jButtonsUsers[currentChat].getText()+" : "+message +"\n");
                hashMap.get(jButtonsUsers[currentChat]).setCaretPosition(hashMap.get(jButtonsUsers[currentChat]).getText().length() - 1);
                jTextFieldMess.setText("");
            }
        });
    }

    private void ShowChat() {
        for(int i =0; i < jButtonsUsers.length;i++){
            JButton temp = jButtonsUsers[i];
            int tempPos = i;
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hashMap.get(temp).setVisible(true);
                    currentChat = tempPos;
                    for(int j=0;j<jButtonsUsers.length;j++) {
                        if(temp!=jButtonsUsers[j]) {
                            hashMap.get(jButtonsUsers[j]).setVisible(false);
                        }
                    }
                    jPanelChat.removeAll();
                    jPanelChat.add(new JScrollPane(hashMap.get(temp)));
                    jPanelChat.updateUI();
                }
            });
        }
    }

    private void createChatPanel(){

        //springLayoutPanelChat = new SpringLayout();
        jPanelChat = new JPanel(new GridLayout(1,1));
        //jPanelChat.setLayout(springLayoutPanelChat);
        jPanelChat.setPreferredSize(new Dimension(600,300));
        colorChat = new Color(100,200,100);
        jPanelChat.setBackground(colorChat);
        jPanelMain.add(jPanelChat);

    }

    private void createUsersPanel(){

        springLayoutPanelUsers = new SpringLayout();

        jPanelUsers = new JPanel();
        jPanelUsers.setLayout(springLayoutPanelUsers);
        jPanelUsers.setPreferredSize(new Dimension(200,300));
        colorUsers = new Color(200,100,200);
        jPanelUsers.setBackground(colorUsers);

        jScrollPaneUsers = new JScrollPane(jPanelUsers,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelMain.add(jScrollPaneUsers);
    }

    private void createMesPanel(){

        springLayoutPanelMes = new SpringLayout();

        jPanelSendMes = new JPanel();
        jPanelSendMes.setLayout(springLayoutPanelMes);
        jPanelSendMes.setPreferredSize(new Dimension(800,100));
        colorSendMes = new Color(10,10,250);
        jPanelSendMes.setBackground(colorSendMes);
        jPanelMain.add(jPanelSendMes);

        jTextFieldMess = new JTextField(54);
        jTextFieldMess.setMaximumSize(new Dimension(550,290));
        jPanelSendMes.add(jTextFieldMess);

        jButtonSend = new JButton("Send");
        jButtonSend.setSize(new Dimension(15,15));
        jPanelSendMes.add(jButtonSend);

        springLayoutPanelMes.putConstraint(SpringLayout.WEST,jTextFieldMess,5,SpringLayout.WEST,jPanelSendMes);
        springLayoutPanelMes.putConstraint(SpringLayout.NORTH,jTextFieldMess,5,SpringLayout.NORTH,jPanelSendMes);
        springLayoutPanelMes.putConstraint(SpringLayout.WEST,jButtonSend,5,SpringLayout.EAST,jTextFieldMess);
        springLayoutPanelMes.putConstraint(SpringLayout.NORTH,jButtonSend,5,SpringLayout.NORTH,jPanelSendMes);

    }

    private void GetOnlineUsers(){
        JSONArray jsonArrayResponse = new JSONArray();
        try {
            Socket socket= new Socket("127.0.0.1", 1345);
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("GuiName", "CheckOnlineUsers");

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
            data.add((String)jsonObject.get("emp_name"));

            jsonObject = (JSONObject)jsonArrayResponse.get(i++);
            data.add((String)jsonObject.get("emp_sn"));

            dataList.add(data);
        }

        DrawOnlineUsersAndChats(dataList);

    }

    private void DrawOnlineUsersAndChats(Vector<Vector<String>> dataList) {
        jTextAreasChat = new JTextArea[dataList.size()];
        jButtonsUsers = new JButton[dataList.size()];
        hashMap = new HashMap<JButton,JTextArea>();

        for(int j = 0;j< jButtonsUsers.length;j++){

            jButtonsUsers[j] = new JButton(dataList.get(j).get(0));
            jPanelUsers.add(jButtonsUsers[j]);

            if(j == 0){
                springLayoutPanelUsers.putConstraint(SpringLayout.WEST,jButtonsUsers[0],5,SpringLayout.WEST,jPanelUsers);
                springLayoutPanelUsers.putConstraint(SpringLayout.NORTH,jButtonsUsers[0],5,SpringLayout.NORTH,jPanelUsers);
            }
            else {
                springLayoutPanelUsers.putConstraint(SpringLayout.WEST, jButtonsUsers[j], 5, SpringLayout.WEST, jPanelUsers);
                springLayoutPanelUsers.putConstraint(SpringLayout.NORTH, jButtonsUsers[j], 5, SpringLayout.SOUTH, jButtonsUsers[j - 1]);
            }

            jTextAreasChat[j] = new JTextArea(dataList.get(j).get(0)+"\n");//,80,80
            jTextAreasChat[j].setEditable(false);
            jTextAreasChat[j].setVisible(false);

            hashMap.put(jButtonsUsers[j],jTextAreasChat[j]);
        }
    }

    public static void main(String[] args) throws IOException {
        Chat chat = new Chat();
        //chat.DrawChat();
        chat.setVisible(true);
        chat.setLocationRelativeTo(null);
    }
}
