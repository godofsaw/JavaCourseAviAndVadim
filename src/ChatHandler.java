import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class ChatHandler extends Thread{

    protected Socket s;
    protected DataInputStream i;
    protected DataOutputStream o;
    /*protected BufferedReader reader;*/
    protected OutputStreamWriter writer;

    public ChatHandler(Socket s) throws IOException {
        this.s = s;
        this.i = new DataInputStream (new BufferedInputStream(s.getInputStream ()));
        this.o = new DataOutputStream (new BufferedOutputStream(s.getOutputStream ()));
       /* this.reader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));*/
        this.writer = new OutputStreamWriter(s.getOutputStream(), "UTF-8");
    }

    protected static Vector handlers = new Vector ();
    protected static HashMap<String,ChatHandler> ChatUSers = new HashMap<>();

    public void run () {
        try {
            handlers.addElement(this);

            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
                //String msg = i.readUTF ();
                String msg =  reader.readLine();
                JSONObject jsonObject;
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(msg);
                String FromUser = (String) jsonObject.get("Sender");
                String message = (String) jsonObject.get("msg");
                String toUSer = (String) jsonObject.get("Reciever");
                ChatUSers.put(toUSer, this);

                broadcast(message, toUSer, FromUser);
            }
        }
         catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            handlers.removeElement (this);
            try {
                s.close ();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected static void broadcast (String message,String toUser,String FromUser) {
        synchronized (handlers) {
            for(int i =0;i<handlers.size();i++){
                if(handlers.get(i).equals(ChatUSers.get(toUser))){
                    ChatHandler c = (ChatHandler) handlers.get(i);
                    try {
                        synchronized (c.o) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("Sender", FromUser);
                            jsonObject.put("Reciever", toUser);
                            jsonObject.put("msg",message);
                            c.writer.write (jsonObject.toString() + "\n");
                        }
                        c.o.flush ();
                    } catch (IOException ex) {
                        //c.stop ();
                    }
                }
            }
            /*Enumeration e = handlers.elements ();
            while (e.hasMoreElements ()) {
                ChatHandler c = (ChatHandler) e.nextElement ();
                try {
                    synchronized (c.o) {
                        c.o.writeUTF (message);
                    }
                    c.o.flush ();
                } catch (IOException ex) {
                    //c.stop ();
                }
            }*/
        }
    }
}
