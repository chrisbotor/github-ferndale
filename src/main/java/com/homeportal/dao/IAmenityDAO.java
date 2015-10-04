/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.homeportal.dao;

import com.homeportal.model.Amenity;
import java.util.List;

/**
 *
 * @author PSP36488
 */
public interface IAmenityDAO {

    List<Amenity> getAmenities();
    void deleteAmenity(int id);
    Amenity saveAmenity(Amenity amenity);
    Amenity updateAmenity(Amenity amenity);
}
