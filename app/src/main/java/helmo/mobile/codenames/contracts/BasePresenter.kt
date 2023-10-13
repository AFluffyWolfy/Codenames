package helmo.mobile.codenames.contracts

/**
 * Donne une structure de base à tout les Presenter
 */
interface BasePresenter {

    /**
     * Notifie le presneter que la vue est crée
     */
    fun onViewCreated()

    /**
     * Réviens à la vue précédente
     */
    fun onGoBack()

}