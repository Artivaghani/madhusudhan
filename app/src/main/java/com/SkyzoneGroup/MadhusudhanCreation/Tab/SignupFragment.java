package com.SkyzoneGroup.MadhusudhanCreation.Tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.HomActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SignupFragment extends Fragment {

    private static final String TAG = SignupFragment.class.getSimpleName();
    TextInputLayout inputLayoutFirstName, inputLayoutLastName, inputLayoutEmail, inputLayoutPhoneNo, inputLayoutPassword;
    TextInputEditText etFname, etLname, etPhoneNo, etPassword, etEmail;
    TextView tvbtnSignUp;
    ApiInterface apiService;
    String Mail = "";
    ProgressDialog pDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public SignupFragment() {

    }


    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);


        inputLayoutFirstName = (TextInputLayout) view.findViewById(R.id.inputLayoutFirstName);
        inputLayoutLastName = (TextInputLayout) view.findViewById(R.id.inputLayoutLastName);
        inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.inputLayoutEmail);
        inputLayoutPhoneNo = (TextInputLayout) view.findViewById(R.id.inputLayoutPhoneNo);
        inputLayoutPassword = (TextInputLayout) view.findViewById(R.id.inputLayoutPassword);


        etFname = (TextInputEditText) view.findViewById(R.id.etFName);
        etLname = (TextInputEditText) view.findViewById(R.id.etLName);
        etPhoneNo = (TextInputEditText) view.findViewById(R.id.etPhoneNo);
        etPassword = (TextInputEditText) view.findViewById(R.id.etPassword);
        etEmail = (TextInputEditText) view.findViewById(R.id.etEmail);

        tvbtnSignUp = (TextView) view.findViewById(R.id.tvbtnSingup);

        tvbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupdeta();
            }
        });
        return view;
    }

    private void signupdeta() {

        final String Fname = String.valueOf(etFname.getText());
        final String lname = String.valueOf(etLname.getText());
//        final String Phoneno = String.valueOf(etPhoneNo.getText());
        final String Email = String.valueOf(etEmail.getText());
        final String Password = String.valueOf(etPassword.getText());

        if ( Fname == null || Fname.isEmpty() || lname == null || lname.isEmpty() || Email == null || Email.isEmpty()
                || Password == null || Password.isEmpty())
        {

            if (Fname == null || Fname.isEmpty()) {
                inputLayoutFirstName.setError("Please Enter FirstName.");
            } else {
                inputLayoutFirstName.setErrorEnabled(false);
            }

            if (lname == null || lname.isEmpty()) {
                inputLayoutLastName.setError("Please Enter LastName.");
            } else {
                inputLayoutLastName.setErrorEnabled(false);
            }

            if (Email == null || Email.isEmpty()) {
                inputLayoutEmail.setError("Please Enter Email Id.");
            } else {
                inputLayoutEmail.setErrorEnabled(false);
            }
            if (Password == null || Password.isEmpty()) {
                inputLayoutPassword.setError("Please Enter Min 6 Digit Password.");
            } else {
                inputLayoutPassword.setErrorEnabled(false);
            }

//            if (Phoneno.length() != 10) {
//                inputLayoutPhoneNo.setError("Please Enter 10 Digit No.");
//            } else {
//                inputLayoutPhoneNo.setErrorEnabled(false);
//            }

        } else if (Email.matches(emailPattern))
            {
                inputLayoutEmail.setErrorEnabled(false);
                if (Password.length() >= 6) {
                    sigmupuser(Fname, lname, Email, Password);
                }else {
                    inputLayoutPassword.setErrorEnabled(false);
                }
//                Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            }
            else
            {
                inputLayoutEmail.setError("Please Enter valid  Email Id.");
            }
//            Toast.makeText(getActivity(), "getdata", Toast.LENGTH_SHORT).show();




    }


    private void sigmupuser(String fname, String lname, final String email,
                            final String password) {

        // Toast.makeText(getActivity(), name + lname +mail + password , Toast.LENGTH_SHORT).show();
        if (password.length() >= 6) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // Call<Responce> call = apiService.registration(name, lname, mail, password);

            Call<Responce> call = apiService.registration(fname, lname, email, password);
            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {

                    Log.d(TAG, "onResponse: >>>>" + response.body().getSuccess());
                    pDialog.dismiss();

                    if (response.body().getSuccess() == 0) {
                        //  pDialog.dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Call<Responce> call1 = apiService.checkLogin(email, password);
                        call1.enqueue(new Callback<Responce>() {
                            @Override
                            public void onResponse(Call<Responce> call, Response<Responce> response) {
                                List<Datum> users = response.body().getData();
                                Log.d(TAG, "USER :" + users.size());
                                // pDialog.dismiss();

                                Log.d(TAG, "USER ID :" + users.get(0).getCustomerId());
                                Log.d(TAG, "USER NAME:" + users.get(0).getCustomerName());
                                Log.d(TAG, "USER EMAIL:" + users.get(0).getCustomerEmail());
                                Log.d(TAG, "USER SATUS:" + response.body().getSuccess());

                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPref", MODE_PRIVATE).edit();
                                editor.putString("USER_ID", users.get(0).getCustomerId());
                                editor.putString("USER_NAME", users.get(0).getCustomerName());
                                editor.putString("USER_EMAIL", users.get(0).getCustomerEmail());
                                editor.putString("USER_PHONENO", users.get(0).getCustomMobile());
                                editor.putBoolean("LOGIN", true);
                                editor.commit();

                                Intent intent = new Intent(getActivity(), HomActivity.class);
                                startActivity(intent);

                                Toast.makeText(getActivity(), "successss", Toast.LENGTH_SHORT).show();
                            }


                            @Override
                            public void onFailure(Call<Responce> call, Throwable t) {
                                // Log error here since request failed
                                Log.e(TAG, t.toString());
                                pDialog.dismiss();
                                Toast.makeText(getActivity(), "Invalid ID PASSWORD / Internet Conection required", Toast.LENGTH_SHORT).show();
                            }
                        });


                        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
                        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));

                    }


                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    // Log error here since request failed
                    //    pDialog.dismiss();
                    Toast.makeText(getActivity(), "Something Wrong!!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG + ">>>>>>>>", t.toString());
                }
            });


        } else {
            //   pDialog.dismiss();
            Toast.makeText(getContext(), "The password must have at least 6 characters", Toast.LENGTH_LONG).show();
        }

        //    Log.d(TAG, "onClick: " + name + "\n" + lname + "\n" + mail + "\n" + password);

    }


    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etEmail:
                    validateEmail();
                    break;
                case R.id.etPassword:
                    validatePassword();
                    break;
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etPassword);
            return false;
        } else {

            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(etEmail);
            return false;
        } else {
            Mail = etEmail.getText().toString();
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
