package elm.elm.model;

import java.io.Serializable;
import java.util.HashMap;

public class MyResponse implements Serializable {

    final HashMap<String, String> body = new HashMap<>();

    public MyResponse() {
    }
    public MyResponse(String key,String value) {
        this.body.put(key,value);
    }

    public HashMap<String, String> getBody() {
        return this.body;
    }

    public void add(String key,String value){
        this.body.put(key,value);
    }


}
