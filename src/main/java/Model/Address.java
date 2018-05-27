package Model;

public class Address {
	private Long addressId;
	private String city;
	private String street;
	private int house;
	private int flat;
	
	public Address() {
	}
	
	public Address(long addressId) {
		super();
		this.addressId = addressId;
	}

	public Address(Long addressId, String city, String street, int house, int flat) {
		super();
		this.addressId = addressId;
		this.city = city;
		this.street = street;
		this.house = house;
		this.flat = flat;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getHouse() {
		return house;
	}
	public void setHouse(int house) {
		this.house = house;
	}
	public int getFlat() {
		return flat;
	}
	public void setFlat(int flat) {
		this.flat = flat;
	}
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", city=" + city + ", street=" + street + ", house=" + house
				+ ", flat=" + flat + "]";
	}
}
