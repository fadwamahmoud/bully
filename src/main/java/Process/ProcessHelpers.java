/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author fadwa
 */

import java.lang.ProcessBuilder.Redirect;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProcessHelpers {

    int noOfProcesses = 0;
    ProcessData processData;
    ArrayList<ProcessBuilder> processBuilder;
    ArrayList<Process> processList;
    ArrayList<ProcessDataInterface> stubs;
    Registry registry;

    public ProcessHelpers() {
        this.processList = new ArrayList<>();
        this.processBuilder = new ArrayList<>();
        this.stubs = new ArrayList<>();
        
            init();
    }

    void init() {
            try {
               registry = LocateRegistry.createRegistry(2000);
                    
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }

     public void raiseProcess() {
        try{
            noOfProcesses++;

            int id =noOfProcesses - 1;
            System.out.println("noOfProcesses:  " + noOfProcesses);
            ProcessBuilder processBuilderInstance = new ProcessBuilder("java", "-cp", "file:/home/fadwa/Practice/bully/target/classes", "App.Main",
                                ((Integer) id).toString() );
            processBuilder.add(processBuilderInstance);

            processBuilder.get(id).redirectOutput(Redirect.INHERIT);
            processBuilder.get(id).redirectError(Redirect.INHERIT);

            processData = new ProcessData(id);
            stubs.add((ProcessDataInterface) UnicastRemoteObject.exportObject(processData, 0));

            registry.bind("Reg"+ ((Integer) id).toString(), stubs.get(id));
            
            processList.add(processBuilder.get(id).start());
            setNoOfProcessesInProcesses();
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }
       
    public void killProcess() {
        try{
            int id = noOfProcesses - 1;
            if(noOfProcesses == 0) {
                throw new Exception("there are no processes to kill!!");
            }
            processList.get(id).destroy();
            processList.remove(id);
            stubs.remove(id);
            processBuilder.remove(id);
            noOfProcesses--;
            setNoOfProcessesInProcesses();
            registry.unbind("Reg"+ ((Integer) id).toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setNoOfProcessesInProcesses(){
       for(int i = 0; i < noOfProcesses; i++){
           try{

                Registry registry = LocateRegistry.getRegistry(2000);
                ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg"+ ((Integer) i).toString());

                skeleton.setCurrentNoOfProcesses(noOfProcesses);

           }catch(Exception e){
               e.printStackTrace();
           }
           
        
           
       }  
   }


    
    

    public int getNoOfProcesses() {
            return noOfProcesses;
    }

    
    public ArrayList<ProcessDataInterface> getStubs() {
            return stubs;
    }
}

    

