package com.example.greendao1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greendao1.application.MyApplication;
import com.example.greendao1.entity.User;
import com.example.greendao1.gen.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private UserDao userDao;

    private TextView show;

    private Button add;

    private Button update;

    private Button query;

    private Button deleteAll;

    private ListView lv;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = MyApplication.getInstance().getSession().getUserDao();  //创建数据表
        initView();

    }

    /*初始化组件*/
    public void initView(){
        show = (TextView)findViewById(R.id.show);
        add = (Button)findViewById(R.id.insert);
        query = (Button)findViewById(R.id.query);
        update = (Button)findViewById(R.id.update);
        deleteAll = (Button)findViewById(R.id.delete);
        lv = (ListView) findViewById(R.id.listView);/*定义一个动态数组*/

        add.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);
        deleteAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert:
                insertUser();
            case R.id.query:
                selectUser();
                break;
            case R.id.update:
                updateUser();
                break;
            case R.id.delete:
                deleteAll();
                break;
            default:
                break;
        }
    }

    /*t添加用户信息*/
    private void insertUser(){
        user = new User(null,"李坚富","1204");
        userDao.insert(user);
        show.setText(user.toString());
        Log.i("插入数据为：",user.toString());
//        show.setText(user.toString());
    }

    /*更新用户信息*/
    private void updateUser(){
        //通过多种方式获取实例
//        UserInfo userInfo = dao.queryBuilder().where(UserInfoDao.Properties.UserName.eq("qiufeng4")).build().unique();
        user = userDao.load((long)1);
        if (user != null) {
            user.setName("傻妞儿");
            userDao.update(user);
            show.setText(user.toString());
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    /*查询*/
    private void selectUser(){
        List<User> users = userDao.loadAll();

        try{
            if (users != null) {
                user = users.get(users.size() - 1);
                for (int i = 0; i < users.size(); i++) {
                    Log.i("查询的数据：", users.get(i).toString());
//                    show.setText(users.toString());
                    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();/*在数组中存放数据*/
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id","第"+ i +"行");
                    map.put("showText", user.getName() + user.getPassword());
                    listItem.add(map);

                    SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据

                            R.layout.activity_main, //每一行的布局//动态数组中的数据源的键对应到定义布局的View中
                            new String[] {"id","showText"}
                            ,new int[] {}
                    );
                    lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"没有数据",Toast.LENGTH_SHORT).show();
        }
    }

    /*删除全部内容*/
    private void deleteAll(){
        userDao.deleteAll();
        Log.i("删除数据完成：",user.toString());
        Toast.makeText(getApplicationContext(),"删除数据成功",Toast.LENGTH_SHORT).show();
    }

    private void clearShow(){
        show.setText("");
    }
}
