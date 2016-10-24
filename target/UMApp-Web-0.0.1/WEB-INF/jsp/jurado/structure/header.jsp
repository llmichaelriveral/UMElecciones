<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Elecciones</title>
	<!-- Favicon -->
	<link rel="shortcut icon" href="/resources/images/favicon.png">
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">
	<!-- Skin -->
	<link rel="stylesheet" href="/resources/dist/css/skin-green.min.css">
	<!-- Tables -->
	<link rel="stylesheet" href="/resources/dist/css/dataTables.bootstrap.css">
	<!-- Custom -->
	<link rel="stylesheet" href="/resources/dist/css/style.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <header class="main-header">

    <!-- Logo -->
    <!-- Logo -->
    <a href="" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>UM</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>UM </b>Elecciones</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account Menu -->
          <li class="dropdown user user-menu">
            <!-- Menu Toggle Button -->
          	<a href="/logout" class="dropdown-toggle">Volver</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <li class="header"></li>
        <!-- Optionally, you can add icons to the links -->
        <li id="home"><a href="index"><i class="fa fa-link"></i> <span>Inicio</span></a></li>
        <li id="elecciones"><a href="elecciones"><i class="fa fa-link"></i> <span>Elecciones</span></a></li>
        <li id="subirVotos"><a href="subirVotos"><i class="fa fa-link"></i> <span>Subir Votos Físicos</span></a></li>
        <li id="reportes"><a href="reportes"><i class="fa fa-link"></i> <span>Reportes</span></a></li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>