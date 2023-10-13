package helmo.mobile.codenames.presenters

/**
 * Représente les informations des équipes qui seront utiliséees par les activités et qui seront
 * envoyées au Presenter lors de la sauvegarde des options.
 *
 * @param teamName          Nom de l'équipe
 * @param teamPicturePath   Chemin permettant d'accéder à la photo de l'équipe
 */
data class TeamOptionsArgs(var teamName: String, var teamPicturePath: String)
