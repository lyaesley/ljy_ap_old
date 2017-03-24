package com.lyae.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class sortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list	= new ArrayList<String>();
		
		list.add("201702");
		list.add("201705");
		list.add("201701");
		list.add("201703");
		list.add("201704");
		list.add("20170501");
		
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
	}

}
