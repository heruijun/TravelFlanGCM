package com.gcm.heruijun.base.widgets

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

/**
 * Created by heruijun on 2017/12/13.
 */
inline fun ViewManager.printingTextView(): com.gcm.heruijun.base.widgets.PrintingTextView = printingTextView() {}
inline fun ViewManager.printingTextView(init: PrintingTextView.() -> Unit): PrintingTextView {
    return ankoView({ PrintingTextView(it) }, theme = 0, init = init)
}