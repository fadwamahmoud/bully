/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import BullyAlgorithm.Algorithm;

/**
 *
 * @author fadwa
 */
public class Main {
   public static void main(String[] arg) {
       int currentId = Integer.parseInt(arg[0]);
//            boolean coordinatorAlive = 
            Algorithm bullyAlgorithm = new Algorithm(currentId);
            bullyAlgorithm.startAlgorithm();
    } 
}
