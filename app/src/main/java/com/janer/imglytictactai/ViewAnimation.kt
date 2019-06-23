package tictactoe.zeroneun.com.tictactoe

import android.view.View
import android.view.animation.AnimationUtils

class ViewAnimation{

    fun func_slideInBottom(view: View){
        AnimationUtils.loadAnimation(view.context, R.anim.abc_slide_in_bottom).apply{
            view.startAnimation(this)
        }
    }

    fun func_shirkFadeOutFromButtom(view: View){
        AnimationUtils.loadAnimation(view.context, R.anim.abc_shrink_fade_out_from_bottom).apply{
            view.startAnimation(this)
        }
    }


}