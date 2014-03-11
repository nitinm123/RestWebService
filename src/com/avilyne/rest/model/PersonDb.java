package com.avilyne.rest.model;
import java.sql.*;
import org.sqlite.JDBC;

import com.avilyne.rest.model.Person;
public class PersonDb {
/*
	public static void delete_table()
	{
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
		      System.out.println("Opened database successfully");
		      
		      stmt = (Statement) ((java.sql.Connection) c).createStatement();
		      String sql = "DROP TABLE PERSON_TBL ";
		      ((java.sql.Statement) stmt).executeUpdate(sql);
		      ((java.sql.Statement) stmt).close();
		      c.close();
		      
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		   
	}
	
	public static void main( String args[] )
	  {
		delete_table();
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
	      System.out.println("Opened database successfully");
	      
	      stmt = (Statement) ((java.sql.Connection) c).createStatement();
	      String sql = "CREATE TABLE PERSON_TBL " +
	                   "(ID INT PRIMARY KEY     NOT NULL,"
						+ "PSG_NAME  TEXT,"
						+ "USER_LOGIN TEXT,"
						+ "USER_PWD TEXT,"
						+ "PSG_TYPE TEXT,"
						+ "PSG_CONTACT_EMAIL TEXT,"
						+ "PSG_CONTACT_PHONE TEXT,"
						+ "PSG_GENDER TEXT,"
						+ "PSG_AGE TEXT,"
						+ "LANGUAGE_PREF TEXT,"
						+ "FROM_CITY TEXT,"
						+ "TO_CITY TEXT,"
						+ "FLIGHT1 TEXT,"
						+ "FLIGHT2 TEXT,"
						+ "FLIGHT3 TEXT,"
						+ "TRVL_DATE INTEGER,"
						+ "FLEX_WEEKS INTEGER,"
						+ "REWARD TEXT,"
						+ "CREATED_DATE INTEGER,"
						+ "MODIFIED_DATE INTEGER," 
						+ "REGISTRATION_ID TEXT,"
						+ "COMM_MESG TEXT," 
						+ "MATCH_STS TEXT,"
						+ "MATCH_ID INTEGER)"; 
	                   
	   ((java.sql.Statement) stmt).executeUpdate(sql);
	      ((java.sql.Statement) stmt).close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
		Person person = new Person (1);
		insert_db(person);
		display_db();
	  }
	  
	*/  
	
	public static void insert_db(Person person_arg)
	//public static void main( String args[] )
	  {
		Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
        c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
        ((java.sql.Connection) c).setAutoCommit(false);
        System.out.println("Opened database successfully");

        
        // inserting data
        PreparedStatement prep = c.prepareStatement("insert into person_tbl values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        prep.setInt(1, person_arg.id);
        prep.setString(2, person_arg.psg_name);
        prep.setString(3, person_arg.user_login);
        prep.setString(4, person_arg.user_pwd);
        prep.setString(5, person_arg.psg_type);
        prep.setString(6, person_arg.psg_email);
        prep.setString(7, person_arg.psg_contact_phone);
        prep.setString(8, person_arg.psg_gender);
        prep.setString(9, person_arg.psg_age);
        prep.setString(10, person_arg.language_pref);
        prep.setString(11, person_arg.from_city);
        prep.setString(12, person_arg.to_city);
        prep.setString(13, person_arg.flight1);
        prep.setString(14, person_arg.flight2);
        prep.setString(15, "null");
        prep.setString(16, person_arg.date);
        prep.setString(17, person_arg.flex_weeks);
        prep.setString(18, person_arg.rewards);
        prep.setString(21, person_arg.registration_id);
        prep.setString(22, person_arg.comm_msg);
        
        //prep.setInt(21, person_arg.comp_match_id);
       // String sql = "INSERT INTO COMPANY (ID,PSG_NAME,USER_LOGIN,USER_PWD,PSG_TYPE,PSG_CONTACT_EMAIL,PSG_CONTACT_PHONE,PSG_GENDER,PSG_AGE,LANGUAGE_PREF,FROM_CITY,TO_CITY,FLIGHT1,FLIGHT2,FLIGHT3,TRVL_DATE,FLEX_WEEKS,REWARD,MATCH_COMP_ID) " + 
       //              "VALUES (person.id, person.psg_name, person.user_login, person.user_pwd,person.psg_type,person.psg_email, person.psg_contact_phone,person.psg_gender,person.psg_age,person.language_pref,person.from_city,person.to_city,person.flight1,person.flight2,`null`,person.date,person.flex_weeks,person.rewards,person.comp_match_id);"; 
       prep.execute();

       ((java.sql.Connection) c).commit();
        c.close();
     
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
        
        System.out.println("Records created successfully"); 
	  }
	  
	
	public static void display_db()
	//public static void main( String args[] )
	  {
		Connection c = null;
        Statement stmt = null;
		try {
        	Class.forName("org.sqlite.JDBC");
        	c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
            ((java.sql.Connection) c).setAutoCommit(false);
            System.out.println("Opened database successfully");

            
            stmt = (Statement) ((java.sql.Connection) c).createStatement();
            ResultSet rs = ((java.sql.Statement) stmt).executeQuery( "SELECT * FROM PERSON_TBL;" );
            while ( rs.next() ) {
               int id = rs.getInt("id");
               String  name = rs.getString("psg_name");
               String  from_city = rs.getString("from_city");

               System.out.println( "ID = " + id );
               System.out.println( "NAME = " + name );
               System.out.println( "FROM_CITY = " + from_city );
               System.out.println();
            }
            rs.close();
            ((java.sql.Statement) stmt).close();
            c.close();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
	  }
	  
	}

