package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.camera.CameraFragment
import com.ikemura.android_kotlin_lab.dialog.datepickr.dialogfragment.DialogRootFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.commit(false) {
            replace(R.id.container, DialogRootFragment.newInstance())
        }
    }
}
