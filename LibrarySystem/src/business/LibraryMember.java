package business;

final public class LibraryMember extends Person {

	private static final long serialVersionUID = 12958121991926987L;

	private String memberId;
	private CheckoutRecord record;

	public LibraryMember(String id, String firstName, String lastName, String telephoneNum, Address address) {
		super(firstName, lastName, telephoneNum, address);
		this.memberId = id;
		record = new CheckoutRecord();
	}


	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public CheckoutRecord getRecord() {
		return record;
	}

	public void setRecord(CheckoutRecord record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "firstname:" + getFirstName() + ", lastname:"
				+ getLastName() + ", telephone:" + getTelephone() + ", address:" + getAddress() + "";
	}




}
