sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter",
    
    "sap/ui/model/json/JSONModel"
], function (BaseController, APICaller, Formatter, JSONModel) {
    
    return BaseController.extend("dt.cpms.controller.User", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.setModel(new JSONModel({
                User: {},
                Edit: false
            }), "page");
            
            this.getRouter().getRoute("user").attachPatternMatched(this.initUser.bind(this));
        },
        
        onEdit: function() {
            this.getModel("page").setProperty("/Edit", true);
        },
        
        initUser: function(oEvent) {
            var oArgs = oEvent.getParameter("arguments"),
                iId = Number(oArgs.id || 0);
            this.getModel("page").setProperty("/Edit", false);
            this.getView().setBusy(true);
            this._oCaller.doGet("user", {id: iId})
                .then(function(oData) {
                    var oModel = this.getModel("page");
                    oData.name = [oData.firstName, oData.lastName].filter(Boolean).join(" ");
                    oModel.setProperty("/User", oData);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        }
    });
});