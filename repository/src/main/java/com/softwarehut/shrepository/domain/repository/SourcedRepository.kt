package com.softwarehut.shrepository.domain.repository

import com.softwarehut.shrepository.domain.sources.Sources
import com.softwarehut.shrepository.domain.storage.EmptyWebReactiveStorage
import com.softwarehut.shrepository.domain.storage.ReactiveStorage
import com.softwarehut.shrepository.domain.storage.WebReactiveStorage
import io.reactivex.Observable
import io.reactivex.Single

open class SourcedRepository<TData>(private val diskStorage: ReactiveStorage<TData>,
                                    private val networkStorage: WebReactiveStorage<TData>) : Repository<TData> {

    constructor(diskStorage: ReactiveStorage<TData>) : this(diskStorage, EmptyWebReactiveStorage())

    override fun request(id: String, type: Class<TData>): Single<TData> {
        val onDiskSave = diskStorage.diskSaver()
        val diskStorageReadStream = diskStorage.readStream(id, type)
        val networkReadStream = networkStorage.readStream(id)
        val sources = Sources(onDiskSave,
                diskStorageReadStream,
                networkReadStream)
        return sources.all(id)
    }

    override fun store(id: String, data: TData, type: Class<TData>) = diskStorage.diskSaver().save(id, data)

    override fun observe(id: String, type: Class<TData>): Observable<TData> = diskStorage.changes(id)

    override fun exists(id: String): Single<Boolean> = diskStorage.exists(id)
}