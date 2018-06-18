sap.ui.define([
    "sap/ui/base/Object"
], function (BaseObject) {
    "use strict";
    
	var log = jQuery.sap.log;
    
    var BaseDialogController = BaseObject.extend("dt.cpms.controller.dialog.BaseDialog", {
        
        _oAPICaller: null,
	    _oDialog: null,
	    _oDialogModel: null,
	    _oView: null,
	    _sDialogName: null,
        
        constructor: function(oView, oDialogModel, oAPICaller) {
            this._oView = oView;
            this._oDialogModel = oDialogModel;
            this._oAPICaller = oAPICaller;
        }
    });
    
    BaseDialogController.prototype._getDialog = function() {
        if (!this._oDialog) {
            if (this._sDialogName) {
                this._oDialog = sap.ui.xmlfragment("dt.cpms.view.dialog." + this._sDialogName, this);
                this._oView.addDependent(this._oDialog);
                this._oDialog.addStyleClass("sapUiSizeCompact");
            } else {
                log.debug("Dialog name not provided");
            }
        }
        return this._oDialog;
    };
    
    BaseDialogController.prototype._getDialogProperty = function(sProperty) {
        return this._oDialogModel.getProperty("/" + this._sDialogName + sProperty);
    };
    
    BaseDialogController.prototype._setDialogProperty = function(sProperty, vValue) {
        return this._oDialogModel.setProperty("/" + this._sDialogName + sProperty, vValue);
    };
    
    BaseDialogController.prototype.close = function() {
        this._getDialog().close();
    };
    
    BaseDialogController.prototype.getResourceBundle = function() {
        return this.getView().getModel("i18n").getResourceBundle();
    };
    
    BaseDialogController.prototype.getView = function() {
        return this._oView;
    };
    
    BaseDialogController.prototype.getParentController = function() {
        return this._oView.getController();
    };
    
    BaseDialogController.prototype.open = function() {
        this._getDialog().bindElement({
            path: "/" + this._sDialogName,
            model: "dialog"
        });
        this.setBusy(false);
        this._getDialog().open();
    };
    
    BaseDialogController.prototype.setBusy = function(bState) {
        this._getDialog().setBusy(Boolean(bState));
    };
    
    return BaseDialogController;
});