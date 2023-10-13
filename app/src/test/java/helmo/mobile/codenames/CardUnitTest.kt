package helmo.mobile.codenames

import helmo.mobile.codenames.model.Card
import helmo.mobile.codenames.model.CardColor
import org.junit.Test

import org.junit.Assert.*

class CardUnitTest {

    private val card: Card = Card("card", false, CardColor.BLUE)
    private val cardSameWord: Card = Card("card", true, CardColor.BLACK)
    private val cardDifferentWord: Card = Card("cardDifferent", true, CardColor.RED)
    private val cardNull: Card? = null

    @Test
    fun cards_with_same_words_are_equals() {
        assertTrue(card == cardSameWord)
    }

    @Test
    fun card_different_words_are_not_equals() {
        assertFalse(card == cardDifferentWord)
    }

    @Test
    fun card_null_not_equals_card() {
        assertFalse(card == cardNull)
    }

    @Test
    fun card_get_works() {
        assertTrue(card.word == "card")
        assertTrue(!card.isFlipped)
        assertTrue(card.cardColor == CardColor.BLUE)
    }
}