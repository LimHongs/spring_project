<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�α���������</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div id="login-container-wrapper">
		<div id="login-container">
			<h2>�α���</h2>
			<!--  form�� ���� �����͸� POST(��ȣȭ) �ѱ�� -->
			<form action ="${pagecontext.request.contextPath }/login" method="post">
				<div class="input-group">
					<label for="username">���̵�</label> <input type="text" id="username"
						name="username" required="���̵� �Է��ϼ���" />
				</div>

				<div class="input-group">
					<label for="password">��й�ȣ</label> <input type="password" id="password"
						name="password" required="��й�ȣ�� �Է��ϼ���" />
				</div>
				<button type="submit" id="login-button">�α���</button>
			</form>

			<div id="register-link">
			<!-- �ֻ��� ��� �̵� -->
			<a href="${pageContext.request.contextPath}/register">ȸ������</a>
			</div>


		</div>
	</div>

	<%@ include file="/WEB-INF/views/common/footer.jsp"%>


</body>
</html>