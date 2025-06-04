package esprit.collaborative_space.entity;


public class ChatMessage {
    private String type;     // "chat" or "signal"
    private String from;     // sender session ID or username
    private String to;       // recipient session ID
    private String content;  // message text or signaling data

    public ChatMessage() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
