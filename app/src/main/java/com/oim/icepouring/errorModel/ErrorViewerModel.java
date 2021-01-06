package com.oim.icepouring.errorModel;

import androidx.databinding.ObservableField;

import com.oim.icepouring.module.batteryModule.BatteryDataMonitor;
import com.oim.icepouring.module.batteryModule.ErrorChecker;

import java.util.ArrayList;
import java.util.List;

public class ErrorViewerModel implements ErrorService
{
    private int indexColumn;
    private int indexLine;
    private int errorIndex;

    List<List<? extends String>> list ;
    private ObservableField<String> errorField ;
    private BatteryDataMonitor batteryDataMonitor;

    public ObservableField<String> getErrorField() {
        return errorField;
    }

    public ErrorViewerModel(BatteryDataMonitor batteryDataMonitor)
    {
        this.batteryDataMonitor = batteryDataMonitor;
        errorField = new ObservableField<>("");
        list = new ArrayList<>();
    }

    @Override
    public void updateErrorList() {
        checkErrorList(batteryDataMonitor.getBattery_0810FFFF_model(), batteryDataMonitor.getBattery_0810FFFF_model().getErrorList());
        checkErrorList(batteryDataMonitor.getBatteryState_0C07F301_model(), batteryDataMonitor.getBatteryState_0C07F301_model().getErrorList());
        checkErrorList(batteryDataMonitor.getContactors_0CFEF301_model(), batteryDataMonitor.getContactors_0CFEF301_model().getErrorList());
    }

    @Override
    public void updateField() {
        if(list.isEmpty()) {
            errorField.set(" ");
        }
        else {
            int errorCount = 0;
            for (int i = 0; i < list.size(); i++)
                errorCount += list.get(i).size();

            errorIndex = errorIndex >= errorCount ? errorIndex = 1 : ++errorIndex;
            errorField.set(errorIndex + "/" + errorCount + list.get(indexColumn).get(indexLine));

            indexLine = (indexLine == list.get(indexColumn).size() - 1)? indexLine = 0 : ++indexLine ;
            indexColumn = (indexLine   == 0 ) ? indexColumn == list.size()- 1  ? indexColumn = 0 :++indexColumn  : indexColumn;
        }
    }

    public List<List<? extends String>> getList() {
        return list;
    }

    public BatteryDataMonitor getBatteryDataMonitor() {
        return batteryDataMonitor;
    }

    public void setBatteryDataMonitor(BatteryDataMonitor batteryDataMonitor) {
        this.batteryDataMonitor = batteryDataMonitor;
    }
    private void checkErrorList(ErrorChecker<?> errorChecker , List<String> listV)
    {
        if(errorChecker.checkErrorExistence())
        {
            if(!this.list.contains(listV))
                this.list.add(listV);
        }
        else
        {
            if(this.list.contains(listV))
            {
                this.list.remove(listV);
                indexColumn = indexLine = 0;
            }
        }
    }
}
