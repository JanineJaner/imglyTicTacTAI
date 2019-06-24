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
import com.janer.imglytictactai.SharedPref
import java.util.*


enum class Markers {
    X, O, E;

    companion object {
        fun random(): Markers {
            return values()[Random.nextInt(values().size)]
        }
    }
}

class TicTacToe(val view: View) {
    var grid: Array<Markers>
    var isPlayerTurn: Boolean
    var turnCounter: UByte = 0u
    var tileColor1: String
    var tileColor2: String

    init {
        grid = Array(9) { Markers.E }
        isPlayerTurn = Random.nextBoolean()
        tileColor1 = "#" + Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundPlayer))
        tileColor2 = "#" + Integer.toHexString(ContextCompat.getColor(view.context, R.color.c_ButtonBackgroundAI))
    }

    fun startGame(timerThread: TimerThread) {

        if (!isPlayerTurn) {
            Log.i("TimerThread", "FIRST TURN = AI")
            AIThread(grid).start()
            timerThread.pause()
        }

        //Else it will wait for a button click from main activity
    }

    fun btnPressed(view: View, timerThread: TimerThread) {
        var btnSelected: Button = view as Button
        var cellID = 0
        when (btnSelected.id) {
            R.id.btn1 -> cellID = 1
            R.id.btn2 -> cellID = 2
            R.id.btn3 -> cellID = 3
            R.id.btn4 -> cellID = 4
            R.id.btn5 -> cellID = 5
            R.id.btn6 -> cellID = 6
            R.id.btn7 -> cellID = 7
            R.id.btn8 -> cellID = 8
            R.id.btn9 -> cellID = 9
        }
        PlayGame(cellID, btnSelected, timerThread)
    }


    fun PlayGame(cellID: Int, btnSelected: Button, timerThread: TimerThread) {
        turnCounter++

        Log.i("TimerThread", "TURN:" + turnCounter)
        if (isPlayerTurn) {
            grid[cellID - 1] = Markers.X
            btnSelected.text = "X"
            btnSelected.background.setColorFilter(Color.parseColor(tileColor1), PorterDuff.Mode.SRC_ATOP)

            if (!checkGameOver(timerThread)) {
                isPlayerTurn = false
                timerThread.pause()
                AIThread(grid).start()
            }

        } else {
            grid[cellID - 1] = Markers.O
            btnSelected.text = "O"
            btnSelected.background.setColorFilter(Color.parseColor(tileColor2), PorterDuff.Mode.SRC_ATOP)

            if (!checkGameOver(timerThread)) {
                isPlayerTurn = true
                timerThread.resume()
            }
        }

        btnSelected.isEnabled = false
        Log.i("TimerThread", "Cell ID ${cellID - 1}:" + Arrays.toString(grid))

    }

    fun checkGameOver(timerThread: TimerThread): Boolean {

        if (checkWinner(grid) || turnCounter >= 10u) {
            var winner: String
            if (isPlayerTurn)
                winner = SharedPref(view.context).func_loadString(Constants.PREF_PLAYERNAME)
            else
                winner = "Computer"
            gameOver(timerThread, winner)

            return true
        } else
            return false

    }

    fun gameOver(timerThread: TimerThread, winner: String) {
        val i = Intent(view.context, EndScreenActivity::class.java)
        val extras = Bundle()
        extras.putString(Constants.EXTRA_WINNER, winner)
        extras.putString(Constants.EXTRA_TIME, "" + timerThread.getElapsedTime())
        i.putExtras(extras)
        view.context.startActivity(i)
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

}

