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


        val intent = getIntent()
        val extras = intent.extras
        val Winner = extras.getString(Constants.EXTRA_WINNER)
        val Time =  extras.getString(Constants.EXTRA_TIME)
        textview_congratulation.text = "Congratulations $Winner has won"
        textview_time.text= "Your time of Thinking $Time"

        button_restart.setOnClickListener {
            Utils.func_startActivity(this, TicTacToeActivity::class.java)
        }

    }

}