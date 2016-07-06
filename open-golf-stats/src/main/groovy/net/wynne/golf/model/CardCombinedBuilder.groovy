package net.wynne.golf.model

import java.util.List;

import net.wynne.golf.types.Round;

class CardCombinedBuilder {
	
	String date
	Player player
	
	ScoreCard front
	ScoreCard back

	Round round
	
	ScoreCard build() {
		ScoreCard card = new ScoreCard(date:date, player:player, round:Round.COMBINED)

		card.name = "<" + front.course.id+ "/" + back.course.id+ ">"
		card.tee = "<" + front.tee + "/" + back.tee + ">"

		card.scores.addAll(front.scores)
		card.scores.addAll(back.scores)

		Tee frontTee = front.course.tees[front.tee]
		Tee backTee = back.course.tees[back.tee]
		
		card.rating = frontTee.rating + backTee.rating
		card.slope  = (frontTee.slope  + backTee.slope)/2
		card.parOut = backTee.parOut
		card.parIn = frontTee.parOut
		
		return card
	}
	
	Tee findTee(List<Tee> tees, String color) {
		tees.each { Tee tee ->
			if (tee.color.equals(color)) {
				return Tee
			}
		}
		return null
	}

}
