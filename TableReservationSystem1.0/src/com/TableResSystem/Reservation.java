package com.TableResSystem;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

//interface ReservationMethods {
//	public void createReservation();
//	public void viewReservations();
//}

class Reservation {
	
	public void showDetail(int ReservationId, double SubTotalAmount, double DiscountAmount, double TaxAmount, double TotalAmount) {
		
		System.out.println("Reservation Id: " + ReservationId);
		System.out.println("Sub Total Amount: " + SubTotalAmount + " Rupees");
		System.out.println("Discount Amount: " + DiscountAmount + " Rupees");
		System.out.println("Tax Amount: " + TaxAmount + " Rupees");
		System.out.println("Total Amount: " + TotalAmount + " Rupees");
		System.out.println("");
	}
	
	public int genrateId(LocalDateTime ReservationDate)
	{
		int minRange = 1;
		int maxRange = 9999;
		int range = maxRange - minRange + 1;
		
		int rand = (int)(Math.random() * range) + minRange;
		int resYear = ReservationDate.getYear();
		
		String r = Integer.toString(rand);
		String y = Integer.toString(resYear);
		String Id = y + r;
		int resId = Integer.parseInt(Id);
		return resId;
	}
	
	public double discount(double SubTotalAmount, LocalDateTime ReservationDate)
	{
		
		double amount;
		if(ReservationDate.getDayOfWeek().equals("WEDNESDAY")) {
			amount = (SubTotalAmount / 100) * 50;
		} else {
			amount = 0.00;
		}
		return amount;
	}
	
	public void createReservation(ArrayList reservationDetail)
	{
		Scanner input = new Scanner(System.in);
		
		// input
		System.out.println("Enter you details: ");
		String CustomerName = input.next();
		String ReservationDes = input.next();
		LocalDateTime ReservationDate = LocalDateTime.now();
		int	ReservationId = genrateId(ReservationDate);	
		int Adult = input.nextInt();
		int Children = input.nextInt();
		boolean Status  = true;	
		double AdultCharge = 500 * Adult;
		double ChildrenCharge = 300 * Children;
		double SubTotalAmount = AdultCharge + ChildrenCharge;
		double DiscountAmount = discount(SubTotalAmount, ReservationDate);
		double tax = ((SubTotalAmount - DiscountAmount) / 100) * 5;
		double TaxAmount = tax;
		double TotalAmount = (SubTotalAmount - DiscountAmount) + TaxAmount;
		
		reservationDetail.add(new ReservationMethods(ReservationId, CustomerName, ReservationDes, ReservationDate,
				Adult, Children, Status, SubTotalAmount,
				DiscountAmount, TaxAmount, TotalAmount));
		
		System.out.println("--- Reservation Created Successfully ---");
		System.out.println();
		showDetail(ReservationId, SubTotalAmount, DiscountAmount, TaxAmount, TotalAmount);
		System.out.println();
	}
	
	public void viewReservations(ArrayList reservationDetail)
	{
		for (int i = 0; i < reservationDetail.size(); i++) {
            System.out.println(reservationDetail.get(i) + "   ");
        }
	}
	
	public void viewByReservationId(String id)
	{
		try {
			Scanner fileAsInput = new Scanner(new File("C:/Users/Sathisha Narayana/Desktop/OrderManagement.txt"));
			
			ArrayList<String> l1 = new ArrayList<String>();
            ArrayList<String> data = new ArrayList<String>();
            
            
           data.add("ReservationId "); data.add("ReservationDesc ");data.add("ReservationDate ");data.add("NoOfAdult ");data.add("NoOfChildren ");
           data.add("SubTotal ");data.add("Discount ");data.add("Tax ");data.add("Total ");
            System.out.println(data);
           while (fileAsInput.hasNext()) {
            	l1.add(fileAsInput.next());
            }
            if (!(l1.contains(id))) {

                System.out.println("Please enter valid Order Id");
                return;
            }
            System.out.println();
            int index = data.indexOf(id);
            int cnt=0;
            
            System.out.println("----------------------------------");
            System.out.println("Reservation Deatils:");
            System.out.println("----------------------------------");
            
            for (int i = index; i < index + 10; i++) {
                //System.out.print(list1.get(i) + " ");

                System.out.println(data.get(cnt)+": "+l1.get(i));
                cnt++;
            }
            return;
		} catch(Exception e) {
			System.out.println("Reservation Id is not available.");
		}
	}
	
	// Main method
		public static void main(String[] args) throws Exception {
		
			Reservation obj1 = new Reservation();
		
		Scanner input = new Scanner(System.in);
		ArrayList<Reservation> reservationDetail = new ArrayList<Reservation>();
		FileWriter fw = new FileWriter("C:/Users/Jenish Kanani/Desktop/ReservationFile.txt", true);
		
		while(true) {
			System.out.println();
			System.out.println("****** Table Reservation System ******");
			System.out.println("1. Add Reservation.");
			System.out.println("2. View Reservation List.");
			System.out.println("3. View By Reservation Id.");
			System.out.println("4. Sort Reservation.");
			System.out.println("5. Delete/Cancel Reservation by Id.");
			System.out.println("6. Gernrate Report.");
			System.out.println("7. Exit.");
			
			System.out.println("Choose Option: ");
			int ch = input.nextInt();
			
			switch(ch) {
			case 1:
				
				obj1.createReservation(reservationDetail);
				try {
	                for (int i = 0; i < reservationDetail.size(); i++) {
	                    fw.write(reservationDetail.get(i) + "   ");
	                }
	                fw.write("\n");
	                fw.close();
	            } catch (Exception e) {
	                System.out.println(e);
	            }
				
				System.out.println("Do you want to enter more Reservation details(Y/N)");
				String ans = input.next();
				
				if(ans.charAt(0) == 'Y')
					obj1.createReservation(reservationDetail);
				
				break;
			case 2:
				obj1.viewReservations(reservationDetail);
				break;
			case 3:
				System.out.println("Enter Reservation Id : ");
				String id = input.next();
				obj1.viewByReservationId(id);
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				System.exit(1);
				break;
			}
		}
	}
}
