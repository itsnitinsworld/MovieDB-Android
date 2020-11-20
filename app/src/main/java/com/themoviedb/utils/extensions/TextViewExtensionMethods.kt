package com.themoviedb.utils.extensions

import android.widget.TextView
import java.util.*

/**
 * @author- Nitin Khanna
 * @date - 19-11-2020
 */


fun TextView.setFirstCharCapitalText(string: String?) {
    if (string.isNullOrEmpty()) return
    text = (string.substring(0, 1).toUpperCase(Locale.ROOT) + string.substring(1)
        .toLowerCase(Locale.ROOT))
}

