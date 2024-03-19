package ch.zli.kilometerkoenig.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        for (Measurement measurement : allMeasurements) {
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
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            container.setLayoutParams(containerParams);
            container.setBackgroundColor(Color.parseColor("#FF00FF"));
            final TextView lvl = new TextView(MainActivity.this);
            lvl.setText(measurement.getLvlPoints() + " lvl     ");
            lvl.setTextColor(Color.WHITE);
            lvl.setTextSize(22);
            container.addView(lvl);

            final TextView steps = new TextView(MainActivity.this);
            steps.setText(measurement.getSteps() + " Schritte      ");
            steps.setTextColor(Color.WHITE);
            container.addView(steps);
            steps.setTextSize(22);


            long elapsedTime = Long.parseLong(measurement.getEndTime()) - Long.parseLong(measurement.getStartTime());
            String formattedTime = formatTime(elapsedTime);


            final TextView time = new TextView(MainActivity.this);
            time.setText(formattedTime);
            time.setGravity(Gravity.CENTER);
            time.setTextColor(Color.WHITE);
            time.setTextSize(22);
            container.addView(time);
            linearLayout.addView(container);
        }
    }

    private String formatTime(long milliseconds) {
        int totalSeconds = (int) (milliseconds / 1000);
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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