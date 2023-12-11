package com.example.redblue;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int Player;
    boolean setPlayer;
    View vi;
    View GP;
    int[][] gridLayoutIds;
    Board[][] boardA;
    private int originalColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardA = new Board[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardA[i][j] = new Board();
            }
        }
        GP = findViewById(R.id.GridLayoutParent);
        vi = findViewById(R.id.view);
        gridLayoutIds = new int[][]{
                {R.id.gridLayoutA, R.id.gridLayoutB, R.id.gridLayoutC},
                {R.id.gridLayoutD, R.id.gridLayoutE, R.id.gridLayoutF},
                {R.id.gridLayoutG, R.id.gridLayoutH, R.id.gridLayoutI}
        };
        for (int row = 0; row < gridLayoutIds.length; row++) {
            for (int col = 0; col < gridLayoutIds[row].length; col++) {
                int id = gridLayoutIds[row][col];
                GridLayout gridLayout = findViewById(id);
                setButtonClickListener(gridLayout, row, col, boardA);
            }
        }
        Button PlayAgian = findViewById(R.id.Play);
        PlayAgian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GP.setBackgroundColor(Color.TRANSPARENT);
                for (int row = 0; row < gridLayoutIds.length; row++) {
                    for (int col = 0; col < gridLayoutIds[row].length; col++) {
                        int id = gridLayoutIds[row][col];
                        GridLayout gridLayout = findViewById(id);
                        gridLayout.setVisibility(View.VISIBLE);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                boardA[i][j].board[k][l] = 0;
                            }
                        }
                    }
                }
                for (int row = 0; row < gridLayoutIds.length; row++) {
                    for (int col = 0; col < gridLayoutIds[row].length; col++) {
                        int id = gridLayoutIds[row][col];
                        GridLayout gridLayout = findViewById(id);
                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            Button button = (Button) gridLayout.getChildAt(i);
                            button.setBackgroundColor(getResources().getColor(R.color.defualt));
                        }
                    }
                }
            }
        });
    }

    private void setButtonClickListener(GridLayout gridLayout, int rowA, int colA, Board[][] boardA) {
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
                        setColor(button, rowA, colA, row, col, boardA);
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

    private void setColor(Button button, int rowA, int colA, int row, int col, Board[][] boardA) {
        boardA[rowA][colA].board[row][col] = Player;
        if (Player == 1) {
            button.setBackgroundColor(getResources().getColor(R.color.redLight));
            if (boardA[rowA][colA].checkWin() == Player) {
                int id = gridLayoutIds[rowA][colA];
                GridLayout gridLayout = findViewById(id);
                gridLayout.setBackgroundColor(getResources().getColor(R.color.redLight));
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    Button buttonA = (Button) gridLayout.getChildAt(i);
                    buttonA.setVisibility(View.GONE);
                }
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (boardA[i][j].board[rowA][colA] == 0) {
                            boardA[i][j].board[rowA][colA]=Player;
                            id = gridLayoutIds[i][j];
                            gridLayout = findViewById(id);
                            int numColumns = gridLayout.getColumnCount();
                            int index = rowA * numColumns + colA;
                            Button buttonA = (Button) gridLayout.getChildAt(index);
                            buttonA.setBackgroundColor(getResources().getColor(R.color.redLight));
                        }
                    }
                }
                return;
            }
            vi.setBackgroundColor(getResources().getColor(R.color.blueBright));
        } else if (Player == -1) {
            button.setBackgroundColor(getResources().getColor(R.color.blueBright));
            if (boardA[rowA][colA].checkWin() == Player) {
                int id = gridLayoutIds[rowA][colA];
                GridLayout gridLayout = findViewById(id);
                gridLayout.setBackgroundColor(getResources().getColor(R.color.blueBright));
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    Button buttonA = (Button) gridLayout.getChildAt(i);
                    buttonA.setVisibility(View.GONE);
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (boardA[i][j].board[rowA][colA] == 0) {
                            boardA[i][j].board[rowA][colA]=Player;
                            id = gridLayoutIds[i][j];
                            gridLayout = findViewById(id);
                            int numColumns = gridLayout.getColumnCount();
                            int index = rowA * numColumns + colA;
                            Button buttonA = (Button) gridLayout.getChildAt(index);
                            buttonA.setBackgroundColor(getResources().getColor(R.color.blueBright));
                        }
                    }
                }
                return;
            }
            vi.setBackgroundColor(getResources().getColor(R.color.redLight));
        }
    }

    class Board {
        int[][] board;

        public Board() {
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

