<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 1/2/2015
  Time: 5:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">

    <title>e-StA</title>
    <meta name="description" content="e-StA - statistical analysis">
    <meta name="author" content="Adrian Iasinschi">

    <!-- css part -->
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/menu.css">
    <link rel="stylesheet" href="css/tabs.css">

    <!-- css for the jqGrid-->
    <link rel="stylesheet" href="css/jquery-ui.css" type="text/css"/>
    <link rel="stylesheet" href="css/ui.jqgrid.css" type="text/css"/>

    <!-- fonts part -->
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Aladin' rel='stylesheet' type='text/css'>
    <!-- js part -->
    <!-- jQuery general - should this be everywhere? -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <!-- jqGrid stuff - js -->
    <script src="js/jquery.jqGrid.min.js"></script>
    <script src="js/grid.locale-en.js"></script>
    <!-- local routines -->
    <script src="js/summary.js"></script>

    <script type="text/javascript">
        $(
                function() {
                    $(".ui-tabs").tabs();
                }
        );
    </script>
</head>

<body>
<header>
    <div class="esta-logo">
        <img src="img/esta.png" height=100/>
    </div>
    <div class="esta-header-text">
        <h1>e-StA - statistical app</h1>

        <h3>easy reporting and analysis</h3>
    </div>
    <div class="esta-user-info">
        Logged in as: </br> <a href="/profile/${userid}">${fullname}</a> </br>
        <form action="logout" method="post">
            <input type=submit value="Logout" name="logout">
        </form>
    </div>
</header>

<div id="menu">

    <ul id="nav">
        <li><a href="#">Home</a>
        <li><a href="#">File</a>
            <ul>
                <li><a href="#">Import</a>
                    <ul>
                        <li><a href="upload-file.action">From Excel</a>
                        <li><a href="#">From text(csv)</a>
                    </ul>
                </li>
                <li><a href="#">Export</a></li>
            </ul>
        </li>

        <li><a href="#">Analysis</a>
            <ul>
                <li><a href="#">Descriptive</a>
                    <ul>
                        <li><a href="#" onclick="loadSummaryDialog(${userid});">Summary</a></li>
                    </ul>
                </li>
                <li><a href="#">Tests</a></li>
                <li><a href="#">Regression</a></li>
            </ul>
        </li>

        <li><a href="#">Graphs</a>
            <ul>
                <li><a href="#">Bar charts</a></li>
                <li><a href="#">Histogram</a></li>
                <li><a href="#">Scatter plots</a></li>
            </ul>
        </li>
        <li><a href="#">Help</a> </li>
    </ul>

    <div id="selectTableDialog" hidden="hidden" title="Choose a table">
        Select a table name from the list:
        <select id="selectedTable">

        </select>
        <div id="tableSelected">Select</div>
    </div>
</div>