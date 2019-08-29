package com.xx.chinetek.model.Car;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GHOST on 2017/6/13.
 */

public class TransportSupplier  implements Parcelable{

    public int id ;
    public String platenumber ;
    public int isdel;
    public String PalletNo ;
    public String boxcount ;
    public String outboxcount ;
    public String creater ;
    public String erpvoucherno;
    public String customername;
    public String FEIGHT;
    public String voucherno;
    public String type ;
    public String remark ;
    public String remark1;
    public String remark2;
    public String remark3;
    public String palletno ;

    public String getPalletNo() {
        return PalletNo;
    }

    public void setPalletNo(String palletNo) {
        PalletNo = palletNo;
    }

    public String getPalletno() {
        return palletno;
    }

    public void setPalletno(String palletno) {
        this.palletno = palletno;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }



    public String getErpvoucherno() {
        return erpvoucherno;
    }

    public void setErpvoucherno(String erpvoucherno) {
        this.erpvoucherno = erpvoucherno;
    }



    public String getBoxcount() {
        return boxcount;
    }

    public String getOutboxcount() {
        return outboxcount;
    }



    public void setBoxcount(String boxcount) {
        this.boxcount = boxcount;
    }

    public void setOutboxcount(String outboxcount) {
        this.outboxcount = outboxcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Creator<TransportSupplier> getCREATOR() {
        return CREATOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }


    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getFEIGHT() {
        return FEIGHT;
    }

    public void setFEIGHT(String FEIGHT) {
        this.FEIGHT = FEIGHT;
    }

    public String getVoucherno() {
        return voucherno;
    }

    public void setVoucherno(String voucherno) {
        this.voucherno = voucherno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }


    public TransportSupplier() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.platenumber);
        dest.writeInt(this.isdel);
        dest.writeString(this.PalletNo);
        dest.writeString(this.boxcount);
        dest.writeString(this.outboxcount);
        dest.writeString(this.creater);
        dest.writeString(this.erpvoucherno);
        dest.writeString(this.customername);
        dest.writeString(this.FEIGHT);
        dest.writeString(this.voucherno);
        dest.writeString(this.type);
        dest.writeString(this.remark);
        dest.writeString(this.remark1);
        dest.writeString(this.remark2);
        dest.writeString(this.remark3);
        dest.writeString(this.palletno);
    }

    protected TransportSupplier(Parcel in) {
        this.id = in.readInt();
        this.platenumber = in.readString();
        this.isdel = in.readInt();
        this.PalletNo = in.readString();
        this.boxcount = in.readString();
        this.outboxcount = in.readString();
        this.creater = in.readString();
        this.erpvoucherno = in.readString();
        this.customername = in.readString();
        this.FEIGHT = in.readString();
        this.voucherno = in.readString();
        this.type = in.readString();
        this.remark = in.readString();
        this.remark1 = in.readString();
        this.remark2 = in.readString();
        this.remark3 = in.readString();
        this.palletno = in.readString();
    }

    public static final Creator<TransportSupplier> CREATOR = new Creator<TransportSupplier>() {
        @Override
        public TransportSupplier createFromParcel(Parcel source) {
            return new TransportSupplier(source);
        }

        @Override
        public TransportSupplier[] newArray(int size) {
            return new TransportSupplier[size];
        }
    };
}
