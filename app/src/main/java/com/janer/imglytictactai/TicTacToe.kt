package com.janer.imglytictactai
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import com.janer.imglytictactai.Activities.AIHandler
import com.janer.imglytictactai.Activities.resultHandler
import com.janer.imglytictactai.DialogFragments.ProcessingAIMoveDialog
import com.janer.imglytictactai.Utils.Utils
import kotlinx.android.synthetic.main.activity_tictactoe.view.*
import kotlin.random.Random

enum class GridMarker{
    E,X,O
}
class TicTacToe(var view:View){
    var grid= arrayListOf<GridMarker>()
    var isHumanPlaying:Boolean
    var turn:UByte = 0u
    lateinit var tileColor1:String
    lateinit var tileColor2:String
    init{
        isHumanPlaying = Random.nextBoolean()
        Utils.showToast(view.context,"Player Active: $isHumanPlaying")
        tileColor1 = "#"+Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundPlayer))
        tileColor2 = "#"+Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundAI))
        Utils.showToast(view.context, tileColor1)

    }

        fun btnClick(view: View){
        var btnSelected: Button = view as Button
        var cellID = 0
        when (btnSelected.id)
        {
            R.id.btn1 ->cellID = 1
            R.id.btn2 ->cellID = 2
            R.id.btn3 ->cellID = 3
            R.id.btn4 ->cellID = 4
            R.id.btn5 ->cellID = 5
            R.id.btn6 ->cellID = 6
            R.id.btn7 ->cellID = 7
            R.id.btn8 ->cellID = 8
            R.id.btn9 ->cellID = 9
        }


        PlayGame(cellID,btnSelected)
    }



    fun PlayGame(cellID:Int,btnSelected: Button){
        Utils.showToast(view.context, "vCell ID: $cellID | IsHmanplaying: $isHumanPlaying | \"${btnSelected.text}\"")

            if(isHumanPlaying){
                btnSelected.text = "X"
                btnSelected.background.setColorFilter(Color.parseColor(tileColor1), PorterDuff.Mode.SRC_ATOP)
                //   grid[cellID]= GridMarker.X
                isHumanPlaying = false
                AIThread().start()
            }else{
                btnSelected.text = "O"
                // grid[cellID]= GridMarker.O
               btnSelected.background.setColorFilter(Color.parseColor(tileColor2), PorterDuff.Mode.SRC_ATOP)
                isHumanPlaying = true
            }

        btnSelected.isEnabled = false
    }


}

class AIThread: Thread(){
    val TAG = "TicTacToe"
    override fun start() {
        super.start()

        var selectionDelay = Random.nextInt(3,8)
        publishProgress(selectionDelay + 20)
        Handler().postDelayed(this::AutoPlay, selectionDelay*1000.toLong())

    }

    fun AutoPlay()
    {
        publishProgress(21)
        val cellId =Random.nextInt(1,9)
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