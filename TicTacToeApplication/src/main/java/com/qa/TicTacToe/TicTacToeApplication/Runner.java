package com.qa.TicTacToe.TicTacToeApplication;

class Runner
{
	public static void main(String args[])
	{
		Player ani = new Player("Ani", false, 'x');
		Player matt = new Player("Matt", true, 'o');
		Player chris = new Player("Chris", true, 'c');
		// ======
		Player[] players =
		{
			ani,
			matt,
			//chris
		};
		Board board = new Board(players);
		board.printBoard();
		board.playerTurn();
	}
}
