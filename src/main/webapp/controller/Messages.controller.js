sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter"
], function (BaseController, APICaller, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Messages", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.getRouter().getRoute("messages").attachPatternMatched(this.initMessages.bind(this));
        },
        
        initMessages: function() {
            this.getView().setBusy(true);
            this._oCaller.doGet("dialog")
                .then(function(oData) {
                    var oModel = this.getModel("data");
                    oModel.setProperty("/Messages", oData.dialogs);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },

        onDialogPress: function(oEvent) {
            var oSource = oEvent.getSource(),
                oDialog = oSource.getBindingContext("data").getObject();

            this.getRouter().navTo("dialog", {id: oDialog.user.id});
        }
    });
});