package com.guantao.redbird.utils;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * 解析json数据
 */
public class GsonUtils {
    /**
     * 解析json数据
     * @param <T>
     */
    public static <T> T java2Bean(String json,Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * 将经纬度数组转换为json字符串
     * @param latLngList
     * @return
     */
    public static String latlngArrayList2String( ArrayList<LatLng> latLngList  )
    {
        if(latLngList.size()==0)
            return "";
        String sJson = "{  \"rings\":" + "[ [";
        for(int nIndex=0;nIndex<latLngList.size();nIndex++)
        {
            LatLng objLatLng = latLngList.get(nIndex);
            if(nIndex!=(latLngList.size()-1))
            {
                sJson=sJson+"[";
                sJson =sJson+ String.valueOf(objLatLng.latitude)+","+String.valueOf(objLatLng.longitude);
                sJson =sJson+  "],";
            }
            else
            {
                sJson=sJson+"[";
                sJson = sJson +String.valueOf(objLatLng.latitude)+","+String.valueOf(objLatLng.longitude);
                sJson = sJson + "]";
            }
        }
        sJson=sJson + "] ],";
        sJson =sJson +  "spatialReference:" + "{\"wkid\":4326}";   //WGS84坐标系

        sJson =sJson + "   }";
        return  sJson;

       /* {
            "rings":
            [
                    [
                            [38618274.09237155,4061402.1794967125],
                            [38618418.02599275,4061410.646180312],
                            [38618257.15900435,4061148.1789887114],
                            [38618197.892219156,4061283.645926312],
                            [38618274.09237155,4061402.1794967125]
                    ]
            ],
            "spatialReference":
            {"wkid":2362}
        }*/

    }



}
