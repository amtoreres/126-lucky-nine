package com.aldrich;
/**
 * Contain methods for revealing the statistics after the game ends.
 */
public class Statistics {
	
	/**
	 * This method gets and returns the largest bet.
	 * @param currentLargestBet		int value of current largest bet.
	 * @param playerBet				int value of current bet.	
	 * @return	int value of current largest bet.
	 */
	public int getLargestBet(int currentLargestBet, int playerBet) {
		if (currentLargestBet < playerBet) {
			currentLargestBet = playerBet;
			return currentLargestBet;
		}
		return currentLargestBet;
	}
	
	/**
	 * This methods records the number of rounds won and rounds lost of the player 
	 * and returns an array containing the number of wins and losses.
	 * @param winLoseDraw	string value of either "win", "lose", or "char".
	 * @param roundsWon		int value of rounds won.
	 * @param roundsLost	int value of rounds lost.
	 * @return	an array where array[0] is the number of rounds won and array[1] is the number of rounds lost.
	 */
	public int[] updateRoundsResult(String winLoseDraw, int roundsWon, int roundsLost) {
		int[] numOfWinsAndLose = new int[2];
		switch(winLoseDraw) {
			case "win":
				numOfWinsAndLose[0] = roundsWon+=1;
				numOfWinsAndLose[1] = roundsLost;
				break;
			case "lose":
				numOfWinsAndLose[0] = roundsWon;
				numOfWinsAndLose[1] = roundsLost+=1;
				break;
			case "draw":
				numOfWinsAndLose[0] = roundsWon;
				numOfWinsAndLose[1] = roundsLost;
				break;
			default:
				// do nothing
		}
		return numOfWinsAndLose;
	}
	
}
