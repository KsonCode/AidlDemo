package com.bwie.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class PersonService extends Service {
    private List<Person> list = new ArrayList<>();

    IPersonManager.Stub stub = new IPersonManager.Stub() {
        @Override
        public List<Person> getList() throws RemoteException {
            //从数据库，业务逻辑生成list集合
//            list.add(new Person("张三"));
//            list.add(new Person("lisi"));
            return list;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {

            list.add(person);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


//
//    class MyBinder extends IPersonManager.Stub{
//
//        @Override
//        public List<Person> getList() throws RemoteException {
//                        Person person = new Person("张三");
//            list.add(person);
//            return list;
//        }
//
//        @Override
//        public void addPerson(Person person) throws RemoteException {
//
//        }
//    }
}
