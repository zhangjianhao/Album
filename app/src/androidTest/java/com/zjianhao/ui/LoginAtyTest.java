package com.zjianhao.ui;

import android.content.Intent;
import android.test.InstrumentationTestCase;

import com.zjianhao.constants.Constants;

/**
 * Created by 张建浩（Clarence) on 2016-7-3 21:09.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 */
public class LoginAtyTest extends InstrumentationTestCase {
   private LoginAty loginAty;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.setClassName("com.zjianhao.album", LoginAty.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loginAty = (LoginAty) getInstrumentation().startActivitySync(intent);
    }



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        loginAty = null;


    }

    public void testLogin(){
        loginAty.login("123","123", Constants.LOGIN_URL);
    }
}
