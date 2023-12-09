package com.example.redblue;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int Player;
    boolean setPlayer;
    View vi;
    View vW;
    View GP;
    int[] gridLayoutIds;
    Tab[] tabArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabArray = new Tab[9];
        for (int i = 0; i < tabArray.length; i++) {
            tabArray[i] = new Tab();
        }
        GP = findViewById(R.id.GridLayoutParent);
        vi = findViewById(R.id.view);
        vW = findViewById(R.id.viewWin);
        gridLayoutIds = new int[]{
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
        int i = 0;
        for (int id : gridLayoutIds) {
            GridLayout gridLayout = findViewById(id);
            setButtonClickListener(gridLayout, i, tabArray);
            i++;
        }
        Button PlayAgian = findViewById(R.id.Play);
        PlayAgian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GP.setVisibility(View.VISIBLE);
                vW.setVisibility(View.GONE);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {
                            tabArray[i].board[j][k] = 0;

                        }
                    }
                }
                for (int id : gridLayoutIds) {
                    GridLayout gridLayout = findViewById(id);

                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        View VV = gridLayout.getChildAt(i);
                        if (VV instanceof Button) {
                            Button button = (Button) VV;
                            button.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    }
                }
            }
        });
    }

    private void setButtonClickListener(GridLayout gridLayout, int gridLayoutId, Tab[] tabArray) {
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
                        setPlayer();
                        setColor(button, gridLayoutId, row, col, tabArray);
                    }
                });
            }
        }
    }

    private void setPlayer() {
        this.setPlayer = !setPlayer;
        if (setPlayer)
            Player = 1;
        else
            Player = -1;
    }

    private void setColor(Button button, int index, int row, int col, Tab[] tabArray) {
        tabArray[index].board[row][col] = Player;
        if (Player == 1) {
            if (tabArray[index].checkWin() == Player) {
                GP.setVisibility(View.GONE);
                vW.setVisibility(View.VISIBLE);
                vW.setBackgroundColor(getResources().getColor(R.color.redLight));
                return;
            }
            vi.setBackgroundColor(getResources().getColor(R.color.blueBright));
            button.setBackgroundColor(getResources().getColor(R.color.redLight));
        } else {
            if (tabArray[index].checkWin() == Player) {
                GP.setVisibility(View.GONE);
                vW.setVisibility(View.VISIBLE);
                vW.setBackgroundColor(getResources().getColor(R.color.blueBright));
                return;
            }
            vi.setBackgroundColor(getResources().getColor(R.color.redLight));
            button.setBackgroundColor(getResources().getColor(R.color.blueBright));
        }
    }

    class Tab {
        int[][] board;

        public Tab() {
            this.board = new int[3][3];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    board[i][j] = 0;  // Initialize with some default value
                }
            }
        }

        public int checkWin() {
            for (int i = 0; i < 3; i++) {
                if ((board[i][0] == Player && board[i][1] == Player && board[i][2] == Player) ||
                        (board[0][i] == Player && board[1][i] == Player && board[2][i] == Player)) {
                    return Player; // Row or column win
                }
            }

            if ((board[0][0] == Player && board[1][1] == Player && board[2][2] == Player) ||
                    (board[0][2] == Player && board[1][1] == Player && board[2][0] == Player)) {
                return Player; // Row or column win
            }
            return 0;
        }
    }

}

