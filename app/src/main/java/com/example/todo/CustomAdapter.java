package com.example.todo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context context;
    LayoutInflater layoutInflater;
    CustomAdapter(ArrayList<String> list, Context context){
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size()/3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int index = position * 3;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.customadapter, parent, false);
        }
        TextView yourTitle = convertView.findViewById(R.id.yourTitle);
        TextView yourDate = convertView.findViewById(R.id.yourDate);

        yourTitle.setText(list.get(index));
        yourDate.setText(list.get(index + 2));
        return convertView;
    }
}
