package helmo.mobile.codenames.repository

import android.content.SharedPreferences
import helmo.mobile.codenames.R
import helmo.mobile.codenames.infrastructures.IOptionRepository
import helmo.mobile.codenames.model.Options

private const val FIRST_TEAM_NAME_KEY = "FIRST_TEAM_NAME_KEY"
private const val SECOND_TEAM_NAME_KEY = "SECOND_TEAM_NAME_KEY"
private const val FIRST_TEAM_PICTURE_PATH_KEY = "FIRST_TEAM_PICTURE_PATH_KEY"
private const val SECOND_TEAM_PICTURE_PATH_KEY = "SECOND_TEAM_PICTURE_PATH_KEY"
private const val CHOSEN_BUZZER_PATH_KEY = "BUZZER_PATH_KEY"

/**
 * Cette classe permet de récupérer et enregistrer les options dans le stockage de l'appareil.
 */
class OptionsRepository(private val sharedPreferences: SharedPreferences): IOptionRepository {

    /**
     * Récupère les options enregistrées dans les SharedPreferences données plus tôt au constructeur.
     */
    override fun getOptions(): Options {
        return Options(
            sharedPreferences.getString(FIRST_TEAM_NAME_KEY, "Equipe 1")!!,
            sharedPreferences.getString(SECOND_TEAM_NAME_KEY, "Equipe 2")!!,
            sharedPreferences.getString(FIRST_TEAM_PICTURE_PATH_KEY,"")!!,
            sharedPreferences.getString(SECOND_TEAM_PICTURE_PATH_KEY,"")!!,
            sharedPreferences.getString(CHOSEN_BUZZER_PATH_KEY,"" + R.raw.ding)!!
        )
    }

    /**
     * Sauvegarde les options données.
     */
    override fun saveOptions(options: Options) {
        sharedPreferences.edit()
            .putString(FIRST_TEAM_NAME_KEY, options.blueTeamName)
            .putString(SECOND_TEAM_NAME_KEY, options.redTeamName)
            .putString(FIRST_TEAM_PICTURE_PATH_KEY, options.blueTeamPicture)
            .putString(SECOND_TEAM_PICTURE_PATH_KEY, options.redTeamPicture)
            .putString(CHOSEN_BUZZER_PATH_KEY, options.chosenBuzz)
            .apply()
    }
}