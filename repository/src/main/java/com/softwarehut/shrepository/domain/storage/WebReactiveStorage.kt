package com.softwarehut.shrepository.domain.storage

import io.reactivex.Observable

interface WebReactiveStorage<TData> {

    fun readStream(id: String): Observable<TData>
}