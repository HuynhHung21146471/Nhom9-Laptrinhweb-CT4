<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
	
<h2>${userModel.isForgot ? 'Quên mật khẩu' : 'Tạo tài khoản'}</h2>

<div class="container">
	<br /> <br /> <br /> <span>Hệ thống đã gửi mã kích hoạt đến
		Email của bạn.</span> <span>Xin vui lòng kiểm tra Email để lấy mã kích
		hoạt .</span> <br />
	<div>
		<form action="verifycode" method="post" class="log-reg-block sky-form">
		<input type="hidden" value="${userModel.isForgot}" name="isForgot">
			<div class="input-group">
				<input type="text" name="authcode"
					class="form-control margin-top-20">
			</div>
			<input type="submit" value="Kích hoạt"
				class="btn-u btn-u-sea-shop margin-top-20">		
		</form>
	</div>
	<br /> <br /> <br />
</div>