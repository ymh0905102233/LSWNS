/***********************************************
 * CONFIDENTIAL AND PROPRIETARY
 *
 * The source code and other information contained herein is the confidential and the exclusive property of
 * ZIH Corp. and is subject to the terms and conditions in your end user license agreement.
 * This source code, and any other information contained herein, shall not be copied, reproduced, published,
 * displayed or distributed, in whole or in part, in any medium, by any means, for any purpose except as
 * expressly permitted under such license agreement.
 *
 * Copyright ZIH Corp. 2012
 *
 * ALL RIGHTS RESERVED
 ***********************************************/

package com.zebra.android.devdemo.multichannel;

import com.zebra.android.devdemo.ConnectionScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 */
public class MultiChannelDemo extends ConnectionScreen {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testButton.setText("Run Multi channel test");
        statusPortLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void performTest() {
        Intent intent;
        intent = new Intent(this, MultiChannelScreen.class);
        intent.putExtra("bluetooth selected", isBluetoothSelected());
        intent.putExtra("mac address", getMacAddressFieldText());
        intent.putExtra("tcp address", getTcpAddress());
        intent.putExtra("tcp port", getTcpPortNumber());
        intent.putExtra("tcp status port", getTcpStatusPortNumber());
        startActivity(intent);
    }

    protected int desiredVisibilityForSecondTestButton() {
        return View.GONE;
    }
}
