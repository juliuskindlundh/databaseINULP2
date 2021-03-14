import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlHandler {
	UserInterfaceContainer uic;
	Connection con;

	SqlHandler(UserInterfaceContainer uic){
		this.uic = uic;
	}
	
	//Method to make sure that sql injections dont happen since we ban among other things "'
	//and the user input part of the query is always within a set of " or ' the use can not make a malicious query
	public boolean controlInput(String str) {
		String acceptedFormat = "[A-Za-z0-9 -]*";
		Pattern p = Pattern.compile(acceptedFormat);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	//attempts to connect to the sql server with a password and username
	public boolean attemptConnection() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/bibliotek",uic.gui.username.getText(),String.valueOf(uic.gui.password.getPassword()));
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	//This method returns an array list of booktitles that are "sql-LIKE" what the user is searching for
	public ArrayList<String> searchBook(String str){	
		ArrayList<String> arrList = new ArrayList<String>();
		if(!controlInput(str)) {
			return arrList;
		}
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT titel FROM böcker WHERE titel LIKE '"+"%"+str+"%"+"';");
			while(rs.next()) {
				arrList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;   	
	}
	
	//This method returns an array list of every "tidsskrift" together with its "utgivningsdatum"
	public ArrayList<String> searchTidsskrift(String str){		
		ArrayList<String> arrList = new ArrayList<String>();
		if(!controlInput(str)) {
			return arrList;
		}
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT tidsskrifter.id,titelts,utgivningsdatum "+
					"FROM titlar,tidsskrifter WHERE "+
					"tidsskrifter.titel = titlar.id AND "+
					"titelts LIKE '"+"%"+str+"%"+"';");

			while(rs.next()) {
				arrList.add(rs.getString(2)+ " "+ rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;   	
	}
	
	//This method returns a string containing all information connected to a book or magazine
	public String getDataFromItem(String str) {
		String retstr = "";
		if(!controlInput(str)) {
			return retstr;
		}
		try {
			//We use a regex to determine if what we are searching for is a magazine or a book (a magazine will have a date together with the title)
			String dateFormat = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern p = Pattern.compile(dateFormat);
			Matcher m = p.matcher(str);
			if(m.find()) {
				// if the selected item is a magazine
				str = str.substring(0,m.start()-1);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT DISTINCT tidsskrifter.id,titelts,utgivningsdatum,lagerplatser.lagerplats "+
						"FROM titlar,tidsskrifter,lagerplatser WHERE "+
						"tidsskrifter.titel = titlar.id AND "+
						"tidsskrifter.lagerplats = lagerplatser.id AND "+
						"titelts = '"+str+"';");
				while(rs.next()) {
					retstr = "Titel: "+rs.getString(2)+"\nUtgivnings datum: "+rs.getString(3)+"\nLagerplats: "+rs.getString(4);
				}
				uic.gui.borrowbtn.setEnabled(false);
			}
			else {
				//the selected item is a book
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT titel,antal_sidor,klassifikationer.klassifikation,författare.namn FROM böcker,klassifikationer,författare WHERE titel = '"+str+
						"' AND böcker.klassifikation = klassifikationer.id AND böcker.författare = författare.id"+";");
				while(rs.next()) {
					retstr = "Titel: "+ rs.getString(1)+"\nAntal Sidor: "+rs.getString(2)+"\nKlassifikation: "+rs.getString(3)+"\nFörfattare: "+rs.getString(4);
				}
				uic.gui.borrowbtn.setEnabled(true);
			}
		} catch (SQLException e) {
		}
		return retstr;

	}
	
	public boolean borrowBook(String target) {
		return false;
		// TODO Auto-generated method stub

	}
	
	//this method searches for and returns all employees with a name that is "sql-LIKE" what the user is searching for
	public ArrayList<String> searchEmploye(String str) {
		ArrayList<String> arrList = new ArrayList<String>();
		if(!controlInput(str)) {
			return arrList;
		}
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT namn FROM anställda WHERE namn LIKE '"+"%"+str+"%"+"';");
			while(rs.next()) {
				arrList.add(rs.getString(1));
				System.out.println(arrList.get(arrList.size()-1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;  
	}
	
	//This method returns a string with all the data associated with a specific employee
	public String getDataFromEmploye(String target) {
		String str = "";
		if(!controlInput(target)) {
			return str;
		}
		System.out.println(target);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT namn,adress,månadslön,semesterdagar_kvar,id "+
					"FROM anställda WHERE "+
					"namn = '"+target+"';");
			int id = -1;
			while(rs.next()) {
				str = "Namn: "+rs.getString(1)+"\nAdress: "+rs.getString(2)+"\nMånadslön: "+rs.getString(3)+"\nSemesterdagar kvar: "+rs.getString(4);
				id = rs.getInt(5);
			}

			rs = stmt.executeQuery("SELECT telefonnummer FROM telefonnummer WHERE anställnings_id = "+id+";");
			while(rs.next()) {
				str = str + "\nTelefonnummer: " + rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;   	
	}
	
	//this method returns all the data for a certain employee as a arraylist
	public ArrayList<String> getDataFromEmployeAsArrList(String target) {
		ArrayList<String> arrList = new ArrayList<String>();
		if(!controlInput(target)) {
			return arrList;
		}
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT namn,adress,månadslön,semesterdagar_kvar,id "+
					"FROM anställda WHERE "+
					"namn = '"+target+"';");
			int id = -1;
			while(rs.next()) {
				arrList.add(rs.getString(1));
				arrList.add(rs.getString(2));
				arrList.add(rs.getString(3));
				arrList.add(rs.getString(4));
				arrList.add(rs.getString(5));
				id = rs.getInt(5);
			}

			rs = stmt.executeQuery("SELECT telefonnummer FROM telefonnummer WHERE anställnings_id = "+id+";");
			while(rs.next()) {
				arrList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;   
	}
	
	//This method modifies the data related to a certain employee
	public void modifyEmploye(ArrayList<String> employeData) {
		for(String a:employeData) {
			if(!controlInput(a)) {
				return;
			}
		}
		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE anställda SET namn = '"+employeData.get(0)+"' WHERE id = "+Integer.parseInt(employeData.get(4))+";");
			stmt.execute("UPDATE anställda SET adress = '"+employeData.get(1)+"' WHERE id = "+Integer.parseInt(employeData.get(4))+";");
			stmt.execute("UPDATE anställda SET månadslön = '"+employeData.get(2)+"' WHERE id = "+Integer.parseInt(employeData.get(4))+";");
			stmt.execute("UPDATE anställda SET semesterdagar_kvar = '"+employeData.get(3)+"' WHERE id = "+Integer.parseInt(employeData.get(4))+";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}