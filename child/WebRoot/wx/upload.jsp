<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>Insert title here</title>

	<script src="static/js/jquery-1.7.2.js"></script>
</head>
<body>
	<button style="width: 100%; height: 500px" onclick="as();">上传</button>
</body>
<script type="text/javascript">
	function as() {
		$.ajax({
			type : "POST",
			url : 'http://aidi.tunnel.qydev.com/farmlot/login_login',
			data : {
				KEYDATA : "1",
				tm : new Date().getTime()
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				alert(data.a);
			}
		});
	}
</script>
</html>