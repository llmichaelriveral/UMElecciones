<%@include file="structure/header.jsp"%>
 <!-- Content Wrapper. Contains page content -->
 <div class="content-wrapper">
   <!-- Main content -->
   <section class="content">
   <div class="form-group" style="margin-top:1%">
		<!-- ALERTAS PRESTAMOS -->
		<c:if test="${param.NoApto ne null}">
			<div class="alert alert-danger"><div class="text-center">No es apto para votar en esta eleción.</div></div>
		</c:if>
	</div>
   	<!-- Data table -->
	<div class="box">
		<div class="box-header"><h3>Elecciones Actuales</h3></div>
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
				<c:forEach var="item" items="${listaElecciones}" >
					<c:if test="${item.cierre == true}">
					<tr class="itemTr" onclick="document.location = 'eleccion?id=${item.id}'">
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
<%@include file="structure/footer.jsp"%>