<%@include file="structure/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Subir Votos Físicos</h1>
	</section>
	<!-- Main content -->
	<section class="content">
	<div class="container">
		<div class="row">
			<div class="col-lg-8">
				<div class="panel panel-success">
					<div class="panel-heading"></div>
				  		<div class="panel-body">
				  			<div class="row">
				  				<div class="col-md-2">
				  					<div class="form-group">
				  						<label><h4>Elección: </h4></label>
				  					</div>
				  				</div>
				  				<div class="col-md-10">
				  					<div class="form-group">
				  						<select class="form-control" id="eleccionSubirVotos">
				  							<c:forEach items="${elecciones}" var="item">
				  							<c:if test="${item.idElecciones.cierre == false}">
				  							<c:if test="${item.idElecciones.votosFisicos == false}">
				  								<option value="${item.idElecciones.id}">${item.idElecciones.nombre}</option>
				  							</c:if>
				  							</c:if>
				  							</c:forEach>
				  						</select>
				  					</div>
				  				</div>
				  			</div>
				  			<div class="row">
				  				<div class="col-md-2">
				  					<div class="form-group">
				  						<label><h4>Candidato: </h4></label>
				  					</div>
				  				</div>
				  				<div class="col-md-4">
				  					<div class="form-group">
				  						<select class="form-control" id="candidatoSubirVotos">
				  							
				  						</select>
				  					</div>
				  				</div>
				  				<div class="col-md-2">
				  					<div class="form-group">
				  						<label><h4># de Votos: </h4></label>
				  					</div>
				  				</div>
				  				<div class="col-md-4">
				  					<div class="form-group">
				  						<input type="number" class="form-control" id="cantidadVotos">
				  					</div>
				  				</div>
				  			</div>
				  			<div class="row">
				  				<div class="col-md-12">
				  					<div class="form-group">
				  						<a class="btn btn-success" style="float:right" onclick="if(confirm('¿Está Seguro?')){SubirVotos();}">Subir Votos</a>
				  					</div>
				  				</div>
				  			</div>
				  			<div class="row">
					  			<div class="col-md-12" style="padding-top:5px">
					  				<div class="alert alert-danger" style="display:none" id="panelSubirVotos">
					  					<div class="text-center"><label id="labelSubirVotos"></label></div>
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