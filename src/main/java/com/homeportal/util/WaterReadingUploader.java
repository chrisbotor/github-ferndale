package com.homeportal.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.homeportal.model.WaterReading;
import com.homeportal.service.WaterReadingService;


public class WaterReadingUploader 
{
	private static Logger logger = Logger.getLogger(WaterReadingUploader.class);
	
	
	public static void main(String[] args)
    {
        //Input file which needs to be parsed
        // String fileToParse = "/Users/chrisbotor/Desktop/ferndale-water.csv";
		String fileToParse = "C:/app-root/data/water_reading/WATER TEMPLATE NOV.2013.csv";
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        WaterReadingService waterReadingService = new WaterReadingService();
        List<WaterReading> waterReadingList = new ArrayList<WaterReading>();
       
        
        try
        {
            String line = "";
            // String remarks = null;
            Double rate = null;
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                WaterReading wr = new WaterReading();
                int i = 0;
                
                for(String token : tokens)
                {
                    //Print all tokens
                    //System.out.println(token);
                    //System.out.println("i = " +i);
                    switch (i)  
                    {
                        case 0: wr.setUserId(Integer.parseInt(token)); break;
                        case 1: wr.setHouseId(Integer.parseInt(token)); break;
                        case 2: wr.setStartReading(Double.parseDouble(token)); break;    
                        case 3: wr.setEndReading(Double.parseDouble(token)); break;    
                        case 4: wr.setRate(Double.parseDouble(token)); break;
                        case 5: wr.setUsedMonth(token); 
                        		wr.setRemarks(token);
                        		break;
                    }
                    
                    i++;
                }
                
                // GET WATER RATE
                // rate = waterReadingService.getWaterRate();
                rate = wr.getRate();
                
                wr.setConsumption(wr.getEndReading() - wr.getStartReading());
                wr.setAmount(wr.getConsumption() * rate);
                
                logger.info("UserID = " + wr.getUserId() + " HouseID = " + wr.getHouseId() 
                        +" Consumption = " + wr.getConsumption() + " Rate = " + rate 
                        +" Amount = " + wr.getAmount() + " Used for month = " + wr.getUsedMonth());
                
                System.out.println("UserID = " + wr.getUserId() + " HouseID = " + wr.getHouseId() 
                        +" Consumption = " + wr.getConsumption() + " Rate = " + rate 
                        +" Amount = " + wr.getAmount() + " Used for month = " + wr.getUsedMonth());
                
               
                // add to a List
                waterReadingList.add(wr);
            }
            
            
            // INSERT THE WATER READING LIST TO DB
            waterReadingService.insertWaterReading(waterReadingList);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
