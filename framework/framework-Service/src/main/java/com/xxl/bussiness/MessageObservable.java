package com.xxl.bussiness;

import java.util.Observable;
import common.bussiness.Message;

public class MessageObservable extends Observable{
        private Message message;
        public MessageObservable(){
                MessageObserver observer=new MessageObserver();
                this.addObserver(observer);
        }
        public void sendMessage(Message message){
                this.message=message;
                this.setChanged();
                notifyObservers(message);
        }

}
  