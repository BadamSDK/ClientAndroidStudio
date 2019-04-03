package com.game.ziipin.badamsdkv2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abc.def.ghi.InitListener;
import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.modle.UserBean;
import com.ziipin.pay.sdk.library.utils.Logger;
import com.ziipin.pay.sdk.library.utils.ToastUtil;


/**
 * 上报用户角色的 activity<br />
 * 这里把上报角色 api 独立到一个 activity 的目的是为了拆分代码，减少 {@link MainActivity} 中的代码逻辑复杂度<br />
 * 作者:邱雷(Hanker) on 18/1/17 16:30.<br />
 * Email:qiulei@ziipin@.com
 */

public class UpdateRoleActivity extends Activity implements View.OnClickListener{
    /**
     * 角色级别输入文本框
     */
    private EditText mLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        BadamSdk.getInstance().initActivity(this,  BaseApp.LANG, new InitListener() {
            @Override
            public void onInitResult(boolean success, int erro, String message) {
                Logger.debug( success ? "初始化成功" : "初始化失败" + message);
            }
        });

        mLevel = (EditText) findViewById(R.id.edt_level);
        // 上报 按钮
        Button mUpdateRole = (Button) findViewById(R.id.btn_updateRole);
        if(mUpdateRole != null){
            mUpdateRole.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        final UserBean user = (UserBean) getIntent().getSerializableExtra("user");
        String levelStr = mLevel.getText().toString().trim();
        if (TextUtils.isEmpty(levelStr) || ("").equals(levelStr)) {
            ToastUtil.showLongToast(this, "请输入角色等级！");
            return;
        } else if (user == null) {
            ToastUtil.showLongToast(this, "请先登录！");
            return;
        }
        int level = Integer.parseInt(levelStr);

        Logger.debug("mUpdateRole: openid = " + user.openid + "，token = " + user.token);
        String name = "test";     // 角色名
        String area = "test 1区"; // 区服
        String roleId = "1234";   // 角色id
        addRole(this, user.openid, name, area, roleId, level);
    }



    private void addRole(Context context, String openId, String name, String area, String roleId, int level) {
        BadamSdk.getInstance().addRole(context, BaseApp.APP_ID, openId, name, area, roleId, level);
    }

    @Override
    protected void onDestroy() {
        BadamSdk.getInstance().onDestroy(this);
        super.onDestroy();

    }
}
