App.projects = new Vue({
    el: "#projects",
    data: {
        my: [],
        all: []
    },
    methods: {
        refresh: function() {
            App.overlay.show();
            $.get("project")
                .then(function (data) {
                    this.my = data.my;
                    this.all = data.all;
                }.bind(this))
                .always(function() {
                    App.overlay.hide();
                })
        }
    }
});

$(document).ready(function() {
    App.projects.refresh();
});