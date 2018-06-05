package com.dfjx.measure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sc on 2018/5/11.
 */
/*@RestController
@ControllerAdvice*/
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger("ExceptionHandler");

/*    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return e.getMessage();
    }*/

/*    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Map<String,Object> defaultErrorHandler(HttpServletRequest req, HttpServletResponse response,Exception e) throws Exception {
        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        Map map = new HashMap();
        if (response.getStatus()==500){
           map.put("return_code","-1");
            e.printStackTrace();
       }else if (response.getStatus()==404){
           map.put("return_code","-1");
            e.printStackTrace();
       }
        map.put("return_code","-1");
       e.printStackTrace();
        return map;
    }*/

/*    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("", e);
        ResponseData r = new ResponseData();
        r.setMessage(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            r.setCode(404);
        } else {
            r.setCode(500);
        }
        r.setData(null);
        r.setStatus(false);
        return r;
    }*/
}
