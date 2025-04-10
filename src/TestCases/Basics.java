package TestCases;

public class Basics {

	public static void main(String[] args) {
		String str= "Payment $100 Paid";
		System.out.println(str.indexOf("$"));
		System.out.println(str.substring(str.indexOf("$")));
		
		// reverse string
		String str1 = "madam";
		String temp="";
		for(int i=str1.length()-1;i>=0;i--) {
			System.out.println(str1.charAt(i));
			temp=temp+str1.charAt(i);
		}
		System.out.println(temp);
		if(str1==temp) {
			System.out.println("Pliandrome");
		}
	}
}
