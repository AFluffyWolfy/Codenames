package helmo.mobile.codenames.presenters

import helmo.mobile.codenames.R
import helmo.mobile.codenames.infrastructures.IOptionRepository
import helmo.mobile.codenames.model.Options
import helmo.mobile.codenames.repository.*

class FakeOptionRepository: IOptionRepository {
    val values = HashMap(mapOf<String, String>(
        "FIRST_TEAM_NAME_KEY" to "Equipe 1",
        "SECOND_TEAM_NAME_KEY" to "Equipe 2",
        "FIRST_TEAM_PICTURE_PATH_KEY" to "",
        "SECOND_TEAM_PICTURE_PATH_KEY" to "",
        "CHOSEN_BUZZER_PATH_KEY" to "" + R.raw.ding
    ))

    override fun getOptions(): Options {
        return Options(
            values["FIRST_TEAM_NAME_KEY"]!!,
            values["SECOND_TEAM_NAME_KEY"]!!,
            values["FIRST_TEAM_PICTURE_PATH_KEY"]!!,
            values["SECOND_TEAM_PICTURE_PATH_KEY"]!!,
            values["CHOSEN_BUZZER_PATH_KEY"]!!,
        )
    }

    override fun saveOptions(options: Options) {
        values.replace("FIRST_TEAM_NAME_KEY", options!!.blueTeamName)
        values.replace("SECOND_TEAM_NAME_KEY", options!!.redTeamName)
        values.replace("FIRST_TEAM_PICTURE_PATH_KEY", options!!.blueTeamPicture)
        values.replace("SECOND_TEAM_PICTURE_PATH_KEY", options!!.redTeamPicture)
        values.replace("CHOSEN_BUZZER_PATH_KEY", options!!.chosenBuzz)
    }
}