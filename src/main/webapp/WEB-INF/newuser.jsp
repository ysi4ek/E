<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<h2>LIBRARY</h2>

		<c:out value="${ user.userName }"></c:out>

		<form action="/userservice/users?command=delete" method="POST">
			<input type="hidden" name="userId" value="${ user.userId }">
			<button type="submit">DELETE</button>
		</form>
		
		<form action="/userservice/users?command=update" method="POST">
			<input type="hidden" name="userId" value="${ user.userId }">
			<button type="submit">UPDATE</button>
		</form>

	</br>
	<form action="/userservice/users?command=save" method="POST">
		User name: <input type="text">
		<button type="submit">SAVE</button>
	</form>

</body>
</html>