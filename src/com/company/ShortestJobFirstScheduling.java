package com.company;

import java.io.InputStream;
import java.util.List;

public class ShortestJobFirstScheduling extends ProcessScheduling {

    ShortestJobFirstScheduling(InputStream inputStream) {
        super(inputStream);
    }
    ShortestJobFirstScheduling(List<Process> data){
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
