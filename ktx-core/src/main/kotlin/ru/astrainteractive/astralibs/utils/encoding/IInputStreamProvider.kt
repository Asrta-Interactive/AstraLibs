package ru.astrainteractive.astralibs.utils.encoding

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

interface IInputStreamProvider {
    fun provide(istream: ByteArrayInputStream): ObjectInputStream
}