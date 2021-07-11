package BullyAlgorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Message.Message;

import java.util.concurrent.TimeUnit; 


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import Process.ProcessDataInterface;
import Process.ProcessHelpers;
import java.time.temporal.ChronoUnit;


/**
 *
 * @author fadwa
 */
public class Algorithm {
    private boolean currentCoordinator;
    private int currentId;
    private int currentNoOfProcesses;

    public Algorithm(int id) {
        this.currentCoordinator = false;
        this.currentNoOfProcesses = id + 1;
        
        this.currentId = id;
    }

    
    public void startAlgorithm(){
        try {
            Registry registry = LocateRegistry.getRegistry(2000);
            ProcessDataInterface stub = (ProcessDataInterface) registry.lookup("Reg"+ ((Integer) this.currentId).toString()); 
            bully(stub);

        }
        catch(Exception e) 
        {   
            e.printStackTrace();
	}
        
        
    }
    
   public void bully(ProcessDataInterface currentProcessData){
       try{
           
           int currentId = currentProcessData.getId();
           
           while(true){
               System.out.println("current process in bully: " + currentId );
               currentCoordinator = currentProcessData.getCurrentNoOfProcesses() != currentNoOfProcesses ? false : currentCoordinator;
               currentNoOfProcesses = currentProcessData.getCurrentNoOfProcesses();               
               
               
               broadcast(currentId, currentCoordinator ?  "COORDINATOR_ALIVE" : "ALIVE");
                   
               if(isCoordinatorAlive(currentProcessData ) == false && currentCoordinator == false){
                   
                    broadcast(currentId, "COORDINATOR_DEAD");
                    Election(currentProcessData);
                }
               sleepFunction(5000);
           }
            
       }
       catch(Exception e){
           
       }
   }
   
 
   public void Election(ProcessDataInterface currentProcessData){
       try{
           int currentId = currentProcessData.getId();
           int subsequentWins = 0;
           while(true){            


            
            System.out.println("current process in election: " + currentId);

            System.out.println("____________________________________________________________ ");
            System.out.println( currentProcessData.getMailboxToString());

            sendToLargerIds(currentId);

            sleepFunction(6000);

            replyOK(currentProcessData, 14000);

            sleepFunction(6000);

            currentNoOfProcesses = currentProcessData.getCurrentNoOfProcesses();               
                         
            if(currentProcessData.searchMailbox("OK", 14000)){
              System.out.println("current process in OK: " + currentId);

               break;
            }
            
            else if(currentId == currentNoOfProcesses - 1){
            System.out.println("current process in WIN: " + currentId);

                subsequentWins++;
               

                
            }
            if(subsequentWins == 3){
               System.out.println("current process in final WIN: " + currentId);

                currentCoordinator = true;
                broadcast(currentId, "WIN");
                return;
            }
            
            
           }
           while(didCoordinatorWin(currentProcessData) == false){
               sleepFunction(1000);
           }
           
       }

       catch(Exception e){
           
       }
   }

   public void replyOK(ProcessDataInterface process, int timeInterval){
       
        try{
            
       
       LocalTime now = LocalTime.now();
       for(int i = process.getMailboxSize() -1 ; i>=0 ; i-- ){   
           
           if((now).until(LocalTime.now(), ChronoUnit.MILLIS) > timeInterval) {

               break;
           }
           int toProcessId = process.shouldReplyOK(i);
           
           System.out.println("toProcessId: " + toProcessId);
           
           if(toProcessId == -1) {
               continue;
           }
           else{
               Registry registry = LocateRegistry.getRegistry(2000);
               ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg"+ ((Integer) toProcessId).toString()); 
               skeleton.addToMailbox("OK" , LocalTime.now() , process.getId());
           }
        }
       }
       catch(Exception e){
           e.printStackTrace();
           
       }
   }

   
   public void sendToLargerIds(int id){

       for(int i = id+1; i< currentNoOfProcesses; i++){
           try{

            Registry registry = LocateRegistry.getRegistry(2000);
            ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg"+ ((Integer) i).toString());

            skeleton.addToMailbox("ELECTION", LocalTime.now() ,id);
           }
           catch(Exception e){
               e.printStackTrace();
           }

           
           
           
       }
   }

   
   public void broadcast(int id, String messageType){
       // send to all processes that im alive
       for(int i = 0; i< currentNoOfProcesses; i++){
           try{
                if(i == id) continue; 

                 Registry registry = LocateRegistry.getRegistry(2000);
                 ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg"+ ((Integer) i).toString());

                 skeleton.addToMailbox(messageType , LocalTime.now(), id);

           }catch(Exception e){
               e.printStackTrace();
           }
           
        
           
       }  
   };
   
   public boolean isCoordinatorAlive(ProcessDataInterface stub){

    try{
     return stub.searchMailbox("COORDINATOR_ALIVE", 1000);

    }catch(Exception e){
        e.printStackTrace();
       
    }
        return false;
       
   }
   
    public boolean didCoordinatorWin(ProcessDataInterface stub){
         try{
      return stub.searchMailbox("WIN", 2000);
      }catch(Exception e){
        e.printStackTrace();
       
    }
        return false;
       
       
   }
   
   void sleepFunction(long ms) {
        try {
        TimeUnit.MILLISECONDS.sleep(ms);
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
   
    
}
