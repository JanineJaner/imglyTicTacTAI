package com.janer.imglytictactai.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.janer.imglytictactai.Constants
import com.janer.imglytictactai.R
import com.janer.imglytictactai.Utils.Utils
import kotlinx.android.synthetic.main.activity_startpage.*
import com.janer.imglytictactai.MyStaticVariables
import com.janer.imglytictactai.SharedPref
import com.janer.imglytictactai.DialogFragments.InputPlayerNameDialog
import com.janer.imglytictactai.MyStaticVariables.Companion.isSamePlayer


class StartPageActivity :AppCompatActivity(), InputPlayerNameDialog.InputPlayerNameDialogListener {

    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = SharedPref(this)
        MyStaticVariables.isDarkMode =sharedPref.func_loadBoolState(Constants.PREF_DARK_MODE)

        //  Set Theme
        if( MyStaticVariables.isDarkMode )setTheme(R.style.DarkTheme)
        else setTheme(R.style.LightTheme)

        setContentView(R.layout.activity_startpage)

        //Check app first start
        // true -> Show Enter Player Name Dialog
        func_checkFirstStart()

        //update textview
        func_updateWelcomeText()

        // Start Game
        button_startGame.setOnClickListener {
            Utils.func_startActivity(this,TicTacToeActivity::class.java)
            overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
            finish()
        }

        //Change Name
        button_changeName.setOnClickListener {
            func_showDialog()
        }

        // Dark/Light Mode Switching
        checkbox_darkModeEnabled.setOnClickListener {
            var checkbox_state = checkbox_darkModeEnabled.isChecked
            sharedPref.func_saveBoolState(Constants.PREF_DARK_MODE, checkbox_state)
            Utils.func_startActivity(this,StartPageActivity::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        var checkboxState= sharedPref.func_loadBoolState(Constants.PREF_DARK_MODE)
        checkbox_darkModeEnabled.setChecked(checkboxState)
        MyStaticVariables.isDarkMode =!checkboxState
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out) //fade transition when dark mode checkbox selected
    }

    override fun onDestroy() {
        super.onDestroy()
        isSamePlayer = false
    }

    fun func_updateWelcomeText(){

        val playerName = sharedPref.func_loadString(Constants.PREF_PLAYERNAME)
        if(!isSamePlayer){
            textview_greetings.text = "Hi, $playerName welcome back."
            isSamePlayer = true

        }else{
            textview_greetings.text = "Hello again, $playerName"
        }
    }

    fun func_checkFirstStart(){
        sharedPref.func_loadBoolState(Constants.PREF_FIRST_START).apply{
            if(!this)
            {
                sharedPref.func_saveString(Constants.PREF_PLAYERNAME,"Player") // Initialized default player name at first app start
                func_showDialog()
                sharedPref.func_saveBoolState(Constants.PREF_FIRST_START,true)
            }
        }
    }
    fun func_showDialog(){
        InputPlayerNameDialog().show(
            this.supportFragmentManager,
            "tag_loginDialog"
        )
    }

    // Called by player name dialog to update player name
    override fun applyText(playerName: String) {
        textview_greetings.text = "Hey $playerName :)"
    }

}