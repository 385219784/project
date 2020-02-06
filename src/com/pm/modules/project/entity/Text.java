package com.pm.modules.project.entity;

public class Text {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "123123#13213";
		String[] ss = s.split("#");
		for(String d : ss){
			System.out.println(d);
		}
	}

}
