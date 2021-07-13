package Process;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import Message.Message;
import java.rmi.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author fadwa
 */
public class ProcessData implements ProcessDataInterface {

    private final int id;
    private ArrayList<Message> mailbox = new ArrayList<Message>();
    private int currentNoOfProcesses;

    public ProcessData(int id) throws RemoteException {
        this.id = id;
        ArrayList<Message> emptyMailbox = new ArrayList<Message>();
        Message emptyMessage = new Message("EMPTY", LocalTime.now(), id);
        this.mailbox = emptyMailbox;
    }

    @Override
    public void setCurrentNoOfProcesses(int currentNoOfProcesses) throws RemoteException {
        this.currentNoOfProcesses = currentNoOfProcesses;
    }

    @Override
    public int getCurrentNoOfProcesses() throws RemoteException {
        return currentNoOfProcesses;
    }

    @Override
    public void setMailbox(ArrayList<Message> mailbox) throws RemoteException {
        this.mailbox = mailbox;
    }

    @Override
    public int getMailboxSize() throws RemoteException {
        return this.mailbox.size();
    }

    @Override
    public int shouldReplyOK(int idx) throws RemoteException {
        try {
            ArrayList<Message> mailbox = this.mailbox;
            if (mailbox.get(idx).getType().compareTo("ELECTION") == 0 && mailbox.get(idx).isRepliedTo() == false) {
                int toProcessId = mailbox.get(idx).getProcessId();
                mailbox.get(idx).setRepliedTo(true);
                return toProcessId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addToMailbox(String messageType, LocalTime time, int id) throws RemoteException {
        try {
            Message message = new Message(messageType, time, id);
            this.mailbox.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getId() throws RemoteException {
        return id;
    }

    @Override
    public String getMailboxToString() throws RemoteException {
        String mailbox = "";
        for (int i = 0; i < this.mailbox.size(); i++) {
            mailbox += this.mailbox.get(i).getMessageToString();
        }
        return mailbox;
    }

    @Override
    public boolean searchMailbox(String messageType, int timeInterval) throws RemoteException {
        try {
            LocalTime now = LocalTime.now();
            ArrayList<Message> mailbox = this.mailbox;
            for (int i = mailbox.size() - 1; i >= 0; i--) {
                if (mailbox.get(i).getTimestamp().until(now, ChronoUnit.MILLIS) > timeInterval) {
                    break;
                }
                if (mailbox.get(i).getType().compareTo(messageType) == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
