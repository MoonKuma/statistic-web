package com.sincetimes.statisticweb.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MoonKuma
 * @since 2019/4/26
 *
 * jqGrid take in data of List<JSONObject>
 */

@Data
public class JqgridData {

    final private List<JSONObject> data = new ArrayList<JSONObject>();

    public void addJsonRow(JSONObject jsonObject){
        if(jsonObject!=null){
            data.add(jsonObject);
        }
    }
}
