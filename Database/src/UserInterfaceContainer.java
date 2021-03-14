
public class UserInterfaceContainer implements Runnable{
	
	Gui gui;
	SqlHandler sqlh;
	private boolean login = false;
	private int mode = -1;
	UserInterfaceContainer(){
		gui = new Gui(this);
		sqlh = new SqlHandler(this);
		this.run();
	}
	
	public void attemptLogin() {
		login = sqlh.attemptConnection();
	}

	@Override
	public void run(){
		//open the login gui and attempt to login
		gui.openLoginGUI();		
		while(!login) {
			try {				
				Thread.sleep(10);			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		gui.closeCurrentGUI();
		gui.openAdminCustomerGUI();
		
		//wait for the user to select a "mode" (admin, librarian or customer)
		while(mode == -1) {
			try {				
				Thread.sleep(10);			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		gui.closeCurrentGUI();

		//open a gui
		if(mode == 1) {
			gui.openAdminGUI();
		}
		if(mode == 2) {
			gui.openLibrarianGUI();
		}
		if(mode == 3) {
			gui.openCustomerGUI();
		}
		mode = -1;
	}

	public void setMode(int i) {
		this.mode = i;
		
	}
}
