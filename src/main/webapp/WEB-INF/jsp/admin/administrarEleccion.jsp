<%@include file="structure/header.jsp"%>
 <!-- Content Wrapper. Contains page content -->
 <c:if test="${param.imagen ne null}"><script>location.href='/admin/administrarElecciones?id='+${eleccion.id};</script></c:if>
 <div class="content-wrapper">
   <!-- Main content -->
   <section class="content">
   <div class="form-group" style="margin-top:1%">
   <input type="hidden" id="idEleccion" value="${eleccion.id}">
    <h1>${eleccion.nombre}
    	<c:if test="${eleccion.cierre = true}">
    		<c:if test="${noEditar == false}">
    			<a class="btn btn-success" data-toggle="modal" data-target="#modalNuevoCandidato">Añadir Candidato</a>
    		</c:if> 
    		<a class="btn btn-success" data-toggle="modal" data-target="#nuevoJurado">Añadir Jurado</a>
    		<a class="btn btn-success" data-toggle="modal" data-target="#nuevoAptos">Subir Aptos</a>
    		<c:if test="${noEditar == true}">
    			<a class="btn btn-danger" onclick="if(confirm('¿Desea continuar?. No puede deshacer esta acción.')){CerrarEleccion()}">Cerrar Elección</a>
    		</c:if>
    	</c:if>
    </h1>
   </div>
	<div class="row">
		<div class="col-lg-8">
			<c:if test="${noEditar == false}">
			<div class="panel panel-success">
				<div class="panel-heading">Datos Elección</div>
				<div class="panel-body">
					<form action="/admin/editEleccion" onsubmit="return validarEditarEleccion()">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Nombre</label>
								<input type="text" class="form-control" id="nombreEditarEleccion" name="nombre" value="${eleccion.nombre}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label>Fecha Inicio</label>
								<input data-format="dd/MM/yyyy hh:mm:ss" type="text" id="fechaInicioNuevaEleccion" class="form-control" name="fechaInicio" value="${eleccion.fechaInicio}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label>Fecha Fin</label>
								<input data-format="dd/MM/yyyy hh:mm:ss" type="text" id="fechaFinNuevaEleccion"  class="form-control" name="fechaFin" value="${eleccion.fechaFin}">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label>Descripción</label>
								<textarea class="form-control" id="descripcionEditarEleccion" name="descripcion">${eleccion.descripcion}</textarea>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="hidden" value="${eleccion.id}" name="idEleccion">
								<input type="submit" class="btn btn-success" style="float:right" value="Actualizar">
							</div>
						</div>
					</div>
					<div class="row">
			  			<div class="col-md-12" style="padding-top:5px">
			 				<div class="alert alert-danger" style="display:none" id="panelEditarEleccion">
			 					<div class="text-center"><label id="labelEditarEleccion"></label></div>
			 				</div>
			 			</div>
			 		</div>
					</form>
				</div>
				<div class="panel-footer"></div>
			</div>
			</c:if>
			<div class="box box-success">
            <div class="box-header with-border">
            	<h3 class="box-title">Candidatos</h3></div>
            	<!-- /.box-header -->
            <div class="box-body">
              <div class="table-responsive">
                <table class="table no-margin">
                  <thead>
                  <tr>
                  	<th class="text-center">Imagen</th>
                    <th class="text-center">Nombre</th>
                    <th class="text-center">Número Tarjetón</th>
                    <c:if test="${eleccion.cierre = true}">
                    <c:if test="${noEditar == false}">
                    	<th class="text-center">Opciones</th>
                    </c:if>
                    </c:if>
                  </tr>
                  </thead>
                  <tbody>
                  	<!-- Acá va el foreach -->
                  	<c:forEach items="${candidatos}" var="item">
                  		<tr>
	                    <td class="text-center"><div class="product-img"><img class="img-candidato" src="${item.imagen}"/></div></td>
	                    <td class="text-center">${item.nombre}</td>
	                    <td class="text-center">${item.numeroTarjeton}</td>
	                    <c:if test="${eleccion.cierre = true}">
		                    <c:if test="${noEditar == false}">
			                    <td class="text-center">
			                    	<a class="btn btn-sm btn-warning" style="padding:5px" onclick="cargarDatosEditarCandidato(${item.id})" data-toggle="modal" data-target="#modalEditarCandidato">Editar</a>
			                    	<a class="btn btn-sm btn-danger" onclick="if(confirm('¿Esta Seguro?')){window.location.href = '/admin/elimCandidato?id=${item.id}&idEleccion=${eleccion.id}';}">Eliminar</a>
			                    </td>
		                    </c:if>
	                    </c:if>
	                    </tr>
                    </c:forEach>
                    <!-- Acá termina el foreach -->
                  </tbody>
                </table>
              </div>
              <!-- /.table-responsive -->
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix"></div>
            <!-- /.box-footer -->
          </div>
		</div>
		<div class="col-lg-4">
		<div class="box box-success">
            <div class="box-header with-border">
              <h3 class="box-title">Jurados</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <ul class="products-list product-list-in-box">
              <!-- Acá va el foreach -->
              	<c:forEach items="${jurados}" var="item">
	                <li class="item">
	                  <div class="product-info">
	                    <a href="javascript:void(0)" class="product-title">${item.cedula}</a>
	                    <span class="label pull-right">
	                      	<a class="btn btn-sm btn-warning" data-toggle="modal" data-target="#modalEditarJurado"  onclick="cargarDatosEditarJurado(${item.id})">Editar</a>
	                      	<a class="btn btn-sm btn-danger" onclick="if(confirm('¿Desea continuar?. No puede deshacer esta acción.')){window.location.href = '/admin/eliminarJurado?id=${item.id}&idEleccion=${eleccion.id}'}">Eliminar</a>
	                    </span>
	                  </div>
	                </li>
                </c:forEach>
                <!-- Acá termina el foreach -->
              </ul>
            </div>
            <!-- /.box-body -->
            <div class="box-footer text-center"></div>
            <!-- /.box-footer -->
          </div>
		</div>
	</div>
	</section>
</div>
<!-- Modal Editar Candidato -->
<div id="modalEditarCandidato" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Editar Candidato</h4>
			</div>
			<div class="modal-body">
				<form id="formEditarCandidato" onsubmit="return validarEditarCandidato()">
				<div class="row">
					<div class="col-md-5">
						<label>Imagen</label>
						<input type="file" class="form-control" id="imagenEditarCandidato" name="imagen" accept="*" />
					</div>
					<div class="col-md-5">
						<label>Nombre</label>
						<input type="text" class="form-control" id="nombreEditarCandidato" name="nombre">
					</div>
					<div class="col-md-2">
						<div class="form-group">
						<label># Tarjeton</label>
						<input type="number" class="form-control" id="numeroEditarCandidato" name="tarjeton">
						<input type="hidden" id="idCandidato" name="idCandidato" value="">
						<input type="hidden" id="idEditarCandidato" name="id" value="${eleccion.id}" accept="*">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<input type="submit" class="btn btn-success" style="float:right" value="Continuar"/>
					</div>
				</div>
				</form>
				<div class="row">
					<div class="col-md-12" style="padding-top:5px">
		 				<div class="alert alert-danger" style="display:none" id="panelEditarCandidato">
		 					<div class="text-center"><label id="labelEditarCandidato"></label></div>
		 				</div>
		 			</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal Nuevo Candidato -->
<div id="modalNuevoCandidato" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Nuevo Candidato</h4>
			</div>
			<div class="modal-body">
				<form id="formNuevoCandidato" onsubmit="return nuevoCandidato()">
				<div class="row">
					<div class="col-md-5">
						<label>Imagen</label>
						<input type="file" class="form-control" id="imagenNuevoCandidato" name="imagen" accept="*" />
					</div>
					<div class="col-md-5">
						<label>Nombre</label>
						<input type="text" class="form-control" id="nombreNuevoCandidato" name="nombre" accept="*">
					</div>
					<div class="col-md-2">
						<div class="form-group">
						<label># Tarjeton</label>
						<input type="number" class="form-control" id="numeroNuevoCandidato" name="tarjeton" accept="*">
						</div>
					</div>
					<input type="hidden" id="id" name="id" value="${eleccion.id}" accept="*">
				</div>
				<div class="row">
					<div class="col-md-12">
						<input type="submit" class="btn btn-success" style="float:right" value="Continuar" />
					</div>
				</div>
				</form>
				<div class="row">
					<div class="col-md-12" style="padding-top:5px">
		 				<div class="alert alert-danger" style="display:none" id="panelNuevoCandidato">
		 					<div class="text-center"><label id="labelNuevoCandidato"></label></div>
		 				</div>
		 			</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal Nuevo Jurado -->
<div id="nuevoJurado" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Nuevo Jurado</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-2">
						<h4><label>Cédula</label></h4>
					</div>
					<div class="col-md-8">
						<div class="form-group">
						<input type="number" class="form-control" id="cedulaNuevoJurado">
						</div>
					</div>
					<div class="col-md-2">
						<a class="btn btn-success" style="float:right" onclick="nuevoJurado()">Continuar</a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12" style="padding-top:5px">
		 				<div class="alert alert-danger" style="display:none" id="panelNuevoJurado">
		 					<div class="text-center"><label id="labelNuevoJurado"></label></div>
		 				</div>
		 			</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal Editar Jurado -->
<div id="modalEditarJurado" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Editar Jurado</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-2">
						<h4><label>Cédula</label></h4>
					</div>
					<div class="col-md-8">
						<div class="form-group">
						<input type="number" class="form-control" id="cedulaEditarJurado" maxlength="20">
						<input type="hidden" id="oldCedulaEditarJurado" value="">
						</div>
					</div>
					<div class="col-md-2">
						<a class="btn btn-success" style="float:right" onclick="editarJurado()">Continuar</a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12" style="padding-top:5px">
		 				<div class="alert alert-danger" style="display:none" id="panelEditarJurado">
		 					<div class="text-center"><label id="labelEditarJurado"></label></div>
		 				</div>
		 			</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Subir Aptos -->
<div id="nuevoAptos" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Subir Aptos</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-2">
						<h4><label>Archivo</label></h4>
					</div>
					<div class="col-md-5">
						<div class="form-group">
						<input type="file" class="form-control" id="fileAptos">
						</div>
					</div>
					<div class="col-md-2">
						<button class="btn btn-success" style="float:right" id="btnLoadFile">Continuar</button>
					</div>
					<div class="col-md-3">
						<a class="btn btn-primary" style="float:right" href="../resources/documents/Plantilla_Aptos_Votaciones.csv">Plantilla Aptos</a>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12" style="padding-top:5px">
		 				<div class="alert alert-danger" style="display:none" id="panelFileAptos">
		 					<div class="text-center"><label id="labelFileAptos"></label></div>
		 				</div>
		 			</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="structure/footer.jsp"%>