package com.telefonica;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public Main() {
    }

    private Connection getConnection() throws JRException
    {
        Connection conn;

        try
        {
            //Change these settings according to your local configuration
            String driver = "oracle.jdbc.driver.OracleDriver";
            String connectString = "jdbc:oracle:thin:@10.80.1.86:1531:SCEL";

            java.util.Properties info = new java.util.Properties();
            info.put ("user", "accenture");
            info.put("password", "Colombia50$");
            info.put("v$session.osuser", "accenture");
            info.put("v$session.machine", "WORKGROUP\\ACCENTURE_10-HP");
            info.put("v$session.terminal", "ACCENTURE_10-HP");

            Class.forName(driver);
            conn = DriverManager.getConnection(connectString, info);
        }
        catch (ClassNotFoundException e)
        {
            throw new JRException(e);
        }
        catch (SQLException e)
        {
            throw new JRException(e);

        }

        return conn;
    }

    public void fill() throws JRException
    {
        long start = System.currentTimeMillis();
        //Preparing parameters
        Image image =
                Toolkit.getDefaultToolkit().createImage(
                        JRLoader.loadBytesFromResource("dukesign.jpg")
                );
        MediaTracker traker = new MediaTracker(new Panel());
        traker.addImage(image, 0);
        try
        {
            traker.waitForID(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ReportTitle", "The First Jasper Report Ever");
        parameters.put("MaxOrderID", new Integer(10500));
        parameters.put("SummaryImage", image);

        JasperFillManager.fillReportToFile("build/reports/FirstJasper.jasper", parameters, getConnection());
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }


    /**
     *
     */
    public void print() throws JRException
    {
        long start = System.currentTimeMillis();
        JasperPrintManager.printReport("build/reports/FirstJasper.jrprint", true);
        System.err.println("Printing time : " + (System.currentTimeMillis() - start));
    }


    /**
     *
     */
    public void pdf() throws JRException
    {
        long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile("build/reports/FirstJasper.jrprint");
        System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
	    Main m = new Main();
        try {
            m.getConnection();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
