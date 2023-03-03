package com.automation.ryder.controller.access;

import com.automation.ryder.library.core.AzureServiceBus;
import com.automation.ryder.library.core.DataBases;
import com.automation.ryder.library.core.RestfulWebServices;

public class ServiceUserPerforms {
    private RestfulWebServices restfulWebServices;
    private AzureServiceBus azureServiceBus;
    private DataBases dataBases;
    public ServiceUserPerforms(RestfulWebServices restfulWebServices, AzureServiceBus azureServiceBus, DataBases dataBases) {
        this.restfulWebServices=restfulWebServices;
        this.azureServiceBus=azureServiceBus;
        this.dataBases=dataBases;
    }

    public AzureServiceBus azureServiceBusActionsTo(){
        return azureServiceBus;
    }

    public DataBases dataBaseActionsTo(){
        return dataBases;
    }

    /**
     * access web services
     *
     * @author nauman.shahid
     */
    public RestfulWebServices restHttp() {
        return restfulWebServices;
    }
}
