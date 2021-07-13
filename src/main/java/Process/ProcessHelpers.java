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

    private int noOfProcesses = 0;
    private ProcessData processData;
    private ArrayList<ProcessBuilder> processBuilder;
    private ArrayList<Process> processList;
    private ArrayList<ProcessDataInterface> stubs;
    private Registry registry;

    public ProcessHelpers() {
        this.processList = new ArrayList<>();
        this.processBuilder = new ArrayList<>();
        this.stubs = new ArrayList<>();
        init();
    }

    private void init() {
        try {
            registry = LocateRegistry.createRegistry(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void raiseProcess() {
        try {
            noOfProcesses++;
            int id = noOfProcesses - 1;
            System.out.println("noOfProcesses:  " + noOfProcesses);
            ProcessBuilder processBuilderInstance = new ProcessBuilder("java", "-cp", "file:/home/fadwa/Practice/bully/target/classes", "ProcessEntryPoint.EntryPoint",
                    ((Integer) id).toString());
            processBuilder.add(processBuilderInstance);
            processBuilder.get(id).redirectOutput(Redirect.INHERIT);
            processBuilder.get(id).redirectError(Redirect.INHERIT);
            processData = new ProcessData(id);
            stubs.add((ProcessDataInterface) UnicastRemoteObject.exportObject(processData, 0));
            registry.bind("Reg" + ((Integer) id).toString(), stubs.get(id));
            processList.add(processBuilder.get(id).start());
            setGlobalNoOfProcesses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void killProcess() {
        try {
            int id = noOfProcesses - 1;
            if (noOfProcesses == 0) {
                throw new Exception("there are no processes to kill!!");
            }
            processList.get(id).destroy();
            processList.remove(id);
            stubs.remove(id);
            processBuilder.remove(id);
            noOfProcesses--;
            setGlobalNoOfProcesses();
            registry.unbind("Reg" + ((Integer) id).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGlobalNoOfProcesses() {
        for (int i = 0; i < noOfProcesses; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(2000);
                ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg" + ((Integer) i).toString());
                skeleton.setCurrentNoOfProcesses(noOfProcesses);
            } catch (Exception e) {
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

    public int[] getIds() {
        int[] ids = new int[noOfProcesses];
        try {
            for (int i = 0; i < noOfProcesses; i++) {
                ids[i] = (stubs.get(i).getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
}
