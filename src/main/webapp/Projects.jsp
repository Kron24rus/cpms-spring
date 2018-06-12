<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/dash.css">
    <title>Projects</title>
</head>
<body class="bg-light">


<div class="nav-scroller bg-white box-shadow">
    <nav id="navbar" class="nav nav-underline">
        <a class="nav-link" href="#">Dashboard</a>
        <a class="nav-link active" href="#">
            Projects
            <!--<span class="badge badge-pill bg-light align-text-bottom">2</span>-->
        </a>
        <a class="nav-link" href="#">
            Messages
            <!--<span class="badge badge-pill bg-light align-text-bottom">2</span>-->
        </a>
        <a class="nav-link" href="#">Explore</a>
        <span class="nav-span ml-auto"></span>
        <span class="nav-span" v-if="user">

        </span>
        <a class="nav-link" href="#" @click="logout">Logout</a>
    </nav>
</div>

<main role="main" class="container">
    <div id="header" class="d-flex align-items-center p-3 my-3 text-white-50 bg-purple rounded box-shadow">
        <div class="lh-100">
            <h6 class="mb-0 text-white lh-100">Projects</h6>
            <small>Explore all projects in CPMS</small>
        </div>
    </div>

    <div id="projects">
        <div class="my-3 p-3 bg-white rounded box-shadow">
            <h6 class="border-bottom border-gray pb-2 mb-0">My projects</h6>
            <div v-for="project in my" class="media text-muted pt-3">
                <p class="media-body pb-3 mb-0 small border-bottom border-gray">
                    <strong class="d-block">
                        <a class="text-gray-dark" href="#">{{ project.name }}</a>
                        <span class="badge badge-danger">{{ project.priority }}</span>
                        <span class="badge badge-info">{{ project.projectType.name }}</span>
                    </strong>
                    {{ project.description }}
                </p>
            </div>
            <small class="d-block text-right mt-3">
                <!--<a href="#">All updates</a>-->
            </small>
        </div>

        <div class="my-3 p-3 bg-white rounded box-shadow">
            <h6 class="border-bottom border-gray pb-2 mb-0">All projects</h6>
            <div v-for="project in all" class="media text-muted pt-3">
                <p class="media-body pb-3 mb-0 small border-bottom border-gray">
                    <strong class="d-block">
                        <a class="text-gray-dark" href="#">{{ project.name }}</a>
                        <span class="badge badge-danger">{{ project.priority }}</span>
                        <span class="badge badge-info">{{ project.projectType.name }}</span>
                    </strong>
                    {{ project.description }}
                </p>
            </div>
            <small class="d-block text-right mt-3">
                <!--<a href="#">All suggestions</a>-->
            </small>
        </div>
    </div>
</main>

<div id="overlay" class="overlay">
    <div class="overlay-inner">
        <img src="img/load.gif"/>
    </div>
</div>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/vue.js"></script>
<script src="js/app.js"></script>
<script src="js/projects.js"></script>
</html>
