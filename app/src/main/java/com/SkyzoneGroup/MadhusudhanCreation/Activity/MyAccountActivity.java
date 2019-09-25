package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity {
    LinearLayout ll_main;
    TextView textCartItemCount;
    private ImageView finalimg;
    private ImageView ic_back;
    private TextView txtPhone, tvSymbol;
    private ImageView iv_Share_More;
    private ImageView iv_Twitter;
    private ImageView iv_facebook;
    private ImageView iv_hike;
    private ImageView iv_instagram;
    private ImageView iv_whatsapp;
    Button btsave;
    ApiInterface apiService;
    String sharedata ="Start your reselling business from home with zero investment \n\n\n Join me and more people who are earning money from home using Madhusudhan Creation App.";
    public static String app_name = "Madhusudhan Creation";
    public static String acc_link = "https://play.google.com/store/apps/details?id=com.first.choice";
    TextView txtMemberName, txtEmailId;
    LinearLayout llLogout, ll_invite_friends, ll_address, ll_myorder, ll_com_service, ll_Changepwd, ll_Rate_us;
    ImageView editAccountDetails;
    ImageView icon_cancel;
    TextInputEditText inputPassword, inputCPassword;
    String USER_ID;
    ProgressDialog pDialog;
    TextView tvLogOut, tvLogIn;
    int mCartItemCount = 0;
    LinearLayout llfaq, llcallus, llaboutus;
    List<CartList> itemss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Madhusudhan Creation");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");


        bindview();
        fatchCartItem(USER_ID);
    }

    private void fatchCartItem(String user_id) {

        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {
            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
                try {
                    Log.d(">>> ", "Number of Cart Item received001:" + itemss.size());
                    mCartItemCount = itemss.size();
                    setupBadge(mCartItemCount);
                } catch (Exception ex) {

                }

            }

            @Override
            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                Log.e(">> ", t.toString());
                pDialog.dismiss();
            }
        });
    }

    private void bindview() {
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_main.setVisibility(View.GONE);
        final ProgressDialog progress = new ProgressDialog(MyAccountActivity.this);
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.setMessage("Please wait...");
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                ll_main.setVisibility(View.VISIBLE);
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2000);

        tvSymbol = (TextView) findViewById(R.id.tvSymbol);
        txtMemberName = (TextView) findViewById(R.id.txtMemberName);
        txtEmailId = (TextView) findViewById(R.id.txtEmailId);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        tvLogOut = (TextView) findViewById(R.id.tvLogOut);
        tvLogIn = (TextView) findViewById(R.id.tvLogIn);
        llLogout = (LinearLayout) findViewById(R.id.ll_logout);
        ll_myorder = (LinearLayout) findViewById(R.id.ll_myorder);
        ll_Changepwd = (LinearLayout) findViewById(R.id.ll_Changepwd);
        ll_Rate_us = (LinearLayout) findViewById(R.id.ll_Rate_us);
        ll_invite_friends = (LinearLayout) findViewById(R.id.ll_invite_friends);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_com_service = (LinearLayout) findViewById(R.id.ll_com_service);
        editAccountDetails = (ImageView) findViewById(R.id.editAccountDetails);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);

        final Boolean Login = prefs.getBoolean("LOGIN", false);
        if (Login.booleanValue() == false) {
            tvLogOut.setVisibility(View.GONE);
            tvLogIn.setVisibility(View.VISIBLE);
        } else {
            tvLogOut.setVisibility(View.VISIBLE);
            tvLogIn.setVisibility(View.GONE);

        }
        if (prefs.getString("USER_EMAIL", null) == null) {
            txtMemberName.setText("USER NAME");
            txtEmailId.setText("USER EMAIL ID");
            txtPhone.setText("USER_PHONENO");
        } else {
            pDialog = new ProgressDialog(MyAccountActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            Call<Responce> call = apiService.get_customer_detail(USER_ID);
            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {
                    pDialog.dismiss();
                    List<Datum> items = response.body().getData();
                    if (response.body().getSuccess() == 0) {
                        Toast.makeText(MyAccountActivity.this, "User Not Login", Toast.LENGTH_SHORT).show();
                    } else {
                        txtMemberName.setText(items.get(0).getFirstname() + " " + items.get(0).getLastname());
                        txtEmailId.setText(items.get(0).getEmail());
//                        txtPhone.setText("+91 - " + (items.get(0).getCustomMobile()));
                        try {
                            char first = items.get(0).getFirstname().charAt(0);
                            char last = items.get(0).getLastname().charAt(0);
                            tvSymbol.setText("" + first + last);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        //   tvSymbol.setText("" + first + last);
                    }
                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    Log.e(">> ", t.toString());
                    pDialog.dismiss();
                    Toast.makeText(MyAccountActivity.this, "Try.....", Toast.LENGTH_SHORT).show();
                }
            });

//            txtMemberName.setText(prefs.getString("USER_NAME", null));
//            txtEmailId.setText(prefs.getString("USER_EMAIL", null));
//            txtPhone.setText("+91 - " + prefs.getString("USER_PHONENO", null));
//
//            String name = prefs.getString("USER_NAME", null);
//
//            int firstSpace = name.indexOf(" "); // detect the first space character
//            String firstName = name.substring(0, firstSpace);  // get everything upto the first space character
//            String lastName = name.substring(firstSpace).trim();
//
//            char first = firstName.charAt(0);
//            char last = lastName.charAt(0);
//            /// Toast.makeText(this, ">>>"+first+" >> "+last, Toast.LENGTH_SHORT).show();
//            tvSymbol.setText("" + first + last);
//            /// Toast.makeText(this, ">>>"+first+" >> "+last, Toast.LENGTH_SHORT).show();
//            tvSymbol.setText("" + first + last);
            // panel = headerLayout.findViewById(R.id.viewId);
        }
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Login.booleanValue() == false) {
                    SharedPreferences settings = MyAccountActivity.this.getSharedPreferences("myPref", MyAccountActivity.this.MODE_PRIVATE);
                    settings.edit().clear().commit();

                    Intent intent = new Intent(MyAccountActivity.this, HomActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    tvLogOut.setVisibility(View.GONE);
                    tvLogIn.setVisibility(View.VISIBLE);

                } else {
                    Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                            Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();
                    tvLogOut.setVisibility(View.VISIBLE);
                    tvLogIn.setVisibility(View.GONE);
                }

//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ll_invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) MyAccountActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ;
                View promptsView1 = li.inflate(R.layout.user_invite_dialog_box, null);

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MyAccountActivity.this);
                icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                iv_whatsapp = (ImageView) promptsView1.findViewById(R.id.iv_whatsapp);
                iv_instagram = (ImageView) promptsView1.findViewById(R.id.iv_instagram);
                iv_facebook = (ImageView) promptsView1.findViewById(R.id.iv_facebook);
                iv_Twitter = (ImageView) promptsView1.findViewById(R.id.iv_Twitter);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView1);
                alertDialogBuilder1.setCancelable(true);
                final AlertDialog alertDialog1 = alertDialogBuilder1.create();

                iv_whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shareicon);
                        String imgBitmapPath = MediaStore.Images.Media.insertImage(MyAccountActivity.this.getContentResolver(), imgBitmap, "title", null);
                        Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,  sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                        try {
                            shareIntent.setPackage("com.whatsapp");
                            startActivity(shareIntent);
                            return;
                        } catch (Exception e) {
                            Toast.makeText(MyAccountActivity.this, "WhatsApp doesn't installed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
                iv_instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shareicon);
                        String imgBitmapPath = MediaStore.Images.Media.insertImage(MyAccountActivity.this.getContentResolver(), imgBitmap, "title", null);
                        Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_TITLE,  sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                        try {
                            shareIntent.setPackage("com.instagram.android");
                            startActivity(shareIntent);
                            return;
                        } catch (Exception e4) {
                            Toast.makeText(MyAccountActivity.this, "Instagram doesn't installed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
                iv_facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shareicon);
                        String imgBitmapPath = MediaStore.Images.Media.insertImage(MyAccountActivity.this.getContentResolver(), imgBitmap, "title", null);
                        Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                       // shareIntent.putExtra(Intent.EXTRA_TITLE, sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,  sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);

                        try {
                            shareIntent.setPackage("com.facebook.katana");
                            startActivity(shareIntent);
                            return;
                        } catch (Exception e3) {
                            Toast.makeText(MyAccountActivity.this, "Facebook doesn't installed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
                iv_Twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shareicon);
                        String imgBitmapPath = MediaStore.Images.Media.insertImage(MyAccountActivity.this.getContentResolver(), imgBitmap, "title", null);
                        Uri imgBitmapUri = Uri.parse(imgBitmapPath);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_TITLE, sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        //shareIntent.putExtra(Intent.EXTRA_TEXT,  sharedata + "\n" + "\n" + " Download Now : " +"\n" + acc_link);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                        try {
                            shareIntent.setPackage("com.twitter.android");
                            startActivity(shareIntent);
                            return;
                        } catch (Exception e5) {
                            Toast.makeText(MyAccountActivity.this, "Twitter doesn't installed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

                icon_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });

                // show it
                alertDialog1.show();

                ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(MyAccountActivity.this, R.anim.shake));

            }
        });
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyAccountActivity.this, MyAccountAddressActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //  MyAccountActivity.this.finish();
            }
        });
        ll_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountActivity.this, MyOrderActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ll_com_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) MyAccountActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ;
                View promptsView1 = li.inflate(R.layout.customer_service, null);

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MyAccountActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView1);
                alertDialogBuilder1.setCancelable(true);
                final AlertDialog alertDialog1 = alertDialogBuilder1.create();

                icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                llfaq = (LinearLayout) promptsView1.findViewById(R.id.llfaq);
                llcallus = (LinearLayout) promptsView1.findViewById(R.id.llcallus);
                llaboutus = (LinearLayout) promptsView1.findViewById(R.id.llaboutus);

                icon_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });
                llfaq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                        Intent intent = new Intent(MyAccountActivity.this, WebviewActivity.class);
                        intent.putExtra("TITLE", "FAQ");
                        intent.putExtra("ID", 1);
                        startActivity(intent);
                    }
                });
                llcallus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                        Intent intent = new Intent(MyAccountActivity.this, WebviewActivity.class);
                        intent.putExtra("TITLE", "Call Us");
                        intent.putExtra("ID", 3);
                        startActivity(intent);
                    }
                });
                llaboutus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                        Intent intent = new Intent(MyAccountActivity.this, WebviewActivity.class);
                        intent.putExtra("TITLE", "About Us");
                        intent.putExtra("ID", 2);
                        startActivity(intent);
                    }
                });


                // show it
                alertDialog1.show();

                ((ViewGroup) alertDialog1.getWindow().getDecorView())
                        .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(MyAccountActivity.this, R.anim.shake));
            }
        });
        ll_Changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = MyAccountActivity.this.getSharedPreferences("myPref", MODE_PRIVATE);
                final Boolean Login = prefs.getBoolean("LOGIN", false);
                if (Login.booleanValue() == false) {
                    Toast.makeText(MyAccountActivity.this, "User Not Login", Toast.LENGTH_SHORT).show();
                } else {
                    LayoutInflater li = (LayoutInflater) MyAccountActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View promptsView1 = li.inflate(R.layout.user_changepwd_box, null);

                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MyAccountActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder1.setView(promptsView1);
                    alertDialogBuilder1.setCancelable(true);
                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                    btsave = (Button) promptsView1.findViewById(R.id.btVproduct);
                    icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                    icon_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog1.dismiss();
                        }
                    });
                    inputPassword = (TextInputEditText) promptsView1.findViewById(R.id.etPassword);
                    inputCPassword = (TextInputEditText) promptsView1.findViewById(R.id.etCPassword);

                    btsave.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String strPass1 = inputPassword.getText().toString();
                            String strPass2 = inputCPassword.getText().toString();
                            if (strPass1.length() < 6 || strPass2.length() < 6) {

                                Toast.makeText(MyAccountActivity.this, "Plase Enter Password In 6 Digits...", Toast.LENGTH_SHORT).show();
                            } else if (strPass1.equals(strPass2)) {
                                pDialog = new ProgressDialog(MyAccountActivity.this);
                                pDialog.setMessage("Please wait...");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();
                                //    Toast.makeText(MyAccountActivity.this, USER_ID + strPass2, Toast.LENGTH_SHORT).show();
                                Call<Responce> call = apiService.changepassword(USER_ID, strPass2);
                                call.enqueue(new Callback<Responce>() {
                                    @Override
                                    public void onResponse(Call<Responce> call, Response<Responce> response) {
                                        pDialog.dismiss();
                                        if (response.body().getSuccess() == 1) {
                                            Log.d("Main", "DaDATADADD" + response.body().getSuccess());
                                            pDialog.dismiss();
                                            alertDialog1.dismiss();
                                            SharedPreferences settings = MyAccountActivity.this.getSharedPreferences("myPref", MyAccountActivity.this.MODE_PRIVATE);
                                            settings.edit().clear().commit();
                                            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(MyAccountActivity.this, "Password Updated Successfully...", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<Responce> call, Throwable t) {
                                        Log.e(">> ", t.toString());
                                        pDialog.dismiss();
                                    }
                                });
                            } else {

                                Toast.makeText(MyAccountActivity.this, "Password Don't Match", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                    // show it
                    alertDialog1.show();

                    ((ViewGroup) alertDialog1.getWindow().getDecorView())
                            .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(MyAccountActivity.this, R.anim.shake));

                }


            }
        });
        editAccountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    protected void openDialog() {
        LayoutInflater li = (LayoutInflater) MyAccountActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View promptsView1 = li.inflate(R.layout.user_profile_update, null);

        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MyAccountActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder1.setView(promptsView1);
        alertDialogBuilder1.setCancelable(true);

        final AlertDialog alertDialog1 = alertDialogBuilder1.create();
        final TextInputEditText etFName = (TextInputEditText) promptsView1.findViewById(R.id.etFName);
        final TextInputEditText etLName = (TextInputEditText) promptsView1.findViewById(R.id.etLName);
        final TextInputEditText tv_phone_no = (TextInputEditText) promptsView1.findViewById(R.id.tv_phone_no);
        final TextInputEditText etEmail = (TextInputEditText) promptsView1.findViewById(R.id.etEmail);

        final TextInputLayout inputLayoutFirstName = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutFirstName);
        final TextInputLayout inputLayoutLastName = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutLastName);
        final TextInputLayout inputLayoutPhoneNo = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutPhoneNo);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        Call<Responce> call = apiService.get_customer_detail(USER_ID);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> items = response.body().getData();
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    Toast.makeText(MyAccountActivity.this, "User Not Login", Toast.LENGTH_SHORT).show();
                } else {
                    ll_main.setVisibility(View.VISIBLE);
                    etFName.setText(items.get(0).getFirstname());
                    etLName.setText(items.get(0).getLastname());
                    etEmail.setText(items.get(0).getEmail());
                    tv_phone_no.setText(items.get(0).getCustomMobile());
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                Log.e(">> ", t.toString());
            }
        });
        tv_phone_no.setText(prefs.getString("USER_PHONENO", null));
        TextView tv_save = (TextView) promptsView1.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fname = etFName.getText().toString();
                String LName = etLName.getText().toString();
                String phone_no = tv_phone_no.getText().toString();
//                SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
//                editor.putString("USER_PHONENO", phone_no);
//                editor.commit();

                if (Fname == null || Fname.isEmpty() || LName == null || LName.isEmpty() || phone_no == null || phone_no.isEmpty() || phone_no.length() != 10) {
                    if (Fname == null || Fname.isEmpty()) {
                        inputLayoutFirstName.setError("Please Enter FirstName.");
                    } else {
                        inputLayoutFirstName.setErrorEnabled(false);
                    }
                    if (LName == null || LName.isEmpty()) {
                        inputLayoutLastName.setError("Please Enter LastName.");
                    } else {
                        inputLayoutLastName.setErrorEnabled(false);
                    }
//                    if (phone_no.length() != 10) {
//                        inputLayoutPhoneNo.setError("Please Enter 10 Digit No.");
//
//
//                    } else {
//                        inputLayoutPhoneNo.setErrorEnabled(false);
//                    }
                } else {
                    Call<Responce> call = apiService.edit_customer_profile(USER_ID, Fname, LName, phone_no);
                    call.enqueue(new Callback<Responce>() {
                        @Override
                        public void onResponse(Call<Responce> call, Response<Responce> response) {
                            List<Datum> items = response.body().getData();
                            pDialog.dismiss();
                            if (response.body().getSuccess() == 0) {
                                Toast.makeText(MyAccountActivity.this, "Updated not Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                ll_main.setVisibility(View.VISIBLE);
                                Toast.makeText(MyAccountActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                bindview();
                                alertDialog1.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Responce> call, Throwable t) {
                            Log.e(">> ", t.toString());
                            pDialog.dismiss();
                        }
                    });
                }
            }
        });
        icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });


        // show it
        alertDialog1.show();

        ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(MyAccountActivity.this, R.anim.shake));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mycart, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);

        setupBadge(mCartItemCount);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccountActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

        return true;
    }

    public void setupBadge(int upBadge) {
        if (textCartItemCount != null) {
            if (this.mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, upBadge)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        Intent intent = new Intent(MyAccountActivity.this, HomActivity.class);
        startActivity(intent);
        finish();
    }
}
