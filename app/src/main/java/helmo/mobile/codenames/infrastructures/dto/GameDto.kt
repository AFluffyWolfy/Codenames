package helmo.mobile.codenames.infrastructures.dto

data class GameDto(var cards: Collection<CardDto>, val teamTurn: String)
