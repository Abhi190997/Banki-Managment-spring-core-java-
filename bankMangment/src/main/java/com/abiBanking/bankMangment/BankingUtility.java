package com.abiBanking.bankMangment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankingUtility {
    
	int account_no;   //Customer Account_no
	String name;      //Name of account holder
	double oamount;      //Opening Amount
	double damount;      //Deposit Money
	double wamount;      //Widraw Money
	double balance;   //Total Balance 
	
	String q_insert = "insert into banking(account,name,oamount,balance)"+"values(?,?,?,?)";
	//Insert query If you want to add new user/account in database
	String q_showAll = "select * from banking";
	//Used for showing all account detail
	String q_search = "select * from banking where account = ?";
	//Used for showing a particular account detail
	String q_deposite = "update banking set damount = ?,balance = ? where account = ?" ;
	//Used In order to deposite money in any account
	String q_widraw = "update banking set wamount = ?,balance = ? where account = ?" ;
	//Used In order to widraw money in any account
	String q_delete = "delete from banking where account = ?";
	//Used for deleting any customer record
	
	String confirm,more;
	Connection con; 
	PreparedStatement ps;
	ResultSet rs;
	
	Dao dao; //Creating ref to Dao class;
	
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	InputStreamReader ins = new InputStreamReader(System.in);
	BufferedReader buf = new BufferedReader(ins);
	
	//setter methond called using spring bean
	public Connection setConncetion() {
			try {
				con = dao.getCon();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		
	}
	
	//This method used to add new customer/account in database
	public void insert() throws IOException {
		
		
			try {
				do {
				   ps = con.prepareStatement(q_search);//setting up insert query
				
				   System.out.println("Enter account no: ");
				   String y = buf.readLine();
				   while(y.length() != 5) {
					   System.out.println("Sorry! invalid account no: ");
					   System.out.println("Please! Enter valid account no: ");
					
					   y = buf.readLine();
				   }
				   account_no = Integer.parseInt(y);
							
				   ps.setInt(1,account_no);
				   rs = ps.executeQuery();
				
				   if(rs.next()) {
					 System.out.println("sorry ! Account no already exist");
					 return;
				   }
				   else {
				      ps = con.prepareStatement(q_insert);
				      System.out.println("Enter name of customer: ");
				      name = buf.readLine();
				      while(name.equalsIgnoreCase("")) {
					     System.out.println("Sorry! name field can't be empty: ");
					     System.out.println("Please! Enter valid name: ");
					     name = buf.readLine();
				      }
				
				      System.out.println("Enter opening amount: ");
				      oamount = Integer.parseInt(buf.readLine());
				      while(oamount<1000) {
					     System.out.println("Sorry! opening amount can't be less than 1000:");
					     System.out.println("Please! Enter valid amount:");
					     oamount = Integer.parseInt(buf.readLine());
				      }
				
				      balance = oamount;
				
				      //setting parameter for insert query in order to exexute it
				      ps.setInt(1,account_no);
				      ps.setString(2,name);
				      ps.setDouble(3,oamount);
				      ps.setDouble(4,balance);
				
				      System.out.println("Do you really wanted to save record(y/n): ");
				      String yes = buf.readLine();
				
				      if(yes.equalsIgnoreCase("y"))
				      {
				        int res = ps.executeUpdate(); //query executed
				
				        if(res<1) {
					      System.out.println("Sorry! service isn't available this time");
					      System.out.println("Maybe system isn't reposnding");
				        }
				        else {
					     System.out.println("Congrat's ! account created successfully");
				        }
				     }
				   
			       }
				   System.out.println("Do you want to create new account(y/n)");
				   more = buf.readLine();
				}while(more.equalsIgnoreCase("Y"));
			
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		
	}
	
	//Method that show all customer detail
	public void showAll(){
		try {
			ps = con.prepareStatement(q_showAll); //setting query to execute
			rs = ps.executeQuery(); //Query execute

			//Display all account detail
			if(rs.next()) {
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.println(" Acc.No\t\tName\t\toamount\t\tdamount\t\twamount\t\tbalance");
			
				rs.previous();
				while(rs.next()) {
				
				    System.out.println();
					System.out.print(" "+rs.getInt(1));
					System.out.print(" \t\t"+rs.getString(2));
					System.out.print(" \t\t"+rs.getDouble(3));
					System.out.print(" \t\t"+rs.getDouble(4));
					System.out.print(" \t\t"+rs.getDouble(5));
					System.out.print(" \t\t"+rs.getDouble(6));
					System.out.println();
				}
				System.out.println("-------------------------------------------------------------------------------------------");
			}
			else {
				System.out.println("No record inserted yet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Method used to display a paritcular account status or used
	//generate mini statement for any customer
	public void search() {
			try {
				ps = con.prepareStatement(q_search);//setting up search query here
				System.out.println(); 
				System.out.println("Enter account_no you want to search: ");
				String y = buf.readLine();
				if(y.equalsIgnoreCase("")) {
					System.out.println("Sorry invalid account no");
					System.out.println("Thanks for using our service");
					return;
				}
				else {
					account_no = Integer.parseInt(y);
					ps.setInt(1, account_no);//setting parameter for search query
					rs = ps.executeQuery();//Search Query Executed
					
					if(rs.next()) {
						//Displaying account detail
						System.out.println("              ***Mini Statement***  ");
						System.out.println();
						System.out.println("||------------------------------------------ ||");
						System.out.println("|| Account_no:   "+rs.getInt(1) +"                       ||");
						System.out.println("||                                           ||");
						System.out.println("|| Name of account holder:   "+rs.getString(2)+"            ||");
						System.out.println("||                                           ||");
						System.out.println("|| Last time Amount deposite:   "+rs.getDouble(4)+"         ||");
						System.out.println("||                                           ||");
						System.out.println("|| Last time Amount widrawable:   "+rs.getDouble(5)+"          ||");
						System.out.println("||                                           ||");
						System.out.println("|| Current Balance/Total Balance:   "+rs.getDouble(6)+"    ||");
						System.out.println("||------------------------------------------ ||");
					}
					else {
						System.out.println("Sorry no account found with linked to this account no: ");
						System.out.println("Please check your account no");
						System.out.println("***Thanks for using our service***");
					}
				}
					
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	
	//Method used In order to deposite money in any account
	public void deposite() {
		try {
			ps = con.prepareStatement(q_search); //First search for account in order to deposite money
			System.out.println("Enter account no in which you want to deposite money: ");
			String y = buf.readLine();
			account_no = Integer.parseInt(y);
			ps.setInt(1, account_no);
			rs = ps.executeQuery(); //Executing search query
				
			if(rs.next()) {
				//Run if paritcular account exist you are looking for
				balance = rs.getDouble(6);
				System.out.println(balance);
				
				ps = con.prepareStatement(q_deposite); //Exexute query in order to deposite money
				System.out.println("Enter amount you want to deposite: ");
				damount = Integer.parseInt(buf.readLine());
				if(damount<=20000) {
					
				 	balance += damount;
				 	//Setting up parameter for money deposite query
					ps.setDouble(1,damount);
					ps.setDouble(2,balance);
					ps.setInt(3,account_no);
						
						
					int res = ps.executeUpdate(); //Updating account stauts in which you deposite money
					if(res < 1) {
						System.out.println("Sorry! Money isn't deposited this time");
						System.out.println("Maybe system isn't responding");
					}
					else {
						System.out.println("*______________________________________*");
						System.out.println("  Congrat's money deposite successfully");
						System.out.println("    Go and check your account status");
						System.out.println("      Thanks for using our service");
					}
						
				}
			}else {
				System.out.println("Sorry! No such account exist");
			}
		}catch(NumberFormatException ex){
			System.out.println("Sorry! Invalid Input");
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method used In order to widraw  money from any account
	public void widraw() {
		try {
			ps = con.prepareStatement(q_search); //First search for account in order to widraw money
			System.out.println("Enter account no from which you want to widraw money: ");
			String y = buf.readLine();
			account_no = Integer.parseInt(y);
			ps.setInt(1, account_no); //Executing search query
			rs = ps.executeQuery();
				
			if(rs.next()) {
				balance = rs.getDouble(6);
				System.out.println(balance);
				ps = con.prepareStatement(q_widraw);//Now exexute query in order to widraw money
				System.out.println("Enter amount you want to widraw from your account: ");
				wamount = Integer.parseInt(buf.readLine());
				
				if(wamount<balance&& wamount<=10000) {
					balance -= wamount;
					//setting up parameter in order to execute query
					ps.setDouble(1,wamount);
					ps.setDouble(2,balance);
					ps.setInt(3,account_no);
						
						
					int res = ps.executeUpdate();//execute query in order to update status of account
					if(res < 1) {
						System.out.println("Sorry! service isn't available this time");
						System.out.println("Maybe system isn't responding");
					}
					else {
						System.out.println("*______________________________________*");
						System.out.println("  Congrat's money widraw successfully");
						System.out.println("    Go and check your account status");
						System.out.println("      Thanks for using our service");
					}
						
				}
				else {
					System.out.println("May be your account doesn't have this much money");
					System.out.println("or maybe you try to widraw more then 10000 once");
				}
			}else {
				System.out.println("Sorry! No such account exist");
			}
		}catch(NumberFormatException ex){
			System.out.println("Sorry! Invalid Input");
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method Used to remove detail of any customer account permanently from database
	public void delete() {
		try {
			//setting search query in order to find a account which you want to delete
			ps = con.prepareStatement(q_search);
			
			System.out.println("Please! Enter account no you want to delete");
			account_no = Integer.parseInt(buf.readLine());
			ps.setInt(1,account_no);//setting parameter for search query
			rs = ps.executeQuery();//executing search query
			
			if(rs.next()) {
				//Block execute if account found
				System.out.println("         ***Current_Status_of_Account***  ");
				System.out.println();
				System.out.println("||------------------------------------------ ||");
				System.out.println("|| Account_no:   "+rs.getInt(1) +"                       ||");
				System.out.println("||                                           ||");
				System.out.println("|| Name of account holder:   "+rs.getString(2)+"            ||");
				System.out.println("||                                           ||");
				System.out.println("|| Curent Balance/Total Balance:   "+rs.getDouble(6)+"    ||");
				System.out.println("||------------------------------------------ ||");
				System.out.println();
				System.out.println("Are you sure you want me to delete this account(y/n)");
				String yes = buf.readLine();
				
				if(yes.equalsIgnoreCase("y")) {
					//setting up delete query in order to remove customer account from database
					ps = con.prepareStatement(q_delete);
					ps.setInt(1,account_no);
					int res = ps.executeUpdate();//delete query is execute and status is updated
					if(res<1) {
						System.out.println("Sorry! your request isn't fullfill this time");
						System.out.println("**Come back later**");
                        
					}
					else {
						System.out.println("Account Deleted Successfully");
					}
	
				}else {
					System.out.println("Operation Aborted");
				}
				
			}else {
				System.out.println("Sorry no such account exist");
			}
			
		}catch(Exception ex){
			System.out.println("Sorry! Invalid Inpyt");
		}
	}

}
