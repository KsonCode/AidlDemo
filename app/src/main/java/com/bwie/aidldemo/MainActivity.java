package com.bwie.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bwie.server.IPersonManager;
import com.bwie.server.Person;

import java.util.IllegalFormatCodePointException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private boolean isConnect = false;
    private int i = -1;
    private IPersonManager iPersonManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();

    }

    private void initService() {
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isConnect = true;
                iPersonManager = IPersonManager.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                isConnect = false;
            }
        };
        Intent intent = new Intent();
        intent.setAction("com.bwie.server.PersonService");
//        intent.setPackage(getPackageName());//获取的是服务组件当前项目的包名
        intent.setPackage("com.bwie.server");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

//        bindService(new Intent(this,myser.class));

    }


    /**
     * 获取另一端的对象数据
     * @param view
     */
    public void get(View view) {

        if (isConnect){
            try {
                List<Person> list = iPersonManager.getList();
                System.out.println("getlist===="+list.size());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加数据
     * @param view
     */
    public void add(View view) {
        if (isConnect){
            try {
                iPersonManager.addPerson(new Person("你你你你"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
