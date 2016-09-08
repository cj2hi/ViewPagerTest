package com.killer.viewpagertest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**自定义弹出对话框，创建JSON并解析JSON
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements View.OnClickListener {


    private TextView showJSON_TextView;
    private Button createJSON;
    private Button getJSON;


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view2, container, false);
        view.findViewById(R.id.view2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = inflater.inflate(R.layout.dialog_layout, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                builder.setTitle("自定义对话框");
                builder.setView(view1);
                final AlertDialog dialog = builder.create();
                dialog.show();
                view1.findViewById(R.id.dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        showJSON_TextView = (TextView) view.findViewById(R.id.view2_showJson_textView);
        createJSON = (Button) view.findViewById(R.id.view2_create_jsonbutton);
        getJSON = (Button) view.findViewById(R.id.view2_get_jsonbutton);

        createJSON.setOnClickListener(this);
        getJSON.setOnClickListener(this);


        return view;
    }

    // 创建JSON，类似于{"id":"10","news":"new12","author":["aa1","bb3"]},{"id":"10","news":"new12","author":["aa1","bb3"]}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view2_create_jsonbutton:
                createJson();

                break;
            case R.id.view2_get_jsonbutton:
                String json = showJSON_TextView.getText().toString();


                parseJson(json);

                break;
        }


    }

    /**
     * 解析JSON
     *
     * @param s 要解析的JSON字符串
     */
    private void parseJson(String s) {
        try {
            JSONArray j1 = new JSONArray(s);

            for (int i = 0; i < j1.length(); i++) {
                JSONObject json = (JSONObject) j1.get(i);
                int id = json.getInt("id");
                String news = json.getString("news");
                JSONArray author = json.getJSONArray("author");
                String author1 = author.getString(0);
                String author2 = author.getString(1);
                showJSON_TextView.append("\n");
                showJSON_TextView.append("id=" + id + "\n");
                showJSON_TextView.append("news=" + news + "\n");
                showJSON_TextView.append("author=[" + author1 + "," + author2 + "]\n");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 创建JSON
     */
    private void createJson() {
        JSONArray mainJson = new JSONArray();
        try {
            for (int i = 0; i < 2; i++) {
                JSONObject json = new JSONObject();
                json.put("id", 10);
                json.put("news", "new" + (new Random().nextInt(10) + 1));
                JSONArray author = new JSONArray();
                author.put("aa" + (new Random().nextInt(6) + 1)).put("bb" + new Random().nextInt(10));
                json.put("author", author);
                mainJson.put(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showJSON_TextView.setText(mainJson.toString());


    }
}
