package helmo.mobile.codenames.contracts

import helmo.mobile.codenames.model.WordList

interface NewGameContract {
    interface Presenter : BasePresenter {
        fun onCreateGameFor(wordList: String)

    }

    interface View : BaseView<Presenter> {

        fun setWordLists(wordLists: Collection<WordList>)

        fun startGame()

    }
}