package BullyAlgorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.concurrent.TimeUnit;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import Process.ProcessDataInterface;

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
        startAlgorithm();
    }

    private void startAlgorithm() {
        try {
            Registry registry = LocateRegistry.getRegistry(2000);
            ProcessDataInterface currentProcessData = (ProcessDataInterface) registry.lookup("Reg" + ((Integer) currentId).toString());
            bully(currentProcessData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bully(ProcessDataInterface currentProcessData) {
        try {
            while (true) {
                System.out.println("current process in bully: " + currentId);
                currentCoordinator = checkIfNoOfProcessesChanged(currentProcessData) ? false : currentCoordinator;
                updateCurrentNoOfProcesses(currentProcessData);
                broadcast(currentId, currentCoordinator ? "COORDINATOR_ALIVE" : "ALIVE");
                if (isCoordinatorAlive(currentProcessData) == false && currentCoordinator == false) {
                    broadcast(currentId, "INITIATE_ELECTION");
                    Election(currentProcessData);
                }
                sleepFunction(4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Election(ProcessDataInterface currentProcessData) {
        try {
            int subsequentWins = 0;
            while (true) {
                System.out.println("____________________________________________________________ ");

                System.out.println("current process in election: " + currentId);

                sendToLargerIds();

                sleepFunction(2000);

                System.out.println("subsequentWins inside conditon : " + subsequentWins);

                // was 5000
                replyOK(currentProcessData, 5000);

                sleepFunction(2000);

                updateCurrentNoOfProcesses(currentProcessData);

                if (currentProcessData.searchMailbox("OK", 5000)) {
                    System.out.println("current process in OK: " + currentId);
                    break;
                } else if (currentId == currentNoOfProcesses - 1) {
                    System.out.println("current process in WIN: " + currentId);
                    subsequentWins++;
                }
                if (subsequentWins == 7) {
                    System.out.println("current process in final WIN: " + currentId);
                    currentCoordinator = true;
                    broadcast(currentId, "WIN");
                    return;
                }
            }
            while (didCoordinatorWin(currentProcessData) == false) {
                sleepFunction(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentNoOfProcesses(ProcessDataInterface currentProcessData) {
        try {
            currentNoOfProcesses = currentProcessData.getCurrentNoOfProcesses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfNoOfProcessesChanged(ProcessDataInterface currentProcessData) {
        try {
            return currentProcessData.getCurrentNoOfProcesses() != currentNoOfProcesses ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void replyOK(ProcessDataInterface process, int timeInterval) {
        try {
            LocalTime now = LocalTime.now();
            for (int i = process.getMailboxSize() - 1; i >= 0; i--) {
                if ((now).until(LocalTime.now(), ChronoUnit.MILLIS) > timeInterval) {
                    break;
                }
                int toProcessId = process.shouldReplyOK(i);
                System.out.println("should reply ok " + toProcessId);
                if (toProcessId == -1) {
                    continue;
                } else {
                    Registry registry = LocateRegistry.getRegistry(2000);
                    ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg" + ((Integer) toProcessId).toString());
                    skeleton.addToMailbox("OK", LocalTime.now(), process.getId());
                    System.out.println("replied ok to: " + toProcessId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToLargerIds() {
        for (int i = currentId + 1; i < currentNoOfProcesses; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(2000);
                ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg" + ((Integer) i).toString());
                skeleton.addToMailbox("ELECTION", LocalTime.now(), currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcast(int id, String messageType) {
        for (int i = 0; i < currentNoOfProcesses; i++) {
            try {
                if (i == id) {
                    continue;
                }
                Registry registry = LocateRegistry.getRegistry(2000);
                ProcessDataInterface skeleton = (ProcessDataInterface) registry.lookup("Reg" + ((Integer) i).toString());
                skeleton.addToMailbox(messageType, LocalTime.now(), id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isCoordinatorAlive(ProcessDataInterface currentProcessData) {
        try {
            return currentProcessData.searchMailbox("COORDINATOR_ALIVE", 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean didCoordinatorWin(ProcessDataInterface currentProcessData) {
        try {
            return currentProcessData.searchMailbox("WIN", 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sleepFunction(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
