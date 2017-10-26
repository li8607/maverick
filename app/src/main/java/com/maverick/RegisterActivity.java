package com.maverick;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.avos.avoscloud.AVException;
//import com.avos.avoscloud.AVFile;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.SignUpCallback;
import com.maverick.api.PearApi;
import com.maverick.bean.Info;
import com.maverick.bean.PearVideoInfoObj;
import com.maverick.bean.PearVideoInfoHome;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mRegisterFormView;

    public static void launch(Context context) {

        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set up the register form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button musernameSignInButton = (Button) findViewById(R.id.username_register_button);
        musernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://0c89a76q.api.lncld.net/1.1/")
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())//添加头文件
                .build();

        PearApi api = retrofit.create(PearApi.class);
        Call<Info> call = api.login("limingfei2", "12345");

        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Log.e("lmf", "info2 = " + response.body());
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.e("lmf", "t = " + t.toString());
                Log.e("lmf", "info = null22222info = null22222info = null22222");
            }
        });



        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl("http://app.pearvideo.com/clt/jsp/v2/")
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient2())//添加头文件
                .build();

        PearApi api3 = retrofit3.create(PearApi.class);
        Call<PearVideoInfoHome> call3 = api3.getPearVideoHome("1063871%2C1063985%2C1064069%2C1064123%2C1064078%2C1064186%2C1062372%2C1064164%2C1064081%2C1064176%2C1064070%2C1064019");

        call3.enqueue(new Callback<PearVideoInfoHome>() {
            @Override
            public void onResponse(Call<PearVideoInfoHome> call, Response<PearVideoInfoHome> response) {
                Log.e("lmf3", "info23333 = " + response.body());
            }

            @Override
            public void onFailure(Call<PearVideoInfoHome> call, Throwable t) {
                Log.e("lmf3", "t = " + t.toString());
                Log.e("lmf3", "info = null333333info = null22222info = null22222");
            }
        });
    }

    public static OkHttpClient genericClient2() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-Channel-Code", "official")
                                .addHeader("X-Client-Agent", "Xiaomi")
                                .addHeader("X-Client-Hash", "2f3d6ffkda95dlz2fhju8d3s6dfges3t")
                                .addHeader("X-Client-ID", "123456789123456")
                                .addHeader("X-Client-Version", "2.3.2")
                                .addHeader("X-Long-Token", "")
                                .addHeader("X-Platform-Type", "0")
                                .addHeader("X-Platform-Version", "5.0")
                                .addHeader("X-Serial-Num", "1492140134")
                                .addHeader("X-User-ID", "")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
        return httpClient;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("X-LC-Id", "0C89a76qNodEmSHBpCSTGgCX-gzGzoHsz")
                                .addHeader("X-LC-Key", "2Bcw0fzmDjYF6WXqEJumCKxR")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
        return httpClient;
    }

    private void attemptRegister() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

//            AVUser user = new AVUser();// 新建 AVUser 对象实例
//            user.setUsername(username);// 设置用户名
//            user.setPassword(password);// 设置密码
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher).compress(Bitmap.CompressFormat.PNG, 100, baos);
//                InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//                user.put("userImage", new AVFile("dadds", getBytes(isBm)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            user.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(AVException e) {
//                    if (e == null) {
//                        // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
//                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                        RegisterActivity.this.finish();
//                    } else {
//                        // 失败的原因可能有多种，常见的是用户名已经存在。
//                        showProgress(false);
//                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private boolean isusernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

