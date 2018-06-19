sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"sap/ui/model/json/JSONModel",
    "dt/cpms/components/APICaller",
	"dt/cpms/model/models"
], function(UIComponent, Device, JSONModel, APICaller, models) {
	"use strict";

	return UIComponent.extend("dt.cpms.Component", {

		metadata: {
			manifest: "json"
		},

		/**
		 * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
		 * @public
		 * @override
		 */
		init: function() {
            var oUserModel = new JSONModel();
		    this._oCaller = new APICaller({
		        baseUrl: "./"
		    });

            this._oCaller
                .doGet("user", {id: 0})
                .then(function(oUser) {
                    oUserModel.setData(oUser);
                    if (oUser.isAdmin) {
                        sap.ui.getCore().applyTheme("sap_belize_plus");
                    } else {
                        sap.ui.getCore().applyTheme("sap_belize");
                    }
                }).fail(function() {
                	window.location.href = "/login";
            	});

			// call the base component's init function
			UIComponent.prototype.init.apply(this, arguments);

			// set the device model
			this.setModel(models.createDeviceModel(), "device");
			this.setModel(oUserModel, "user");
			this.setModel(models.createUiModel(), "ui");
			this.setModel(models.createDataModel(), "data");

			this.getRouter().initialize();
			if (!this.getModel("device").getProperty("/system/phone")) {
                this.getRouter().navTo("projects", {});
			} else {
                this.getRouter().navTo("master", {});
			}
		}
	});
});