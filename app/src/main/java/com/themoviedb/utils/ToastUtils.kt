package com.themoviedb.utils

import android.content.Context
import android.widget.Toast

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
object ToastUtils {
    private var mContext: Context? = null

    fun init(context: Context) {
        if (mContext == null) {
            synchronized(Context::class.java) {
                if (mContext == null) this.mContext = context
            }
        }
    }

    fun show(message: String?, time: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty() || mContext == null) return
        Toast.makeText(mContext, message, time).show()
    }
}