package com.janer.imglytictactai.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.janer.imglytictactai.Constants
import com.janer.imglytictactai.R
import com.janer.imglytictactai.Utils.Utils
import com.janer.imglytictactai.Utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_startpage.*
import com.janer.imglytictactai.MyStaticVariables
import com.janer.imglytictactai.SharedPref
import com.janer.imglytictactai.DialogFragments.InputPlayerNameDialog


class StartPageActivity :AppCompatActivity(), InputPlayerNameDialog.InputPlayerNameDialogListener {

    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = SharedPref(this)
        MyStaticVariables.isDarkMode =sharedPref.func_loadBoolState(Constants.PREF_DARK_MODE)

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
        sharedPref.func_loadBoolState(Constants.PREF_FIRST_START).apply{
            if(!this)
            {
                func_showDialog()
                sharedPref.func_saveBoolState(Constants.PREF_FIRST_START,true)
            }
        }

        //textview
        if(!MyStaticVariables.isInStartPageActivity){
            textview_greetings.text = "Hi, ${sharedPref.func_loadString(Constants.PREF_PLAYERNAME)} welcome back."
            MyStaticVariables.isInStartPageActivity = true
            MyAnimationUtils(textview_greetings).func_slideInBottom()

        }else{
            textview_greetings.text = "Hello ${sharedPref.func_loadString(Constants.PREF_PLAYERNAME)}"
            MyAnimationUtils(textview_greetings).func_shirkFadeOutFromButtom()
        }


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
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

    }

    override fun onDestroy() {
        super.onDestroy()
        MyStaticVariables.isInStartPageActivity = false
    }


    fun func_showDialog(){
        InputPlayerNameDialog().show(
            this.supportFragmentManager,
            "tag_loginDialog"
        )
    }

    override fun applyText(playerName: String) {
        textview_greetings.text = "Hey $playerName :)"
    }


}