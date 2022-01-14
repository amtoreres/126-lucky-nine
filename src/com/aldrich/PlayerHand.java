package com.aldrich;

/**
 * Contain methods for getting cards and calculating total points.
 */
public class PlayerHand {
	
	/**
	 * static card instance of Card class.
	 */
	static Card card = new Card();
	
	/**
	 * This method gives the player and dealer the two initial cards.
	 * @param currentCards array of current cards.
	 */
	public void getInitialCards(String[] currentCards) {
		for (int i=0; i<2; i++) {
			while(true) {
				if (card.drawnCards.size() == 52) {
					card.drawnCards.clear();
				}
				currentCards[i] = card.getRank() + " of " + card.getSuit();
				
				if (card.drawnCards.contains(currentCards[i].toString()) == false) {
					card.drawnCards.add(currentCards[i].toString());
					break;
				}
			}
		}
		
		if (currentCards[2] != null) {
			currentCards[2] = null;
		}
	}
	
	/**
	 * This method reveals the current hand of either player or dealer.
	 * @param currentCards array of current cards.
	 */
	public void revealCurrentCards(String[] currentCards) {
		for (int i=0; i<3; i++) {
			if(currentCards[i] != null) {
				System.out.print(currentCards[i] + "     ");	
			}		
		}
	}
	
	/**
	 * This methods calculates the total points of the current cards of either 
	 * player or dealer and returns the int value of total points
	 * @param currentCards	array of current cards.
	 * @return	int value of total points.
	 */
	public int calculateTotalPoints(String[] currentCards) {
		int totalPoints = 0;
		for (int i=0; i<=2; i++) {
			if (currentCards[i] != null) {
				String rank = currentCards[i].split(" ")[0];
				try {
					totalPoints += Integer.parseInt(rank);
				}
				catch(Exception e) {
					if (rank.equals("Ace")) {
						totalPoints += 1;
					}
					else if (rank.equals("Jack")) {
						totalPoints += 11;
					}
					else if (rank.equals("Queen")) {
						totalPoints += 12;
					}
					else {
						totalPoints += 13;
					}
				}
			}
			if (totalPoints >= 10) {
				totalPoints -= 10;
			}
		}
		
		return totalPoints;
	}
	
	/**
	 * This method gives the player a third card if the player chose to hit.
	 * @param currentCards	array of current cards.
	 * @param choice		char int of choice.
	 */
	public void getThirdCard(String[] currentCards, char choice) {
		while(true) {
			if (card.drawnCards.size() == 52) {
				card.drawnCards.clear();
			}
			
			currentCards[2] = card.getRank() + " of " + card.getSuit();
			if(card.drawnCards.contains(currentCards[2].toString()) == false) { 
				card.drawnCards.add(currentCards[2].toString());
				break;
			}
		}	
	}

}
