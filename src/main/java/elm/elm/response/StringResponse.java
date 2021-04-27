package elm.elm.response;

import java.io.Serializable;
import java.util.HashMap;

public class StringResponse implements Serializable {
    final HashMap<String, String> body = new HashMap<>();

    public StringResponse() {
    }
    public StringResponse(String key,String value) {
        this.body.put(key,value);
    }

    public HashMap<String, String> getBody() {
        return this.body;
    }

    public void add(String key,String value){
        this.body.put(key,value);
    }

}
