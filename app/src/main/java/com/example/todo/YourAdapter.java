package com.example.todo;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
public class YourAdapter extends ArrayAdapter<YourList> {
    ArrayList<YourList> yourLists;
    LayoutInflater inflater;
    public YourAdapter(@NonNull Context context,ArrayList<YourList> yourLists) {
        super(context, 0, yourLists);
        this.yourLists = yourLists;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView ==null){
            convertView = inflater.inflate(R.layout.customadapter, parent,false);
        }
        TextView yourTitle = convertView.findViewById(R.id.yourTitle);
        TextView yourDate = convertView.findViewById(R.id.yourDate);

        YourList currentList = yourLists.get(position);

        String getcolor =currentList.colorList;
        makeShape(convertView, getcolor);

        yourTitle.setText(currentList.titleList);
        yourDate.setText(currentList.dateList);


        return convertView;
    }
    // Create a GradientDrawable and shape
    void makeShape(View convertView, String getcolor){
        View myView = convertView.findViewById(R.id.gridColor1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(convertView.getResources().getColor(R.color.grid));
        gradientDrawable.setStroke(6, Color.parseColor(getcolor));
        gradientDrawable.setCornerRadius(72);
        myView.setBackground(gradientDrawable);

    }
}
class YourList{
    String colorList;
    String titleList;
    String descriptionList;
    String dateList;
    YourList(String titleList,String descriptionList,String dateList, String colorList){
        this.titleList = titleList;
        this.colorList=colorList;
        this.descriptionList = descriptionList;
        this.dateList = dateList;

    }
}
