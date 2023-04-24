<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Post</title>
</head>
<body>

<a href="posts-servlet">See Published Posts</a>
<br>
Add Post

<form action="posts-servlet" method="POST">
    Nickname: <input name="nickname" type="text">
    <br>

    First Name: <input name="first_name" type="text">
    <br>

    Last Name: <input name="last_name" type="text">
    <br>

    Post: <input name="post" type="text" style="height: 100px">
    <br>

    <input type="submit">
</form>

</body>
</html>