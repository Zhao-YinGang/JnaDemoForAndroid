package com.zyg.jna

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

@FieldOrder("name", "height", "weight")
open class User(
    @JvmField var name: String?,
    @JvmField var height: Int,
    @JvmField var weight: Double
) : Structure(), Structure.ByReference

class ByValue(
    name: String?,
    height: Int,
    weight: Double
) : User(name, height, weight), Structure.ByValue