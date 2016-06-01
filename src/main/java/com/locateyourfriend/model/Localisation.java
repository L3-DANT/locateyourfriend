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
	 * Permet d'instancier une localisation à partir d'un string ayant la structure adaptée
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
	
	public static Localisation fromJSONtoLoc(String message){
		Localisation loc = new Localisation();
		String[] tabMessages = message.replace("{", "").replace("}", "").split(",");
		for(String mess : tabMessages){
			if(mess.contains("longitude")){
				loc.setLongitude(Float.parseFloat(mess.split(":")[1]));
			}
			if(mess.contains("latitude")){
				loc.setLatitude(Float.parseFloat(mess.split(":")[1]));
			}
		}
		return loc;
	}

	private void setLongitude(Float longitude) {
		this.longitude = longitude;
		
	}
	private void setLatitude(Float latitude) {
		this.latitude = latitude;
		
	}

}
