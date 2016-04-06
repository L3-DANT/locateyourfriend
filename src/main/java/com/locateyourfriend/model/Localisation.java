package com.locateyourfriend.model;

public class Localisation {
	private Float longitude;
	private Float latitude;
	
	public Localisation(){
		super();
	}
	
	public Localisation(String stringLocationToParse){
		super();
		String longitudeLatitude[] =  stringLocationToParse.replace(",", " ").split(" ");
		latitude = Float.parseFloat(longitudeLatitude[0]);
		longitude = Float.parseFloat(longitudeLatitude[1]);
	}
	
	public String toString(){
		return latitude.toString() + "," + longitude.toString();
	}
	
}
