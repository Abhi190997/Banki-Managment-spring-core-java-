package com.abiBanking.bankMangment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankingMain {
	
	 ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	 BankingUtility bank = (BankingUtility) context.getBean("banking");
	 
     public static void main(String[] args) {
	   BankingMain menu = new BankingMain();
	  
	   try {
		  menu.menu();
	   } catch (ClassNotFoundException e) {
		  e.printStackTrace();
	   } catch (IOException e) {
		  e.printStackTrace();
	   } catch (SQLException e) {
		  e.printStackTrace();
	   }
}
   
   public void menu() throws IOException, ClassNotFoundException, SQLException {
	   
	   Scanner s = new Scanner(System.in);
	 while(true) {  
         System.out.println("             ******************************");
	     System.out.println("             |     Select Any operation   |");
	     System.out.println("             ******************************");
	     System.out.println("--------------------------------------------------------"); 
	     System.out.println("|                                                      |");
	     System.out.println("|    Press 1: To add new account                       |");
	     System.out.println("|    Press 2: To display all account record            |");
	     System.out.println("|    Press 3: Generate Mini Statement for any account  |");
	     System.out.println("|    Press 4: For Deposite Cash                        |");
	     System.out.println("|    Press 5: To Widraw Cash                           |");
	     System.out.println("|    Press 6: Delete Any Account Record Permanently    |");
	     System.out.println("|                                                      |");
	     System.out.println("--------------------------------------------------------");
	     System.out.println();
	   
	   
	     int select = s.nextInt();
	     switch(select){
	   
	      case 1:
		    bank.setConncetion();
		    bank.insert();
		    System.out.println();
		    break;  
		  
	     case 2:
		    bank.setConncetion();
		    bank.showAll();
		    System.out.println();
		    break;
	
	     case 3:
		    bank.setConncetion();
		    bank.search();
		    System.out.println();
		    break;
		    
	     case 4:
		    bank.setConncetion();
		    bank.deposite();
		    System.out.println();
		    break;
		    
	     case 5:
		    bank.setConncetion();
		    bank.widraw();
		    System.out.println();
		    break;
		    
	     case 6:
		    bank.setConncetion();
		    bank.delete();
		    System.out.println();
		    break;	   
	   }
	}
	   
   }
}
