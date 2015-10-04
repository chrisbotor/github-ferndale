/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.dao.impl.IConnectionDaoImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class WaterReadingUtil {

    private static final String CSV_FILES_PATH = MessageBundle.getProperty("csvFilesPath");
    private static final String CSV_FILE_NAME_SUFFIX = MessageBundle.getProperty("csvFileNameSuffix");

    private List<HolderBean> populateCsv() {
        BufferedReader br = null;
        List<HolderBean> list = new ArrayList<HolderBean>();

        try {
            
            DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
            Date date = new Date();
            System.out.println("Date is : " +dateFormat.format(date));
            
            String strFile = CSV_FILES_PATH.concat(dateFormat.format(date).concat(CSV_FILE_NAME_SUFFIX));
            String strLine = "";
            int lineNumber = 0;
            String[] splitStr = null;

            HolderBean hb = null;
            System.out.println("strFile is : "+strFile);
            br = new BufferedReader(new FileReader(strFile));
            //HolderBean hb = new HolderBean();
            while ((strLine = br.readLine()) != null) {

                splitStr = strLine.split(",");

                for (int x = 0; x < splitStr.length; x++) {
                    hb = new HolderBean();
                    hb.setId(Integer.parseInt(splitStr[0]));
                    hb.setName(splitStr[1]);
                    hb.setReading(Integer.parseInt(splitStr[2]));

                }
                list.add(hb);
            }

        } catch (Exception ex) {
            System.out.println("Exception is " + ex.toString());
        }
        return list;
    }

    private void insertIntoWaterReading(List<HolderBean> list) {
        
        Connection connection = null;
    	IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
        connection = connectionDao.getConnection();
        
        DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
        Date date = new Date();
        //System.out.println(dateFormat.format(date));
        
        // check if the following id is already available in water reading
        for (HolderBean hb : list) {
            try {
                String checkIfExistSql = "SELECT COUNT(1) AS total from water_reading where billed_to = "+hb.getId();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(checkIfExistSql);
                int checker = 0;
                while (resultSet.next()) {
                    checker = resultSet.getInt("total");
                    // if zero means insert
                    if(checker == 0)
                    {
                        String insertSql = "insert into water_reading (billed_to,billing_month,start_reading,end_reading,created_at,updated_at) values ("+ hb.getId()+",DATE_FORMAT(NOW(), '%m%Y'),0,"+hb.getReading()+",Now(),Now())";
                        System.out.println(insertSql);
                        int exec = statement.executeUpdate(insertSql);
                    }else
                    {
                        String getLastEndReading = "select wr.end_reading from water_reading as wr where billed_to ="+hb.getId();
                        ResultSet rs = statement.executeQuery(getLastEndReading);
                        int startReading = 0;
                        while(rs.next())
                        {
                            startReading = rs.getInt("end_reading");
                            String updateSql = "update water_reading set billing_month = DATE_FORMAT(NOW(), '%m%Y')"+
                                            ",start_reading="+startReading+
                                            ",end_reading="+hb.getReading()+",updated_at=Now() where billed_to="+hb.getId();
                        System.out.println(updateSql);
                        int exec = statement.executeUpdate(updateSql);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(WaterReadingUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void insertToWaterReadingHistory()
    {
        try {
            Connection connection = null;
            IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
            connection = connectionDao.getConnection();
            String insertSql = "insert into water_reading_history (billed_to,billing_month,start_reading,end_reading,created_at,updated_at,status,amount)\n" +
                               "select billed_to,billing_month,start_reading,end_reading,created_at,updated_at,'Unpaid',(end_reading - start_reading) * (select amount from rates where code = 'WaterRate')  from water_reading";
            Statement statement = connection.createStatement();
            System.out.println(insertSql);
            int exec = statement.executeUpdate(insertSql);
        } catch (SQLException ex) {
            Logger.getLogger(WaterReadingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertToJobOrder()
    {
        try {
            Connection connection = null;
            IConnectionDaoImpl connectionDao = new IConnectionDaoImpl();
            connection = connectionDao.getConnection();
            String insertSql = "insert into job_orders (order_type_id,request_id,created_at,user_id,status)\n" +
                                "select 3,id,updated_at,billed_to,0 from water_reading_history where billing_month = DATE_FORMAT(NOW(), '%m%Y')";
            Statement statement = connection.createStatement();
            System.out.println(insertSql);
            int exec = statement.executeUpdate(insertSql);
        } catch (SQLException ex) {
            Logger.getLogger(WaterReadingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            WaterReadingUtil rcfu = new WaterReadingUtil();
            rcfu.insertIntoWaterReading(rcfu.populateCsv());
            Thread.sleep(3000);
            rcfu.insertToWaterReadingHistory();
            rcfu.insertToJobOrder();
        } catch (InterruptedException ex) {
            Logger.getLogger(WaterReadingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class HolderBean {

        private int id;
        private String name;
        private int reading;

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the reading
         */
        public int getReading() {
            return reading;
        }

        /**
         * @param reading the reading to set
         */
        public void setReading(int reading) {
            this.reading = reading;
        }
    }
}
