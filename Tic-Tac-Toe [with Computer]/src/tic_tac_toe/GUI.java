package tic_tac_toe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GUI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7141171492725639138L;
	protected static JPanel contentPane;
	private JPanel panel;
	private JPanel btnPanel;
	private JButton btnReset;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JButton btn8;
	private JButton btn9;
	protected JButton buttons[] = new JButton[9];
	protected static int btn1Cnt = 0;
	protected static int btn2Cnt = 0;
	protected static int btn3Cnt = 0;
	protected static int btn4Cnt = 0;
	protected static int btn5Cnt = 0;
	protected static int btn6Cnt = 0;
	protected static int btn7Cnt = 0;
	protected static int btn8Cnt = 0;
	protected static int btn9Cnt = 0;
	private static JLabel lbl1;
	private static JLabel lbl2;

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		initialize();
	}

	protected void initialize()
	{
		initializeWindow();
		initializeButtons();
		initializeButtonsArray();
		initializeButtonsPanel();
	}

	protected void setLabel1(String txt)
	{
		lbl1.setText(txt);
	}

	protected void setLabel2(String txt)
	{
		lbl2.setText(txt);
	}

	private void initializeWindow()
	{
		setTitle("Tic-Tac-Toe");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(450, 500));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	protected void initializeButtonsPanel()
	{
		btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);

		lbl1 = new JLabel(Computer.leftLabel);
		btnPanel.add(lbl1);

		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Computer.clearTable();
				Computer.cntXwins = 0;
				Computer.cntOwins = 0;
				lbl1.setText("");
				lbl2.setText("");
			}
		});
		btnReset.setFocusPainted(false);
		btnPanel.add(btnReset);

		lbl2 = new JLabel(Computer.rightLabel);
		btnPanel.add(lbl2);
	}

	protected void initializeButtonsArray()
	{
		buttons[0] = btn1;
		buttons[1] = btn2;
		buttons[2] = btn3;
		buttons[3] = btn4;
		buttons[4] = btn5;
		buttons[5] = btn6;
		buttons[6] = btn7;
		buttons[7] = btn8;
		buttons[8] = btn9;
	}

	protected void initializeButtons()
	{
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 3));

		{
			btn1 = new JButton();
			btn1.setName("1");
			btn1.setAlignmentY(0.0f);
			btn1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn1Cnt < 1)
					{
						btn1Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn1);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn1);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn1.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn1);

			btn2 = new JButton();
			btn2.setName("2");
			btn2.setAlignmentY(0.0f);
			btn2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn2Cnt < 1)
					{
						btn2Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn2);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn2);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn2.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn2);

			btn3 = new JButton();
			btn3.setName("3");
			btn3.setAlignmentY(0.0f);
			btn3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn3Cnt < 1)
					{
						btn3Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn3);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn3);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn3.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn3);

			btn4 = new JButton();
			btn4.setName("4");
			btn4.setAlignmentY(0.0f);
			btn4.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn4Cnt < 1)
					{
						btn4Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn4);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn4);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn4.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn4);

			btn5 = new JButton();
			btn5.setName("5");
			btn5.setAlignmentY(0.0f);
			btn5.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn5Cnt < 1)
					{
						btn5Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn5);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn5);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn5.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn5);

			btn6 = new JButton();
			btn6.setName("6");
			btn6.setAlignmentY(0.0f);
			btn6.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn6Cnt < 1)
					{
						btn6Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn6);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn6);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn6.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn6);

			btn7 = new JButton();
			btn7.setName("7");
			btn7.setAlignmentY(0.0f);
			btn7.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn7Cnt < 1)
					{
						btn7Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn7);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn7);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn7.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn7);

			btn8 = new JButton();
			btn8.setName("8");
			btn8.setAlignmentY(0.0f);
			btn8.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn8Cnt < 1)
					{
						btn8Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn8);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn8);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn8.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn8);

			btn9 = new JButton();
			btn9.setName("9");
			btn9.setAlignmentY(0.0f);
			btn9.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btn9Cnt < 1)
					{
						btn9Cnt++;
						if(Computer.notComputer)
						{
							Computer.set_X_icon(btn9);
							Computer.isWinner();
							Computer.computersTurn();
						}
						// else
						// {
						// Computer.set_O_icon(btn9);
						// Computer.isWinner();
						// }
					}
				}
			});
			btn9.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.add(btn9);
		}
	}
}
