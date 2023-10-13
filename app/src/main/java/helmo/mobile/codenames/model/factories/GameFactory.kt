package helmo.mobile.codenames.model.factories

import helmo.mobile.codenames.model.Card
import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.model.Game
import helmo.mobile.codenames.repository.WordListRepository
import java.util.stream.Collectors
import java.util.stream.IntStream

class GameFactory(val wordListRepo: WordListRepository) {

    fun newGameForList(wordListName: String) : Game {
        val wordList = wordListRepo.getListsOfWords().first {
            it.listName == wordListName
        }
        val words: ArrayList<String> = ArrayList(wordList.words)

        words.shuffle()
        val randomWords: ArrayList<String> = ArrayList(words.subList(0, 24))

        val mapCards: HashMap<String, Card> = HashMap()
        val positions: ArrayList<Int> = IntStream.rangeClosed(0, 23).boxed().collect(
            Collectors.toList()) as ArrayList<Int>
        positions.shuffle()

        val blueCardsPositions: HashSet<Int> = HashSet(positions.subList(0, 8))
        val redCardsPositions: HashSet<Int> = HashSet(positions.subList(8, 15))
        val whiteCardsPositions: HashSet<Int> = HashSet(positions.subList(15, 23))
        val blackCardPosition: Int = positions[23]

        for(i in 0..23) {
            if(blueCardsPositions.contains(i)) {
                mapCards[randomWords[i]] = Card(randomWords[i], false, CardColor.BLUE)
            } else if (redCardsPositions.contains(i)) {
                mapCards[randomWords[i]] = Card(randomWords[i], false, CardColor.RED)
            } else if (whiteCardsPositions.contains(i)) {
                mapCards[randomWords[i]] = Card(randomWords[i], false, CardColor.WHITE)
            } else if (blackCardPosition == i) {
                mapCards[randomWords[i]] = Card(randomWords[i], false, CardColor.BLACK)
            }
        }
        return Game(mapCards, "Blue")
    }

}