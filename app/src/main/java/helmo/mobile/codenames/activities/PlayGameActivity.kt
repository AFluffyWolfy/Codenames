package helmo.mobile.codenames.activities

import android.content.Intent
import android.media.MediaPlayer
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import helmo.mobile.codenames.R
import helmo.mobile.codenames.adapters.CardListAdapter
import helmo.mobile.codenames.adapters.viewHolders.CardListItemDecoration
import helmo.mobile.codenames.contracts.BuzzerPlayer
import helmo.mobile.codenames.contracts.PlayContract
import helmo.mobile.codenames.databinding.ActivityPlayGameBinding
import helmo.mobile.codenames.model.CardColor
import helmo.mobile.codenames.presenters.CardViewInfo
import helmo.mobile.codenames.presenters.PlayGamePresenter
import helmo.mobile.codenames.repository.GameRepository
import helmo.mobile.codenames.repository.OptionsRepository
import java.io.File

class PlayGameActivity : AppCompatActivity(), PlayContract.View, BuzzerPlayer {
    private lateinit var binding: ActivityPlayGameBinding
    private lateinit var presenter: PlayContract.Presenter
    private var idBuzzerResources: Int = -1
    private var currentTeam = CardColor.BLUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayGameBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initializeComponent()

        setPresenter(PlayGamePresenter(this,
            GameRepository(filesDir.absolutePath, "save.json"),
            OptionsRepository(getSharedPreferences("config", MODE_PRIVATE))
        ))
        binding.plateDisplay.addItemDecoration(CardListItemDecoration(50))
        this.presenter.onViewCreated()
    }

    private fun initializeComponent() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.choice_number, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nbWordChoices.adapter = adapter
        binding.homeBtn.setOnClickListener {
            presenter.onGoHome()
        }

        binding.skipTurn.setOnClickListener {
            presenter.skipTurnRequested()
        }

        binding.spyBtn.setOnClickListener {
            presenter.onSpyViewClicked()
        }

        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#3a8ee8")
    }

    override fun displayCards(cards: List<CardViewInfo>) {
        binding.plateDisplay.adapter = null
        binding.plateDisplay.adapter = CardListAdapter(presenter, cards, this)
    }

    override fun setTeamsName(blueTeam: String, redTeam: String) {
        binding.blueTeamName.text = blueTeam
        binding.redTeamName.text = redTeam
    }

    override fun setTeamsPicture(blueTeam: String, redTeam: String) {
        binding.blueTeamPicture.setImageURI(Uri.parse(blueTeam))
        binding.redTeamPicture.setImageURI(Uri.parse(redTeam))
    }

    override fun setCurrentTeam(color: String) {
        if (color.uppercase() == "BLUE") {
            binding.currentTeam.setBackgroundColor(Color.parseColor("#3a8ee8"))
            window.statusBarColor = Color.parseColor("#3a8ee8")
            currentTeam = CardColor.BLUE
        } else {
            binding.currentTeam.setBackgroundColor(Color.parseColor("#db3543"))
            window.statusBarColor = Color.parseColor("#db3543")
            currentTeam = CardColor.RED
        }
    }

    override fun setBuzzerSound(idResource: Int) {
        this.idBuzzerResources = idResource
    }

    override fun gameOver(winnerPath: String, winnerName: String) {
        intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra("winnerPath", winnerPath)
        intent.putExtra("winnerName", winnerName)
        startActivity(intent)
        finish()
    }

    override fun changeCurrentTeam() {
        if (currentTeam == CardColor.BLUE) {
            binding.currentTeam.setBackgroundColor(Color.parseColor("#db3543"))
            window.statusBarColor = Color.parseColor("#db3543")
            currentTeam = CardColor.RED
        } else {
            binding.currentTeam.setBackgroundColor(Color.parseColor("#3a8ee8"))
            window.statusBarColor = Color.parseColor("#3a8ee8")
            currentTeam = CardColor.BLUE
        }
        binding.nbWordChoices.setSelection(0)
    }

    override fun setScore(blueScore: Int, redScore: Int) {
        binding.blueScore.text = String.format("%d/8", blueScore)
        binding.redScore.text = String.format("%d/7", redScore)
    }

    override fun warnUserSpyView() {
        Toast.makeText(this, "SEUL L'ESPION PEUT VOIR CETTE VUE !!! Cliquer à nouveau si vous êtes bien l'espion !", Toast.LENGTH_LONG).show()
    }

    override fun setPresenter(presenter: PlayContract.Presenter) {
        this.presenter = presenter
    }

    override fun goHome() {
        presenter.onViewLeave()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun goBack() {}

    override fun onPause() {
        super.onPause()

        presenter.saveGame()
    }

    override fun playBuzzzer() {
        if(idBuzzerResources > -1) {
            val mp: MediaPlayer = MediaPlayer.create(this, idBuzzerResources)
            mp.start()
            mp.setOnCompletionListener {
                mp.reset()
                mp.release()
            }
        }
    }
}