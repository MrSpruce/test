package com.mm.dss.demo.push;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.business.adapter.DataAdapteeImpl;
import com.android.business.adapter.DataAdapterInterface;
import com.android.business.callback.IMessageCallback;
import com.android.business.common.BroadCase;
import com.android.business.exception.BusinessException;
import com.example.dhcommonlib.log.LogHelper;
import com.mm.dss.demo.group.ChannelFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by 17801 on 2017/3/6.
 */
public class DSSPush implements IMessageCallback {

    private static final String TAG = DSSPush.class.getSimpleName();
    private Context mContext;
    /**把通知和报警消息队列分开，防止报警太多，通知消息处理不及时的问题；**/
    /**通知消息队列**/
    private LinkedList<String> msgList = new LinkedList<>();
    private final byte[] threadLock = new byte[]{};
    /**报警消息队列**/
    private LinkedList<String> alarmList = new LinkedList<>();
    private final byte[] alarmLock = new byte[]{};

    private boolean bQuit = false;

    public boolean init(Context context) throws BusinessException {
        mContext = context;
        DataAdapterInterface dataAdapterInterface = DataAdapteeImpl.getInstance();
        try {
            dataAdapterInterface.registerMessageCallback(this);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        initMsgLooper();
        return true;
    }

    private void initMsgLooper() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!bQuit) {
                    String callMsg = "";
                    synchronized (threadLock) {
                    if (msgList.size() > 0) {
                        callMsg  = msgList.pop();
                    }
                    }
                    if (!callMsg.equals("")) {
                        dealCallbackMsg(callMsg);
                    }
                    callMsg = "";
                    synchronized (alarmLock) {
                        if (alarmList.size() > 0) {
                            callMsg = alarmList.pop();
                        }
                    }
                    if (!callMsg.equals("")) {
                        dealCallbackMsg(callMsg);
                    }

                    if (msgList.isEmpty() && alarmList.isEmpty()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void callback(String jsonMsg) {
        synchronized (threadLock) {
            String msg = new StringBuilder().append(jsonMsg).toString();
            msgList.push(msg);
        }
    }

    private void dealCallbackMsg(String jsonMsg) {
        int cmd ;
        try {
            JSONObject jsonObj = new JSONObject(jsonMsg);
            cmd = jsonObj.optInt("cmd");
            notify(cmd, jsonMsg);
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设备推送
     */
    private void notify(int type, String content) throws BusinessException {
        try {
            JSONObject json = new JSONObject(content);
            PushMessageCode cmd = PushMessageCode.valueOf(type);
            switch (cmd) {
                case DPSDK_CMD_CHNL_STATUS_NOTIFY:
                {
                    String channelId = json.optString("id");
                    int state = json.optInt("status");
                    LogHelper.debug("DPSDK_CMD_CHNL_STATUS_NOTIFY : " + "channelId: " + channelId + " state: " + state);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("channelId", channelId);
                    bundle.putInt("state", state);
                    BroadCase.send(BroadCase.Action.DEVICE_ACTION_PUSH_MODIFY_DEVICE_STATE, bundle, mContext);
                }
                break;
            }
        } catch (JSONException e) {
            LogHelper.debug("device notify jsonException:" + e.toString());
        }
    }

}
