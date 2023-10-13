package helmo.mobile.codenames.contracts

import helmo.mobile.codenames.presenters.TeamOptionsArgs

/**
 * Definit le contrat entre le presenter et la vue pour l'activité Options
 * Chaque fonction définie dans le presenter sera connue de sa vue et vice-versa
 *
 * La vue sera le fichier OptionsActivity avec son layout activity_options.
 *
 * Le presenter sera la fichier OptionsPresenter
 */
interface OptionsContract {
    interface Presenter : BasePresenter {
        fun onOptionsConfirmed()

        /**
         * Notifie le presenter que l'utilisateur souhaite sauvegarder les modifications apportées aux options.
         */
        fun onSaveOption(firstTeamInfos: TeamOptionsArgs, secondTeamInfos: TeamOptionsArgs, buzzer: String?)
    }

    interface View : BaseView<Presenter> {
        fun displayNameChanges() // <-- y'en a pas besoin mais c'est pour montrer commenr ça marche
        // etc ...

        /**
         * Affiche les informations données pour les équipes.
         *
         * @param firstTeamInfos    Informations de la première équipe
         * @param secondTeamInfos    Informations de la deuxième équipe
         */
        fun setTeamsInfos(firstTeamInfos: TeamOptionsArgs, secondTeamInfos: TeamOptionsArgs)

        fun setBuzzers(buzzers: List<String>, selectedBuzzer: String)

        fun displayMessage(message: String)
    }
}