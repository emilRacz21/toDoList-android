package com.example.todo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView makeList;
    GridView todoList;
    LinearLayout setEmpty;
    String title1;
    CalendarView getDate;
    String date1;
    String calendarDate;
    String description1;
    String color1;
    YourAdapter yourAdapter;
    ArrayList<YourList> yourLists = new ArrayList<>();
    ColorInfo[] colors = {
            new ColorInfo("Black", "#000000"),
            new ColorInfo("Red", "#ff0000"),
            new ColorInfo("Green", "#00ff00"),
            new ColorInfo("Blue", "#0000ff"),
            new ColorInfo("Yellow", "#ffff00"),
            new ColorInfo("Magenta", "#ff00ff"),
            new ColorInfo("Cyan", "#00ffff"),
            new ColorInfo("Purple", "#800080"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEmpty = findViewById(R.id.emptyList);
        makeList = findViewById(R.id.addList);
        todoList = findViewById(R.id.todoList);
        makeList.setOnClickListener(view -> makeDialog());
        //get your list position and show more content.
        sendToDo();
        //long click to remove.
        listLongClick();
    }
    //Long click remove your collection.
    void listLongClick(){
        todoList.setOnItemLongClickListener((parent, view, position, id) -> {
            removeItemDialog(position);
            return true;
        });
    }
    //remove task from gridview.
    void removeItemDialog(int position){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Remove collecion")
                .setMessage("Do you want to delete this collecion?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    makeToast("Removed!");
                    yourLists.remove(position);
                    clearWindowItem();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                    makeToast("Cancelled!");
                })
                .show();
    }
    //check if the deleted task is still in gridview
    void clearWindowItem(){
        yourAdapter.notifyDataSetChanged();
        if(yourLists.size() == 0){
            setEmpty.setVisibility(View.VISIBLE);
        }
    }
    //click listview to go to the another site.
    void sendToDo(){
        todoList.setOnItemClickListener((parent, view, position, id) -> makeIntent(position));
    }

    //send string to another activity class.

    void makeIntent(int position){
        Intent showMoreInfo = new Intent(MainActivity.this, ResultActivity.class);
        YourList values = yourLists.get(position);
        showMoreInfo.putExtra("title", values.titleList);
        showMoreInfo.putExtra("date", values.dateList);
        showMoreInfo.putExtra("description", values.descriptionList);
        startActivity(showMoreInfo);
    }

    //dialog when you click add to list
    void makeDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        Button accept = dialog.findViewById(R.id.acceptBtn);
        TextView getTitle = dialog.findViewById(R.id.getTitle);
        Spinner colorSpinner = dialog.findViewById(R.id.colorSpinner);
        colorAdapter(colorSpinner);

        getDate= dialog.findViewById(R.id.calendarView);
        TextView getDescription = dialog.findViewById(R.id.getDescription);
        datePicker();
        accept.setOnClickListener(view -> dialogPositive(getTitle, getDescription, dialog));
        cancel.setOnClickListener(view ->{
            dialog.dismiss();
            makeToast("Cancelled!");
        });
        dialog.show();
    }

    //select color stroke
    void colorAdapter(Spinner colorSpinner){
        ArrayAdapter<ColorInfo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ColorInfo selectedColor = (ColorInfo) parent.getItemAtPosition(position);
                color1 = selectedColor.colorName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //option when you click the positive button.
    void dialogPositive(TextView getTitle, TextView getDescription, Dialog dialog){
        if(String.valueOf(getTitle.getText()).isEmpty() || String.valueOf(getDate.getDate()).isEmpty() || String.valueOf(getDescription.getText()).isEmpty() ||String.valueOf(getTitle.getText()).length()>=15 ){
            makeToast("Fill all labels or change title!");
        }else {
            dialogAcceptBtn(getTitle, getDate, getDescription);
            dialog.dismiss();
            adapter();
            setEmpty.setVisibility(View.GONE);
        }
    }

    //here you can make toast
    void makeToast(String content){
        Toast.makeText(this,content, Toast.LENGTH_SHORT).show();
    }
    //option when you click the positive button.
    void dialogAcceptBtn(TextView getTitle, CalendarView getDate, TextView getDescription){
        title1 = String.valueOf(getTitle.getText());
        description1 = String.valueOf(getDescription.getText());
        date1 = calendarDate;
        setTable();
    }
    //pick your random data.
    void datePicker(){
        getDate.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String zeroMonth = (month+1)<=9 ? "0"+(month+1) : String.valueOf(month+1);
            calendarDate = dayOfMonth + "/" + zeroMonth + "/" + year;
        });
    }
    //your custom arraylist with class YourList.
    void setTable(){
        yourLists.add(new YourList(title1, description1, date1, color1));
    }
    //set your adapter
    void adapter(){
        yourAdapter= new YourAdapter(this, yourLists);
        todoList.setAdapter(yourAdapter);
    }
}
//class with colors to spinner
 class ColorInfo {
     String colorName;
     String colorCode;
    public ColorInfo(String colorName, String colorCode) {
        this.colorName = colorName;
        this.colorCode = colorCode;
    }
    @NonNull
    public String toString(){
        return colorName;
    }
}