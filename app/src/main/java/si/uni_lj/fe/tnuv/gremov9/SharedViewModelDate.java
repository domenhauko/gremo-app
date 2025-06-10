// SharedViewModelDate.java
package si.uni_lj.fe.tnuv.gremov9;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

public class SharedViewModelDate extends ViewModel {
    private final MutableLiveData<Date> selectedDate = new MutableLiveData<>();

    public void setSelectedDate(Date date) {
        selectedDate.setValue(date);
    }

    public LiveData<Date> getSelectedDate() {
        return selectedDate;
    }
}
