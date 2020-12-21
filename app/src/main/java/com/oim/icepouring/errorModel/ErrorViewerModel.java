package com.oim.icepouring.errorModel;

import androidx.databinding.ObservableField;

import com.oim.icepouring.module.batteryModule.BatteryDataMonitor;

import java.util.ArrayList;
import java.util.List;

public class ErrorViewerModel implements ErrorService
{
    private int indexColumn;
    private int indexColumnPrevious;
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
        if(!batteryDataMonitor.getBatteryState_0C07F301_model().checkErrorExistence())
        {
            list.add(batteryDataMonitor.getBatteryState_0C07F301_model().getErrorList());
        }
        else
        {
            list.remove(batteryDataMonitor.getBatteryState_0C07F301_model().getErrorList());
        }
        if(!batteryDataMonitor.getBattery_0810FFFF_model().checkErrorExistence())
        {
            list.add(batteryDataMonitor.getBattery_0810FFFF_model().getErrorList());
        }
        else
        {
            list.remove(batteryDataMonitor.getBattery_0810FFFF_model().getErrorList());
        }
        if(!batteryDataMonitor.getContactors_0CFEF301_model().checkErrorExistence())
        {
            list.add(batteryDataMonitor.getContactors_0CFEF301_model().getErrorList());
        }
        else
        {
            list.remove(batteryDataMonitor.getContactors_0CFEF301_model().getErrorList());
        }
    }

    @Override
    public void updateField() {
        if(list.isEmpty()) {
            errorField.set(" ");
        }
        else {
            int errorCount = 0;
            for (int i = 0; i < list.size(); i++) {
                errorCount += list.get(i).size();
            }
            errorIndex = errorIndex >= errorCount ? errorIndex = 1 : errorIndex++;
            errorField.set(errorIndex + "/" + errorCount + ":" + list.get(indexColumn == list.size() ?
                    indexColumn = 0 : indexLine < list.get(indexColumn).size() ?
                    indexColumn : indexColumn++).get(indexLine < list.get(indexColumnPrevious == list.size() ?
                    indexColumnPrevious = indexColumn : indexColumnPrevious).size() ?
                    indexLine++ : (indexLine = 0)));
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
}
