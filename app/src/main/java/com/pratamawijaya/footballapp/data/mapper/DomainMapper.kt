package com.pratamawijaya.footballapp.data.mapper

interface DomainMapper<D, M> {
    fun mapFromModel(model: M): D
    fun mapToModel(domain: D): M
}