package com.by5388.demo.xml;


import android.os.Handler;

import com.by5388.demo.common.CommonMainActivity;

import java.util.Collections;
import java.util.List;

/**
 * todo code复制来源 https://www.runoob.com/w3cnote/android-tutorial-xml.html
 * todo xml文件来源：https://www.w3school.com.cn/example/xdom/books.xml
 */
public class MainActivity extends CommonMainActivity {

    @Override
    public List<String> getCategories() {
        return Collections.singletonList(BuildConfig.APPLICATION_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final XmlApplication application = XmlApplication.getApplication();
        final Handler workHandler = application.getWorkHandler();
        workHandler.removeCallbacksAndMessages(null);
        application.getHandlerThread().quitSafely();
    }
}