package com.example.tyrecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;
import java.io.FileOutputStream;

public class opencv3 extends AppCompatActivity {

    TextView result,result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opencv3);

        result=findViewById(R.id.resultt);
        result2=findViewById(R.id.result2);

        Button gallery= findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){

            Uri selectedImage= data.getData();
            //String Path=selectedImage.getPath();
            ImageView imageView = findViewById(R.id.imageViewed);
            imageView.setImageURI(selectedImage);
            //System.out.println("koko"+Path);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (! Python.isStarted()) {
                Python.start(new AndroidPlatform(this));
            }

            Python py = Python.getInstance();
            PyObject pyobj = py.getModule("script");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File("/storage/6394-0EF9/Pictures/tirecare");
            dir.mkdirs();

            String filename = String.format("test.png",System.currentTimeMillis());
            File outFile = new File(dir,filename);
            try{
                outputStream = new FileOutputStream(outFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            try{
                outputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                outputStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            String pathfinal="/storage/6394-0EF9/Pictures/tirecare";
            System.out.println(pathfinal);

            PyObject obj=pyobj.callAttr("tester",pathfinal);
            result.setText(obj.toString());

            PyObject objj=pyobj.callAttr("calibtest",pathfinal);
            result2.setText(objj.toString());

        }
    }
}
