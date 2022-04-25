<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="right-side">
	<h2 class="table-title">Add Faculty</h2>
	<form class="form" action="add-faculty">
	<p>
		<input type="text" name="fname" placeholder="First Name" required />
	</p>
	<p>
		<input type="text" name="lname" placeholder="Last Name" required />
	</p>
	<p>
		<select name="gender">
			<option value="Male">Male</option>
			<option value="Female">Female</option>
			<option value="Others">Others</option>
		</select>
	</p>
	<p>
		<input type="number" name="age" placeholder="Age" required />
	</p>
	<p>
		<input type="text" name="department" placeholder="Department" required />
	</p>
	<p>
		<input type="email" name="email" placeholder="Email Id" required />
	</p>	
	<input type="submit" value="Add" />
	</form>

</div>

</body>
</html>