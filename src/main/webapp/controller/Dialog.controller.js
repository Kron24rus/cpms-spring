sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter"
], function (BaseController, APICaller, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Dialog", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.getRouter().getRoute("dialog").attachPatternMatched(this.initDialog.bind(this));
        },

        initDialog: function(oEvent) {
            var iId = oEvent.getParameter("arguments").id;
            this._iId = iId;
            jQuery.sap.delayedCall(2000, this, this.pullChanges);
            this.loadDialog();
        },

        loadDialog: function() {
            this.getView().setBusy(true);
            this._oCaller.doGet("dialog", {id: this._iId})
                .then(function(oData) {
                    var oModel = this.getModel("data");
                    oModel.setProperty("/Dialog", oData);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },

        onPost: function(oEvent) {
            var sText = oEvent.getParameter("value");
            this.getView().setBusy(true);
            this._oCaller.doPost("message", {
                    content: sText,
                    target: this._iId
                })
                .then(function() {
                    sap.m.MessageToast.show("Message sent");
                    this.loadDialog();
                }.bind(this))
                .fail(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },

        pullChanges: function() {
            var oView = this.getView(),
                oList = oView.byId("dialogEntrieslist"),
                sHash = sap.ui.core.routing.HashChanger.getInstance().getHash();

            if (/dialog\//.test(sHash)) {
                oList.setBusy(true);
                this._oCaller.doGet("dialog", {id: this._iId})
                    .then(function(oData) {
                        var oModel = this.getModel("data");
                        oModel.setProperty("/Dialog", oData);
                    }.bind(this))
                    .always(function() {
                        oList.setBusy(false);
                        jQuery.sap.delayedCall(2000, this, this.pullChanges);
                    }.bind(this));
            }
        }
    });
});