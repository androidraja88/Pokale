package com.ganesh.picmsg.adapter;

import com.ganesh.picmessage.Edit_Image;
import com.ganesh.picmessage.R;
import com.ganesh.picmessage.R.id;
import com.ganesh.picmessage.R.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Spinner_Font_Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context _mcontext;
    String [] list_font_path;


    public Spinner_Font_Adapter(Edit_Image con, Context _mcontext,
			String[] list_font_path) {
		super();
		this.mInflater = LayoutInflater.from(con);;
		this._mcontext = _mcontext;
		this.list_font_path = list_font_path;
	}

	@Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list_font_path.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ListContent holder;
        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.spinner_text, null);
            holder = new ListContent();

            holder.name = (TextView) v.findViewById(R.id.textView1);

            v.setTag(holder);
        } else {

            holder = (ListContent) v.getTag();
        }
        
        
        String fontpath="myfonts";

        String font=fontpath + "/" +list_font_path[position];
		
	    Typeface  mFace = Typeface.createFromAsset(_mcontext.getAssets(),font);
    

        holder.name.setTypeface(mFace);
        holder.name.setText("Sample Text");

        return v;
    }

}

class ListContent {

    TextView name;

}

