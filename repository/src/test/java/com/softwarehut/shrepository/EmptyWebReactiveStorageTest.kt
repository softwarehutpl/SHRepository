package com.softwarehut.shrepository

import com.softwarehut.shrepository.domain.storage.EmptyWebReactiveStorage
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EmptyWebReactiveStorageTest {

    private lateinit var testData: TestData

    private lateinit var storage: EmptyWebReactiveStorage<TestData>

    @Before
    fun beforeTest() {
        testData = createZeroIdTestData()
        storage = EmptyWebReactiveStorage()
    }

    @Test
    fun readStream_withZeroId_doesNotReturnAnyItems() {
        storage.readStream(testData.id.toString()).subscribe(object : Observer<TestData> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: TestData) {
                Assert.fail()
            }

            override fun onError(e: Throwable) {
                Assert.fail()
            }

        })
    }

    @Test
    fun readStream_withZeroId_doesNotThrow() {
        val storage = EmptyWebReactiveStorage<TestData>()
        try {
            storage.readStream(testData.id.toString())
        } catch (ex: Exception) {
            Assert.fail()
        }
    }

    private fun createZeroIdTestData() = TestData(0)

    data class TestData(val id: Long)
}