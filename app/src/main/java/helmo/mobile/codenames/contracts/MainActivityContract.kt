package helmo.mobile.codenames.contracts

import helmo.mobile.codenames.model.WordList

interface MainActivityContract {
    interface Presenter : BasePresenter {

    }

    interface View : BaseView<MainActivityContract.Presenter> {
        fun hideContinueButton()
        fun showContinueButton()
    }
}