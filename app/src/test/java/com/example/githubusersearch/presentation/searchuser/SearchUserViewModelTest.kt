package com.example.githubusersearch.presentation.searchuser

import android.content.Context
import com.example.githubusersearch.R
import com.example.githubusersearch.business.interactors.usecase.SearchUserUsecase
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.common.util.ResourcesProvider
import com.example.githubusersearch.common.util.ResourcesProviderImpl
import com.example.githubusersearch.framework.datasource.network.mappers.UserDefaultInfoMapper
import com.example.githubusersearch.framework.datasource.network.service.GithubRetrofitService
import com.example.githubusersearch.framework.presentation.searchuser.SearchUserViewModel
import com.example.githubusersearch.network.data.MockGithubResponses
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchUserViewModelTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    private lateinit var searchUserUsecase: SearchUserUsecase

    private lateinit var githubRetrofitService: GithubRetrofitService
    private val userDefaultInfoMapper = UserDefaultInfoMapper()

    private lateinit var resourcesProvider: ResourcesProvider
    private lateinit var context: Context
    private lateinit var viewModel: SearchUserViewModel

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/search/users/")

        githubRetrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(GithubRetrofitService::class.java)

        searchUserUsecase = SearchUserUsecase(
            githubRetrofitService = githubRetrofitService,
            userDefaultInfoMapper = userDefaultInfoMapper
        )
        context = mock {
            on { getString(R.string.search_result) } doReturn "DUMMY"
            on { getString(R.string.no_search_result) } doReturn "DUMMY"
        }
        resourcesProvider = ResourcesProviderImpl(context)
        viewModel = SearchUserViewModel(SearchUserInteractors(searchUserUsecase), resourcesProvider)
    }

    @Test
    fun getUsersFromNetwork_updateUserList() = runBlocking {
        // api 호출 이전 유저 리스트
        val originList = viewModel.users.toList()

        // API 성공적 호출
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockGithubResponses.userListResponse)
        )
        val query = "DUMMY"
        viewModel.searchUsers(query, "", "", 1)

        // 유저리스트가 업데이트 된 것을 확인
        assert(viewModel.users != originList)
    }

    @Test
    fun getUsersFromNetwork_offerDialogQueueErrorMessage() = runBlocking {
        // dialogQueue 가 비워진 것을 확인
        assert(viewModel.dialogQueue.getQueueSize() == 0)

        // API 호출 실패
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )
        val query = "DUMMY"
        viewModel.searchUsers(query, "", "", 1)

        // dialogQueue 에 새로운 큐가 들어간 것을 확인
        assert(viewModel.dialogQueue.getQueueSize() > 0)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}