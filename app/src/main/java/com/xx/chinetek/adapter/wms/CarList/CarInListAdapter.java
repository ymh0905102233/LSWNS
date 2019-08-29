package com.xx.chinetek.adapter.wms.CarList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Car.TransportSupplier;
import com.xx.chinetek.model.Material.BarCodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHOST on 2017/1/13.
 */

public class CarInListAdapter extends BaseAdapter {
    private Context context; // 运行上下文
    private ArrayList<TransportSupplier> transportSuppliers; // 信息集合
    private LayoutInflater listContainer; // 视图容器


    public final class ListItemView { // 自定义控件集合
        public TextView txtBarcode;
        public TextView txtMaterialName;
        public TextView txtBatch;
    }

    public CarInListAdapter(Context context, ArrayList<TransportSupplier> TransportSuppliers) {
        this.context = context;
        listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.transportSuppliers = TransportSuppliers;

    }


    @Override
    public int getCount() {
        return transportSuppliers==null?0: transportSuppliers.size();
    }

    @Override
    public Object getItem(int position) {
        return transportSuppliers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int selectID = position;
        // 自定义视图
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();

            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.item_pallet_listview,null);
            listItemView.txtBarcode = (TextView) convertView.findViewById(R.id.item_Barcode);
            listItemView.txtMaterialName = (TextView) convertView.findViewById(R.id.item_MattterialName);
            listItemView.txtBatch = (TextView) convertView.findViewById(R.id.item_Batch);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        TransportSupplier ransportSupplier=transportSuppliers.get(selectID);
        listItemView.txtBarcode.setText(ransportSupplier.getErpvoucherno());
        listItemView.txtMaterialName.setText(ransportSupplier.getCustomername());
        listItemView.txtBatch.setText(ransportSupplier.getBoxcount()+ "");
        return convertView;
    }




}