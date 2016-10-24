<%@include file="structure/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<section class="content-header">
		<h1>Reportes</h1>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="panel panel-success">
		<div class="panel-heading"></div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-8">
						<div class="row">
							<div class="col-md-3">
								<label><h4>Elección</h4></label>
							</div>
							<div class="col-md-9">
								<select class="form-control" id="eleccionReporte">
									<c:forEach items="${elecciones}" var="item">
									<option value="${item.idElecciones.id}">${item.idElecciones.nombre}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col-md-2"><div class="form-group"><h5>Fecha Inicio</h5></div></div>
							<div class="col-md-4"><div class="form-group"><input type="text" id="fechaInicioReporte" class="form-control" disabled></div></div>
							<div class="col-md-2"><div class="form-group"><h5>Fecha Fin</h5></div></div>
							<div class="col-md-4"><div class="form-group"><input type="text" id="fechaFinReporte" class="form-control" disabled></div></div>
						</div>
						<div class="row">
							<div class="col-md-3"><h5>Votos Electrónicos</h5></div>
							<div class="col-md-2"><input type="text" id="votosElectronicosReporte" class="form-control" disabled></div>
							<div class="col-md-2"><h5>Votos Físicos</h5></div>
							<div class="col-md-2"><input type="text" id="votosFisicosReporte" class="form-control" disabled></div>
							<div class="col-md-3"><div id="estadoReporte"><label id="estadoReporteLabel"></label></div></div>
						</div>
				</div>
				<div class="col-md-4">
					<div class="box box-success">
			            <div class="box-header with-border">
			              <h3 class="box-title">Total Votos</h3>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body">
			              <ul class="products-list product-list-in-box" id="ulCandidatos">
			              </ul>
			            </div>
			            <!-- /.box-body -->
			            <div class="box-footer text-center"></div>
			            <!-- /.box-footer -->
			          </div>
				</div>
			</div>
		</div>
		</div>
		<div class="panel-footer"></div>
	</section>
</div>
<%@include file="structure/footer.jsp"%>