sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter"
], function (BaseController, APICaller, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Master", {
        formatter: Formatter,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "../"
            });
        },
        
        logout: function() {
            this.getView().setBusy(true);
            this._oCaller.doPost("logout")
                .then(function() {
                    window.location.href = "login";
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },
        
        navMessages: function() {
            this.getRouter().navTo("messages", {});
        },
        
        navMy: function() {
            this.getRouter().navTo("user", {id: 0});
        },
        
        navProjects: function() {
            this.getRouter().navTo("projects", {});  
        },

        navUsers: function() {
            this.getRouter().navTo("users", {});
        }
    });
});