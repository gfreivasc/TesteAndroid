package com.gabrielfv.ibmtest.libraries.design

import android.content.res.Resources

fun Int.toDp() = (this * Resources.getSystem().displayMetrics.density).toInt()