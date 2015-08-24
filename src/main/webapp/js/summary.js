/**
 * Created by Adrian on 2/12/2015.
 */
function loadSummaryDialog(userid){
    $.ajax({
        url: 'getTablesForUser.action',
        data: {userId: userid},
        success: function(result){
            result = JSON.parse(result);
            $( "#selectTableDialog" ).dialog();
            for (var i = 0; i < result.length; i++) {
                $('#selectedTable').append("<option value='" +  result[i] + "'>" + result[i] + "</option>")
            }
            $( "#tableSelected").button()
                .click(
                    function (event){
                        var tableName = $("#selectedTable option:selected").val();
                        console.log(tableName);
                        displayTableSummary(tableName);
            }   );
        },
        error: function(result){
            console.log('Some error occured!!');
        }
    });
}

function displayTableSummary(tableName) {
    $.ajax({
        url: 'summarize.action',
        data: {tableName: tableName},
        success: function (result) {
            result = JSON.parse(result);
            console.log(result);
            var mydata = result.values;
            var mymodel = result.headers;
            var colnames = result.colnames;
            console.log(mydata);
            console.log(mymodel);
            $("#report").jqGrid({
                    //total: mydata.total,
                    //page: mydata.page,
                    //records: mydata.records,
                    data: mydata,
                    datatype: "local",
                    colNames: colnames,
                    colModel: mymodel,
                    pager: '#pager_report',
                    'cellEdit': true,
                    'cellsubmit': 'clientArray',
                    editurl: 'clientArray',
                    gridView: true,
                    caption: 'Data summary for table ' + tableName,
                    height: 'auto',
                    loadComplete: function () {
                        $("tr.jqgrow:odd").css("background", "#AADDEE");
                    }
                }
            );
        }
    });
}