package practica.univalle.basicretrofitadapter.models;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import practica.univalle.basicretrofitadapter.R;

public class Horse implements Runnable {

    private String horseName; // Nuevo campo para el nombre del caballo
    private int horseImageResource; // Nuevo campo para la imagen del caballo

    private int distanceTraveled = 0;
    private final ImageView horseImage;
    private final LinearLayout horsesLayout;
    private static final int MAX_DISTANCE = 1000;

    private volatile boolean running = true;
    private int currentDistance = 0; // Agregamos esta variable para rastrear la distancia actual

    private int horseImageResId;


    public void updateProgressAndName() {
        int percentage = getProgressPercentage();

        horseImage.post(() -> {
            TextView progressText = horsesLayout.findViewById(R.id.progressText);
            progressText.setText("Progreso: " + percentage + "%");

            TextView nameText = horsesLayout.findViewById(R.id.horseName);
            nameText.setText(horseName); // Actualiza el nombre del caballo
        });
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public Horse(View view, String horseName, int horseImageResource) {
        this.horseName = horseName;
        this.horseImageResource = horseImageResource;

        horseImage = view.findViewById(R.id.horseImage);
        horseImage.setImageResource(horseImageResource); // Establece la imagen del caballo
        horsesLayout = view.findViewById(R.id.racetrackLayout);
    }

    @Override
    public void run() {
        int distance = 0;
        while (running && distance < MAX_DISTANCE) {
            try {
                Thread.sleep((long) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            distance += (int) (Math.random() * 10);
            currentDistance = Math.min(distance, MAX_DISTANCE);

            float progress = (float) currentDistance / MAX_DISTANCE;
            horseImage.post(() -> {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) horseImage.getLayoutParams();
                params.leftMargin = (int) (progress * (horsesLayout.getWidth() - horseImage.getWidth()));
                horseImage.setLayoutParams(params);
            });

            // Actualiza el TextView de progreso después de cada actualización
            updateProgressTextView();
        }
    }

    private void updateProgressTextView() {
        int percentage = getProgressPercentage();

        // Actualiza el TextView de progreso en la misma vista de caballo
        horseImage.post(() -> {
            TextView progressText = horsesLayout.findViewById(R.id.progressText); // Reemplaza horsesLayout con el ID correcto
            progressText.setText("Progreso: " + percentage + "%");
        });
    }


    public int getProgressPercentage() {
        int distance = Math.min(currentDistance, MAX_DISTANCE);
        return (int) (((float) distance / MAX_DISTANCE) * 100);
    }

    public void stop() {
        running = false;
    }


}