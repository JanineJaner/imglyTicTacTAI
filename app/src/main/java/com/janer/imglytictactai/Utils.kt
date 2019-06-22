package tictactoe.zeroneun.com.tictactoe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_input_playername.view.*
import tictactoe.zeroneun.com.tictactoe.Activity.StartPageActivity
import kotlin.reflect.KClass

object Utils{
    fun showToast(context:Context, message:String, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, message, duration).show()
    }

    fun func_startActivity(packageContext: Context,cls:Class<*>){
        var intent = Intent(packageContext, cls)
          packageContext.startActivity(intent)

    }

    fun func_showKeyboard(activity: Activity?) {

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    fun func_showKeyboardFromFragment(view:View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    fun func_hideKeyboardFromFragement(view:View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.rootView.windowToken,0)

    }

}