package com.company;

import java.io.InputStream;
import java.util.List;

public class PriorityScheduling extends ProcessScheduling {

    PriorityScheduling(InputStream inputStream) {
        super(inputStream);
    }
    PriorityScheduling(List<Process> data) {
        super(data);
    }

    @Override
    protected List<Process> EnterData() {
        return null;
    }

    @Override
    public List<Process> Simulate() {
        return null;
    }
}
