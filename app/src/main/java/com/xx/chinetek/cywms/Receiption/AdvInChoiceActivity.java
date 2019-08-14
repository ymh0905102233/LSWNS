package com.xx.chinetek.cywms.Receiption;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.os.Bundle;
import android.security.keystore.KeyNotYetValidException;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.adapter.wms.Receiption.ReceiptScanDetailAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Base_Model;
import com.xx.chinetek.model.Material.BarCodeInfo;
import com.xx.chinetek.model.Material.MaterialPack_Model;
import com.xx.chinetek.model.Receiption.ReceiptDetail_Model;
import com.xx.chinetek.model.Receiption.Receipt_Model;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.ReturnMsgModelList;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.User.User;
import com.xx.chinetek.model.WMS.AdvInStock.AdvInStockDetail_Model;
import com.xx.chinetek.model.WMS.AdvInStock.AdvInStockInfo_Model;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.dialog.BasisTimesUtils;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.DoubleClickCheck;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.pattern.Converter;

import static com.xx.chinetek.util.function.GsonUtil.parseModelToJson;

@ContentView(R.layout.activity_adv_in_choice)
public class AdvInChoiceActivity extends BaseActivity {

    Context context = AdvInChoiceActivity.this;

    String TAG_GetT_InStockDetailListByHeaderIDADF = "ReceiptionScan_GetT_InStockDetailListByHeaderIDADF";
    String TAG_GetAdvin_GetMaterialPackADF = "GetAdvin_GetMaterialPackADF";
    String TAG_SaveAdvInStock="SaveAdvInStock";

    private final int RESULT_Msg_GetT_InStockDetailListByHeaderIDADF = 101;
    private final int RESULT_Msg_GetAdvin_GetMaterialPackADF = 102;
    private final  int RESULT_Msg_SaveAdvInStock =103;

    @ViewInject(R.id.et_adv_orderInfo)
    EditText txtVoucherNo;

    @ViewInject(R.id.sp_adv_qc_type)
    Spinner spQcType;
    @ViewInject(R.id.et_adv_barcode)
    EditText etBarcode;
    @ViewInject(R.id.btn_adv_date)
    Button btnDate;
    @ViewInject(R.id.tv_adv_order_qty)
    TextView txtOrderQty;
    @ViewInject(R.id.et_adv_qty)
    EditText etScanQty;
    @ViewInject(R.id.lv_adv_order_info)
    ListView lvOrderInfo;

    @ViewInject(R.id.et_adv_Batch)
    EditText etBatch;

    /*include 物料信息*/
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


    /*订单表头*/
    Receipt_Model receiptModel = null;

    ReceiptScanDetailAdapter receiptScanDetailAdapter;

    /*订单明细列表*/
    ArrayList<ReceiptDetail_Model> receiptDetailModels;
    /*当前扫描明细*/
    ReceiptDetail_Model receiptScanDetail;
    /*获取物料档案*/
    ArrayList<MaterialPack_Model> listMaterialPacks;
    /*
    当前选中档案
    */
    MaterialPack_Model materialPack;

    ArrayList<AdvInStockDetail_Model> listAdvInStock = new ArrayList<>();


    /*效期*/
    String eDate = "";

    @Override
    protected void initData() {
        receiptModel = getIntent().getParcelableExtra("receiptModel");
        GetReceiptDetail(receiptModel);
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.advtitle), false);
        x.view().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_adv_in_choice);
    }

    /*载入顶部按钮*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receiptbilldetail, menu);
        return true;
    }

    @Override
    public void onHandleMessage(Message msg) {
        switch (msg.what) {
            case RESULT_Msg_GetT_InStockDetailListByHeaderIDADF:
                AnalysisGetT_InStockDetailListJson((String) msg.obj);
                break;
            case RESULT_Msg_GetAdvin_GetMaterialPackADF:
                AnalysisGetT_MaterialPackListJson((String) msg.obj);
                break;
            case RESULT_Msg_SaveAdvInStock:
                AnalysisSetT_AdvInStock((String) msg.obj);
                break;
        }
        super.onHandleMessage(msg);
    }

    /*
    * 顶部按钮点击*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            if (DoubleClickCheck.isFastDoubleClick(context)) {
                return false;
            }
            AdvInStockInfo_Model advInStockInfo = new AdvInStockInfo_Model();
            advInStockInfo.setErpVoucherNo(receiptModel.getErpVoucherNo());
            advInStockInfo.setSupplierNo(receiptModel.getSupplierNo());
            advInStockInfo.setSupplierName(receiptModel.getSupplierName());
            advInStockInfo.setVoucherType(receiptModel.getVoucherType());
            advInStockInfo.setStatus(1);
            advInStockInfo.setIsDel(1);
            advInStockInfo.setStrongHoldCode(receiptModel.getStrongHoldCode());
            advInStockInfo.setStrongHoldName(receiptModel.getStrongHoldName());
            advInStockInfo.setCompanyCode(receiptModel.getCompanyCode());

            advInStockInfo.setCreater(BaseApplication.userInfo.getUserNo());
            advInStockInfo.setWarehouseID(BaseApplication.userInfo.getWarehouseID());
            advInStockInfo.setLstDetail(listAdvInStock);
            final Map<String, String> params = new HashMap<String, String>();
            String jsonValue =GsonUtil.parseModelToJson(advInStockInfo);
            params.put("advInStock",jsonValue );
            String UserJson = GsonUtil.parseModelToJson(BaseApplication.userInfo);
            params.put("UserJson", UserJson);
            LogUtil.WriteLog(ReceiptionScan.class, TAG_GetAdvin_GetMaterialPackADF, jsonValue);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_SaveAdvInStock, getString(R.string.Msg_SaveT_SubInfoADF), context, mHandler, RESULT_Msg_SaveAdvInStock, null, URLModel.GetURL().Post_SaveAdvInStock, params, null);
        }

        return super.onOptionsItemSelected(item);
    }


    @Event(value = R.id.et_adv_barcode, type = View.OnKeyListener.class)
    private boolean etAdvBarcode(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            keyBoardCancle();
            String code = etBarcode.getText().toString().trim();
            MaterialPack_Model material = new MaterialPack_Model();
            material.setWATERCODE(code);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("UserJson", GsonUtil.parseModelToJson(BaseApplication.userInfo));
            params.put("ModelJson", GsonUtil.parseModelToJson(material));
            LogUtil.WriteLog(ReceiptionScan.class, TAG_GetAdvin_GetMaterialPackADF, code);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetAdvin_GetMaterialPackADF, getString(R.string.Msg_GetT_SerialNoByPalletADF), context, mHandler, RESULT_Msg_GetAdvin_GetMaterialPackADF, null, URLModel.GetURL().GetT_MaterialPackADF, params, null);

        }
        return false;
    }

   // @Event(value ={R.id.tb_UnboxType,R.id.tb_PalletType,R.id.tb_BoxType} ,type = CompoundButton.OnClickListener.class)
   // private void TBonCheckedChanged(View view) {
 //  @Event(value = {R.id.button6},type = View.OnClickListener.class)
 //  private void onClick(View view) {
    @Event(value = R.id.btn_adv_date, type = View.OnClickListener.class)
    private void onClick(View v) {

        if (materialPack == null) {
            MessageBox.Show(context, "请先扫描条码");
            return ;
        }
        /**     * 年月日选择     */ //BasisTimesUtils.THEME_HOLO_DARK
        BasisTimesUtils.showDatePickerDialog(context,0, "请选择年月日", Calendar.YEAR, Calendar.MONTH+1, Calendar.DATE, new BasisTimesUtils.OnDatePickerListener() {
            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                eDate = year + "-" + month + "-" + dayOfMonth;
                btnDate.setText(eDate);
                String bdate = BasisTimesUtils.getDateStr(eDate, materialPack.getQUALITYDAY());
                etBatch.setText(bdate);
            }
            @Override
            public void onCancel() {
                eDate = "";
                btnDate.setText("选   择");
            }
        });
    }

    @Event(value = R.id.et_adv_qty, type = OnKeyListener.class)
    private boolean etAdvQty(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            keyBoardCancle();
            int scanQty = 0;

            try {
                scanQty = Integer.valueOf(etScanQty.getText().toString());
                if (scanQty <= 0) {
                    MessageBox.Show(context, "请输入大于0的数量");
                    CommonUtil.setEditFocus(etScanQty);
                    return false;
                }
            } catch (Exception e) {
                MessageBox.Show(context, "转换异常");
                CommonUtil.setEditFocus(etScanQty);
                return false;
            }
            if (receiptScanDetail == null) {
                MessageBox.Show(context, "请先扫描条码信息");
                CommonUtil.setEditFocus(etBarcode);
                return false;
            }
            if (eDate.equals("")) {
                MessageBox.Show(context, "请选择效期");
                CommonUtil.setEditFocus(etBarcode);
                return false;
            }
            Float value = receiptScanDetail.getInStockQty() - receiptScanDetail.getADVRECEIVEQTY() - receiptScanDetail.getScanQty();
            if (value < scanQty) {
                MessageBox.Show(context, "录入数量不能大于可扫描数量 " + value);
                CommonUtil.setEditFocus(etScanQty);
                return false;
            }
            AdvInStockDetail_Model advScanValue = new AdvInStockDetail_Model();
            advScanValue.setMaterialNo(receiptScanDetail.getMaterialNo());
            advScanValue.setMaterialDesc(receiptScanDetail.getMaterialDesc());
            advScanValue.setAdvQty(scanQty);
            advScanValue.setUnit(receiptScanDetail.getUnit());
            advScanValue.setLineStatus(1);
            advScanValue.setCreater(BaseApplication.userInfo.getQuanUserNo());
            advScanValue.setIsDel(1);
            advScanValue.setEAN(materialPack.getWATERCODE());
            advScanValue.setErpVoucherNo(receiptScanDetail.getErpVoucherNo());
            advScanValue.setQualityType(spQcType.getId());//质检类型
            Date date = new Date(BasisTimesUtils.getLongTimeOfYMD(eDate));
            advScanValue.setEDate(date);
            advScanValue.setSupBatch(etBatch.getText().toString());
            advScanValue.setERPNote(String.valueOf(receiptScanDetail.getID()));//ERP订单行
            listAdvInStock.add(advScanValue);
            receiptScanDetail.setScanQty(receiptScanDetail.getScanQty() + scanQty);
            scanEnd();
        }
        return false;
    }

    /*
    * 扫描完成清理界面
    * */
    void scanEnd() {
        receiptScanDetail = null;
        materialPack = null;
        eDate = "";
        etBatch.setText("");
        etScanQty.setText("");
        etBarcode.setText("");
        txtOrderQty.setText("订单数量");
        btnDate.setText("选   择");
        CommonUtil.setEditFocus(etBarcode);
    }

    /*获取订单明细*/
    void GetReceiptDetail(Receipt_Model receiptModel) {
        if (receiptModel != null) {
            txtVoucherNo.setText(receiptModel.getErpVoucherNo());
            txtVoucherNo.setEnabled(false);
            final ReceiptDetail_Model receiptDetailModel = new ReceiptDetail_Model();
            receiptDetailModel.setHeaderID(receiptModel.getID());
            receiptDetailModel.setErpVoucherNo(receiptModel.getErpVoucherNo());
            receiptDetailModel.setVoucherType(receiptModel.getVoucherType());
            final Map<String, String> params = new HashMap<String, String>();
            params.put("ModelDetailJson", parseModelToJson(receiptDetailModel));
            String para = (new JSONObject(params)).toString();
            LogUtil.WriteLog(ReceiptionScan.class, TAG_GetT_InStockDetailListByHeaderIDADF, para);
            RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetT_InStockDetailListByHeaderIDADF, getString(R.string.Msg_GetT_InStockDetailListByHeaderIDADF), context, mHandler, RESULT_Msg_GetT_InStockDetailListByHeaderIDADF, null, URLModel.GetURL().GetT_InStockDetailListByHeaderIDADF, params, null);
        }
    }

    /*
       订单明细处理
   */
    void AnalysisGetT_InStockDetailListJson(String result) {
        LogUtil.WriteLog(ReceiptionScan.class, TAG_GetT_InStockDetailListByHeaderIDADF, result);
        try {
            ReturnMsgModelList<ReceiptDetail_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModelList<ReceiptDetail_Model>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                receiptDetailModels = returnMsgModel.getModelJson();
                //自动确认扫描箱号
                BindListVIew(receiptDetailModels);
            } else {
                MessageBox.Show(context, returnMsgModel.getMessage());
            }
        } catch (Exception ex) {
            MessageBox.Show(context, ex.getMessage());
        }
        CommonUtil.setEditFocus(etBarcode);
    }

    void AnalysisSetT_AdvInStock(String result){
        LogUtil.WriteLog(ReceiptionScan.class, TAG_SaveAdvInStock, result);
        try{

            final ReturnMsgModel<Base_Model> returnMsgModel =  GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModel<Base_Model>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                MessageBox.Show(context, returnMsgModel.getMessage());
                closeActiviry();
            }else {
                MessageBox.Show(context, returnMsgModel.getMessage());
            }

        }catch (Exception ex) {
            MessageBox.Show(context, ex.getMessage());
        }
    }
    /*
    获取物料信息处理
*/
    void AnalysisGetT_MaterialPackListJson(String result) {
        LogUtil.WriteLog(ReceiptionScan.class, TAG_GetAdvin_GetMaterialPackADF, result);
        try {

            final ReturnMsgModelList<MaterialPack_Model> returnMsgModel = GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModelList<MaterialPack_Model>>() {
            }.getType());
            if (returnMsgModel.getHeaderStatus().equals("S")) {
                listMaterialPacks = returnMsgModel.getModelJson();
                if (listMaterialPacks.size() > 1) {
                    ArrayList<String> listString = new ArrayList<>();

                    for (MaterialPack_Model pack :
                            listMaterialPacks) {
                        listString.add(pack.getMATERIALNO());
                    }
                    String[] items = (String[]) listString.toArray(new String[listString.size()]);
                    AlertDialog alertDialog3 = new AlertDialog.Builder(this)
                            .setTitle("选择物料")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    setMaterailPackInfo(listMaterialPacks.get(i));
                                    return;
                                }
                            }).create();
                    alertDialog3.show();
                } else if (listMaterialPacks.size() == 1) {
                    setMaterailPackInfo(listMaterialPacks.get(0));
                    return;
                } else {
                    MessageBox.Show(context, "未找到该条码档案信息");
                    etBarcode.setText("");
                }
            } else {
                MessageBox.Show(context, returnMsgModel.getMessage());
            }
        } catch (Exception ex) {
            MessageBox.Show(context, ex.getMessage());
        }
        CommonUtil.setEditFocus(etBarcode);
    }

    /*扫描物料条码后处理*/
    void setMaterailPackInfo(MaterialPack_Model materialPackMdl) {
        materialPack = materialPackMdl;

        txtCompany.setText(materialPackMdl.getMATERIALNO());
        txtBatch.setText("");
        txtStatus.setText("");
        txtMaterialName.setText(materialPackMdl.getMATERIALDESC());
        txtEDate.setText(String.valueOf(materialPackMdl.getQUALITYDAY()));

        receiptScanDetail = null;
        for (ReceiptDetail_Model detail :
                receiptDetailModels) {
            if (detail.getMaterialNo().equals(materialPackMdl.getMATERIALNO()) &&(detail.getInStockQty()- detail.getADVRECEIVEQTY())  > detail.getScanQty()) {
                receiptScanDetail = detail;
                break;
            }
        }
        if (receiptScanDetail == null) {
            txtOrderQty.setText("订单数量");
            MessageBox.Show(context, "未找到符合该物料的明细信息");
            etBarcode.setText("");
            CommonUtil.setEditFocus(etBarcode);
        } else {
            txtOrderQty.setText("订单数 " + receiptScanDetail.getInStockQty() + "可入数 " + (receiptScanDetail.getInStockQty() - receiptScanDetail.getADVRECEIVEQTY() - receiptScanDetail.getScanQty()) + "已扫" + receiptScanDetail.getScanQty());
            etScanQty.setText("");
            CommonUtil.setEditFocus(etScanQty);
        }
    }

    void InitFrm(BarCodeInfo barCodeInfo) {
        if (barCodeInfo != null) {
            txtCompany.setText(barCodeInfo.getStrongHoldName());
            txtBatch.setText(barCodeInfo.getBatchNo());
            txtStatus.setText(barCodeInfo.getStrStatus());
            txtMaterialName.setText(barCodeInfo.getMaterialDesc());
            txtEDate.setText(CommonUtil.DateToString(barCodeInfo.getEDate()));
        }
    }

    private void BindListVIew(ArrayList<ReceiptDetail_Model> receiptDetailModels) {
        receiptScanDetailAdapter = new ReceiptScanDetailAdapter(context, receiptDetailModels);
        lvOrderInfo.setAdapter(receiptScanDetailAdapter);
    }


}
