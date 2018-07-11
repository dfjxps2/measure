package com.dfjx.measure;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sc on 2018/5/21.
 * 异常处理的类
 */
@Controller
public class MainsiteErrorController implements ErrorController{

    private static final String ERROR_PATH = "/error";

    @ResponseBody
    @RequestMapping(value = ERROR_PATH)
    public DataResult handleError(HttpServletResponse response){
        DataResult rs = new DataResult();
        if(response.getStatus() == 404){
            rs.setCode(0);
            rs.setError("请求路径有误");
        }
        if(response.getStatus() == 500){
            rs.setCode(0);
            rs.setError("服务器内部出错");
        }

        return rs;
    }

/*
    改动前的备份
    @ResponseBody
    @RequestMapping(value=ERROR_PATH)
    public Map handleError(HttpServletResponse response){
        Map<String,Object> map = new HashMap();
        if (response.getStatus() == 404){
            map.put("return_code","404");
        }else if (response.getStatus() == 500){
            map.put("return_code","500");
        }else{
            map.put("return_code","-2");
        }
        return map;
    }*/

/*    @ResponseBody
    @RequestMapping(value=ERROR_PATH)
    public Map handleError(){
        Map<String,Object> map = new HashMap();
        return map;
    }*/

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
