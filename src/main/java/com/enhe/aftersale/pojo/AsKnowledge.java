package com.enhe.aftersale.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.kuangkie.carbon.record.Updatable;
import com.kuangkie.carbon.record.annotation.Attribute;
import com.kuangkie.carbon.record.annotation.CarbonRecord;
import com.kuangkie.carbon.record.annotation.RecordCode;
import com.kuangkie.carbon.uid.model.UidManager;

// 知识库
@CarbonRecord("enhee14459")
public class AsKnowledge implements Updatable{
    @JSONField(name="唯一编码")
    @RecordCode
    private Long id= UidManager.getLongUID();

    // 问题描述
    @JSONField(name="问题描述")
    @Attribute("enhe14461")
    private String wtms问题描述;

    // 解决方案
    @JSONField(name="解决方案")
    @Attribute("enhe14462")
    private String jjfa解决方案;

    // 标签
    @JSONField(name="标签")
    @Attribute("enhe14463")
    private String bq标签;

    public String getJjfa解决方案() { 
        return jjfa解决方案;
    }
    public void setJjfa解决方案(String jjfa解决方案) {
        this.jjfa解决方案 = jjfa解决方案;
    }
    public String getWtms问题描述() { 
        return wtms问题描述;
    }
    public void setWtms问题描述(String wtms问题描述) {
        this.wtms问题描述 = wtms问题描述;
    }
    public Long getId() { 
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBq标签() { 
        return bq标签;
    }
    public void setBq标签(String bq标签) {
        this.bq标签 = bq标签;
    }
    @Override
    public String getRecordCode() {
        return this.id.toString();
    }
}