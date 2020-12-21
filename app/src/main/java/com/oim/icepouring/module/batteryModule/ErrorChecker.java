package com.oim.icepouring.module.batteryModule;

import java.util.List;

public interface ErrorChecker {
    void registerError(Object object);
    boolean checkErrorExistence();
    List <String> getErrorList();
}
