/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.Lessee;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author PSP36488
 */
@Component
public class LesseeUtil 
{

    /**
     * Get list of Lessee from request.
     * @param data - json data from request
     * @return list of Lessee
     */
    public List<Lessee> getLesseeFromRequest(Object data) {

        List<Lessee> list;

        //it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {

            list = getListLeeseesFromJSON(data);

        } else { //it is only one object - cast to object/bean

            Lessee Leesee = getLeeseeFromJSON(data);

            list = new ArrayList<Lessee>();
            list.add(Leesee);
        }

        return list;
    }

    /**
     * Transform json data format into Leesee object
     * @param data - json data from request
     * @return
     */
    private Lessee getLeeseeFromJSON(Object data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        Lessee newLeesee = (Lessee) JSONObject.toBean(jsonObject, Lessee.class);
        return newLeesee;
    }

    /**
     * Transform json data format into list of Leesee objects
     * @param data - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Lessee> getListLeeseesFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<Lessee> newLeesees = (List<Lessee>) JSONArray.toCollection(jsonArray, Lessee.class);
        return newLeesees;
    }

    /**
     * Tranform array of numbers in json data format into
     * list of Integer
     * @param data - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getListIdFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<Integer> idLeesees = (List<Integer>) JSONArray.toCollection(jsonArray, Integer.class);
        return idLeesees;
    }
}
