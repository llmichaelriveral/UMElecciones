<%@include file="structure/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>${eleccionActual.nombre} <a class="btn btn-danger" onclick="if(confirm('¿Desea continuar?. No puede deshacer esta acción.')){CerrarEleccion()}">Cerrar Elección</a></h1>
	</section>
	<!-- Main content -->
	<!-- Your Page Content Here -->
	<section class="content">
		<div class="row">
			<div class="col-lg-4">
				<div class="row" style="padding:5px">
					<div class="panel panel-success">
						<div class="panel-heading">Votar Fisico</div>
					  		<div class="panel-body">
						  		<div class="row">
						  			<div class="col-md-3">
							  			<label><h4>Cédula</h4></label>
						  			</div>
						  			<div class="col-md-9">
						  				<input type="hidden" id="idEleccion" value="${eleccionActual.id}">
							  			<input type="number" class="form-control" id="cedulaUsuario">
						  			</div>
						  		</div>
						  		<div class="row">
						  			<div class="col-md-12" style="padding-top:5px">
						  				<a class="btn btn-success" style="float:right" onclick="if(confirm('¿Esta seguro que desea registrar el voto?')){BuscarUsuario()}">Registrar Voto</a>
						  			</div>
						  		</div>
						  		<div class="row">
						  			<div class="col-md-12" style="padding-top:5px">
						  				<div class="alert alert-danger" style="display:none" id="panelRespuestaBuscarUser">
						  					<div class="text-center"><label id="labelRespuestaBuscarUser"></label></div>
						  				</div>
						  			</div>
						  		</div>
						  	</div>
					  	<div class="panel-footer"></div>
					</div>
				</div>
			</div>
			<div class="col-lg-8">
				<div class="row" style="padding:5px">
					<div class="panel panel-success">
						<div class="panel-heading">Ingresar Nuevo Votante</div>
					  		<div class="panel-body">
							  		<div class="row">
							  			<div class="col-md-4">
							  				<label>Cédula</label>
							  				<input type="number" class="form-control" id="cedulaNuevoUser">
							  			</div>
							  			<div class="col-md-4">
							  				<label>Correo Electrónico</label>
							  				<input type="email" class="form-control" id="emailNuevoUser">
							  			</div>
							  			<div class="col-md-4">
							  				<label>Nombres y Apellidos</label>
							  				<input type="email" class="form-control" id="nombresNuevoUser">
							  			</div>
							  		</div>
							  		<div class="row">
							  			<div class="col-md-12">
								  			<div class="form-group">
								  				<label>Concepto</label>
								  				<textarea rows="2" class="form-control" id="conceptoNuevoUser"></textarea>
								  			</div>
							  			</div>
							  		</div>
							  		<div class="row">
							  			<div class="col-md-12">
								  			<div class="form-group">
								  				<input type="hidden" id="idEleccion" value="${eleccionActual.id}">
								  				<a class="btn btn-success" style="float:right" onclick="IngresarUsuario()">Agregar Usuario</a>
								  			</div>
							  			</div>
							  		</div>
							  		<div class="row">
							  			<div class="col-md-12" style="padding-top:5px;">
							  				<div class="form-group">
							  					<div class="alert alert-danger" style="display:none" id="panelNuevoUser">
								  					<div class="text-center"><label id="labelNuevoUser"></label></div>
								  				</div>
							  				</div>
							  			</div>
							  		</div>
						  	</div>
					  	<div class="panel-footer"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@include file="structure/footer.jsp"%>