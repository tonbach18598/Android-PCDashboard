package com.example.pcdashboard.API;

import android.content.Context;
import android.util.Log;

import com.example.pcdashboard.Model.Token;
import com.example.pcdashboard.Model.User;
import com.example.pcdashboard.Request.TokenRequest;
import com.example.pcdashboard.Utility.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountService {
    private static AccountService accountService;
    private static IAccountService iAccountService;
    private Context context;
    private AccountListener listener;

    public interface AccountListener {
        void onTokenSuccess();

        void onSelfSuccess();

        void onForgotSuccess();

        void onForgotFailure();

        void onLoginFailure();
    }

    private AccountService(Context context) {
        this.context = context;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServiceManager.url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        iAccountService = retrofit.create(IAccountService.class);
    }

    public void setListener(AccountListener listener) {
        this.listener = listener;
    }

    public static AccountService getInstance(Context context) {
        if (accountService == null)
            accountService = new AccountService(context);
        return accountService;
    }

    public void getToken(String userId, String password) {
        Call<Token> call = iAccountService.getToken(new TokenRequest(userId, password));
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                if (token != null) {
                    SharedPreferencesUtil.saveToken(context, token);
                    listener.onTokenSuccess();
                } else listener.onLoginFailure();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
            }
        });
    }

    public void forgetPassword(String userId) {
        Call<String> call = iAccountService.forgetPassword(userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String email = response.body();
                if (email != null) {
                    SharedPreferencesUtil.saveEmail(context, email);
                    listener.onForgotSuccess();
                } else listener.onForgotFailure();
                Log.i("tag", "forgetPassword " + SharedPreferencesUtil.loadEmail(context));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onForgotFailure();
            }
        });
    }

//    public void changePassword(String userId, String oldPassword, String newPassword) {
//      Call<Boolean>call=iAccountService.changePassword(userId,oldPassword,newPassword);
//      call.enqueue(new Callback<Boolean>() {
//          @Override
//          public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//              listener.onDepartmentSuccess();;
//          }
//
//          @Override
//          public void onLoginFailure(Call<Boolean> call, Throwable t) {
//              listener.onLoginFailure();
//          }
//      });
//    }
//
//    public void updateInfo(String userId, String email, String phone) {
//        Call<Boolean>call=iAccountService.updateInfo(userId,email,phone);
//        call.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                listener.onDepartmentSuccess();
//            }
//
//            @Override
//            public void onLoginFailure(Call<Boolean> call, Throwable t) {
//                listener.onLoginFailure();
//            }
//        });
//    }

    public void getSelf(String userId) {
        String token = SharedPreferencesUtil.loadToken(context).getTokenType() + " " + SharedPreferencesUtil.loadToken(context).getAccessToken();
        Call<User> call = iAccountService.getSelf(token, userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User self = response.body();
                Log.i("tag", "getSelf " + self.getName() + self.getId());
                SharedPreferencesUtil.saveSelf(context, self);
                listener.onSelfSuccess();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

//    public void getAllUsers(String userId) {
//        Call<ArrayList<User>> call = iAccountService.getAllUsers(userId);
//        call.enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//                ArrayList<User> users = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//
//            }
//        });
//    }
}
