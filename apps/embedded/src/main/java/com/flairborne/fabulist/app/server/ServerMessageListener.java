package com.flairborne.fabulist.app.server;

import com.flairborne.fabulist.runtime.element.channel.MessageListener;
import com.flairborne.fabulist.runtime.element.channel.message.Message;
import com.flairborne.fabulist.runtime.Runtime;

class ServerMessageListener implements MessageListener {

    private final Runtime runtime;

    public ServerMessageListener(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void onReceive(Message message) {
        runtime.step();
    }
}