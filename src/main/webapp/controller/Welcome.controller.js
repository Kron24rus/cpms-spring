sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/model/Formatter"
], function (BaseController, Formatter) {
    
    return BaseController.extend("dt.cpms.controller.Welcome", {
        formatter: Formatter,
        
        navMaster: function() {
            sap.ui.getCore().byId("app").backToTopMaster();
        }
    });
});