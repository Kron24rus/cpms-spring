sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter",
    
    "sap/ui/model/json/JSONModel"
], function (BaseController, APICaller, Formatter, JSONModel) {
    
    return BaseController.extend("dt.cpms.controller.Project", {
        formatter: Formatter,
        _iId: 0,
        
        onInit: function() {
            this._oCaller = new APICaller({
                baseUrl: "./"
            });
            
            this.setModel(new JSONModel({
                Project: {},
                Stages: [],
                Role: {},
                Log: [],
                Member: false,
                Edit: false
            }), "page");
            
            this.getRouter().getRoute("project").attachPatternMatched(this.initProject.bind(this));
        },
        
        onEdit: function() {
            this.getModel("page").setProperty("/Edit", true);
        },
        
        flush: function() {
            this.getModel("page").setData({
                Project: {},
                Stages: [],
                Log: [],
                Role: {},
                Member: false,
                Edit: false
            });
        },
        
        initProject: function(oEvent) {
            var oArgs = oEvent.getParameter("arguments"),
                iId = Number(oArgs.id || 1),
                bAdmin = this.getModel("user").getProperty("/isAdmin");
                
            this._iId = iId;
            this.flush();
            this.getView().setBusy(true);
            this._oCaller.doGet("project", {id: iId})
                .then(function(oData) {
                    var oModel = this.getModel("page");
                    oModel.setProperty("/Project", oData.project);
                    oModel.setProperty("/Role", oData.role);
                    oModel.setProperty("/Member", Boolean(oData.role.member || bAdmin));
                    if (oData.role.member || bAdmin) {
                        var oPromise = $.Deferred();
                        this.initInternalData(oPromise, iId);
                        return oPromise.promise();
                    }
                }.bind(this))
                .always(function() {
                    this.getView().setBusy(false);
                }.bind(this));
        },
        
        initInternalData: function(oPromise, iId) {
            var oFirst = $.Deferred(),
                oSecond = $.Deferred();
            
            this.initLog(oFirst, iId);
            this.initStages(oSecond, iId);
            
            $.when(oFirst, oSecond)
                .then(function() {
                    oPromise.resolve();
                }).fail(function() {
                    oPromise.reject();
                });
        },
        
        initLog: function(oPromise, iId) {
            this._oCaller.doGet("log", {project: iId})
                .then(function(oData) {
                    var oModel = this.getModel("page");
                    oModel.setProperty("/Log", oData);
                    oPromise.resolve();
                }.bind(this))
                .fail(function() {
                    oPromise.reject(); 
                });
        },
        
        initStages: function(oPromise, iId) {
            this._oCaller.doGet("stage", {project: iId})
                .then(function(oData) {
                    var oModel = this.getModel("page");
                    oModel.setProperty("/Stages", oData);
                    oPromise.resolve();
                }.bind(this))
                .fail(function() {
                    oPromise.reject(); 
                });
        },
        
        navStage: function(oEvent) {
            var iId = oEvent.getSource().getBindingContext("page").getProperty("id");
            this.getRouter().navTo("stage", {id: iId});
        },
        
        navUser: function(oEvent) {
            var iId = oEvent.getSource().getBindingContext("page").getProperty("user/id");
            this.getRouter().navTo("user", {id: iId});
        },

        onReport: function() {
            window.open("report?id=" + this._iId, "_blank");
        }
    });
});