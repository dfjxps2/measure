package com.dfjx.measure;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Sc on 2018/5/7.
 */
@Transactional
public interface MeaService {

    //返回字段
    public List<Map<String,Object>> reCol();

    //返回tablename:bsc_measure的值
    public List<Map<String,Object>> reBscMea();

    //返回tablename:bsc_measure中的measure_name formula_desc的值
    public List<Map<String,Object>> reMeaName(String meaName);

    //给集合重命名
    public List<Map<String,Object>> reNameMap(String id);

    //返回对方要的数据
    public List<Map<String,Object>> reMea();

    //去括号
    public List<Map<String,Object>> fiterKuo(String meaName);

    //返回指标数据
    List<Map<String,Object>> reMeaSource(Map meaId,String fromYear,String toYear);

    //按对方所需要的json格式返回指标数据
    Map<String,Object> reMeaSourceJson(String[] meaId,String fromYear,String toYear);

    //新的返回指标体系的方法
    List<Map<String,Object>> reMeaSys();

    //按规格返回指标体系
    List<Map<String,Object>> reMeaSystem();

    List<MeasureEntity> reMea1();

}
