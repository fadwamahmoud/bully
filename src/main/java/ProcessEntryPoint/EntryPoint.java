/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessEntryPoint;

import BullyAlgorithm.Algorithm;
import java.util.logging.Logger;

/**
 *
 * @author fadwa
 */
public class EntryPoint {

    public static void main(String[] arg) {
        int currentId = Integer.parseInt(arg[0]);
        Algorithm bullyAlgorithm = new Algorithm(currentId);
    }
}
