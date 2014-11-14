package com.letv.portal.proxy.impl;

public class test {

	public static void main(String[] args) {
		int i;
		for(i=0;i<10;i++){	
				System.out.println(i);
				if(i==5) {
					System.out.println("in");
					int j = 1/0;
				}
			
		}
	}
}
