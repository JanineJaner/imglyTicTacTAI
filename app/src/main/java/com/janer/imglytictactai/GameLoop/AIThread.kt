package com.janer.imglytictactai.GameLoop

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.janer.imglytictactai.Activities.AIHandler
import kotlin.random.Random

class AIThread(val grid: Array<Markers>): Thread(){
    val TAG = "SELECTION"
    override fun start() {
        super.start()
        var selectionDelay = Random.nextInt(3,8)
        publishProgress(selectionDelay + 20)
        Handler().postDelayed(this::selectCell, selectionDelay*1000.toLong())

    }

    fun selectCell()
    {
        publishProgress(21)
        var cellId =Random.nextInt(0,8)
        if(grid[cellId].equals(Markers.E))
            Log.i(TAG,"Cell ID ${cellId} = E")
        while(!grid[cellId].equals(Markers.E))
        {
            cellId =Random.nextInt(0,8)
            Log.i(TAG,"RESELECTING POS:${cellId}")
        }
        publishProgress(cellId)
        Log.i(TAG,"Sending back CELL ID $cellId")
    }


    private fun publishProgress(btn:Int){

        var msgBundle = Bundle().also {
            it.putInt("btn_selected",btn)
        }
        var msg: Message = Message()
        msg.data = msgBundle
        AIHandler.sendMessage(msg)
    }


}