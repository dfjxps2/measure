package com.dfjx.measure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sc on 2018/5/7.
 */
@RestController
@RequestMapping("/services")
public class MeaController {

    @Resource(name = "MeaService")
    private MeaService meaService;

    @RequestMapping(value = "/reCol")
    public List<Map<String,Object>> reCol(){
        List<Map<String,Object>> list = meaService.reBscMea();
        for(Map m :list){
            System.out.println(m.get("MEASURE_ID"));
        }
        return meaService.reBscMea();
    }


    public List<Map<String,Object>> reMeaName(String meaName) {


        return meaService.reMeaName(meaName);
    }


    @RequestMapping("/reMeaSys")
    @ResponseBody
    public List<Map<String,Object>> reMea(){
/*        //真正返回数据的集合
        List<Map<String,Object>> list1 = new LinkedList<>();
        //用来存储基础指标的集合
        List<Map<String,Object>> list2 = meaService.reBscMea();
        //List<Map<String,Object>> list3 = meaService.reMeaName();
        for(Map m :list2){
            Map<String,Object> map = new HashMap<>();
            map.put("指标id", m.get("SOURCE_ID"));
            map.put("指标分类名称", m.get("MEASURE_NAME"));
            map.put("指标名", meaService.reMeaName((String) m.get("MEASURE_ID")));
            list1.add(map);
        }

        System.out.println(list2);*/
        return meaService.reMea();
    }

    @RequestMapping(value = "/test")
    public String test(){
        String i = "{abc_c}";
        int l = i.length();
        String s = i.substring(1,i.length()-1);
        System.out.println(s);
        //meaService.fiterKuo(s);
        return null;
    }

    @RequestMapping("/measures")
    public Map<String,Object> reMeaSource(String[] meaId,String from,String to){

        if (from==null|| from==" "||"null".equals(from)){
            Map map = new HashMap();
            map.put("return_code","-2");
            return map;
        }
        if(to==null||to==" "||"null".equals(to)){
            if(from.length()==4){
                to = new SimpleDateFormat("yyyy").format(new Date());
            }else if(from.length()==6){
                to = new SimpleDateFormat("yyyyMM").format(new Date());
            }
        }
        return meaService.reMeaSourceJson(meaId,from,to);
    }

    @RequestMapping("/meaTest")
    public List<Map<String,Object>> reMeaSourceTest(){


        //return meaService.reMeaSource("C40003","2016");
        return null;
    }
}
