package by.artur.server.listener;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.nio.cs.StandardCharsets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        String type="text/html",acceptRanges = "";
        byte []answer;
        try {
            request = readInputHeaders();
            if(request.isGetType()) {
                if (request.getRequestFile().equals("")) {
                    answer = "<html><head><meta charset='UTF-8'></head><body><h1>Welcome to my server</h1></body></html>".getBytes();
                }
                else {
                    if (request.getExtenFile().equals("jpg")){
                        acceptRanges = "accept-ranges:bytes\r\n";
                        type = "image/jpeg";
                        answer = readPicture(request.getRequestFile());
                    }
                    else {
                        if(request.getExtenFile().equals("css"))
                            type="text/css";
                        answer = readFile(request.getRequestFile()).getBytes();
                    }
                }
            }
            else{
                answer = "<html><head><meta charset='UTF-8'></head><body><h1>It's is a not GET request</h1></body></html>".getBytes();
            }
            writeResponse(acceptRanges,answer,type);
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

    private void writeResponse(String acceptRanges,byte [] data,String type) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                acceptRanges+
                "Server: ArturServer/2018-02-23\r\n" +
                "Content-Type: "+type+"\r\n" +
                "Content-Length: " + data.length + "\r\n"+
                "Connection: close\r\n\r\n";
        os.write(response.getBytes());
        os.write(data);
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

    private byte[] readPicture(String name){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
            BufferedImage image = ImageIO.read(new File(name));

            // явно указываем расширение файла для простоты реализации
            ImageIO.write(image, "jpg", baos);
            baos.flush();

            String base64String = Base64.encode(baos.toByteArray());
            String result = baos.toString("UTF-8");
            baos.close();

            // декодируем полученную строку в массив байт
            byte[] resByteArray = Base64.decode(base64String);
            return resByteArray;

        }
        catch (Exception e){
        }
        return null;
       // ImageIO.write(imag, "jpg", new File(dirName,"snap.jpg"));
    }
}
