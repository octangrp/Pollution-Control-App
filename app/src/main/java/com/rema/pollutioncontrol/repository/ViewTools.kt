package com.rema.pollutioncontrol.repository

import android.content.Context
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.animation.ValueAnimator
import android.widget.TextView


class ViewTools {

    companion object {

        fun setMargins(context: Context, view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(getPixels(context, left), getPixels(context, top), getPixels(context, right), getPixels(context, bottom))
                view.requestLayout()
            }
        }

        fun setHeight(context: Context, view: View, height: Int) {

            view.layoutParams.height = getPixels(context, height)
            view.requestLayout()
        }

        fun setNavigationDrawerToggler(hamMenu: View, drawerLayout: androidx.drawerlayout.widget.DrawerLayout) {
            hamMenu.setOnClickListener {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(Gravity.START)
                else
                    drawerLayout.closeDrawer(Gravity.END)
            }
        }

        private fun getPixels(context: Context, size: Int): Int {
            val scale = context.getResources().getDisplayMetrics().density
            return (size * scale + 0.5f).toInt()
        }


        fun startCountAnimation(textView: TextView, oldValue: Int, value: Int, duration: Int) {
            val animator = ValueAnimator.ofInt(oldValue, value)
            animator.duration = duration.toLong()
            animator.addUpdateListener { animation -> textView.text = animation.animatedValue.toString() }
            animator.start()
        }

    }
}