package com.xx.chinetek.model;

/**
 * Created by GHOST on 2017/6/9.
 */

public class URLModel {

    private static URLModel instance;

    public static URLModel  GetURL() {
        return  new URLModel();
    }

    public static String IPAdress="wmstest.beukay.com";//"wmstest.beukay.com";
    public static int Port=9010;//9000;
    public static String  LastContent="AndroidService.svc/";
    public static String PrintIP="10.2.32.192";
    public static String ElecIP="10.2.32.244";
    public static Boolean isWMS=true;
    public static boolean isSupplier=false;
    String  GetWCFAdress(){
        return  "http://"+IPAdress+":"+Port+"/"+LastContent;
    }

    public String UserLoginADF = GetWCFAdress()+"UserLoginADF"; //用户登录
    public String GetWareHouseByUserADF = GetWCFAdress()+"GetWareHouseByUserADF"; //获取仓库列表
    public String GetT_InStockListADF = GetWCFAdress()+"GetT_InStockListADF"; //收货表头
    public String GetT_InTaskListADF = GetWCFAdress()+"GetT_InTaskListADF"; //上架表头
    public String GetCheckADF = GetWCFAdress()+"GetCheck"; //盘点表头
    public String GetCheckMing = GetWCFAdress()+"GetCheckMing"; //盘点表头
    public String GetPDNoAndroid = GetWCFAdress()+"GetPDNoAndroid"; //获取盘点单号
    public String GetAreanoID = GetWCFAdress()+"TakeAID"; //获取盘点库位
    public String GetTransportSupplierListADF = GetWCFAdress()+"GetTransportSupplierListADF"; //获取盘点库位
    public String GetDeliveryInfoAndroid = GetWCFAdress()+"GetDeliveryInfoAndroid"; //获取目的地
    public String GetWareHouse = GetWCFAdress()+"GetWareHouse"; //获取盘点仓库
    public String SaveCheckAndroid = GetWCFAdress()+"SaveCheckAndroid"; //提交盘点库位
    public String GetAreanobyCheckno = GetWCFAdress()+"GetAreanobyCheckno"; //获取可盘点库位
    public String GetMinDetail = GetWCFAdress()+"GetMinDetail"; //获取明盘明细
    public String GetScanInfo = GetWCFAdress()+"GetScanInfo"; //获取盘点条码信息
    public String GetMinBarocde = GetWCFAdress()+"GetMinBarocde"; //获取盘点条码信息
    public String GetInfoBySerial = GetWCFAdress()+"GetInfoBySerial"; //获取盘点条码信息
    public String SaveInfo = GetWCFAdress()+"SaveInfo"; //提交库存调整
    public String GetAreanobyCheckno2 = GetWCFAdress()+"GetAreanobyCheckno2"; //检查盘点库位
    public String SummitMin = GetWCFAdress()+"SummitMin"; //检查盘点库位
    public String InsertCheckDetail = GetWCFAdress()+"InsertCheckDetail"; //提交盘点条码信息
    public String GetCheckDetail = GetWCFAdress()+"GetCheckDetail"; //获取已盘点条码信息
    public String GetMinSerialno = GetWCFAdress()+"GetMinSerialno"; //获取已盘点条码信息
    public String DeleteCheckDetail = GetWCFAdress()+"DeleteCheckDetail"; //删除盘点条码信息
    public String GetT_OutStockReviewListADF = GetWCFAdress()+"GetT_OutStockReviewListADF"; //下架复核表头
    public String GetT_InStockDetailListByHeaderIDADF = GetWCFAdress()+"GetT_InStockDetailListByHeaderIDADF"; //收货表体
    public String GetT_InTaskDetailListByHeaderIDADF = GetWCFAdress()+"GetT_InTaskDetailListByHeaderIDADF"; //上架表体
    public String GetT_OutStockReviewDetailListByHeaderIDADF = GetWCFAdress()+"GetT_OutStockReviewDetailListByHeaderIDADF"; //下架复核表体
    public String DeletePalletByErpVoucherNo = GetWCFAdress()+"DeletePalletByErpVoucherNo"; //删除复核托盘
    public String SaveT_InStockDetailADF = GetWCFAdress()+"SaveT_InStockDetailADF"; //提交收货
    public String SaveT_StockADF = GetWCFAdress()+"SaveT_StockADF"; //提交移库
    public String SaveMoveStockToOutADF = GetWCFAdress()+"SaveMoveStockToOutADF"; //提交移库
    public String UpadteT_QualityUserADF = GetWCFAdress()+"UpadteT_QualityUserADF"; //更新取样人
    public String PrintQYAndroid = GetWCFAdress()+"PrintQYAndroid"; //打印取样标签
    public String SaveT_InStockTaskDetailADF = GetWCFAdress()+"SaveT_InStockTaskDetailADF"; //提交上架
    //public static String GetT_SerialNoADF=GetWCFAdress()+"GetT_SerialNoADF"; //获取条码信息
    public String GetT_SerialNoByPalletADF=GetWCFAdress()+"GetT_SerialNoByPalletADF";//获取条码信息
    public String GetT_GetT_OutBarCodeInfoByBoxADF=GetWCFAdress()+"GetT_OutBarCodeInfoByBoxADF";//获取拆托条码信息
    public String GetT_PalletDetailByNoADF=GetWCFAdress()+"GetT_PalletDetailByNoADF";//获取托盘信息
    public String GetT_PalletDetailByBarCodeADF=GetWCFAdress()+"GetT_PalletDetailByBarCodeADF";//库存获取托盘信息
    public String ScanOutStockReviewByBarCodeADF=GetWCFAdress()+"ScanOutStockReviewByBarCodeADF";//复核条码扫描
    public String GetT_ScanInStockModelADF=GetWCFAdress()+"GetT_ScanInStockModelADF";//上架扫描条码或者托盘条码
    public String GetAreaModelADF=GetWCFAdress()+"GetAreaModelADF";//上架扫描库位
    public String GetAreaModelByMoveStockADF=GetWCFAdress()+"GetAreaModelByMoveStockADF";//移库扫描库位
    public String SaveT_OutStockReviewPalletDetailADF=GetWCFAdress()+"SaveT_OutStockReviewPalletDetailADF";//保存组托信息
    public String SaveT_OutStockReviewPalletDetailForLanyaADF=GetWCFAdress()+"SaveT_OutStockReviewPalletDetailForLanyaADF";//保存组托信息给富士通蓝牙打印

    public String SaveT_PalletDetailADF=GetWCFAdress()+"SaveT_PalletDetailADF";//保存组托信息
    public String SaveT_CPPalletDetailADF=GetWCFAdress()+"PrintForProductAndroid";//保存成品组托信息
    public String SaveT_CPPalletDetailADFSup=GetWCFAdress()+"PrintForProductAndroidSup";//保存成品组托信息

    public String PrintLpkPalletAndroid=GetWCFAdress()+"PrintLpkPalletAndroid";//打印托盘标签
    public String PrintLpkApartAndroid=GetWCFAdress()+"PrintLpkApartAndroid";//打印拆零标签
    public String PrintLpkApartAndroidForsup=GetWCFAdress()+"PrintLpkApartAndroidForsup";//打印拆零标签供应商用标签打印（便携式，斑马打印机）
    public String QYReprintAndroid=GetWCFAdress()+"QYReprintAndroid";//打印取样标签
    public String SaveT_OutStockReviewDetailADF=GetWCFAdress()+"SaveT_OutStockReviewDetailADF";//提交复核明细
    public String GetStockByOutStockReviewByID=GetWCFAdress()+"GetStockByOutStockReviewByID";//点击获取复核明细
    public String PrintAndroid=GetWCFAdress()+"PrintAndroid";//打印期初标签
    public String Delete_PalletORBarCodeADF=GetWCFAdress()+"Delete_PalletORBarCodeADF";//删除组托信息
    public String SaveT_BarCodeToStockADF=GetWCFAdress()+"SaveT_BarCodeToStockADF";//装箱拆箱提交
    public String SaveT_BarCodeToStockLanyaADF=GetWCFAdress()+"SaveT_BarCodeToStockLanyaADF";//装箱拆箱提交  蓝牙的方法

    public String Get_PalletDetailByVoucherNo=GetWCFAdress()+"Get_PalletDetailByVoucherNo";//复核获取托盘信息
    public String GetT_QualityListADF=GetWCFAdress()+"GetT_QualityListADF";//获取质检表头信息
    public String ScanQualityStockADF=GetWCFAdress()+"ScanQualityStockADF";//获取质检扫描条码
    public String SaveTransportSupplierListADF=GetWCFAdress()+"SaveTransportSupplierListADF";//保存承运商
    public String GetT_QualityDetailListByHeaderIDADF=GetWCFAdress()+"GetT_QualityDetailListByHeaderIDADF";//获取质检表体信息
    public String GetT_OutBarCodeInfoForQuanADF=GetWCFAdress()+"GetT_OutBarCodeInfoForQuanADF";//获取质检扫描条码信息
    public String GetStockModelADF=GetWCFAdress()+"GetStockModelADF";//获取下架和仓库内移库条码信息
    public String SaveT_QuanlitySampADF=GetWCFAdress()+"SaveT_QuanlitySampADF";//提交质检明细
    public String CreateQualityForStock=GetWCFAdress()+"CreateQualityForStock";//提交在库检明细
    public String SaveT_OutStockTaskDetailADF=GetWCFAdress()+"SaveT_OutStockTaskDetailADF";//提交下架明细
    public String SaveT_ChangeMaterialADF=GetWCFAdress()+"SaveT_ChangeMaterialADF";//提交转料明细
    public String GetT_OutTaskDetailListByHeaderIDADF=GetWCFAdress()+"GetT_OutTaskDetailListByHeaderIDADF";//获取下架表体信息
    public String GetT_OutTaskListADF=GetWCFAdress()+"GetT_OutTaskListADF";//获取下架表头信息
    public String GetPickUserListByUserADF=GetWCFAdress()+"GetPickUserListByUserADF";//获取拣货人员信息
    public String SavePickUserListADF=GetWCFAdress()+"SavePickUserListADF";//提交拣货分配人员信息
    public String GetStockByMaterialNoADF=GetWCFAdress()+"GetStockByMaterialNoADF";//获取查询信息

    //生产
    public String GetT_LineManageInfoModel=GetWCFAdress()+"GetT_LineManageInfoModel";//获取作业记录
    public String GetT_WoinfoModel=GetWCFAdress()+"GetWOInfo";//获取工单任务信息
    public String GetT_OutWoinfoModel=GetWCFAdress()+"GetOutWOInfo";//获取外销工单任务信息
    public String GetT_UserInfoModel=GetWCFAdress()+"GetT_UserInfoModel";//获取员工信息
    public String GetWoDetailModelByWoNo=GetWCFAdress()+"GetWODetailInfo";//获取工单任务明细信息
    public String GetMaterialByBarcode=GetWCFAdress()+"GetMaterialByBarcode";//工单获取条码信息
    public String GetPalletDetailByBarCode_Product=GetWCFAdress()+"GetPalletDetailByBarCode_Product";//生产入库扫描条码

    public String SaveModeListForT_StockT=GetWCFAdress()+"SaveModeListForT_StockT";//生产入库提交

    //生产打印
    public String PrintLabel=GetWCFAdress()+"PrintForProductAndroid";//生产打印条码
    public String GetBaoGongByListWoinfo=GetWCFAdress()+"PostBaoGongByListWoinfo";//生成报工单 ERP接口
    public String GetFinishInStockByListWoinfo=GetWCFAdress()+"PostFinishInStockByListWoinfo";//生成完工入库单 ERP接口
    public String GetBaoGongSumQtyLastQty=GetWCFAdress()+"GetBaoGongSumQtyLastQty";//根据工单号获取总报工数量和上一次报工数量 ERP接口
    public String Insert_LineManageInfoModel=GetWCFAdress()+"Insert_LineManageInfoModel";//插入工单人员
    public String Sync_WoinfoModel=GetWCFAdress()+"UpdateWoInfoByNo";//同步单据UpdateWoInfoByNo
    public String GetT_WoinfoModelBack=GetWCFAdress()+"GetWoInfoByVoucherNo";//获取最新的工单


    public String Save_StockOutADF=GetWCFAdress()+"SaveBarcodeListForStockTaskTrans";//成品出库
    public String PrintForChaiTuoProductAndroid=GetWCFAdress()+"PrintForChaiTuoProductAndroid";//拆托标签打印

    public String SaveT_YMHCPPalletDetailADF=GetWCFAdress()+"SavePalletForProductAndroid";//完工入库成品保存组托信息
    public String SaveT_YMHCPPrintADF=GetWCFAdress()+"PrintForChaiTuoProductAndroid";//完工入库成品打印托盘
    public String GetPalletNoBySerialno=GetWCFAdress()+"GetPalletNoBySerialno";//获取成品托盘号
    public String GetPalletDetailByBarCodeForInStock=GetWCFAdress()+"GetPalletDetailByBarCodeForInStock";//领料入库扫描
    public String SaveModeListForLingLiao_Stock=GetWCFAdress()+"SaveModeListForLingLiao_Stock";//根据扫描的条码集合入线边库
    public String GetWODetailInfo=GetWCFAdress()+"GetWODetailInfo";// 根据工单ID获取工单明细数据
    public String SaveBarcodeListInStockForTuiLiao=GetWCFAdress()+"SaveBarcodeListInStockForTuiLiao";// 根据扫描的条码集合退料入库

    public String GetSystemDate=GetWCFAdress()+"GetSystemDate";//根据工单类型获取有效期yyyy-MM-dd
    public String GetPalletDetailByBarCodeForStockOut=GetWCFAdress()+"GetPalletDetailByBarCodeForStockOut";//库存获取托盘信息
    public String PostBaoJianByListWoinfo=GetWCFAdress()+"PostBaoJianByListWoinfo";//报检
    public String GetBatchNoBySerialnoForOnly=GetWCFAdress()+"GetBatchNoBySerialnoForOnly";//根据条码获取批次(不从库存表查询)


    //生产计划
    public String StartWork=GetWCFAdress()+"StartWork";//开工string UserJson, string modelid
    public String OverWork=GetWCFAdress()+"OverWork";//结束工单string UserJson, string modelid
    public String UpdateLineManageUser=GetWCFAdress()+"UpdateLineManageUser";//添加删除用户string model, string userno, string flag
    public String SuspendOrReWork=GetWCFAdress()+"SuspendOrReWork";//暂停或者开始工单string UserJson, string modelid, string Flag
    public String GetLinemanagestate = GetWCFAdress()+"GetLinemanagestate"; //判断计划状态
    public String SaveBarcodeListForQiTao = GetWCFAdress()+"SaveBarcodeListForQiTao"; //记录齐套数据并更新工单明细数据（齐套）
    public String IsLINEMANAGEID = GetWCFAdress()+"IsLINEMANAGEID"; //判断人员是否存在计划

    //生成调拨单
    public String GetStockInfoByBarcodeForDiaoBo=GetWCFAdress()+"GetStockInfoByBarcodeForDiaoBo";//调拨扫描条码
    public String Post_DBOutStockERPADF=GetWCFAdress()+"Post_DBOutStockERPADF";//生成调拨单

    public String GetChengDataByErpVoucherNoBatchno=GetWCFAdress()+"GetChengDataByErpVoucherNoBatchno";//生产扣料倒扣（散装品倒扣原料---特勒多秤读取）
    public String PostDaoKouForChengPinOrSemi=GetWCFAdress()+"PostDaoKouForChengPinOrSemi";//生产扣料倒扣（成品和半制品倒扣过账---领料和退料）
    public String SaveNewBarcodeToStockForChaiXiang=GetWCFAdress()+"SaveNewBarcodeToStockForChaiXiang";//拆零并打印条码
    public String SaveBarcodeListOutStockForLingLiao=GetWCFAdress()+"SaveBarcodeListOutStockForLingLiao";//提交
    public String GetWoBanGongListByErpVoucherNoForBaoJian=GetWCFAdress()+"GetWoBanGongListByErpVoucherNoForBaoJian";//获取报检列表
    public String SaveT_StockADF_Product = GetWCFAdress()+"SaveT_StockADF_Product"; //ymh提交移库
    public String GetT_SerialNoADF = GetWCFAdress()+"GetT_SerialNoADF"; //收货获取条码信息(不支持托盘，只是单个条码信息)
    public String GetKouLiaoRecord = GetWCFAdress()+"GetKouLiaoRecord"; //判断工单号是否已经扣料过
    public String GetStockModelADF_Product = GetWCFAdress()+"GetStockModelADF_Product"; //领料出库扫描


    //制成检
    public String SaveNewBarcodeToStockForZhiChengJian = GetWCFAdress()+"SaveNewBarcodeToStockForZhiChengJian"; //制成检拆零并打印条码
    public String Post_DBZaRuInStockERPADF = GetWCFAdress()+"Post_DBZaRuInStockERPADF"; //生成杂入单  UserJson  StockInfoJson

    public String GetT_MaterialPackADF=GetWCFAdress()+"GetT_MaterialPackADF";//获取物料信息
    public String Post_SaveAdvInStock =GetWCFAdress()+"Post_SaveAdvInStock";//保存预到货信息
    public String Get_AdvInParameter =GetWCFAdress()+"Get_AdvInParameter";//获得预收货检验类型


    //ymh盘点
    public String CheckGetBatchnoAndMaterialno =GetWCFAdress()+"CheckGetBatchnoAndMaterialno";//根据EAN，areaid获取batch
    public String CheckSerialno =GetWCFAdress()+"CheckSerialno";//根据EAN 获取serialno

    //ymh下架
    public String GetAreano =GetWCFAdress()+"GetAreaModelADF";//获取库位信息
    public String OffSerialno =GetWCFAdress()+"OffSerialno";


}
