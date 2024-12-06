<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/forgotpassword" method="post">
  <div class="container">
    <h1>Quên mật khẩu</h1>
    <p>Please fill in this form to change password.</p>
    <hr>
    <c:if test="${error !=null}">
					<h3 class="alert alertdanger">${error}</h3>
				</c:if>
    
    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="uname" id="uname" required>
    
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" id="email" required>
    
    <label for="psw"><b> Đổi mật khẩu</b></label>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" required>

    
    <hr>

    <button type="submit" class="registerbtn">Xác nhận</button>
  </div>

 
</form>
</body>
</html>