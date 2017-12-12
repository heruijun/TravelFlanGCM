package com.gcm.heruijun.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by heruijun on 2017/12/10.
 */

class PrintTextView : android.support.v7.widget.AppCompatTextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

    }

}
