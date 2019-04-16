package com.bwie.messengerdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 客户端
 */
public class MainActivity extends AppCompatActivity {
    private static final int REPLY_MSG_ID = 2;
    private boolean mServiceConnected = false;
    private Button btn = null;
    //用于向Service端发送消息的Messenger
    private Messenger mBoundServiceMessenger = null;
    //用于接收Service发送消息的Messenger
    private final Messenger mReceiveMessenger = new Messenger(new ReceiveMessHandler(this));


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundServiceMessenger = null;
            mServiceConnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundServiceMessenger = new Messenger(service);
            mServiceConnected = true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.button);
        bindService(new Intent(this, MessengerService.class), conn, Context.BIND_AUTO_CREATE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceConnected){
                    //获取消息对象
                    Message msg = Message.obtain(null, 1, 0, 0);
                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("client","我是客户端，我来了");
                        msg.setData(bundle);
                        //replyTo参数包含客户端Messenger
                        msg.replyTo = mReceiveMessenger;
                        //向Service端发送消息

                        mBoundServiceMessenger.send(msg);
                    }catch(RemoteException re){
                        re.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mServiceConnected){
            unbindService(conn);
            mServiceConnected = false;
        }
    }
    /**
     * 客户端实现一个Handler用于接收服务端返回的响应
     * @author Administrator
     *
     */
    static class ReceiveMessHandler extends Handler{
        //持有当前Activity的弱引用，避免内存泄露
        private final WeakReference<MainActivity> mActivity;
        public ReceiveMessHandler(MainActivity activity){
            mActivity = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case REPLY_MSG_ID:
                    System.out.println("来自服务端："+msg.getData().getString("server"));
                    Toast.makeText(mActivity.get(), msg.getData().getString("server"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
