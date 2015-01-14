package com.ganesh.picmsg.adapter;

import android.graphics.Bitmap;

public class Item {

    public final String name;
    public final String path;
    final Bitmap drawableId;
    final int item_id;
    public Item(String name, Bitmap drawableId,int _item_id,String _path) {
        this.name = name;
        this.drawableId = drawableId;
        this.item_id=_item_id;
        this.path=_path;
    }

}
