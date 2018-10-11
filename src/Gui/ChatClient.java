package Gui;

import org.apache.xmlbeans.soap.SOAPArrayType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatClient extends Frame implements Runnable{
    protected DataInputStream i;
    protected DataOutputStream o;
    protected TextArea output;
    protected TextField input;
    protected Thread listener;
    protected TextField name;
    protected TextField toName;
    String user;
    String toUser;
    Socket socket;
    public ChatClient(String title, InputStream i, OutputStream o, Socket socket) {
        super(title);
        this.i = new DataInputStream(new BufferedInputStream(i));
        this.o = new DataOutputStream(new BufferedOutputStream(o));
        this.socket = socket;

        setLayout(new BorderLayout());
        add("Center", output = new TextArea());
        output.setEditable(false);
        add("South", input = new TextField());
        add("West",name = new TextField());
        add("East",toName = new TextField());

        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = name.getText();
            }
        });
        toName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toUser = toName.getText();
            }
        });
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Sender", user);
                    jsonObject.put("Reciever", toUser);
                    jsonObject.put("msg",input.getText());
                    writer.write(jsonObject.toString() + "\n");
                    output.appendText (user +" :"+ input.getText() +"\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    listener.stop ();
                }
                input.setText ("");
            }
        });
        pack();
        show();
        input.requestFocus();
        listener = new Thread(this);
        listener.start();
    }

    public void run () {
        try {
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                //String msg = i.readUTF ();
                String msg = reader.readLine();
                JSONObject jsonObject;
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(msg);
                String FromUser = (String) jsonObject.get("Sender");
                String message = (String) jsonObject.get("msg");
                String toUSer = (String) jsonObject.get("Reciever");

                output.appendText (FromUser +" :"+ message + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            listener = null;
            input.hide ();
            validate ();
            try {
                o.close ();
            } catch (IOException ex) {
                ex.printStackTrace ();
            }
        }
    }

    /*public boolean handleEvent (Event e) {
        if ((e.target == input) && (e.id == Event.ACTION_EVENT)) {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Sender", user);
                jsonObject.put("Reciever", toUser);
                jsonObject.put("msg",(String)e.arg);
                writer.write(jsonObject.toString() + "\n");
                output.appendText (user +" :"+ (String)e.arg +"\n");
                *//*o.writeUTF (jsonObject.toString() + "\n");
                o.flush ();*//*
            } catch (IOException ex) {
                ex.printStackTrace();
                listener.stop ();
            }
            input.setText ("");
            return true;
        } else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            if (listener != null)
                listener.stop ();
            hide ();
            return true;
        }
        return super.handleEvent (e);
    }*/

    public static void main (String args[]) throws IOException {
        /*if (args.length != 2)
            throw new RuntimeException ("Syntax: ChatClient <host> <port>");*/
        Socket s = new Socket ("localhost", 1234);
        new ChatClient ("Chat " ,
                s.getInputStream (), s.getOutputStream (),s);
    }
}
