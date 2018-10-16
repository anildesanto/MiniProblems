package com.qa.TicTacToe.TicTacToeApplication;

class Piece
{
	public static char emptyPiece = '0';
	private char type;
	private int row;
	private int column;

	public Piece(char type, int row, int column)
	{
		this.type = type;
		this.row = row;
		this.column = column;
	}
	public Piece(int row, int column)
	{
		this.type = emptyPiece;
		this.row = row;
		this.column = column;
	}
	public static char getEmptyPiece()
	{
		return emptyPiece;
	}
	public char getType()
	{
		return type;
	}
	public void setType(char type)
	{
		this.type = type;
	}
	public int getRow()
	{
		return row;
	}
	public void setRow(int row)
	{
		this.row = row;
	}
	public int getColumn()
	{
		return column;
	}
	public void setColumn(int column)
	{
		this.column = column;
	}
}











