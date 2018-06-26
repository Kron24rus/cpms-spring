sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter",

    "dt/cpms/controller/dialog/CreateUser"
], function (BaseController, APICaller, Formatter, CreateUserDialog) {
    
    return BaseController.extend("dt.cpms.controller.Users", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "../"
            });
            this.setModel(new sap.ui.model.json.JSONModel({}), "dialog");
            this.getRouter().getRoute("users").attachPatternMatched(this.initUsers.bind(this));
        },

        initUsers: function() {
            this.getView().setBusy(true);
            this._oCaller.doGet("user")
                .then(function(oData) {
                    var oModel = this.getModel("data");
                    oModel.setProperty("/Users", oData);
                    sap.m.MessageToast.show("User list updated", {duration: 500});
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },

        navUser: function(oEvent) {
            var iId = oEvent.getSource().getBindingContext("data").getProperty("id");
            this.getRouter().navTo("user", {id: iId});
        },

        onCreate: function() {
            if (!this._oCreateUserDialog) {
                this._oCreateUserDialog = new CreateUserDialog(this.getView(), this.getModel("dialog"), this._oCaller);
            }
            this._oCreateUserDialog.open();
        }
    });
});