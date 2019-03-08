package com.dfjx.measure;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sc on 2019/3/7.
 *
 * @Description:
 */
@Service("JsonTree")
public class JsonTree {

    @Resource
    private MeaMapper meaMapper;

    private static List<MeasureEntity> getChild(String id, List<MeasureEntity> rootMenu) {
        // 子菜单
        List<MeasureEntity> childList = new ArrayList();
        for (MeasureEntity menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getMeasurePid().equals(id)) {
                childList.add(menu);
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (MeasureEntity menu : childList) {
            menu.setMeasures(getChild(menu.getMeasureId(), rootMenu));// 递归
        }

        // 判断递归结束
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    //构建json树
    public List<MeasureEntity> aga() {
        // TODO Auto-generated method stub
        List<MeasureEntity> treeData = meaMapper.reMeasure(); // 原始数据

        // 查看结果
        for (MeasureEntity mea : treeData) {
            //System.out.println(menu.toString());
        }

        List<MeasureEntity> meaList = new ArrayList(); // 树递归

        // 先找到所有的一级节点
        for (int i = 0; i < treeData.size(); i++) {
            // 一级节点的父节点为root
            if (treeData.get(i).getMeasurePid().equals("root")) {
                meaList.add(treeData.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (MeasureEntity mea : meaList) {
            mea.setMeasures(getChild(mea.getMeasureId(), treeData));
        }

        return meaList;
/*        Map<String,Object> jsonMap = new HashMap<String,Object>();
        jsonMap.put("measure", menuList);
       System.out.println(jsonMap);
        System.out.println(1);*/
    }
}
