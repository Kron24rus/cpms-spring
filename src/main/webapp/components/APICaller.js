sap.ui.define([
    "sap/ui/base/ManagedObject"
], function(BaseObject) {
    
    var SLASH = "/";
   
    var APICaller = BaseObject.extend("vp.vp.components.APICaller", {
        metadata: {
            properties: {
                baseUrl: {
                    type: "string",
                    defaultValue: ""
                }
            },
            events: {
                requestFailed: {
                    parameters: {
                        status: { type: "int" },
                        statusText: { type: "string" },
                        response: { type: "string" },
                        responseJson: { type: "object" }
                    }
                }
            }
        },
        
        init: function() {
            
        }
    });
    
    /**
     * Construct URL using base and relative ones
     * @param {string} sRelativeUrl - relative URL
     * @returns {string}
     * @function
     * @private
     */
    APICaller.prototype._constructUrl = function(sRelativeUrl) {
        var sBasePart = this.getBaseUrl(),
            sRelativePart = sRelativeUrl || "";
        if (sBasePart && sRelativePart) {
            if (sBasePart.slice(-1) !== SLASH && sRelativePart.slice(0, 1) !== SLASH) {
                return sBasePart + SLASH + sRelativePart;
            }
            return sBasePart + sRelativePart;
        } 
        return sBasePart || sRelativePart;
    };
    
    /**
     * Run a promised request with given settings
     * @param {string} sUrl - relative URL for a request
     * @param {object} oSettings - request settings
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype._promisedRequest = function(sUrl, oSettings) {
        var oDefaults = {
            async: true,
            type: "GET"
        };
        var oRequestSettings = jQuery.extend(oDefaults, oSettings);
        var sRequestUrl = this._constructUrl(sUrl);
        var oRequest = jQuery.ajax(sRequestUrl, oRequestSettings);
        
        oRequest.fail(function(oXhr) {
            this.fireRequestFailed({
               status: Number(oXhr.status),
               statusText: oXhr.statusText,
               response: oXhr.responseText,
               responseJson: oXhr.responseJSON || null
            });
        }.bind(this));
        return oRequest;
    };
        
    /**
     * Run a GET Request with given relative URL and data
     * @param {string} sUrl - relative URL for a request
     * @param {object} oData - data to send
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype.doGet = function(sUrl, oData) {
        return this._promisedRequest(sUrl, {
            data: oData || {},
            type: "GET"
        });
    };
    
    /**
     * Run a POST Request with given relative URL and data
     * @param {string} sUrl - relative URL for a request
     * @param {object} oData - data to send
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype.doPost = function(sUrl, oData) {
        return this._promisedRequest(sUrl, {
            data: oData || {},
            type: "POST"
        });
    };
    /**
     * Run a POST Request with given relative URL and JSON data
     * @param {string} sUrl - relative URL for a request
     * @param {object} oData - data to send
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype.doPostJSON = function(sUrl, oData) {
        return this._promisedRequest(sUrl, {
            data: JSON.stringify(oData || {}),
            type: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8"
        });
    };
    
    /**
     * Run a PUT Request with given relative URL and data
     * @param {string} sUrl - relative URL for a request
     * @param {object} oData - data to send
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype.doPut = function(sUrl, oData) {
        var oNewData = jQuery.extend({}, oData || {}, {_method: "put"});
        return this._promisedRequest(sUrl, {
            data: oData || oNewData,
            type: "POST"
        });
    };
    
    /**
     * Run a DELETE Request with given relative URL and data
     * @param {string} sUrl - relative URL for a request
     * @param {object} oData - data to send
     * @returns {object} - Deferred object
     * @function
     * @private
     */
    APICaller.prototype.doDelete = function(sUrl, oData) {
        var oNewData = jQuery.extend({}, oData || {}, {_method: "delete"});
        return this._promisedRequest(sUrl, {
            data: oData || oNewData,
            type: "POST"
        });
    };

    return APICaller;
});