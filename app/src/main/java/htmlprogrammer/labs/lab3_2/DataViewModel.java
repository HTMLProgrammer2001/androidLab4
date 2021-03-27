package htmlprogrammer.labs.lab3_2;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class DataViewModel extends ViewModel {
    private final MutableLiveData<List<Contact>> data = new MutableLiveData<>();

    public DataViewModel(){
        data.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Contact>> getData(){
        return data;
    }

    public void addContact(Contact c){
        List<Contact> list = data.getValue();
        list.add(c);
        data.setValue(list);
    }

    public void removeContact(int idx){
        List<Contact> list = data.getValue();
        list.remove(idx);
        data.setValue(list);
    }
}
