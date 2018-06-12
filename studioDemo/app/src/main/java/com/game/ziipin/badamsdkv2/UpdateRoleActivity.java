package com.game.ziipin.badamsdkv2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abc.def.ghi.InitListener;
import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.modle.UserBean;
import com.ziipin.pay.sdk.library.modle.UserReq;
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
        } else if (user == null) {
            ToastUtil.showLongToast(this, "请先登录！");
            return;
        }
        int level = Integer.parseInt(levelStr);

        Logger.debug("mUpdateRole onclick...  openid = " + user.openid + "   token = " + user.token);

        UserReq req = new UserReq(this);
        req.appid = BaseApp.mAppId;
        req.openid = user.openid;
        req.token = user.token;
        req.roleid = "1234";//角色ID 客户端提供
        req.name = "test";
        req.level = level;
        req.appArea = "test 1区";
        updateRoleInfo(req);
    }

    private void updateRoleInfo(final UserReq userReq) {

        BadamSdk.getInstance().addRole(this, userReq, new InitListener() {
            @Override
            public void onInitResult(boolean succeed, int errorCode, String message) {
                Logger.debug("updateRoleInfo ...  succeed = " + succeed + "     message = " + message);
                if (succeed) {
                    ToastUtil.showLongToast(UpdateRoleActivity.this, "角色上报成功！");
                } else {
                    ToastUtil.showLongToast(UpdateRoleActivity.this, "角色上报失败！" + message);
                }
                finish();
            }

        });


    }

}
