$(function () {

    //Bug信息表格
    $("#jqGrid").jqGrid({
        url: baseURL + " ",
        datatype: "json",
        colModel: [
            {label: '序号', name: 'rowNum', width: 30, sortable: false},
            {label: 'ID', name: 'bugID', index: 'id', key: true, hidden: true},
            {label: 'ErrorMessage', name: 'errorMessage', index: 'errorMessage', width: 80},
            {label: 'General Bug Type', name: 'type', index: 'type', width: 80},
            {label: 'Describe', name: 'describe', index: 'describe', width: 80},
            {
                label: '操作', name: 'agencyId', index: 'agency_id', width: 70,
                formatter: function (cellValue, options, rowObject) {
                    // if (rowObject.auditStatus == "审核中") {
                    //     return '<a class="btn btn-primary btn-xs" onclick="vm.check(\'' + cellValue + '\')"><i class="fa fa-check-square-o"></i>&nbsp;审核</a>'
                    // }
                    return '<a class="btn btn-primary btn-xs" onclick="vm.showPrompt(\'' + cellValue + '\')"><i class="fa fa-eye"></i>&nbsp;详情</a>'
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.agencyList",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "pageIndex",
            rows: "pageSize",
            order: "order",
        },
        ajaxGridOptions:
            {
                dataFilter:
                    function (data, dataType) {    // preprocess the data
                        let responseJson = JSON.parse(data);
                        if (responseJson.code !== 0) {
                            return data;
                        }
                        // 设置行号的起始位置
                        let startRowNum = (responseJson.data.currPage - 1)
                            * responseJson.data.pageSize;
                        let arr = responseJson.data.agencyList;
                        for (let i = 0; i < arr.length; i++) {
                            let ar = arr[i];
                            ar.rowNum = ++startRowNum;
                        }
                        return JSON.stringify(responseJson);
                    }
            },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
        }
    });

});