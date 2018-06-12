sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter"
], function (BaseController, APICaller, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Main", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.getRouter().getRoute("main").attachPatternMatched(this.initProjects.bind(this));
        },
        
        _getContainer: function() {
            return this.getView().byId("container");  
        },
        
        _navPage: function(sPageName) {
            this.getModel("ui").setProperty("/tab", sPageName);
            this._getContainer().to(this.getView().byId(sPageName).getId(), "fade");
        },
        
        initProjects: function() {
            this._navPage("projects");
            this.getView().setBusy(true);
            this._oCaller.doGet("project")
                .then(function(oData) {
                    var oModel = this.getModel("data");
                    oModel.setProperty("/Projects/My", oData.my);
                    oModel.setProperty("/Projects/All", oData.all);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },
        
        initMessages: function() {
            this._navPage("messages");
            this.getView().setBusy(true);
            this._oCaller.doGet("message")
                .then(function(oData) {
                    var oModel = this.getModel("data");
                    oModel.setProperty("/Messages", oData);
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        }
    });
});