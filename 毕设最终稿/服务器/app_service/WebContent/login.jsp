<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="api/login" method="post">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="userName"></td>
			</tr>
			<tr>
				<td>密  码：</td>
				<td><input type="text" name="password"></td>
			</tr>
			<tr>
				
				<td><input type="submit" value="登录"></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>