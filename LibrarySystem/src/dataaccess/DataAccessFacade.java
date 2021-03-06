package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import business.Book;
import business.CheckoutRecordEntry;
import business.LibraryMember;


public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ ((System.getProperty("os.name").startsWith("Windows"))? "\\src\\dataaccess\\storage" : "/src/dataaccess/storage");


	public static final String DATE_PATTERN = "MM/dd/yyyy";


	//specialized lookup methods
	public LibraryMember searchMember(String memberId) {
		Set<String> ids = readMemberMap().keySet();
		if(ids.contains(memberId)){
			return readMemberMap().get(memberId);
		}else{
			return null;
		}
	}

	public LibraryMember searchMemberByBook(String isbn, int copyNum) {
		Collection<LibraryMember> members = readMemberMap().values();
		//List<LibraryMember> membersOfbook = new ArrayList<>();
		for(LibraryMember member: members){
			List<CheckoutRecordEntry> entries = member.getRecord().getEntries();
			for(CheckoutRecordEntry entry: entries){
				if(entry.getCopy().getBook().getIsbn().equals(isbn) &&
						entry.getCopy().getCopyNum() == copyNum){
					return member;
				}
			}
		}
		return null;
	}

	public Book searchBook(String isbn) {

		Set<String> isbns = readBooksMap().keySet();
		if(isbns.contains(isbn)){
			return readBooksMap().get(isbn);
		}else{
			return null;
		}
	}

	public Auth searchAuth(String username){
		HashMap<String,User> userMap = readUserMap();
		for(String userId : userMap.keySet()){
			if(userId.equalsIgnoreCase(username)){
				userMap.get(username).getAuthorization();
			}
		}
		return null;
	}

	public Auth login(String id, String pwd) {
		HashMap<String, User> userMap = readUserMap();
		if(!userMap.containsKey(id)) return null;
		User user = userMap.get(id);
		if(!pwd.equals(user.getPassword())) {
			return null;
		}
		return user.getAuthorization();
	}


	//saveNewMember
	@Override
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> memberMap = readMemberMap();
		String memberId = member.getMemberId();
		memberMap.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, memberMap);

	}

	@Override
	public void updateMember(LibraryMember member){
		saveNewMember(member);
	}

	@Override
	public void updateBook(Book book) {
		saveNewBook(book);
	}

	@Override
	//save new lendable item
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}


	//////read methods that return full maps


	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		return (HashMap<String, LibraryMember>)readFromStorage(StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}


	/////load methods - these place test data into the storage area
	///// - used just once at startup

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}



	final static class Pair<S,T> implements Serializable{

		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}

















}
