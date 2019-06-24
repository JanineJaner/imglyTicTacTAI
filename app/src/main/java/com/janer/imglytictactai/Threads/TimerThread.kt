package com.janer.imglytictactai.Threads


import android.os.Bundle
import android.os.Message
import android.os.SystemClock
import android.util.Log
import com.janer.imglytictactai.Activities.resultHandler

//
//
//
//
class TimerThread : Runnable {
    val TAG = "TimerThread"
    var pause:Boolean
    var startTime: Long = 0L
    var elapsedTime = 0L
    init{
        pause = false
        startTime = SystemClock.currentThreadTimeMillis()
    }

    override fun run() {
        if(!pause){

            val currentTime = (SystemClock.currentThreadTimeMillis()- startTime) + elapsedTime
            publishProgress( convertTime(currentTime))
        }
       resultHandler.postDelayed(this,100)
    }

    fun resume(){
        Log.i(TAG,"RESUME")

        pause = false
        startTime = SystemClock.currentThreadTimeMillis()
        Log.i(TAG,"Starttime = $startTime")
    }

    fun pause(){
        Log.i(TAG,"PAUSED")
        pause = true
        elapsedTime = SystemClock.currentThreadTimeMillis()
    }

    fun stop(){
        Log.i(TAG,"STOP")
        resultHandler.removeCallbacks(this)
    }

    private fun convertTime(currentTime:Long):String{
        val minutes = (currentTime/100 / 60) %60
        var seconds = (currentTime/100) %60
        var miliseconds = currentTime % 100
        return String.format("%02d:%02d:%02d", minutes,seconds,miliseconds)
    }


    private fun publishProgress(count:String){
        Log.i(TAG,"Sending back to the UI Thread")
        var msgBundle = Bundle().also {
            it.putString("result", count.toString())
        }
        var msg: Message = Message()
        msg.data = msgBundle
        resultHandler.sendMessage(msg)
    }

}
