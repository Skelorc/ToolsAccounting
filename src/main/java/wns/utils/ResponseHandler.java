package wns.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wns.constants.Messages;

import java.util.HashMap;
import java.util.Map;

import static wns.constants.Messages.CLIENT_CREATE;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Messages message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", message.getStatus());
        map.put("data", responseObj);

        return new ResponseEntity<>(map, message.getStatus());
    }
    public static ResponseEntity<Object> generateResponse(Messages message) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", message.getStatus());
        return new ResponseEntity<>(map, message.getStatus());
    }
}
