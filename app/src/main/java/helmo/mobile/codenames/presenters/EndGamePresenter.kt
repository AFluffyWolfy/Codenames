package helmo.mobile.codenames.presenters

import helmo.mobile.codenames.contracts.EndGameContract
import helmo.mobile.codenames.infrastructures.GameRepository

class EndGamePresenter(
    private val view: EndGameContract.View,
    private val gameRepository: GameRepository
) : EndGameContract.Presenter {
    override fun onViewCreated() {
        gameRepository.deleteGame()
    }

    override fun onGoBack() {

    }

}