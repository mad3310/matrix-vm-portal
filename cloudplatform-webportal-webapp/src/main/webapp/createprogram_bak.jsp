<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="en">
<head>
<meta charset="utf-8">
<title>PAAS云</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">


<link href="css/bootstrap.css" rel="stylesheet">


<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</head>

<body>
				

					<form class="form-horizontal">
						<div class="control-group-l">
							<label class="control-label" for="inputEmail">上线项目名称</label>
							<div class="controls">
								<input id="inputEmail" type="text" >
							</div>
						</div>
					</form>
					
					
					<form class="form-horizontal">
  <div class="control-group">
    <label class="control-label" for="inputEmail">Email</label>
    <div class="controls">
      <input type="text" id="inputEmail" placeholder="Email">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="inputPassword">Password</label>
    <div class="controls">
      <input type="password" id="inputPassword" placeholder="Password">
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <label class="checkbox">
        111111111111111<input type="checkbox"> Remember me
      </label>
      <button type="submit" class="btn">Sign in</button>
    </div>
  </div>
</form>
</body>
<script type="text/javascript">
	$("#signin>#loginbutton").click(function() {
		$("#signin").hide();
		$("#usercenter").show();
	});

	$("#headeregisterbutton").click(function() {
		window.location.href = './register.jsp';

	});

	$(document).ready(function() {
		$("#emailmessage").bootstrapSwitch();

		$("#signin").show();
		$("#sqlcluster").addClass("active");

		$("#userdata tr").click(function() {
			alert("hello");
		});
	});
</script>

</html>
