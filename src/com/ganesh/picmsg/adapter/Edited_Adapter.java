package com.ganesh.picmsg.adapter;

import java.util.List;

import com.ganesh.picmessage.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Edited_Adapter extends BaseAdapter {
    private List<Item> items;
    private LayoutInflater inflater;
    Context _context;
   
    public Edited_Adapter(Context context,List<Item> _items) {
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
        final TextView path;
        ImageView Whatsapp;
        ImageView Facebook;
        
        ImageView Share;

        if(v == null) {
            v = inflater.inflate(R.layout.edited_listitem, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.path, v.findViewById(R.id.path));
            
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);
        path = (TextView)v.getTag(R.id.path);
        Whatsapp = (ImageView)v.findViewById(R.id.whatsapp);
        Facebook = (ImageView)v.findViewById(R.id.facebook);
        
        Share = (ImageView)v.findViewById(R.id.share);
        
        
        Item item = (Item)getItem(i);

        picture.setImageBitmap(item.drawableId);
        name.setText(item.name);
        path.setText(item.path);

        
        
        Whatsapp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent waIntent = new Intent(Intent.ACTION_SEND);
				waIntent.setType("image/*");
				   waIntent.setPackage("com.whatsapp");
				waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+path.getText().toString()));
				//startActivity(Intent.createChooser(waIntent, "Share Image"));
				 if (waIntent != null) {
			      
					 _context.startActivity(Intent.createChooser(waIntent, "Share with"));
			    } 
			}
		});
        
        Facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent waIntent = new Intent(Intent.ACTION_SEND);
				waIntent.setType("image/*");
				   waIntent.setPackage("com.facebook.katana");
				waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+path.getText().toString()));
				//startActivity(Intent.createChooser(waIntent, "Share Image"));
				 if (waIntent != null) {
			      
					 _context.startActivity(Intent.createChooser(waIntent, "Share with"));
			    } 
			}
		});
        
 
        
        Share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String save_path=path.getText().toString();

				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("image/*");

				share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+save_path));
				_context.startActivity(Intent.createChooser(share, "Share Image"));
			}
		});
        return v;
    }


}

