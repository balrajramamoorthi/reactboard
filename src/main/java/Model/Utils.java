package Model;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Utils
{
	public static String DEFAULT_DATE_DDMMMYYHHMMSS = "dd/MMM/yy HH:mm:ss";
	public static String DDMMMYYHHMMSSMSS = "dd-MMM-yy hh.mm.ss.S aa";
	public static String DATE_DDMMYYYY = "dd/MM/yyyy";
	public static String DATEFORMATddMMyyyy = "dd-MM-yyyy";
	public static String DDMMYYY = "dd-MM-YYYY";
	public static String DATEFORMATyyyyMMdd = "yyyy-MM-dd";
	public static String DATEFORMATyyyyMMMMdd = "yyyyMMdd";
	private static final int BUFSIZE = 4096;

	public static String getCurrentDateTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_DDMMMYYHHMMSS);
		return sdf.format(new Date());
	}

	public static String getCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATddMMyyyy);
		return sdf.format(new Date());
	}

	public static Object clearNull(Object str)
	{
		Object returnValue = str;
		if (str == null)
			returnValue = "";
		return returnValue;
	}

	public static String clearNull(String str)
	{
		String returnValue = str;
		if (str == null)
			returnValue = "";
		return returnValue;
	}

	public static Object clearNull(Object str, boolean returnDummy)
	{
		Object returnValue = str;
		if (str == null)
			returnValue = "dum";

		return returnValue;
	}

	public static String getDummyString(String str, boolean returnDummy)
	{
		String returnValue = str;
		if (str == null || str.length() == 0)
			returnValue = "dum";

		return returnValue;
	}

	public static Boolean clearNull(Boolean str)
	{
		Boolean returnValue = true;
		if (str == null)
			returnValue = false;
		return returnValue;
	}

	public static Long getLong(String str)
	{
		Long returnVal = new Long(0);
		try
		{
			returnVal = Long.parseLong(str);
		}
		catch (Exception e)
		{
			returnVal = 0L;
		}
		return returnVal;
	}

	public static Integer getInteger(String str)
	{
		Integer returnVal = new Integer(0);
		try
		{
			returnVal = Integer.parseInt(str);
		}
		catch (Exception e)
		{
			returnVal = 0;
		}
		return returnVal;
	}

	public static Float getFloat(String str)
	{
		Float returnVal = new Float(0);
		try
		{
			returnVal = Float.parseFloat(str);
		}
		catch (Exception e)
		{
			returnVal = 0F;
		}
		return returnVal;
	}

	public static Double getDouble(String str)
	{
		Double returnVal = new Double(0);
		try
		{
			returnVal = Double.parseDouble(str);
		}
		catch (Exception e)
		{
			returnVal = 0D;
		}
		return returnVal;
	}
	public static Date addMonth(Date date,int month)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, month);
	    return cal.getTime();
	}

	public static String getFormattedDateTime(Date date, String format)
	{
		String returnDate = "";
		if (date == null)
			return returnDate;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		returnDate = formatter.format(date);
		return returnDate;
	}

	public static Date getDateFromString(String format, String datetoConvert)
	{
		Date date = null;
		try
		{
			date = new SimpleDateFormat(format, Locale.ENGLISH).parse(datetoConvert);

		}
		catch (ParseException e)
		{

		}
		return date;
	}
	
	
	/*public static Date getDateFormat(String format, Date datetoConvert)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date dateObject = null;
		  try
		  {
		    String date2 = formatter2.format(formatter.parse(datetoConvert));
		    dateObject = formatter2.parse(date2);
		  }
		  catch (Exception e)
		  {
			  
		  }
	 	return dateObject;
	}*/
	
	

	public static String padZeros(String str, int length)
	{
		int numOfZeros = length - str.length();
		if (numOfZeros <= 0)
			return str;

		StringBuffer buf = new StringBuffer(numOfZeros);

		for (int i = 0; i < numOfZeros; i++)
		{
			buf.append('0');
		}

		return buf.toString() + str;
	}

	public static boolean copyFile(String src, String dest)
	{
		boolean returnVal = true;
		Path srcPath = Paths.get(src);
		Path destPath = Paths.get(dest);
		try
		{
			Files.copy(srcPath, destPath, REPLACE_EXISTING);
		}
		catch (IOException e)
		{
			returnVal = false;
			e.printStackTrace();
		}
		return returnVal;
	}

	public static boolean createDirectory(String src)
	{
		boolean returnVal = true;
		try
		{
			Path path = Paths.get(src);
			if (!Files.exists(path))
				Files.createDirectory(path);
		}
		catch (IOException e)
		{
			returnVal = false;
			e.printStackTrace();
		}
		return returnVal;
	}

	

	public static boolean deleteFileDirectory(String src)
	{
		boolean returnVal = true;
		try
		{
			Path path = Paths.get(src);
			returnVal = Files.deleteIfExists(path);
		}
		catch (IOException e)
		{
			returnVal = false;
			e.printStackTrace();
		}
		return returnVal;
	}

	public static String getDateDiff(Date date, int monthsBefore)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, monthsBefore);
		return getFormattedDateTime(cal.getTime(), DATEFORMATddMMyyyy);

	}

	public static Date getDateDiff(Date date, long monthsBefore)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, (int) monthsBefore);
		return cal.getTime();

	}

	public static Date getDateBefore(Date date, int daysplusminus)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, daysplusminus);
		return cal.getTime();

	}

	public static Date getYearBefore(Date date, int yearsplusminus)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, yearsplusminus);
		return cal.getTime();

	}

	public static Date getMonthStartDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getFinancialStartDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);

		int month = cal.get(Calendar.MONTH);
		// If months between Jan and March subtract 1 from year
		if (month >= Calendar.JANUARY && month <= Calendar.MARCH)
		{
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		}
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/*
	 * Stores multipart request in requestMap
	 * returns the requestMap hashmap object with the path where the files are uploaded
	 * also fills the hashmap with the values of the file fields and names of files.
	 */
	
	
	public static void downloadFile(String filePath, HttpServletResponse response, String displayName, int downloadinline)
			throws IOException
	{
		File file = new File(filePath);
		int length = 0;
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentLength((int) file.length());
		//String fileName = (new File(filePath)).getName();
		if (displayName == null)
			displayName = file.getName();
		String arrContent[] =
		{ "attachment;", "inline;" };
		// sets HTTP header
		response.setHeader("Content-Disposition", arrContent[downloadinline] + "filename=\"" + displayName + "\"");

		byte[] byteBuffer = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(file));

		// reads the file's bytes and writes them to the response stream
		while ((in != null) && ((length = in.read(byteBuffer)) != -1))
		{
			outStream.write(byteBuffer, 0, length);
		}

		in.close();
		outStream.close();
	}
	public static HashMap<Integer, String> getStatus()
	{
		HashMap<Integer, String> hashKeyValues = new HashMap<Integer, String>();
		hashKeyValues.put(0, "");
		hashKeyValues.put(1, "Active");
		hashKeyValues.put(2, "In-Active");
		return hashKeyValues;
	}
	public static HashMap<Integer, String> getSpareType()
	{
		HashMap<Integer, String> hashKeyValues = new HashMap<Integer, String>();
		hashKeyValues.put(0, "");
		hashKeyValues.put(1, "Spare");
		hashKeyValues.put(2, "Line");
		hashKeyValues.put(3, "Damaged");
		return hashKeyValues;
	}
	public static String getDecimalString(String d)
	{
		BigDecimal x = new BigDecimal(d);
		DecimalFormat df = new DecimalFormat();

		x.setScale(2, RoundingMode.HALF_DOWN);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(x);
	}

	private static String getDaySuffix(final int n)
	{
		if (n < 1 || n > 31)
			return "Invalid date";
		if (n >= 11 && n <= 13)
			return "th";

		switch (n % 10)
		{
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static String getLegalFormat(Object date)
	{
		Date x = null;
		String returnDate = "";
		if (date instanceof String)
		{
			x = getDateFromString(DATEFORMATddMMyyyy, (String) date);

		}
		else if (date instanceof Date)
		{
			x = (Date) date;
		}

		String day = getFormattedDateTime(x, "dd");
		returnDate = (day + getDaySuffix(Integer.parseInt(day)) + " day of " + getFormattedDateTime(x, "MMMM,yyyy"));
		return returnDate;
	}

	public static String formatLakh(double d)
	{
		String s = String.format(Locale.UK, "%1.2f", Math.abs(d));
		s = s.replaceAll("(.+)(...\\...)", "$1,$2");
		while (s.matches("\\d{3,},.+"))
		{
			s = s.replaceAll("(\\d+)(\\d{2},.+)", "$1,$2");
		}
		return d < 0 ? ("-" + s) : s;
	}

	public static String formatLakh(String d)
	{
		Double x = Double.valueOf(d);

		return formatLakh(x);

	}

	public static void getMethodNames(Object obj)
	{
		if (obj != null)
		{
			Method[] method = obj.getClass().getMethods();
			for (Method objMethod : method)
			{
				System.out.println(objMethod.toString());
			}
		}
	}


	public static long getDayDiff(Date startDate, Date endDate)
	{
		Calendar startcal = Calendar.getInstance();
		Calendar endcal = Calendar.getInstance();

		endcal.setTime(endDate);
		startcal.setTime(startDate);
		startcal.set(Calendar.AM_PM, Calendar.AM);
		startcal.set(Calendar.HOUR, 0);
		startcal.set(Calendar.MINUTE, 0);
		startcal.set(Calendar.SECOND, 0);
		startcal.set(Calendar.MILLISECOND, 0);

		endcal.set(Calendar.AM_PM, Calendar.AM);
		endcal.set(Calendar.HOUR, 0);
		endcal.set(Calendar.MINUTE, 0);
		endcal.set(Calendar.SECOND, 0);
		endcal.set(Calendar.MILLISECOND, 0);

		long timeDiff = endcal.getTimeInMillis() - startcal.getTimeInMillis();

		timeDiff = timeDiff / 86400000L;
		return timeDiff;
	}

	
	public static String appendNewLine(String str)
	{
		String retVal = str;
		if (((String) clearNull(str)).length() > 0)
			retVal = retVal + "\n";
		return retVal;
	}

	public static void main(String args[])
	{
		Date x = new Date();
		System.out.println(getFormattedDateTime(x, "MMMM dd,yyyy"));
	}
	
	public static String getDecimal(double d){
		DecimalFormat df = new DecimalFormat("##############.###");
        String deci = df.format(d);
        return deci;
        		       		
        		
        		
	}
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
}

	public static Date addDays(Date date, int numDays){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, numDays);
		return cal.getTime();
	}
	
	public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
	//Method to concatenate Date and time 
		public static Date getConcateDate(String date, String time){
			Date result = new Date();
			try{
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			String tempDate = date + " " + time;
			result = formatter.parse(tempDate);
			}catch(ParseException e){
				e.printStackTrace();
			}
			return result;
		}
		public static HashMap<Integer, String> getIssuesStatus()
		{
			HashMap<Integer, String> hashKeyValues = new HashMap<Integer, String>();
			hashKeyValues.put(0, "");
			hashKeyValues.put(1, "Pending");
			hashKeyValues.put(2, "Completed");						
			return hashKeyValues;
		}
		public static int getYear(Date d1){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(d1);
		    int month = cal.get(Calendar.MONTH);
			// If months between Jan and March subtract 1 from year
			if (month >= Calendar.JANUARY && month <= Calendar.MARCH)
			{
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
			}
		    int year = cal.get(Calendar.YEAR);
		    return year;
		 }
		public static int getYears(Date d1){
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(d1);
		    int month = cal.get(Calendar.MONTH);
			// If months between Jan and March subtract 1 from year
			/*if (month >= Calendar.JANUARY && month <= Calendar.MARCH)
			{
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
			}*/
		    int year = cal.get(Calendar.YEAR);
		    return year;
		 }
		public static HashMap<Integer, String> getMonth()
		{
			int year = Utils.getYears(Utils.getDateFromString("dd-MM-yyyy", Utils.getCurrentDate()));
			HashMap<Integer, String> hashKeyValues = new HashMap<Integer, String>();
			hashKeyValues.put(1, "JAN - " +  year);
			hashKeyValues.put(2, "FEB - " +  year)  ;
			hashKeyValues.put(3, "MAR - " +  year);
			hashKeyValues.put(4, "APL - " +  year);
			hashKeyValues.put(5, "MAY - " +  year);
			hashKeyValues.put(6, "JUN - " +  year);
			hashKeyValues.put(7, "JUL - " +  year);
			hashKeyValues.put(8, "AUG - " +  year);
			hashKeyValues.put(9, "SEP - " +  year);
			hashKeyValues.put(10, "OCT - " +  year);
			hashKeyValues.put(11, "NOV - " +  year);
			hashKeyValues.put(12, "DEC - " +  year);
			return hashKeyValues;
		}
		//break down details
		public static HashMap<String, Integer> getRSCode()
		{
			HashMap<String, Integer> hashKeyValues = new HashMap<String, Integer>();
			hashKeyValues.put("0600", 4);
			hashKeyValues.put("1701", 2);
			hashKeyValues.put("1702", 2);
			hashKeyValues.put("1711", 2);
			hashKeyValues.put("1730", 2);
			hashKeyValues.put("1734", 2);
			hashKeyValues.put("1801", 4);
			hashKeyValues.put("1802", 4);
			hashKeyValues.put("1901", 4);
			hashKeyValues.put("1902", 4);
			hashKeyValues.put("1903", 4);
			hashKeyValues.put("1905", 4);
			hashKeyValues.put("2001", 1);
			hashKeyValues.put("2002", 2);
			hashKeyValues.put("2101", 5);
			hashKeyValues.put("2103", 5);
			hashKeyValues.put("2104", 6);
			hashKeyValues.put("2105", 5);
			hashKeyValues.put("2301", 3);
			hashKeyValues.put("2501", 4);
			return hashKeyValues;
		}
		
		public static String getDiffTime(Date date1, Date date2) {
	        long ms = 0;
	        long days = 0;
	        long hrs = 0;
	        long mins = 0;
	        long secs = 0;
	        String returnVal = null;
	        if (date2.getTime() - date1.getTime() > 0) {// if for example date1 =
	                                                    // 22:00, date2 = 01:55.
	            Calendar c = Calendar.getInstance();
	            c.setTime(date2);
	            c.add(Calendar.DATE, 1);
	            date2 = c.getTime();
	            if (date2.getDate() > date1.getDate()) {
	                ms = date2.getDate() - date1.getDate();
	                days = TimeUnit.DAYS.convert(ms, TimeUnit.DAYS);
	                days=days*1440;
	            }
	            if (date2.getHours() > date1.getHours()) {
	                ms = date2.getHours() - date1.getHours();
	                hrs = TimeUnit.HOURS.convert(ms, TimeUnit.HOURS);
	            }
	            if (date2.getMinutes() > date1.getMinutes()) {
	                ms = date2.getMinutes() - date1.getMinutes();
	                mins = TimeUnit.MINUTES.convert(ms, TimeUnit.MINUTES);
	            } else {
	                ms = 60 - date1.getMinutes();
	                mins = TimeUnit.MINUTES.convert(ms, TimeUnit.MINUTES);
	                hrs = hrs - 1;

	            }
	            hrs=(hrs*60)+ mins;
	            //returnVal = hrs + ":" + mins;
	            returnVal = hrs+"";
	        }
	        return returnVal;
	    }
	
		public static double roundTwoDecimals(double number){
			number = Math.round(number * 100);
			number = number/100;
			return number;
		}

		public static String getTwoDecimal(double d)
		{
			BigDecimal x = new BigDecimal(d);
			DecimalFormat df = new DecimalFormat("################.##");

			x.setScale(2, RoundingMode.HALF_DOWN);
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);

			return df.format(x);
		}
		public static double roundFourDecimals(double number){
			number = Math.round(number * 10000);
			number = number/10000;
			return number;
		}

		public static Date getFromString(String format, String stringDate) {
	        Date exeplandate1 = null;
	        try {
	            java.util.Date simdat3 = new SimpleDateFormat(format).parse(stringDate);
	            exeplandate1 = new Date(simdat3.getTime());
	        }
	        catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return exeplandate1;
	    }
		public static String getDiffTime1(Date date1, Date date2) {	     
	        long hrs = 0,ms=0;	      
	        String returnVal = null;
	        if (date2.getTime() - date1.getTime() > 0) {  
	        	  	ms = 60 - date2.getMinutes();
	                long diff = date2.getTime() - date1.getTime();
	    		//	long diffSeconds = diff / 1000 % 60;
	    			long diffMinutes = diff / (60 * 1000) % 60;
	    			long diffHours = diff / (60 * 60 * 1000) % 24;
	    		//	long diffDays = diff / (24 * 60 * 60 * 1000);  			

	     	      hrs=(diffHours*60)+ diffMinutes;	           
	            returnVal = hrs+"";
	        }
	        return returnVal;
	    }

		}
