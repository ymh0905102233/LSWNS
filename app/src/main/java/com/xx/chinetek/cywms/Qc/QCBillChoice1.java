package com.xx.chinetek.cywms.Qc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.adapter.wms.QC.QCBillChioceItemAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Base_Model;
import com.xx.chinetek.model.QC.QualityInfo_Model;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.ReturnMsgModelList;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.WMS.Stock.StockInfo_Model;
import com.xx.chinetek.util.Network.NetworkError;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@ContentView(R.layout.activity_bill_choice)
public class QCBillChoice1 extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    String TAG_GetT_QualityListADF = "QCBillChoice_GetT_QualityListADF";
    String TAG_PrintQYAndroid = "QCBillChoice_PrintQYAndroid";
    String TAG_GetT_OutBarCodeInfoForQuanADF="QCScan_GetT_OutBarCodeInfoForQuanADF";
    private final int RESULT_GetT_QualityListADF = 101;
    private final int RESULT_PrintQYAndroid = 102;
    private final int RESULT_Msg_GetT_OutBarCodeInfoForQuanADF = 103;

    @Override
    public void onHandleMessage(Message msg) {
        mSwipeLayout.setRefreshing(false);
        switch (msg.what) {
            case RESULT_GetT_QualityListADF:
               AnalysisGetT_QualityListADFJson((String) msg.obj);
                break;
            case RESULT_PrintQYAndroid:
                AnalysisPrintQYAndroidJson((String) msg.obj);
                break;
            case RESULT_Msg_GetT_OutBarCodeInfoForQuanADF:
                AnalysisGetT_OutBarCodeInfoForQuanADFJson((String) msg.obj);
                break;
            case NetworkError.NET_ERROR_CUSTOM:
                ToastUtil.show("获取请求失败_____"+ msg.obj);
                CommonUtil.setEditFocus(edtfilterContent);
                break;
        }
    }

    @ViewInject(R.id.lsvChoice)
    ListView lsvChoice;
    @ViewInject(R.id.mSwipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @ViewInject(R.id.edt_filterContent)
    EditText edtfilterContent;
//    @ViewInject(R.id.btn_PrintQCLabrl)
//    Button btn_PrintQCLabrl;


    Context context = QCBillChoice1.this;
    QCBillChioceItemAdapter qcBillChioceItemAdapter;
    ArrayList<QualityInfo_Model> qualityInfoModels;
    StockInfo_Model stockInfoModel;
    MenuItem  gMenuItem=null;
    Boolean isQcPrint=false;

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.QC_title), true);
        x.view().inject(this);
     //   btn_PrintQCLabrl.setVisibility(View.GONE);
        //edtfilterContent.setVisibility(View.GONE);
        edtfilterContent.addTextChangedListener(TaskNoTextWatcher);
    }

    @Override
    protected void initData() {
        super.initData();
        mSwipeLayout.setOnRefreshListener(this); //下拉刷新
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitListView();
    }

    @Override
    public void onRefresh() {
        qualityInfoModels=new ArrayList<>();
        edtfilterContent.setText("");
        InitListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventorybilldetail, menu);
        gMenuItem=menu.findItem(R.id.action_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==R.id.action_filter){

            Intent intent=new Intent(context,QCInStock.class);
            startActivityLeft(intent);
//            if(qualityInfoModels!=null && qualityInfoModels.size()>0){
//                isQcPrint=!isQcPrint;
//                initFrm();
//            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Listview item点击事件
     */
    @Event(value = R.id.lsvChoice,type =  AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isQcPrint){
            qcBillChioceItemAdapter.modifyStates(position);
            qcBillChioceItemAdapter.notifyDataSetInvalidated();
        }else {
            QualityInfo_Model qualityInfoModel = (QualityInfo_Model) qcBillChioceItemAdapter.getItem(position);
            StartScanIntent(qualityInfoModel);
        }
    }

    @Event(value = R.id.edt_filterContent,type = View.OnKeyListener.class)
    private  boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            if(qualityInfoModels!=null && qualityInfoModels.size()>0) {
                String code = edtfilterContent.getText().toString().trim();
                final Map<String, String> params = new HashMap<String, String>();
                params.put("BarCode", code);
                LogUtil.WriteLog(QCBillChoice1.class, TAG_GetT_OutBarCodeInfoForQuanADF, code);
                RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetT_OutBarCodeInfoForQuanADF, getString(R.string.Msg_GetT_SerialNoByPalletADF), context, mHandler, RESULT_Msg_GetT_OutBarCodeInfoForQuanADF, null, URLModel.GetURL().GetT_OutBarCodeInfoForQuanADF, params, null);



//                //扫描单据号、检查单据列表
//                QualityInfo_Model qualityInfoModel = new QualityInfo_Model(code);
//                int index=qualityInfoModels.indexOf(qualityInfoModel);
//                if (index!=-1) {
//                    StartScanIntent(qualityInfoModels.get(index));
//                    return false;
//                }
            }
            CommonUtil.setEditFocus(edtfilterContent);
        }
        return false;
    }

    /**
     * 文本变化事件
     */
    TextWatcher TaskNoTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!edtfilterContent.getText().toString().equals(""))
                qcBillChioceItemAdapter.getFilter().filter(edtfilterContent.getText().toString());
            else{
                BindListVIew(qualityInfoModels);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

//    @Event(R.id.btn_PrintQCLabrl)
//    private  void btnPrintQCLabrlClick(View view){
//        ArrayList<Barcode_Model> temp=new ArrayList<>();
//        int size=qualityInfoModels.size();
//        for (int i=0;i<size;i++){
//            if(qcBillChioceItemAdapter.getStates(i)){
//                Barcode_Model barcodeModel=new Barcode_Model();
//                barcodeModel.setCreater(BaseApplication.userInfo.getUserName());
//                barcodeModel.setMaterialNo( qualityInfoModels.get(i).getMaterialNo());
//                barcodeModel.setQty( qualityInfoModels.get(i).getSampQty());
//                barcodeModel.setIP(URLModel.PrintIP);
//                temp.add(0,barcodeModel);
//            }
//        }
//        String ModelJson = GsonUtil.parseModelToJson(temp);
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("json",ModelJson );
//        LogUtil.WriteLog(QCBillChoice.class, TAG_PrintQYAndroid, ModelJson);
//        RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_PrintQYAndroid, getString(R.string.Msg_PrintQYAndroid), context, mHandler, RESULT_PrintQYAndroid, null,  URLModel.GetURL().PrintQYAndroid, params, null);
//    }

    /**
     * 初始化加载listview
     */
    private void InitListView() {
        QualityInfo_Model qualityInfoModel=new QualityInfo_Model();
        qualityInfoModel.setStatus(1);
        qualityInfoModel.setERPStatusCode("N");
        qualityInfoModel.setQuanUserNo(BaseApplication.userInfo.getID()+"");
        GetT_QualityList(qualityInfoModel);
    }

    void GetT_QualityList(QualityInfo_Model qualityInfoModel){
        try {
            String ModelJson = GsonUtil.parseModelToJson(qualityInfoModel);
            Map<String, String> params = new HashMap<>();
            params.put("UserJson", GsonUtil.parseModelToJson(BaseApplication.userInfo));
            params.put("ModelJson", ModelJson);
            LogUtil.WriteLog(QCBillChoice1.class, TAG_GetT_QualityListADF, ModelJson);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetT_QualityListADF, getString(R.string.Msg_GetT_QualityListADF), context, mHandler, RESULT_GetT_QualityListADF, null,  URLModel.GetURL().GetT_QualityListADF, params, null);
        } catch (Exception ex) {
            mSwipeLayout.setRefreshing(false);
            MessageBox.Show(context, ex.getMessage());
        }
    }

    void AnalysisGetT_OutBarCodeInfoForQuanADFJson(String result){
        try {
            LogUtil.WriteLog(QCBillChoice1.class, TAG_GetT_OutBarCodeInfoForQuanADF, result);
            ReturnMsgModel<StockInfo_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<StockInfo_Model>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                stockInfoModel = returnMsgModel.getModelJson();
                if(stockInfoModel!=null){
                  edtfilterContent.setText(stockInfoModel.getMaterialNo());
                    CommonUtil.setEditFocus(edtfilterContent);
                }
            } else {
                edtfilterContent.setText("");
                MessageBox.Show(context, returnMsgModel.getMessage());
                CommonUtil.setEditFocus(edtfilterContent);
            }
        }catch (Exception ex){
            MessageBox.Show(context, ex.getMessage());
            CommonUtil.setEditFocus(edtfilterContent);
        }

    }


    void AnalysisGetT_QualityListADFJson(String result){
        try {
            LogUtil.WriteLog(QCBillChoice1.class, TAG_GetT_QualityListADF, result);
            ReturnMsgModelList<QualityInfo_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModelList<QualityInfo_Model>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                qualityInfoModels = returnMsgModel.getModelJson();
//                if (qualityInfoModels != null && qualityInfoModels.size() == 1 && stockInfoModel != null)
//                    StartScanIntent(qualityInfoModels.get(0));
//                else
                   // BindListVIew(qualityInfoModels);
            } else {
               ToastUtil.show(returnMsgModel.getMessage());
            }
        }catch (Exception ex){
            MessageBox.Show(context,ex.getMessage());
        }
        BindListVIew(qualityInfoModels);
    }

    void AnalysisPrintQYAndroidJson(String result){
        try {
            LogUtil.WriteLog(QCBillChoice1.class, TAG_PrintQYAndroid,result);
            ReturnMsgModel<Base_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<Base_Model>>() {}.getType());
            if(returnMsgModel.getHeaderStatus().equals("S")){
                isQcPrint=false;
                initFrm();
            }else{
                MessageBox.Show(context, returnMsgModel.getMessage());
            }
        } catch (Exception ex) {
            MessageBox.Show(context, ex.getMessage());
        }
    }


    void StartScanIntent(QualityInfo_Model qualityInfoModel){
        Intent intent=new Intent(context,QCScan.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("qualityInfoModel",qualityInfoModel);
        intent.putExtras(bundle);
        startActivityLeft(intent);
    }

    void initFrm(){
        gMenuItem.setTitle(getString(isQcPrint?R.string.cancel:R.string.QCprint));
        edtfilterContent.setVisibility(isQcPrint?View.GONE:View.VISIBLE);
     //   btn_PrintQCLabrl.setVisibility(isQcPrint?View.VISIBLE:View.GONE);
        BindListVIew(qualityInfoModels);
    }

    private void BindListVIew(ArrayList<QualityInfo_Model> qualityInfoModels) {
        qcBillChioceItemAdapter=new QCBillChioceItemAdapter(context,qualityInfoModels);
        lsvChoice.setAdapter(qcBillChioceItemAdapter);
    }

}
