package com.dfjx.measure;

import oracle.net.aso.n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sc on 2018/5/7.
 */
@RestController
@RequestMapping("/services")
public class MeaController {

    Logger logger = LoggerFactory.getLogger(MeaController.class);

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
/*
    2018.7.2按要求修改 为新类型的方法
    @RequestMapping("/reMeaSys")
    @ResponseBody
    public List<Map<String,Object>> reMea(){
        return meaService.reMea();
    }*/

    /**
     * 指标体系查询方法
     * @return 结果包在DataResult的Data数据中
     */
    @RequestMapping("/reMeaSys")
    public DataResult reMea(){
        DataResult rs = new DataResult();
        rs.setOk("OK");
        rs.setData(meaService.reMea());
        return rs;
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

    /**
     * 指标值查询
     * @param meaId 指标id
     * @param from  起始时间
     * @param to    截止时间
     * @return
     * 返回DataResult对象 真正的业务数据在此对象的 data属性中
     */
    @RequestMapping("/measures")
    public DataResult reMeaSource(String[] meaId,String from,String to){
        DataResult rs = new DataResult();
        //此处为逻辑处理 from如果为空就不进行查询
        if (from==null|| from==" "||"null".equals(from)){
            rs.setError("起始时间不能为空");
            logger.info(rs.getMsg()+
                    new SimpleDateFormat("yyyymmdd-HH:MM:SS").format(new Date()));
            return rs;
        }else if(from.length()!=to.length()){
            if (from.length()<=4){
                Integer i = Integer.valueOf(from);
                DecimalFormat df = new DecimalFormat("0000");
                from = df.format(i);
                to = new SimpleDateFormat("yyyy").format(new Date());
            }else if (4<from.length()&&from.length()<=6) {
                Integer i = Integer.valueOf(from);
                DecimalFormat df = new DecimalFormat("000000");
                from = df.format(i);
                to = new SimpleDateFormat("yyyyMM").format(new Date());
            }else {
                rs.setError("你输入的条件不符合规范");
                logger.info(rs.getMsg()+
                        new SimpleDateFormat("yyyymmdd-HH:MM:SS").format(new Date()));
                return rs;
            }
        }
        if (meaId==null|| meaId.length==0){
            rs.setError("请输入你要查询的指标");
            logger.info(rs.getMsg()+
                    new SimpleDateFormat("yyyymmdd-HH:MM:SS").format(new Date()));
            return rs;
        }

        rs.setOk("OK");
        rs.setData(meaService.reMeaSourceJson(meaId,from,to));
        logger.info(rs.getMsg()+
                new SimpleDateFormat("yyyymmdd-HH:MM:SS").format(new Date()));
        return rs;
    }



/*
     * 指标值查询
     * @param meaId 指标id
     * @param from  起止时间
     * @param to    截止时间
     * @return
     * 这个方法在2018.7.2按要求修改
     */
/*
    @RequestMapping("/measures")
    public Map<String,Object> reMeaSource(String[] meaId,String from,String to){

        //此处为逻辑处理 from如果为空就不进行查询
        if (from==null|| from==" "||"null".equals(from)){
            Map map = new HashMap();
            map.put("return_code","-2");
            return map;
        }
        //根据from长度来确定查询出数据的时间粒度
        if(to==null||to==" "||"null".equals(to)){
            if(from.length()==4){
                to = new SimpleDateFormat("yyyy").format(new Date());
            }else if(from.length()==6){
                to = new SimpleDateFormat("yyyyMM").format(new Date());
            }
        }
        return meaService.reMeaSourceJson(meaId,from,to);
    }*/

    @RequestMapping("/meaTest")
    public List<Map<String,Object>> reMeaSourceTest(){


        //return meaService.reMeaSource("C40003","2016");
        return null;
    }

    @RequestMapping("/reLog")
    public String reLog(){
        System.out.println("reLog方法被调用");
        return "ok";
    }

/*    @RequestMapping
    public String edit(){

        return "edit";
    }

    @RequestMapping
    public String adda(){

        return "add";
    }*/
}
