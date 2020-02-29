<%@ page import="com.demo.data.domain.ConfigEntity" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>App</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/css/bootstrap.css" TYPE="text/css">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="navbar navbar-dark bg-dark d-flex justify-content-center align-items-center">
    <h1 class="text-white">Website Monitoring Tool</h1>
</div>
<div class="container d-flex justify-content-around flex-column">
    <br/>
    <br/>
    <br/>
    <table class="table table-dark">
        <thead>
        <tr>
            <th>Host</th>
            <th>Service</th>
            <th>Status</th>
            <th>Last check</th>
            <th>Duration</th>
            <th>Details</th>
        </tr>
        </thead>
        <%
            List<ConfigEntity> list = (List<ConfigEntity>) request.getSession().getAttribute("configs");
            if (list != null && !list.isEmpty()) {
        %>
        <tbody>
        <%for (ConfigEntity e : list) { %>
        <tr>
            <th><%out.println(e.getUrl());%></th>
            <th><%
                out.println(e.getExpectedHttpResponseCode());
            %></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th>
                <form action="api/<%out.print(e.getId());%>" method="post">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </th>
        </tr>
        </tbody>
        <%
                }
            }
        %>
    </table>
    <br/>
    <br/>
    <br/>

    <form method="post" action="api/create">
        <div class="form-group d-flex flex-md-column justify-content-center align-items-center">
            <label class="w-50">url</label>
            <input class="form-control w-50" required placeholder="http://example.com/" type="text" name="url"/>
            <label class="w-50">querying interval (hh:mm:ss)</label>
            <input class="form-control w-50" type="time" value="00:00:10" name="queryingInterval"/>
            <label class="w-50">response time ok (hh:mm:ss)</label>
            <input class="form-control w-50" type="time" value="00:00:02" min="0:00:00" max="100:59:59"
                   name="responseTimeOk"/>
            <label class="w-50">response time warning (hh:mm:ss)</label>
            <input class="form-control w-50" type="time" value="00:00:05" min="0:00:00" max="100:59:59"
                   name="responseTimeWarning"/>
            <label class="w-50">response time critical (hh:mm:ss)</label>
            <input class="form-control w-50" type="time" value="00:00:10" min="0:00:00" max="100:59:59"
                   name="responseTimeCritical"/>
            <label class="w-50">expected http code</label>
            <input class="form-control w-50" value="200" min="100" max="526" type="number" name="expectedHttpCode"/>
            <label class="w-50">min expected response size</label>
            <input class="form-control w-50" required type="number" name="minExpectedResponseSize"/>
            <label class="w-50">max expected response size</label>
            <input class="form-control w-50" required type="number" name="maxExpectedResponseSize"/>
            <br/>
            <button class="button btn-success btn-lg" type="submit">Add web-site</button>
        </div>
    </form>
</div>
</body>
</html>
