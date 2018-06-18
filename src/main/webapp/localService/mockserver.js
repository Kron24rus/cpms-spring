sap.ui.define([
	"sap/ui/core/util/MockServer"
], function (MockServer) {
	"use strict";
	
	var USERS = {
	    "1": {"id":1,"login":"admin","firstName":"Ivan","lastName":"Ivanov","isAdmin":true,"info":{"id":1,"description":"CPMS Admin","position":{"id":1,"name":"Junior Software Developer"}},"ownUser":false},
        "2": {"id":2,"login":"test","firstName":"Petr","lastName":"Petrov","isAdmin":true,"info":{"id":2,"description":"Test User"},"ownUser":false}
	};
	
	var PROJECTS = {
	    "1": {"project":{"id":1,"name":"CPMS","description":"Corporate Project Management System","active":true,"priority":1,"projectType":{"id":1,"slug":"waterfall","name":"Waterfall"},"members":[{"user":{"id":2,"login":"test","firstName":"Petr","lastName":"Petrov","isAdmin":true,"ownUser":false},"role":{"id":8,"slug":"dev","name":"Developer"}}]},"role":{"member":false,"manager":false,"id":0}},
	    "2": {"project":{"id":2,"name":"Test creating","description":"Descr","active":false,"priority":2,"projectType":{"id":1,"slug":"waterfall","name":"Waterfall"},"members":[]},"role":{"member":false,"manager":false,"id":0}}
	};
	
	var STAGES = {
	    "1": {"id":1,"name":"Test planning","order":1,"project":{"id":2,"name":"Test creating","description":"Descr","active":false,"priority":2,"projectType":{"id":1,"slug":"waterfall","name":"Waterfall"}},"users":[]},
	    "2": {"id":2,"name":"Test planning 2","order":1,"project":{"id":2,"name":"Test creating","description":"Descr","active":false,"priority":2,"projectType":{"id":1,"slug":"waterfall","name":"Waterfall"}},"users":[]}
	};
	
	var LOG = {
	    "1": {"id":1,"date":"27.05.2018 23:57:32","type":{"id":1,"slug":"newproj","name":"Project created"},"author":{"id":1,"login":"admin","firstName":"Ivan","lastName":"Ivanov","isAdmin":true,"ownUser":false}},
	    "2": {"id":2,"date":"28.05.2018 00:19:44","newValue":"Test planning","type":{"id":6,"slug":"newstage","name":"Project stage created"},"author":{"id":1,"login":"admin","firstName":"Ivan","lastName":"Ivanov","isAdmin":true,"ownUser":false}},
	    "3": {"id":3,"date":"28.05.2018 00:27:06","newValue":"Test planning","type":{"id":6,"slug":"newstage","name":"Project stage created"},"author":{"id":1,"login":"admin","firstName":"Ivan","lastName":"Ivanov","isAdmin":true,"ownUser":false}},
	    "4": {"id":4,"date":"28.05.2018 00:35:31","newValue":"Google","type":{"id":10,"slug":"newdepl","name":"Deployment created"},"author":{"id":1,"login":"admin","firstName":"Ivan","lastName":"Ivanov","isAdmin":true,"ownUser":false}}
	};
	
	var TYPES = {
	    "1": {"id":1,"slug":"waterfall","name":"Waterfall"}, 
	    "2": {"id":2,"slug":"scrum","name":"Scrum"},
	    "3": {"id":3,"slug":"kanban","name":"Kanban"} 
	};
	
	var fnParse = function(sBody) {
        var aItems = sBody.split("&");
        var oData = {};
        
        aItems.forEach(function(sItem) {
            var aPair = sItem.split("=");
            if (aPair.length === 2 && aPair[0]) {
                var sName = decodeURIComponent(aPair[0]).toLowerCase();
                if (/\[\]$/.test(sName)) {
                    sName = /(.*)\[\]$/.exec(sName)[1];
                    if (!oData[sName]) {
                        oData[sName] = [];
                    }
                    oData[sName].push(aPair[1]);
                } else {
                    oData[sName] = decodeURIComponent(aPair[1]);
                }
            }
        }); 
        return oData;
	};
	
	var aRequests = [
        {
            method: "GET",
            path: /project[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oBody = fnParse(sUrlParams || "");
                var oResponse = {};
                if (oBody.id) {
                    oResponse = PROJECTS[Number(oBody.id)] || {};
                } else {
                    oResponse = {
                        my: [PROJECTS[1].project],
                        all: [{
                            "id": 2, "name": "Test creating", "description": "Descr", "active": false,
                            "priority": 2, "projectType": { "id": 1, "slug": "waterfall", "name": "Waterfall" }
                        }]
                    };
                }
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        },
        {
            method: "POST",
            path: /project[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                console.log(oXhr.requestBody, sUrlParams);
                oXhr.respondJSON(200, {}, "{}");
                return true;
            }
        },
        {
            method: "GET",
            path: /user[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oBody = fnParse(sUrlParams || "");
                var oResponse = {};
                if (oBody.id === "0") {
                    oResponse = USERS[1];
                } else if (oBody.id) {
                    oResponse = USERS[Number(oBody.id)] || {};   
                }
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        },
        {
            method: "GET",
            path: /message[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oResponse = [{"id":2,"content":"Message to admin","creationDate":"27.05.2018 23:50:01","unread":false,"author":{"id":2,"login":"test","firstName":"Petr","lastName":"Petrov","isAdmin":true,"ownUser":false}}];
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        },
        {
            method: "GET",
            path: /stage[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oBody = fnParse(sUrlParams || "");
                var oResponse = {};
                if (oBody.id) {
                    oResponse = STAGES[Number(oBody.id)] || {};
                } else if (oBody.project) {
                    oResponse = [STAGES[1], STAGES[2]];
                }
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        },
        {
            method: "GET",
            path: /log[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oBody = fnParse(sUrlParams || "");
                var oResponse = {};
                if (oBody.id) {
                    oResponse = LOG[Number(oBody.id)] || {};
                } else if (oBody.project) {
                    oResponse = LOG;
                } 
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        },
        {
            method: "GET",
            path: /type[\/]?\??(.*)/,
            response: function(oXhr, sUrlParams) {
                var oBody = fnParse(sUrlParams || "");
                var oResponse = {};
                if (oBody.id) {
                    oResponse = TYPES[Number(oBody.id)] || {};
                } else {
                    oResponse = TYPES;
                } 
                oXhr.respondJSON(200, {}, JSON.stringify(oResponse));
                return true;
            }
        }
        
	    
	];
	
	return {

		init: function () {
			var oMockServer = new MockServer({
				rootUri: "../",
				requests: aRequests
			});

			var oUriParameters = jQuery.sap.getUriParameters();

			MockServer.config({
				autoRespond: true,
				autoRespondAfter: oUriParameters.get("serverDelay") || 100
			});
		
			oMockServer.start();
		}
	};

});