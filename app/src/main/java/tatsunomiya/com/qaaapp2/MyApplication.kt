package tatsunomiya.com.qaaapp2

import android.app.Application

class MyApplication : Application() {


    companion object {
        lateinit var instance: Application private set  // <- これ
    }

    override fun onCreate() {
        super.onCreate()

        instance = this  // <- これ
    }
}
