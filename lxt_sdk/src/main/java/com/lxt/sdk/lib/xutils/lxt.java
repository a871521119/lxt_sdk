package com.lxt.sdk.lib.xutils;

import android.app.Application;
import android.content.Context;


import com.lxt.sdk.lib.xutils.common.TaskController;
import com.lxt.sdk.lib.xutils.common.task.TaskControllerImpl;
import com.lxt.sdk.lib.xutils.db.DbManagerImpl;
import com.lxt.sdk.lib.xutils.http.HttpManagerImpl;

import java.lang.reflect.Method;


/**
 * 任务控制中心, http, db注入等接口的入口.
 * 需要在在application的onCreate中初始化: lxt.Ext.init(this);
 */
public final class lxt {

    private lxt() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke lxt.Ext.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return Ext.app;
    }

    public static TaskController task() {
        return Ext.taskController;
    }

    public static HttpManager http() {
        if (Ext.httpManager == null) {
            HttpManagerImpl.registerInstance();
        }
        return Ext.httpManager;
    }


    public static DbManager getDb(DbManager.DaoConfig daoConfig) {
        return DbManagerImpl.getInstance(daoConfig);
    }

    public static class Ext {
        private static boolean debug;
        private static Application app;
        private static TaskController taskController;
        private static HttpManager httpManager;

        private Ext() {
        }

        public static void init(Application app) {
            TaskControllerImpl.registerInstance();
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }

        public static void setTaskController(TaskController taskController) {
            if (Ext.taskController == null) {
                Ext.taskController = taskController;
            }
        }

        public static void setHttpManager(HttpManager httpManager) {
            Ext.httpManager = httpManager;
        }

    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}