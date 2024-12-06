<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
  <div class="container">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>
    <c:if test="${error !=null}">
					<h3 class="alert alertdanger">${error}</h3>
				</c:if>
    
    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="uname" id="uname" required>
    
    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" required>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" id="email" required>
    
    <label for="fullname"><b>Fullname</b></label>
    <input type="text" placeholder="Enter Fullname" name="fullname" id="fullname" required>
    
        <label for="phone"><b>Phone</b></label>
    <input type="text" placeholder="Enter Phone" name="phone" id="phone" required>

    <hr>

    <button type="submit" class="registerbtn">Register</button>
  </div>

  <div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
  </div>
</form>
</body>
</html>