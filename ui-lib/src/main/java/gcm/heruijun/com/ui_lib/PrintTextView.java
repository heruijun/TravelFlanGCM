package gcm.heruijun.com.ui_lib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by heruijun on 2017/12/10.
 */

public class PrintTextView extends android.support.v7.widget.AppCompatTextView {

    public PrintTextView(Context context) {
        super(context);
        init();
    }

    public PrintTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrintTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

}
