package com.xx.chinetek.model.Material;

import android.os.Parcel;
import android.os.Parcelable;

import com.xx.chinetek.model.Base_Model;

import java.util.Date;

/**
 * Created by GHOST on 2017/6/13.
 */

public class BarCodeInfo extends Base_Model implements Parcelable{

    private String VoucherNo;
    private String RowNo;
    private String RowNoDel;
    private String MaterialNo;
    private String MaterialDesc;
    private String CusCode;
    private String CusName;
    private String SupCode;
    private String SupName;
    private Float OutPackQty;
    private Float InnerPackQty;
    private Float Qty;
    private int NoPack;
    private Float PrintQty;

    public String getLabelMark() {
        return LabelMark;
    }

    public void setLabelMark(String labelMark) {
        LabelMark = labelMark;
    }

    private String BarCode;
    private int BarcodeType;
    private String SerialNo;
    private int BarcodeNo;
    private int OutCount;
    private int InnerCount;
    private Float MantissaQty;
    private int IsRohs;
    private int OutBox_ID;
    private int Inner_ID;
    private String BatchNo;
    private Float ABatchQty;
    private int IsDel;
    private Float VoucherQty ;
    private Date SupPrdDate ;
    private String SupPrdBatch ;
    private Date ProductDate ;
    private String ProductBatch ;
    private String StoreconDition;
    private String SpecialRequire;
    private String BarcodeMType;
    private int WareHouseID;
    private int HouseID;
    private int AreaID;
    private String Palletno;
    private Float PalletQty;
    private int PalletType;

    private String Unit;
    private String ProductClass  ; //ymh生产班组
    private String RelaWeight; //相对比重
    private String LabelMark; //标记

    private  String EAN;//ENA码

    public String getRelaWeight() {
        return RelaWeight;
    }

    public void setRelaWeight(String relaWeight) {
        RelaWeight = relaWeight;
    }

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getRowNoDel() {
        return RowNoDel;
    }

    public void setRowNoDel(String rowNoDel) {
        RowNoDel = rowNoDel;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public int getPalletType() {
        return PalletType;
    }

    public void setPalletType(int palletType) {
        PalletType = palletType;
    }

    public String getStoreconDition() {
        return StoreconDition;
    }

    public void setStoreconDition(String storeconDition) {
        StoreconDition = storeconDition;
    }

    public String getSpecialRequire() {
        return SpecialRequire;
    }

    public void setSpecialRequire(String specialRequire) {
        SpecialRequire = specialRequire;
    }

    public String getBarcodeMType() {
        return BarcodeMType;
    }

    public void setBarcodeMType(String barcodeMType) {
        BarcodeMType = barcodeMType;
    }

    public int getWareHouseID() {
        return WareHouseID;
    }

    public void setWareHouseID(int wareHouseID) {
        WareHouseID = wareHouseID;
    }

    public int getHouseID() {
        return HouseID;
    }

    public void setHouseID(int houseID) {
        HouseID = houseID;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public String getPalletno() {
        return Palletno;
    }

    public void setPalletno(String palletno) {
        Palletno = palletno;
    }

    public Float getPalletQty() {
        return PalletQty;
    }

    public void setPalletQty(Float palletQty) {
        PalletQty = palletQty;
    }

    public static Creator<BarCodeInfo> getCREATOR() {
        return CREATOR;
    }

    public String getVoucherNo() {
        return VoucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        VoucherNo = voucherNo;
    }

    public String getRowNo() {
        return RowNo;
    }

    public void setRowNo(String rowNo) {
        RowNo = rowNo;
    }

    public String getMaterialNo() {
        return MaterialNo;
    }

    public void setMaterialNo(String materialNo) {
        MaterialNo = materialNo;
    }

    public String getMaterialDesc() {
        return MaterialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        MaterialDesc = materialDesc;
    }

    public String getCusCode() {
        return CusCode;
    }

    public void setCusCode(String cusCode) {
        CusCode = cusCode;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getSupCode() {
        return SupCode;
    }

    public void setSupCode(String supCode) {
        SupCode = supCode;
    }

    public String getSupName() {
        return SupName;
    }

    public void setSupName(String supName) {
        SupName = supName;
    }

    public Float getOutPackQty() {
        return OutPackQty;
    }

    public void setOutPackQty(Float outPackQty) {
        OutPackQty = outPackQty;
    }

    public Float getInnerPackQty() {
        return InnerPackQty;
    }

    public void setInnerPackQty(Float innerPackQty) {
        InnerPackQty = innerPackQty;
    }

    public Float getQty() {
        return Qty;
    }

    public void setQty(Float qty) {
        Qty = qty;
    }

    public int getNoPack() {
        return NoPack;
    }

    public void setNoPack(int noPack) {
        NoPack = noPack;
    }

    public Float getPrintQty() {
        return PrintQty;
    }

    public void setPrintQty(Float printQty) {
        PrintQty = printQty;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public int getBarcodeType() {
        return BarcodeType;
    }

    public void setBarcodeType(int barcodeType) {
        BarcodeType = barcodeType;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public int getBarcodeNo() {
        return BarcodeNo;
    }

    public void setBarcodeNo(int barcodeNo) {
        BarcodeNo = barcodeNo;
    }

    public int getOutCount() {
        return OutCount;
    }

    public void setOutCount(int outCount) {
        OutCount = outCount;
    }

    public int getInnerCount() {
        return InnerCount;
    }

    public void setInnerCount(int innerCount) {
        InnerCount = innerCount;
    }

    public Float getMantissaQty() {
        return MantissaQty;
    }

    public void setMantissaQty(Float mantissaQty) {
        MantissaQty = mantissaQty;
    }

    public int getIsRohs() {
        return IsRohs;
    }

    public void setIsRohs(int isRohs) {
        IsRohs = isRohs;
    }

    public int getOutBox_ID() {
        return OutBox_ID;
    }

    public void setOutBox_ID(int outBox_ID) {
        OutBox_ID = outBox_ID;
    }

    public int getInner_ID() {
        return Inner_ID;
    }

    public void setInner_ID(int inner_ID) {
        Inner_ID = inner_ID;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setABatchNo(String ABatchNo) {
        this.BatchNo = BatchNo;
    }

    public Float getABatchQty() {
        return ABatchQty;
    }

    public void setABatchQty(Float ABatchQty) {
        this.ABatchQty = ABatchQty;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    public Float getVoucherQty() {
        return VoucherQty;
    }

    public void setVoucherQty(Float voucherQty) {
        VoucherQty = voucherQty;
    }

    public Date getSupPrdDate() {
        return SupPrdDate;
    }

    public void setSupPrdDate(Date supPrdDate) {
        SupPrdDate = supPrdDate;
    }

    public String getSupPrdBatch() {
        return SupPrdBatch;
    }

    public void setSupPrdBatch(String supPrdBatch) {
        SupPrdBatch = supPrdBatch;
    }

    public Date getProductDate() {
        return ProductDate;
    }

    public void setProductDate(Date productDate) {
        ProductDate = productDate;
    }

    public String getProductBatch() {
        return ProductBatch;
    }

    public void setProductBatch(String productBatch) {
        ProductBatch = productBatch;
    }

    public BarCodeInfo() {
    }

    public BarCodeInfo(String barcode) {
        this.BarCode=barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BarCodeInfo that = (BarCodeInfo) o;

        return BarCode.equals(that.BarCode);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.VoucherNo);
        dest.writeString(this.RowNo);
        dest.writeString(this.RowNoDel);
        dest.writeString(this.MaterialNo);
        dest.writeString(this.MaterialDesc);
        dest.writeString(this.CusCode);
        dest.writeString(this.CusName);
        dest.writeString(this.SupCode);
        dest.writeString(this.SupName);
        dest.writeValue(this.OutPackQty);
        dest.writeValue(this.InnerPackQty);
        dest.writeValue(this.Qty);
        dest.writeInt(this.NoPack);
        dest.writeValue(this.PrintQty);
        dest.writeString(this.BarCode);
        dest.writeInt(this.BarcodeType);
        dest.writeString(this.SerialNo);
        dest.writeInt(this.BarcodeNo);
        dest.writeInt(this.OutCount);
        dest.writeInt(this.InnerCount);
        dest.writeValue(this.MantissaQty);
        dest.writeInt(this.IsRohs);
        dest.writeInt(this.OutBox_ID);
        dest.writeInt(this.Inner_ID);
        dest.writeString(this.BatchNo);
        dest.writeValue(this.ABatchQty);
        dest.writeInt(this.IsDel);
        dest.writeValue(this.VoucherQty);
        dest.writeLong(this.SupPrdDate != null ? this.SupPrdDate.getTime() : -1);
        dest.writeString(this.SupPrdBatch);
        dest.writeLong(this.ProductDate != null ? this.ProductDate.getTime() : -1);
        dest.writeString(this.ProductBatch);
        dest.writeString(this.StoreconDition);
        dest.writeString(this.SpecialRequire);
        dest.writeString(this.BarcodeMType);
        dest.writeInt(this.WareHouseID);
        dest.writeInt(this.HouseID);
        dest.writeInt(this.AreaID);
        dest.writeString(this.Palletno);
        dest.writeValue(this.PalletQty);
        dest.writeInt(this.PalletType);
        dest.writeString(this.EAN);
    }

    protected BarCodeInfo(Parcel in) {
        super(in);
        this.VoucherNo = in.readString();
        this.RowNo = in.readString();
        this.RowNoDel = in.readString();
        this.MaterialNo = in.readString();
        this.MaterialDesc = in.readString();
        this.CusCode = in.readString();
        this.CusName = in.readString();
        this.SupCode = in.readString();
        this.SupName = in.readString();
        this.OutPackQty = (Float) in.readValue(Float.class.getClassLoader());
        this.InnerPackQty = (Float) in.readValue(Float.class.getClassLoader());
        this.Qty = (Float) in.readValue(Float.class.getClassLoader());
        this.NoPack = in.readInt();
        this.PrintQty = (Float) in.readValue(Float.class.getClassLoader());
        this.BarCode = in.readString();
        this.BarcodeType = in.readInt();
        this.SerialNo = in.readString();
        this.BarcodeNo = in.readInt();
        this.OutCount = in.readInt();
        this.InnerCount = in.readInt();
        this.MantissaQty = (Float) in.readValue(Float.class.getClassLoader());
        this.IsRohs = in.readInt();
        this.OutBox_ID = in.readInt();
        this.Inner_ID = in.readInt();
        this.BatchNo = in.readString();
        this.ABatchQty = (Float) in.readValue(Float.class.getClassLoader());
        this.IsDel = in.readInt();
        this.VoucherQty = (Float) in.readValue(Float.class.getClassLoader());
        long tmpSupPrdDate = in.readLong();
        this.SupPrdDate = tmpSupPrdDate == -1 ? null : new Date(tmpSupPrdDate);
        this.SupPrdBatch = in.readString();
        long tmpProductDate = in.readLong();
        this.ProductDate = tmpProductDate == -1 ? null : new Date(tmpProductDate);
        this.ProductBatch = in.readString();
        this.StoreconDition = in.readString();
        this.SpecialRequire = in.readString();
        this.BarcodeMType = in.readString();
        this.WareHouseID = in.readInt();
        this.HouseID = in.readInt();
        this.AreaID = in.readInt();
        this.Palletno = in.readString();
        this.PalletQty = (Float) in.readValue(Float.class.getClassLoader());
        this.PalletType = in.readInt();
        this.EAN = in.readString();
    }

    public static final Creator<BarCodeInfo> CREATOR = new Creator<BarCodeInfo>() {
        @Override
        public BarCodeInfo createFromParcel(Parcel source) {
            return new BarCodeInfo(source);
        }

        @Override
        public BarCodeInfo[] newArray(int size) {
            return new BarCodeInfo[size];
        }
    };

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }
}
