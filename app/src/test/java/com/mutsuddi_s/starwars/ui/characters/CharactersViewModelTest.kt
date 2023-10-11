package com.mutsuddi_s.starwars.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.mutsuddi_s.starwars.data.adapters.CharactersAdapter
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import com.mutsuddi_s.starwars.ui.characterdetails.util.CharacterPagingSource
import com.mutsuddi_s.starwars.ui.characterdetails.util.DataDummy
import com.mutsuddi_s.starwars.ui.characterdetails.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalPagingApi::class)
class CharactersViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: CharactersViewModel


    @Mock
    lateinit var repository: StarWarsRepository


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CharactersViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacters()= runTest {
        val searchString = "Luke"
        // Mock the behavior of the repository
       /* val dummyCharacter=DataDummy.generateDummyCharacterItemEntity()
        val data = CharacterPagingSource.snapshot(dummyCharacter)
        val expectedResult = MutableLiveData<PagingData<Character>>()
        expectedResult.value = data
        `when`(repository.getCharacters(searchString)).thenReturn(expectedResult)

        // When calling the getCharacters function
        val result = viewModel.getCharacters(searchString).getOrAwaitValue()

        testDispatcher.scheduler.advanceUntilIdle()

        val differ = AsyncPagingDataDiffer(
            diffCallback = CharactersAdapter.CHARACTER_COMPARATOR,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(result)
        assertNotNull(differ.snapshot())
        assertEquals(dummyCharacter, differ.snapshot())
        assertEquals(dummyCharacter.size, differ.snapshot().size)
        assertEquals(dummyCharacter[0].name, differ.snapshot()[0]?.name)*/


    }










    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }


}