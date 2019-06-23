package com.janer.imglytictactai.Utils

import android.view.View
import android.view.animation.AnimationUtils
import com.janer.imglytictactai.MyBounceInterpolator
import com.janer.imglytictactai.R

class MyAnimationUtils(val view: View){

    fun func_slideInBottom(){
        AnimationUtils.loadAnimation(view.context, R.anim.abc_slide_in_bottom).apply{
            view.startAnimation(this)
        }
    }

    fun func_shirkFadeOutFromButtom(){
        AnimationUtils.loadAnimation(view.context, R.anim.abc_shrink_fade_out_from_bottom).apply{
            view.startAnimation(this)
        }
    }

    fun func_bounceAnimation(){
        AnimationUtils.loadAnimation(view.context, R.anim.bounce).also{
            it.interpolator = MyBounceInterpolator(0.1, 30.0)
            view.startAnimation(it)
        }
    }

    //----------------------------------------------------//
    //          tictactoe 4 button corner transition
    //          to retain shape and change color
    //---------------------------------------------------//

    fun func_playerButtomLeft(){
        AnimationUtils.loadAnimation(view.context, R.anim.bounce).also{
            it.interpolator = MyBounceInterpolator(0.1, 30.0)
            view.startAnimation(it)
        }
    }

}