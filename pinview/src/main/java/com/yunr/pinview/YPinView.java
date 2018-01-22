package com.yunr.pinview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Yunr
 * @date 2018/01/22 13:44
 */
public class YPinView extends LinearLayout {

    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;

    @DrawableRes
    private int mPinBackground = R.drawable.sample_background;//单个密码框背景
    private int mPinLength = 6;//内容长度
    private int mPinHeight = (int) (40 * DENSITY);//单个高度
    private int mPinWidth = (int) (40 * DENSITY);//单个宽度
    private int mSplitWidth = 20;//空隙宽度
    private boolean mPassword = true;//是否为密码形式

    private ArrayList<TextView> editList;
    private TextView currEditText;
    private int currIndex = -1;
    private LayoutParams childParams;
    private InputListener listener;

    public YPinView(Context context) {
        this(context, null);
    }

    public YPinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YPinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setWillNotDraw(false);//跳过自身绘制
        initAttributes(getContext(), attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        childParams = new LayoutParams(mPinWidth, mPinHeight);
        createEditTexts();
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PinView, defStyleAttr, 0);
            mPinBackground = array.getResourceId(R.styleable.PinView_pinBackground, mPinBackground);
            mPinLength = array.getInt(R.styleable.PinView_pinLength, mPinLength);
            editList = new ArrayList<>(mPinLength);
            mPinHeight = (int) array.getDimension(R.styleable.PinView_pinHeight, mPinHeight);
            mPinWidth = (int) array.getDimension(R.styleable.PinView_pinWidth, mPinWidth);
            mSplitWidth = (int) array.getDimension(R.styleable.PinView_splitWidth, mSplitWidth);
            mPassword = array.getBoolean(R.styleable.PinView_password, mPassword);
            array.recycle();
        }
    }

    private void createEditTexts() {
        removeAllViews();//清除所有子View
        editList.clear();
        for (int i = 0; i < mPinLength; i++) {
            TextView textView = new TextView(getContext());
            generateText(textView);
            editList.add(i, textView);
            addView(textView);
        }

        setTransformation();
        currEditText = editList.get(0);
        currIndex = 0;
    }

    private void generateText(TextView textView) {
        childParams.setMargins(mSplitWidth, mSplitWidth, mSplitWidth, mSplitWidth);
        textView.setLayoutParams(childParams);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(18);
        textView.setBackgroundResource(mPinBackground);
        textView.setPadding(0, 0, 0, 0);
    }

    private void setTransformation() {
        if (mPassword) {
            for (TextView editText : editList) {
                editText.setTransformationMethod(new PinTransformationMethod());
            }
        } else {
            for (TextView editText : editList) {
                editText.setTransformationMethod(null);
            }
        }
    }

    public void setInputListener(InputListener listener) {
        this.listener = listener;
    }

    public String getText() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (TextView textView : editList) {
            stringBuilder.append(textView.getText());
        }
        return stringBuilder.toString();
    }

    public void add(CharSequence c) {
        if (c != null && c.length() == 1) {
            if ((currIndex + 1) <= mPinLength) {//当前不为最后一个
                currEditText.setText(c);
                move();
            }
        }
    }

    private void move() {
        currIndex++;
        if (currIndex >= mPinLength) {
            currIndex = mPinLength;
            if (listener != null) {
                listener.onCompleted(getText());
            }

            return;
        }

        TextView nextEditText = editList.get(currIndex);
        if (nextEditText == null) {
            return;
        }

        currEditText = nextEditText;
    }

    public void del() {
        if (currEditText.length() > 0) {//有内容
            currEditText.setText("");
        } else {//无内容，前移，删除
            currIndex--;
            if (currIndex < 0) {
                currIndex = 0;
                return;
            }

            TextView nextEditText = editList.get(currIndex);
            if (nextEditText == null) {
                return;
            }

            nextEditText.setText("");
            currEditText = nextEditText;
        }
    }

    public void delAll() {
        for (TextView textView : editList) {
            textView.setText("");
        }

        currEditText = editList.get(0);
        currIndex = 0;
    }

    private class PinTransformationMethod implements TransformationMethod {

        private char BULLET = '\u2022';

        @Override
        public CharSequence getTransformation(CharSequence source, final View view) {
            return new PinTransformationMethod.PasswordCharSequence(source);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence sourceText, final boolean focused, final int direction, final Rect previouslyFocusedRect) {

        }

        private class PasswordCharSequence implements CharSequence {
            private final CharSequence source;

            public PasswordCharSequence(@NonNull CharSequence source) {
                this.source = source;
            }

            @Override
            public int length() {
                return source.length();
            }

            @Override
            public char charAt(int index) {
                return BULLET;
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return new PinTransformationMethod.PasswordCharSequence(this.source.subSequence(start, end));
            }

        }
    }

}
