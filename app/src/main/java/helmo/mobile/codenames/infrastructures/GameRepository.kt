package helmo.mobile.codenames.infrastructures

import helmo.mobile.codenames.model.Game

interface GameRepository {

    fun getCurrentGame() : Game?

    fun saveGame(game: Game)

    fun deleteGame()

}