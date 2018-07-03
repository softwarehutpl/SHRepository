package com.softwarehut.shrepository.domain.storage

import com.softwarehut.shrepository.domain.storage.disk.DiskSaver
import io.reactivex.Observable
import io.reactivex.Single

interface ReactiveStorage<TData> {

    fun changes(id: String): Observable<TData>

    fun diskSaver(): DiskSaver<TData>

    fun readStream(id: String, type: Class<TData>): Observable<TData>

    fun exists(id: String): Single<Boolean>
}