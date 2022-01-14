package com.aldrich;

import java.util.Scanner;

/**
 * This is a simple Lucky 9 game with betting feature.
 * @since 01-12-2022
 * @version 1.0
 * @author Aldrich Toreres
 * 
 */
public class MainGame {

	private static PlayerHand playerHand = new PlayerHand();
	private static PlayerHand dealerHand = new PlayerHand();
	private static Statistics stat = new Statistics();
	private static String[] currentCards = new String[3];
	private static char choice;
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("\t\t\t\tLUCKY 9!\n");
		int playerMoney = 100; 
		int dealerMoney = 150;
		int totalRounds = 0;
		int roundsWon = 0;
		int roundsLost = 0;
		int currentLargestBet = 0;
		
		while(true) {
			totalRounds+=1;
			System.out.print("==========================================================================\n");
			System.out.println("\t\t\t\tROUND " + totalRounds);
			
			System.out.println("Your current money: $" + playerMoney);
			int playerBet = getBet(playerMoney, dealerMoney);
			currentLargestBet = stat.getLargestBet(currentLargestBet, playerBet);
			revealPlayerHand();
			revealTotalPoints(true);
			
			choiceToHit(playerHand.calculateTotalPoints(currentCards));
			int playerPoints = playerHand.calculateTotalPoints(currentCards);
			
			revealDealerHandAndPoints(dealerMoney);
			
			String winLoseDraw = result(playerPoints, dealerHand.calculateTotalPoints(currentCards));
			int[] money = gainOrLoss(playerMoney, dealerMoney, playerBet, winLoseDraw);
			playerMoney = money[0];
			dealerMoney = money[1];
			
			int[] numberOfWinsAndLosses = stat.updateRoundsResult(winLoseDraw, roundsWon, roundsLost);
			roundsWon = numberOfWinsAndLosses[0];
			roundsLost = numberOfWinsAndLosses[1];
			
			if (dealerMoney == 0 || playerMoney == 0) {
				if (dealerMoney == 0) {
					System.out.println("Dealer has no money! You win!\n");	
				}
				if (playerMoney == 0) {
					System.out.println("You don't have money! You lost!\n");
				}
				
				revealStatistics(currentLargestBet, playerBet, totalRounds, roundsWon, roundsLost, playerMoney);
				System.out.println("\n------------------------THANKS FOR PLAYING!------------------------");
				System.exit(0);
			}
					
			System.out.print("Do you want to play another round (Y/N)? ");
			char isContinue = scan.next().charAt(0);
			
			System.out.println();
			if (isContinue == 'n') {
				revealStatistics(currentLargestBet, playerBet, totalRounds, roundsWon, roundsLost, playerMoney);
				System.out.println("\n------------------------THANKS FOR PLAYING!------------------------");
				break;
			}		
		}
			scan.close();		
	}
	
	/**
	 * This method reveals the current hand of the player.
	 */
	private static void revealPlayerHand() {
		System.out.print("Your current hand: \t");
		playerHand.getInitialCards(currentCards);
		playerHand.revealCurrentCards(currentCards);
	}
	
	/**
	 * This method reveals the total points of either the player and the dealer.
	 * @param bool	switch to either calculate the points of the player or the dealer.
	 */
	private static void revealTotalPoints(boolean bool) {
		if (bool) {
			System.out.println("\nTotal Points: " + playerHand.calculateTotalPoints(currentCards));
		}
		else {
			System.out.println("\nTotal Points: " + dealerHand.calculateTotalPoints(currentCards));
		}
		
	}
	
	/**
	 * This method gives the player the choice to hit or stand.
	 * @param totalPoints Total points of the player
	 */
	private static void choiceToHit(int totalPoints) {
		if(totalPoints == 0) {
			System.out.println("0 points. An automatic hit.");
			choice = 'y';
		}
		else {
			do {
				System.out.print("Hit or Stand (Y/N)? ");
				char rawChoice = scan.next().charAt(0);
				choice = Character.toLowerCase(rawChoice);
			
			} while(choice != 'y' && choice != 'n');
		}
		
		if(choice == 'y') {
			System.out.print("Your current hand: \t");
			playerHand.getThirdCard(currentCards, choice);
			playerHand.revealCurrentCards(currentCards);
			revealTotalPoints(true);
		}
	}
	
	/**
	 * This method reveals the current hand and the total points of the dealer.
	 * @param dealerMoney Current money of the dealer.
	 */
	private static void revealDealerHandAndPoints(int dealerMoney) {
		System.out.println("\n\nDealer's current money: $" + dealerMoney);
		System.out.print("Dealer's Current Hand: \t");
		dealerHand.getInitialCards(currentCards);
		dealerHand.revealCurrentCards(currentCards);
		int points = dealerHand.calculateTotalPoints(currentCards);
		System.out.println("\nTotal Points: " + points);
		
		if (dealerHand.calculateTotalPoints(currentCards) < 6) {
			System.out.println("Dealer decided to hit.");
			choice = 'y';
		}
		else {
			choice = 'n';
		}
		
		if (choice == 'y') {
			dealerHand.getThirdCard(currentCards, choice);
			System.out.print("Dealer's Current Hand: \t");
			dealerHand.revealCurrentCards(currentCards);
			revealTotalPoints(false);
		}
		
	}
	
	/**
	 * This method checks if the player won and returns "win", "lose", or "draw".
	 * @param playerPoints	Total points of the player.
	 * @param dealerPoints	Total points of the dealer.
	 * @param bet			Current bet.
	 * @param playerMoney	Current money of the player.
	 * @param dealerMoney	Current money of the dealer.
	 * @return "win" if total points of the player is greater than the total points of the dealer; "lose" if total points of dealer is greater than the total points of the player; and "draw" if player and dealer has equal total points.
	 */
	private static String result(int playerPoints, int dealerPoints) {
		System.out.println("\n\t\t\t********************");
		if(playerPoints > dealerPoints) {
			System.out.println("\t\t\t*      YOU WIN     *");
			System.out.println("\t\t\t********************\n");
			return "win";
		}
		else if (playerPoints < dealerPoints) {
			System.out.println("\t\t\t*     YOU LOSE     *");
			System.out.println("\t\t\t********************\n");
			return "lose";
		}
		else {
			System.out.println("\t\t\t*    IT'S A DRAW   *");
			System.out.println("\t\t\t********************\n");
			return "draw";
		}
		
	}
	
	/**
	 * This method either adds or subtracts money from the player and dealer 
	 * and returns an array of the player's and dealer's money.
	 * @param playerMoney	int value of current money of the player.
	 * @param dealerMoney	int value of urrent money of the dealer.
	 * @param bet			int value of current bet.
	 * @param winLoseDraw	string value of either "win", "lose", or "draw".
	 * @return an array where array[0] is the player's money and array[1] is the dealer's money.
	 */
	private static int[] gainOrLoss(int playerMoney, int dealerMoney, int bet, String winLoseDraw) {
		int[] money = new int[2];
		switch(winLoseDraw) {
			case "win":
				money[0] = playerMoney+=bet;
				money[1] = dealerMoney-=bet;
				break;
			
			case "lose":
				money[0] = playerMoney-=bet;
				money[1] = dealerMoney+=bet;
				break;
			
			case "draw":
				money[0] = playerMoney;
				money[1] = dealerMoney;
				break;
			default:
				// do nothing
		}
		return money;
	}
	
	/**
	 * This method gets int bet from the player.
	 * @param playerMoney	int value of current money of the player.
	 * @param dealerMoney	int value of current money of the dealer.
	 * @return	int value of bet
	 */
	private static int getBet(int playerMoney, int dealerMoney) {
		System.out.print("Place your bet: $");
		while(true) {
			try {
				int bet = 	Integer.parseInt(scan.next());
				if (bet > playerMoney) {
					System.out.print("Insufficient money. Bet again: $");
					continue;
				}
				else if (bet > dealerMoney) {
					System.out.print("Maximum bet is $" + dealerMoney +  ". Bet again: $");
					continue;
				}
				else if (bet < 1) {
					System.out.print("Minimum bet is $1. Bet again: $" );
					continue;
				}
				else {		
					return bet;
				}
			}
			catch (Exception e) {
				System.out.print("Please enter a valid bet: ");
			}
			
			
		}
	}
	
	/**
	 * This method reveals the statistics after the game ends.
	 * @param currentLargestBet	int value of current largest bet.
	 * @param playerBet			int value of the bet from the player.
	 * @param totalRounds		int value of the number of the total rounds.
	 * @param roundsWon			int value of the number of the rounds won.
	 * @param roundsLost		int value of the number of the rounds lost.
	 * @param playerMoney		int value of the current money of the player
	 */
	private static void revealStatistics(int currentLargestBet, int playerBet, int totalRounds, int roundsWon, int roundsLost, int playerMoney) {
		System.out.println("\t\t\t+++ STATISTICS +++");
		int largestBet = stat.getLargestBet(currentLargestBet, playerBet);
		System.out.println("\t\t\tLargest Bet: $" + largestBet);
		System.out.println("\t\t\tTotal Rounds: " + totalRounds);
		System.out.println("\t\t\tNumber of rounds won: " + roundsWon);
		System.out.println("\t\t\tNumber of rounds lost: " + roundsLost);
		if(playerMoney >= 100) {
			System.out.println("\t\t\tMoney gained: $" + (playerMoney - 100));
		}
		else {
			System.out.println("\t\t\tMoney lost: $" + (100 - playerMoney));
		}
	}

}
