package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Impl.UserServiceImpl;
import Model.Address;
import Model.User;
import Service.UserService;

public class ShowUsersServlet extends HttpServlet {

		private static final long serialVersionUID = 6925798856246159139L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Exception adding JDBC driver in servlet");
		}
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		performAction(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		performAction(req, resp);
	}

	private void performAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("command");

		if (command != null && command.equals("delete")) {
			removeUser(request, response);
		} else if (command != null && command.equals("update")) {
			updateUser(request, response);
		} else if (command != null && command.equals("save")) {
			saveUser(request, response);
		} else {
			showAllUsers(request, response);
		}
	}

	private void showAllUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getSession().getAttribute("sessionUser"));
		
		Cookie testCookie = new Cookie("testCookie", "CookietestValue");
		response.addCookie(testCookie);
		
		List<User> usersList = userService.findAll();

		request.setAttribute("users", usersList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/users.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

	private void saveUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		int userAge = Integer.parseInt(request.getParameter("userAge"));
		String city = request.getParameter("city");
		String street = request.getParameter("street");
		int house = Integer.parseInt(request.getParameter("house"));
		int flat = Integer.parseInt(request.getParameter("flat"));
		
		Address address = new Address();
		address.setCity(city);
		address.setStreet(street);
		address.setHouse(house);
		address.setFlat(flat);
		User user = new User(userName, userAge, address);
		userService.save(user);
		
		HttpSession session = request.getSession();
		session.setAttribute("sessionUser", user);

		response.setStatus(HttpServletResponse.SC_RESET_CONTENT);
        response.sendRedirect("/userservice/users");
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void removeUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("userId") != null && !request.getParameter("userId").isEmpty()) {
			Long userId = Long.parseLong(request.getParameter("userId"));
			userService.remove(userService.findById(userId));
		}
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		for(Cookie c : cookies){
			if(c.getName().equals("testCookie")){
				System.out.println(c.getValue());
			}
		}
		
		showAllUsers(request, response);
	}

}