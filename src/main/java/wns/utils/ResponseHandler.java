package wns.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wns.constants.Messages;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Messages message) {

        Map<String, Object> map = new HashMap<>();
        map.put("response_code", message.getCode());
        map.put("message", message.getValue());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    public static ResponseEntity<Object> generateResponse(Messages message,String redirect_url) {

        Map<String, Object> map = new HashMap<>();
        map.put("response_code", message.getCode());
        map.put("message", message.getValue());
        map.put("redirect_url", redirect_url);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }



    public static ResponseEntity<Object> generateResponse(Messages message,String redirect_url, Object responseObj) {

        Map<String, Object> map = new HashMap<>();
        map.put("response_code", message.getCode());
        map.put("message", message.getValue());
        map.put("redirect_url", redirect_url);
        map.put("data", responseObj);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
