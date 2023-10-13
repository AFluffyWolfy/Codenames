package helmo.mobile.codenames.model

/**
 * Réprésente les options de l'application.
 */
data class Options (
    var blueTeamName: String = "Equipe Bleue",
    var redTeamName: String = "Equipe Rouge",
    var blueTeamPicture: String = "path",
    var redTeamPicture: String = "path",
    var chosenBuzz: String = "path"
)