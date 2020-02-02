package com.xx.chinetek.Box;

import android.content.Context;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.PrintConnectActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Base_Model;
import com.xx.chinetek.model.CheckNumRefMaterial;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.WMS.Stock.StockInfo_Model;
import com.xx.chinetek.util.Network.NetworkError;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.SharePreferUtil;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.DoubleClickCheck;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_boxingforwuliu)
public class BoxingForWuliu extends PrintConnectActivity {


    String  TAG_SaveT_BarCodeToStockLanyaADF="Boxing_SaveT_BarCodeToStockLanyaADF";
    private  final  int RESULT_SaveT_BarCodeToStockLanyaADF=103;

    @Override
    public void onHandleMessage(Message msg) {

        switch (msg.what) {
            case RESULT_SaveT_BarCodeToStockLanyaADF:
                Analysis_SaveT_BarCodeToStockLanyaADF((String) msg.obj);
                break;
            case NetworkError.NET_ERROR_CUSTOM:
                ToastUtil.show("获取请求失败_____"+ msg.obj);
                break;
        }
    }

    Context context=BoxingForWuliu.this;


    @ViewInject(R.id.edt_BoxCode)
    EditText edtBoxCode;
    @ViewInject(R.id.edt_UnboxCode)
    EditText edtUnboxCode;
    @ViewInject(R.id.btn_BoxConfig)
    TextView btnBoxConfig;

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.Boxing_title), false);
        x.view().inject(this);
        BaseApplication.isCloseActivity=false;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Event(value ={R.id.edt_UnboxCode} ,type = View.OnKeyListener.class)
    private  boolean edtUnboxCodeonKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            if(!edtUnboxCode.getText().toString().trim().contains("P")){
                MessageBox.Show(context,"拆箱码格式错误！");
                return false;
            }

            CommonUtil.setEditFocus(edtUnboxCode);
            return false;
        }
        return false;
    }

    @Event(value ={R.id.edt_BoxCode} ,type = View.OnKeyListener.class)
    private  boolean edtboxCodeonKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            if(!edtBoxCode.getText().toString().trim().contains("P")){
                MessageBox.Show(context,"装箱码格式错误！");
                return false;
            }
            CommonUtil.setEditFocus(edtUnboxCode);
            return false;
        }
        return false;
    }


    @Event(R.id.btn_BoxConfig)
    private void BtnBoxConfigClick(View v){
        if (DoubleClickCheck.isFastDoubleClick(context)) {
            return;
        }
        if(!edtUnboxCode.getText().toString().trim().contains("P")){
            MessageBox.Show(context,"拆箱码格式错误！");
            return;
        }
        if(!edtBoxCode.getText().toString().trim().contains("P")){
            MessageBox.Show(context,"装箱码格式错误！");
            return;
        }

        final Map<String, String> params = new HashMap<String, String>();

        params.put("strOldPalletNo", edtUnboxCode.getText().toString().trim());
        params.put("strNewPalletNo", edtBoxCode.getText().toString().trim());
        SharePreferUtil.ReadSupplierShare(context);
        RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_SaveT_BarCodeToStockLanyaADF, getString(R.string.Msg_SaveT_BarCodeToStockADF), context, mHandler, RESULT_SaveT_BarCodeToStockLanyaADF, null,  URLModel.GetURL().UpdatePalletBoxQty, params, null);

    }



  void  Analysis_SaveT_BarCodeToStockLanyaADF(String result){
      try {
          LogUtil.WriteLog(BoxingForWuliu.class, TAG_SaveT_BarCodeToStockLanyaADF, result);
          ReturnMsgModel<Base_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<Base_Model>>() {
          }.getType());
          if(returnMsgModel.getHeaderStatus().equals("S")){
              MessageBox.Show(context,"操作成功！");
          }
      } catch (Exception ex) {
          MessageBox.Show(context, ex.getMessage());
      }
      CommonUtil.setEditFocus(edtUnboxCode);
  }


}
