<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인페이지</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div id="login-container-wrapper">
		<div id="login-container">
			<h2>로그인</h2>
			<!--  form에 받은 데이터를 POST(암호화) 넘긴다 -->
			<form action ="${pagecontext.request.contextPath }/login" method="post">
				<div class="input-group">
					<label for="username">아이디</label> <input type="text" id="username"
						name="username" required="아이디를 입력하세요" />
				</div>

				<div class="input-group">
					<label for="password">비밀번호</label> <input type="password" id="password"
						name="password" required="비밀번호를 입력하세요" />
				</div>
				<button type="submit" id="login-button">로그인</button>
			</form>

			<div id="register-link">
			<!-- 최상위 경로 이동 -->
			<a href="${pageContext.request.contextPath}/register">회원가입</a>
			</div>


		</div>
	</div>

	<%@ include file="/WEB-INF/views/common/footer.jsp"%>


</body>
</html>