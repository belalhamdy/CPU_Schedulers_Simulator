package com.company;

import java.io.InputStream;
import java.util.List;

public class AGScheduling extends ProcessScheduling {
    AGScheduling(InputStream inputStream) {
        super(inputStream);
    }
    AGScheduling(List<Process> data) {
        super(data);
    }

    @Override
    void EnterData() {

    }

    @Override
    public List<Process> Simulate() {
        return null;
    }
}
