package helmo.mobile.codenames.model

class Card(word: String, isFlipped: Boolean, cardColor: CardColor) {
    val word: String
    var isFlipped = false
    val cardColor: CardColor

    init {
        this.word = word
        this.isFlipped = isFlipped
        this.cardColor = cardColor
    }

    override fun equals(other: Any?): Boolean =
        (other is Card) && word == other.word
}