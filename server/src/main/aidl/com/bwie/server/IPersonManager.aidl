// IPersonManager.aidl
package com.bwie.server;
import com.bwie.server.Person;
// Declare any non-default types here with import statements

interface IPersonManager {
    List<Person> getList();
    void addPerson(inout Person person);
}
