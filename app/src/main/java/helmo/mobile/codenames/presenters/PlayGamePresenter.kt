package helmo.mobile.codenames.presenters

import android.util.Log
import helmo.mobile.codenames.contracts.PlayContract
import helmo.mobile.codenames.infrastructures.GameRepository
import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.model.Game
import helmo.mobile.codenames.model.Options
import helmo.mobile.codenames.repository.OptionsRepository

class PlayGamePresenter(
    private val view: PlayContract.View,
    private val gameRepository: GameRepository,
    private val optionsRepository: OptionsRepository
) : PlayContract.Presenter {
    private var game: Game? = null
    private val cards = ArrayList<CardViewInfo>()
    private var isSpyView = false
    private var wasSpyViewClickedOnce: Boolean = false
    private var option: Options? = null

    private fun createCardViewInfo() {
        this.cards.clear()
        for(card in game!!.cards.values) {
            this.cards.add(CardViewInfo(card.cardColor.toString(), card.word, card.isFlipped || isSpyView))
        }
    }

    override fun onGoHome() {
        view.goHome()
    }

    override fun saveGame() {
        gameRepository.saveGame(game!!)
    }

    override fun onCardSelected(cardWord: String) {
        // Faire le tour avec la carte choisie
        if(game!!.takeTurn(cardWord)) {
            val winner = game!!.checkGameOver()
            view.setScore(game!!.blueTeamScore, game!!.redTeamScore)
            if(winner != null) {
                if(winner == CardColor.BLUE) {
                    view.gameOver(option!!.blueTeamPicture, option!!.blueTeamName)
                } else{
                    view.gameOver(option!!.redTeamPicture, option!!.redTeamName)
                }
            } else {
                view.changeCurrentTeam()
            }
        } else {
            val winner = game!!.checkGameOver()
            view.setScore(game!!.blueTeamScore, game!!.redTeamScore)
            if(winner != null) {
                if(winner == CardColor.BLUE) {
                    view.gameOver(option!!.blueTeamPicture, option!!.blueTeamName)
                } else{
                    view.gameOver(option!!.redTeamPicture, option!!.redTeamName)
                }
            }
        }
        createCardViewInfo()
        view.displayCards(cards)
        Log.d("PlayGamePresenter", "Card selected")
    }

    override fun onViewCreated() {
        game = gameRepository.getCurrentGame()
        option = optionsRepository.getOptions()
        createCardViewInfo()
        view.displayCards(cards)
        view.setScore(game!!.blueTeamScore, game!!.redTeamScore)
        view.setBuzzerSound(Integer.parseInt(option!!.chosenBuzz))
        view.setTeamsPicture(option!!.blueTeamPicture, option!!.redTeamPicture)
        view.setTeamsName(option!!.blueTeamName, option!!.redTeamName)
        view.setCurrentTeam(game!!.turnOf.toString())
    }

    override fun onGoBack() {
        onViewLeave()
    }

    override fun onViewLeave() {
        gameRepository.saveGame(game!!)
    }

    override fun onSpyViewClicked() {
        if (!wasSpyViewClickedOnce) {
            wasSpyViewClickedOnce = true
            view.warnUserSpyView()
        } else {
            isSpyView = !isSpyView
            createCardViewInfo()
            view.displayCards(cards)
        }
    }

    override fun skipTurnRequested() {
        game!!.skipTurn()
        view.changeCurrentTeam()
    }
}