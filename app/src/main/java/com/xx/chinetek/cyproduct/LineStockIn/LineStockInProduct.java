package com.xx.chinetek.cyproduct.LineStockIn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.adapter.product.LineStockIn.LineStockInMaterialItemAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.InnerMove.InnerMoveDetail;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.cywms.Receiption.ReceiptionScan;
import com.xx.chinetek.model.Base_Model;
import com.xx.chinetek.model.Material.BarCodeInfo;
import com.xx.chinetek.model.Production.LineStockIn.LineStockInProductModel;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.ReturnMsgModelList;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.User.WareHouseInfo;
import com.xx.chinetek.util.Network.NetworkError;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.ArithUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.DoubleClickCheck;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_product_linestockinproduct_scan)
public class LineStockInProduct extends BaseActivity {

    String TAG_GetPalletDetailByBarCode_Product="LineStockInProduct_GetPalletDetailByBarCode_Product";
    String TAG_SaveModeListForT_StockT="LineStockInProduct_SaveModeListForT_StockT";

    private final int RESULT_Msg_GetPalletDetailByBarCode_Product=102;
    private final int RESULT_SaveModeListForT_StockT=101;


    @Override
    public void onHandleMessage(Message msg) {
        switch (msg.what) {
            case RESULT_SaveModeListForT_StockT:
                AnalysisetSaveModeListForT_StockTJson((String) msg.obj);
                break;
            case RESULT_Msg_GetPalletDetailByBarCode_Product:
                AnalysisetGetPalletDetailByBarCode_ProductJson((String) msg.obj);
                break;
            case NetworkError.NET_ERROR_CUSTOM:
                ToastUtil.show("获取请求失败_____" + msg.obj);
                CommonUtil.setEditFocus(edtLineStockInScanBarcode);
                break;
        }
    }

    Context context = LineStockInProduct.this;
    @ViewInject(R.id.lsv_LineStockInProduct)
    ListView lsvLineStockInProduct;
    @ViewInject(R.id.txt_Company)
    TextView txtCompany;
    @ViewInject(R.id.txt_Batch)
    TextView txtBatch;
    @ViewInject(R.id.txt_Status)
    TextView txtStatus;
    @ViewInject(R.id.txt_EDate)
    TextView txtEDate;
    @ViewInject(R.id.txt_MaterialName)
    TextView txtMaterialName;
    @ViewInject(R.id.txt_WareHousName)
    TextView txtWareHousName;
    @ViewInject(R.id.edt_LineStockInScanBarcode)
    EditText edtLineStockInScanBarcode;

    ArrayList<LineStockInProductModel> lineStockInProductModels;
   // ArrayList<BarCodeInfo> SumbitbarCodeInfos=null;
    LineStockInMaterialItemAdapter lineStockInMaterialItemAdapter;


    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle( getString(R.string.Product_ProductStockin_subtitleYMH), true);
        x.view().inject(this);
        BaseApplication.isCloseActivity=false;
    }

    @Override
    protected void initData() {
        super.initData();
     //   SelectWareHouse();
        lineStockInProductModels=new ArrayList<>();
        txtWareHousName.setText(BaseApplication.userInfo.getWarehouseName());        //SumbitbarCodeInfos=new ArrayList<>();
        CommonUtil.setEditFocus(edtLineStockInScanBarcode);
    }

    @Event(value =R.id.edt_LineStockInScanBarcode,type = View.OnKeyListener.class)
    private  boolean edtLineStockInScanBarcodeClick(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            String code = edtLineStockInScanBarcode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                CommonUtil.setEditFocus(edtLineStockInScanBarcode);
                return true;
            }
            final Map<String, String> params = new HashMap<String, String>();
            params.put("BarCode", code);
            LogUtil.WriteLog(ReceiptionScan.class, TAG_GetPalletDetailByBarCode_Product, code);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetPalletDetailByBarCode_Product, getString(R.string.Msg_GetT_SerialNoByPalletADF), context, mHandler, RESULT_Msg_GetPalletDetailByBarCode_Product, null,  URLModel.GetURL().GetPalletDetailByBarCode_Product, params, null);
        }
        return false;
    }

    @Event(R.id.txt_WareHousName)
    private void txtWareHousNameClick(View view){
        SelectWareHouse();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receiptbilldetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            if (DoubleClickCheck.isFastDoubleClick(context)) {
                return false;
            }
            //提交
            if(lineStockInProductModels!=null && lineStockInProductModels.size()!=0){
                final Map<String, String> params = new HashMap<String, String>();
                ArrayList<BarCodeInfo> SumbitbarCodeInfos=new ArrayList<>();
                for (LineStockInProductModel lineStockInProduct:lineStockInProductModels) {
                    if(lineStockInProduct.getBarCodeInfos()!=null && lineStockInProduct.getBarCodeInfos().size()!=0){
                        SumbitbarCodeInfos.addAll(0,lineStockInProduct.getBarCodeInfos());
                    }
                }
                if(SumbitbarCodeInfos.size()!=0) {
                    String ModelJson = GsonUtil.parseModelToJson(SumbitbarCodeInfos);
//                UerInfo uerInfo=new UerInfo();
//                uerInfo.setWarehouseID(SelectWareHouseID);
//                uerInfo.setWarehouseID(BaseApplication.userInfo.getWarehouseID());
//                uerInfo.setUserNo(BaseApplication.userInfo.getUserNo());
//                uerInfo.setUserName(BaseApplication.userInfo.getUserName());
//                uerInfo.setReceiveAreaNo(BaseApplication.userInfo.getReceiveAreaNo());
//                uerInfo.setReceiveAreaID(BaseApplication.userInfo.getReceiveAreaID());
                    String UserJson = GsonUtil.parseModelToJson(BaseApplication.userInfo);
                    params.put("UserJson", UserJson);
                    params.put("ModelJson", ModelJson);
                    LogUtil.WriteLog(ReceiptionScan.class, TAG_SaveModeListForT_StockT, ModelJson);
                    RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_SaveModeListForT_StockT, getString(R.string.Msg_SaveT_LineInStockProductlADF), context, mHandler, RESULT_SaveModeListForT_StockT, null, URLModel.GetURL().SaveModeListForT_StockT, params, null);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Event(value = R.id.lsv_LineStockInProduct,type =  AdapterView.OnItemClickListener.class)
    private  boolean lsvLineStockInProductClick(AdapterView<?> parent, View view, int position, long id){
        if(id>=0) {
            LineStockInProductModel lineStockInProduct=(LineStockInProductModel)lineStockInMaterialItemAdapter.getItem(position);
            if (lineStockInProduct.getBarCodeInfos().size() != 0) {
                Intent intent = new Intent(context, InnerMoveDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("barCodeInfos", lineStockInProduct.getBarCodeInfos());
                intent.putExtras(bundle);
                startActivityLeft(intent);
            }
        }
        return true;
    }

    /*
   扫描条码
    */
    void AnalysisetGetPalletDetailByBarCode_ProductJson(String result){
        LogUtil.WriteLog(LineStockInProduct.class, TAG_GetPalletDetailByBarCode_Product,result);
        ReturnMsgModelList<BarCodeInfo> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModelList<BarCodeInfo>>() {}.getType());
        try {
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                ArrayList<BarCodeInfo> barCodeInfos = returnMsgModel.getModelJson();
                Bindbarcode(barCodeInfos);
            } else {
                MessageBox.Show(context,returnMsgModel.getMessage());
            }
        }catch (Exception ex){
            MessageBox.Show(context,ex.toString());
        }
        CommonUtil.setEditFocus(edtLineStockInScanBarcode);
    }

    void  AnalysisetSaveModeListForT_StockTJson(String result){
        LogUtil.WriteLog(LineStockInProduct.class, TAG_SaveModeListForT_StockT,result);
        ReturnMsgModel<Base_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<Base_Model>>() {}.getType());
        try {
            MessageBox.Show(context,returnMsgModel.getMessage());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                ClearFrm();
            }
        }catch (Exception ex){
            MessageBox.Show(context,ex.toString());
        }
        CommonUtil.setEditFocus(edtLineStockInScanBarcode);
    }

    void Bindbarcode(final ArrayList<BarCodeInfo> barCodeInfos){
        if (barCodeInfos != null && barCodeInfos.size() != 0) {
            try {
                String MaterialNo=barCodeInfos.get(0).getMaterialNo();
                String BatchNo=barCodeInfos.get(0).getBatchNo();
                String MaterialDesc=barCodeInfos.get(0).getMaterialDesc();
                Float SumQty=0f;
                for (BarCodeInfo barcodinfo:barCodeInfos) {
                    SumQty= ArithUtil.add(SumQty,barcodinfo.getQty());
                }
                final Float sumQty=SumQty;
                LineStockInProductModel templineStockIn=new LineStockInProductModel(MaterialNo,BatchNo);
                final int index=lineStockInProductModels.indexOf(templineStockIn);
                if(index!=-1){
                    if(lineStockInProductModels.get(index).getBarCodeInfos().indexOf(barCodeInfos.get(0))!=-1){
                        //MessageBox.Show(context,getString(R.string.Error_Barcode_hasScan));
                        new AlertDialog.Builder(context) .setCancelable(false).setTitle("提示").setIcon(android.R.drawable.ic_dialog_info).setMessage("是否删除已扫描条码？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO 自动生成的方法
                                        lineStockInProductModels.get(index).getBarCodeInfos().removeAll(barCodeInfos);
                                        lineStockInProductModels.get(index).setQty(ArithUtil.sub(lineStockInProductModels.get(index).getQty(),sumQty));
                                        if( lineStockInProductModels.get(index).getBarCodeInfos().size()==0){
                                            lineStockInProductModels.remove(index);
                                        }
                                        InitFrm(barCodeInfos.get(0));
                                        BindListVIew(lineStockInProductModels);
                                    }
                                }).setNegativeButton("取消", null).show();
                        return;
                    }
                    lineStockInProductModels.get(index).setQty(ArithUtil.add(lineStockInProductModels.get(index).getQty(),SumQty));
                    lineStockInProductModels.get(index).getBarCodeInfos().addAll(0,barCodeInfos);
                }else{
                    templineStockIn.setMaterialDesc(MaterialDesc);
                    templineStockIn.setQty(SumQty);
                    if(templineStockIn.getBarCodeInfos()==null)
                        templineStockIn.setBarCodeInfos(new ArrayList<BarCodeInfo>());
                    templineStockIn.getBarCodeInfos().addAll(0,barCodeInfos);
                    lineStockInProductModels.add(0,templineStockIn);
                }
                InitFrm(barCodeInfos.get(0));
                BindListVIew(lineStockInProductModels);
            }catch (Exception ex){
                MessageBox.Show(context,ex.getMessage());
                CommonUtil.setEditFocus(edtLineStockInScanBarcode);
            }

        }
    }


    /*
   提交入库
    */
//    void AnalysisSaveT_InStockTaskDetailADFJson(String result){
//        try {
//            LogUtil.WriteLog(LineStockInProduct.class, TAG_SaveT_InStockTaskDetailADF,result);
//            ReturnMsgModel<Base_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<Base_Model>>() {
//            }.getType());
//            MessageBox.Show(context, returnMsgModel.getMessage());
//            if(returnMsgModel.getHeaderStatus().equals("S")) {
//                ClearFrm();
//                GetInStockTaskDetail(inStockTaskInfoModel);
//            }
//            CommonUtil.setEditFocus(edtUpShelfScanBarcode);
//        } catch (Exception ex) {
//            MessageBox.Show(context, ex.getMessage());
//        }
//    }

    void InitFrm(BarCodeInfo barCodeInfo){
        try {
            if (barCodeInfo != null) {
                txtCompany.setText(barCodeInfo.getStrongHoldName());
                txtBatch.setText(barCodeInfo.getBatchNo());
                txtStatus.setText("");
                txtMaterialName.setText(barCodeInfo.getMaterialDesc());
                txtEDate.setText(CommonUtil.DateToString(barCodeInfo.getEDate()));
            }
        }catch (Exception ex){
            MessageBox.Show(context,ex.getMessage());
            CommonUtil.setEditFocus(edtLineStockInScanBarcode);
        }
    }

    private void BindListVIew(ArrayList<LineStockInProductModel> lineStockInProductModels) {
        lineStockInMaterialItemAdapter=new LineStockInMaterialItemAdapter(context,lineStockInProductModels);
        lsvLineStockInProduct.setAdapter(lineStockInMaterialItemAdapter);
    }



    void ClearFrm(){
        lineStockInProductModels = new ArrayList<>();
        edtLineStockInScanBarcode.setText("");
        txtCompany.setText("");
        txtBatch.setText("");
        txtEDate.setText("");
        txtStatus.setText("");
        txtMaterialName.setText("");
        BindListVIew(lineStockInProductModels);
    }


    int  SelectWareHouseID=-1;
    void SelectWareHouse(){
        if (BaseApplication.userInfo==null || BaseApplication.userInfo.getLstWarehouse() == null) return;
        List<String> wareHouses = new ArrayList<String>();
        if(BaseApplication.userInfo.getLstWarehouse().size()>1) {
            for (WareHouseInfo warehouse : BaseApplication.userInfo.getLstWarehouse()) {
                if (warehouse.getWareHouseName() != null && !warehouse.getWareHouseName().equals("")) {
                    wareHouses.add(warehouse.getWareHouseName());
                }
            }
            final String[] items = wareHouses.toArray(new String[0]);
            new AlertDialog.Builder(context) .setCancelable(false).setTitle(getResources().getString(R.string.activity_login_WareHousChoice))// 设置对话框标题
                    .setIcon(android.R.drawable.ic_dialog_info)// 设置对话框图
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO 自动生成的方法存根
                            String select_item = items[which].toString();
                            SelectWareHouseID = BaseApplication.userInfo.getLstWarehouse().get(which).getID();
                            txtWareHousName.setText(select_item);
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            SelectWareHouseID = BaseApplication.userInfo.getLstWarehouse().get(0).getID();
            txtWareHousName.setText(BaseApplication.userInfo.getLstWarehouse().get(0).getWareHouseName());
        }
    }




}
