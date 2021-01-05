package com.example.controller;

import java.text.ParseException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.CustomerService;

@Controller
public class MarketplaceStatisticsController {
	@Autowired
	private CustomerService cs;
	
	@RequestMapping("/")
	public String showHome() throws ParseException {
		return "index.jsp";
	}


	// ajax request handled for Subscribers per product chart 
	@RequestMapping(value="api/v1/EmailSendingServlet",method=RequestMethod.POST)
	@ResponseBody
	public String sendMail(Model model, @RequestParam(value="img") String imgu) throws ParseException {
		String result;
		   
		   // Recipient's email ID needs to be mentioned.
		   String to = "nivethasuresh3@gmail.com";

		   // Sender's email ID needs to be mentioned
		   String from = "nisuresh@informatica.com";

		   // Assuming you are sending email from localhost
		   String host = "in23ex01.informatica.com";

		   // Get system properties object
		   Properties properties = System.getProperties();

		   // Setup mail server
		   properties.setProperty("mail.smtp.host", host);

		   // Get the default Session object.
		   Session mailSession = Session.getDefaultInstance(properties);

		   try {
		      // Create a default MimeMessage object.
		      MimeMessage message = new MimeMessage(mailSession);
		      
		      // Set From: header field of the header.
		      message.setFrom(new InternetAddress(from));
		      
		      // Set To: header field of the header.
		      message.addRecipient(Message.RecipientType.TO,
		                               new InternetAddress(to));
		      // Set Subject: header field
		      message.setSubject("This is the Subject Line!");
		      
		      
		      String chartConfig = "{" +
					  "\"type\": \"bar\"," +
					  "\"data\": {" +
					    "\"labels\": [2012, 2013, 2014, 2015, 2016]," +
					    "\"datasets\": [{" +
					      "\"label\": \"Users\"," +
					      "\"data\": [120, 60, 50, 180, 120]" +
					    "}]" +
					  "}" +
					"}";
			
			String chartConfig1 = "{" +
					  "\"type\": \"bar\"," +
					  "\"data\": {" +
					    "\"labels\": [2012, 2013, 2014, 2015, 2016]," +
					    "\"datasets\": [{" +
					      "\"label\": \"Users\"," +
					      "\"data\": [120, 60, 50, 180, 120]" +
					    "}]" +
					  "}" +
					"}";
		      
		      // Now set the actual message
		      message.setContent( "Hello, please see the chart from service below:<br><br><img src=\""+imgu+"\">","text/html");
		      //message.setContent( "Hello, please see the chart1 below:<br><br><img src=\"" + "https://quickchart.io/chart?width=500&height=200&chart=" +
		      	//    URLEncoder.encode(chartConfig, StandardCharsets.UTF_8) + "\">"+"<img src=\""+"https://quickchart.io/chart?width=500&height=200&chart="+URLEncoder.encode(chartConfig1, StandardCharsets.UTF_8)+ "\">","text/html");
		      // Send message
		      Transport.send(message);
		      result = "Sent message successfully....";
		   } catch (MessagingException mex) {
		      mex.printStackTrace();
		      result = "Error: unable to send message....";
		   }
		return result;

	}
	
	@RequestMapping("api/v1/subscriberDetails")
	@ResponseBody
	public String NumberOfSubscribers(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.NumberOfSubscribers(startDate, endDate, product);
	}
	
	//ajax request handled for hours per product chart 
	@RequestMapping("api/v1/customerDetails")
	@ResponseBody
	public String UsagePerProduct(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.UsagePerProduct(startDate, endDate, product);
	}
	
	// ajax request handled for Deployment Hours / Customers graph
	@RequestMapping("api/v1/customerDetailsStacked")
	@ResponseBody
	public String CompanyData(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.CompanyData(startDate, endDate, product);
	}
	
	// ajax request handled for total number of subscribers
	@RequestMapping("api/v1/countSubscribers")
	@ResponseBody
	public int countSubscribers(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.countSubscribers(startDate, endDate, product);
	}
	
	// ajax request handled for total number of hours
	@RequestMapping("api/v1/countHours")
	@ResponseBody
	public int countHours(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.countHours(startDate, endDate, product);
	}
	
	// ajax request handled for monthly new subscribers
	@RequestMapping("api/v1/customerDetailsByDate")
	@ResponseBody
	public String customerDetailsByDate(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.customerDetailsByDate(startDate, endDate, product);
	}
	
	// ajax request handled for Usage Units / Country graph
	@RequestMapping("api/v1/country")
	@ResponseBody
	public String countryDetails(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.countryDetails(startDate, endDate, product);
	}
	
	/*@RequestMapping("api/v1/usagepercountry")
	@ResponseBody
	public String usagepercountry(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
			return cs.usagepercountry(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/revenueperproduct")
	@ResponseBody
	public String revenueperproduct(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
			return cs.revenueperproduct(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/instanceperproduct")
	@ResponseBody
	public String instanceperproduct(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
			return cs.instanceperproduct(startDate, endDate, product);
	}*/
	
	//azure
	
	@RequestMapping("api/v1/azure/customerDetailsByDate")
	@ResponseBody
	public String azurecustomerDetailsByDate(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate) throws ParseException {
		return cs.azurecustomerDetailsByDate(startDate, endDate);
	}
	
	@RequestMapping("api/v1/azure/countSubscribers")
	@ResponseBody
	public int azurecountSubscribers(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate) throws ParseException {
		return cs.azurecountSubscribers(startDate, endDate);
	}
	
	@RequestMapping("api/v1/azure/countHours")
	@ResponseBody
	public int azurecountHours(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.azurecountHours(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/azure/customerDetails")
	@ResponseBody
	public String azureUsagePerProduct(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.azureUsagePerProduct(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/azure/country")
	@ResponseBody
	public String azurecountryDetails(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.azurecountryDetails(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/azure/customerDetailsStacked")
	@ResponseBody
	public String azureVisitsDetailsStacked(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate, @RequestParam(value="product") String product) throws ParseException {
		return cs.azureVisitsDetailsStacked(startDate, endDate, product);
	}
	
	@RequestMapping("api/v1/azure/trendsDetails")
	@ResponseBody
	public String trendsDetails(Model model, @RequestParam(value="toTime") String startDate,
			@RequestParam(value="fromTime") String endDate) throws ParseException {
		return cs.trendsDetails(startDate, endDate);
	}
	


	
}
