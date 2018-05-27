package Impl;

import java.util.List;

import Model.Address;
import Model.User;
import Repository.AddressRepository;
import Repository.UserRepository;
import Service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	private AddressRepository addressRepository;

	public UserServiceImpl() {
		this.userRepository = new UserRepositoryImpl();
		this.addressRepository = new AddressRepositoryImpl();
	}

	public void save(User user) {
		Long addressId = addressRepository.add(user.getAddress());
		Address address = user.getAddress();
		address.setAddressId(addressId);
		user.setAddress(address);
		userRepository.add(user);
	}

	public User findById(long id) {
		User user = userRepository.getById(id);
		Address address = addressRepository.getById(user.getAddress().getAddressId());
		user.setAddress(address);
		return user;
	}

	public List<User> findAll() {
		List <User> usersList = userRepository.getAll();
		for(User u : usersList){
			Address address = addressRepository.getById(u.getAddress().getAddressId());
			u.setAddress(address);
		}
		return usersList;
	}

	public void update(User user) {
		userRepository.update(user);
	}

	public void remove(User user) {
		Address address = user.getAddress();
		if(address != null){
			addressRepository.delete(address);
		}
		userRepository.delete(user);
	}

	public List<User> findInAgeRange(int from, int to) {
		List <User> usersList = userRepository.getInAgeRange(from, to);
		for(User u : usersList){
			Address address = addressRepository.getById(u.getAddress().getAddressId());
			u.setAddress(address);
		}
		return usersList;
	}
	
	public void updateAddress (Address address) {
		addressRepository.update(address);
	}

}