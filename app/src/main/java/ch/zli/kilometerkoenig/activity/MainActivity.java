package ch.zli.kilometerkoenig.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import ch.zli.kilometerkoenig.R;
import ch.zli.kilometerkoenig.domain.AppDatabase;
import ch.zli.kilometerkoenig.domain.entity.Measurement;

public class MainActivity extends AppCompatActivity {

    Button startMeasurementButton;


    List<Measurement> allMeasurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        startMeasurementButton = findViewById(R.id.startMeasurement);

        startMeasurementButton.setOnClickListener(view -> {
            Intent stepCounterIntent = new Intent(this, StepCounterActivity.class);
            startActivity(stepCounterIntent);
        });
        getMeasurements();
    }





    public void getMeasurements() {
        Runnable myRunnable = () ->
        {
            try {
                allMeasurements = AppDatabase.getInstance(this).measurementDao().getAll();
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        Thread thread = new Thread(myRunnable);
        thread.start();

    }

}