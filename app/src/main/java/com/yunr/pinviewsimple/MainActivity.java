package com.yunr.pinviewsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yunr.pinview.InputListener;
import com.yunr.pinview.YPinView;
import com.yunr.pinview.keyboard.NumberKeyDialog;
import com.yunr.pinview.keyboard.onKeyInputListener;

public class MainActivity extends AppCompatActivity implements onKeyInputListener {

    private YPinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pinView = findViewById(R.id.pinview);
        pinView.setInputListener(new InputListener() {

            @Override
            public void onCompleted(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        pinView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NumberKeyDialog.getInstantiate(MainActivity.this).show(getSupportFragmentManager());
            }
        });

    }

    @Override
    public void onKeyInput(String text) {
        pinView.add(text);
    }

    @Override
    public void onDelKeyInput() {
        pinView.del();
    }

    @Override
    public void onDelKeyLongPress() {
        pinView.delAll();
    }

    @Override
    public void onDefineKeyInput() {
        Toast.makeText(this, pinView.getText(), Toast.LENGTH_SHORT).show();
    }
}
