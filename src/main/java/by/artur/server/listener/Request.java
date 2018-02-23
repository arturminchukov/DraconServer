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
}
