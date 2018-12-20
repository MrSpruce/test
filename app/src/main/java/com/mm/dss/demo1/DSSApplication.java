/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mm.dss.demo1;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.mm.dss.demo1.push.DSSPush;

public class DSSApplication extends Application {
    private static DSSApplication instance;

    public static synchronized DSSApplication getApplication() {
        if (instance == null) {
            throw new NullPointerException("app not create or be terminated!");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        loadLibrary();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void loadLibrary(){
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("dsl");
        System.loadLibrary("dslalien");
        System.loadLibrary("DPRTSPSDK");
        System.loadLibrary("PlatformRestSDK");
        System.loadLibrary("PlatformSDK");
        System.loadLibrary("netsdk");
        System.loadLibrary("CommonSDK");
    }
    //限制字体尺寸，避免受系统字体大小影响导致界面不适配
    // limit the font size to avoid the interface mismatch caused by system font size.
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        return resources;
    }

    private void init() throws Exception{

        DSSPush mDSSPush = new DSSPush();
        boolean bDSSOk = mDSSPush.init(this.getApplicationContext());
    }

    @Override
    public String getPackageName() {
        return super.getPackageName();
    }
}
