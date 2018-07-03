package com.dreamonjava.mvpplayground.domain.repository

import java.lang.reflect.Type

interface DiskStorage {

    fun containsObject(id: String): Boolean

    fun storeObjectAsJson(id: String, value: Any)

    fun <TData> restoreObjectFromJson(id: String, type: Class<TData>): TData

    fun <TData> restoreObjectFromJson(id: String, type: Type): TData

    fun removeObject(id: String)
}
