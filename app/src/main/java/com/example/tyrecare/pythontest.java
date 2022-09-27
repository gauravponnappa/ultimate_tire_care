package com.example.tyrecare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.w3c.dom.Text;

public class pythontest extends AppCompatActivity {

    Button add;
    EditText one,two;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pythontest);

        add=findViewById(R.id.add);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        result=findViewById(R.id.result);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("script");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PyObject obj=pyobj.callAttr("main",one.getText().toString(),two.getText().toString());
                result.setText(obj.toString());

            }
        });

    }
}