package nl.kvtulder.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Game game;
    int[] buttons = {R.id.game00,R.id.game01,R.id.game02,R.id.game10,R.id.game11,R.id.game12,
            R.id.game20,R.id.game21,R.id.game22};
    Boolean againstComputer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if there is a saved game; if so, restore it.
        if(savedInstanceState!=null)
        {
            game = (Game) savedInstanceState.getSerializable("game");
            Tile[][] board = game.getBoard();
            updateUI(board);
        }
        // no saved game found, create a new one.
        else
            game = new Game();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // the game is shut down; save the current state
        outState.putSerializable("game",game);
    }

    public void tileClicked(View view) {

        // check if game is already over
        if(game.getGameOver())
        {
            Toast.makeText(this, R.string.game_over, Toast.LENGTH_SHORT).show();
            // game is already over, cancel the called function
            return;
        }

        // get the button
        Button button = (Button) view;
        int ID = button.getId();

        int row = 0;
        int column = 0;

        for(int i = 0;i < buttons.length;i++) {
            if(buttons[i]==ID) {
                // match found, get the board id and break the loop
                column = i / 3;
                row = i % 3;
                break;
            }
        }

        // check if the input is valid,
        Tile tile = game.draw(row,column);
        switch (tile)
        {
            case INVALID:
                Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show();
                // invalid move, break the loop and let the player make a new move
                break;
            case CIRCLE:
                button.setText("O");
                break;
            case CROSS:
                button.setText("X");
        }

        // check if game is over
        GameState gameState = game.getGameState();
        switch (gameState)
        {
            case IN_PROGRESS:
                // game still in process, do nothing
                break;
            case DRAW:
                // a draw, display a toast to inform the players
                Toast.makeText(this,R.string.draw_message, Toast.LENGTH_SHORT).show();
                break;
            case PLAYER_ONE:
                // player one has won
                Toast.makeText(this,R.string.p1_won_message, Toast.LENGTH_SHORT).show();
                break;
            case PLAYER_TWO:
                // player two has won
                Toast.makeText(this,R.string.p2_won_message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void resetClicked(View v)
    {
        // reset the game
        game = new Game();
        // reset the UI
        for(int buttonID : buttons)
        {
            Button button = findViewById(buttonID);
            button.setText("");
        }
    }

    // updates the user interface
    public void updateUI(Tile[][] board)
    {
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++) {
                // convert grid index to list index by using j*3 + i
                Button button = findViewById(buttons[j*3 + i]);
                switch (board[i][j]){
                    case CROSS:
                        button.setText("X");
                        break;
                    case CIRCLE:
                        button.setText("O");
                        break;
                    default:
                        button.setText("");
                }
            }
        }
    }
}
