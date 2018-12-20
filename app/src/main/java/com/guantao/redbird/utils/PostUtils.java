package com.guantao.redbird.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.guantao.redbird.bean.Url;
import com.guantao.redbird.bean.dbbean.ClueRegistration;
import com.guantao.redbird.myapplication.MainActivity;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 请求工具类
 */
public class PostUtils {
    /**
     * 发送post请求
     * @param url
     * @param map
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Callback.Cancelable Post(String url, Map<String,Object> map, Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        params.setConnectTimeout(50000);

        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(),entry.getValue());
            }
        }

        Callback.Cancelable cancelable = x.http().get(params,callback); //x.http().post(params, callback);*/
        //RequestParams entity = new RequestParams("https://www.baidu.com");
        //数据请求，这里先要设置回到的call接口对象,数据在接口对象的方法中获取
        //Callback.Cancelable cancelable = x.http().post(entity, callback);
        return cancelable;
    }

    public static <T> Callback.Cancelable Post2(String url, Map<String,Object> map, Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        params.setConnectTimeout(50000);

        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(),entry.getValue());
            }
        }

        //Callback.Cancelable cancelable = x.http().get(params,callback); //x.http().post(params, callback);*/
        //RequestParams entity = new RequestParams("https://www.baidu.com");
        //数据请求，这里先要设置回到的call接口对象,数据在接口对象的方法中获取
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 上传图片
     * @param listPaths
     * @param context
     * @return
     */
    public static void upLoadImg(List<String> listPaths, final Context context)
    {
        List<KeyValue> list = new ArrayList<KeyValue>();
        for(int i=0;i<listPaths.size();i++)
        {
            String filePath = listPaths.get(i).toString();
            File file = new File(filePath);
            list.add(new KeyValue(file.getName(), file));
        }

        MultipartBody body = new MultipartBody(list, "UTF-8");

        RequestParams params = new RequestParams(Url.UPLOAD_IMAGE_URL);
        //上传代码
        params.setRequestBody(body);
//        params.addBodyParameter("file", new File(realPathFromUri));
//        params.addBodyParameter("msg", "uplaodimg");
        params.setMultipart(true);
        params.addHeader("head", "android");
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("上传中···");
        dialog.setCancelable(false);
        dialog.show();
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                //加载成功回调，返回获取到的数据
                try {
                    JSONObject obj = new JSONObject(result);
                    String msg = obj.getString("msg");
                    if (msg.equals("1")) {
                        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("上传成功！");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("上传失败！错误：'" + ex.toString() + "'");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * 上传图片
     * @param listPaths
     * @param bean
     * @param context
     */
    public static void upLoadImg(List<String> listPaths, final ClueRegistration bean, final Context context)
    {
        List<KeyValue> list = new ArrayList<KeyValue>();
        for(int i=0;i<listPaths.size();i++)
        {
            String filePath = listPaths.get(i).toString();
            File file = new File(filePath);
            list.add(new KeyValue(file.getName(), file));
        }

        MultipartBody body = new MultipartBody(list, "UTF-8");

        RequestParams params = new RequestParams(Url.UPLOAD_IMAGE_URL);
        //上传代码
        params.setRequestBody(body);
//        params.addBodyParameter("file", new File(realPathFromUri));
//        params.addBodyParameter("msg", "uplaodimg");
        params.setMultipart(true);
        params.addHeader("head", "android");
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("上传中···");
        dialog.setCancelable(false);
        dialog.show();
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(final String result) {
                dialog.dismiss();
                //加载成功回调，返回获取到的数据
                try {
                    JSONObject obj = new JSONObject(result);
                    String msg = obj.getString("msg");
                    if (msg.equals("1")) {
                        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("上传成功！");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bean.setIs_apply_photo(1);
                                try {
                                    MainActivity.getApplicationDb().update(bean);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("上传失败！错误：'" + ex.toString() + "'");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }



    /**
     *
     * @param listPaths  文件路径
     * @param clueId  线索编号
     */
    public static  List<String> renameFileNames(List<String> listPaths,String clueId)  //List<String>
    {
        List<String> listNewPaths = new ArrayList<String>();
        for(int i=0;i<listPaths.size();i++)
        {
            String filePath = listPaths.get(i).toString();            //oldFilePath
            File oldFile = new File(filePath);
            String sFileName = oldFile.getName();
            int nIndex = sFileName.indexOf("_");
            String sValue =  clueId + sFileName.substring(nIndex, sFileName.length());
            String[] sPaths = filePath.split("/");
            String newFileName = "";
            for(int m=0;m<sPaths.length;m++)
            {
                if(m==sPaths.length-1)
                {
                    newFileName=newFileName+"/"+sValue;
                    break;
                }
                if(sPaths[m]==null || sPaths[m].equals(""))
                    continue;
                newFileName = newFileName +"/"+sPaths[m];
            }
            //执行重命名
            oldFile.renameTo(new File(newFileName));
            listNewPaths.add(newFileName);
        }
        return listNewPaths;
    }


}
