package helmo.mobile.codenames.repository

import helmo.mobile.codenames.infrastructures.dto.CardDto
import helmo.mobile.codenames.infrastructures.dto.GameDto
import helmo.mobile.codenames.model.Card
import helmo.mobile.codenames.model.Game

class Mapper {

    /**
     * Retournera l'équivalent d'une Game, en GTO.
     *
     * @param game  Game à convertir
     *
     * @return      La game donnée convertie en DTO.
     */
    fun convertGameToDto(game: Game): GameDto? {
        return null
    }

    /**
     * Retournera l'équivalent d'un DTO, en Game.
     *
     * @param dto   DTO à convertir
     *
     * @return      Le DTO donné converti en Game utilisable par le jeu.
     */
    fun convertDtoToGame(dto: GameDto): Game? {
        return null
    }

    /**
     * Retournera l'équivalent d'un DTO, en Card.
     *
     * @param card  Carte à convertir
     *
     * @return      Le donnée convertie en Card du jeu.
     */
    private fun convertDtoToCard(dto: CardDto): Card? {
        return null
    }

    /**
     * Retournera l'équivalent d'une carte, en DTO.
     * Ce DTO servira pour le stockage de la carte sur un quelconque système de stockage.
     *
     * @param card  Carte à convertir
     *
     * @return      La carte donnée convertie en DTO.
     */
    private fun convertCardToDto(card: Card): CardDto? {
        return null
    }

}