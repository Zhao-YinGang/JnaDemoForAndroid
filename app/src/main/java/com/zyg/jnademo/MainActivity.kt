package com.zyg.jnademo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zyg.jnademo.jna.java.JnaTest
import com.zyg.jnademo.jna.kotlin.jnaTest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_janTest.setOnClickListener {
            lifecycleScope.launch {
                try {
                    jnaTest()
                    withContext(Dispatchers.IO) {
                        JnaTest.doJnaTest()
                    }
                    tv_jnaTestResult.setText(R.string.jna_test_succeed)
                } catch (e: AssertionError) {
                    Log.e(TAG, "test failed: $it", e)
                    tv_jnaTestResult.setText(R.string.jna_test_failed)
                }
            }
        }
    }
}

