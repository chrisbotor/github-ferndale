/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.model.Occupant;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IOccupant {
    
     List<Occupant> getOccupants();
    void deleteOccupants(int id);
    Occupant saveOccupants(Occupant occupant);
    Occupant updateOccupant(Occupant occupant);
    
}
