package ch.zli.kilometerkoenig.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

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
    }
}