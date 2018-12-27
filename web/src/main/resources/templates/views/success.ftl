<html>
<head>
    <title></title>
</head>
<body>
    <@shiro.hasRole name="ADMIN">
    ADMIN进来了===============
    </@shiro.hasRole>
<br>
    <@shiro.hasRole name="ROLE_SHCOOLADMIN">
    ROLE_SHCOOLADMIN进来了===============
    </@shiro.hasRole>
<br>
    <@shiro.hasPermission name="user:create">
    用户的创建权限===============
    </@shiro.hasPermission>
    <@shiro.hasPermission name="school:create">
    学校的创建权限===============
    </@shiro.hasPermission>
<br>
<a href="/logout">退出登录</a>

</body>
</html>
