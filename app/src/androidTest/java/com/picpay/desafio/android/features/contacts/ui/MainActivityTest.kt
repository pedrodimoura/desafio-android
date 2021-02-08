package com.picpay.desafio.android.features.contacts.ui

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.data.datasource.remote.adapter.NetworkResponseCallFactory
import com.picpay.desafio.android.common.di.DEFAULT_CLIENT
import com.picpay.desafio.android.common.di.DEFAULT_GSON
import com.picpay.desafio.android.common.di.DEFAULT_RETROFIT
import com.picpay.desafio.android.features.contacts.ui.activity.MainActivity
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(AndroidJUnit4ClassRunner::class)
@KoinApiExtension
class MainActivityTest : KoinComponent {

    private val mockWebServer = MockWebServer()
    private val url = mockWebServer.url("/")
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        val networkModuleTest = module {
            single(named(DEFAULT_CLIENT), override = true) { OkHttpClient.Builder().build() }
            single(named(DEFAULT_RETROFIT), override = true) {
                Retrofit.Builder()
                    .client(get(named(DEFAULT_CLIENT)))
                    .baseUrl(url)
                    .addCallAdapterFactory(NetworkResponseCallFactory())
                    .addConverterFactory(GsonConverterFactory.create(get(named(DEFAULT_GSON))))
                    .build()
            }
        }
        getKoin().loadModules(listOf(networkModuleTest))
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)
            moveToState(Lifecycle.State.RESUMED)
            onView(withText(expectedTitle)).check(matches(isDisplayed()))
            close()
        }

    }

    @Test
    fun shouldDisplayListItem() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.recyclerView)).check(matches(isCompletelyDisplayed()))
            onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
                if (noViewFoundException != null)
                    throw noViewFoundException

                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                assertThat(adapter?.itemCount, Matchers.`is`(2))
            }
            close()
        }
    }

    @Test
    fun shouldHideContentAndShowError() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> errorResponse
                    else -> errorResponse
                }
            }
        }

        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
                if (noViewFoundException != null)
                    throw noViewFoundException

                val recyclerView = view as RecyclerView
                val visibility = recyclerView.visibility
                assertThat(visibility, Matchers.`is`(View.GONE))
            }
            close()
        }
    }

    companion object {

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":null,\"username\":\"@eduardo.santos\"}," +
                        "{\"id\":1002,\"name\":\"Pedro Moura\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@pedro.moura\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}