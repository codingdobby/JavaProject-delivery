/*
 * 2019.09.24
 * 이승연
 * 
 * */

package JaBae.admin.VO;

public class SalesVO {
	private String IdCustomer; //구매자 아이디
	private String IdSeller; // 판매자 아이디
	private String NameCustomer;// 구매자 이름
	private String StartDay; // 주문일
	private String EndDay; // 도착일
	private String fee; // 금액
	private String FreeTicket; // 정기권 사용 유무

	public String getIdSeller() {
		return IdSeller;
	}

	public void setIdSeller(String idSeller) {
		IdSeller = idSeller;
	}

	public String getNameCustomer() {
		return NameCustomer;
	}

	public void setNameCustomer(String nameCustomer) {
		NameCustomer = nameCustomer;
	}

	public String getStartDay() {
		return StartDay;
	}

	public void setStartDay(String startDay) {
		StartDay = startDay;
	}

	public String getEndDay() {
		return EndDay;
	}

	public void setEndDay(String endDay) {
		EndDay = endDay;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String string) {
		this.fee = string;
	}

	public String getFreeTicket() {
		return FreeTicket;
	}

	public void setFreeTicket(String freeTicket) {
		FreeTicket = freeTicket;
	}

}
