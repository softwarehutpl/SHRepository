package com.softwarehut.shrepository.domain.storage.disk

interface DiskSaver<TData> {
    fun save(id: String, data: TData)
}