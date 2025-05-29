package aps.fithom.startandroidapp.data.di

interface Factory<T> {
    fun create():T
}