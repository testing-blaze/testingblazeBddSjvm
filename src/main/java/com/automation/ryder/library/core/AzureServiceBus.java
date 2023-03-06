package com.automation.ryder.library.core;

import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.models.SubQueue;

import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class AzureServiceBus {
    private String errorDesc;
    private ReportController reportController;
    public AzureServiceBus(ReportController reportController){
        this.reportController=reportController;
    }

    /**
     * This method send messages to the topic
     * @param connectionString
     * @param topicName
     * @param message
     * @return true if its success
     */
    public boolean sendMessageToTopic(String connectionString,String topicName, String message) {
        try {

            ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                    .connectionString(connectionString)
                    .sender()
                    .topicName(topicName)
                    .buildClient();
            List<ServiceBusMessage> messages = Arrays.asList(
                    new ServiceBusMessage(message));
            sender.sendMessages(messages);
            sleep(5000);
            reportController.write(LogLevel.INFO,"Message sent to topic");
            sender.close();
            reportController.write(LogLevel.INFO,"Connection closed");
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR,"Exception occurred while sending message to service bus topic "+e.getMessage());
            return false;

        }
        return true;

    }

    public  boolean readDeadLetterAndDelete(String connectionString,String topicName, String subName,String keyword) {
        try {
            ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
                    .connectionString(connectionString)
                    .receiver() // Use this for session or non-session enabled queue or topic/subscriptions
                    .topicName(topicName)
                    .subscriptionName(subName)
                    .subQueue(SubQueue.DEAD_LETTER_QUEUE)
                    .buildClient();
            IterableStream<ServiceBusReceivedMessage> message = receiver.receiveMessages(100);

            for (ServiceBusReceivedMessage msg : message) {
                if (msg.getBody().toString().contains(keyword)) {
                    reportController.write(LogLevel.INFO,"Message read from dead Letter Queue"+msg.getBody());
                    errorDesc=msg.getDeadLetterErrorDescription();
                    receiver.complete(msg);
                    reportController.write(LogLevel.INFO,"Successfully deleted  test message with given keyword");
                }
            }
            receiver.close();
            return true;
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR,"Exception occured while reading message from DeadQueue and completing it " + e);
            return false;
        }

    }

}
