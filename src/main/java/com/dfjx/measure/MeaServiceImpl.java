package com.dfjx.measure;

import javafx.beans.binding.ObjectExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sc on 2018/5/7.
 */
@Transactional
@Service("MeaService")
public class MeaServiceImpl implements MeaService {

    @Resource
    private MeaMapper meaMapper;

    @Override
    public List<Map<String, Object>> reCol() {
        return meaMapper.reCol();
    }

    @Override
    public List<Map<String, Object>> reBscMea() {

        return meaMapper.reBscMea();
    }

    @Override
    public List<Map<String, Object>> reMeaName(String meaName) {

        return meaMapper.reMeaName(meaName);
    }



    @Override
    public List<Map<String, Object>> reMea() {
        //真正返回数据的集合
        List<Map<String,Object>> list1 = new LinkedList<>();
        //用来存储基础指标的集合 从sql查出的集合
        List<Map<String,Object>> list2 = this.reBscMea();
        //List<Map<String,Object>> list3 = meaService.reMeaName();
        for(Map m :list2){
            Map<String,Object> map = new HashMap<>();
            //去过括号的集合
            List<Map<String,Object>> list3 = this.fiterKuo((String) m.get("MEASURE_ID"));
            //list3.get(1).get("SOURCE_ID")
            //(meaMapper.reSouceId((String) m.get("MEASURE_ID")))  这部分主要获取 Mapper获取的 id
            // .get("SOURCE_ID") 获取 source_id
            //map.put("category_id",(meaMapper.reSouceId((String) m.get("MEASURE_ID"))).get("SOURCE_ID"));
            map.put("category_id",m.get("MEASURE_ID"));
            //map.put("category_id",m.get("MEASURE_ID"));
            map.put("category_name", m.get("MEASURE_NAME"));
            //拿到tableName:bsc_measure 的 "MEASURE_ID"

            map.put("measures",list3);

            list1.add(map);
        }
        return list1;
    }


    @Override
    public List<Map<String,Object>> fiterKuo(String meaName){


        //源数据
        List<Map<String,Object>> list1 = this.reMeaName(meaName);
        //新容器
        List<Map<String,Object>> list2 = new LinkedList<>();

        for(Map map:list1){
            Map map1 = new HashMap();
            //去括号
            //map1.put("formula_desc",((String)map.get("FORMULA_DESC")).substring(1,((String)map.get("FORMULA_DESC")).length()-1));
            map1.put("measure_id",map.get("MEASURE_ID"));
            map1.put("measure_name",map.get("MEASURE_NAME"));
            map1.put("measure_desc",map.get("MEASURE_DESC"));
            //根据数据库的数据判断指标时间粒度
            if (1==this.meaMapper.reDim((String)map.get("MEASURE_ID")).size()){
                if (4 == this.meaMapper.reDim((String) map.get("MEASURE_ID")).get(0)){
                    map1.put("time_dim","year");
                }else{
                    map1.put("time_dim","month");
                }
            }else{
                map1.put("time_dim","year,month");
            }

            //map1.put("SOURCE_ID",map.get("SOURCE_ID"));
            list2.add(map1);
        }
        System.out.println(list2);
        return list2;
    }

    @Override
    public List<Map<String, Object>> reNameMap(String id) {
        //获取详细指标
        //String name = "C20000";
        List<Map<String,Object>> list1 = this.reMeaName(id);
        //给数据进行更名，然后返回
        List<Map<String,Object>> list2 = new LinkedList<>();
        for(Map m:list1){
            Map<String,Object> map1 = new HashMap();
            map1.put("measure_id",m.get("MEASURE_ID"));
            map1.put("measure_name",m.get("MEASURE_NAME"));
            list2.add(map1);
        }
        return list2;
    }



    public List<Map<String, Object>> reMeaSource(Map meaId,String fromYear,String toYear) {

        return null;
    }

/*    //第二层是map的
    public Map<String, Object> reMeaSourceJson1(String[] meaId,String from,String to) {
        int length = from.length();
        Map map2 = new HashMap();
        //源数据
        for (int i=0;i<meaId.length;i++){

            List<Map<String,Object>> list = meaMapper.reMeaSource(meaId[i],from,to,length);
            //当条件有误导致没有返回数据的时候 则返回如下信息
            if(list.size()==0 || meaId.length==0){
                Map<String,Object> map = new HashMap();
                map.put("return_code","-2");
                return map;
            }
            //最里层数据List<map>
            List<Map<String,Object>> list1 = new LinkedList<>();
            for(Map m:list){
                Map map = new HashMap();
                map.put("object_id",m.get("OBJECT_ID"));
                map.put("object_name",m.get("OBJECT_NAME"));
                map.put("month_id",m.get("MONTH_ID"));
                map.put("value",m.get("VALUE"));
                list1.add(map);
            }
            //
            Map map1 = new HashMap();
            map1.put("measure_id",meaId);
            map1.put("measure_name",list.get(0).get("MEASURE_NAME"));
            map1.put("measures",list1);

            //最外层是一层Map
            //Map map2 = new HashMap();
            map2.put("return_code" ,"0");
            map2.put("measure",map1);
        }

        return map2;
    }*/




    //
    @Override
    public Map<String, Object> reMeaSourceJson(String[] meaId,String from,String to) {
        if (meaId==null|| meaId.length==0){
            Map<String,Object> map = new HashMap();
            map.put("return_code","-2");
            return map;
        }else if(from.length()!=to.length()){
            Map<String,Object> map = new HashMap();
            map.put("return_code","-2");
            return map;
        }
        //根据起止时间的长度来判断查询是四位时间段还是六位的时间段
        int length = from.length();
        //最外层
        Map map2 = new HashMap();
        //第二层数据
        List<Map<String,Object>> list3 = new LinkedList<>();
       // Map map1 = new HashMap();

        //最内层数据封装类
        List<List<Map<String,Object>>> list0 = new LinkedList<>();
        //中间层数据封装类
        List<List<Map<String,Object>>> list01 = new LinkedList<>();
        for (int i=0;i<meaId.length;i++){
            //源数据
            List<Map<String,Object>> list = meaMapper.reMeaSource(meaId[i],from,to,length);

            //当条件有误导致没有返回数据的时候 则返回如下信息
            if(list.size()==0){
                Map<String,Object> map = new HashMap();
                map.put("return_code","-2");
                return map;
            }
            //最里层数据List<map>
            List<Map<String,Object>> list1 = new LinkedList<>();
            for(Map m:list){
                Map map = new HashMap();
                map.put("object_id",m.get("OBJECT_ID"));
                map.put("object_name",m.get("OBJECT_NAME"));
                map.put("month_id",m.get("MONTH_ID"));
                map.put("value",m.get("VALUE"));
                list1.add(map);
            }
            list0.add(list1);

            //第二层也是List<Map>
            //List<Map<String,Object>> list3 = new LinkedList<>();
            //Map map1 = new HashMap();

                Map map1 = new HashMap();
                map1.put("measure_id", meaId[i]);
                map1.put("measure_name", list.get(0).get("MEASURE_NAME"));
                System.out.println(list.get(0).get("MEASURE_NAME"));
                map1.put("measures", list0.get(i));
                list3.add(map1);

            System.out.println(list0.size());
        }
        //最外层是一层Map
        //Map map2 = new HashMap();
        map2.put("return_code" ,"0");
        map2.put("measure",list3);

        return map2;
    }

    //算法待研究
/*    public Map<String, Object> reMeaSourceJson1(String[] meaId,String from,String to) {
        int length = from.length();
        //源数据
        List<Map<String,Object>> list = meaMapper.reMeaSourceList(meaId,from,to,length);
        //当条件有误导致没有返回数据的时候 则返回如下信息
        if(list.size()==0 || meaId.length==0){
            Map<String,Object> map = new HashMap();
            map.put("return_code","-2");
            return map;
        }


*//*        //最里层数据List<map>
        List<Map<String,Object>> list1 = new LinkedList<>();
        for(Map m:list){
            Map map = new HashMap();
            map.put("object_id",m.get("OBJECT_ID"));
            map.put("object_name",m.get("OBJECT_NAME"));
            map.put("month_id",m.get("MONTH_ID"));
            map.put("value",m.get("VALUE"));
            list1.add(map);
        }*//*

        //最里层数据List<List<map>> 这里为分区的list
        List<List<Map<String,Object>>> list0 = new LinkedList();
        //最里层数据List<List<map>>
        List<Map<String,Object>> list1 = new LinkedList<>();
        for(int i=0;i<list.size()-1 ;i++){
                if (!(list.get(i).get("MEASURE_ID").equals(list.get(i+1).get("MEASURE_ID")))){
                //拿到一个分区的List<Map>\
                //程序计数器 c
                int c = 0;
                for (int j=0+c;j<=i;){
                    Map ma = new HashMap();
                    ma.put("object_id",list.get(j).get("OBJECT_ID"));
                    ma.put("object_name",list.get(j).get("OBJECT_NAME"));
                    ma.put("month_id",list.get(j).get("MONTH_ID"));
                    ma.put("value",list.get(j).get("VALUE"));
                    list1.add(ma);
                    c++;
                }
                list0.add(list1);
            }
        }
        System.out.println(list0);
        //第二层数据List<Map>
        List<Map<String,Object>> list2 = new LinkedList<>();
        Map m = new HashMap();
        m.put("measure_id",list.get(0).get("MEASURE_ID"));
        m.put("measure_name",list.get(0).get("MEASURE_NAME"));
        m.put("values",list0.get(0));
        list2.add(m);
        for(int i=0;i<list.size()-1 ;i++){
            if (!list.get(i).get("MEASURE_ID").equals(list.get(i+1).get("MEASURE_ID"))){
                    Map map = new HashMap();
                    map.put("measure_id",list.get(i+1).get("MEASURE_ID"));
                    map.put("measure_name",list.get(i+1).get("MEASURE_NAME"));
                    map.put("values",list0.get(i+1));
                    list2.add(map);
            }
        }
        System.out.println(list2);
*//*        //第二层数据List<Map>  ---这个写法已经不能够适用
        List<Map<String,Object>> list2 = new LinkedList<>();
        for(int i=0;i<meaId.length;i++){

            Map map1 = new HashMap();
            map1.put("measure_id",meaId[i]);
            //map1.put("measure_name",list.get(0).get(""));
            map1.put("values",list1);
            list2.add(map1);
        }*//*
        //最外层是一层Map
        Map map2 = new HashMap();
        map2.put("return_code" ,"0");
        map2.put("measureList",list2);

        return map2;
    }*/

    @Override
    public List<Map<String, Object>> reMeaSys() {

        return meaMapper.reMeaSys();
    }

    @Override
    public List<Map<String, Object>> reMeaSystem() {
        //最内层数据为Lsit<Map> 这层为源数据
        List<Map<String,Object>> list1 = this.reMeaSys();
        //最内存数据重新命名
        List<Map<String,Object>> list2 = new LinkedList<>();
        //最外层也是一个List<map>
        List<Map<String,Object>> list3 = new LinkedList<>();
        //获取所有父类节点
        List<Map<String,Object>> list4 = meaMapper.reParent();
        for(Map m :list1){
            Map<String,Object> map = new HashMap();
            Map<String,Object> map1 = new HashMap<>();
            map.put("measure_id",m.get("MEASURE_ID"));
            map.put("measure_name",m.get("MEASURE_NAME"));
            list2.add(map);
/*            map1.put("category_id",list4.get());
            map1.put("category_name",m.get("CATEGORY_NAME"));*/
        }
        //构建最外层的数据
        int i = 0;
        for(Map m : list1){
            Map<String,Object> map = new HashMap<>();
            map.put("category_id",list4.get(i).get("MEASURE_ID"));
            map.put("category_name",list4.get(i).get("MEASURE_NAME"));
            map.put("measures","");
            i++;
        }

        return null;
    }

    public List<Map<String,Object>> reMeaSystem1(){
        int i = 0;
        //首先拿到源数据List<Map>
        List<Map<String,Object>> list1 = new LinkedList<>();
        //创建最外层数据
        List<Map<String,Object>> list2 = new LinkedList<>();

        for(Map m:list1){
            Map map = new HashMap();
            map.put("category_id",m.get("CATEGORY_ID"));
            map.put("category_name",m.get("CATEGORY_NAME"));

            while (list1.get(i).get("CATEGORY_ID")==list1.get(i+1).get("CATEGORY_ID")){
                Map map1 = new HashMap();
                map1.put("measure_id","MEASURE_ID");
                map1.put("measure_name","MEASURE_NAME");
                i++;
                //创建最内层数据
                List<Map<String,Object>> list3 = new LinkedList<>();
                list3.add(map1);
            }
            map.put("measures","");


        }
        return  null;
    }
}
