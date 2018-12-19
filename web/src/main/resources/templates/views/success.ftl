
<html>
<head>
    <title></title>
</head>
<body>
<shiro:hasRole name="admin">
    管理员进来了
</shiro:hasRole>

<shiro:hasPermission name="user:create">
    用户的创建权限
</shiro:hasPermission>
</body>
</html>
