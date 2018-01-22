package com.yunr.pinview.keyboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yunr.pinview.R;


/**
 * Created by Yunr on 2017-09-15 14:08
 */

@SuppressLint("ValidFragment")
public class NumberKeyDialog extends DialogFragment  {

    private onKeyInputListener inputListener;
    private View view;
    private RecyclerView keyView;
    private View moveDownView;

    private NumberKeyDialog(onKeyInputListener inputListener) {
        this.inputListener = inputListener;
    }

    public static NumberKeyDialog getInstantiate(onKeyInputListener inputListener) {
        NumberKeyDialog numberKeyDialog = new NumberKeyDialog(inputListener);
        numberKeyDialog.setStyle(STYLE_NORMAL, R.style.dialog);
        return numberKeyDialog;
    }

    @Override
    public void onStart() {
        getDialog().getWindow().getAttributes().width =
                getContext().getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(false);
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.parseColor("#00000000"));
        //设置dialog的进出动画
        getDialog().getWindow().setWindowAnimations(R.style.animate_party);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        view = inflater.inflate(R.layout.dialog_key, container);
        keyView = view.findViewById(R.id.key_view);
        moveDownView = view.findViewById(R.id.key_move_down);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keyView.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL));
        keyView.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.HORIZONTAL));
        keyView.setAdapter(new KeyAdapter(inputListener));
        moveDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager) {
        show(manager, "key_num");
    }

}
