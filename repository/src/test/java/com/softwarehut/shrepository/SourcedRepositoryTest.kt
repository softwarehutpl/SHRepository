package com.softwarehut.shrepository

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.softwarehut.shrepository.domain.repository.SourcedRepository
import com.softwarehut.shrepository.domain.storage.ReactiveStorage
import com.softwarehut.shrepository.domain.storage.WebReactiveStorage
import com.softwarehut.shrepository.domain.storage.disk.DiskSaver
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SourcedRepositoryTest {

    private lateinit var testData: TestData

    @Before
    fun beforeTest() {
        testData = createZeroIdTestData()
    }

    @Test
    fun observe_withZeroId_doesNotThrow() {
        val diskStorage: ReactiveStorage<TestData> = mockDiskStorage()
        val networkStorage: WebReactiveStorage<TestData> = mockNetworkStorage()
        val sourcedRepository = SourcedRepository(diskStorage, networkStorage)

        try {
            sourcedRepository.observe(testData.id.toString(), TestData::class.java)
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun exists_withZeroId_doesNotThrow() {
        val diskStorage: ReactiveStorage<TestData> = mockDiskStorage()
        val networkStorage: WebReactiveStorage<TestData> = mockNetworkStorage()
        val sourcedRepository = SourcedRepository(diskStorage, networkStorage)

        try {
            sourcedRepository.exists(testData.id.toString())
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun store_withZeroId_doesNotThrow() {
        val diskSaver: DiskSaver<TestData> = mock()
        val diskStorage: ReactiveStorage<TestData> = mock {
            on { diskSaver() } doReturn diskSaver
        }
        val networkStorage: WebReactiveStorage<TestData> = mockNetworkStorage()
        val sourcedRepository = SourcedRepository(diskStorage, networkStorage)

        try {
            sourcedRepository.store(testData.id.toString(), testData, testData.javaClass)
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun request_withZeroId_doesNotThrow() {
        val diskSaver: DiskSaver<TestData> = mock()
        val readStreamObservable: Observable<TestData> = mock()
        val diskStorage: ReactiveStorage<TestData> = mock {
            on { diskSaver() } doReturn (diskSaver)
            on { readStream(testData.id.toString(), TestData::class.java) } doReturn (readStreamObservable)
        }
        val sourcedRepository = SourcedRepository(diskStorage)
        sourcedRepository.request(testData.id.toString(), testData.javaClass)
    }

    private fun createZeroIdTestData() = TestData(0)

    private fun mockNetworkStorage(): WebReactiveStorage<TestData> =
            mock()

    private fun mockDiskStorage(): ReactiveStorage<TestData> =
            mock()

    data class TestData(val id: Long)
}