var App = {};

App.overlay = new Vue({
    el: "#overlay",
    data: {},
    methods: {
        hide: function() {
            $(this.$el).hide();
        },
        show: function() {
            $(this.$el).show();
        }
    }
});

App.navbar = new Vue({
    el: "#navbar",
    data: {
        user: null
    },
    methods: {
        refresh: function() {

        },
        logout: function() {
            $.post("logout")
                .then(function() {
                    window.location.href = "";
                });
        }
    }
});

App.header = new Vue({
    el: "#header",
    data: {},
    methods: {
        
    }
});