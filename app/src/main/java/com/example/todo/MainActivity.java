package com.example.todo;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView makeList;
    ListView todoList;
    LinearLayout setEmpty;
    String title1;
    CalendarView getDate;
    String date1;
    String calendarDate;
    String description1;
    ArrayList<String> values = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEmpty = findViewById(R.id.emptyList);
        makeList = findViewById(R.id.addList);
        todoList = findViewById(R.id.todoList);
        makeList.setOnClickListener(view -> makeDialog());

        //get your list position and show more.
        sendToDo();
    }

    //click listview to go to the another site.
    void sendToDo(){
        todoList.setOnItemClickListener((parent, view, position, id) -> {
            int index = position * 3;
            makeIntent(index);
        });
    }

    void makeIntent(int index){
        Intent showMoreInfo = new Intent(MainActivity.this, ResultActivity.class);
        showMoreInfo.putExtra("title", values.get(index));
        showMoreInfo.putExtra("date", values.get(index+2));
        showMoreInfo.putExtra("description", values.get(index+1));
        startActivity(showMoreInfo);
    }
    void makeDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        Button accept = dialog.findViewById(R.id.acceptBtn);
        TextView getTitle = dialog.findViewById(R.id.getTitle);
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
    void setTable(){
        values.add(title1);
        values.add(description1);
        values.add(date1);
    }
    void adapter(){
        CustomAdapter customAdapter = new CustomAdapter(values,this);
        todoList.setAdapter(customAdapter);
    }
}