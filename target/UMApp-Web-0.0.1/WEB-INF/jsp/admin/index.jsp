<%@include file="structure/header.jsp"%>
 <!-- Content Wrapper. Contains page content -->
 <div class="content-wrapper">
   <!-- Main content -->
   <section class="content">
   <div class="form-group" style="margin-top:1%">
		<!-- ALERTAS PRESTAMOS -->
		<c:if test="${param.VotoExitoso ne null}">
			<div class="alert alert-success"><div class="text-center">Voto registrado eitosamente.</div></div>
		</c:if>
		<c:if test="${param.NoApto ne null}">
			<div class="alert alert-danger"><div class="text-center">No es apto para votar en esta eleción.</div></div>
		</c:if>
		<c:if test="${param.Closed ne null}">
			<div class="alert alert-success"><div class="text-center">Elección cerrada con éxito.</div></div>
		</c:if>
		<c:if test="${param.Success ne null}">
			<div class="alert alert-success"><div class="text-center">Elección creada con éxito.</div></div>
		</c:if>
		<c:if test="${param.FueraFechas ne null }">
			<div class="alert alert-warning"><div class="text-center">Se encuentra fuera de las fechas de votación.</div></div>
		</c:if>
		<c:if test="${param.YaVoto ne null }">
			<div class="alert alert-warning"><div class="text-center">Usted ya registró un voto ${param.YaVoto}.</div></div>
		</c:if>
	</div>
   	<!-- Data table -->
	<div class="box">
		<div class="box-header"><h3>Tus elecciones <a class="btn btn-success" data-toggle="modal" data-target="#nuevaEleccion">Añadir nueva</a></h3></div>
		<!-- /.box-header -->
		<div class="box-body" style="overflow-x: scroll;">
			<table id="tabla-elecciones" class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Drescripción</th>
					<th>Fecha/Hora Inicio</th>
					<th>Fecha/Hora Fin</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${eleccionesAdmin}" >
					<c:if test="${item.cierre == true}">
					<tr class="itemTr" onclick="document.location = 'administrarElecciones?id=${item.id}'">
						<td class="text-center">${item.nombre}</td>
						<td class="text-center">${item.descripcion}</td>
						<td class="text-center">${item.fechaInicio}</td>
						<td class="text-center">${item.fechaFin}</td>
					</tr>
					</c:if>
				</c:forEach>
			</tbody>
			<tfoot></tfoot>
			</table>
		</div>
		<!-- /.box-body -->
	</div>
	<!-- /.Data table -->
   </section>
   <!-- /.content -->
 </div>
 <!-- /.content-wrapper -->
 <!-- Modal -->
<div id="nuevaEleccion" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Nueva Elección</h4>
      </div>
      <div class="modal-body">
      <form action="nuevaEleccion" onsubmit="return validarNuevaEleccion()">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label>Nombre</label>
					<input type="text" class="form-control" id="nombreNuevaEleccion" name="nombre" maxlength="20">
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label>Fecha Inicio</label>
					<input data-format="dd/MM/yyyy hh:mm:ss" type="text" id="fechaInicioNuevaEleccion" class="form-control" name="fechaInicio" maxlength="20">
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label>Fecha Fin</label>
					<input data-format="dd/MM/yyyy hh:mm:ss" type="text" id="fechaFinNuevaEleccion"  class="form-control" name="fechaFin">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label>Descripción</label>
					<textarea class="form-control" id="descripcionNuevaEleccion" name="descripcion" maxlength="200"></textarea>
				</div>
			</div>
		</div>
		<div class="row">
  			<div class="col-md-12" style="padding-top:5px">
 				<div class="alert alert-danger" style="display:none" id="panelNuevaEleccion">
 					<div class="text-center"><label id="labelNuevaEleccion"></label></div>
 				</div>
 			</div>
 		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
        <input type="submit" class="btn btn-success" value="Continuar"/>
      </div>
    </div>
    </form>
  </div>
</div>
<%@include file="structure/footer.jsp"%>