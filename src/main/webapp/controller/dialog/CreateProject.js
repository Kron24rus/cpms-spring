sap.ui.define([
    "dt/cpms/controller/dialog/BaseDialog"
], function (BaseDialog) {
    "use strict";

    var CreateProjectDialog = BaseDialog.extend("dt.cpms.controller.dialog.CreateProject", {
        _sDialogName: "CreateProject"
    });

    CreateProjectDialog.prototype.open = function() {
        BaseDialog.prototype.open.call(this);
        this._setDialogProperty("/", {
            Project: {
                Name: "",
                Description: "",
                Priority: null,
                Type: null
            },
            Types: {},
            Valid: false
        });
        this.setBusy(true);
        this._oAPICaller.doGet("type")
            .then(function(oData) {
                this._setDialogProperty("/Types", oData);
            }.bind(this), function(oXhr) {
                sap.m.MessageToast.show(oXhr.responseText);
            }).always(this.setBusy.bind(this, false));
    };

    CreateProjectDialog.prototype.create = function() {
    	var oProject = this._getDialogProperty("/Project");
        this.setBusy(true);
        this._oAPICaller.doPostJSON("activiti/project", {
        	name: oProject.Name,
			description: oProject.Description,
			priority: Number(oProject.Priority),
			type: Number(oProject.Type)
		})
            .then(function () {
                this.close();
                this.getParentController().initProjects();
            }.bind(this))
            .fail(function(oXhr) {
                var sText = oXhr.responseText;
                sap.m.MessageToast.show(sText && sText.split("\n").shift());
            }).always(this.setBusy.bind(this, false));
    };

    CreateProjectDialog.prototype.validate = function() {
        var oProject = this._getDialogProperty("/Project");
        this._setDialogProperty("/Valid", Boolean(oProject.Name && oProject.Type && oProject.Priority));
    };

    return CreateProjectDialog;
});