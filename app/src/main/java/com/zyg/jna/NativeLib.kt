package com.zyg.jna

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

// https://github.com/java-native-access/jna/releases

interface NativeLib : Library {
    companion object {
        val INSTANCE = Native.load("native_lib", NativeLib::class.java) as NativeLib
    }

    fun getInt(): Int

    fun getString(str: Pointer, len: Long)
    fun getString(str: String, len: Long)

    fun getString2(): Pointer

    fun testUser(user: User)

    fun testUserPointer(user: User)

    fun testUserPointer2(user: User?)
}

