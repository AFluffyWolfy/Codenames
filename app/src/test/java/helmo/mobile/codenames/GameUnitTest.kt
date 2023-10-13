package helmo.mobile.codenames

import helmo.mobile.codenames.model.Card
import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.model.Game
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GameUnitTest {
    private val cards: Map<String, Card> = buildCards()
    private val justBeganCards: Map<String, Card> = buildJustBeganCards()
    private lateinit var almostOverGame: Game
    private lateinit var justBeganGame: Game

    
    @Before
    fun setup() {
        almostOverGame = Game(HashMap(cards), "red")
        justBeganGame = Game(HashMap(justBeganCards), "blue")
    }

    @Test
    fun score_is_calculated_correctly() {
        assertTrue(almostOverGame.blueTeamScore == 7 && almostOverGame.redTeamScore == 6)
        assertTrue(justBeganGame.blueTeamScore == 0 && justBeganGame.redTeamScore == 0)
    }

    @Test
    fun turn_is_correct_on_build() {
        assertTrue(almostOverGame.turnOf == CardColor.RED)
        assertTrue(justBeganGame.turnOf == CardColor.BLUE)
    }

    @Test
    fun game_is_not_over() {
        assertTrue(almostOverGame.checkGameOver() == null)
    }

    @Test
    fun select_white_card_skips_turn() {
        assertTrue(almostOverGame.takeTurn("blanc1"))
        assertTrue(almostOverGame.turnOf == CardColor.BLUE)
    }

    @Test
    fun select_already_flipped_card_does_nothing() {
        assertFalse(almostOverGame.takeTurn("bleue2"))
        assertFalse(almostOverGame.takeTurn("rouge2"))
        assertFalse(almostOverGame.takeTurn("blanc2"))
        assertTrue(almostOverGame.turnOf == CardColor.RED)
    }

    @Test
    fun blue_wins_by_score() {
        // Blue turn
        assertTrue(almostOverGame.skipTurn())
        assertFalse(almostOverGame.takeTurn("bleue7"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.BLUE)
    }

    @Test
    fun blue_wins_by_score_from_red() {
        assertTrue(almostOverGame.takeTurn("bleue7"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.BLUE)
    }

    @Test
    fun blue_wins_by_black_card() {
        assertFalse(almostOverGame.takeTurn("noire"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.BLUE)
    }

    @Test
    fun red_wins_by_score_from_blue() {
        // Blue turn
        assertTrue(almostOverGame.skipTurn())
        assertTrue(almostOverGame.takeTurn("rouge5"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.RED)
    }

    @Test
    fun red_wins_by_score() {
        assertFalse(almostOverGame.takeTurn("rouge5"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.RED)
    }

    @Test
    fun red_wins_by_black_card() {
        // Blue turn
        assertTrue(almostOverGame.skipTurn())
        assertFalse(almostOverGame.takeTurn("noire"))
        assertTrue(almostOverGame.checkGameOver() == CardColor.RED)
    }

    private fun buildJustBeganCards(): Map<String, Card> {
        return HashMap(
            mapOf(
                "bleue1" to Card("bleue1", false, CardColor.BLUE),
                "bleue2" to Card("bleue2", false, CardColor.BLUE),
                "bleue3" to Card("bleue3", false, CardColor.BLUE),
                "bleue4" to Card("bleue4", false, CardColor.BLUE),
                "bleue5" to Card("bleue5", false, CardColor.BLUE),
                "bleue6" to Card("bleue6", false, CardColor.BLUE),
                "bleue7" to Card("bleue7", false, CardColor.BLUE),
                "bleue8" to Card("bleue8", false, CardColor.BLUE),
                "rouge1" to Card("rouge1", false, CardColor.RED),
                "rouge2" to Card("rouge2", false, CardColor.RED),
                "rouge3" to Card("rouge3", false, CardColor.RED),
                "rouge4" to Card("rouge4", false, CardColor.RED),
                "rouge5" to Card("rouge5", false, CardColor.RED),
                "rouge6" to Card("rouge6", false, CardColor.RED),
                "rouge7" to Card("rouge7", false, CardColor.RED),
                "blanc1" to Card("blanc1", false, CardColor.WHITE),
                "blanc2" to Card("blanc2", false, CardColor.WHITE),
                "blanc3" to Card("blanc3", false, CardColor.WHITE),
                "blanc4" to Card("blanc4", false, CardColor.WHITE),
                "blanc5" to Card("blanc5", false, CardColor.WHITE),
                "blanc6" to Card("blanc6", false, CardColor.WHITE),
                "blanc7" to Card("blanc7", false, CardColor.WHITE),
                "blanc8" to Card("blanc8", false, CardColor.WHITE),
                "noire" to Card("noire", false, CardColor.BLACK)
            )
        )
    }


    private fun buildCards(): Map<String, Card> {
        return HashMap(
            mapOf(
                "bleue1" to Card("bleue1", true, CardColor.BLUE),
                "bleue2" to Card("bleue2", true, CardColor.BLUE),
                "bleue3" to Card("bleue3", true, CardColor.BLUE),
                "bleue4" to Card("bleue4", true, CardColor.BLUE),
                "bleue5" to Card("bleue5", true, CardColor.BLUE),
                "bleue6" to Card("bleue6", true, CardColor.BLUE),
                "bleue7" to Card("bleue7", false, CardColor.BLUE),
                "bleue8" to Card("bleue8", true, CardColor.BLUE),
                "rouge1" to Card("rouge1", true, CardColor.RED),
                "rouge2" to Card("rouge2", true, CardColor.RED),
                "rouge3" to Card("rouge3", true, CardColor.RED),
                "rouge4" to Card("rouge4", true, CardColor.RED),
                "rouge5" to Card("rouge5", false, CardColor.RED),
                "rouge6" to Card("rouge6", true, CardColor.RED),
                "rouge7" to Card("rouge7", true, CardColor.RED),
                "blanc1" to Card("blanc1", false, CardColor.WHITE),
                "blanc2" to Card("blanc2", true, CardColor.WHITE),
                "blanc3" to Card("blanc3", false, CardColor.WHITE),
                "blanc4" to Card("blanc4", true, CardColor.WHITE),
                "blanc5" to Card("blanc5", false, CardColor.WHITE),
                "blanc6" to Card("blanc6", true, CardColor.WHITE),
                "blanc7" to Card("blanc7", false, CardColor.WHITE),
                "blanc8" to Card("blanc8", true, CardColor.WHITE),
                "noire" to Card("noire", false, CardColor.BLACK)
            )
        )
    }

}