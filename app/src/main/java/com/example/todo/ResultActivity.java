package com.example.todo;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
public class ResultActivity extends AppCompatActivity {
    TextView titleIntent;
    TextView dateIntent;
    TextView descriptionIntent;
    TextView returnBtn;
    String title;
    String date;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
        Intent getStrings = getIntent();
        getSTtringIntent(getStrings);

        titleIntent = findViewById(R.id.titleIntent);
        dateIntent = findViewById(R.id.dateIntent);
        descriptionIntent = findViewById(R.id.descriptionIntent);
        returnBtn = findViewById(R.id.returnBtn);

        setResultText();

        returnBtn.setOnClickListener( v-> onBackPressed());
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
    void getSTtringIntent(Intent getStrings){
        title= getStrings.getStringExtra("title");
        date = getStrings.getStringExtra("date");
        description = getStrings.getStringExtra("description");
    }
    void setResultText(){
        titleIntent.setText(title);
        dateIntent.setText(date);
        descriptionIntent.setText(description);
    }
}