package helmo.mobile.codenames.model

class Game(cards: Map<String, Card>, currentTeam: String) {
    var cards: Map<String, Card> = HashMap()
    var blueTeamScore = 0 // Doit atteindre 8 car premier à jouer
    var redTeamScore = 0 // Doit atteindre 7 car second à jouer
    private var currentGuessingTeam = CardColor.BLUE
    private var blackRevealed: Boolean = false

    val allCards
        get() = cards.values

    val turnOf
        get() = currentGuessingTeam

    init {
        this.cards = HashMap(cards)

        calculateScores()

        if (currentTeam.uppercase() == "BLUE") {
            this.currentGuessingTeam = CardColor.BLUE
        } else if (currentTeam.uppercase() == "RED") {
            this.currentGuessingTeam = CardColor.RED
        }
    }

    /**
     * Le booléen indique si un tour à été skip (Black card, White card, ou carte équipe adverse)
     *
     * Cela permettra en UI de reset le bouton de choix de nbr de mots et
     * de rendre les cartes non clickables jusqu'à ce qu'un nombre de mots
     * ai été sélectionné
     *
     * En plus ça indique qu'il faut visuellement update la couleur de l'équipe en cours
     */
    fun takeTurn(word: String): Boolean {
        val card: Card = cards[word] ?: return false
        if (!card.isFlipped) {
            card.isFlipped = true
            when (card.cardColor) {
                CardColor.BLACK -> {
                    blackRevealed = true
                    return false
                }
                CardColor.WHITE -> return skipTurn()
                CardColor.RED ->
                    if (currentGuessingTeam == CardColor.RED) {
                        redTeamScore += 1
                        return false
                    } else {
                        redTeamScore += 1
                        skipTurn()
                        return true
                    }
                CardColor.BLUE ->
                    if (currentGuessingTeam == CardColor.BLUE) {
                        blueTeamScore += 1
                        return false
                    } else {
                        blueTeamScore += 1
                        skipTurn()
                        return true
                    }
            }
        }
        return false
    }

    /**
     * Retourne l'équipe gagnante (Color) ou null si la partie continue
     */
    fun checkGameOver(): CardColor? {
        // Vérification victoire bleu
        if (blueTeamScore == 8) {
            return CardColor.BLUE
        }

        // Vérification victoire rouge
        if (redTeamScore == 7) {
            return CardColor.RED
        }

        // Vérification victoire par carte noire
        if(blackRevealed) {
            return if(currentGuessingTeam == CardColor.BLUE) CardColor.RED else CardColor.BLUE
        }
        return null
    }

    fun skipTurn(): Boolean {
        changeGuessingTeam()
        return true
    }

    private fun changeGuessingTeam() {
        currentGuessingTeam = if (this.currentGuessingTeam == CardColor.BLUE) {
            CardColor.RED
        } else {
            CardColor.BLUE
        }
    }

    private fun calculateScores() {
        for(card in cards.values) {
            if (card.isFlipped) {
                if (card.cardColor == CardColor.BLUE) {
                    blueTeamScore += 1
                } else if (card.cardColor == CardColor.RED) {
                    redTeamScore += 1
                }
            }
        }
    }

}
