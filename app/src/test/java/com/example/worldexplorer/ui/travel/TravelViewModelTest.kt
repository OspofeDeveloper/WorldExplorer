package com.example.worldexplorer.ui.travel

import com.example.worldexplorer.domain.usecases.travel.GetRandomCca2UseCase
import com.example.worldexplorer.motherobject.WorldExplorerMotherObject
import io.kotlintest.runner.console.WordSpecStyleParser
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TravelViewModelTest {

    @Mock
    private lateinit var mockGetRandomCca2UseCase: GetRandomCca2UseCase

    private lateinit var viewModel: TravelViewModel

    @Before
    fun setup() {
        /** Inicializar mock */
        MockitoAnnotations.openMocks(this)

        /** Configurar comportamiento del mock */
        runBlocking {
            `when`(mockGetRandomCca2UseCase.invoke()).thenReturn(WorldExplorerMotherObject.travelModel)
        }

        viewModel = TravelViewModel(mockGetRandomCca2UseCase)
    }

    @Test
    fun `restartViewModel() should set ViewModel state to Resource Loading`() {
        /** TODO **/
    }
}