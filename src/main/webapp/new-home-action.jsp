<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 8/24/2015
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- jQuery Stuff -->
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>

    <!-- jqGrid Stuff -->
    <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
    <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

    <!-- jqPlot stuff -->
    <!--[if lt IE 9]><script language="javascript" type="text/javascript" src="js/jqplot/excanvas.js"></script><![endif]-->
    <script language="javascript" type="text/javascript" src="js/jqplot/jquery.jqplot.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/plugins/jqplot.barRenderer.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
    <link rel="stylesheet" type="text/css" href="js/jqplot/jquery.jqplot.css" />

    <!-- Custom application js -->
    <script language="javascript" type="text/javascript" src="js/estapp/estapp.js"></script>

    <!-- CSS stuff -->
    <link href="jquery-ui.css" rel="stylesheet">
    <link href="css/ui.jqgrid.css" rel="stylesheet">
    <link href="css/estapp.css" rel="stylesheet">
    <!-- Google fonts Stuff -->
    <link href='http://fonts.googleapis.com/css?family=Ubuntu:400,400italic' rel='stylesheet' type='text/css'>

</head>

<body>
<div class="header ui-widget-header">
    <h2 style="display: inline;"> <img src="esta.png" height="45px"/> &nbsp; e-StA Application: statistics in the cloud</h2>
</div>
<br/>
<div class="menu-holder">
    <ul id="menu">
        <li class="ui-state-disabled">ACTIONS</li>
        <li>File
            <ul>
                <li onclick="loadCustomDialog();">Open dialog</li>
            </ul>
        </li>
        <li>Edit</li>
        <li>-</li>
        <li>Graphs
            <ul>
                <li>Scatter plots</li>
                <li>Bar charts
                    <ul>
                        <li>Simple</li>
                        <li>Mutiple Comparison</li>
                        <li>Stacked</li>
                        <li>Clustered</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>Analysis
            <ul>
                <li class="ui-state-disabled">Regression</li>
                <li>Descriptive
                    <ul>
                        <li>Summary</li>
                        <li>Mean</li>
                        <li>Variance </li>
                    </ul>
                </li>
                <li>Correlations
                    <ul>
                        <li>Pearson</li>
                        <li>Chi<sup>2</sup></li>
                        <li>Other </li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>Help</li>
        <li>-</li>
        <li class="ui-state-disabled">Adrian Iasinschi</li>
        <li>Profile</li>
        <li>Logout</li>
    </ul>
</div>

<div id="tabs" class="tabs-content">
    <ul>
        <li><a href="#tabs-1">Data sheet</a></li>
        <li><a href="#tabs-2">Report sheet</a></li>
        <li id="tab-3-trigger"><a href="#tabs-3">Graph sheet</a></li>
    </ul>
    <div id="tabs-1">
        <div style="width: 95%; overflow-x: auto;">
            <table id="grid"></table>
            <div id="pager"></div>
            <br/>
        </div>
        <br/>
    </div>
    <div id="tabs-2" class="scroll-tab">
        <div style="width: 95%; overflow-x: auto;">
            <h2>Report 1</h2> <br/>
            <table id="report-1"></table>
            <br/>
        </div>
        <br/>
        <div>
            <p>Between reports text - is that really necessary?</p>
        </div>
        <br/>
        <div style="width: 95%; overflow-x: auto;">
            <h2>Report 2</h2> <br/>
            <table id="report-2"></table>
            <br/>
        </div>

    </div>
    <div id="tabs-3" class="scroll-tab">
        <div>
            <div><h2>Histogram example</h2></div>
            <div id="graph-1" style="height:400px;width:600px; border: 1px solid; "></div>
        </div>
        <br/>
        <div>
            <div><h2>Another Histogram example</h2></div>
            <div id="graph-2" style="height:400px;width:600px; border: 1px solid; "></div>
        </div>
    </div>
</div>
<br/>
<div class="footer ui-widget-header">
    <h3>Copyright Adrian Iasinschi (2015)</h3>
</div>
</body>
</html>