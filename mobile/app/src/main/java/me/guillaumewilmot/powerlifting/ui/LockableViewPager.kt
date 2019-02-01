package me.guillaumewilmot.powerlifting.ui

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import me.guillaumewilmot.powerlifting.R

class LockableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var swipeable: Boolean = true

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LockableViewPager)
        try {
            swipeable = a.getBoolean(R.styleable.LockableViewPager_swipeable, true)
        } finally {
            a.recycle()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (swipeable) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (swipeable) {
            super.onInterceptTouchEvent(event)
        } else false

    }
}