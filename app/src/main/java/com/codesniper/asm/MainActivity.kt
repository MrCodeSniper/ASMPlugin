package com.codesniper.asm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View

import com.codesniper.gradle_interface.MethodTracer

class MainActivity : AppCompatActivity() {

    private val objList = ArrayList<Any>()
    private val TAG = javaClass.simpleName
    private val PERMISSIONS_STORAGE =
            arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

    private var rabbitMethodMonitor: RabbitMethodMonitor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        findViewById<View>(R.id.tv_open).setOnClickListener {
            RabbitLog.d("开启函数监听器")
            rabbitMethodMonitor = RabbitMethodMonitor()
            rabbitMethodMonitor!!.open(this@MainActivity)
        }
        findViewById<View>(R.id.tv_test).setOnClickListener { test() }
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            }
        }
    }


    fun test() {
        Log.d("chenhong", "測試")
        //        MethodTracer.recordMethodStart();
        //        MethodTracer.recordMethodEnd("com.codesniper.asm.MainActivity&test");
    }
}
