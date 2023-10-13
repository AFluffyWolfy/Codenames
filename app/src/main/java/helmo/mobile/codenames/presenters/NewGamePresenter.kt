package helmo.mobile.codenames.presenters

import helmo.mobile.codenames.contracts.NewGameContract
import helmo.mobile.codenames.infrastructures.GameRepository
import helmo.mobile.codenames.model.WordList
import helmo.mobile.codenames.model.factories.GameFactory
import helmo.mobile.codenames.repository.WordListRepository

class NewGamePresenter(
    private val view: NewGameContract.View,
    private val repo: WordListRepository,
    private val gameRepository: GameRepository,
) : NewGameContract.Presenter {
    private val factory = GameFactory(repo)

    override fun onCreateGameFor(wordList: String) {
        gameRepository.saveGame(factory.newGameForList(wordList))
        view.startGame()
    }

    override fun onViewCreated() {
        view.setWordLists(repo.getListsOfWords())
    }

    override fun onGoBack() {

    }
}