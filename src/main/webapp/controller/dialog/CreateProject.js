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
            Exists: false,
            Types: {},
            Projects: [],
            Valid: false
        });
        this.setBusy(true);

        var oTypeLoad = this._oAPICaller.doGet("type")
            .then(function(oData) {
                this._setDialogProperty("/Types", oData);
            }.bind(this), this._showError);
        var oProjectLoad = this._oAPICaller.doGet("project")
            .then(function(oData) {
                var aNames = oData.all || [];
                aNames = aNames.map(function (value) {
                    return value.name;
                });
                this._setDialogProperty("/Projects", aNames);
            }.bind(this), this._showError);

        jQuery.when(oTypeLoad, oProjectLoad).always(this.setBusy.bind(this, false));
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
            .fail(this._showError)
            .always(this.setBusy.bind(this, false));
    };

    CreateProjectDialog.prototype.validate = function() {
        var oProject = this._getDialogProperty("/Project"),
            aProjects = this._getDialogProperty("/Projects"),
            bValid = oProject.Name && oProject.Type && oProject.Priority,
            bExists = false;

        if (aProjects.includes(oProject.Name)) {
            bValid = false;
            bExists = true;
        }

        this._setDialogProperty("/Valid", Boolean(bValid));
        this._setDialogProperty("/Exists", Boolean(bExists));
    };

    return CreateProjectDialog;
});