
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import javafx.scene.text.Text;

public class Gui extends JFrame {
	UserInterfaceContainer uic;
	TextField username = new TextField();
	JPasswordField password = new JPasswordField();
	Button login = new  Button("LOGIN");
	JFrame frame;
	List searchList;
	Button borrowbtn = new Button("Borrow book");
	String target;
	public Gui(UserInterfaceContainer uic) {
		this.uic = uic;
	}
	
	//Create a gui for logging in
	public void openLoginGUI() {	
		login.addActionListener(a->{
			uic.attemptLogin();
		});
		frame = new JFrame("Login");
		frame.setSize(300,200);
		frame.setLayout(new GridLayout(3,2));
		frame.add(new Label("Username "));
		frame.add(username);
		frame.add(new Label("Password"));
		frame.add(password);
		frame.add(login);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	//Create a gui with three buttons to go to either a customer, librarian or admin interface
	public void openAdminCustomerGUI() {
		frame = new JFrame("Select mode");
		frame.setSize(300,200);
		frame.setLayout(new GridLayout(1,2));
		Button customer = new Button("Customer");
		frame.add(customer);
		Button librarian = new Button("Librarian");
		frame.add(librarian);
		Button admin = new Button("Admin");
		frame.add(admin);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		customer.addActionListener(a->{
			uic.setMode(3);
		});
		
		librarian.addActionListener(a->{
			uic.setMode(2);
		});
		
		admin.addActionListener(a->{
			uic.setMode(1);
		});
	}
	
	//Method to cluse the current gui
	public void closeCurrentGUI() {
		frame.removeAll();
		frame.setVisible(false);
		frame = null;
	}
	
	//create a gui for the admin mode
	public void openAdminGUI() {
		frame = new JFrame("Admin");
		frame.setSize(500,500);
		frame.setLayout(new GridLayout(3,2));
		frame.add(new Label("Search"));
		TextField tf = new TextField();
		frame.add(tf);
		searchList = new List();
		searchList.setSize(new Dimension(300,250));
		frame.add(searchList);	
		TextArea employeDescription = new TextArea();
		employeDescription.setEditable(false);
		employeDescription.setSize(new Dimension(300,250));
		frame.add(employeDescription);
		Button modify = new Button("Modify");
		frame.add(modify);
		Button add = new Button("add");
		frame.add(add);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		tf.addTextListener(l->{
			searchList.removeAll();
			ArrayList<String> temp1 = uic.sqlh.searchEmploye(tf.getText());
			for(String a:temp1) {
				searchList.add(a);
			}
		});
			
		searchList.addActionListener(a->{
			target = a.getActionCommand();	
			employeDescription.setText(uic.sqlh.getDataFromEmploye(target));
			employeDescription.setVisible(true);
		});
		
		modify.addActionListener(a->{
			createPopupForm(target);
		});
		add.addActionListener(a->{
			createPopupForm(target);
		});	
	}
	
	
	private void createPopupForm(String target) {
		ArrayList<String> employeData = uic.sqlh.getDataFromEmployeAsArrList(target);
		ArrayList<TextField> tele = new ArrayList<TextField>();
		JFrame popup = new JFrame();
		popup.setSize(300, 600);
		popup.setLayout(new GridLayout(20,2));
		popup.add(new Label("Namn"));
		TextField name = new TextField(employeData.get(0));
		popup.add(name);
		popup.add(new Label("Adress"));
		TextField adress = new TextField(employeData.get(1));
		popup.add(adress);
		popup.add(new Label("Månadslön"));
		TextField manadslon = new TextField(employeData.get(2));
		popup.add(manadslon);
		popup.add(new Label("Semesterdagar kvar"));
		TextField sk = new TextField(employeData.get(3));
		popup.add(sk);
		for(int i = 5;i<employeData.size();i++) {
			popup.add(new Label("Telefonnummer"));
			TextField temp = new TextField(employeData.get(i));
			tele.add(temp);
			popup.add(temp);
		}
		Button save = new Button("Submit");
		Button discard = new Button("Cancel");
		popup.add(save);
		popup.add(discard);
		popup.setVisible(true);
		
		save.addActionListener(a->{
			employeData.set(0, name.getText());
			employeData.set(1, adress.getText());
			employeData.set(2, manadslon.getText());
			employeData.set(3, sk.getText());
			for(int i = 5;i<employeData.size();i++) {
				employeData.set(i, tele.get(i-5).getText());
			}
			uic.sqlh.modifyEmploye(employeData);
		});
		discard.addActionListener(a->{
			popup.setVisible(false);
			popup.dispose();
		});
		
	}

	public void openLibrarianGUI() {
		// TODO Auto-generated method stub
		
	}

	public void openCustomerGUI() {
		frame = new JFrame("Customer");
		frame.setSize(500,500);
		frame.setLayout(new GridLayout(3,2));
		frame.add(new Label("Search"));
		TextField tf = new TextField();
		frame.add(tf);
		searchList = new List();
		searchList.setSize(new Dimension(300,250));
		frame.add(searchList);	
		TextArea itemDescription = new TextArea();
		itemDescription.setEditable(false);
		itemDescription.setSize(new Dimension(300,250));
		frame.add(itemDescription);
		frame.add(borrowbtn);
		borrowbtn.setEnabled(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		tf.addTextListener(l->{
			searchList.removeAll();
			ArrayList<String> temp1 = uic.sqlh.searchBook(tf.getText());
			ArrayList<String> temp2 = uic.sqlh.searchTidsskrift(tf.getText());
			for(String a:temp1) {
				searchList.add(a);
			}
			for(String a:temp2) {
				searchList.add(a);

			}
		});
			
		searchList.addActionListener(a->{
			target = a.getActionCommand();	
			itemDescription.setText(uic.sqlh.getDataFromItem(target));
			itemDescription.setVisible(true);
		});
		
		borrowbtn.addActionListener(a->{
			createPopup(uic.sqlh.borrowBook(target));
		});		
	}

	private void createPopup(boolean borrowBook) {
		// TODO Auto-generated method stub
		System.out.println("attempting to borrow book: "+target);
	}
	
	
}