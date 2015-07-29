package business;

public class CopyStatus {
	public static final String NOT_AVAILABLE = "Not Available";
	public static final String AVAILABLE = "Available";
	public static final String OVER_DUE = "Overdue";
//	private static final String DUE_BACK = "";
	private String memberName;
	private String isbn;
	private String title;
	private String copyNum;
	private String dueBack;
	private String availability;

	public CopyStatus(String isbn) {
		this.isbn = isbn;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getIsbn() {
		return isbn;
	}

//	public void setIsbn(String isbn) {
//		this.isbn = isbn;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCopyNum() {
		return copyNum;
	}

	public void setCopyNum(String copyNum) {
		this.copyNum = copyNum;
	}

	public String getDueBack() {
		return dueBack;
	}

	public void setDueBack(String dueBack) {
		this.dueBack = dueBack;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "CopyStatus [memberName=" + memberName + ", isbn=" + isbn + ", title=" + title + ", copyNum=" + copyNum
				+ ", dueBack=" + dueBack + ", availability=" + availability + "]";
	}



}
