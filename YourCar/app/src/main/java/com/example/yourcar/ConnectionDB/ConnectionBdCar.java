package com.example.yourcar.ConnectionDB;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lry41
 * @version 1.0
 * @return conn qui contient le driver +URL+UserName+Password
 */

public class ConnectionBdCar {
	public static String dbhost="localhost";
	public static String dbport="8889";
	public static String dbuser="root";
	public static String dbpass="root";
	public static String dbname="Banque";

	public static String str = "jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";




	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("JAVA:  reussi");
		} catch (Exception e) {
			System.out.println("JAVA: Class.forName() error");
			e.printStackTrace();
		}
		try {

			Connection connection = DriverManager.getConnection(str,"root","root");
			Statement statement=connection.createStatement();
			return connection;

			}
		catch(SQLException e){

			Log.d("erreur", str);
			Log.d("erreur", "connect: erooooooooor");
			Logger.getLogger(ConnectionBdCar.class.getName()).log(Level.SEVERE,null,e);
			e.printStackTrace();

		}


		return null;

	}
	
}
