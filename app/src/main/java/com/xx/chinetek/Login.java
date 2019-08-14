package com.xx.chinetek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.User.UerInfo;
import com.xx.chinetek.model.User.UserInfo;
import com.xx.chinetek.model.User.WareHouseInfo;
import com.xx.chinetek.util.Network.NetworkError;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.SharePreferUtil;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.DESUtil;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;
import com.zebra.android.devdemo.connectivity.ConnectivityDemo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ContentView(R.layout.activity_login)
public class Login extends BaseActivity{

    String TAG = "Loagin";
    String TAG_GetWareHouseByUserADF = "Login_GetWareHouseByUserADF";
    private static final int RESULT_GET_LOGIN_INFO        = 101;
    private static final int RESULT_GetWareHouseByUserADF = 102;



    @Override
    public void onHandleMessage(Message msg) {
        switch (msg.what) {
            case RESULT_GET_LOGIN_INFO:
                AnalysisJson((String) msg.obj);
                break;
            case RESULT_GetWareHouseByUserADF:
                AnalysisGetWareHouseByUserADFJson((String) msg.obj);
                break;
            case NetworkError.NET_ERROR_CUSTOM:
                ToastUtil.show("获取请求失败_____" + msg.obj);
                break;
        }
    }

    @ViewInject(R.id.txt_Verion)
    TextView txtVersion;
    @ViewInject(R.id.txt_WareHousName)
    TextView txtWareHousName;
    @ViewInject(R.id.edt_UserName)
    EditText edtUserName;
    @ViewInject(R.id.edt_Password)
    EditText edtPassword;
    int SelectWareHouseID = -1;

    Context context = Login.this;
    List<WareHouseInfo> lstWarehouse;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//      }

    @Override
    protected void initViews() {
        BaseApplication.context = context;
        x.view().inject(this);
        SharePreferUtil.ReadShare(context);
        SharePreferUtil.ReadUserShare(context);
        if (BaseApplication.userInfo != null) {
            edtUserName.setText(BaseApplication.userInfo.getUserNo());
            edtPassword.setText(DESUtil.decode(BaseApplication.userInfo.getPassWord()));
            txtWareHousName.setText(BaseApplication.userInfo.getWarehouseName());
            lstWarehouse = BaseApplication.userInfo.getLstWarehouse();
        }
        txtVersion.setText(getString(R.string.login_Version) + (updateVersionService.getVersionCode(context)));

        super.initViews();
    }

    @Event(value = R.id.edt_UserName, type = View.OnKeyListener.class)
    private boolean edtUserNameOnKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            keyBoardCancle();
            GetWareHouse();
        }
        return false;
    }

    @Event(value = R.id.txt_WareHousName, type = View.OnClickListener.class)
    private void txtWareHousNameOnClick(View v) {
        GetWareHouse();
    }


    @Event(R.id.btn_Login)
    private void btnLoginClick(View view) {
        if (TextUtils.isEmpty(URLModel.PrintIP)) {
            MessageBox.Show(context, getString(R.string.Error_PrintIPNotSet));
            return;
        }
        if (!URLModel.isWMS && TextUtils.isEmpty(URLModel.ElecIP)) {
            MessageBox.Show(context, getString(R.string.Error_PrintIPNotSet));
            return;
        }
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        UserInfo user = new UserInfo();
        user.setUserNo(userName);
        user.setPassWord(password);
        if (BaseApplication.userInfo != null) {
            user.setWarehouseID(BaseApplication.userInfo.getWarehouseID());
            if (user.CheckUserAndPass()) {
                user.setPassWord(DESUtil.encode(user.getPassWord()));
                String userJson = GsonUtil.parseModelToJson(user);
                LogUtil.WriteLog(Login.class, TAG, userJson);
                Map<String, String> params = new HashMap<>();
                params.put("UserJson", userJson);
                RequestHandler.addRequestWithDialog(Request.Method.POST, TAG, getString(R.string.Msg_Login), context, mHandler, RESULT_GET_LOGIN_INFO, null, URLModel.GetURL().UserLoginADF, params, null);
            }
        }
    }

    @Event(R.id.btn_Setting)
    private void btnSetting(View view) {
//        //创建弹出式菜单对象（最低版本11）
//        PopupMenu popup = new PopupMenu(Login.this, view);//第二个参数是绑定的那个view
////        PopupMenu popupMenu = new PopupMenu(context, view, Gravity.END);
////        popupMenu.inflate(R.menu.menu_setting);
//
//        //获取菜单填充器
//        MenuInflater inflater = popup.getMenuInflater();
//        //填充菜单
//        inflater.inflate(R.menu.menu_setting, popup.getMenu());
//        Menu menu = popup.getMenu();
//        String text = getResources().getString(R.string.main_menu_ip_address);
//        String text2 = getResources().getString(R.string.main_menu_zebra_print_connect_address);
//        SpannableStringBuilder builder = new SpannableStringBuilder(text);
//        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.black));
//        builder.setSpan(colorSpan, 0, text.length() , Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        menu.findItem(R.id.menu_ip_address).setTitle(builder);
//        SpannableStringBuilder builder2 = new SpannableStringBuilder(text2);
//        builder2.setSpan(colorSpan, 0, text2.length() , Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        menu.findItem(R.id.menu_print).setTitle(builder2);
//        //绑定菜单项的点击事件
//        popup.setOnMenuItemClickListener(this);
//        popup.show(); //这一行代码不要忘记了

        startActivityLeft(new Intent(context,Setting.class));
    }

    void AnalysisJson(String result) {
        LogUtil.WriteLog(Login.class, TAG, result);
        ReturnMsgModel<UerInfo> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<UerInfo>>() {
        }.getType());
        if (returnMsgModel.getHeaderStatus().equals("S")) {
            BaseApplication.userInfo = returnMsgModel.getModelJson();
            BaseApplication.userInfo.setPDAPrintIP(URLModel.PrintIP);
            if (lstWarehouse != null && lstWarehouse.size() != 0)
                BaseApplication.userInfo.setLstWarehouse(lstWarehouse);
            BaseApplication.userInfo.setWarehouseName(txtWareHousName.getText().toString());
            if (BaseApplication.userInfo.getReceiveAreaID() <= 0) {
                MessageBox.Show(context, getResources().getString(R.string.Message_No_ReceiveAreaID));
            } else if (BaseApplication.userInfo.getPickAreaID() <= 0 && URLModel.isWMS) {
                MessageBox.Show(context, getResources().getString(R.string.Message_No_PickAreaID));
            } else if (BaseApplication.userInfo.getToSampAreaNo() == null || BaseApplication.userInfo.getToSampAreaNo().equals("")) {
                MessageBox.Show(context, getResources().getString(R.string.Message_No_SampAreaNo));
            } else if (BaseApplication.userInfo.getToSampWareHouseNo() == null || BaseApplication.userInfo.getToSampWareHouseNo().equals("")) {
                MessageBox.Show(context, getResources().getString(R.string.Message_No_QuanUserNo));
            } else if (BaseApplication.userInfo.getLstMenu() == null || BaseApplication.userInfo.getLstMenu().size() == 0) {
                MessageBox.Show(context, getResources().getString(R.string.Message_No_MenuList));
            } else {
                SharePreferUtil.SetUserShare(context, BaseApplication.userInfo);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        } else {
            ToastUtil.show(returnMsgModel.getMessage());
        }
    }

    void AnalysisGetWareHouseByUserADFJson(String result) {
        try {
            LogUtil.WriteLog(Login.class, TAG_GetWareHouseByUserADF, result);
            ReturnMsgModel<UerInfo> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<UerInfo>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                BaseApplication.userInfo = returnMsgModel.getModelJson();
                if (BaseApplication.userInfo.getLstWarehouse() == null) {
                    MessageBox.Show(context, getResources().getString(R.string.Message_No_WhareHouse));
                    CommonUtil.setEditFocus(edtUserName);
                } else {
                    SelectWareHouse();
                    CommonUtil.setEditFocus(edtPassword);
                }
            } else {
                MessageBox.Show(context, returnMsgModel.getMessage());
                CommonUtil.setEditFocus(edtUserName);
            }

        } catch (Exception ex) {
            MessageBox.Show(context, ex.getMessage());
            CommonUtil.setEditFocus(edtUserName);
        }
    }

    void SelectWareHouse() {
        if (BaseApplication.userInfo == null || BaseApplication.userInfo.getLstWarehouse() == null)
            return;
        List<String> wareHouses = new ArrayList<String>();
        lstWarehouse = BaseApplication.userInfo.getLstWarehouse();
        if (BaseApplication.userInfo.getLstWarehouse().size() > 1) {
            for (WareHouseInfo warehouse : BaseApplication.userInfo.getLstWarehouse()) {
                if (warehouse.getWareHouseName() != null && !warehouse.getWareHouseName().equals("")) {
                    wareHouses.add(warehouse.getWareHouseName());
                }
            }
            final String[] items = wareHouses.toArray(new String[0]);
            new AlertDialog.Builder(context).setTitle(getResources().getString(R.string.activity_login_WareHousChoice))// 设置对话框标题
                    .setIcon(android.R.drawable.ic_dialog_info)// 设置对话框图
                    .setCancelable(false)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO 自动生成的方法存根
                            String select_item = items[which].toString();
                            SelectWareHouseID = BaseApplication.userInfo.getLstWarehouse().get(which).getID();
                            txtWareHousName.setText(select_item);
                            BaseApplication.userInfo.setWarehouseID(SelectWareHouseID);
                            BaseApplication.userInfo.setWarehouseName(select_item);
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            SelectWareHouseID = BaseApplication.userInfo.getLstWarehouse().get(0).getID();
            txtWareHousName.setText(BaseApplication.userInfo.getLstWarehouse().get(0).getWareHouseName());
            BaseApplication.userInfo.setWarehouseID(SelectWareHouseID);
            BaseApplication.userInfo.setWarehouseName(txtWareHousName.getText().toString());
        }
    }


    void GetWareHouse() {
        String userNo = edtUserName.getText().toString().trim();
        if (!userNo.isEmpty()) {
            keyBoardCancle();
            final Map<String, String> params = new HashMap<String, String>();
            params.put("UserNo", userNo);
            LogUtil.WriteLog(Login.class, TAG_GetWareHouseByUserADF, userNo);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetWareHouseByUserADF, getString(R.string.Msg_GetWareHouse), context, mHandler, RESULT_GetWareHouseByUserADF, null, URLModel.GetURL().GetWareHouseByUserADF, params, null);
        }
    }


//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_ip_address:
//                startActivityLeft(new Intent(context, Setting.class));
//                break;
//
//            case R.id.menu_print:
//                startActivityLeft(new Intent(context, ConnectivityDemo.class));
//                break;
//        }
//        return false;
//    }
}
