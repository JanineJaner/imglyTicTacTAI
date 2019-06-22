package com.janer.imglytictactai.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.janer.imglytictactai.R
import kotlinx.android.synthetic.main.activity_startpage.*
import tictactoe.zeroneun.com.tictactoe.*
import tictactoe.zeroneun.com.tictactoe.Classes.MyStaticVariables
import tictactoe.zeroneun.com.tictactoe.Classes.SharedPref


class StartPageActivity :AppCompatActivity(), DialogSetPlayerName.DialogSetPlayerNameListener {

    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = SharedPref(this)
        MyStaticVariables.isDarkMode =sharedPref.func_loadBoolState(Constants.SKEY_DARK_MODE)

        //
        //  Set Theme
        //
        if( MyStaticVariables.isDarkMode ){
            setTheme(R.style.DarkTheme)
        }
        else {
            setTheme(R.style.LightTheme)
        }
        setContentView(R.layout.activity_startpage)

        //
        // Show Set Player Name Dialog

        //
        sharedPref.func_loadBoolState(Constants.SKEY_FIRST_START).apply{
            if(!this)
            {
                func_showDialog()
                sharedPref.func_saveBoolState(Constants.SKEY_FIRST_START,true)
            }
        }

        //textview
        if(!MyStaticVariables.isInStartPageActivity){
            textview_greetings.text = "Hi, ${sharedPref.func_loadString(Constants.SKEY_PLAYERNAME)} welcome back."
            MyStaticVariables.isInStartPageActivity = true
            ViewAnimation().func_slideInBottom(textview_greetings)

        }else{
            textview_greetings.text = "Hello ${sharedPref.func_loadString(Constants.SKEY_PLAYERNAME)}"
            ViewAnimation().func_shirkFadeOutFromButtom(textview_greetings)
        }


        // Start Game
        button_startGame.setOnClickListener {
            //TODO: CALL TICTACTOE ACTIVITY

        }

        //Change Name
        button_changeName.setOnClickListener {
            func_showDialog()

        }

        // Dark/Light Mode Switching
        checkbox_darkModeEnabled.setOnClickListener {
            var checkbox_state = checkbox_darkModeEnabled.isChecked
            sharedPref.func_saveBoolState(Constants.SKEY_DARK_MODE, checkbox_state)
            Utils.func_startActivity(this,StartPageActivity::class.java)
        }

    }


    override fun onResume() {
        super.onResume()
        var checkboxState= sharedPref.func_loadBoolState(Constants.SKEY_DARK_MODE)
        checkbox_darkModeEnabled.setChecked(checkboxState)
        MyStaticVariables.isDarkMode =!checkboxState

    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

    }

    override fun onDestroy() {
        super.onDestroy()
        MyStaticVariables.isInStartPageActivity = false
    }


    fun func_showDialog(){
        DialogSetPlayerName().show(
            this.supportFragmentManager,
            "tag_loginDialog"
        )
    }

    override fun applyText(playerName: String) {
        textview_greetings.text = "Hey $playerName :)"
    }


}