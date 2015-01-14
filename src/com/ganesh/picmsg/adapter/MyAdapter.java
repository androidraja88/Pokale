package com.ganesh.picmsg.adapter;

import java.util.List;

import com.ganesh.picmessage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private List<Item> items;
    private LayoutInflater inflater;
    Context _context;
   
    public MyAdapter(Context context,List<Item> _items) {
        inflater = LayoutInflater.from(context);

        this._context=context;
       
        this.items=_items;


    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).item_id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;
        TextView path;

        if(v == null) {
            v = inflater.inflate(R.layout.gallery_listitem, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.path, v.findViewById(R.id.path));
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);
        path = (TextView)v.getTag(R.id.path);

        Item item = (Item)getItem(i);

        picture.setImageBitmap(item.drawableId);
        name.setText(item.name);
        path.setText(item.path);

        return v;
    }


}

