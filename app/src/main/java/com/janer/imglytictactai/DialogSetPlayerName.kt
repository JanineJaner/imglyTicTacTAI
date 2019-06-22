package tictactoe.zeroneun.com.tictactoe

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_setplayername.view.*
import tictactoe.zeroneun.com.tictactoe.Classes.SharedPref

// <Summary>
// This script is responsible for taking player name from the dialog
// This Dialog fragment is called when "Change Name" button from "StartPageActivity" is clicked
// or when the app first started
// </Summary>

class DialogSetPlayerName : DialogFragment() {

    lateinit var listener: DialogSetPlayerNameListener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        LayoutInflater.from(context).inflate(R.layout.dialog_setplayername, null).apply {
            // Set Focus on Edit Text
            // Show Keyboard
            edittext_playerName.requestFocus()
            Utils.func_showKeyboardFromFragment(this)
            dialog.window.attributes.windowAnimations = R.style.DialogAnimationTransition
            dialog.setCanceledOnTouchOutside(false)
            // If Player Name Confirmed
            button_confirmPlayerName.setOnClickListener {
                // Clear Focus on Edit Text
                // apply text on Welcome Screen
                // Save player name

                edittext_playerName.text.also {
                    it.isNotEmpty().apply {
                        if (this) {
                            clearFocus()
                            listener.applyText(it.toString())
                            SharedPref(context).func_saveString(
                                Constants.SKEY_PLAYERNAME,
                                it.toString()
                            )
                            Utils.func_hideKeyboardFromFragement(rootView)
                            dismiss()
                        } else {
                            Utils.showToast(context, resources.getString(R.string.s_msg_alert_NO_NAME))
                        }
                    }

                }
            }
            return this
        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as DialogSetPlayerNameListener
    }

    interface DialogSetPlayerNameListener {
        fun applyText(userName: String)
    }
}

