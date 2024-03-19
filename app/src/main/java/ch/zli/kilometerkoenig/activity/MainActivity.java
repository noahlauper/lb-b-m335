package ch.zli.kilometerkoenig.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    TextView lvlText;

    private long startTime;

    int lvl;
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

        lvlText = findViewById(R.id.lvl);
        startMeasurementButton = findViewById(R.id.startMeasurement);

        startMeasurementButton.setOnClickListener(view -> {
            startTime = System.currentTimeMillis();
            Intent stepCounterIntent = new Intent(this, StepCounterActivity.class);
            stepCounterIntent.putExtra("startTime", startTime);
            startActivity(stepCounterIntent);
        });
        getMeasurements();
    }


    private void setLvl() {
        for (Measurement measurement: allMeasurements) {
            lvl += measurement.getLvlPoints();
        }
        lvlText.setText("lvl: " + lvl);
        if (lvl >= 1000) {
            ImageView trophyImage = findViewById(R.id.trophyImage);
            trophyImage.setVisibility(View.VISIBLE);
        }
    }


    public void displayMeasurements() {
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.measurementLayout);
        System.out.println(allMeasurements);
        for (Measurement measurement : allMeasurements) {
            LinearLayout container = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.setLayoutParams(params);
            container.setBackgroundColor(Color.parseColor("#FF00FF"));
            final TextView lvl = new TextView(MainActivity.this);
            lvl.setText(measurement.getLvlPoints() + " lvl                     ");
            lvl.setTextColor(Color.WHITE);
            container.addView(lvl);

            final TextView steps = new TextView(MainActivity.this);
            steps.setText(measurement.getSteps() + " Schritte");
            steps.setTextColor(Color.WHITE);
            container.addView(steps);

            long elapsedTime = Long.parseLong(measurement.getEndTime()) - Long.parseLong(measurement.getStartTime());
            String formattedTime = formatTime(elapsedTime);



            linearLayout.addView(container);
        }

    }

    private String formatTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    public void getMeasurements() {
        Runnable myRunnable = () ->
        {
            try {
                allMeasurements = AppDatabase.getInstance(this).measurementDao().getAll();
                displayMeasurements();
                setLvl();
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        Thread thread = new Thread(myRunnable);
        thread.start();
    }

}