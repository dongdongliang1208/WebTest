<%--
  Created by IntelliJ IDEA.
  User: dongdl
  Date: 2018/4/11
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
$END$
<form action="/WebServlet" method="POST" enctype ="multipart/form-data">
    <input type="checkbox" name="week" value="选择1"/>选择1
    <input type="checkbox" name="week" value="选择2"/>选择2
    <input type="checkbox" name="week" value="选择3"/>选择3
    <hr/>
    <input type="file" name="file"/><br/>
    <input type="file" name="photo" onchange="showPhoto(this)"/><img id="photo"/>
    <input type="submit"/>

    <script type="text/javascript">
        //即时显示照片
        function showPhoto(obj){

        }
    </script>
</form>
</body>
</html>
