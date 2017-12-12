package gcm.heruijun.com.travelflangcm.data.protocol;

/**
 * Created by heruijun on 2017/12/11.
 */

public class ChatMessage {

    private String message;
    private boolean isLeftMessage;      // in production we should bind by user role

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLeftMessage() {
        return isLeftMessage;
    }

    public void setLeftMessage(boolean leftMessage) {
        isLeftMessage = leftMessage;
    }
}
