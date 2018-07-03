package com.softwarehut.shrepository

import com.nhaarman.mockito_kotlin.mock
import com.softwarehut.shrepository.domain.sources.Sources
import com.softwarehut.shrepository.domain.storage.disk.DiskSaver
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SourcesTest {

    private lateinit var testData: TestData

    @Before
    fun beforeTest() {
        testData = createZeroIdTestData()
    }

    @Test
    fun store_withZeroId_DoesNotThrow() {
        val sources: Sources<TestData> = createSources()
        try {
            sources.store(testData.id.toString(), testData)
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun all_withZeroId_DoesNotThrow() {
        val sources: Sources<TestData> = createSources()
        try {
            sources.all(testData.id.toString())
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun all_withZeroIdAndDiskAndNetworkValues_ReturnsDiskValue() {
        val diskSaver: DiskSaver<String> = mock()
        val diskValue = "diskValue"
        val diskStorageReadStream: Observable<String> = Observable.just(diskValue)
        val networkValue = "networkValue"
        val networkReadStream: Observable<String> = Observable.just(networkValue)
        val sources = Sources(diskSaver, diskStorageReadStream, networkReadStream)
        val observable = sources.all(testData.id.toString())
        observable.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String) {
                if (t != diskValue) {
                    Assert.fail()
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Assert.fail()
            }

        })
    }

    @Test
    fun all_withZeroIdAndWithNetworkValue_ReturnsNetworkValue() {
        val diskSaver: DiskSaver<String> = mock()
        val diskStorageReadStream: Observable<String> = Observable.empty()
        val networkValue = "networkValue"
        val networkReadStream: Observable<String> = Observable.just(networkValue)
        val sources = Sources(diskSaver, diskStorageReadStream, networkReadStream)
        val observable = sources.all(testData.id.toString())
        observable.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String) {
                if (t != networkValue) {
                    Assert.fail()
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Assert.fail()
            }
        })
    }

    @Test
    fun all_withZeroIdWithoutValues_ReturnsError() {
        val diskSaver: DiskSaver<String> = mock()
        val diskStorageReadStream: Observable<String> = Observable.empty()
        val networkReadStream: Observable<String> = Observable.empty()
        val sources = Sources(diskSaver, diskStorageReadStream, networkReadStream)
        val observable = sources.all(testData.id.toString())
        observable.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String) {
                Assert.fail()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    private fun createSources(): Sources<TestData> {
        val diskSaver: DiskSaver<TestData> = mock()
        val diskStorageReadStream: Observable<TestData> = mock()
        val networkReadStream: Observable<TestData> = mock()
        return Sources(diskSaver, diskStorageReadStream, networkReadStream)
    }

    private fun createZeroIdTestData() = TestData(0)

    data class TestData(val id: Long)
}