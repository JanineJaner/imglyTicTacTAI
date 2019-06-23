package com.janer.imglytictactai
import android.view.View
import android.widget.Button
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

    init{
        isHumanPlaying = Random.nextBoolean()
        Utils.showToast(view.context,"Player Active: $isHumanPlaying")
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


    fun AutoPlay()
    {

        val cellId =Random.nextInt(1,9)

        var buSelect: Button

        when(cellId)
        {
            1 -> buSelect =  view.btn1
            2 -> buSelect = view.btn2
            4 -> buSelect =view.btn3
            5 -> buSelect =view.btn4
            6 -> buSelect = view.btn5
            7 -> buSelect = view.btn6
            8 -> buSelect =view.btn7
            9 -> buSelect = view.btn9
            else -> buSelect = view.btn1
        }
        PlayGame(cellId,buSelect)
    }

    fun PlayGame(cellID:Int,btnSelected: Button){
        Utils.showToast(view.context, "vCell ID: $cellID | IsHmanplaying: $isHumanPlaying | \"${btnSelected.text}\"")

            if(isHumanPlaying){
                btnSelected.text = "X"
                btnSelected.setBackgroundResource(R.color.c_ButtonBackgroundPlayer)

                //   grid[cellID]= GridMarker.X
                isHumanPlaying = false
                AutoPlay()
            }else{
                btnSelected.text = "O"
              //  btnSelected.setBackgroundResource(R.color.c_ButtonBackgroundAI)
                // grid[cellID]= GridMarker.O
                isHumanPlaying = true
            }

        btnSelected.isEnabled = false
    }


}