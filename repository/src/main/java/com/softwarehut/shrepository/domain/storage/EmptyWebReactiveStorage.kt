package com.softwarehut.shrepository.domain.storage

import io.reactivex.Observable

class EmptyWebReactiveStorage<TData> : WebReactiveStorage<TData> {
    override fun readStream(id: String): Observable<TData> = Observable.empty()
}
