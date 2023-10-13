package helmo.mobile.codenames.presenters

import android.content.Intent
import android.os.Bundle
import helmo.mobile.codenames.activities.OptionsActivity
import helmo.mobile.codenames.contracts.NewGameContract
import helmo.mobile.codenames.contracts.OptionsContract
import helmo.mobile.codenames.R
import helmo.mobile.codenames.infrastructures.IOptionRepository
import helmo.mobile.codenames.repository.Repository
import helmo.mobile.codenames.repository.WordListRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import java.io.InputStream
import java.security.cert.PKIXRevocationChecker.Option

class NewGamePresenterTests {

    private lateinit var mockedView: OptionsContract.View
    private lateinit var mockedPresenter: OptionsContract.Presenter

    @Before
    fun setUp() {
        mockedView = mock(OptionsContract.View::class.java)
        mockedPresenter = OptionsPresenter(mockedView,
            FakeOptionRepository())
    }
    
    @Test
    fun whenViewCreatedThenNotifyPresenter() {
        mockedPresenter.onViewCreated()
        verify(mockedView).setBuzzers(listOf("ding", "twinkle", "blink"), "" + R.raw.ding)
        verify(mockedView).setTeamsInfos(
            TeamOptionsArgs("Equipe 1", ""),
            TeamOptionsArgs("Equipe 2", "")
        )
    }

    @Test
    fun whenPresenterGoBackThenChangeActivitiy() {
        mockedPresenter.onGoBack()
        verify(mockedView).goBack()
    }

}