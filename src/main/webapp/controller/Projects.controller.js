sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter"
], function (BaseController, APICaller, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Projects", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.getRouter().getRoute("projects").attachPatternMatched(this.initProjects.bind(this));
        },
        
        initProjects: function() {
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
        
        navProject: function(oEvent) {
            var iProjectId = oEvent.getSource().getBindingContext("data").getProperty("id");
            this.getRouter().navTo("project", {id: iProjectId});
        }
    });
});