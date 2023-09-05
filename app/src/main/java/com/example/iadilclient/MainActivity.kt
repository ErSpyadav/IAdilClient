package com.example.iadilclient

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aidlserver.IAidlPhoneInService

class MainActivity : AppCompatActivity() {
    var showResult :Button? = null;

    var resultTv :TextView? = null;
    val ACTION = "AIDLPhoneInService"
    val PACKAGE ="com.example.aidlserver"
    val CLASS = "com.example.aidlserver.AidlPhoneInServerService"
     var iAidlPhoneInService : IAidlPhoneInService? = null


     val serviceConnection = object :ServiceConnection{
         override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        iAidlPhoneInService = IAidlPhoneInService.Stub.asInterface(p1)
         }

         override fun onServiceDisconnected(p0: ComponentName?) {
         }

     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(ACTION)
        intent.setComponent(ComponentName(PACKAGE,CLASS))
        bindService(intent,serviceConnection, BIND_AUTO_CREATE)

         showResult = findViewById(R.id.showResultBtn) as Button
        resultTv = findViewById(R.id.resultTv)


        showResult?.setOnClickListener {
            if(iAidlPhoneInService != null){
                val callDetail = iAidlPhoneInService!!.callDetai
                val contact = iAidlPhoneInService!!.mobile
                resultTv?.setText("CallDetail:${callDetail} \n Contact:${contact}")
            }
            Toast.makeText(this.applicationContext,"Clicked",Toast.LENGTH_LONG).show()
        }

    }
}