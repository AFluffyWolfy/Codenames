package helmo.mobile.codenames.contracts

import android.os.Bundle

/**
 * Donne une structure de base à toutes les vues
 */
interface BaseView<T> {
    fun setPresenter(presenter : T)

    fun goHome()

    fun goBack()

    fun onCreate(savedInstanceState: Bundle?)

}