package com.killer.viewpagertest;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**运用SharedPreferences记录是否保存用户名，通过SQLiteDatabase记录注册的用户及密码
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements View.OnClickListener{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText etUsername;
    private EditText psd;
    private CheckBox checkBox;
    private Button login;
    private Button cancel;
    private SQLiteDatabase sqLiteDatabase;

    private String userName;
    private String password;


    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view3, container, false);
        etUsername = (EditText) view.findViewById(R.id.view3_username);
        psd = (EditText) view.findViewById(R.id.view3_psd);
        checkBox = (CheckBox) view.findViewById(R.id.view3_checkBox);
        login = (Button) view.findViewById(R.id.view3_login);
        cancel = (Button) view.findViewById(R.id.view3_reset);

        preferences = view.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        // 使用SharedPreferences.Editor添加或删除数据
        editor = preferences.edit();

        login.setOnClickListener(this);
        cancel.setOnClickListener(this);


        String user = preferences.getString("username","");
        if("".equals(user)){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
            etUsername.setText(user);
        }


        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getContext().getFilesDir() + "user.db",null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS login_info " +
                "(_id integer primary key autoincrement," +
                "user text not null,password text not null)");


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view3_login:
                userName = etUsername.getText().toString();
                password = psd.getText().toString();
                // 判断是否没输入内容
                if(!"".equals(userName) && !"".equals(password)) {
                    Cursor login_info = sqLiteDatabase.query("login_info", new String[]{"user", "password"}, "user=?", new String[]{userName}, null, null, null);
                    // 判断查询结果是否有结果
                    if(login_info != null && login_info.moveToNext()) {

                        String u1 = login_info.getString(login_info.getColumnIndex("user"));
                        String p1 = login_info.getString(login_info.getColumnIndex("password"));

                        // 判断用户名密码是否相同
                        if (u1.equals(userName) && p1.equals(password)) {
                            if (checkBox.isChecked()) {
                                editor.putString("username", userName);
                                editor.commit();
                            } else {
                                editor.remove("username");
                                editor.commit();
                            }
                            Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "没注册的用户名！", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(getContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.view3_reset:
                userName = etUsername.getText().toString();
                password = psd.getText().toString();
                // 判断是否没输入内容
                if(!"".equals(userName) && !"".equals(password)) {
                    Cursor login_info = sqLiteDatabase.query("login_info", new String[]{"user"}, "user=?", new String[]{userName},  null, null, null);

                    // 判断查询结果是否有结果
                    if (login_info != null && login_info.moveToNext()) {
                        Toast.makeText(getContext(), "已经存在此用户名" , Toast.LENGTH_SHORT).show();
                    } else {
                        // 无结果就新加入数据
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("user", userName);
                        contentValues.put("password", password);
                        sqLiteDatabase.insert("login_info", null, contentValues);
                        Toast.makeText(getContext(), userName + "已注册成功！", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}
