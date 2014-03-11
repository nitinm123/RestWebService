package com.avilyne.rest.resource;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.omg.CORBA.portable.OutputStream;

import sun.net.www.protocol.mailto.MailToURLConnection;
 
import com.avilyne.rest.model.Person;
import com.avilyne.rest.model.PersonDb;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.sun.xml.internal.txw2.TypedXmlWriter;
 
@Path("/person")
public class PersonResource {
 
	private final static String REG_ID = "regid";

    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String EMAIL = "email";
    
    private final static String FROM_CITY = "from_city";
    private final static String TO_CITY = "to_city";
    private final static String USER_LOGIN = "user_login";
    private final static String USER_PWD = "user_pwd";
    
    private final static String FLIGHT1 = "flight1";
    private final static String FLIGHT2 = "flight2";
    private final static String TRVL_DATE = "trvl_date";
    private final static String FLEX_WEEKS = "flex_weeks";
    private final static String LANGUAGE_PREF = "language_pref";
    private final static String PSG_TYPE =  "psg_type";
    private final static String REWARDS = "rewards";
    private final static String PSG_GENDER = "psg_gender";
    private final static String PSG_NAME = "psg_name";
    private final static String PSG_EMAIL = "psg_email";
    private final static String PSG_CONTACT_PHONE = "psg_contact_phone";
    private final static int PSG_MATCH_ID = -1;
    private final static String COMM_MESSAGE = "comm_message";
    
    private final static String REGISTRATION_ID = "registration_id";
    
    //private Person person_arr[];
    //private Person prev_person_arr[];
    private static int person_db = 0;
    
    private static ArrayList<Person> person_arr = new ArrayList<Person>();
         
    private static ArrayList<String> reg_id_arr = new ArrayList<String>();
    
    private Person def_person = new Person(1);
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }

    
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getSamplePerson() {
         
        System.out.println("Returning sample person 1: " + def_person.id);
        String headers[]={"null"};
        try {
			sendMail("mhaske.nitin@gmail.com", "nitinmhaske1979@yahoo.com", "GoKarmaa: companion found", "Please connect", headers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        return def_person;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Path("new_entry")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(
            MultivaluedMap<String, String> personParams
            ) {

    	Person person = new Person(1);
    	
    	//person_arr.add(person); // remove to remove
    	    	    	
    	person_db+=1;
    	
    	person.id = person_db-1;
      	
    	person.from_city = personParams.getFirst(FROM_CITY);
      	person.to_city = personParams.getFirst(TO_CITY);

      	person.flight1 = personParams.getFirst(FLIGHT1);
      	person.flight2 = personParams.getFirst(FLIGHT2);
      	person.date = personParams.getFirst(TRVL_DATE);
      	person.flex_weeks = personParams.getFirst(FLEX_WEEKS);
      	person.language_pref = personParams.getFirst(LANGUAGE_PREF);
      	person.psg_type =  personParams.getFirst(PSG_TYPE);
      	person.rewards = personParams.getFirst(REWARDS);
       	person.psg_gender = personParams.getFirst(PSG_GENDER);
      	person.psg_name = personParams.getFirst(PSG_NAME);
      	person.psg_email = personParams.getFirst(PSG_EMAIL);
      	person.psg_contact_phone = personParams.getFirst(PSG_CONTACT_PHONE);
  
      	person.user_login = personParams.getFirst(USER_LOGIN);
      	person.user_pwd = personParams.getFirst(USER_PWD);
    	
    	person.registration_id = personParams.getFirst(REGISTRATION_ID);
      	
        System.out.println("Storing posted " + person.psg_type + " " + person.psg_email + " " + person.from_city + "  " + person.to_city);
        
        person_arr.add(person);
        System.out.println("size= " + person_arr.size() + "person_db= " + person_db);
        
        PersonDb PersonDB = new PersonDb();
        PersonDB.insert_db(person);
        PersonDB.display_db();
        
          
        
        return person;
                         
    }
    
    @POST
    @Path("communicate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person communicate(
            MultivaluedMap<String, String> personParams
            ) {
    	Person person = new Person(1);
    	

       
    	return person; 
    }
    
    
    @POST
    @Path("new_reg")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person new_reg(
            MultivaluedMap<String, String> personParams
            ) {
    
    	System.out.println("got new registration ID" + personParams.getFirst(REG_ID));	    	
    	reg_id_arr.add(personParams.getFirst(REG_ID));
   	 
    	def_person.registration_id=personParams.getFirst(REG_ID);
    	
    	/*
    	HttpURLConnection connection = null;  
    	URL url = null;
    	// assign URL
    	try {
    		url = new URL("https://android.googleapis.com/gcm/send");
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	// open Http connection
    	try {
    		connection = (HttpURLConnection)url.openConnection();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	// set method as POST
    	try {
    		connection.setRequestMethod("POST");
    	} catch (ProtocolException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	// con.setRequestProperty("User-Agent", USER_AGENT);
    	connection.setRequestProperty("Content-Type", "application/json");
    	connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    	
    	connection.setRequestProperty("Authorization", "AIzaSyD7Eu6lLEs81-zOqI46gBOChklnertsOZw");
			
    	connection.setUseCaches (false);
    	connection.setDoInput(true);
    	connection.setDoOutput(true);

    	
    	//Send request
    	DataOutputStream out = null;
    	try {
    		out = new DataOutputStream (connection.getOutputStream ());
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	
	
    	OutputStreamWriter wr = new OutputStreamWriter(out);
    
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("registration_id", personParams.getFirst(REG_ID));
    	} catch (JSONException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
	
    	
	
    	try {
    		obj.put("data", "hello Nitin");
    	} catch (JSONException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
 
    	// write, flush and close
    	try {
    		wr.write(obj.toString());
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	try {
    		wr.flush ();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	try {
    		wr.close ();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	*/
    	return def_person;
    }
    
    
    
    @POST
    @Path("find_matches")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person matchPerson(
            MultivaluedMap<String, String> personParams
            ) {
    	
    	System.out.println("In matching");
    	boolean restart = false;
        //boolean restart = true;
      	if(restart)
      	{
        	// delete the arrays
      	}
      	
    	Person person = new Person(1);
    	Person match_person = new Person(1);

      	person.from_city = personParams.getFirst(FROM_CITY);
      	person.to_city = personParams.getFirst(TO_CITY);
      	System.out.println("to_city as is: " + personParams.getFirst(TO_CITY));
      	System.out.println("person.to_city: " + person.to_city);
      			
      	person.flight1 = personParams.getFirst(FLIGHT1);
      	person.flight2 = personParams.getFirst(FLIGHT2);
      	person.date = personParams.getFirst(TRVL_DATE);
      	person.flex_weeks = personParams.getFirst(FLEX_WEEKS);
      	person.language_pref = personParams.getFirst(LANGUAGE_PREF);
      	person.psg_type =  personParams.getFirst(PSG_TYPE);
      	person.rewards = personParams.getFirst(REWARDS);
       	person.psg_gender = personParams.getFirst(PSG_GENDER);
      	person.psg_name = personParams.getFirst(PSG_NAME);
      	person.psg_email = personParams.getFirst(PSG_EMAIL);
      	person.psg_contact_phone = personParams.getFirst(PSG_CONTACT_PHONE);
  
      	person.user_login = personParams.getFirst(USER_LOGIN);
      	person.user_pwd = personParams.getFirst(USER_PWD);
      	
      
    	   	
    	// display output results
      	System.out.println("size= " + person_arr.size() + "person_db= " + person_db);
      	
      	find_matches(person, match_person);
      	    	
    	//return ret_person_arr;
    	return match_person;
    	
    }
    
   
    public void find_matches(Person to_match_person, Person ret_match_person)
    {
    	
    	if(person_db == 0) delete_all_rows();
    	
    	int date_matching_entries = 0;
    	Connection c = null;
        Statement stmt = null;
		try {
        	Class.forName("org.sqlite.JDBC");
        	c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
            ((java.sql.Connection) c).setAutoCommit(false);
            System.out.println("Opened database successfully");
           
            stmt = (Statement) ((java.sql.Connection) c).createStatement();
            ResultSet rs = ((java.sql.Statement) stmt).executeQuery( "SELECT * FROM PERSON_TBL; 	" );
            while ( rs.next() ) 
            {
            	    		
            // 5 entries matching is good enough
            if(date_matching_entries== 5) break;
          	
          	// Match criteria 1: type of passenger
        	String psg_type_entered = to_match_person.psg_type;
        	String psg_type_db = rs.getString("psg_type");
        	System.out.println("psg_type_db: " + psg_type_db + "psg_type_entered: " + psg_type_entered);
        	       	
    		if(((psg_type_entered.toLowerCase().equals("buddy")) && (!(psg_type_db.toLowerCase().equals("buddy")))) ||
    	    		   ((psg_type_entered.toLowerCase().equals("assist_provider")) && (!(psg_type_db.toLowerCase().equals("assist_seeker")))) ||
    	    		   ((psg_type_entered.toLowerCase().equals("assist_seeker")) && (!(psg_type_db.toLowerCase().equals("assist_provider")))))
    	    		   {
    	    			continue;
    	    		   }	

    		// Match criteria 2: Source and destination city
          	System.out.println("from_city_db: " +  rs.getString("from_city") + "from city: " + to_match_person.from_city);
          	System.out.println("to_city_db: " +  rs.getString("to_city") + "to city: " + to_match_person.to_city);
          	
        	    		
          	// check for src and destination match
          	if((rs.getString("from_city").equals(to_match_person.from_city)) &&
          		(rs.getString("to_city").equals(to_match_person.to_city)))
          	{
          		       		
          		System.out.println("Source and destination matches found");
          		
          		// Do date matching
        		String travel_date_db = rs.getString("trvl_date");
        		String date_entered = to_match_person.date;
        		
        		// if no entry for date, make them optimistically equal
          		if(travel_date_db.equals("")) travel_date_db = date_entered;
    		    
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    			
    			// convert into date format
    			java.util.Date travel_db_date = null;
				try {
					travel_db_date = sdf.parse(travel_date_db);												
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				java.util.Date travel_entered_date = null;
				try {
					travel_entered_date = sdf.parse(date_entered);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

          		
				// get the flexible weeks data
    			String flex_weeks_db_sub = rs.getString("flex_weeks").substring(4, 5);
    			int flex_weeks_db_int = Integer.parseInt(flex_weeks_db_sub);
    			String flex_weeks_entered_sub = to_match_person.flex_weeks.substring(4, 5);
    			int flex_weeks_entered_int = Integer.parseInt(flex_weeks_entered_sub);
          		
    			// get the difference in the date
    			long diff_date, ref_a_min = 0, ref_a_pls = 0, ref_b_min = 0, ref_b_pls = 0;
    			System.out.println("date entered" + (((long)(travel_entered_date.getTime()))/(24 * 60 * 60 * 1000)));
    			System.out.println("date in db" + (((long)(travel_db_date.getTime()))/(24 * 60 * 60 * 1000)));
 			    
 			    if(travel_entered_date.getTime() >= travel_db_date.getTime())
 			    	diff_date = (((long)(travel_entered_date.getTime() - travel_db_date.getTime()))/(24 * 60 * 60 * 1000));
 			    else
 			    	diff_date = (((long)(travel_db_date.getTime() - travel_entered_date.getTime()))/(24 * 60 * 60 * 1000));
 			    	
 			    System.out.println(diff_date);
          		
 			    // comparing by keeping flex weeks in mind, keeping base as 1000 to avoid negative numbers
			    if(travel_entered_date.getTime() > travel_db_date.getTime())
			    {
			    	ref_a_min = 1000 + diff_date - (flex_weeks_entered_int*7);
			    	ref_a_pls = 1000 + diff_date + (flex_weeks_entered_int*7);
			    	ref_b_min = 1000 - (flex_weeks_db_int*7);
			    	ref_b_pls = 1000 + (flex_weeks_db_int*7);
			    }
			    else if(travel_entered_date.getTime() < travel_db_date.getTime())
			    {
			    	ref_a_min = 1000 + diff_date - (flex_weeks_db_int*7);
			    	ref_a_pls = 1000 + diff_date + (flex_weeks_db_int*7);
			    	ref_b_min = 1000 - (flex_weeks_entered_int*7);
			    	ref_b_pls = 1000 + (flex_weeks_entered_int*7);
			    }
			    // case 1: e > d then if dp < em implies no match
			    // case 2: d > e then if ep < dm implies no match
			    // if (ref_b_pls < ref_a_min) no match

			    //old code if((!((ref_b_min > ref_a_pls) || (ref_b_pls > ref_a_min)))||
			    if ((ref_b_pls >= ref_a_min) || (diff_date == 0))
			    {
			    	System.out.println("date also matches");
				   
			    	// return the matches
			    	
			    	ret_match_person.from_city = ret_match_person.from_city + ":" + rs.getString("from_city");
			    	ret_match_person.to_city = ret_match_person.to_city + ":" + rs.getString("to_city");
			    	ret_match_person.psg_name = ret_match_person.psg_name + ":" + rs.getString("psg_name");
			    	ret_match_person.date = ret_match_person.date + ":" + rs.getString("trvl_date");
			    	ret_match_person.flex_weeks = ret_match_person.flex_weeks + ":" + rs.getString("flex_weeks");
			    	ret_match_person.language_pref = ret_match_person.language_pref + ":" + rs.getString("language_pref");
			    	ret_match_person.psg_gender = ret_match_person.psg_gender + ":" + rs.getString("psg_gender");
			    	ret_match_person.rewards = ret_match_person.rewards + ":" + rs.getString("reward");
			    	ret_match_person.psg_email = ret_match_person.psg_email + ":" + rs.getString("psg_contact_email");
			    	System.out.println("psg_email:" + ret_match_person.psg_email);
			    	date_matching_entries+=1;
			    }
          	 }
            }
            rs.close();
            ((java.sql.Statement) stmt).close();
            c.close();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
       }
    
    // for debug, flush database
    public void flush_db()
    {
    	for (int idx = 0; idx < person_arr.size(); idx++)
              	person_arr.remove(idx); 	
    	   	
    }
   
    
    public void delete_all_rows()
    {
    	Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\business/person.db");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          String sql = "DELETE from PERSON_TBL";
          stmt.executeUpdate(sql);
          c.commit();
          } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	
    }
    public static void sendMail(String from, String to, String subject, String body, String[] headers) throws IOException {
		   System.setProperty("mail.host", "192.168.1.66");

		   URL u = new URL("mailto:"+to);
		   MailToURLConnection con = (MailToURLConnection)u.openConnection();
		   OutputStream os = (OutputStream) con.getOutputStream();
		   OutputStreamWriter w = new OutputStreamWriter(os);

		   DateFormat df = new SimpleDateFormat("E, d MMM yyyy H:mm:ss Z");
		   Date d = new Date();
		   String dt = df.format(d);
		   String mid = d.getTime()+from.substring(from.indexOf('@'));

		   w.append("Subject: "+subject+"\r\n");
		   w.append("Date: " +dt+ "\r\n");
		   w.append("Message-ID: <"+mid+ ">\r\n");
		   w.append("From: "+from+"\r\n");
		   w.append("To: <"+to+">\r\n");
		   if(headers!=null) {
		      for(String h: headers)
		         w.append(h).append("\r\n");
		   }
		   w.append("\r\n");

		   w.append(body.replace("\n", "\r\n"));
		   w.flush();
		   w.close();
		   os.close();
		   con.close();
		}
	
}




