/*
 * 2019.09.24
 * 이승연
 * 
 * */

package JaBae.admin.VO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class getDate {
	
	private Calendar cal;
	private int year;
	private int month;
	private int date;
	private String mm;
	private String dd;
	
	public String monthChange(int month) { //month의 형변환?  10이하일 경우 0을 추가해주는 메서드
		 if(month <10)
			 mm = "0" + Integer.toString(month) ;
		 else mm = Integer.toString(month);
		return mm;
	}
	
	public String dateChange(int date) { //date의 형변환
		if(date < 10)
			dd = "0" + Integer.toString(date) ;
		 else dd = Integer.toString(date);
		return dd;
	}
	
	public String getToday(){ //오늘
		 cal = Calendar.getInstance();
		 year = cal.get(cal.YEAR);
		 if(month == 12)  month = cal.get ( cal.MONTH ) + 1 ;
		 month = cal.get ( cal.MONTH ) + 1 ;
		 date = cal.get ( cal.DATE ) ;
		 
		 return year + "-" + monthChange(month)+ "-" + dateChange(date);
	}
	
	public String getYesterday(){ //어제
		 cal = new GregorianCalendar(Locale.KOREA);
		 cal.add(Calendar.DATE, -1);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 
		 String d = sdf.format(cal.getTime()); 
		 return d;
	}
	
	public String get1MonthAgo(){ //1개월 전
		 cal = Calendar.getInstance();
		 year = cal.get(cal.YEAR);
		 month = cal.get ( cal.MONTH );
		 //월 별 나누기 
		 if(month == 0) month = 12;
		 date = cal.get ( cal.DATE );
		 
		 return year + "-" + monthChange(month)+ "-" +dateChange(date);
	}
	
	public String get6MonthAgo(){ //6개월 전
		 cal = Calendar.getInstance();
		 year = cal.get(cal.YEAR);
		 month = cal.get ( cal.MONTH ) -5;
		 if(month == 0) month = 12;
		 else if(month < 0) month = 12 + month;
		 date = cal.get ( cal.DATE );
		 
		 return year + "-" + monthChange(month)+ "-" +dateChange(date);
	}
	
	public String getLastYear(){ //1년전
		 cal = Calendar.getInstance();
		 year = cal.get(cal.YEAR) -1;
		 month = cal.get ( cal.MONTH ) +1;
		 date = cal.get ( cal.DATE );
		 
		 return year + "-" + monthChange(month)+ "-" +dateChange(date);
	}
	
}
