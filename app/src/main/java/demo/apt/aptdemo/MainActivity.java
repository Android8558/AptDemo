package demo.apt.aptdemo;

import android.app.Activity;
import android.os.Bundle;

import demo.apt.aptlib.ContentViewId;
import demo.apt.contentview.ContentView;

/**
 * <p>主界面</p>
 *
 * @author liuzhaohong 2018/6/4 16:01
 * @version V1.0
 * @name MainActivity
 */
@ContentViewId(id = R.layout.activity_main,
title = "主界面")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentView.init(this);
    }
}
