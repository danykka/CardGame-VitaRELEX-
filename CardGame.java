import java.util.*;
	
public class CardGame {
	
	public static void main (String[] args) {		
		System.out.println("Greetings! Welcome to the Card Game, it's your turn to..");			
		Game game = new Game();
		game.setup();
		game.run();		
	}	
}
	
class Game {	
	boolean inGame = true;
	int step = 1;
	int level = 1;
	int attackCard, defenseCard;
	Gamer human = new Human();
	Gamer bot = new Bot();
	Scanner input = new Scanner(System.in);
		
	Game() {		
	}
		
	void setup() {
		human.setup();
		bot.setup();
	}
	
	void makeMoves(Gamer attacker, Gamer defender) {
			attackCard = attacker.attack();
			defenseCard = defender.defense();					
	}
		
	void showCards(Gamer attacker, Gamer defender){
		attacker.showCard(attackCard);
		defender.showCard(defenseCard);
	}
		
	void updateCards(Gamer attacker, Gamer defender){
		attacker.updateOpponentCards(defenseCard);
		defender.updateOpponentCards(attackCard);
		defender.updateDamage(attackCard - defenseCard);
	}
	
	void winner() {
		//System.out.println("********");
		System.out.println("Your damage: " + human.damage + " Bot's damage: " + bot.damage + " ");
		if (human.damage < bot.damage) {
			System.out.println("You win!");
		} else if (human.damage > bot.damage) {
			System.out.println("You lose!");
		} else System.out.println("Draw!");
	}
		
	void newLevel() {
		if (human.damage < bot.damage) {
			level++;
			bot.level = level;
		}
		System.out.println("Type '0' for exit or any other number to fight a level " + level + " bot");
		if (input.nextInt() == 0) inGame = false; 
		else {
			step = 1;
			setup();
		}
	}
		
	void run() {
		int queue;
		while (inGame) {
			queue = (int)(Math.random()*2);			
			while (step <= 12) {	
				if ((step + queue)%2 == 1) {
					System.out.println("Attack!");	
					makeMoves(human, bot);
					showCards(human, bot);
					System.out.print("Bot's damage: ");
					updateCards(human, bot);
					System.out.print("Your cards: ");	
					for (int i=0; i<12; i++) {
						if (human.cards[i]) {
							System.out.print(i);
							System.out.print(' ');
						}
						else System.out.print("- ");
					}
					System.out.println();
				} else {
					System.out.println("Defend!");
					makeMoves(bot, human);
					showCards(bot, human);
					System.out.print("Your damage: ");
					updateCards(bot, human);
					System.out.println("Your cards: ");
					for (int i=0; i<12; i++) {
						if (human.cards[i]) {
							System.out.print(i);
							System.out.print(' ');
						}
						else System.out.print("- ");
					}
					System.out.println();
				}
				step++;
			}
			winner();
			newLevel();
		}
		System.out.println("End!");		
	}
}		
			
	class Gamer {
		
		boolean[] cards;
		boolean[] opponentCards;
		int index;
		int damage;
		int level = 1;
		int little, middle, big;
		int score;
			
		Gamer() {
			cards = new boolean[12];
			opponentCards = new boolean[12];			
		}
			
		void setup() {
			for (int i=0; i<12; i++) {					
				cards[i] = true;
				opponentCards[i] = true;
			}
			score = 2;
			damage = 0;
		}
			
		int attack() {
			return 0;
		}
			
		int defense() {
			return 0;
		}
			
		void showCard(int card) {			
		}
			
		void updateOpponentCards(int i) {
			opponentCards[i] = false;
		}
		
		void updateDamage(int _damage) {			
			if (_damage > 0) {
				damage += _damage;
				System.out.print(_damage);
			} else System.out.print(0);
			System.out.print("  Total damage: ");
			System.out.println(damage);
			System.out.println();
		}	
	}
	
	class Human extends Gamer {
		Scanner input = new Scanner(System.in);
			
		Human() {
			super();
		}
			
		int attack() {
			index = input.nextInt();
			while (index > 11 || index < 0 || cards[index] == false) {
				System.out.print("This card can not be used! Once more, please: ");
				index = input.nextInt();
			}			
			cards[index] = false;
			return index;
		}
			
		int defense() {
			index = input.nextInt();
			while (index > 11 || index < 0 || cards[index] == false) {
				System.out.print("This card can not be used! Once more, please: ");
				index = input.nextInt();
			}
			cards[index] = false;
			return index;
		}
			
		void showCard(int card) {
			System.out.println("Human: " + card);
		}
	}
			
	class Bot extends Gamer {			
							
		Bot() {
			super();
		}
			
		int attack() {
			int k;
			switch (level) {
				case 1:
					index = (int)(Math.random()*12);
					while (cards[index] == false) index = (int)(Math.random()*12); // нападаем любой
					break;
				case 2: 					
					if (score == 2) {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*6 + 0); // нападаем маленькими
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					} 
					else if (score == 0) {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*9 + 3); // нападаем средними +
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					}
					else {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*6 + 6); // нападаем большими
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					}
					break;
					
				default: 
					if (score == 2) {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*6 + 0); // нападаем маленькими
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					} 
					else if (score == 0) {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*9 + 3); // нападаем средними +
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					}
					else {
						index = (int)(Math.random()*6);
						k = 0;
						while (cards[index] == false && k<24) {
							index = (int)(Math.random()*6 + 6); // нападаем большими
							k++;
						}
						if (k>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12); // если не находим подходящую карту, нападаем любой
						}
					}
					break;
			}
			cards[index] = false;			
			//System.out.println("*");
			return index;
		}
					
		int defense() {
			int j;
			switch (level) {
				case 1:
					index = (int)(Math.random()*12);
					while (cards[index] == false) {
						index = (int)(Math.random()*12);	// защищаемся любыми
					}
					break;
				case 2: 
					if (score == 2) {
						index = (int)(Math.random()*6);
						j = 0;
						while(cards[index] == false && j<24) {
							index = (int)(Math.random()*6+4);	// защищаемся средними +
							j++;
						}
						if (j>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12);	// если не находим подходящую карту, защищаемся любой
						} 
					else if (score == 0) {
						index = (int)(Math.random()*6);
						j = 0;
						while (cards[index] == false) index = (int)(Math.random()*8);  // защищаемся средними -
					}
					else {
						index = (int)(Math.random()*6);
						while(cards[index] == false && j<24) {
							index = (int)(Math.random()*7 + 3);   // защищаемся средними
							j++;
						}
						if (j>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12);	// если не находим подходящую карту, защищаемся любой
						}
					}
					break;
				}
				default: 
					if (score == 2) {
						index = (int)(Math.random()*6);
						j = 0;
						while(cards[index] == false && j<24) {
							index = (int)(Math.random()*6+4);	// защищаемся средними +
							j++;
						}
						if (j>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12);	// если не находим подходящую карту, защищаемся любой
						} 
					else if (score == 0) {
						index = (int)(Math.random()*6);
						j = 0;
						while (cards[index] == false) index = (int)(Math.random()*8); 	// защищаемся средними -
					}
					else {
						index = (int)(Math.random()*6);
						while(cards[index] == false && j<24) {
							index = (int)(Math.random()*7 + 3);	 // защищаемся средними
							j++;
						}
						if (j>=24) {
							while (cards[index] == false) index = (int)(Math.random()*12);  // если не находим подходящую карту, защищаемся любой
						}
					}
					break;
					}
				}
			cards[index] = false;			
			//System.out.println("*");
			return index;
		}
			
		void showCard(int card) {
			System.out.println("Bot: " + card);
		}
			
		void updateOpponentCards(int i) {
			if (i<4) little++;
			else if (i>7) big++;	// little, middle и big содержат информацию о количестве карт в каждой категории (от 0 до 3, от 4 до 7, от 8 до 11 соответственно)
			else middle++;
			if (big >= middle && big >= little) score = 2;
			else if (little >= big && little >= middle) score = 0;		// от значения score зависит стратегия бота
			else score = 1;
			opponentCards[i] = false;
		}
			
		void updateLevel(int _level) {
			level = _level;
		}
	}
	

