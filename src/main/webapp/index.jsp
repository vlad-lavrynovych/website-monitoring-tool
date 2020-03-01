<%@ page import="com.demo.data.dto.ConfigCheckInfoDto" %>
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
<div class="d-flex justify-content-around flex-column">
    <br/>
    <br/>
    <br/>
    <table class="table table-striped table-dark">
        <thead>
        <tr>
            <th>Host</th>
            <th>Status</th>
            <th>Last check</th>
            <th>Duration (millis)</th>
            <th>Response code</th>
            <th>Response size (bytes)</th>
            <th>Details</th>
            <th>Monitoring status</th>
        </tr>
        </thead>
        <%
            List<ConfigCheckInfoDto> list = (List<ConfigCheckInfoDto>) request.getSession().getAttribute("data");
            if (list != null && !list.isEmpty()) {
        %>
        <tbody>
        <%for (ConfigCheckInfoDto e : list) { %>
        <tr>
            <th><%out.println(e.getUrl());%></th>
            <th class="<%
            switch (e.getStatus()){
                case "CRITICAL":
                    out.print("bg-danger");
                    break;
                case "WARNING":
                    out.print("bg-warning");
                    break;
                default:
                    out.print("bg-success");
            }
            %>"><% out.println(e.getStatus()); %></th>
            <th><% out.println(e.getLastCheck());%></th>
            <th><% out.println(e.getDuration());%></th>
            <th><%out.print(e.getResponseCode());%></th>
            <th><%out.print(e.getResponseSize());%></th>
            <th><%out.println(e.getDetails());%></th>
            <th><%out.println(e.isMonitored() ? "Monitored" : "Not Monitored");%></th>
            <th>
                <form action="api/<%out.print(e.getId());%>" method="post">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </th>
            <th>
                <form action="api/<%out.print("changeMonitoringStatus/"); out.print(e.getId());%>"
                      method="post">
                    <button type="submit" class="btn btn-warning"><%
                        out.println(e.isMonitored() ? "Disable" : "Enable");%></button>
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
            <label class="w-50">querying interval (millis)</label>
            <input class="form-control w-50" type="number" value="10000" name="queryingInterval"/>
            <label class="w-50">response time ok (millis)</label>
            <input class="form-control w-50" type="number" value="20000" min="0:00:00"
                   name="responseTimeOk"/>
            <label class="w-50">response time warning (millis)</label>
            <input class="form-control w-50" type="number" value="5000" min="0:00:00"
                   name="responseTimeWarning"/>
            <label class="w-50">response time critical (millis)</label>
            <input class="form-control w-50" type="number" value="10000" min="0:00:00"
                   name="responseTimeCritical"/>
            <label class="w-50">expected http code</label>
            <input class="form-control w-50" value="200" min="100" max="526" type="number" name="expectedHttpCode"/>
            <label class="w-50">min expected response size (bytes)</label>
            <input class="form-control w-50" required type="number" name="minExpectedResponseSize"/>
            <label class="w-50">max expected response size (bytes)</label>
            <input class="form-control w-50" required type="number" name="maxExpectedResponseSize"/>
            <br/>
            <button class="button btn-success btn-lg" type="submit">Add web-site</button>
        </div>
    </form>
</div>
</body>
</html>
