package ru.astrainteractive.astralibs.database_v2

import kotlin.reflect.KProperty

abstract class Entity<T>(val table: Table<T>) {
    val writeValues = LinkedHashMap<Column<Any?>, Any?>()

    operator fun <K> Column<K>.getValue(o: Entity<T>, desc: KProperty<*>): K {
        return writeValues[this as Column<Any?>] as K
    }

    operator fun <K> Column<K>.setValue(o: Entity<T>, desc: KProperty<*>, value: K) {
        writeValues[this as Column<Any?>] = value
    }

    operator fun set(column: Column<Any?>, value: Any?) {
        writeValues[column] = value
    }
}