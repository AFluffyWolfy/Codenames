package helmo.mobile.codenames.infrastructures

import helmo.mobile.codenames.infrastructures.dto.CardDto
import helmo.mobile.codenames.infrastructures.dto.GameDto
import helmo.mobile.codenames.model.Card
import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.model.Game

class Mapper {

    companion object {

        fun toDTO(game: Game): GameDto? {
            var cards: List<CardDto> = ArrayList()
            for (c: Card in game.allCards) {
                cards = cards.plus(toDTO(c)!!)
            }
            return GameDto(cards, game.turnOf.toString())
        }

        fun fromDTO(dto: GameDto): Game? {
            var cards = mapOf<String, Card>()
            for (c: CardDto in dto.cards) {
                cards = cards.plus(c.word to fromDTO(c)!!)
            }
            return Game(cards, dto.teamTurn)
    }

        fun toDTO(card: Card): CardDto? {
            return CardDto(card.cardColor.toString(), card.word, card.isFlipped)
        }

        fun fromDTO(dto: CardDto): Card? {
            return Card(dto.word, dto.isFlipped, CardColor.valueOf(dto.color))
        }

    }

}