package tic_tac_toe;

import java.awt.EventQueue;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Computer
{
	private static URL osx = Computer.class.getClassLoader().getResource("OS-X-El-Capitan.png");
	private static URL win = Computer.class.getClassLoader().getResource("OS-Windows-8.png");
	private static final ImageIcon X = new ImageIcon(osx);
	private static final ImageIcon O = new ImageIcon(win);
	private static char[] table;
	protected static boolean notComputer;
	protected static int cntXwins;
	protected static int cntOwins;
	protected static String leftLabel = "";
	protected static String rightLabel = "";
	private static Random random;
	private static GUI gui;

	public Computer()
	{
		table = new char[9];
		notComputer = true;
		cntXwins = 0;
		cntOwins = 0;
		random = new Random();
	}

	protected static boolean isWinner()
	{
		System.out.println("isWinner()");
		int humanHorizontalCnt = 0;
		int computerHorizontalCnt = 0;
		int humanVerticalCnt = 0;
		int computerVerticalCnt = 0;
		int humanMainDiagonal = 0;
		int computerMainDiagonal = 0;
		int humanSecondDiagonal = 0;
		int computerSecondDiagonal = 0;
		int noWinnerCnt = 0;

		for(int i = 0; i < 3; i++)
		{
			// First Diagonal check
			if(table[i * 3 + i] == 'O')
			{
				computerMainDiagonal++;
			}
			else if(table[i * 3 + i] == 'X')
			{
				humanMainDiagonal++;
			}

			for(int j = 0; j < 3; j++)
			{
				// checks if there's no winner
				if(table[i * 3 + j] == 'X' || table[i * 3 + j] == 'O')
				{
					noWinnerCnt++;
				}

				// Horizontal check
				if(table[i * 3 + j] == 'O')
				{
					computerHorizontalCnt++;
				}
				else if(table[i * 3 + j] == 'X')
				{
					humanHorizontalCnt++;
				}

				// Vertical check
				if(table[j * 3 + i] == 'O')
				{
					computerVerticalCnt++;
				}
				else if(table[j * 3 + i] == 'X')
				{
					humanVerticalCnt++;
				}

				// Second Diagonal check
				if(i + j == 2 && (i + j) % 2 == 0)
				{
					if(table[i * 3 + j] == 'O')
					{
						computerSecondDiagonal++;
					}
					else if(table[i * 3 + j] == 'X')
					{
						humanSecondDiagonal++;
					}

				}
			}

			// Horizontal
			if(humanHorizontalCnt == 3)
			{
				cntXwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Player 'X' wins!", "Horizontal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			else if(computerHorizontalCnt == 3)
			{
				cntOwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Computer 'O' wins!", "Horizontal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			// Vertical
			if(humanVerticalCnt == 3)
			{
				cntXwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Player 'X' wins!", "Vertical win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			else if(computerVerticalCnt == 3)
			{
				cntOwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Computer 'O' wins!", "Vertical win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);

				clearTable();
				return true;
			}
			// Main Diagonal
			if(humanMainDiagonal == 3)
			{
				cntXwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Player 'X' wins!", "First diagonal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			else if(computerMainDiagonal == 3)
			{
				cntOwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Computer 'O' wins!", "First diagonal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			// Second Diagonal
			if(humanSecondDiagonal == 3)
			{
				cntXwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Player 'X' wins!", "Second diagonal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}
			else if(computerSecondDiagonal == 3)
			{
				cntOwins++;
				JOptionPane.showMessageDialog(GUI.contentPane, "Computer 'O' wins!", "Second diagonal win", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				clearTable();
				return true;
			}

			if(noWinnerCnt == 9)
			{
				// JOptionPane.showMessageDialog(GUI.contentPane, "No winner!",
				// "Information", JOptionPane.INFORMATION_MESSAGE);
				leftLabel = "'X' wins: " + cntXwins;
				gui.setLabel1("'X' wins: " + cntXwins);
				rightLabel = "'O' wins: " + cntOwins;
				gui.setLabel2("'O' wins: " + cntOwins);
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				clearTable();
				return true;
			}

			humanHorizontalCnt = 0;
			computerHorizontalCnt = 0;
			humanVerticalCnt = 0;
			computerVerticalCnt = 0;

		}
		return false;
	}

	private static boolean computerWinLogic()
	{
		System.out.println("computerWinLogic()");
		int computerHorizontalCnt = 0;
		int computerVerticalCnt = 0;
		int computerMainDiagonal = 0;
		int computerSecondDiagonal = 0;
		int computerHorizontal = 0;
		int computerVertical = 0;
		int mainDiagonal = 0;
		int secondDIagonal = 0;
		int vertical = 9;
		int horizontal = 3;
		int h = 0;
		int v = 0;
		int md = 0;
		int sd = 0;
		int strike = 0;

		for(int i = 0; i < 3; i++)
		{
			// First Diagonal check

			if(table[i * 3 + i] != 'O' && table[i * 3 + i] != 'X')
			{
				computerMainDiagonal++;
				mainDiagonal += (i * 3 + i);

				if(computerMainDiagonal == 2)
				{
					md = i * 3 + i;
					strike = 12 - mainDiagonal;
					if(table[strike] == 'O')
					{
						set_O_icon(gui.buttons[md]);
						isWinner();
						return true;
					}
				}
			}

			for(int j = 0; j < 3; j++)
			{
				// Horizontal check
				if(table[i * 3 + j] == 'O' || table[i * 3 + j] != 'X')
				{
					if(table[i * 3 + j] != 'O' && table[i * 3 + j] != 'X')
					{
						computerHorizontalCnt++;
						computerHorizontal += (i * 3 + j);

						if(computerHorizontalCnt == 2)
						{
							h = i * 3 + j;
							strike = horizontal - computerHorizontal;
							if(table[strike] == 'O')
							{
								set_O_icon(gui.buttons[h]);
								isWinner();
								return true;
							}
						}
					}
				}

				// Vertical check
				if(table[j * 3 + i] != 'O' && table[j * 3 + i] != 'X')
				{
					computerVerticalCnt++;
					computerVertical += (j * 3 + i);

					if(computerVerticalCnt == 2)
					{
						v = j * 3 + i;
						strike = vertical - computerVertical;
						if(table[strike] == 'O')
						{
							set_O_icon(gui.buttons[v]);
							isWinner();
							return true;
						}
					}
				}

				// Second Diagonal check
				if(i + j == 2 && (i + j) % 2 == 0)
				{
					if(table[i * 3 + j] != 'O' && table[i * 3 + j] != 'X')
					{
						computerSecondDiagonal++;
						secondDIagonal += (i * 3 + j);

						if(computerSecondDiagonal == 2)
						{
							sd = i * 3 + j;
							strike = 12 - secondDIagonal;
							if(table[strike] == 'O')
							{
								//
								set_O_icon(gui.buttons[sd]);
								isWinner();
								return true;
							}
						}
					}

				}
			}

			computerHorizontalCnt = 0;
			computerVerticalCnt = 0;
			computerHorizontal = 0;
			computerVertical = 0;
			horizontal += 9;
			vertical += 3;
		}
		return false;
	}

	private static boolean computerCheckO()
	{
		System.out.println("computerChechO()");
		int computerHorizontalCntO = 0;
		int computerVerticalCntO = 0;
		int computerMainDiagonalO = 0;
		int computerSecondDiagonalO = 0;
		int computerHorizontalO = 0;
		int computerVerticalO = 0;
		int mainDiagonalO = 0;
		int secondDIagonalO = 0;
		int verticalO = 9;
		int horizontalO = 3;
		int strike = 0;

		for(int i = 0; i < 3; i++)
		{
			// First Diagonal check
			if(table[i * 3 + i] == 'O')
			{
				computerMainDiagonalO++;
				mainDiagonalO += (i * 3 + i);
				if(computerMainDiagonalO == 2)
				{
					strike = 12 - mainDiagonalO;
					if(isFreePosition(strike))
					{

						set_O_icon(gui.buttons[strike]);
						isWinner();
						return true;
					}
				}
			}

			for(int j = 0; j < 3; j++)
			{
				// Horizontal check
				if(table[i * 3 + j] == 'O')
				{
					computerHorizontalCntO++;
					computerHorizontalO += (i * 3 + j);
					if(computerHorizontalCntO == 2)
					{
						strike = horizontalO - computerHorizontalO;
						if(isFreePosition(strike))
						{

							set_O_icon(gui.buttons[strike]);
							isWinner();
							return true;
						}
					}
				}

				// Vertical check
				if(table[j * 3 + i] == 'O')
				{
					computerVerticalCntO++;
					computerVerticalO += (j * 3 + i);
					if(computerVerticalCntO == 2)
					{
						strike = verticalO - computerVerticalO;
						if(isFreePosition(strike))
						{

							set_O_icon(gui.buttons[strike]);
							isWinner();
							return true;
						}
					}
				}

				// Second Diagonal check
				if(i + j == 2 && (i + j) % 2 == 0)
				{
					if(table[i * 3 + j] == 'O')
					{
						computerSecondDiagonalO++;
						secondDIagonalO += (i * 3 + j);
						if(computerSecondDiagonalO == 2)
						{
							strike = 12 - secondDIagonalO;
							if(isFreePosition(strike))
							{

								set_O_icon(gui.buttons[strike]);
								isWinner();
								return true;
							}
						}
					}

				}
			}

			computerHorizontalCntO = 0;
			computerVerticalCntO = 0;
			computerHorizontalO = 0;
			computerVerticalO = 0;
			horizontalO += 9;
			verticalO += 3;
		}
		return false;
	}

	private static boolean computerCheckX()
	{
		System.out.println("computerCheckX()");
		int humanHorizontalCntX = 0;
		int humanVerticalCntX = 0;
		int humanMainDiagonalX = 0;
		int humanSecondDiagonalX = 0;
		int humanHorizontalX = 0;
		int humanVerticalX = 0;
		int mainDiagonalX = 0;
		int seconDiagonalX = 0;
		int horizontalX = 3;
		int verticalX = 9;
		int strike = 0;

		for(int i = 0; i < 3; i++)
		{
			// First Diagonal check
			if(table[i * 3 + i] == 'X')
			{
				humanMainDiagonalX++;
				mainDiagonalX += (i * 3 + i);
				if(humanMainDiagonalX == 2)
				{
					strike = 12 - mainDiagonalX;
					if(isFreePosition(strike))
					{

						set_O_icon(gui.buttons[strike]);
						isWinner();
						return true;
					}
				}
			}

			for(int j = 0; j < 3; j++)
			{
				// Horizontal check
				if(table[i * 3 + j] == 'X')
				{
					humanHorizontalCntX++;
					humanHorizontalX += (i * 3 + j);
					if(humanHorizontalCntX == 2)
					{
						strike = horizontalX - humanHorizontalX;
						if(isFreePosition(strike))
						{

							set_O_icon(gui.buttons[strike]);
							isWinner();
							return true;
						}
					}
				}

				// Vertical check
				if(table[j * 3 + i] == 'X')
				{
					humanVerticalCntX++;
					humanVerticalX += (j * 3 + i);
					if(humanVerticalCntX == 2)
					{
						strike = verticalX - humanVerticalX;
						if(isFreePosition(strike))
						{

							set_O_icon(gui.buttons[strike]);
							isWinner();
							return true;
						}
					}
				}

				// Second Diagonal check
				if(i + j == 2 && (i + j) % 2 == 0)
				{
					if(table[i * 3 + j] == 'X')
					{
						humanSecondDiagonalX++;
						seconDiagonalX += (i * 3 + j);
						if(humanSecondDiagonalX == 2)
						{
							strike = 12 - seconDiagonalX;
							if(isFreePosition(strike))
							{
								set_O_icon(gui.buttons[strike]);
								isWinner();
								return true;
							}
						}
					}

				}
			}

			humanHorizontalCntX = 0;
			humanVerticalCntX = 0;
			horizontalX += 9;
			verticalX += 3;
			humanHorizontalX = 0;
			humanVerticalX = 0;
		}
		return false;
	}

	private static boolean isFreePosition(int position)
	{
		return(table[position] != 'O' && table[position] != 'X');
	}

	protected static void set_X_icon(JButton jb)
	{
		if(jb.getIcon() == null)
		{
			table[Integer.parseInt(jb.getName()) - 1] = 'X';
			System.out.println("table[" + (Integer.parseInt(jb.getName()) - 1) + "] -> " + table[Integer.parseInt(jb.getName()) - 1]);
			jb.setIcon(X);
			jb.doClick();
			notComputer = false;
		}
	}

	protected static void set_O_icon(JButton jb)
	{
		if(jb.getIcon() == null)
		{
			table[Integer.parseInt(jb.getName()) - 1] = 'O';
			System.out.println("table[" + (Integer.parseInt(jb.getName()) - 1) + "] -> " + table[Integer.parseInt(jb.getName()) - 1]);
			jb.setIcon(O);
			jb.doClick();
			notComputer = true;
		}
	}

	protected static void clearTable()
	{
		// table = null;
		table = new char[9];
		notComputer = true;
		random = new Random();

		GUI.btn1Cnt = 0;
		GUI.btn2Cnt = 0;
		GUI.btn3Cnt = 0;
		GUI.btn4Cnt = 0;
		GUI.btn5Cnt = 0;
		GUI.btn6Cnt = 0;
		GUI.btn7Cnt = 0;
		GUI.btn8Cnt = 0;
		GUI.btn9Cnt = 0;

		gui.initialize();
		gui.setVisible(true);
	}

	private static void randomO()
	{
		int rand;
		rand = random.nextInt(9);
		System.out.println("Random position: " + rand);

		// Да напиша метод, който проверява дали има само един 'Х' и ако е само
		// един -> да слага 'О' на четна позиция

		if(isFreePosition(rand))
		{
			set_O_icon(gui.buttons[rand]);
		}
		else
		{
			randomO();
		}
	}

	protected static void computersTurn()
	{
		if(!notComputer)
		{
			if(!computerCheckO())
			{
				if(!computerCheckX())
				{
					if(!computerWinLogic())
					{
						randomO();
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new Computer();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					gui = new GUI();
					gui.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
