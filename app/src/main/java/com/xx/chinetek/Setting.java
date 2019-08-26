package com.xx.chinetek;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mylibrary.LPK130;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.WMS.Stock.StockInfo_Model;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.SharePreferUtil;
import com.xx.chinetek.util.dialog.LoadingDialog;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.zebra.android.devdemo.connectivity.ConnectivityDemo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@ContentView(R.layout.activity_setting)
public class Setting extends BaseActivity {

    Context context=Setting.this;

    @ViewInject(R.id.edt_IPAdress)
    EditText    edtIPAdress;
    @ViewInject(R.id.edt_Port)
    EditText    edtPort;
    @ViewInject(R.id.edt_TimeOut)
    EditText    edtTimeOut;

//    @ViewInject(R.id.rb_WMS)
//    RadioButton rbWMS;
//    @ViewInject(R.id.rb_Product)
//    RadioButton rbProduct;
//    @ViewInject(R.id.supplier_checkBox)
//    CheckBox    mCheckBox;
//    @ViewInject(R.id.testBluetooth)
//    Button      mTestBluetoothButton;
    final  int LogUploadIndex=1;

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.login_setting),true);
        x.view().inject(this);
        SharePreferUtil.ReadSupplierShare(context);
//        if (URLModel.isSupplier){
//            mCheckBox.setChecked(true);
//            mTestBluetoothButton.setVisibility(View.VISIBLE);
//        }else {
//            mCheckBox.setChecked(false);
//            mTestBluetoothButton.setVisibility(View.INVISIBLE);
//        }


    }

    @Override
    protected void initData() {
        super.initData();
        BaseApplication.DialogShowText = getString(R.string.Msg_UploadLogFile);
        SharePreferUtil.ReadShare(context);
        edtIPAdress.setText(URLModel.IPAdress);
        edtPort.setText(URLModel.Port+"");
//        edtIPAdress.setEnabled(false);
//        edtPort.setEnabled(false);
//        if(URLModel.isWMS) rbWMS.setChecked(true); else rbProduct.setChecked(true);
        edtTimeOut.setText(RequestHandler.SOCKET_TIMEOUT/1000+"");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusetting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            final LoadingDialog  dialog = new LoadingDialog(context);
            dialog.show();
            String url="http://"+ URLModel.IPAdress+":"+URLModel.Port+"/UpLoad.ashx";
            File[] files = new File(Environment.getExternalStorageDirectory()+"/wmshht/").listFiles();
            final List<File> list= Arrays.asList(files);
            Collections.sort(list, new FileComparator());

            for(int i=0;i<LogUploadIndex;i++) {
                final  int index=i;
                RequestParams params = new RequestParams(url);
                params.setMultipart(true);
                params.addBodyParameter("file", new File(list.get(list.size()-i-1).getAbsolutePath()));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //加载成功回调，返回获取到的数据
                        if(index==LogUploadIndex-1) {
                            ToastUtil.show(result);
                        }
                    }

                    @Override
                    public void onFinished() {
                        if(index==LogUploadIndex-1) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtil.show(ex.toString());
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public class FileComparator implements Comparator<File> {
        public int compare(File file1, File file2) {
            if(file1.getName().compareTo(file2.getName())<1)
            {
                return -1;
            }else
            {
                return 1;
            }
        }
    }

    @Event(R.id.btn_SaveSetting)
    private void btnSetting(View view){
        String IPAdress=edtIPAdress.getText().toString().trim();

        Integer Port=Integer.parseInt(edtPort.getText().toString().trim());
        Integer TimeOut=Integer.parseInt(edtTimeOut.getText().toString().trim())*1000;
            SharePreferUtil.SetShare(context,IPAdress,"","",Port,TimeOut,true);
            new AlertDialog.Builder(context).setTitle("提示").setCancelable(false).setMessage(getResources().getString(R.string.SaveSuccess)).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   closeActiviry();
                }
            }).show();

    }

    @Event(R.id.btn_SaveMac)
    private void btnTestBluetooth(View view){
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, 1);
    }

    @Event(R.id.btn_Test)
    private void btnTest(View view){
        try{
            StockInfo_Model model = new StockInfo_Model();
            LPK130DEMO(model,"");
        }catch(Exception ex){
            MessageBox.Show(context, ex.toString());
        }

    }



}
