<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="includes/header_logged.jsp"%>

    <div style="width: 600px; height: 100px;">
    </div>

      <div class="ui-tabs">
          <ul class="ui-tabs-nav">
              <li><a href="#tabs-1">Data view</a></li>
              <li><a href="#tabs-2">Reports view</a></li>
              <li><a href="#tabs-3">Help</a></li>
          </ul>
          <div id="tabs-1" class="ui-tabs-panel">
              <section class="esta-content">
                  <div style="display: block;"><p>User relevant data:</p></div>

                  <div class="esta-data" style="z-index: 1;">
                      <!-- jqGrid data stuff -->
                      <div style="position:relative">
                          <div style="width:600px;">
                              <table id="grid"></table>
                              <div id="pager"></div>
                          </div>
                      </div>
                      <!-- end jqGrid stuff -->
                  </div>
              </section>
          </div>
          <div id="tabs-2" class="ui-tabs-panel">
              <section class="esta-content">
                  <div class="esta-data">
                      <!-- jqGrid report stuff -->
                      <div style="position:relative">
                          <div style="width:600px;">
                              <table id="report"></table>
                              <div id="pager_report"></div>
                          </div>
                      </div>
                      <!-- end jqGrid stuff -->
                  </div>
              </section>
          </div>
          <div id="tabs-3" class="ui-tabs-panel">
              <p>Help shit.</p>
          </div>
      </div>



<script type="text/javascript">

    var mydata;
    var mymodel;

    $.ajax({
        url:"/estat/fetchData.action",
        type: "POST",
        data: {userId: ${userid} },
        success:function(response) {
            var jresponse = JSON.parse(response);
            mydata = jresponse.datastr;
            mymodel = jresponse.colModel;
            console.log("Table data fetched succesfully");
            console.log(mydata.rows);
            console.log(mydata.records);
            console.log(mymodel);
            loadGrid();
        },
        error: function(){
            console.log("Could not fetch table data");
            loadMockUp();
            loadGrid();
        }
    });

    function loadMockUp() {
        mydata = [{
            name: "Toronto",
            country: "Canada",
            continent: "North America",
            population: 4000000
        }, {
            name: "New York City",
            country: "USA",
            continent: "North America",
            population: 9000000
        }, {
            name: "Silicon Valley",
            country: "USA",
            continent: "North America",
            population: 100000
        }, {
            name: "Paris",
            country: "France",
            continent: "Europe",
            population: 7000000
        }, {
            name: "Craiova",
            country: "Romania",
            continent: "Europe",
            population: 290000
        }];

        mymodel = [{
            name: 'name',
            index: 'name',
            editable: true
        }, {
            name: 'country',
            index: 'country',
            editable: true
        }, {
            name: 'continent',
            index: 'continent',
            editable: true
        }, {
            name: 'population',
            index: 'population',
            editable: true
        }];
    }


    function loadGrid(){

    $("#grid").jqGrid({
                total: mydata.total,
                page: mydata.page,
                records: mydata.records,
                data: mydata.rows,
                datatype: "local",
                colNames: mydata.colNames,
                colModel: mymodel,
                pager: '#pager',
                'cellEdit': true,
                'cellsubmit': 'clientArray',
                editurl: 'clientArray',
                gridView: true,
                caption: 'Data for user ${fullname}',
                height: 'auto',
                loadComplete: function () {
                    $("tr.jqgrow:odd").css("background", "#AADDEE");
                }
            }
        );
    }

</script>


<%@ include file="includes/footer.jsp" %>