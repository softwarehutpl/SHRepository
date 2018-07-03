package com.softwarehut.shrepository.domain.sources

import com.softwarehut.shrepository.domain.storage.disk.DiskSaver
import io.reactivex.Observable
import io.reactivex.Single

class Sources<TData>(val onDiskSave: DiskSaver<TData>,
                     val diskStorageReadStream: Observable<TData>,
                     val networkReadStream: Observable<TData>) {

    private fun disk(id: String): Observable<TData> {
        return diskStorageReadStream.onErrorResumeNext(network(id))
    }

    private fun network(id: String): Observable<TData> {
        val observable = networkReadStream

        return observable.doOnNext({ data ->
            store(id, data)
        })
    }

    fun all(id: String): Single<TData> {
        return Observable.concat(disk(id), network(id)).firstOrError()
    }

    fun store(id: String, data: TData) {
        onDiskSave.save(id, data)
    }
}