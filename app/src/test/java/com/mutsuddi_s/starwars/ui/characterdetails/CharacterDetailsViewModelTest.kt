package com.mutsuddi_s.starwars.ui.characterdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import com.mutsuddi_s.starwars.data.model.people.FilmResponse
import com.mutsuddi_s.starwars.data.model.people.HomeWorldResponse
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import com.mutsuddi_s.starwars.ui.characterdetails.util.DataDummy
import com.mutsuddi_s.starwars.ui.characterdetails.util.getOrAwaitValue
import com.mutsuddi_s.starwars.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalPagingApi::class)
class CharacterDetailsViewModelTest {


    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: CharacterDetailsViewModel


    @Mock
    lateinit var repository: StarWarsRepository



    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterDetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun `fetchHomeWorld should update live data with result`() = runTest {

        // Arrange
        val homeWorldResponse = HomeWorldResponse("Earth")
        val expectedResponse = Resource.Success(homeWorldResponse)
        val testUrl = "https://swapi.dev/api/planets/1"

        `when`(repository.getHomeWorld(testUrl)).thenReturn(expectedResponse)

        // Act
        viewModel.fetchHomeWorld(testUrl)
        testDispatcher.scheduler.advanceUntilIdle()
        val result=viewModel.homeWorldLiveData.getOrAwaitValue()


        // Assert
        Assert.assertEquals(expectedResponse,result)
    }


    @Test
    fun `fetchFilms should update live data with result`() = runTest{

        val testUrls = listOf("https://swapi.dev/api/films/1","https://swapi.dev/api/films/1")
        val expectedResponse=Resource.Success(DataDummy.generateDummyFilmsItemEntity())
        `when`(repository.getFilms(testUrls)).thenReturn(expectedResponse)


       // Act
        viewModel.fetchFilms(testUrls)
        testDispatcher.scheduler.advanceUntilIdle()
        val result=viewModel.films.getOrAwaitValue()

        // Assert
        Assert.assertEquals(expectedResponse,result)



    }

    @Test
    fun `fetchSpecies should update live data with result`() = runTest{

        val testUrls = listOf("https://swapi.dev/api/species/1","https://swapi.dev/api/species/2")
        val expectedResponse=Resource.Success(DataDummy.generateDummySpeciesItemEntity())
        `when`(repository.getSpecies(testUrls)).thenReturn(expectedResponse)


        // Act
        viewModel.fetchSpecies(testUrls)
        testDispatcher.scheduler.advanceUntilIdle()
        val result=viewModel.species.getOrAwaitValue()

        // Assert
        Assert.assertEquals(expectedResponse,result)



    }


}