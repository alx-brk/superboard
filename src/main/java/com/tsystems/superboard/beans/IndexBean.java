package com.tsystems.superboard.beans;

import com.rabbitmq.client.*;
import com.tsystems.superboard.dto.StationInfoDto;
import com.tsystems.superboard.services.IndexInt;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("index")
@ViewScoped
public class IndexBean implements Serializable {
    private static final Logger log = Logger.getLogger(IndexBean.class);
    private static ConnectionFactory rabbit;
    private List<StationInfoDto> infoList;
    public static final String QUEUE_NAME = "queue";

    @Inject
    @Push
    PushContext infoChanges;

    @EJB
    private IndexInt index;

    @PostConstruct
    public void init(){
        infoList = index.getInfo();

        if (rabbit == null){
            rabbit = new ConnectionFactory();
            rabbit.setHost("localhost");

            try{
                Connection connection = rabbit.newConnection();
                Channel channel = connection.createChannel();
                channel.exchangeDeclare(QUEUE_NAME, BuiltinExchangeType.FANOUT);
                String queue = channel.queueDeclare().getQueue();
                channel.queueBind(queue, QUEUE_NAME, "");

                Consumer consumer = new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");
                        log.info("Received message: " + message);
                        infoChanges.send(message);
                    }
                };

                channel.basicConsume(queue, true, consumer);
            } catch (Exception e){
                log.error(e);
            }
        }
    }

    public List<StationInfoDto> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<StationInfoDto> infoList) {
        this.infoList = infoList;
    }
}
