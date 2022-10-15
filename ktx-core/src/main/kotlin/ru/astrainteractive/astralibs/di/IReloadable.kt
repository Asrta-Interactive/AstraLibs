package ru.astrainteractive.astralibs.di

/**
 * [IReloadable] can be used to create reloadable singletons with kotlin object
 * If you want to create non-reloadable singleton - see [IModule]
 */
abstract class IReloadable<T> {
    abstract fun initializer(): T
    var value: T = initializer()
        private set

    fun reload() {
        value = initializer()
    }

}