package com.example.redblue;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean buttonRed;
    View vi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vi = findViewById(R.id.view);
        int[] gridLayoutIds = {
                R.id.gridLayoutA,
                R.id.gridLayoutB,
                R.id.gridLayoutC,
                R.id.gridLayoutD,
                R.id.gridLayoutE,
                R.id.gridLayoutF,
                R.id.gridLayoutG,
                R.id.gridLayoutH,
                R.id.gridLayoutI
        };
        for (int id : gridLayoutIds) {
            GridLayout gridLayout = findViewById(id);
            setButtonClickListener(gridLayout);
        }
    }

    private void setButtonClickListener(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                Button button = (Button) view;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = gridLayout.indexOfChild(view);
                        int row = index / gridLayout.getColumnCount();
                        int col = index % gridLayout.getColumnCount();
                        setButtonRed();
                        setColor(button);
                    }
                });
            }
        }
    }

    private void setButtonRed() {
        this.buttonRed = !buttonRed;
    }

    private void setColor(Button button) {
        if (buttonRed) {
            vi.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        } else {
            vi.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        }

    }
}
