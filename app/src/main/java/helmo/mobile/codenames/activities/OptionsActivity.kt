package helmo.mobile.codenames.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import helmo.mobile.codenames.R
import helmo.mobile.codenames.contracts.OptionsContract
import helmo.mobile.codenames.databinding.ActivityOptionsBinding
import helmo.mobile.codenames.presenters.OptionsPresenter
import helmo.mobile.codenames.presenters.TeamOptionsArgs
import helmo.mobile.codenames.repository.OptionsRepository
import helmo.mobile.codenames.utils.FileNotSaveException
import helmo.mobile.codenames.utils.FileUtils
import java.io.File

class OptionsActivity : AppCompatActivity(), OptionsContract.View {

    private lateinit var presenter: OptionsContract.Presenter
    private lateinit var confirmBtn: Button

    private lateinit var firstTeamFragment: TeamOptionItemFragment
    private lateinit var secondTeamFragment: TeamOptionItemFragment

    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Toute Activité/Fragment possède un Binding
        // Pour le récupérer il suffit de prendre le nom de la classe, de mettre activity/fragment au début,
        // bing a la fin de la classe associées. (Ex: OptionsActivity ==> ActivityOptionsBinding)
        binding = ActivityOptionsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Ici on empêche le clavier de s'afficher automatiquement lors de l'ouverture de l'activité
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        // Phase d'initialisation des composants
        initializeComponent()

        // La vue/activité crée son presenter et lui donnes les arguments nécessaires
        // Ici elle lui donne son repo
        setPresenter(presenter = OptionsPresenter(this, OptionsRepository(getSharedPreferences("config", MODE_PRIVATE))))
    }

    /**
     * Initialise les composants de l'activité.
     */
    private fun initializeComponent() {
        setStatusBarColor()

        confirmBtn = findViewById(R.id.confirm_option_button)
        confirmBtn.setOnClickListener {
            // La sauvegarde des images est réalisée ici, car les presenter n'ont pas à connaitre le contexte de l'activité (c'est contraire au MVP)
            try {
                val firstTeamInfos = firstTeamFragment.teamInfos
                val secondTeamInfos = secondTeamFragment.teamInfos
                firstTeamInfos.teamPicturePath = FileUtils.savePhoto(File(firstTeamInfos.teamPicturePath), contentResolver) ?: ""
                secondTeamInfos.teamPicturePath = FileUtils.savePhoto(File(secondTeamInfos.teamPicturePath), contentResolver) ?: ""
                presenter.onSaveOption(
                    firstTeamInfos,
                    secondTeamInfos,
                    binding.spinnerBuzzerSound.selectedItem.toString()
                )
                displayMessage("Options successfully saved")
            } catch (e: FileNotSaveException) {
                displayMessage("An error has occur while the saving process")
            }
        }

        binding.backButton.setOnClickListener {
            goBack()
        }

    }

    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#8f776b")
    }

    override fun displayNameChanges() {

    }

    override fun setTeamsInfos(firstTeamInfos: TeamOptionsArgs, secondTeamInfos: TeamOptionsArgs) {
        firstTeamFragment = TeamOptionItemFragment.newInstance(firstTeamInfos)
        secondTeamFragment = TeamOptionItemFragment.newInstance(secondTeamInfos)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.first_team_view, firstTeamFragment)
            .replace(R.id.second_team_view, secondTeamFragment)
            .commit()
    }

    override fun setBuzzers(buzzers: List<String>, selectedBuzzer: String) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, buzzers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBuzzerSound.adapter = adapter
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: OptionsContract.Presenter) {
        this.presenter = presenter
        // On notifie le presenter que sa vue est crée
        presenter.onViewCreated()
    }

    override fun goHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun goBack() {
        finish()
    }

}