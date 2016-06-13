using System;
using System.Collections.Generic;
using System.Linq;
using System.Media;
using System.Speech.Synthesis;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Yahtzee;

namespace WpfApplication1
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        #region variables
        enum Category
        {
            ONE,
            TWO,
            THREE,
            FOUR,
            FIVE,
            SIX,
            THREE_OF_A_KIND,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            SMALL_STRAIGHT,
            LARGE_STRAIGHT,
            CHANCE,
            YAHTZEE
        };
        private string yahtzeeOf = "";
        private int computerEndGameCounter = 0 ;
        private int sum = 0;
        private int bonus = 0;
        private int total = 0;
        private int computer_sum = 0;
        private int computer_bonus = 0;
        private int computer_total = 0;
        private int clickCounter = 0;
        private int computerCounter = 0;
        public static bool isPlayer = false;

        private bool isSelectedRadioButton = false;

        private readonly int ONE = 1;
        private readonly int TWO = 2;
        private readonly int THREE = 3;
        private readonly int FOUR = 4;
        private readonly int FIVE = 5;
        private readonly int SIX = 6;

      public  static Dictionary<Rectangle, bool> list = new Dictionary<Rectangle, bool>();
        public static Dictionary<RadioButton, bool> radioBtns = new Dictionary<RadioButton, bool>();
       public static Dictionary<Rectangle, int> dicesDictionary = new Dictionary<Rectangle, int>();
        private Random rand;
        SpeechSynthesizer speech = new SpeechSynthesizer();
        #endregion

        public MainWindow()
        {
            InitializeComponent();
            initializeMainVariable();
        }

        private async void initializeMainVariable()
        {
            list.Add(dice1, true);
            list.Add(dice2, true);
            list.Add(dice3, true);
            list.Add(dice4, true);
            list.Add(dice5, true);

            radioBtns.Add(scoreBox1, true);
            radioBtns.Add(scoreBox2, true);
            radioBtns.Add(scoreBox3, true);
            radioBtns.Add(scoreBox4, true);
            radioBtns.Add(scoreBox5, true);
            radioBtns.Add(scoreBox6, true);
            radioBtns.Add(scoreBoxThreeOfAKind, true);
            radioBtns.Add(scoreBoxFourOfAKind, true);
            radioBtns.Add(scoreBoxFullHouse, true);
            radioBtns.Add(scoreBoxSmallStreight, true);
            radioBtns.Add(scoreBoxLargeStreight, true);
            radioBtns.Add(scoreBoxChance, true);
            radioBtns.Add(scoreBoxYahtzee, true);

            dicesDictionary.Add(dice1, 0);
            dicesDictionary.Add(dice2, 0);
            dicesDictionary.Add(dice3, 0);
            dicesDictionary.Add(dice4, 0);
            dicesDictionary.Add(dice5, 0);
            
            rand = new Random();

            await Task.Delay(100);
            speech.SpeakAsync("Hello! Please enter your name and press Enter!");
        }

        #region controlGUI
        private void rollDice()
        {
            int random = 0;

            for (int i = 0; i < dicesDictionary.Count; i++)
            {
                random = rand.Next(1, 7);
                
                if (list.ElementAt(i).Value)
                {
                    dicesDictionary[dicesDictionary.ElementAt(i).Key] = random;
                    setDiceIcon(random, i);
                    radioBtnShowOptionToPlayer();
                }

            }
        }

        private void computerRollDice()
        {
            int random = 0;

            for (int i = 0; i < dicesDictionary.Count; i++)
            {
                random = rand.Next(1, 7);

                if (list.ElementAt(i).Value)
                {
                    dicesDictionary[dicesDictionary.ElementAt(i).Key] = random;
                     setDiceIcon(random, i);
                }
            }
        }

        private void setDiceIcon(int random, int i)
        {
            list.ElementAt(i).Key.Fill = new ImageBrush(new BitmapImage(new Uri("../../Resources/dice" + random.ToString() + ".png", UriKind.Relative)));
        }

        private async void swap_backgrounds()
        {
            if (isPlayer)
            {
                btnRoll.Visibility = Visibility.Hidden;
                slot.Source = new BitmapImage(new Uri(@"Resources/rolled_slot.png", UriKind.Relative));
                await Task.Delay(1000);
                slot.Source = new BitmapImage(new Uri(@"Resources/unrolled_slot.png", UriKind.Relative));
                btnRoll.Visibility = Visibility.Visible;
            }
            else
            {
                btnRoll.Visibility = Visibility.Hidden;
                slot.Source = new BitmapImage(new Uri(@"Resources/rolled_slot.png", UriKind.Relative));
                await Task.Delay(1000);
                slot.Source = new BitmapImage(new Uri(@"Resources/unrolled_slot.png", UriKind.Relative));
            }
        }

      
        private async void btnRoll_MouseUp(object sender, MouseButtonEventArgs e)
        {
          
            bool checkRadioButtonsVisiblity = false;
            if (clickCounter < 3 && !isSelectedRadioButton)
            {
              
                isPlayer = true;
                turn.Text = "It's your turn, "+textBox.Text.ToString();

                swap_backgrounds();
                rollDice();
                ++clickCounter;
                counter.Text = clickCounter.ToString();

                if (clickCounter == 3)
                {
                    for (int i = 0; i < radioBtns.Count; i++)
                    {
                        if (radioBtns.ElementAt(i).Key.Visibility == Visibility.Visible)
                        {
                            checkRadioButtonsVisiblity = true;
                            break;
                        }
                    }

                    if (!checkRadioButtonsVisiblity)
                    {
                        await Task.Delay(2500);
                        showAllRadioButtons();
                        await Task.Delay(2500);
                        btnRoll.Visibility = Visibility.Hidden;
                        clickCounter = 0;
                        counter.Text = "";
                        isPlayer = false;
                        isSelectedRadioButton = true;
                    }
                }

            }
        }

        private void showAllRadioButtons()
        {
            for(int i=0; i<radioBtns.Count; i++)
            {
                if(radioBtns.ElementAt(i).Value == true)
                {
                    radioBtns.ElementAt(i).Key.Visibility = Visibility.Visible;
                }
            }
        }

        private async void computerRoll()
        {
            if (computerCounter < 3)
            {
               
                await Task.Delay(1000);
                computerCounter++;
                counter.Text = computerCounter.ToString();

                swap_backgrounds();
                computerRollDice();
             
                computerGoTo(selectCategory());

            }
        }
        private void prepareSlotForRoll()
        {
            for(int i=0; i<list.Count; i++)
            {
                list.ElementAt(i).Key.Fill = new ImageBrush(new BitmapImage(new Uri("../../Resources/dice0.png", UriKind.Relative)));
            }

            list[dice1] = true;
            dice1.Opacity = 1.0;
            list[dice2] = true;
            dice2.Opacity = 1.0;
            list[dice3] = true;
            dice3.Opacity = 1.0;
            list[dice4] = true;
            dice4.Opacity = 1.0;
            list[dice5] = true;
            dice5.Opacity = 1.0;

            GC.Collect(); //Garbage Collector ->> Collect
        }

        #endregion

        #region computer logic
        private async void computerPlay()
        {
            isPlayer = false;
            if (computerCounter <= 3)
            {
                turn.Text = "Computer's turn";
                prepareSlotForRoll();
                await Task.Delay(2000); //for Computer
                computerRoll();
            }
        }

        private string selectCategory()
        {
            if (isYAHTZEE())
            {
                if ((computer_yahtzee_scoreText.Text.Equals("")))
                {
                    return Category.YAHTZEE.ToString();
                }
                else if (computer_four_of_a_kind_scoreText.Text.Equals(""))
                {
                    return Category.FOUR_OF_A_KIND.ToString();
                }
                else if (computer_three_of_a_kind_scoreText.Text.Equals(""))
                {
                    return Category.THREE_OF_A_KIND.ToString();
                }
                else if (yahtzeeOf.Equals("SIX"))
                {
                    return Category.SIX.ToString();
                }
                else if (yahtzeeOf.Equals("FIVE"))
                {
                    return Category.FIVE.ToString();
                }
                else if (yahtzeeOf.Equals("FOUR"))
                {
                    return Category.FOUR.ToString();
                }
                else if (yahtzeeOf.Equals("THREE"))
                {
                    return Category.THREE.ToString();
                }
                else if (yahtzeeOf.Equals("TWO"))
                {
                    return Category.TWO.ToString();
                }
                else if (yahtzeeOf.Equals("ONE"))
                {
                    return Category.ONE.ToString();
                }
                else if (computer_chance_scoreText.Text.Equals(""))
                {
                    return Category.CHANCE.ToString();
                }
                else
                {
                    selectZero();
                    calculateBonus(100);
                }
                
            }
            else if (isFULL_House() && computer_full_house_scoreText.Text.Equals(""))
            {
                return Category.FULL_HOUSE.ToString();
            }
            else if (isLARGE_Straight() && computer_large_straight_scoreText.Text.Equals(""))
            {
                return Category.LARGE_STRAIGHT.ToString();
            }
            else if (isSMALL_Straight() && (computer_small_straight_scoreText.Text.Equals("") || computer_large_straight_scoreText.Text.Equals("")))
            {
                return Category.SMALL_STRAIGHT.ToString();
            }                      
            else if (isFOUR_Of_A_Kind() && computer_four_of_a_kind_scoreText.Text.Equals(""))
            {
                return Category.FOUR_OF_A_KIND.ToString();
            }
            else if (isTHREE_Of_A_Kind() && computer_three_of_a_kind_scoreText.Text.Equals(""))
            {
                return Category.THREE_OF_A_KIND.ToString();
            }
            GC.Collect();
            return "GO TO DEFAULT CASE ;]";
        }

        private string selectCategory123456()
        {
         
            if (is2x2() && computer_full_house_scoreText.Text.Equals("") && computerCounter < 3)
            {
                return "2X2";
            }
            else if ((dicesDictionary.Where(n => n.Value == SIX).Count() >= 4 && (computer_six_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.SIX.ToString();
            }
            else if ((dicesDictionary.Where(n => n.Value == FIVE).Count() >= 4 && (computer_five_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.FIVE.ToString();
            }
            else if ((dicesDictionary.Where(n => n.Value == FOUR).Count() >= 4 && (computer_four_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.FOUR.ToString();
            }
            else if ((dicesDictionary.Where(n => n.Value == THREE).Count() >= 4 && (computer_three_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.THREE.ToString();
            }
            else if ((dicesDictionary.Where(n => n.Value == TWO).Count() >= 4 && (computer_two_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.TWO.ToString();
            }
            else if ((dicesDictionary.Where(n => n.Value == ONE).Count() >= 4 && (computer_one_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
            {
                return Category.ONE.ToString();
            }
            else
            {
                if ((dicesDictionary.Where(n => n.Value == SIX).Count() == 3 && (computer_six_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals("") || computer_yahtzee_scoreText.Text.Equals(""))))
                {
                    return Category.SIX.ToString();
                }
                else if ((dicesDictionary.Where(n => n.Value == FIVE).Count() == 3 && (computer_five_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals(""))))
                {
                    return Category.FIVE.ToString();
                }
                else if ((dicesDictionary.Where(n => n.Value == FOUR).Count() == 3 && (computer_four_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals(""))))
                {
                    return Category.FOUR.ToString();
                }
                else if ((dicesDictionary.Where(n => n.Value == THREE).Count() == 3 && (computer_three_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals(""))))
                {
                    return Category.THREE.ToString();
                }
                else if ((dicesDictionary.Where(n => n.Value == TWO).Count() == 3 && (computer_two_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals(""))))
                {
                    return Category.TWO.ToString();
                }
                else if ((dicesDictionary.Where(n => n.Value == ONE).Count() == 3 && (computer_one_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals("") || computer_four_of_a_kind_scoreText.Text.Equals(""))))
                {
                    return Category.ONE.ToString();
                }
                else
                {
                    if ((dicesDictionary.Where(n => n.Value == SIX).Count() == 2 && (computer_six_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.SIX.ToString();
                    }
                    else if ((dicesDictionary.Where(n => n.Value == FIVE).Count() == 2 && (computer_five_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.FIVE.ToString();
                    }
                    else if ((dicesDictionary.Where(n => n.Value == FOUR).Count() == 2 && (computer_four_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.FOUR.ToString();
                    }
                    else if ((dicesDictionary.Where(n => n.Value == THREE).Count() == 2 && (computer_three_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.THREE.ToString();
                    }
                    else if ((dicesDictionary.Where(n => n.Value == TWO).Count() == 2 && (computer_two_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.TWO.ToString();
                    }
                    else if ((dicesDictionary.Where(n => n.Value == ONE).Count() == 2 && (computer_one_scoreText.Text.Equals("") || computer_three_of_a_kind_scoreText.Text.Equals(""))))
                    {
                        return Category.ONE.ToString();
                    }
                    else
                    {
                        if ((dicesDictionary.Where(n => n.Value == SIX).Count() == 1 && computer_six_scoreText.Text.Equals("")))
                        {
                            return Category.SIX.ToString();
                        }
                        else if ((dicesDictionary.Where(n => n.Value == FIVE).Count() == 1 && computer_five_scoreText.Text.Equals("")))
                        {
                            return Category.FIVE.ToString();
                        }
                        else if ((dicesDictionary.Where(n => n.Value == FOUR).Count() == 1 && computer_four_scoreText.Text.Equals("")))
                        {
                            return Category.FOUR.ToString();
                        }
                        else if ((dicesDictionary.Where(n => n.Value == THREE).Count() == 1 && computer_three_scoreText.Text.Equals("")))
                        {
                            return Category.THREE.ToString();
                        }
                        else if ((dicesDictionary.Where(n => n.Value == TWO).Count() == 1 && computer_two_scoreText.Text.Equals("")))
                        {
                            return Category.TWO.ToString();
                        }
                        else if ((dicesDictionary.Where(n => n.Value == ONE).Count() == 1 && computer_one_scoreText.Text.Equals("")))
                        {
                            return Category.ONE.ToString();
                        }
                        else if (computer_chance_scoreText.Text.Equals(""))
                        {
                            return Category.CHANCE.ToString();
                        }
                    }
                }
            }
            return "GO TO DEFAULT CASE ;]";
        }

  

        private async void computerGoTo(string category)
        {
            switch (category.ToString().ToUpper())
            {
                case "YAHTZEE":
                    //set score
                    await Task.Delay(2000);
                    computer_yahtzee_scoreText.Text = YAHTZEE_Score().ToString();
                    computer_CalculateTOTAL(YAHTZEE_Score());
                    afterComputerSelectScore_PrepareForPlayer();
                    break;
                case "FULL_HOUSE":
                    //set score
                    await Task.Delay(2000);
                    computer_full_house_scoreText.Text = FULL_House_Score().ToString();
                    computer_CalculateTOTAL(FULL_House_Score());
                    afterComputerSelectScore_PrepareForPlayer();
                    break;
                case "LARGE_STRAIGHT":
                    //set score
                    await Task.Delay(2000);
                    computer_large_straight_scoreText.Text = LARGE_Straight_Score().ToString();
                    computer_CalculateTOTAL(LARGE_Straight_Score());
                    afterComputerSelectScore_PrepareForPlayer();
                    break;
                case "SMALL_STRAIGHT":
                    if (computerCounter < 3)
                    {
                        //select small straight and roll again
                        await Task.Delay(2000);
                        position_Of_SMALL_Straight();
                        computerRoll();                       
                    }
                    else if (computerCounter == 3 && !computer_small_straight_scoreText.Text.Equals(""))
                    {
                        computerGoTo123456(selectCategory123456());
                    }
                    else
                    {
                        //set score
                        await Task.Delay(2000);
                        computer_small_straight_scoreText.Text = SMALL_Straight_Score().ToString();
                        computer_CalculateTOTAL(SMALL_Straight_Score());
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    break;
                case "FOUR_OF_A_KIND":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_four_of_a_kind_scoreText.Text.Equals(""))
                    {
                        // check do you have yahtzee
                        await Task.Delay(2000);
                        computer_CalculateBonus(100);
                        computer_four_of_a_kind_scoreText.Text = FOUR_Of_A_Kind_Score().ToString();
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        if (computerCounter < 3)
                        {
                            // select four of a kind and roll again
                            await Task.Delay(2000);
                            position_Of_FOUR_Of_A_Kind();
                            computerRoll();
                        }
                        else if(computerCounter == 3 && !computer_four_of_a_kind_scoreText.Text.Equals(""))
                        {
                            computerGoTo123456(selectCategory123456());
                        }
                        else 
                        {
                            // set score
                            await Task.Delay(2000);
                            computer_four_of_a_kind_scoreText.Text = FOUR_Of_A_Kind_Score().ToString();
                            computer_CalculateTOTAL(FOUR_Of_A_Kind_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        
                    }
                    break;
                case "THREE_OF_A_KIND":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_three_of_a_kind_scoreText.Text.Equals(""))
                    {
                        // check do you have yahtzee
                        await Task.Delay(2000);
                        computer_three_of_a_kind_scoreText.Text = THREE_Of_A_Kind_Score().ToString();
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        if (computerCounter < 3)
                        {

                            //select dices three of a kind
                            // and roll again
                            await Task.Delay(2000);
                            position_Of_THREE_Of_A_Kind();
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_three_of_a_kind_scoreText.Text.Equals(""))
                        {
                            computerGoTo123456(selectCategory123456());
                        }
                        else 
                        {
                            // set score
                            await Task.Delay(2000);
                            computer_three_of_a_kind_scoreText.Text = THREE_Of_A_Kind_Score().ToString();
                            computer_CalculateTOTAL(THREE_Of_A_Kind_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }                     
                    }
                    break;
                default:
                    computerGoTo123456(selectCategory123456());
                    break;
            }
        }

        private async void computerGoTo123456(string category)
        {
            switch(category)
            {
                case "CHANCE":
                    //set score

                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals(""))
                    {
                        computer_CalculateBonus(100);
                    }
                    else
                    {
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            computerRoll();
                        }
                        else //if (computerCounter == 3)
                        {

                            await Task.Delay(2000);
                            computer_chance_scoreText.Text = CHANCE_Score().ToString();
                            computer_CalculateTOTAL(CHANCE_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "2X2":
                    if (computerCounter < 3 && computer_full_house_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        position_Of_2x2();
                        computerRoll();
                    }
                    else
                    {
                        computerGoTo(selectCategory());
                    }
                    break;
                case "ONE":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_one_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_one_scoreText.Text = one_Score().ToString();
                        computer_CalculateSUM(one_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(ONE);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_one_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else 
                        {
                            await Task.Delay(2000);
                            computer_one_scoreText.Text = one_Score().ToString();
                            computer_CalculateSUM(one_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "TWO":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_two_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_two_scoreText.Text = two_Score().ToString();
                        computer_CalculateSUM(two_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(TWO);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_two_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else 
                        {
                            await Task.Delay(2000);
                            computer_two_scoreText.Text = two_Score().ToString();
                            computer_CalculateSUM(two_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "THREE":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_three_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_three_scoreText.Text = three_Score().ToString();
                        computer_CalculateSUM(three_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(THREE);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_three_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else 
                        {
                            await Task.Delay(2000);
                            computer_three_scoreText.Text = three_Score().ToString();
                            computer_CalculateSUM(three_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "FOUR":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_four_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_four_scoreText.Text = four_Score().ToString();
                        computer_CalculateSUM(four_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(FOUR);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_four_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else
                        {
                            await Task.Delay(2000);
                            computer_four_scoreText.Text = four_Score().ToString();
                            computer_CalculateSUM(four_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "FIVE":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_five_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_five_scoreText.Text = five_Score().ToString();
                        computer_CalculateSUM(five_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(FIVE);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_five_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else 
                        {
                            await Task.Delay(2000);
                            computer_five_scoreText.Text = five_Score().ToString();
                            computer_CalculateSUM(five_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                case "SIX":
                    if (isYAHTZEE() && !computer_yahtzee_scoreText.Text.Equals("") && computer_six_scoreText.Text.Equals(""))
                    {
                        await Task.Delay(2000);
                        computer_six_scoreText.Text = six_Score().ToString();
                        computer_CalculateSUM(six_Score());
                        computer_CalculateBonus(100);
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    else
                    {
                        //roll again
                        if (computerCounter < 3)
                        {
                            await Task.Delay(2000);
                            iterateAndOpacitySet(SIX);
                            computerRoll();
                        }
                        else if (computerCounter == 3 && !computer_six_scoreText.Text.Equals(""))
                        {
                            computerGoTo(selectCategory());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                        else
                        {
                            await Task.Delay(2000);
                            computer_six_scoreText.Text = six_Score().ToString();
                            computer_CalculateSUM(six_Score());
                            afterComputerSelectScore_PrepareForPlayer();
                        }
                    }
                    break;
                default:
                    if (computerCounter < 3)
                    {
                        {
                            // roll again
                            await Task.Delay(2000);
                            computerRoll();
                            computerGoTo(selectCategory());
                        }
                    }
                    else if (computerCounter == 3)
                    {
                        // player's turn
                        await Task.Delay(2000);
                        if ((isYAHTZEE() && computer_yahtzee_scoreText.Text.Equals("")) || (isCHANCE() && computer_chance_scoreText.Text.Equals("")) || (isLARGE_Straight() && computer_large_straight_scoreText.Text.Equals("")) || (isSMALL_Straight() && computer_small_straight_scoreText.Text.Equals("")) || (isFULL_House() && computer_full_house_scoreText.Text.Equals("")) || (isFOUR_Of_A_Kind() && computer_four_of_a_kind_scoreText.Text.Equals("")) || (isTHREE_Of_A_Kind() && computer_three_of_a_kind_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == ONE).Count() >= 1 && computer_one_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == TWO).Count() >= 1 && computer_two_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == THREE).Count() >= 1 && computer_three_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == FOUR).Count() >= 1 && computer_four_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == FIVE).Count() >= 1 && computer_five_scoreText.Text.Equals("")) || (dicesDictionary.Where(n => n.Value == SIX).Count() >= 1 && computer_six_scoreText.Text.Equals(""))) // DA OPRAVQ OSHTE MALKO PROVERKA I ZA DALI POLETO E SVOBODNO I DA RAZMENQ MESTATA NA IF-ELSE
                        {
                            computerGoTo(selectCategory());
                        }
                        else
                        {
                            selectZero();
                        }
                        afterComputerSelectScore_PrepareForPlayer();
                    }
                    break;
            }
        }

        #endregion

        #region options

        private async void afterComputerSelectScore_PrepareForPlayer()
        {
            await Task.Delay(2000);
            computerCounter = 0;
            counter.Text = "";
            prepareSlotForRoll();
            isSelectedRadioButton = false;
            btnRoll.Visibility = Visibility.Visible;
            isPlayer = true;
            turn.Text = "It's your turn, " + textBox.Text.ToString();
            computerEndGameCounter++;
            isGameEnd();
        }

        private void selectZero()
        {
            if(computer_yahtzee_scoreText.Text.Equals(""))
            {
                computer_yahtzee_scoreText.Text = YAHTZEE_Score().ToString();
            }
            else if(computer_large_straight_scoreText.Text.Equals(""))
            {
                computer_large_straight_scoreText.Text = LARGE_Straight_Score().ToString();
            }
            else if (computer_four_of_a_kind_scoreText.Text.Equals(""))
            {
                computer_four_of_a_kind_scoreText.Text = FOUR_Of_A_Kind_Score().ToString();
            }
            else if (computer_small_straight_scoreText.Text.Equals(""))
            {
                computer_small_straight_scoreText.Text = SMALL_Straight_Score().ToString();
            }
            else if(computer_three_of_a_kind_scoreText.Text.Equals(""))
            {
                computer_three_of_a_kind_scoreText.Text = THREE_Of_A_Kind_Score().ToString();
            }           
            else if (computer_one_scoreText.Text.Equals(""))
            {
                computer_one_scoreText.Text = one_Score().ToString();
            }
            else if (computer_two_scoreText.Text.Equals(""))
            {
                computer_two_scoreText.Text = two_Score().ToString();
            }
            else if (computer_three_scoreText.Text.Equals(""))
            {
                computer_three_scoreText.Text = three_Score().ToString();
            }
            else if (computer_four_scoreText.Text.Equals(""))
            {
                computer_four_scoreText.Text = four_Score().ToString();
            }
            else if (computer_five_scoreText.Text.Equals(""))
            {
                computer_five_scoreText.Text = five_Score().ToString();
            }
            else if (computer_six_scoreText.Text.Equals(""))
            {
                computer_six_scoreText.Text = six_Score().ToString();
            }
            else if (computer_chance_scoreText.Text.Equals(""))
            {
                computer_chance_scoreText.Text = CHANCE_Score().ToString();
            }
        }

        private void isGameEnd()
        {
            if (computerEndGameCounter == 13)
            {
                //calculate TOTAL
                computer_CalculateTOTAL(computer_bonus + computer_sum);
                computer_total_scoreText.Text = computer_total.ToString();

                calculateTOTAL(bonus + sum);
                total_scoreText.Text = total.ToString();

                if(total > computer_total)
                {
                    SystemSounds.Hand.Play();
                    turn.Text = textBox.Text.ToString() + ", you are Winner!";
                }
                else
                {
                    SystemSounds.Hand.Play();
                    turn.Text = "Computer win!";
                }
                
                btnRoll.Visibility = Visibility.Hidden;
                prepareSlotForRoll();
                counter.Text = "";
            }
        }

        private void radioBtnShowOptionToPlayer()
        {
            if (radioBtns.ContainsValue(true))
            {
                radioBtns.ElementAt(0).Key.Visibility = (dicesDictionary.ContainsValue(ONE) && radioBtns[scoreBox1] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(1).Key.Visibility = (dicesDictionary.ContainsValue(TWO) && radioBtns[scoreBox2] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(2).Key.Visibility = (dicesDictionary.ContainsValue(THREE) && radioBtns[scoreBox3] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(3).Key.Visibility = (dicesDictionary.ContainsValue(FOUR) && radioBtns[scoreBox4] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(4).Key.Visibility = (dicesDictionary.ContainsValue(FIVE) && radioBtns[scoreBox5] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(5).Key.Visibility = (dicesDictionary.ContainsValue(SIX) && radioBtns[scoreBox6] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(6).Key.Visibility = (isTHREE_Of_A_Kind() && radioBtns[scoreBoxThreeOfAKind] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(7).Key.Visibility = (isFOUR_Of_A_Kind() && radioBtns[scoreBoxFourOfAKind] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(8).Key.Visibility = (isFULL_House() && radioBtns[scoreBoxFullHouse] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(9).Key.Visibility = (isSMALL_Straight() && radioBtns[scoreBoxSmallStreight] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(10).Key.Visibility = (isLARGE_Straight() && radioBtns[scoreBoxLargeStreight] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(11).Key.Visibility = (isCHANCE() && radioBtns[scoreBoxChance] == true) ? Visibility.Visible : Visibility.Hidden;
                radioBtns.ElementAt(12).Key.Visibility = (isYAHTZEE() && radioBtns[scoreBoxYahtzee] == true) ? Visibility.Visible : Visibility.Hidden;

            }
        }
     
        private async void dicesOpacityVisible()
        {
            await Task.Delay(500);

            for (int i = 0; i < dicesDictionary.Count; i++)
            {
                list.ElementAt(i).Key.Opacity = 1.0;
                list[list.ElementAt(i).Key] = true;
            }
            await Task.Delay(2000);
        }

        private async void iterateAndOpacitySet(int findNum)
        {
            await Task.Delay(500);

            for (int i = 0; i < dicesDictionary.Count; i++)
            {
                if (dicesDictionary.ElementAt(i).Value == findNum)
                {
                    list.ElementAt(i).Key.Opacity = 0.5;
                    list[list.ElementAt(i).Key] = false;
                }
            }
            await Task.Delay(2000);
        }

        private async void iterateAndOpacitySetForSmallStraight(int findNum)
        {
            dicesOpacityVisible();

            await Task.Delay(500);

            for (int i = 0; i < dicesDictionary.Count; i++)
            {
                
                if (dicesDictionary.ElementAt(i).Value == findNum)
                { 
                    if (dicesDictionary.Where(n => n.Value == findNum).Count() == 1)
                    {                        
                        list.ElementAt(i).Key.Opacity = 0.5;
                        list[list.ElementAt(i).Key] = false;
                    }
                    else if(dicesDictionary.Where(n => n.Value == findNum).Count() > 1)
                    {
                        list.ElementAt(i).Key.Opacity = 0.5;
                        list[list.ElementAt(i).Key] = false;
                        break;
                    }
                }
               
            }
            await Task.Delay(2000);
        }

        #endregion

        #region check methods
        private void position_Of_2x2()
        {
            var one = dicesDictionary.Where(n => n.Value == ONE);
            var two = dicesDictionary.Where(n => n.Value == TWO);
            var three = dicesDictionary.Where(n => n.Value == THREE);
            var four = dicesDictionary.Where(n => n.Value == FOUR);
            var five = dicesDictionary.Where(n => n.Value == FIVE);
            var six = dicesDictionary.Where(n => n.Value == SIX);

            if (one.Count() == 2 && two.Count() == 2)
            {
                iterateAndOpacitySet(ONE);
                iterateAndOpacitySet(TWO);
            }
            else if (one.Count() == 2 && three.Count() == 2)
            {
                iterateAndOpacitySet(ONE);
                iterateAndOpacitySet(THREE);
            }
            else if (one.Count() == 2 && four.Count() == 2)
            {
                iterateAndOpacitySet(ONE);
                iterateAndOpacitySet(FOUR);
            }
            else if (one.Count() == 2 && five.Count() == 2)
            {
                iterateAndOpacitySet(ONE);
                iterateAndOpacitySet(FIVE);
            }
            else if (one.Count() == 2 && six.Count() == 2)
            {
                iterateAndOpacitySet(ONE);
                iterateAndOpacitySet(SIX);
            }
            else if (two.Count() == 2 && three.Count() == 2)
            {
                iterateAndOpacitySet(TWO);
                iterateAndOpacitySet(THREE);
            }
            else if (two.Count() == 2 && four.Count() == 2)
            {
                iterateAndOpacitySet(TWO);
                iterateAndOpacitySet(FOUR);
            }
            else if (two.Count() == 2 && five.Count() == 2)
            {
                iterateAndOpacitySet(TWO);
                iterateAndOpacitySet(FIVE);
            }
            else if (two.Count() == 2 && six.Count() == 2)
            {
                iterateAndOpacitySet(TWO);
                iterateAndOpacitySet(SIX);
            }
            else if (three.Count() == 2 && four.Count() == 2)
            {
                iterateAndOpacitySet(THREE);
                iterateAndOpacitySet(FOUR);
            }
            else if (three.Count() == 2 && five.Count() == 2)
            {
                iterateAndOpacitySet(THREE);
                iterateAndOpacitySet(FIVE);
            }
            else if (three.Count() == 2 && six.Count() == 2)
            {
                iterateAndOpacitySet(THREE);
                iterateAndOpacitySet(SIX);
            }
            else if (four.Count() == 2 && five.Count() == 2)
            {
                iterateAndOpacitySet(FOUR);
                iterateAndOpacitySet(FIVE);
            }
            else if (four.Count() == 2 && six.Count() == 2)
            {
                iterateAndOpacitySet(FOUR);
                iterateAndOpacitySet(SIX);
            }
            else if (five.Count() == 2 && six.Count() == 2)
            {
                iterateAndOpacitySet(FIVE);
                iterateAndOpacitySet(SIX);
            }
        }

        private void position_Of_THREE_Of_A_Kind()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);
            

            if (one_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(ONE);
            }
            else if(two_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(TWO);
            }
            else if(three_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(THREE);
            }
            else if(four_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(FOUR);
            }
            else if(five_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(FIVE);
            }
            else if(six_ThreOfAKind.Count() == 3)
            {
                iterateAndOpacitySet(SIX);
            }

        }

        private void position_Of_FOUR_Of_A_Kind()
        {
            var one_FourOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_FourOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_FourOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_FourOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_FourOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_FourOfAKind = dicesDictionary.Where(n => n.Value == SIX);
            
            if (one_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(ONE);
            }
            else if (two_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(TWO);
            }
            else if (three_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(THREE);
            }
            else if (four_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(FOUR);
            }
            else if (five_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(FIVE);
            }
            else if (six_FourOfAKind.Count() == 4)
            {
                iterateAndOpacitySet(SIX);
            }
        }

        private void position_Of_SMALL_Straight()
        {
            var one_SMALL_Straight = dicesDictionary.Where(n => n.Value == ONE);
            var two_SMALL_Straight = dicesDictionary.Where(n => n.Value == TWO);
            var three_SMALL_Straight = dicesDictionary.Where(n => n.Value == THREE);
            var four_SMALL_Straight = dicesDictionary.Where(n => n.Value == FOUR);
            var five_SMALL_Straight = dicesDictionary.Where(n => n.Value == FIVE);
            var six_SMALL_Straight = dicesDictionary.Where(n => n.Value == SIX);
            
            if (dicesDictionary.ContainsValue(1) && dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4))
            {
                iterateAndOpacitySetForSmallStraight(ONE);
                iterateAndOpacitySetForSmallStraight(TWO);
                iterateAndOpacitySetForSmallStraight(THREE);
                iterateAndOpacitySetForSmallStraight(FOUR);
            }
            else if (dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5))
            {
                iterateAndOpacitySetForSmallStraight(TWO);
                iterateAndOpacitySetForSmallStraight(THREE);
                iterateAndOpacitySetForSmallStraight(FOUR);
                iterateAndOpacitySetForSmallStraight(FIVE);
            }
            else if (dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5) && dicesDictionary.ContainsValue(6))
            {
                iterateAndOpacitySetForSmallStraight(THREE);
                iterateAndOpacitySetForSmallStraight(FOUR);
                iterateAndOpacitySetForSmallStraight(FIVE);
                iterateAndOpacitySetForSmallStraight(SIX);
            }
        }

       
        private bool isTHREE_Of_A_Kind()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);

            return (one_ThreOfAKind.Count() >= 3 || two_ThreOfAKind.Count() >= 3 || three_ThreOfAKind.Count() >= 3 || four_ThreOfAKind.Count() >= 3 || five_ThreOfAKind.Count() >= 3 || six_ThreOfAKind.Count() >= 3);
        }

        private bool isFOUR_Of_A_Kind()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);

            return (one_ThreOfAKind.Count() >= 4 || two_ThreOfAKind.Count() >= 4 || three_ThreOfAKind.Count() >= 4 || four_ThreOfAKind.Count() >= 4 || five_ThreOfAKind.Count() >= 4 || six_ThreOfAKind.Count() >= 4);
        }

        private bool isFULL_House()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);

            return ((one_ThreOfAKind.Count() == 3 || two_ThreOfAKind.Count() == 3 || three_ThreOfAKind.Count() == 3 || four_ThreOfAKind.Count() == 3 || five_ThreOfAKind.Count() == 3 || six_ThreOfAKind.Count() == 3) && (one_ThreOfAKind.Count() == 2 || two_ThreOfAKind.Count() == 2 || three_ThreOfAKind.Count() == 2 || four_ThreOfAKind.Count() == 2 || five_ThreOfAKind.Count() == 2 || six_ThreOfAKind.Count() == 2));
        }

        private bool isSMALL_Straight()
        {
            return (((dicesDictionary.ContainsValue(1) && dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4)) || (dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5)) || dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5) && dicesDictionary.ContainsValue(6)));
        }
        
        private bool isLARGE_Straight()
        {
            return ((dicesDictionary.ContainsValue(1) && dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5)) || (dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5) && dicesDictionary.ContainsValue(6)));
        }

        private bool isCHANCE()
        {
            return (radioBtns[scoreBoxChance] == true);
        }

        private bool isYAHTZEE()
        {
            var one_Yahtzee = dicesDictionary.Where(n => n.Value == ONE);
            var two_Yahtzee = dicesDictionary.Where(n => n.Value == TWO);
            var three_Yahtzee = dicesDictionary.Where(n => n.Value == THREE);
            var four_Yahtzee = dicesDictionary.Where(n => n.Value == FOUR);
            var five_Yahtzee = dicesDictionary.Where(n => n.Value == FIVE);
            var six_Yahtzee = dicesDictionary.Where(n => n.Value == SIX);

            if(one_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "ONE";
                return true;
            }
            else if(two_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "TWO";
                return true;
            }
            else if (three_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "THREE";
                return true;
            }
            else if (four_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "FOUR";
                return true;
            }
            else if(five_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "FIVE";
                return true;
            }
            else if (six_Yahtzee.Count() == 5)
            {
                yahtzeeOf = "SIX";
                return true;
            }
                return false;  
        }

        private bool is2x2()
        {
            var one = dicesDictionary.Where(n => n.Value == ONE);
            var two = dicesDictionary.Where(n => n.Value == TWO);
            var three = dicesDictionary.Where(n => n.Value == THREE);
            var four = dicesDictionary.Where(n => n.Value == FOUR);
            var five = dicesDictionary.Where(n => n.Value == FIVE);
            var six = dicesDictionary.Where(n => n.Value == SIX);

            if(one.Count()==2 && (two.Count()==2 || three.Count()==2 || four.Count()==2 || five.Count()==2 || six.Count()==2))
            {
                return true;
            }
            else if(two.Count() == 2 && (three.Count() == 2 || four.Count() == 2 || five.Count() == 2 || six.Count() == 2))
            {
                return true;
            }
            else if(three.Count() == 2 && (four.Count() == 2 || five.Count() == 2 || six.Count() == 2))
            {
                return true;
            }
            else if(four.Count() == 2 && (five.Count() == 2 || six.Count() == 2))
            {
                return true;
            }
            else if(five.Count() == 2 && six.Count() == 2)
            {
                return true;
            }
        
            return false;
        }

        private int one_Score()
        {
            var one = dicesDictionary.Where(n => n.Value == ONE);

            if (one.Count() > 0)
            {
                return one.Count() * ONE;
            }
                return 0;
        }

        private int two_Score()
        {
            var two = dicesDictionary.Where(n => n.Value == TWO);
            
            return (two.Count() >0) ? two.Count() * TWO : 0;
        }
        private int three_Score()
        {
            var three = dicesDictionary.Where(n => n.Value == THREE);
            
            return (three.Count() > 0) ? three.Count() * THREE : 0;
        }
        private int four_Score()
        {
            var four = dicesDictionary.Where(n => n.Value == FOUR);
            
            return (four.Count() > 0) ? four.Count() * FOUR : 0;
        }
        private int five_Score()
        {
            var five = dicesDictionary.Where(n => n.Value == FIVE);
            
            return (five.Count() > 0) ? five.Count() * FIVE : 0;
        }
        private int six_Score()
        {
            var six = dicesDictionary.Where(n => n.Value == SIX);
            
            return (six.Count() > 0) ? six.Count() * SIX : 0;
        }

        private int THREE_Of_A_Kind_Score()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);

            if (one_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (two_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (three_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (four_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (five_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (six_ThreOfAKind.Count() >= 3)
            {
                return dicesDictionary.Sum(x => x.Value);
            }

            return 0;
        }

        private int FOUR_Of_A_Kind_Score()
        {
            var one_FourOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_FourOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_FourOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_FourOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_FourOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_FourOfAKind = dicesDictionary.Where(n => n.Value == SIX);

            if (one_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (two_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (three_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (four_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (five_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
            else if (six_FourOfAKind.Count() >= 4)
            {
                return dicesDictionary.Sum(x => x.Value);
            }
       
            return 0;
      }

        private int FULL_House_Score()
        {
            var one_ThreOfAKind = dicesDictionary.Where(n => n.Value == ONE);
            var two_ThreOfAKind = dicesDictionary.Where(n => n.Value == TWO);
            var three_ThreOfAKind = dicesDictionary.Where(n => n.Value == THREE);
            var four_ThreOfAKind = dicesDictionary.Where(n => n.Value == FOUR);
            var five_ThreOfAKind = dicesDictionary.Where(n => n.Value == FIVE);
            var six_ThreOfAKind = dicesDictionary.Where(n => n.Value == SIX);
            
            return (one_ThreOfAKind.Count() == 3 || two_ThreOfAKind.Count() == 3 || three_ThreOfAKind.Count() == 3 || four_ThreOfAKind.Count() == 3 || five_ThreOfAKind.Count() == 3 || six_ThreOfAKind.Count() == 3) && (one_ThreOfAKind.Count() == 2 || two_ThreOfAKind.Count() == 2 || three_ThreOfAKind.Count() == 2 || four_ThreOfAKind.Count() == 2 || five_ThreOfAKind.Count() == 2 || six_ThreOfAKind.Count() == 2) ? 25 : 0;
        }

        private int SMALL_Straight_Score()
        {
            return (dicesDictionary.ContainsValue(1) && dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4)) || (dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5)) || dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5) && dicesDictionary.ContainsValue(6) ? 30 : 0;
        }



             private int LARGE_Straight_Score()
            {
                return (dicesDictionary.ContainsValue(1) && dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5)) || (dicesDictionary.ContainsValue(2) && dicesDictionary.ContainsValue(3) && dicesDictionary.ContainsValue(4) && dicesDictionary.ContainsValue(5) && dicesDictionary.ContainsValue(6)) ? 40 : 0;
            }

        private int CHANCE_Score()
        {
            int chance=0;
            for(int i=0; i<dicesDictionary.Count; i++)
            {
                chance += dicesDictionary.ElementAt(i).Value; 
            }

            return chance;
        }

        private int YAHTZEE_Score()
        {
            var one_Yahtzee = dicesDictionary.Where(n => n.Value == ONE);
            var two_Yahtzee = dicesDictionary.Where(n => n.Value == TWO);
            var three_Yahtzee = dicesDictionary.Where(n => n.Value == THREE);
            var four_Yahtzee = dicesDictionary.Where(n => n.Value == FOUR);
            var five_Yahtzee = dicesDictionary.Where(n => n.Value == FIVE);
            var six_Yahtzee = dicesDictionary.Where(n => n.Value == SIX);

            return (one_Yahtzee.Count() == 5 || two_Yahtzee.Count() == 5 || three_Yahtzee.Count() == 5 || four_Yahtzee.Count() == 5 || five_Yahtzee.Count() == 5 || six_Yahtzee.Count() == 5) ? 50 : 0;
        }

        private void calculateBonus(int n)
        {
            bonus += n;
            bonus_scoreText.Text = bonus.ToString();
        }

        private void computer_CalculateBonus(int n)
        {
            computer_bonus += n;
            computer_bonus_scoreText.Text = computer_bonus.ToString();
        }

        private void calculateSUM(int n)
        {
            sum += n;
            sum_scoreText.Text = sum.ToString(); 
        }

        private void computer_CalculateSUM(int n)
        {
            computer_sum += n;
            computer_sum_scoreText.Text = computer_sum.ToString();
        }

        private int calculateTOTAL(int n)
        {
            return total += n;     
        }

        private int computer_CalculateTOTAL(int n)
        {
           return  computer_total += n;
        }
              
        private void setDiceOpacity(Rectangle dice)
        {
            if (isPlayer)
            {
                if (list[dice])
                {
                    list[dice] = false;
                    dice.Opacity = 0.5;
                }
                else
                {
                    list[dice] = true;
                    dice.Opacity = 1.0;
                }
            }
        }
        #endregion

        #region buttons
        private void dice1_MouseUp(object sender, MouseButtonEventArgs e)
        {
            setDiceOpacity(dice1);
        }
  
        private void dice2_MouseUp(object sender, MouseButtonEventArgs e)
        {
            setDiceOpacity(dice2);
        }
 
        private void dice3_MouseUp(object sender, MouseButtonEventArgs e)
        {
            setDiceOpacity(dice3);
        }

        private void dice4_MouseUp(object sender, MouseButtonEventArgs e)
        {
            setDiceOpacity(dice4);
        }

        private void dice5_MouseUp(object sender, MouseButtonEventArgs e)
        {
            setDiceOpacity(dice5);
        }

        private void textBlock_MouseUp(object sender, MouseButtonEventArgs e)
        {
            textBlock.Visibility = Visibility.Hidden;
            textBox.Visibility = Visibility.Visible;
            Keyboard.Focus(textBox);
        }

        private async void textBox_KeyDown(object sender, KeyEventArgs e)
        {

            if (e.Key == Key.Enter && !textBox.Text.TrimStart(' ').Equals("") && textBox.Text.Count() <= 20)
            {
                btnRoll.Visibility = Visibility.Visible;
                dice1.Visibility = Visibility.Visible;
                dice2.Visibility = Visibility.Visible;
                dice3.Visibility = Visibility.Visible;
                dice4.Visibility = Visibility.Visible;
                dice5.Visibility = Visibility.Visible;
                textBox.Visibility = Visibility.Hidden;
                playerName.Text = "Hi " + textBox.Text.TrimStart(' ').TrimEnd(' ').ToString() + "!";
                turn.Text = "It's your turn, " + textBox.Text.ToString();
                turn.Visibility = Visibility.Visible;

                await Task.Delay(100);
                speech.SpeakAsync("It's your turn " + textBox.Text.ToString() + "! Please roll the slot");
            }                
        }
        
        private void hideRadioButtons()
        {
            for (int i = 0; i < radioBtns.Count; i++)
            {
                radioBtns.ElementAt(i).Key.Visibility = Visibility.Hidden;
            }
        }
        private void scoreBox1_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(0).Key.Visibility = Visibility.Hidden;
            one_scoreText.Text = one_Score().ToString();
            radioBtns[radioBtns.ElementAt(0).Key] = false;
            if(isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(one_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
           computerPlay();
        }

        private void scoreBox2_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(1).Key.Visibility = Visibility.Hidden;
            two_scoreText.Text = two_Score().ToString();
            radioBtns[radioBtns.ElementAt(1).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(two_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBox3_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(2).Key.Visibility = Visibility.Hidden;
            three_scoreText.Text = three_Score().ToString();
            radioBtns[radioBtns.ElementAt(2).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(three_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBox4_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(3).Key.Visibility = Visibility.Hidden;
            four_scoreText.Text = four_Score().ToString();
            radioBtns[radioBtns.ElementAt(3).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(four_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBox5_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(4).Key.Visibility = Visibility.Hidden;
            five_scoreText.Text = five_Score().ToString();
            radioBtns[radioBtns.ElementAt(4).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(five_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBox6_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(5).Key.Visibility = Visibility.Hidden;
           six_scoreText.Text = six_Score().ToString();
            radioBtns[radioBtns.ElementAt(5).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            calculateSUM(six_Score());
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxThreeOfAKind_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(6).Key.Visibility = Visibility.Hidden;
            three_of_a_kind_scoreText.Text = THREE_Of_A_Kind_Score().ToString();
            calculateTOTAL(THREE_Of_A_Kind_Score());
            radioBtns[radioBtns.ElementAt(6).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxFourOfAKind_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(7).Key.Visibility = Visibility.Hidden;
            four_of_a_kind_scoreText.Text = FOUR_Of_A_Kind_Score().ToString();
            calculateTOTAL(FOUR_Of_A_Kind_Score());
            radioBtns[radioBtns.ElementAt(7).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxFullHouse_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(8).Key.Visibility = Visibility.Hidden;
            full_house_scoreText.Text = FULL_House_Score().ToString();
            calculateTOTAL(FULL_House_Score());
            radioBtns[radioBtns.ElementAt(8).Key] = false;
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxSmallStreight_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(9).Key.Visibility = Visibility.Hidden;
            small_straight_scoreText.Text = SMALL_Straight_Score().ToString();
            calculateTOTAL(SMALL_Straight_Score());
            radioBtns[radioBtns.ElementAt(9).Key] = false;
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxLargeStreight_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(10).Key.Visibility = Visibility.Hidden;
           large_straight_scoreText.Text = LARGE_Straight_Score().ToString();
            calculateTOTAL(LARGE_Straight_Score());
            radioBtns[radioBtns.ElementAt(10).Key] = false;
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxChance_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(11).Key.Visibility = Visibility.Hidden;
           chance_scoreText.Text = CHANCE_Score().ToString();
            calculateTOTAL(CHANCE_Score());
            radioBtns[radioBtns.ElementAt(11).Key] = false;
            if (isYAHTZEE() && !yahtzee_scoreText.Text.Equals(""))
            {
                calculateBonus(100);
            }
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }

        private void scoreBoxYahtzee_Click(object sender, RoutedEventArgs e)
        {
            radioBtns.ElementAt(12).Key.Visibility = Visibility.Hidden;
            yahtzee_scoreText.Text =YAHTZEE_Score().ToString();
            calculateTOTAL(YAHTZEE_Score());
            radioBtns[radioBtns.ElementAt(12).Key] = false;
            isSelectedRadioButton = true;
            btnRoll.Visibility = Visibility.Hidden;
            clickCounter = 0;
            counter.Text = "";
            hideRadioButtons();
            computerPlay();
        }
        #endregion
    }
}
