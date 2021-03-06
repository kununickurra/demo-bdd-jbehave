<#ftl strip_whitespace=true>
<#macro renderStat stats name myclass=""><#assign value = stats.get(name)!0><span class="${myclass}">${value}</span></#macro>
<#macro renderMillis stats name myclass=""><#assign millis = stats.get(name)!0><span class="${myclass}"><#assign time = timeFormatter.formatMillis(millis)>${time}</span></#macro>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>JBehave Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<style type="text/css" media="all">
@import url( "./style/jbehave-core.css" );
</style>
<style type="text/css" media="all">
.reports table td {
	text-align: center;
	padding: 10px;
}
.reports table td.story {
	text-align: left;
	padding: 10px;
}
.reports table th {
	border-bottom: 1px solid #000;
}

#banner {
	background: #fff url(../images/background-banner.png) repeat top left;
}

#banner img {
	padding: 4px 0 4px 20px;
}

#footer {
	width: 100%;
	background: #fff url(../images/background-footer.png) repeat;
	color: #fff;
	border: 1px solid;
	padding: 5px 0 5px 0;
	margin-top: 50px;
	font-size: 0.8em;
	position: fixed;
	bottom: 0;
}

#footer div.left {
	float: left;
}

#header {
	margin: 0 auto 0 auto;
	margin-left: 30px;
	margin-bottom: 40px;
}

body {
	margin: 0;
	font-family: "Lucida Grande", "Arial", "Helvetica", "Verdana",
		sans-serif;
	font-size: 0.84em;
	color: #555;
	text-align: left;
	padding: 0 0 10px 0;
	background-color: #fff;
}

h2 {
	background-color: #ffcd13;
	margin-bottom: 10px;
	padding: 5px 10px;
}

body,td,select,input,li {
	font-size: 13px;
}

a {
	text-decoration: none;
}

a:link {
	color: #39912b;
	font-weight: bold;
}

a:visited {
	color: #39912b;
}

a:active,a:hover {
	color: #39912b;
}

b {
	color: #333;
}

.logo {
	float: right;
	margin-right: 30px;
}

.clear {
	clear: both;
}

.left {
	text-align: left;
	margin-left: 10px;
}

.right {
	text-align: right;
	margin-right: 10px;
}

.reports {
	margin-left: 30px;
	margin-top: 30px;
	text-align: left;
}

.reports h2 {
    opacity: 0.85;
}

table {
	border-collapse: collapse;
}

.stories {
    border: 1px solid #000;
}

.scenarios {
	border: 1px solid #000;
}

.steps {
	border: 1px solid #000;
}

.totals {
    border-top: 1px solid #000;
    padding-top: 10px;
    font-weight: bold;
}

.reports table {
	border: solid;
}

.reports table th {
	border-bottom: 1px solid #000;
	text-align: center;
	font-weight: bold;
	padding: 10px;
}

.reports table td {
	text-align: center;
	padding: 5px;
}

.reports table td.story {
	text-align: left;
	padding: 5px;
}

.reports table td a {
    color: #555;
}

.lane {
    border-left: 1px solid #000;
}

.maps {
	margin-left: 30px;
	margin-top: 30px;
	text-align: left;
}

.maps h2 {
    opacity: 0.85;
}

.maps table {
	border: solid;
}

.maps table th {
	text-align: center;
	font-weight: bold;
	padding: 10px;
}

.maps table td {
	text-align: center;
	padding: 5px;
}

.maps table td.name {
	text-align: left;
	padding: 5px;
}

.views {
    margin-left: 30px;
    margin-top: 30px;
    text-align: left;
}

.views h2 {
    opacity: 0.85;
}

.views table {
    border: solid;
}

.views table th {
    text-align: center;
    font-weight: bold;
    padding: 10px;
}

.views table td {
    text-align: center;
    padding: 5px;
}

.views table td.name {
    text-align: left;
    padding: 5px;
}

.story {
	text-align: left;
	margin-left: 10px;
}

.story h2 {
	background-color: #fff;
	border-color: #ffcd13;
}

.meta {
	text-align: left;
	color: purple;
	margin-bottom: 10px;
}

.filter {
	text-align: left;
	color: red;
	margin-left: 10px;
	margin-bottom: 10px;
}

.keyword {
	margin-left: 10px;
}

.property {
	margin-left: 10px;
}

.narrative {
	text-align: left;
	color: blue;
}

.element {
	padding-left: 10px;
}

span.inOrderTo,span.asA,span.iWantTo {
	font-weight: bold;
}

.givenStories, .path {
	font-weight: bolder;
	font-size: 16px;
    opacity: 0.85;
	margin-bottom: 10px;
	padding: 5px 10px;
}

.scenario {
	text-align: left;
	padding-left: 1px;
}

.step {
	color: black;
	padding-left: 10px;
}

.successful {
	color: green;
}

.ignorable {
	color: blue;
}

.pending {
	color: olive;
}

.notPerformed {
	color: brown;
}

.failed {
	color: red;
}

.dryRun {
	border: solid;
	color: amber;
	background-color: yellow;
	margin-left: 12px;
	padding: 10px;
	font-size: 20px;
	text-align: center;
	text-weight: bold;
}

.parameter {
	color: purple;
	padding-left: 0px;
	text-weight: bold;
}

.keyword {
	text-weight: bold;
}

.examples {
	margin-left: 10px;
    padding-bottom: 20px;
}

.examples h3 {
	opacity: 0.85;
}

.examples table {
	border: solid;
	margin-top: 12px;
	margin-left: 12px;
}

.examples table th, td {
	text-align: center;
	padding-left: 5px;
    padding-right: 5px;
    border-left: 1px solid;
}

.examples table th {
    font-weight: bold;
}

.outcomes table {
	border: solid;
	margin-top: 12px;
	margin-left: 12px;
}

.outcomes table th {
	text-align: center;
	font-weight: bold;
}

.outcomes table tr.notVerified {
	color: red;
}

.outcomes table tr.verified {
	color: green;
}

.outcomes table td {
	text-align: center;
}

.outcomes {
	padding-bottom: 20px;
}

pre.failure {
	padding-left: 10px;
	color: red;
}

</style>


</head>

<body>

<div id="banner"><img src="images/jbehave-logo.png" alt="jbehave" />
<div class="clear"></div>
</div>

<#assign reportNames = reportsTable.getReportNames()>
<#assign totalReports = reportNames.size() - 1>

<div class="reports">
<h2>Story Reports ${date?string("dd/MM/yyyy HH:mm")}</h2>
<h3>Summary</h3>

<table>
<colgroup span="2" class="stories"></colgroup>
<colgroup span="5" class="scenarios"></colgroup>
<colgroup span="6" class="steps"></colgroup>
<colgroup class="view"></colgroup>
<tr>
    <th colspan="2">Stories</th>
    <th colspan="5">Scenarios</th>
    <th colspan="6">Steps</th>
    <th></th>
    <th></th>
</tr>
<tr>
    <th width="200px">Name</th>
    <th width="60px">Excluded</th>
    <th width="40px">Total</th>
    <th width="70px">Successful</th>
    <th width="50px">Pending</th>
    <th width="40px">Failed</th>
    <th width="70px">Excluded</th>
    <th width="40px">Total</th>
    <th width="60px">Successful</th>
    <th width="50px">Pending</th>
    <th width="40px">Failed</th>
    <th width="110px">Not Performed</th>
    <th width="50px">Ignorable</th>
    <th width="60px">Duration (hh:mm:ss.SSS)</th>
    <th width="110px">View</th>
</tr>
<tr class="totals">
<td>${totalReports}</td>
<#assign stats = reportsTable.getReport("Totals").getStats()>
<td>
<@renderStat stats "notAllowed" "failed"/>
</td>
<td>
<@renderStat stats "scenarios"/>
</td>
<td>
<@renderStat stats "scenariosSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "scenariosPending" "pending"/>
</td>
<td>
<@renderStat stats "scenariosFailed" "failed"/>
</td>
<td>
<@renderStat stats "scenariosNotAllowed" "failed"/>
</td>
<td>
<@renderStat stats "steps" />
</td>
<td>
<@renderStat stats "stepsSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "stepsPending" "pending"/>
</td>
<td>
<@renderStat stats "stepsFailed" "failed"/>
</td>
<td>
<@renderStat stats "stepsNotPerformed" "notPerformed" />
</td>
<td>
<@renderStat stats "stepsIgnorable" "ignorable"/>
</td>
<td>
<@renderMillis stats "duration"/>
</td>
<td>
Totals
</td>
</tr>
</table>
<br />
</div>

<div class="reports">
<h3>Story Breakdown</h3>

<table>
<colgroup span="2" class="stories"></colgroup>
<colgroup span="5" class="scenarios"></colgroup>
<colgroup span="6" class="steps"></colgroup>
<colgroup class="view"></colgroup>
<tr>
    <th colspan="2">Stories</th>
    <th colspan="5">Scenarios</th>
    <th colspan="6">Steps</th>
    <th></th>
    <th></th>
</tr>
<tr>
    <th width="200px">Name</th>
    <th width="60px">Excluded</th>
    <th width="40px">Total</th>
    <th width="70px">Successful</th>
    <th width="50px">Pending</th>
    <th width="40px">Failed</th>
    <th width="70px">Excluded</th>
    <th width="40px">Total</th>
    <th width="60px">Successful</th>
    <th width="50px">Pending</th>
    <th width="40px">Failed</th>
    <th width="110px">Not Performed</th>
    <th width="50px">Ignorable</th>
    <th width="60px">Duration (hh:mm:ss.SSS)</th>
    <th width="110px">View</th>
</tr>
<#list reportNames as name>
<#assign report = reportsTable.getReport(name)>
<#if name != "Totals">
<tr>
<#assign stats = report.getStats()>
<#assign stepsFailed = stats.get("stepsFailed")!0>
<#assign scenariosFailed = stats.get("scenariosFailed")!0>
<#assign pending = stats.get("pending")!0>
<#assign storyClass = "story">
<#if stepsFailed != 0 || scenariosFailed != 0>
    <#assign storyClass = storyClass + " failed">
<#elseif pending != 0>
    <#assign storyClass = storyClass + " pending">
<#else>
    <#assign storyClass = storyClass + " successful">
</#if>
<td class="${storyClass}">${report.name}</td>
<td>
<@renderStat stats "notAllowed" "failed"/>
</td>
<td>
<@renderStat stats "scenarios"/>
</td>
<td>
<@renderStat stats "scenariosSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "scenariosPending" "pending"/>
</td>
<td>
<@renderStat stats "scenariosFailed" "failed"/>
</td>
<td>
<@renderStat stats "scenariosNotAllowed" "failed"/>
</td>
<td>
<@renderStat stats "steps" />
</td>
<td>
<@renderStat stats "stepsSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "stepsPending" "pending"/>
</td>
<td>
<@renderStat stats "stepsFailed" "failed"/>
</td>
<td>
<@renderStat stats "stepsNotPerformed" "notPerformed" />
</td>
<td>
<@renderStat stats "stepsIgnorable" "ignorable"/>
</td>
<td>
<@renderMillis stats "duration"/>
</td>
<td>
<#assign filesByFormat = report.filesByFormat>
<#list filesByFormat.keySet() as format><#assign myfile = filesByFormat.get(format)><a href="${myfile.name}">${format}</a><#if format_has_next> |</#if></#list>
</td>
</tr>
</#if>
</#list>
</table>

<br />
</div>


<div style="padding-left: 30px; font-size:10px"><br /><br />Powered by JBehave &#169; 2003-2011</div>

</body>

</html>