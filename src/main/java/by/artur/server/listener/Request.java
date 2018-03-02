package by.artur.server.listener;

public class Request {
    private String requestType;
    private String requestFile;

    public boolean isGetType() {
        return requestType.equals("GET");
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestFile() {
        return requestFile;
    }

    public void setRequestFile(String requestFile) {
        this.requestFile = requestFile;
    }

    public String getExtenFile() {
        if(requestFile.contains(".")) {
            int pos = requestFile.indexOf(".");
            String exten = requestFile.substring(pos+1);
            return exten;
        }
        else
            return "";
    }
}
