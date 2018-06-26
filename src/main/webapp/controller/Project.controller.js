sap.ui.define([
    "dt/cpms/controller/BaseController",
    "dt/cpms/components/APICaller",
    "dt/cpms/model/Formatter",

    "dt/cpms/controller/dialog/CreateStage",
    "dt/cpms/controller/dialog/AddMember",
    
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageBox"
], function (BaseController, APICaller, Formatter, CreateStageDialog, AddMemberDialog, JSONModel, MessageBox) {
    
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
                Admin: false,
                Member: false,
                Manager: false,
                Edit: false
            }), "page");

            this.setModel(new sap.ui.model.json.JSONModel({}), "dialog");
            this.getRouter().getRoute("project").attachPatternMatched(this.initProject.bind(this));
        },

        onAddMember: function() {
            if (!this._oAddMemberDialog) {
                this._oAddMemberDialog = new AddMemberDialog(this.getView(), this.getModel("dialog"), this._oCaller);
            }
            this._oAddMemberDialog.open();
        },

        onCreateStage: function() {
            if (!this._oCreateStageDialog) {
                this._oCreateStageDialog = new CreateStageDialog(this.getView(), this.getModel("dialog"), this._oCaller);
            }
            this._oCreateStageDialog.open();
        },

        onDeleteMember: function(oEvent) {
            var oMember = oEvent.getParameter("listItem").getBindingContext("page").getObject(),
                oPageModel = this.getModel("page"),
                iOwnId = this.getModel("user").getProperty("/id"),
                iProjectId = this._iId;

            if (iOwnId === oMember.user.id && !oPageModel.getProperty("/Admin")) {
                MessageToast.show("Cannot remove own user");
            } else {
                MessageBox.warning("Do you really want to remove?", {
                    actions: [MessageBox.Action.OK, MessageBox.Action.CANCEL],
                    onClose: function(sAction) {
                        if (sAction === MessageBox.Action.OK) {
                            this.getView().setBusy(true);
                            this._oCaller.doDelete("member", {project: this._iId, user: oMember.user.id})
                                .then(this.initProject.bind(this, null))
                                .fail(function() {
                                    this.getView().setBusy(false);
                                }.bind(this));
                        }
                    }.bind(this)
                })
            }
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
                Manager: false,
                Edit: false
            });
        },
        
        initProject: function(oEvent) {
            var oArgs = oEvent && oEvent.getParameter("arguments"),
                iId = Number((oArgs && oArgs.id) || this._iId || 1),
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
                    oModel.setProperty("/Manager", Boolean(oData.role.manager || bAdmin));
                    oModel.setProperty("/Admin", Boolean(bAdmin));
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