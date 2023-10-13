package helmo.mobile.codenames.contracts

import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.presenters.CardViewInfo

interface PlayContract {

    interface Presenter : BasePresenter {

        fun onGoHome()

        fun saveGame()

        fun onViewLeave()

        fun onCardSelected(cardWord: String)

        fun onSpyViewClicked()

        fun skipTurnRequested()

    }

    interface View : BaseView<Presenter> {

        fun displayCards(cards: List<CardViewInfo>)

        fun setTeamsName(blueTeam: String, redTeam: String)

        fun setTeamsPicture(blueTeam: String, redTeam: String)

        fun setCurrentTeam(color: String)

        fun gameOver(winnerPath: String, winnerName: String)

        fun changeCurrentTeam()

        fun setScore(blueScore: Int, redScore: Int)

        fun warnUserSpyView()

        fun setBuzzerSound(idResource: Int)

    }

}