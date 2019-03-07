package com.dfjx.measure;

import java.util.List;

/**
 * Created by Sc on 2019/3/7.
 *
 * @Description:
 */
public class MeasureEntity {

    //指标名称
    private String measureName;

    //指标的单位
    private String measureUnit;

    //指标ID
    private String measureId;

    //指标父ID
    private String measurePid;

    //指标来源的单位机构
    private String measureFlag;

    //指标年月标记
    private String timeDim;

    //子指标
    private List<MeasureEntity > Measures;

    public MeasureEntity(String measureName, String measureUnit, String measureId, String measureFlag,String measurePid,String timeDim) {
        this.measureName = measureName;
        this.measureUnit = measureUnit;
        this.measureId = measureId;
        this.measureFlag = measureFlag;
        this.measurePid = measurePid;
        this.timeDim=timeDim;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public String getMeasureFlag() {
        return measureFlag;
    }

    public void setMeasureFlag(String measureFlag) {
        this.measureFlag = measureFlag;
    }

    public String getMeasurePid() {
        return measurePid;
    }

    public void setMeasurePid(String measurePid) {
        this.measurePid = measurePid;
    }


    public String getTimeDim() {
        return timeDim;
    }

    public void setTimeDim(String timeDim) {
        this.timeDim = timeDim;
    }

    public List<MeasureEntity> getMeasures() {
        return Measures;
    }

    public void setMeasures(List<MeasureEntity> measures) {
        Measures = measures;
    }
}
