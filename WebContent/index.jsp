<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="resources/bootstrap/bootstrap.min.css">
<script type="text/javascript"
	src="resources/bootstrap/jquery.min.js"></script>

<script type="text/javascript"
	src="resources/bootstrap/bootstrap.min.js"></script>
<title>Json Reader</title>
<style type="text/css">

.fileError{
	color:red;
}
</style>
<script>
	function checkFile() {

		var file = document.getElementById("file");

		if ('files' in file) {
			if (file.files.length == 0 || file == null) {
				//alert("Please Upload the file");
				$('.fileError').html("Please Upload fhe file");
				return false;
			}
		}
		return true;
	}
</script>
</head>
<body>

	<c:if test="${error != null || not empty error }">
		<div class="fileError">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>

	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2">
				<div class="page-header">
					<h3 id="headline">Json Upload/Read</h3>
				</div>

				<form method="POST" action="./" enctype="multipart/form-data"
					class="form-horizontal">
					<div class="form-group">
						<div class="rowContainer">
							<label class="col-sm-2 control-label">Upload</label>
							<div class="col-sm-4">
								<input type="file" class="form-control" name="file" id="file" />
								<div class="fileError"></div>

							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="rowContainer">

							<div class="col-sm-5 col-sm-offset-2">
								<input type="submit" class="btn btn-primary" value="Upload"
									onClick="return checkFile()">
								Press here to upload the file!
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<c:if test="${object != null || not empty object }">
		<hr>
	
	JSon file is:<br>
	

		<c:out value="${object}"></c:out>
		
		
	</c:if>

</body>
</html>