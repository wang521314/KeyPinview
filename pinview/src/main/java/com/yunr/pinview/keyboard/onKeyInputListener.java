package com.yunr.pinview.keyboard;

/**
 * @author Yunr
 * @date 2018/01/22 17:44
 */
public interface onKeyInputListener {

    void onKeyInput(String text);

    void onDelKeyInput();

    void onDelKeyLongPress();

    void onDefineKeyInput();

}