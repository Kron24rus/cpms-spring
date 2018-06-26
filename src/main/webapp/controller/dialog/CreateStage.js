sap.ui.define([
    "dt/cpms/controller/dialog/BaseDialog",
    "sap/ui/core/format/DateFormat"
], function (BaseDialog, DateFormat) {
    "use strict";

    var _dateFormat = DateFormat.getDateTimeInstance({
        pattern: "dd.MM.yyyy HH:mm:ss"
    });

    var CreateStageDialog = BaseDialog.extend("dt.cpms.controller.dialog.CreateStage", {
        _sDialogName: "CreateStage"
    });

    CreateStageDialog.prototype.open = function() {
        BaseDialog.prototype.open.call(this);
        var iProjectId = this.getParentController().getView().getModel("page").getProperty("/Project/id");
        this._setDialogProperty("/", {
            Stage: {
                name: "",
                template: null,
                project: Number(iProjectId),
                start: null,
                end: null,
                order: 1
            },
            Templates: [],
            Valid: false
        });
        this.setBusy(true);

        var oTemplateLoad = this._oAPICaller.doGet("template")
            .then(function(oData) {
                this._setDialogProperty("/Templates", oData);
            }.bind(this), this._showError);
        jQuery.when(oTemplateLoad).always(this.setBusy.bind(this, false));
    };

    CreateStageDialog.prototype.create = function() {
    	var oStage = jQuery.extend({}, this._getDialogProperty("/Stage"));
    	if (!oStage.start) {
    	    delete oStage.start;
        } else {
    	    oStage.start = _dateFormat.format(oStage.start);
        }
        if (!oStage.end) {
    	    delete oStage.end;
        } else {
            oStage.end = _dateFormat.format(oStage.end);
        }
        this.setBusy(true);
        this._oAPICaller
            /*.doPostJSON*/
            .doPost("stage", oStage)
            .then(function () {
                sap.m.MessageToast.show("Stage created successfully");
                this.close();
                this.getParentController().initProject();
            }.bind(this))
            .fail(this._showError)
            .always(this.setBusy.bind(this, false));
    };

    CreateStageDialog.prototype.validate = function() {
        var oStage = this._getDialogProperty("/Stage"),
            bValid = oStage.name && oStage.template && oStage.order;

        this._setDialogProperty("/Valid", Boolean(bValid));
    };

    return CreateStageDialog;
});