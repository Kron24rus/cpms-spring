sap.ui.define([
    "dt/cpms/controller/dialog/BaseDialog"
], function (BaseDialog) {
    "use strict";

    var CreateUserDialog = BaseDialog.extend("dt.cpms.controller.dialog.CreateUser", {
        _sDialogName: "CreateUser"
    });

    CreateUserDialog.prototype.open = function() {
        BaseDialog.prototype.open.call(this);
        this._setDialogProperty("/", {
            User: {
                admin: false,
                login: "",
                firstName: "",
                lastName: "",
                password: "",
                confirm: "",
                position: null,
                description: ""
            },
            Exists: false,
            Users: [],
            Positions: [],
            Valid: false
        });
        this.setBusy(true);

        var oUsersLoad = this._oAPICaller.doGet("user")
            .then(function(oData) {
                this._setDialogProperty("/Users", oData.map(function(oUser) {
                    return oUser.login;
                }));
            }.bind(this), this._showError);
        var oPositionsLoad = this._oAPICaller.doGet("position")
            .then(function(oData) {
                this._setDialogProperty("/Positions", oData);
            }.bind(this), this._showError);

        jQuery.when(oUsersLoad, oPositionsLoad).always(this.setBusy.bind(this, false));
    };


    CreateUserDialog.prototype.create = function() {
        var oUser = this._getDialogProperty("/User");
        this.setBusy(true);
        this._oAPICaller
            /*.doPostJSON*/
            .doPost("user", oUser)
            .then(function () {
                sap.m.MessageToast.show("User created successfully");
                this.close();
                this.getParentController().initUsers();
            }.bind(this))
            .fail(this._showError)
            .always(this.setBusy.bind(this, false));
    };

    CreateUserDialog.prototype.validate = function() {
        var oUser = this._getDialogProperty("/User"),
            aUsers = this._getDialogProperty("/Users"),
            bValid = oUser.login && oUser.firstName && oUser.lastName && oUser.position && oUser.password && oUser.password === oUser.confirm,
            bExists = false;

        if (aUsers.includes(oUser.login)) {
            bValid = false;
            bExists = true;
        }

        this._setDialogProperty("/Valid", Boolean(bValid));
        this._setDialogProperty("/Exists", Boolean(bExists));
    };

    return CreateUserDialog;
});