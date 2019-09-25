package com.SkyzoneGroup.MadhusudhanCreation;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Notification.Config;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.CATResponce;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class Inquiry extends AppCompatActivity
{

    private static int RESULT_LOAD_IMAGE = 1;
    EditText name,mobno,email,msg;
    String name1,mobno1,email1,msg1,pro_url;
    Button ok;
    ImageView imageView;
    ApiInterface apiService;
    FloatingActionButton choosepic;
    Uri selectedImage;
    String picturePath,imagepath123;
    String IMAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        apiService = ApiClient.getClient().create(ApiInterface.class);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Inquiry");

        Intent i=getIntent();
        pro_url=i.getStringExtra("PRO_URL");
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(R.raw.lodding).asGif().into(imageView);
        imageView.setVisibility(View.GONE);
        name=(EditText)findViewById(R.id.name) ;
        mobno=(EditText)findViewById(R.id.mobiledetail);
        email=(EditText)findViewById(R.id.emailid);
        msg=(EditText)findViewById(R.id.message);
        ok=(Button)findViewById(R.id.submit);


//        choosepic=findViewById(R.id.uploadpic);
//        choosepic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//            }
//        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Config.checkConnection(getApplicationContext()))
                {
//                    if (f_name.getText().toString().isEmpty() || d_o_b.getText().toString().isEmpty())
                    if (mobno.getText().toString().isEmpty())
                    {
                        Toast.makeText(Inquiry.this, "Please Fill Your mobile no...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        name1=name.getText().toString();
                        mobno1 = mobno.getText().toString();
                        msg1 = msg.getText().toString();
                        email1=email.getText().toString();
                        submitalldata(name1,email1,mobno1, msg1);
//                        if (mobno.getText().length() < 10) {
//                            Toast.makeText(Inquiry.this, "Enter Minimum 10 Digit of number...", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            if (!TextUtils.isEmpty(imagepath123)) {
//                                if (Config.checkConnection(getApplicationContext())) {
//                                    //                                /******************Retrofit***************/
//                                    try {
//                                        File compressedImageFile = new Compressor(Inquiry_Activity.this).compressToFile(new File(String.valueOf(imagepath123)));
//                                        uploadImage(compressedImageFile);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Internet Connection not availabal", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                //                            Toast.makeText(getApplicationContext(), "Please select your upload Image...", Toast.LENGTH_SHORT).show();
//                                submitalldata(mobno1, msg1);
//                            }
//                        }
                    }
                } else {
                    Toast.makeText(Inquiry.this, "please check internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
//    private void uploadImage(File file) {
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part body;
//
//        body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
//
//
//        Call<Example> memberCall=apiService.uploadImage(body);
//        memberCall.enqueue(new Callback<Example>() {
//
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//                Log.d(TAG, "onResponse: " +response.body().getResult());
//
//                Log.d(TAG, "onResponse:FILE PATH >>> " +response.body().getFilePath());
//                IMAGES=response.body().getFilePath();
//                // Response Success or Fail
//                if (response.isSuccessful()) {
//                    if (response.body().getResult().equals("success"))
//                    {
//                        submitalldata(mobno1,msg1);
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),  R.string.string_upload_fail, Toast.LENGTH_LONG).show();
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(),  R.string.string_upload_fail, Toast.LENGTH_LONG).show();
//                }
//
//                /**
//                 * Update Views
//                 */
//
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),   "Image not upload...", Toast.LENGTH_LONG).show();
//                Log.e(TAG, t.toString());
//            }
//        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            picturePath = cursor.getString(columnIndex);
//            Toast.makeText(getApplicationContext(),"path :"+selectedImage,Toast.LENGTH_LONG).show();
            cursor.close();
            imagepath123=picturePath;
            ImageView imageView = (ImageView) findViewById(R.id.imageview);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);



        }

    }
    private Bitmap getBitmapFromUri(Uri selectedImage) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(selectedImage, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void submitalldata(String name1,String email1,String mobno1,String msg1) {
        imageView.setVisibility(View.VISIBLE);

        try {
            Call<CATResponce> call = apiService.Inquiry(name1,pro_url,email1,mobno1,msg1);
            call.enqueue(new Callback<CATResponce>() {
                @Override
                public void onResponse(Call<CATResponce> call, retrofit2.Response<CATResponce> response) {
                    try
                    {
                        imageView.setVisibility(View.GONE);
                        try {
                            if (response.body().getSuccess().equals("0")) {
                                Toast.makeText(Inquiry.this, "Not Successfully Please Try Again..", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Inquiry.this, "Inquiry send Successfully...", Toast.LENGTH_SHORT).show();
                                mobno.setText("");
                                name.setText("");
                                msg.setText("");
                                email.setText("");
                            }
                        } catch (Exception e) {

                        }
                    }catch (Exception e){}

                }

                @Override
                public void onFailure(Call<CATResponce> call, Throwable t) {
                    imageView.setVisibility(View.GONE);
                    onBackPressed();
                    Toast.makeText(Inquiry.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

//            case R.id.share:
//                String appname = getString(R.string.app_name);
//                String ExternalString = getString(R.string.String);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, appname + "\n" + ExternalString + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
//                return true;
//
//            case R.id.rate:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        this.finish();
    }

}
