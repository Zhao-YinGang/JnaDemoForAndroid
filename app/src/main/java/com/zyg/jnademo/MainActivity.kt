package com.zyg.jnademo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sun.jna.Memory
import com.zyg.jna.ByValue
import com.zyg.jna.NativeLib
import com.zyg.jna.User
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sample_text.text = NativeLib.INSTANCE.getInt().toString()

        val pStr = Memory(12)
        NativeLib.INSTANCE.getString(pStr, pStr.size())
        val str = pStr.getString(0)
        Log.i(TAG, "getString: $str")

        val pStr3 = NativeLib.INSTANCE.getString2()
        val str3 = pStr3.getString(0)
        Log.i(TAG, "getString2: $str3")

        val userByValue = ByValue("Job by value", 10, 11.2)
        NativeLib.INSTANCE.testUser(userByValue)
        Log.i(TAG, "testUser: user ByValue: ${userByValue.name}")

        NativeLib.INSTANCE.testUserPointer(userByValue)
        Log.i(TAG, "testUserPointer: user ByValue: ${userByValue.name}")

        val user = User("Job by reference",10,  11.2)
        NativeLib.INSTANCE.testUserPointer(user)
        Log.i(TAG, "testUserPointer: user ByReference: ${user.name}")

        val user2: User? = null
        NativeLib.INSTANCE.testUserPointer2(user2)
        Log.i(TAG, "testUserPointer2: user2 ByReference: ${user2?.name} ${user2?.height}")
    }
}

