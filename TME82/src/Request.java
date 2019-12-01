public class Request {

    // ----- Attributes -----

    private Client client;
    private int type;
    private String content;

    // ----- Constructors -----

    public Request(Client client, int type, String content) {
        this.client = client;
        this.type = type;
        this.content = content;
    }

    // ----- Getter -----

    public Client getClient() {
        return client;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    // ----- Setter -----

    public void setClient(Client client) {
        this.client = client;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
