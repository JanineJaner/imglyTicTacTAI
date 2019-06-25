package com.janer.imglytictactai.DialogFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.janer.imglytictactai.Constants
import com.janer.imglytictactai.R
import com.janer.imglytictactai.SharedPref
import com.janer.imglytictactai.Utils.Utils
import kotlinx.android.synthetic.main.dialog_inputplayername.view.*


// <Summary>
// This script is responsible for taking player name from the dialog
// This Dialog fragment is called when "Change Name" button from "StartPageActivity" is clicked
// or when the app first started
// </Summary>

class SetNameDialog : DialogFragment() {
    lateinit var listener: InputPlayerNameDialogListener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        inflater.inflate(R.layout.dialog_inputplayername, null).apply {
            // Set Focus on Edit Text
            // Show Keyboard
            // Attach dialog window transition
            // Supress outside touches
            // If Player Name Confirmed and edittext not empty
            //  true ->Clear Focus on Edit Text
            //        >Apply text on Welcome Screen
            //        >Save player name
            //        >Hide keyboard
            //  false -> show toast message

            edittext_playerName.requestFocus()
            Utils.func_showKeyboardFromFragment(this)
            dialog.window?.attributes!!.windowAnimations = R.style.DialogAnimationTransition
            dialog.setCanceledOnTouchOutside(false)
            button_confirmPlayerName.setOnClickListener {

                edittext_playerName.text.also {
                    it.isNotEmpty().apply {
                        if (this) {
                            clearFocus()
                            listener.applyText(it.toString())
                            SharedPref(context).func_saveString(
                                Constants.PREF_PLAYERNAME,
                                it.toString()
                            )
                            Utils.func_hideKeyboardFromFragement(rootView)
                            dismiss()
                        } else {
                            Utils.showToast(
                                context,
                                resources.getString(R.string.s_msg_alert_NO_NAME)
                            )
                        }
                    }
                }
            }
            return this
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as InputPlayerNameDialogListener
    }

    interface InputPlayerNameDialogListener {
        fun applyText(userName: String)
    }
}

