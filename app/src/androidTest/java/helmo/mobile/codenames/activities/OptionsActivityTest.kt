package helmo.mobile.codenames.activities

import android.content.Context.MODE_PRIVATE
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import helmo.mobile.codenames.R
import helmo.mobile.codenames.contracts.OptionsContract
import helmo.mobile.codenames.infrastructures.IOptionRepository
import helmo.mobile.codenames.model.Options
import helmo.mobile.codenames.presenters.OptionsPresenter
import helmo.mobile.codenames.repository.OptionsRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.*

class OptionsActivityTest {

    @Mock
    private lateinit var mockedPresenter: OptionsContract.Presenter

    private lateinit var activityScenario: ActivityScenario<OptionsActivity>
    private lateinit var presenter: OptionsContract.Presenter
    private lateinit var repo: IOptionRepository
    private lateinit var options: Options

    @Before
    fun setUp() {
//        mockedPresenter = mock(OptionsContract.Presenter::class.java)
        activityScenario = launch(OptionsActivity::class.java)
        repo = OptionsRepository(SPMockBuilder().createContext().getSharedPreferences("test", MODE_PRIVATE))
        repo = FakeOptionRepository()
        activityScenario.onActivity {
            it.setPresenter(OptionsPresenter(it, repo))
        }
    }

    @After
    fun tearDown() {
//        activityScenario.close()
    }

//    @Test
//    fun aTest() {
//        activityScenario.onActivity {
//            it.setPresenter(mockedPresenter)
//        }
//        onView(withId(R.id.confirm_option_button)).perform(click())
//        verify(mockedPresenter).onSaveOption(any(), any(), any())
//    }

    @Test
    fun whenChangeNameAndRatatePhoneThenNothingHasBeenSaved() {
        var of: Fragment? = null
        activityScenario.onActivity {
            of = it.supportFragmentManager.findFragmentById(R.id.first_team_view)

            assertNotNull(of)

            val et = of!!.view!!.findViewById<EditText>(R.id.team_name)

            et.setText("coucou")
        }

        onView(withId(R.id.confirm_option_button)).perform(click());

        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Verify that the fragment has the correct text
        assertEquals("coucou", of!!.view!!.findViewById<EditText>(R.id.team_name).text.toString());
        assertEquals("coucou", repo.getOptions().blueTeamName)
    }

}