<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Клиенты</title>
    <link rel="stylesheet" href="/css/style.css"/>
</head>

<body>
<#if errorMessage??>
    <div style="color:red;font-style:italic;">
        ${errorMessage}
    </div>
</#if>
<div>
    <form action="/create" method="post">
        <div>Введите имя клиента:</div>
        <input type="text" name="name"/>
        <br/><br/>
        <div>Введите адрес клиента:</div>
        <input type="text" name="street"/>
        <br/><br/>
        <div>Введите телефон(ы) (через запятую):</div>
        <input type="text" name="numbers"/>
        <br/><br/>
        <input type="submit" value="Создать"/>
        <input type="reset" value="Reset">
    </form>
</div>

<h4>Список клиентов</h4>
<table class="table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Address</th>
        <th>Phones</th>
    </tr>
    </thead>
    <tbody>
    <#if clients??>
    <#list clients as client>
        <tr>
            <td>${client.id}</td>
            <td>${client.name}</td>
            <td>${client.street}</td>
            <td>${client.numbers}</td>
        </tr>
    </#list>
    </#if>
    </tbody>
</table>
</body>
</html>
