<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<script language="javascript">
	function login(){
		document.form.action="login.scaction";
		document.form.submit();
	}
	function register(){
		document.form.action="register.scaction";
		document.form.submit();
	}
</script>
<body>
<center>
	<h1>登录界面</h1>
	<hr>
	<form action="" method="post" name="form">
		用户名：<input type="text" name="username"><br/>
		密&nbsp;码：<input type="password" name="password"><br/>
		<button type="button" onclick="login()">登录</button>
		<button type="button" onclick="register()">注册</button>
	</form>
	<hr>
</center>
</body>
</html>