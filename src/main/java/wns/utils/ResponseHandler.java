package wns.utils;

import org.springframework.http.ResponseEntity;
import wns.constants.Messages;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Messages message,String data, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message.getValue());
        map.put("status", message.getStatus());
        map.put(data, responseObj);
        return new ResponseEntity<>(map, message.getStatus());
    }

    public static ResponseEntity<Object> generateResponse(Messages message) {

        Map<String, Object> map = new HashMap<>();
        map.put("message", message.getValue());
        map.put("status", message.getStatus());
        return new ResponseEntity<>(map, message.getStatus());
    }
}
