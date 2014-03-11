package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
 
	public String from_city="def_city";   
	public String to_city="def_city";   
    public int id=0;
    public String flight1="";
  	public String flight2="";
  	public String date="def_date";
  	public String flex_weeks="def_flex_weeks";
  	public String language_pref="def_language_pref";
  	public String psg_type="def_psg_type";
  	public String rewards="def_rewrds";
   	public String psg_gender="def_psg_gender";
 	public String psg_age="def_psg_age";
  	public String psg_name="def_psg_name";
  	public String psg_email="def_psg_email";
  	public String psg_contact_phone="def_psg_contact_phone";
  	public String user_login="def_user_login";
  	public String user_pwd="def_user_pwd";
  	public static String registration_id="null";
  	public static String match_sts = "not_matched";
	public static String comm_msg = "null";
  	public int match_id=-1;
  	
	    public Person() {
         
        id = -1;
         
    }
 
    public Person(int id) {
 
        this.id = id;
    }
 
     

      
    
}

