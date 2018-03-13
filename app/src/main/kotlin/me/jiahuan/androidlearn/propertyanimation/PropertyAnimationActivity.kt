package me.jiahuan.androidlearn.propertyanimation


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView

import me.jiahuan.androidlearn.R

class PropertyAnimationActivity : AppCompatActivity() {

    private var mImageView: ImageView? = null

    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)

        mImageView = findViewById(R.id.activity_property_animation_image_view)
        mTextView = findViewById(R.id.activity_property_animation_text_view)

        val objectAnimator = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f, 0.5f)
        objectAnimator.duration = 5000
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()


        val animatorSet = AnimatorSet()
        val floatValueAnimator = ValueAnimator.ofFloat(0f, 126512.36f)
        floatValueAnimator.addUpdateListener { animation ->
            val money = animation.animatedValue as Float
            mTextView!!.text = String.format("%.2f", money)
        }


        val startColor = Color.parseColor("#FCA3AB")
        val endColor = Color.parseColor("#FB0435")
        val colorAnimator = ValueAnimator.ofObject(TextArgbEvaluator(), startColor, endColor)
        colorAnimator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            mTextView!!.setTextColor(color)
        }
        animatorSet.playTogether(floatValueAnimator, colorAnimator)
        animatorSet.duration = 5000
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.start()
    }


    internal inner class TextArgbEvaluator : TypeEvaluator<Int> {

        override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
            val startA = startValue shr 24 and 0xff
            val startR = startValue shr 16 and 0xff
            val startG = startValue shr 8 and 0xff
            val startB = startValue and 0xff

            val endA = endValue shr 24 and 0xff
            val endR = endValue shr 16 and 0xff
            val endG = endValue shr 8 and 0xff
            val endB = endValue and 0xff

            return (startA + (fraction * (endA - startA)).toInt() shl 24) or
                    (startR + (fraction * (endR - startR)).toInt() shl 16) or
                    (startG + (fraction * (endG - startG)).toInt() shl 8) or
                    (startB + (fraction * (endB - startB)).toInt())
        }
    }
}
