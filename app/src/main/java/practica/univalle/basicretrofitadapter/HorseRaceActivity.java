package practica.univalle.basicretrofitadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import practica.univalle.basicretrofitadapter.models.Horse;

public class HorseRaceActivity extends AppCompatActivity {


    private static final int NUM_CABALLOS = 10;

    private TextView progressTextView;


    private Horse[] horses = new Horse[NUM_CABALLOS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_race);

        progressTextView = findViewById(R.id.progressText);

        LinearLayout layout = findViewById(R.id.horsesLayout);
        for (int i = 0; i < NUM_CABALLOS; i++) {
            View horseView = getLayoutInflater().inflate(R.layout.horse_layout, layout, false);
            String horseName = "Caballo " + (i + 1);
            int horseImageResource = getResources().getIdentifier("horse" + (i + 1), "drawable", getPackageName());

            horses[i] = new Horse(horseView, horseName, horseImageResource);
            layout.addView(horseView);
        }


        Button startRaceButton = findViewById(R.id.startRaceButton);
        startRaceButton.setOnClickListener(v -> startRace());


        Button stopRaceButton = findViewById(R.id.detener);
        stopRaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });


    }





    private void startRace() {
        for (Horse horse : horses) {
            new Thread(horse).start();
        }
    }

    private void stop() {
        for (Horse horse : horses) {
            horse.stop();
        }
    }

    private void updateProgressTextView() {
        StringBuilder progressText = new StringBuilder("Progreso: ");
        for (int i = 0; i < NUM_CABALLOS; i++) {
            int percentage = horses[i].getProgressPercentage();
            progressText.append("Caballo ").append(i + 1).append(": ").append(percentage).append("%");
            if (i < NUM_CABALLOS - 1) {
                progressText.append(", ");
            }
        }
        progressTextView.setText(progressText.toString());
    }


}