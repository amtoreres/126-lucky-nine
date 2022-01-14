package com.aldrich;

import java.util.ArrayList;	
import java.util.Arrays;
import java.util.Random;

/**
 * Contain methods for getting the rank and suit of the cards.
 */
public class Card {
	
	Random rand = new Random();
	/**
	 * Arraylist of the drawn cards.
	 */
	ArrayList<String> drawnCards = new ArrayList<String>(); 
	
	/**
	 * This method gets a random card rank from the arraylist of card ranks.
	 * @return string value of card rank.
	 */
	public String getRank() {
		ArrayList<Object> ranks = new ArrayList<>(
				Arrays.asList("Ace", 2, 3, 4, 5, 6, 7, 8, 9, 10, "Jack", "Queen", "King"));
		
		Object rank = ranks.get(rand.nextInt(ranks.size()));
		return rank.toString();
	}
	
	/**
	 * This method gets a random card suit from the arraylist of card suits.
	 * @return	string value of card suit.
	 */
	public String getSuit() {
		String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
		
		int index = rand.nextInt(suits.length);
		String suit = suits[index];
		return suit;
	}

}
