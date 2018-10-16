package com.qa.TicTacToe.TicTacToeApplication;

import java.util.*;

class Board
{
	int count;
	boolean gameEnded;
	Piece[][] grid;
	int size = 3;
	int turn = 0;
	Player currentPlayer;
	Player[] playersList;

	public Board(Player[] players)
	{
		int length = players.length;
		playersList = players;
		if (length > (size - 1))
			this.size = size + (length - 2);
		grid = new Piece[this.size][this.size];

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				grid[i][j] = new Piece(i + 1, j + 1);
			}
		}
	}

	public void printBoard()
	{
		System.out.println("======== Tic Tac Toe =======");
		for (int i = 0; i < size; i++)
		{
			System.out.print(" " + (i + 1) + " ");
		}
		System.out.println("");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (j == 0)
					System.out.print(i + 1);
				Piece p = grid[i][j];
				if (p.getType() == Piece.getEmptyPiece())
					System.out.print("[_]");
				else
					System.out.print("[" + p.getType() + "]");
				
			}
			System.out.println("");
		}
	}

	int i = 1;

	public boolean checkForWin()
	{
		long[] counts = new long[size * size];

		// Piece [] arr =

		for (int b = 0; i <= size; b += 2)
		{
			// horizontal check
			counts[b] = Arrays.stream(grid).flatMap(a -> Arrays.stream(a)).filter(p -> p.getType() != Piece.getEmptyPiece())
					.filter(p -> p.getType() == currentPlayer.pieceType).map(p -> p.getType() + "" + findLocation(p))
					.filter(p -> p.substring(1, 3).startsWith("" + i + "")).count();

			// ======
			// vertical check
			counts[b + 1] = Arrays.stream(grid).flatMap(a -> Arrays.stream(a)).filter(p -> p.getType() != Piece.getEmptyPiece())
					.filter(p -> p.getType() == currentPlayer.pieceType).map(p -> p.getType() + "" + findLocation(p))
					.filter(p -> p.substring(1, 3).endsWith("" + i + "")).count();

			i++;
		}

		// diagonal checks
		for (int b = 0, j = size - 1; b < size; b++, j--)
		{

			Piece p = grid[b][b];

			if (p.getType() != Piece.getEmptyPiece() && p.getType() == currentPlayer.pieceType)
				counts[i + (size - 1)]++;
			p = grid[j][b];
			if (p.getType() != Piece.getEmptyPiece() && p.getType() == currentPlayer.pieceType)
				counts[i + size]++;

		}

		i = 1;
		// Arrays.stream(counts).forEach(a -> System.out.print(a+","));
		return Arrays.stream(counts).anyMatch(c -> c == size);
	}

	public void addPiece(int x, int y)
	{
		Piece p = grid[x - 1][y - 1];
		if (p.getType() == Piece.getEmptyPiece())
		{
			grid[x - 1][y - 1] = new Piece(currentPlayer.pieceType, x, y);
			turn++;
		}
		else
			System.out.println("There is already an " + p.getType() + " in there!");
		printBoard();
		if (checkForWin())
		{
			System.out.println(currentPlayer.name + " Wins!");
			gameEnded = true;
		}
		count++;
		if (count == size * size)
			gameEnded = true;
	}

	public void playerTurn()
	{
		while (!gameEnded)
		{
			currentPlayer = playersList[turn];
			System.out.println(currentPlayer.name + "'s Turn");
			System.out.println("Please enter Row and colum");

			if (currentPlayer.computer)
				chooseRandom();
			else
			{
				Scanner reader = new Scanner(System.in);
				int n = Integer.parseInt(reader.next());
				addPiece(n/10,n%10);
			}
			if (turn == playersList.length)
				turn = 0;
		}
	}

	public void chooseRandom()
	{
		Optional<Piece> isFound = Arrays.stream(grid).flatMap(a -> Arrays.stream(a)).filter(a -> a.getType() != Piece.getEmptyPiece())
				.filter(a -> a.getType() == currentPlayer.pieceType).skip(count).findFirst();

		String[] pos = Arrays.stream(grid).flatMap(a -> Arrays.stream(a)).filter(a -> a.getType() == Piece.getEmptyPiece()).map(a -> {
			return a.getRow() + "" + a.getColumn();
		}).toArray(String[]::new);

		System.out.print(Arrays.toString(pos));
		if (isFound.isPresent())
		{
			Piece p = isFound.get();
			int num = findLocation(p);
			int row = num / 10;
			int nextRow = row < 2 ? row + 1 : row - 1;
			int column = num % 10;
			int nextColumn = (column < 2) ? column + 1 : column - 1;
			if (grid[nextRow - 1][column - 1].getType() == Piece.getEmptyPiece())
			{
				autoPlay(nextRow, column);
			}
			else if (grid[row - 1][nextColumn - 1].getType() == Piece.getEmptyPiece())
			{
				autoPlay(row, nextColumn);
			}
			/*
			 * else if(grid[row-1][row-1] == null) { autoPlay(row,row); } else
			 * if(grid[column-1][column-1] == null) { autoPlay(column,column); } else
			 * if(grid[nextRow-1][nextRow-1] == null) { autoPlay(nextRow,nextRow); } else
			 * if(grid[nextColumn-1][nextColumn-1] == null) {
			 * autoPlay(nextColumn,nextColumn); }
			 */
			else
				chooseRandom();
		}
		else
		{
			Random rand = new Random();
			int row = rand.nextInt(size) + 1;
			int column = rand.nextInt(size) + 1;
			if (grid[row - 1][column - 1].getType() == Piece.getEmptyPiece())
			{
				autoPlay(row, column);
			}
			else
				chooseRandom();
		}
	}

	public void autoPlay(int row, int column)
	{
		System.out.println("Row Chosen: " + row + " Column Chosen: " + column);
		addPiece(row, column);
	}

	public int findLocation(Piece pToFind)
	{
		int row = 0;
		int column = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				Piece p = grid[i][j];
				if (p == pToFind)
				{
					row = i;
					column = j;
				}
			}
		}
		return Integer.parseInt((row + 1) + "" + (column + 1));
	}
}



