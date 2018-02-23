package by.artur.server.listener;
import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable {
    private Socket s;
    private InputStream is;
    private OutputStream os;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }
    public void run() {
        Request request;
        String answer;
        try {
            request = readInputHeaders();
            if(request.isGetType()) {
                if (request.getRequestFile().equals(""))
                    answer = "Welcome to my server";
                else
                    answer = readFile(request.getRequestFile());
            }
            else
                answer = "It's is a not GET request";
            writeResponse("<html><head><meta charset='UTF-8'></head><body><h1>"+answer+"</h1></body></html>");
        } catch (Throwable t) {
                /*do nothing*/
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                    /*do nothing*/
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: ArturServer/2018-02-23\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }

    private Request readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Request request = new Request();
        String s = br.readLine();
        String []requestArray= s.split(" ");

        request.setRequestType(requestArray[0]);
        System.out.println("type of request GET= "+request.isGetType());

        request.setRequestFile(requestArray[1].substring(1));
        System.out.println("requeired file name = "+ request.getRequestFile());
        while(true) {
            s = br.readLine();
            System.out.println(s);
            if(s == null || s.trim().length() == 0) {
                return request;
            }
        }
    }

    private String readFile(String name){
        String answer="",line;
        try{
            FileReader fileReader = new FileReader(name);
            BufferedReader br = new BufferedReader(fileReader);
            while((line=br.readLine())!=null)
                answer+=line;
        }
        catch (FileNotFoundException e){
            answer = "File not found";
        }
        catch (Exception e){

        }
        System.out.println("ANSWER = "+answer);
        return answer;
    }
}
