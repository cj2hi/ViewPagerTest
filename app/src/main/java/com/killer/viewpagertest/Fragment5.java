package com.killer.viewpagertest;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.killer.service.AddressService;
import com.killer.service.WeatherService;


/**查询手机归属地和城市天气情况
 * A simple {@link Fragment} subclass.
 */
public class Fragment5 extends Fragment implements View.OnClickListener{

    private EditText phoneNumber;
    private Button search;
    private TextView showTV;
    private Button weather;
    private EditText cityName;
    private TextView showWeather;


    public Fragment5() {
        // Required empty public constructor
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    String address1 = (String) msg.obj;
                    showTV.setText(address1);

                    break;
                case 2:
                    String weather = (String) msg.obj;
                    showWeather.setText(weather);

                    break;

            }


        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view5, container, false);
        phoneNumber = (EditText) view.findViewById(R.id.view5_et_phone_number);
        search = (Button) view.findViewById(R.id.view5_bt_search);
        showTV = (TextView) view.findViewById(R.id.view5_tv_show);

        cityName = (EditText) view.findViewById(R.id.view5_et_city);
        weather = (Button) view.findViewById(R.id.view5_bt_weather);
        showWeather = (TextView) view.findViewById(R.id.view5_tv_show_weather);




        search.setOnClickListener(this);
        weather.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view5_bt_search:
                new Thread(){
                    @Override
                    public void run() {
                        String mobile = phoneNumber.getText().toString();

                        try {
                            String address = AddressService.getAddress(getContext(),mobile);

                            if(null != address) {
                                Message msg = Message.obtain();
                                msg.arg1 = 1;
                                msg.obj = address;
                                handler.sendMessage(msg);
                            }else{
                                Toast.makeText(getContext(),R.string.search_error,Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),R.string.search_error,Toast.LENGTH_SHORT).show();
                        }


                    }
                }.start();


                break;
            case R.id.view5_bt_weather:
                new Thread(){
                    @Override
                    public void run() {
                        String city = cityName.getText().toString();
                        try {
                            String weatherInfo = WeatherService.getAddress(getContext(), city);
                            if (null != weatherInfo) {
                                Message msg = Message.obtain();
                                msg.arg1 = 2;
                                msg.obj = weatherInfo;
                                handler.sendMessage(msg);
                            }else {
                                Toast.makeText(getContext(),R.string.search_error,Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),R.string.search_error,Toast.LENGTH_SHORT).show();
                        }


                    }
                }.start();


                break;

        }
    }
}
