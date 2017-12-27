package com.lyae.common;

public class Test {

	int a ;
	String b;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time ;
		String res = "";
		String res2 = "";
		StringBuilder sb = new StringBuilder(20000);
		
		time = System.currentTimeMillis();
		for ( int i = 0; i < 100000 ; i++){
			res += "a";
		}
		System.out.println( System.currentTimeMillis() - time );
		
		
		time = System.currentTimeMillis();
		for ( int i = 0; i < 100000 ; i++){
			sb.append("a");
		}
		//res2 = sb.toString();
		System.out.println( System.currentTimeMillis() - time );
		
		res = "";
		time = System.currentTimeMillis();
		for ( int i = 0; i < 100000 ; i++){
			res += "a";
		}
		System.out.println( System.currentTimeMillis() - time );
		
		System.out.println(res.length());
		System.out.println(res2.length());
	}

}
