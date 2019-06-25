package com.janer.imglytictactai.Activities


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.janer.imglytictactai.Constants
import com.janer.imglytictactai.R
import com.janer.imglytictactai.SharedPref
import com.janer.imglytictactai.Utils.Utils
import kotlinx.android.synthetic.main.activity_endscreen.*

class EndScreenActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endscreen)

        updateEndScreen()

        button_restart.setOnClickListener {
            Utils.func_startActivity(this, TicTacToeActivity::class.java)
            
        }
    }

    fun updateEndScreen(){
        val intent = getIntent()
        val extras = intent.extras
        val winnerID = extras.getInt(Constants.EXTRA_WINNER)
        val Time =  extras.getString(Constants.EXTRA_TIME)

        val winner:String?
        when(winnerID)
        {
            1 -> winner = SharedPref(this).func_loadString(Constants.PREF_PLAYERNAME) + " has won"
            2 -> winner = "Computer has won"
            else -> winner = "It's a draw :)"
        }
        textview_winner.text = winner
        textview_time.text= "Your time of Thinking $Time"
    }

}