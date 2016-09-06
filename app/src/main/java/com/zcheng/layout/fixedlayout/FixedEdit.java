package com.zcheng.layout.fixedlayout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Created by User on 2016/8/8.
 */
public class FixedEdit extends EditText {

    //字体的长度
    private int mlength = 0;

    private boolean isFix = false;
    private boolean isDel = false;

    public FixedEdit(Context context) {
        super(context);

        init();
    }

    public FixedEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void getTextString(String t) {

        mlength = t.length();
        if (mlength > 0) {
            this.setText(t);
            isDel = true;
        }
        this.setSelection(mlength);
    }

    public void init() {

        this.addTextChangedListener(mTextWatcher);
        this.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        this.setLongClickable(false);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == mlength || s.length() < mlength) {
                isFix = false;
            } else {
                isFix = true;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            getTextString();
        }
    };

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (isDel) {
            selStart = selStart < mlength ? mlength : selStart;
            selEnd = selStart;
            this.setSelection(selStart, selEnd);
        }
        super.onSelectionChanged(selStart, selEnd);
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getKeyCode() == 67) {
            if (!isFix)
                return !isFix;
        }
        return super.dispatchKeyEvent(event);
    }

    public String getTextString(){

        String []str=getText().toString().split(":");
        for(int i=0;i<str.length;i++) {
            Log.i("aaa", "" +str[i]);
        }
        return getText().toString();
    }
}
