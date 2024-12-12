public class GameLogic {

    private int[][] board = new int[3][3];
    private boolean playerX = true;
    private boolean gameDone = false;
    private int winner = -1;

    public void resetGame() {
        board = new int[3][3];
        playerX = true;
        gameDone = false;
        winner = -1;
    }

    public boolean makeMove(int x, int y) {
        if (board[x][y] != 0) return false;
        board[x][y] = playerX ? 1 : 2;
        playerX = !playerX;
        checkWinner();
        return true;
    }

    private void checkWinner() {

        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                setWinner(board[i][0]);
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                setWinner(board[0][i]);
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            setWinner(board[0][0]);
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            setWinner(board[0][2]);

        // tie
        if (winner == -1) {
            boolean tie = true;
            for (int[] row : board)
                for (int cell : row)
                    if (cell == 0) tie = false;

            if (tie) {
                gameDone = true;
                winner = 3;
            }
        }
    }

    private void setWinner(int winner) {
        this.winner = winner;
        this.gameDone = true;
    }

    public int getBoardValue(int x, int y) {
        return board[x][y];
    }

    public boolean isGameDone() {
        return gameDone;
    }

    public int getWinner() {
        return winner;
    }

    public String getWinnerText() {
        return winner == 1 ? "X" : winner == 2 ? "O" : "Tie";
    }

    public String getCurrentPlayerText() {
        return playerX ? "X" : "O";
    }
}
