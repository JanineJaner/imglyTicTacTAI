package com.janer.imglytictactai.GameLoop

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.janer.imglytictactai.Activities.AIHandler
import kotlin.random.Random

class AIThread(val grid: Array<Boolean>): Thread(){
    val TAG = "TicTacToe"
    override fun start() {
        super.start()
        var selectionDelay = Random.nextInt(3,8)
        publishProgress(selectionDelay + 20)
        Handler().postDelayed(this::selectCell, selectionDelay*1000.toLong())

    }

    fun selectCell()
    {
        publishProgress(21)
        var cellId =Random.nextInt(1,9)
        while(grid[cellId-1]){
            cellId =Random.nextInt(1,9)
            Log.i("TimerThread", "RESELECTING: ${cellId-1}")
        }
        Log.i("TimerThread", "Computer chose: ${cellId-1}")
        publishProgress(cellId)
    }


    private fun publishProgress(btn:Int){
        Log.i(TAG,"Sending back to the UI Thread")
        var msgBundle = Bundle().also {
            it.putInt("btn_selected",btn)
        }
        var msg: Message = Message()
        msg.data = msgBundle
        AIHandler.sendMessage(msg)
    }


}