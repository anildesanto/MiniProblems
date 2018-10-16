package com.qa.TicTacToe.TicTacToeApplication;

class Player
{
	String name;
	boolean computer;
	char pieceType = 'x';

	public Player(String name, boolean computer, char pieceType)
	{
		this.name = name;
		this.computer = computer;
		this.pieceType = pieceType;
	}
}