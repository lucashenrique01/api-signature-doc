package com.poc.apisignaturedoc.jobs;

import com.poc.apisignaturedoc.services.CanceledService;

public class CanceledServiceThread extends Thread{
    private CanceledService canceledService;

    public CanceledServiceThread(CanceledService canceledService) {
        this.canceledService = canceledService;
    }

    @Override
    public void run() {
        canceledService.getMessages();
    }
}
