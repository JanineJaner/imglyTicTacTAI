package com.janer.imglytictactai.GameLoop

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import kotlin.random.Random
import android.content.Intent
import android.os.Bundle
import com.janer.imglytictactai.Activities.EndScreenActivity
import com.janer.imglytictactai.Constants
import com.janer.imglytictactai.R
import java.util.*


enum class Markers {
    X, O, E;

    /*
    companion object {
        fun random(): Markers {
            return values()[Random.nextInt(values().size)]
        }
    }
    */
}

class TicTacToe(val view: View) {
    val TAG = "SELECTION"
    var gridArray: Array<Markers>
    var isPlayerTurn: Boolean
    var turnCounter: UByte = 0u
    var tileColor1: String
    var tileColor2: String

    init {
        gridArray = Array(9) { Markers.E }
        isPlayerTurn = Random.nextBoolean()
        tileColor1 = "#" + Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundPlayer))
        tileColor2 = "#" + Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundAI))
    }

    fun startGame(timerThread: TimerThread) {

        if (!isPlayerTurn) {
            AIThread(gridArray).start()
            timerThread.pause()
        }
        //Else it will wait for a button click from main activity
    }

    //Player Cell Selection
    fun selectCell(view: View, timerThread: TimerThread) {
        var btnSelected: Button = view as Button
        var cellID = 0
        when (btnSelected.id) {
            R.id.btn1 -> cellID = 0
            R.id.btn2 -> cellID = 1
            R.id.btn3 -> cellID = 2
            R.id.btn4 -> cellID = 3
            R.id.btn5 -> cellID = 4
            R.id.btn6 -> cellID = 5
            R.id.btn7 -> cellID = 6
            R.id.btn8 -> cellID = 7
            R.id.btn9 -> cellID = 8
        }

        applyMarkers(cellID, btnSelected, timerThread)
    }


    fun applyMarkers(cellID: Int, btnSelected: Button, timerThread: TimerThread) {

        turnCounter++
        Log.i(TAG, "TURN:" + turnCounter)
        if (isPlayerTurn) {
            gridArray[cellID] = Markers.X
            btnSelected.text = "X"
            btnSelected.background.setColorFilter(Color.parseColor(tileColor1), PorterDuff.Mode.SRC_ATOP)

            if (!checkGameOver(timerThread)) {
                isPlayerTurn = false
                timerThread.pause()
                AIThread(gridArray).start()
            }

        } else {
            gridArray[cellID] = Markers.O
            btnSelected.text = "O"
            btnSelected.background.setColorFilter(Color.parseColor(tileColor2), PorterDuff.Mode.SRC_ATOP)

            if (!checkGameOver(timerThread)) {
                isPlayerTurn = true
                timerThread.resume()
            }
        }

        btnSelected.isEnabled = false
        Log.i(TAG, "Cell ID ${cellID}:" + Arrays.toString(gridArray))

    }

    fun checkGameOver(timerThread: TimerThread): Boolean {
        // winner = 0 -> Draw
        // winner = 1 -> Player Wins
        // winner = 2 -> AI Wins
        if (checkWinner(gridArray)) {
            val winner: Int
            if (isPlayerTurn)
                winner = 1
            else
                winner = 2
            gameOver(timerThread, winner)

            return true
        }

        if(turnCounter>=9u){
            val winner = 3
            gameOver(timerThread, winner)
            return true
        }
            return false

    }

    private fun checkWinner(grid: Array<Markers>): Boolean {
        //---------
        // 0| 1 |2
        // 3| 4 |5
        // 6| 7 |9
        //---------

        //check row
        for (index in 0 until 8 step 3) {
            if (grid[index].equals(grid[index + 1]) && grid[index].equals(grid[index + 2]))
                if (grid[index] != Markers.E) return true
        }

        //check column
        for (index in 0 until 2) {
            if (grid[index].equals(grid[index + 3]) && grid[index].equals(grid[index + 6]))
                if (grid[index] != Markers.E) return true
        }

        // check Diagonals
        if (grid[0].equals(grid[4])&&grid[0].equals(grid[7]))
            if (grid[0] != Markers.E) return true

        if (grid[2].equals(grid[4])&&grid[2].equals(grid[6]))
            if (grid[2] != Markers.E) return true

        return false
    }

    fun gameOver(timerElapsedTime: TimerThread, winner: Int) {
        val intent = Intent(view.context, EndScreenActivity::class.java)
        val extras = Bundle()
        extras.putInt(Constants.EXTRA_WINNER, winner)
        extras.putString(Constants.EXTRA_TIME, "" + timerElapsedTime.getElapsedTime())
        intent.putExtras(extras)
        view.context.startActivity(intent)
    }

}

