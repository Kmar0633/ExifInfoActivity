package com.example.myapplication;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ExifInfoActivity extends Activity {


ImageView profileImage;
    private static int RESULT_LOAD_IMAGE = 1;
Uri imageUri;
    Bitmap bitmap;
    String finalUriString;
    TextView imageDate;
    TextView exifVersionDate;
    TextView orientationDate;
    TextView testText;
    String text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
profileImage=(ImageView)  findViewById(R.id.img_mainActivity_loadedImg);
        imageDate=(TextView)  findViewById(R.id.text_mainActivity_date);
        exifVersionDate=(TextView)  findViewById(R.id.text_mainActivity_exifVersion);
        orientationDate=(TextView) findViewById(R.id.text_mainActivity_orientation);
        testText=(TextView) findViewById(R.id.text_mainActivity_testText);
        Button buttonLoadImage = (Button) findViewById(R.id.btn_mainActivity_buttonLoadPicture);



        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
try {
    Intent gallery = new Intent();
    gallery.setType("image/*");
    gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
    startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), RESULT_LOAD_IMAGE);

}
catch (Exception e){
    e.printStackTrace();
}
            }
        });
        loadData();
        updateViews();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageUri=data.getData();

            try{



                final Intent intent = new Intent(getApplicationContext(), SelectedImagesActivity.class);
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                profileImage.setImageBitmap(bitmap);
               //
                getData(imageUri);
                intent.putExtra("Selected Image",imageUri.toString());


                saveData();

                startActivity(intent);

               // String finalUriString= intent.getStringExtra("Selected final Image");




            }
            catch(Exception e){
                e.printStackTrace();
            }

        }


    }

public void saveData(){
    SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
    SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString("text",imageUri.toString());
    editor.apply();
    Toast.makeText(this, "Data saved",Toast.LENGTH_SHORT);
}
public void loadData(){
    SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
    text=sharedPreferences.getString("text","");
}
public void updateViews(){
        testText.setText(text);
        try{
            Log.e("eeee",text);
            bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(text));
            profileImage.setImageBitmap(bitmap);
        }
        catch(Exception e){
e.printStackTrace();
        }
}
    public void getData(Uri imageUri){
       ; // the URI you've received from the other app
        InputStream in;

        try {

            in = getContentResolver().openInputStream(imageUri);

            ExifInterface exifInterface = new ExifInterface(in);
            // Now you can extract any Exif tag you want
            // Assuming the image is a JPEG or supported raw format
            Log.e("Exif Version: ",exifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION));
            imageDate.setText("Exif Version: "+exifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION));
            orientationDate.setText("Orientation: "+exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
            exifVersionDate.setText("Date time: "+exifInterface.getAttribute(ExifInterface.TAG_DATETIME));

Log.e("Date",exifInterface.getAttribute(ExifInterface.	TAG_DATETIME));

            Log.e("Orientation",exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String StringFromObject(Object x){
        String s="";
        if (x!=null){
            s=x.toString();

        }
        return s;
    }

}