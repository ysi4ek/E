<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<h2>USERS</h2>

	<c:forEach items="${ users }" var="user">
		</br>
		User name: <c:out value="${ user.userName }"></c:out>
		User age: <c:out value="${ user.age }"></c:out></br>
		<c:if test = "${user.address != null}">
		
		<a> User address: </a>
		</br> City: <c:out value="${ user.address.city }"></c:out>
		</br> Street: <c:out value="${ user.address.street }"></c:out>
		</br> House number: <c:out value="${ user.address.house }"></c:out>
		</br> Flat number: <c:out value="${ user.address.flat }"></c:out>
		
		</c:if>

		<form action="/userservice/users?command=delete" method="POST">
			<input type="hidden" name="userId" value="${ user.userId }">
			<button type="submit">DELETE</button>
		</form>
		
		<form action="/userservice/users?command=update" method="POST">
			<input type="hidden" name="userId" value="${ user.userId }">
			<button type="submit">UPDATE</button>
		</form>
	</c:forEach>
	</br>
	
	
	<form action="/userservice/users?command=save" method="POST">
		User name: <input type="text" name="userName">
		User age: <input type="text" name="userAge">
		User address (city): <input type="text" name="city">
		User address (street): <input type="text" name="street">
		User address (house): <input type="text" name="house">
		User address (flat): <input type="text" name="flat">
		<button type="submit">SAVE</button>
	</form>

</body>
</html>