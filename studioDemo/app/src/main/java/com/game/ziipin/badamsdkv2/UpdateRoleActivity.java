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
 * 作者:邱雷(Hanker) on 18/1/17 16:30.
 * Email:qiulei@ziipin@.com
 */

public class UpdateRoleActivity extends Activity {
    private EditText mLevel;
    private Button mUpdateRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        final UserBean user = (UserBean) getIntent().getSerializableExtra("user");

        BadamSdk.getInstance().initActivity(this,  BaseApp.LANG, new InitListener() {
            @Override
            public void onInitResult(boolean success, int erro, String message) {
                Logger.debug( success ? "初始化成功" : "初始化失败" + message);
            }
        });

        mLevel = (EditText) findViewById(R.id.edt_level);
        mUpdateRole = (Button) findViewById(R.id.btn_updateRole);

        mUpdateRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String levelStr = mLevel.getText().toString().trim();
                if (TextUtils.isEmpty(levelStr) || ("").equals(levelStr)) {
                    ToastUtil.showLongToast(UpdateRoleActivity.this, "请输入角色等级！");
                } else if (user == null) {
                    ToastUtil.showLongToast(UpdateRoleActivity.this, "请先登录！");
                }
                int level = Integer.parseInt(levelStr);

                Logger.debug("Hanker", "mUpdateRole onclick...  openid = " + user.openid + "   token = " + user.token);


                UserReq req = new UserReq(UpdateRoleActivity.this);
                req.appid = BaseApp.mAppId;
                req.openid = user.openid;
                req.token = user.token;
                req.roleid = "1234";//角色ID 客户端提供
                req.name = "test";
                req.level = level;
                req.appArea = "test 1区";

                updateRoleInfo(req);
            }
        });
    }

    private void updateRoleInfo(final UserReq userReq) {

        BadamSdk.getInstance().addRole(UpdateRoleActivity.this, userReq, new InitListener() {
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
