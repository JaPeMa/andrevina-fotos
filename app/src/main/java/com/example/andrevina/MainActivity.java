package com.example.andrevina;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    int secretNumber;

    int attempt = 0;

    public static Record record;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    File dir;
    File img;

    Dialog dialog;

    boolean photoTaken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = new File(getApplicationContext().getFilesDir().getPath() + File.separator + "images");

        if (!dir.exists()) {
            dir.mkdir();
        }

        secretNumber = ((int)(Math.random() * 100));

        Button button = findViewById(R.id.buttonPlay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.numberInput);
                createAlert(Integer.parseInt(input.getText().toString()) == secretNumber);
                input.setText(null);
            }
        });
    }

    public void createAlert(final boolean itsOk) {
        attempt++;
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);
        builder.setMessage(itsOk ? "Número correcto!" : "Ese no es el número secreto" + secretNumber);
        builder.setTitle(itsOk ? "Correcto" : "Fallo!");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (itsOk) {
                    createDialogRegister();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void createDialogRegister() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_record);

        Button takePhoto = dialog.findViewById(R.id.buttonPhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        Button registerRecord = dialog.findViewById(R.id.buttonRegister);
        registerRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = dialog.findViewById(R.id.name);
                if (photoTaken && name.getText().toString() != "") {
                    saveRecord(new Record(name.getText().toString(), attempt, Uri.fromFile(img)));
                    startActivity(new Intent(MainActivity.this, Ranking.class));
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.show();

        dialog.getWindow().setAttributes(lp);
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = dialog.findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);

            photoTaken = true;
            OutputStream os = null;
            try {
                if (dir.list().length>0){
                    img = new File(dir, (Integer.parseInt(dir.listFiles()[dir.listFiles().length-1].getName().substring(0,dir.listFiles()[dir.listFiles().length-1].getName().length()-4))+1) + ".png");
                }else{
                    img = new File(dir, "1.png");
                }
                os = new FileOutputStream(img);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveRecord(Record record) {
        this.record = record;
        try {
            if(!new File("data").exists()) new File("data").mkdir();
            if(!new File("data" + File.separator + "persistance.txt").exists()) new File("data" + File.separator + "persistance.txt").createNewFile();
            FileWriter writer = new FileWriter("data" + File.separator +
                    "persistence.txt", true);
            writer.write(record.intentos + ";" + record.nombre + ";" + record.image);
            writer.write("\r\n");
            writer.close();
        } catch (Exception e){
            Log.d("Error", e.toString());
        }
    }

}
