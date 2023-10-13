package helmo.mobile.codenames.presenters

import helmo.mobile.codenames.R
import helmo.mobile.codenames.contracts.OptionsContract
import helmo.mobile.codenames.infrastructures.IOptionRepository
import helmo.mobile.codenames.model.Options
import helmo.mobile.codenames.repository.OptionsRepository

class OptionsPresenter(
    private val view: OptionsContract.View,
    private val repo: IOptionRepository
)
    : OptionsContract.Presenter {


    private val buzzersSongPath: Map<String, Int> = hashMapOf(
        "blink" to R.raw.blink,
        "ding" to R.raw.ding,
        "twinkle" to R.raw.twinkle
    )

    private lateinit var options: Options

    override fun onViewCreated() {
        options = repo.getOptions()
        view.setTeamsInfos(
            TeamOptionsArgs(options.blueTeamName, options.blueTeamPicture),
            TeamOptionsArgs(options.redTeamName, options.redTeamPicture)
        )
        view.setBuzzers(buzzersSongPath.keys.toList(), options.chosenBuzz)
    }

    override fun onOptionsConfirmed() {

    }

    override fun onGoBack() {
        view.goBack()
    }

    override fun onSaveOption(firstTeamInfos: TeamOptionsArgs, secondTeamInfos: TeamOptionsArgs, buzzer: String?) {
        options.blueTeamName = firstTeamInfos.teamName
        options.blueTeamPicture = firstTeamInfos.teamPicturePath
        options.redTeamName = secondTeamInfos.teamName
        options.redTeamPicture = secondTeamInfos.teamPicturePath
        if (buzzer != null && buzzersSongPath[buzzer] != null) {
            options.chosenBuzz = buzzersSongPath[buzzer].toString()
        }
        repo.saveOptions(options)
    }
}