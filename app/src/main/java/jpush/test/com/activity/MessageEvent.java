package jpush.test.com.activity;

/**
 * Created by jesgoo on 2019/8/1.
 */

public class MessageEvent {

    private String message;
    public  MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
