function deleteUnit(unitID) {
    ajax(
        {
            method: 'POST',
            url: 'admin/unitManageAction_deleteUnit.action',
            params: "unitID=" + unitID,
            callback: function (data) {
                if (data == 1) {
                    showInfo("删除成功");
                } else {
                    showInfo("删除失败");
                }
            }
        }
    );
}

$('#modal_info').on('hide.bs.modal', function () {
    location.reload();
});

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}
