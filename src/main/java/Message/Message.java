package Message;

import java.time.LocalTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fadwa
 */
public class Message {

    private String type;
    private LocalTime timestamp;
    private int processId;
    private boolean repliedTo;

    public Message(String type, LocalTime timestamp, int processId) {
        this.type = type;
        this.timestamp = timestamp;
        this.processId = processId;
        this.repliedTo = false;
    }

    public boolean isRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(boolean repliedTo) {
        this.repliedTo = repliedTo;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageToString() {
        return type + " at " + timestamp.toString() + " from process number: " + ((Integer) processId).toString() + "\n";
    }
}
