package com.locateyourfriend.model;

public class Localisation {
	private Float longitude;
	private Float latitude;

	public Localisation(){
		super();
	}

	public Localisation(Float longitude, Float latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/**
	 * Permet d'instancier une localisation � partir d'un string ayant la structure adapt�e
	 * 
	 * @param tableaulongitudeLatitude
	 */
	public Localisation(String[] tableaulongitudeLatitude){
		longitude = Float.parseFloat(tableaulongitudeLatitude[0]);
		latitude = Float.parseFloat(tableaulongitudeLatitude[1]);
		
	}
	
	public Localisation(String longitudeReceiveJson, String latitudeReceiveJson){
		longitude = Float.parseFloat(longitudeReceiveJson);
		latitude = Float.parseFloat(latitudeReceiveJson);
	}

	public Localisation(String stringLocationToParse){
		super();
		System.out.println(stringLocationToParse);
		if(stringLocationToParse != null){
			String longitudeLatitude[] =  stringLocationToParse.replace(",", " ").split(" ");
			latitude = Float.parseFloat(longitudeLatitude[0]);
			longitude = Float.parseFloat(longitudeLatitude[1]);
		}
	}

	public String toString(){
		if(latitude != null && longitude != null){
			return latitude.toString() + "," + longitude.toString();
		}
		return "";
	}

}
