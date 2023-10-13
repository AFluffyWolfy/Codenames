package helmo.mobile.codenames.presenters

import helmo.mobile.codenames.contracts.MainActivityContract
import helmo.mobile.codenames.infrastructures.GameRepository

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val gameRepository: GameRepository
) : MainActivityContract.Presenter {

    override fun onViewCreated() {
        if (gameRepository.getCurrentGame() == null) {
            view.hideContinueButton()
        } else {
            view.showContinueButton()
        }
    }

    override fun onGoBack() {

    }
}