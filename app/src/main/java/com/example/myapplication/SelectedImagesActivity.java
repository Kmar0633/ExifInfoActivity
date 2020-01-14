package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SelectedImagesActivity extends AppCompatActivity {


    ImageView coverImage;
    ImageView rightArrowImage;
    Bitmap bitmap;
    String uriString;
    Uri imageUri;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_image_activity);
        String uri = "@drawable/right_arrow_icon";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        coverImage=(ImageView)  findViewById(R.id.img_selectedImageActivity_selectedImg);
        rightArrowImage=(ImageView)  findViewById(R.id.img_selectedImageActivity_rightArrow);
        Drawable res = getResources().getDrawable(imageResource);
        rightArrowImage.setImageDrawable(res);
        Intent intent = getIntent();
        try {
            uriString = intent.getStringExtra("Selected Image");
            imageUri = Uri.parse(uriString);
            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            Log.e("est",uriString);
            coverImage.setImageBitmap(bitmap);
            rightArrowImage.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                  //  final Intent intent = new Intent(getApplicationContext(), ExifInfoActivity.class);
                 //   Log.e("imageUri",imageUri.toString());
                //    intent.putExtra("Selected final Image",imageUri.toString());
                 //

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Selected final Image",uriString);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
//                    startActivity(returnIntent);
                }
            });

        }

        catch(Exception e){

        }


    }
}
