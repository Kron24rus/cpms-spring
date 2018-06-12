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
		    this._oCaller = new APICaller({
		        baseUrl: "./"
		    });
			// call the base component's init function
			UIComponent.prototype.init.apply(this, arguments);

			// set the device model
			this.setModel(models.createDeviceModel(), "device");
			this.setModel(new JSONModel(), "user");
			this.setModel(models.createUiModel(), "ui");
			this.setModel(models.createDataModel(), "data");
			
			var oUserModel = this.getModel("user");
			this._oCaller
			    .doGet("user", {id: 0})
			    .then(function(oUser) {
			        oUserModel.setData(oUser);
			        if (oUser.isAdmin) {
			            sap.ui.getCore().applyTheme("sap_belize_plus");
			        } else {
			            sap.ui.getCore().applyTheme("sap_belize");
			        }
			    });
			    
			
			this.getRouter().initialize();
			this.getRouter().navTo("master", {});
		}
	});
});