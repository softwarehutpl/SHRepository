package com.softwarehut.shrepository.domain.repository

import io.reactivex.Observable
import io.reactivex.Single

interface Repository<TData> {

    fun request(id: String, type: Class<TData>): Single<TData>

    fun store(id: String, data: TData, type: Class<TData>)

    fun observe(id: String, type: Class<TData>): Observable<TData>

    fun exists(id: String): Single<Boolean>
}