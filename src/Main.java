import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * Author: konglie [ at ] outlook [ dot ] com
 * Date: 6/24/2014
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
	private static GUI gui;
	public static final TheQueue queue = new TheQueue();

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){

		}

		gui = new GUI();
		gui.setVisible(true);
	}

	public static GUI getGui(){
		return gui;
	}



	public static void safeSleep(long l){
		try{
			Thread.sleep(l);
		} catch (Exception e){}
	}
}
