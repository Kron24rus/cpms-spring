sap.ui.define([
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device"
], function(JSONModel, Device) {
	"use strict";

	return {
	    createDataModel: function() {
	        return new JSONModel({
	            Projects: {
	                My: [],
	                All: []
	            },
	            Messages: [
	                
	            ],
				Dialog: {

				}
	        });
	    },

		createDeviceModel: function() {
			var oModel = new JSONModel(Device);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		},
		
		createUiModel: function() {
		    return new JSONModel({
		        tab: "project"
		    });
		}
	};
});