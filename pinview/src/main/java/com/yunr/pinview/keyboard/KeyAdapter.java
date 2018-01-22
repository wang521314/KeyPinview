package com.yunr.pinview.keyboard;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunr.pinview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunr
 * @date 2017/12/05 10:11
 */
public class KeyAdapter extends Adapter {

    private List<String> keyList;

    private onKeyInputListener keyInputListener;

    public KeyAdapter(onKeyInputListener keyInputListener) {
        this.keyInputListener = keyInputListener;
        keyList = new ArrayList<>();
        keyList.add("1");
        keyList.add("2");
        keyList.add("3");
        keyList.add("4");
        keyList.add("5");
        keyList.add("6");
        keyList.add("7");
        keyList.add("8");
        keyList.add("9");
        keyList.add("确定");
        keyList.add("0");
        keyList.add("D");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_item,
                    null, false);
            return new KeyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_item2,
                    null, false);
            return new KeyViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != keyList.size() - 1) {
            ((KeyViewHolder) holder).setData(keyList.get(position));
            ((KeyViewHolder) holder).addListener(keyInputListener, position);
        } else {
            ((KeyViewHolder2) holder).addListener(keyInputListener);
        }
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == keyList.size() - 1) {
            return 2;
        }

        return 1;
    }

    static class KeyViewHolder extends RecyclerView.ViewHolder {

        TextView mKeyItemText;

        KeyViewHolder(View view) {
            super(view);
            mKeyItemText = view.findViewById(R.id.key_item_text);
        }

        public void setData(String text) {
            mKeyItemText.setText(text);
        }

        public void addListener(final onKeyInputListener listener, final int position) {

            mKeyItemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if (position == 9) {
                            listener.onDefineKeyInput();
                            return;
                        }

                        listener.onKeyInput((String) mKeyItemText.getText());
                    }
                }
            });
        }

    }

    static class KeyViewHolder2 extends RecyclerView.ViewHolder {

        ImageView mKeyItemImg;

        KeyViewHolder2(View view) {
            super(view);
            mKeyItemImg = view.findViewById(R.id.key_item_img);
        }

        public void addListener(final onKeyInputListener listener) {
            mKeyItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDelKeyInput();
                    }
                }
            });
            mKeyItemImg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onDelKeyLongPress();
                    }
                    return true;
                }
            });
        }
    }

}
