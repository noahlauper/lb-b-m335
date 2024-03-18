package ch.zli.kilometerkoenig.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ch.zli.kilometerkoenig.R;

public class StepCounterActivity extends AppCompatActivity {

    Button stopMeasurementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_counter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stopMeasurementButton = findViewById(R.id.stopMeasurement);

        stopMeasurementButton.setOnClickListener(view -> {
            Intent mainActivityIntend = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntend);
        });
    }
}