sap.ui.define([
], function() {

    return {
        allowProjectEdit: function(bIsManager, bIsAdmin) {

        },

        count: function(aArray) {
            return aArray && aArray.length || 0;
        },

        priorityName: function(sPriority) {
            var iPrior = Number(sPriority);
            if (iPrior < 2) {
                return "High priority";
            } else if (iPrior < 3) {
                return "Medium priority";
            } else {
                return "Low priority";
            }
        },

        priorityState: function(sPriority) {
            var iPrior = Number(sPriority);
            if (iPrior < 2) {
                return "Error";
            } else if (iPrior < 3) {
                return "Warning";
            } else {
                return "Success";
            }
        }
    };
});