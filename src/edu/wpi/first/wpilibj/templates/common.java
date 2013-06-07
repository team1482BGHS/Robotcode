/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.templates.Team1482Robot;

/**
 *
 * @author Nicholas
 */
class common {

    static void liftSet(boolean state, Solenoid set, Solenoid retract) {
        //If retracted, extend
        if (state == false) {
            set.set(true);
            retract.set(false);
            state = true;
        } //If iextended, retract
        else {
            set.set(false);
            retract.set(true);
            state = false;
        }
    }

    static void cycle(Solenoid set, Solenoid retract, int count, int timeA, int timeB) {
        switch (count) {
            case 0:
                set.set(false);
                retract.set(true);
                break;
            case 200:
                set.set(true);
                retract.set(false);
                break;
            case 400:
                count = 0;
        }

    }
}
