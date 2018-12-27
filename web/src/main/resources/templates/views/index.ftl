<html>
<head>
    <title></title>
</head>
<body>
    <@shiro.hasAnyRoles name="ROLE_ADMIN,ROLE_SHCOOLADMIN">
        您好,欢迎
    </@shiro.hasAnyRoles>
    <@shiro.hasRole name="ROLE_ADMIN">
    超级管理员,您可以<a href="/createSchool">创建校管理员用户</a>
    </@shiro.hasRole>
    <@shiro.hasRole name="ROLE_SCHOOLADMIN">
    校管理员,您可以<a href="/createStudent">创建学生用户</a>
    </@shiro.hasRole>
<br>
<a href="/logout">退出登录</a>

</body>
</html>
