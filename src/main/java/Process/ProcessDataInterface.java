/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Message.Message;
import java.rmi.*;
import java.time.LocalTime;
import java.util.ArrayList;



/**
 *
 * @author fadwa
 */
public interface ProcessDataInterface extends Remote
{
    // Declaring the method prototype
    public void setMailbox(ArrayList<Message> mailbox) throws RemoteException;
    public int shouldReplyOK(int idx) throws RemoteException;
    public int getMailboxSize() throws RemoteException;
    public String getMailboxToString() throws RemoteException;
    public void addToMailbox(String messageType, LocalTime time , int id) throws RemoteException;
    public boolean searchMailbox(String MessageType, int timeInterva) throws RemoteException;
    public int getId() throws RemoteException;
    public void setCurrentNoOfProcesses(int newNoOfProcesses) throws RemoteException;
    public int getCurrentNoOfProcesses() throws RemoteException;
}
