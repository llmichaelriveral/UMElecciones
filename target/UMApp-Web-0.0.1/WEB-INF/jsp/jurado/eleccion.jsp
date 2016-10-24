<%@include file="structure/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Main content -->
	<section class="content">
	<div class="form-group" style="margin-top:1%">
		<!-- ALERTAS PRESTAMOS -->
		<c:if test="${param.VotoExitoso ne null}">
			<div class="alert alert-success"><div class="text-center">Su voto se ha guardado exitosamente.</div></div>
		</c:if>
		<c:if test="${param.YaVoto ne null }">
			<div class="alert alert-warning"><div class="text-center">Usted ya registró un voto ${param.YaVoto}.</div></div>
		</c:if>
	</div>
		<div class="panel panel-success">
			<div class="panel-heading"><h3>${eleccion.nombre}</h3></div>
				<div class="panel-body">
					<div class="row">
						<c:forEach items="${candidatos}" var="candidato">
							<div class="col-lg-6">
								<div class="panel panel-success">
									<div class="panel-heading">
										<div class="row">
											<div class="col-md-6">
												<h2>${candidato.nombre} | </h2>
											</div>
											<div class="col-md-6">
												<h3>Tarjetón # ${candidato.numeroTarjeton}</h3>
											</div>
										</div>
									</div>
									<div class="panel-body text-center center-block">
										<div class="hover-candidato">												
											<a onclick="if(confirm('¿Esta Seguro?')){window.location.href = '/jurado/votar?idEleccion=${eleccion.id}&idCandidato=${candidato.id}'}">
												<figure>
													<img src="${candidato.imagen}" class="img-tarjeton" style="height:150px;cursor:pointer;"/>
												</figure>
											</a>
										</div>
									</div>
									<div class="panel-footer"></div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			<div class="panel-footer"></div>
		</div>
	</section>
</div>
<%@include file="structure/footer.jsp"%>