sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter",
    
    "sap/ui/model/json/JSONModel"
], function (BaseController, APICaller, Formatter, JSONModel) {
    
    return BaseController.extend("dt.cpms.controller.Stage", {
        formatter: Formatter,
        _iId: 0,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.setModel(new JSONModel({
                Stage: {},
                Edit: false
            }), "page");
            
            this.getRouter().getRoute("stage").attachPatternMatched(this.initStage.bind(this));
        },
        
        onEdit: function() {
            this.getModel("page").setProperty("/Edit", true);
        },
        
        flush: function() {
            this.getModel("page").setData({
                Stage: {},
                Edit: false
            });
        },
        
        initStage: function(oEvent) {
            var oArgs = oEvent.getParameter("arguments"),
                iId = Number(oArgs.id || 1),
                bAdmin = this.getModel("user").getProperty("/isAdmin");
            
            this.flush();
            this.getView().setBusy(true);
            this._oCaller.doGet("stage", {id: iId})
                .then(function(oData) {
                    var oModel = this.getModel("page");
                    oModel.setProperty("/Stage", oData);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },
        
        
        navProject: function(oEvent) {
            this.getRouter().navTo("project", {id: this.getModel("page").getProperty("/Stage/project/id")});
        },
        
        navUser: function(oEvent) {
            var iId = oEvent.getSource().getBindingContext("page").getProperty("id");
            this.getRouter().navTo("user", {id: iId});
        }
    });
});