sap.ui.define([
    "dt/cpms/controller/dialog/BaseDialog"
], function (BaseDialog) {
    "use strict";

    var AddMemberDialog = BaseDialog.extend("dt.cpms.controller.dialog.AddMember", {
        _sDialogName: "AddMember"
    });

    AddMemberDialog.prototype.open = function() {
        BaseDialog.prototype.open.call(this);
        var oPageModel = this.getParentController().getView().getModel("page");
        var iProjectId = oPageModel.getProperty("/Project/id");
        var iAdmin = oPageModel.getProperty("/Admin");
        this._setDialogProperty("/", {
            Member: {
                user: null,
                role: null,
                project: Number(iProjectId)
            },
            Users: [],
            Roles: [],
            Valid: false
        });
        this.setBusy(true);

        var oUserLoad = this._oAPICaller.doGet("user")
            .then(function(oData) {
                this._setDialogProperty("/Users", oData);
            }.bind(this), this._showError);
        var oRoleLoad = this._oAPICaller.doGet("role")
            .then(function(oData) {
                if (!iAdmin) {
                    this._setDialogProperty("/Roles", oData.filter(function (oRole) {
                        return oRole.slug !== "manager";
                    }));
                } else {
                    this._setDialogProperty("/Roles", oData);
                }
            }.bind(this), this._showError);
        jQuery.when(oUserLoad, oRoleLoad).always(this.setBusy.bind(this, false));
    };

    AddMemberDialog.prototype.create = function() {
    	var oMember = jQuery.extend({}, this._getDialogProperty("/Member"));
        this.setBusy(true);
        this._oAPICaller
            /*.doPostJSON*/
            .doPost("member", oMember)
            .then(function () {
                sap.m.MessageToast.show("Member added successfully");
                this.close();
                this.getParentController().initProject();
            }.bind(this))
            .fail(this._showError)
            .always(this.setBusy.bind(this, false));
    };

    AddMemberDialog.prototype.validate = function() {
        var oMember = this._getDialogProperty("/Member"),
            bValid = oMember.user && oMember.role;

        this._setDialogProperty("/Valid", Boolean(bValid));
    };

    return AddMemberDialog;
});