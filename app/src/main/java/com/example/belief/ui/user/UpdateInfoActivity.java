package com.example.belief.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.belief.R;

public class UpdateInfoActivity extends AppCompatActivity {

    RadioGroup sex ;
    private Button btn;
    private EditText edt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = getIntent().getStringExtra("action");
        switch (action) {
            case "sex":{
                setContentView(R.layout.sex);
                sex = findViewById(R.id.sex);
                sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton sex = findViewById(checkedId);
                        //数据是使用Intent返回
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("sex", sex.getText());
                        //设置返回数据
                        UpdateInfoActivity.this.setResult(RESULT_OK, intent);
                        //关闭Activity
                        UpdateInfoActivity.this.finish();
                    }
                });
                break;
            }
            case "name":{
                setContentView(R.layout.name);
                btn = findViewById(R.id.baocun2);
                edt = findViewById(R.id.name);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName= edt.getText().toString();
                        if (newName.length() != 0){
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("name", newName);
                            //设置返回数据
                            UpdateInfoActivity.this.setResult(RESULT_OK, intent);
                            //关闭Actrivity
                            UpdateInfoActivity.this.finish();
                        } else {
                            Toast.makeText(UpdateInfoActivity.this,"昵称不为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
}
