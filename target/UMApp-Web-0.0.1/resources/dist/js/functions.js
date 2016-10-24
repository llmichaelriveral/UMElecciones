$(function() {
	Menu();
	CargarCandidatos();
	$("#eleccionSubirVotos").change(CargarCandidatos);
	$("#btnLoadFile").click(subirAptos);
	$("#numeroNuevoCandidato").change(cambiarValorTarjetonNuevoCandidato);
	$("#cedulaNuevoJurado").change(cambiarValorCedulaNuevoJurado);
	$("#cedulaEditarJurado").change(cambiarValorCedulaEditarJurado);
	$("#numeroEditarCandidato").change(cambiarValorTarjetonEditarCandidato);
	if($("#adminor").val() === "0"){
		CargarDatosReporteAdmin();
		$("#eleccionReporte").change(CargarDatosReporteAdmin);
	}else{
		CargarDatosReporte();
		$("#eleccionReporte").change(CargarDatosReporte);
	}
});

function cambiarValorTarjetonNuevoCandidato(){
	var valor = $("#numeroNuevoCandidato").val();
	if(valor < 0){$("#numeroNuevoCandidato").val(valor*-1);}
}

function cambiarValorTarjetonEditarCandidato(){
	var valor = $("#numeroEditarCandidato").val();
	if(valor < 0){$("#numeroEditarCandidato").val(valor*-1);}
}

function cambiarValorCedulaNuevoJurado(){
	var valor = $("#cedulaNuevoJurado").val();
	if(valor < 0){$("#cedulaNuevoJurado").val(valor*-1);}
}

function cambiarValorCedulaEditarJurado(){
	var valor = $("#cedulaEditarJurado").val();
	if(valor < 0){$("#cedulaEditarJurado").val(valor*-1);}
}

function nuevoCandidato(){
	var imagen = $("#imagenNuevoCandidato").val();
	var nombre = $("#nombreNuevoCandidato").val();
	var tarjeton = $("#numeroNuevoCandidato").val();
	if((imagen.trim() === "") || (nombre.trim() === "") || (tarjeton.trim() == "")){
		printValues("#labelNuevoCandidato", "#panelNuevoCandidato", "Ingrese todos los datos para continuar.", "alert alert-danger");
		return false;
	}
	if(tarjeton <= 0){
		printValues("#labelNuevoCandidato", "#panelNuevoCandidato", "Ingrese un número de tarjeton correcto.", "alert alert-danger");
		return false;
	}
	if(imagen.indexOf("jpg") >= 0){
		$.ajax({
			url: "/admin/uploadFile",
			type: "POST",
			data: new FormData($("#formNuevoCandidato")[0], $("#formNuevoCandidato")[1], $("#formNuevoCandidato")[2], $("#formNuevoCandidato")[3]),
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			cache: false,
			success: function () {
				location.reload();
			}
		});
	}else{
		printValues("#labelNuevoCandidato", "#panelNuevoCandidato", "Formato de imagen incorrecto (solo se adminten .jpg).", "alert alert-danger");
		return false;
	}
}

function cargarDatosEditarCandidato(id){
	$.ajax({
		async : true,
		type : "GET",
		dataType : 'html',
		contentType : "application/x-www-form-urlencoded",
		url : "/admin/cargarDatosEditarCandidato",
		data : "id="+id,
		timeout : 100000,
		beforeSend : function() {},
		success : function(data) {cargarDatosCandidato(data)},
		error : function(){}
	});
}

function cargarDatosCandidato(data){
	var rows = JSON.parse(data);
	$("#nombreEditarCandidato").val(rows[1]);
	$("#numeroEditarCandidato").val(rows[2]);
	$("#idCandidato").val(rows[3]);
}

function validarEditarCandidato(){
	var imagen = $("#imagenEditarCandidato").val();
	var nombre = $("#nombreEditarCandidato").val();
	var tarjeton = $("#numeroEditarCandidato").val();
	if((nombre.trim() === "") || (tarjeton.trim() == "")){
		printValues("#labelEditarCandidato", "#panelEditarCandidato", "Ingrese todos los datos para continuar.", "alert alert-danger");
		return false;
	}
	if(tarjeton <= 0){
		printValues("#labelEditarCandidato", "#panelEditarCandidato", "Ingrese un número de tarjeton correcto.", "alert alert-danger");
		return false;
	}
	if(imagen != ""){
		if(imagen.indexOf("jpg") >= 0){}else{
			printValues("#labelEditarCandidato", "#panelEditarCandidato", "Formato de imagen incorrecto (solo se adminten .jpg).", "alert alert-danger");
			return false;
		}
	}
	$.ajax({
		url: "/admin/editarCandidato",
		type: "POST",
		data: new FormData($("#formEditarCandidato")[0], $("#formEditarCandidato")[1], $("#formEditarCandidato")[2], $("#formEditarCandidato")[3], $("#formEditarCandidato")[4]),
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		success: function () {
			location.reload();
		}
	});
}

function Menu(){
	var url = window.location.pathname;
	/* GENERAL */
	if (url.indexOf("index") >= 0) {
		$("#home").addClass("active");
	}
	if (url.indexOf("elecciones") >= 0) {
		$("#elecciones").addClass("active");
	}
	if (url.indexOf("subirVotos") >= 0) {
		$("#subirVotos").addClass("active");
	}
}

function printValues(idLabel, idDiv, text, alert) {
	$(idLabel).html(text);
	$(idDiv).removeClass();
	$(idDiv).addClass(alert);
	$(idDiv).css({display : "block"});
	setTimeout(function() { $(idDiv).hide(); }, 5000);
}

function validarEditarEleccion(){
	var nombre = $("#nombreEditarEleccion").val();
	var fechaIni = $("#fechaInicioNuevaEleccion").val();
	var fechaFin = $("#fechaFinNuevaEleccion").val();
	var descripcion = $("#descripcionEditarEleccion").val();
	if( (nombre.trim() === "") || (descripcion.trim() === "") || (fechaIni.trim() === "") || (fechaFin.trim() === "")){
		printValues("#labelEditarEleccion", "#panelEditarEleccion", "Valores incorrectos.", "alert alert-danger");
		return false;
	}
	var hoy = new Date();
	var fIni = Date.parse(fechaIni);
	var fFin = Date.parse(fechaFin);
	if(fIni < hoy){
		printValues("#labelEditarEleccion", "#panelEditarEleccion", "Fecha de inicio incorrecta.", "alert alert-warning");
		return false;
	}
	if(fFin < hoy){
		printValues("#labelEditarEleccion", "#panelEditarEleccion", "Fecha de fin incorrecta.", "alert alert-warning");
		return false;
	}
	if(fIni > fFin){
		printValues("#labelEditarEleccion", "#panelEditarEleccion", "Fechas incorrectas.", "alert alert-warning");
		return false;
	}
	var fbdIni = fechaIni.replace(/-/g, "/");
	var fbdIniBd = fbdIni.replace("00.0", "");
	$("#fechaInicioNuevaEleccion").val(fbdIniBd);
	var fbdFin = fechaFin.replace(/-/g, "/");
	var fbdFinBd = fbdFin.replace(":00.0", "");
	$("#fechaFinNuevaEleccion").val(fbdFinBd);
	printValues("#labelEditarEleccion", "#panelEditarEleccion", "Actualizado exitosamente.", "alert alert-success");
	return true;
}

function subirAptos(){
	var file = $("#fileAptos").val();
	if(file != ""){
		if(file.indexOf('csv') >= 0){
			var fileInput = document.getElementById("fileAptos");
	        var reader = new FileReader();
	        reader.onload = function () {
	            cargarAptos(reader.result);
	        };
        	reader.readAsBinaryString(fileInput.files[0]);
		}else{
			printValues("#labelFileAptos", "#panelFileAptos", "Formato de archivo incorrecto.", "alert alert-danger");
		}
	}else{
		printValues("#labelFileAptos", "#panelFileAptos", "Debe seleccionar un archivo.", "alert alert-danger");
	}
}

function cargarAptos(data){
	var rows = data.split("\r\n");
	for(var i=0; i<rows.length; i++){
		if(rows[i] != null){
			var id = $("#idEleccion").val();
			$.ajax({
				async : true,
				type : "POST",
				dataType : 'text',
				contentType : "application/x-www-form-urlencoded",
				url : "/admin/subirCsv",
				data : "row="+rows[i]+"&idEleccion="+id,
				timeout : 100000
			});
		}
	}
	printValues("#labelFileAptos", "#panelFileAptos", "Usuarios importados exitosamente.", "alert alert-success");
}

function CargarDatosReporteAdmin(){
	var id = $("#eleccionReporte").val();
	if(id != null){
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/admin/cargarDatosReporte",
			data : "id="+id,
			timeout : 100000,
			beforeSend : function() {},
			success : function(data) {cargarDatosReporte(data)},
			error : function(){}
		});
	}
}

function CargarDatosReporte(){
	var id = $("#eleccionReporte").val();
	if(id != null){
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/jurado/cargarDatosReporte",
			data : "id="+id,
			timeout : 100000,
			beforeSend : function() {},
			success : function(data) {cargarDatosReporte(data)},
			error : function(){}
		});
	}
}

function cargarDatosReporte(data){
	var rows = JSON.parse(data);
	$("#fechaInicioReporte").val(rows[0]);
	$("#fechaFinReporte").val(rows[1]);
	$("#votosElectronicosReporte").val(rows[2]);
	$("#votosFisicosReporte").val(rows[3]);
	if(rows[4] === 'true'){
		$("#estadoReporteLabel").html("Elección Abierta");
		$("#estadoReporte").removeClass();
		$("#estadoReporte").addClass("alert alert-success");
		$("#estadoReporte").css({display : "block"});
	}else{
		$("#estadoReporteLabel").html("Elección cerrada");
		$("#estadoReporte").removeClass();
		$("#estadoReporte").addClass("alert alert-danger");
		$("#estadoReporte").css({display : "block"});
	}
	if($("#adminor").val() === "0"){
		cargarTotalVotosReporteAdmin();
	}else{
		cargarTotalVotosReporte();
	}
}

function cargarTotalVotosReporteAdmin(){
	var id = $("#eleccionReporte").val();
	if(id != null){
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/admin/cargarCandidatosReporte",
			data : "id="+id,
			timeout : 100000,
			beforeSend : function() {},
			success : function(data) {cargarDatosCandidatoReporte(data)},
			error : function(){}
		});
	}
}

function cargarTotalVotosReporte(){
	var id = $("#eleccionReporte").val();
	if(id != null){
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/jurado/cargarCandidatosReporte",
			data : "id="+id,
			timeout : 100000,
			beforeSend : function() {},
			success : function(data) {cargarDatosCandidatoReporte(data)},
			error : function(){}
		});
	}
}

function cargarDatosCandidatoReporte(data){
	var rows = JSON.parse(data);
	for(var i=0; i<Object.keys(rows).length; i++){
		$("#ulCandidatos").append($("<li>").text(rows[i]));
	}
}

function nuevoJurado(){
	var cc = $("#cedulaNuevoJurado").val();
	if(cc > 999999){
		var id = $("#idEleccion").val();
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/admin/nuevoJurado",
			data : "cedula="+cc+"&idEleccion="+id,
			timeout : 100000,
			beforeSend : function() {printValues("#labelNuevoJurado", "#panelNuevoJurado", "Guardando...", "alert alert-warning");},
			success : function(data) {
				if(data){
					printValues("#labelNuevoJurado", "#panelNuevoJurado", "Cambios guardados.", "alert alert-success");
					location.reload();
				}else{
					printValues("#labelNuevoJurado", "#panelNuevoJurado", "Error inesperado.", "alert alert-danger");
				}
			},
			error : function(){printValues("#labelNuevoJurado", "#panelNuevoJurado", "Error inesperado.", "alert alert-danger");}
		});
	}else{
		printValues("#labelNuevoJurado", "#panelNuevoJurado", "Cédula incorrecta.", "alert alert-danger");
	}
}

function editarJurado(){
	var cc = $("#cedulaEditarJurado").val();
	if(cc > 999999){
		var old = $("#oldCedulaEditarJurado").val();
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/admin/editarJurado",
			data : "cedula="+cc+"&old="+old,
			timeout : 100000,
			beforeSend : function() {printValues("#labelEditarJurado", "#panelEditarJurado", "Guardando...", "alert alert-warning");},
			success : function(data) {
				if(data){
					printValues("#labelEditarJurado", "#panelEditarJurado", "Cambios guardados.", "alert alert-success");
					location.reload();
				}else{
					printValues("#labelEditarJurado", "#panelEditarJurado", "Error inesperado.", "alert alert-danger");
				}
			},
			error : function(){printValues("#labelEditarJurado", "#panelEditarJurado", "Error inesperado.", "alert alert-danger");}
		});
	}else{
		printValues("#labelEditarJurado", "#panelEditarJurado", "Cédula incorrecta.", "alert alert-danger");
	}
}

function cargarDatosEditarJurado(id){
	if(id != null){
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/admin/cargarDatosEditarJurado",
			data : "id="+id,
			timeout : 100000,
			beforeSend : function() {},
			success : function(data) {$("#cedulaEditarJurado").val(data);$("#oldCedulaEditarJurado").val(data);},
			error : function(){}
		});
	}
}

function validarNuevaEleccion(){
	var nombre = $("#nombreNuevaEleccion").val();
	var descripcion = $("#descripcionNuevaEleccion").val();
	var fechaIni = $("#fechaInicioNuevaEleccion").val();
	var fechaFin = $("#fechaFinNuevaEleccion").val();
	if((nombre.trim() != "") && (descripcion.trim() != "") && (fechaIni.trim() != "") && (fechaFin.trim() != "")){
		var hoy = new Date();
		var fIni = Date.parse(fechaIni);
		var fFin = Date.parse(fechaFin);
		if(fIni < hoy){
			printValues("#labelNuevaEleccion", "#panelNuevaEleccion", "Fecha de inicio incorrecta.", "alert alert-warning");
			return false;
		}
		if(fFin < hoy){
			printValues("#labelNuevaEleccion", "#panelNuevaEleccion", "Fecha de fin incorrecta.", "alert alert-warning");
			return false;
		}
		if(fIni > fFin){
			printValues("#labelNuevaEleccion", "#panelNuevaEleccion", "Fechas incorrectas.", "alert alert-warning");
			return false;
		}
	}else{
		printValues("#labelNuevaEleccion", "#panelNuevaEleccion", "Debe completar todos los campos para continuar.", "alert alert-danger");
		return false;
	}
}

function SubirVotos(){
	var cantidad  = $("#cantidadVotos").val();
	var candidato = $("#candidatoSubirVotos").val();
	var eleccion = $("#eleccionSubirVotos").val();
	if((cantidad.trim() === "") || (candidato.trim() === "") || (eleccion.trim() === "")){
		
	}else{
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/jurado/subirVotosFisicos",
			data : "idEleccion="+eleccion+"&idCandidato="+candidato+"&cantidad="+cantidad,
			timeout : 100000,
			beforeSend : function() {
				printValues("#labelSubirVotos", "#panelSubirVotos","Subiendo votos.","alert alert-warning");				
			},
			success : function(data) {
				printValues("#labelSubirVotos", "#panelSubirVotos","Transacción exitosa.","alert alert-success");
				CargarCandidatos();
			},
			error : function(){
				printValues("#labelSubirVotos", "#panelSubirVotos","Error al subir los votos.","alert alert-danger");				
			}
		});
	}
}

function CargarCandidatos(){
	var id = $("#eleccionSubirVotos").val();
	if(id != null){
		if(id != ""){
			$.ajax({
				async : true,
				type : "GET",
				dataType : 'html',
				contentType : "application/x-www-form-urlencoded",
				url : "/cargarCandidatos",
				data : "idEleccion="+id,
				timeout : 100000,
				beforeSend : function() {},
				success : function(data) {cargarItemsCandidatos(data)},
				error : function(){}
			});
		}
	}
}

function cargarItemsCandidatos(data){
	$('#candidatoSubirVotos').find('option').remove().end()
	var rows = JSON.parse(data);
	for(var i=0; i<Object.keys(rows).length; i++){
		var option = $('<option/>');
		option.attr({ 'value': rows[i].id }).text(rows[i].nombre);
		$("#candidatoSubirVotos").append(option);
	}
	if(Object.keys(rows).length === 0){
		var val = $("#eleccionSubirVotos").val();
		$("#eleccionSubirVotos option[value='"+val+"']").remove();
		CargarCandidatos()
	}
}

function BuscarUsuario(){
	var cedula = $("#cedulaUsuario").val();
	if(cedula != ""){
		var id = $("#idEleccion").val();
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/jurado/buscarUsuario",
			data : "idEleccion="+id+"&cedula="+cedula,
			timeout : 100000,
			beforeSend : function() {
				printValues("#labelRespuestaBuscarUser", "#panelRespuestaBuscarUser","Validando información.","alert alert-warning");
			},
			success : function(data) {
				if(data === "no"){
					printValues("#labelRespuestaBuscarUser", "#panelRespuestaBuscarUser","El usuario no es apto para votar.","alert alert-danger");
				}else{
					printValues("#labelRespuestaBuscarUser", "#panelRespuestaBuscarUser","Voto registrado exitosamente.","alert alert-success");
				}
				
			},
			error : function(e, x, settings, exception) {
				printValues("#labelRespuestaBuscarUser", "#panelRespuestaBuscarUser","El usuario no es apto para votar.","alert alert-danger");
			}
		});
	}else{
		$("#panelRespuestaBuscarUser").css({display : "none"});
	}
	
}

function IngresarUsuario(){
	var cedula = $("#cedulaNuevoUser").val();
	var email = $("#emailNuevoUser").val();
	var concepto = $("#conceptoNuevoUser").val();
	var id = $("#idEleccion").val();
	if((cedula.trim() === "") || (email.trim() === "") || (concepto.trim() === "")){
		printValues("#labelNuevoUser", "#panelNuevoUser","Ingrese todos los campos para continuar.","alert alert-danger");
	}else{
		$.ajax({
			async : true,
			type : "GET",
			dataType : 'html',
			contentType : "application/x-www-form-urlencoded",
			url : "/nuevoUsuario",
			data : "cedula="+cedula+"&email="+email+"&concepto="+concepto+"&idEleccion="+id,
			timeout : 100000,
			beforeSend : function() {
				printValues("#labelNuevoUser", "#panelNuevoUser","Validando información.","alert alert-warning");
			},
			success : function(data) {
				if(data){
					printValues("#labelNuevoUser", "#panelNuevoUser","El usuario se ingreso correctamente.","alert alert-success");
				}else{
					printValues("#labelNuevoUser", "#panelNuevoUser","Ocurrió un error durante la inscripción del usuario.","alert alert-danger");
				}
			},
			error : function(){
				printValues("#labelNuevoUser", "#panelNuevoUser","Ocurrió un error durante la inscripción del usuario.","alert alert-danger");
			}
		});
	}
}

function CerrarEleccion(){
	var id = $("#idEleccion").val();
	$.ajax({
		async : true,
		type : "GET",
		dataType : 'html',
		contentType : "application/x-www-form-urlencoded",
		url : "/cerrarEleccion",
		data : "idEleccion="+id,
		timeout : 100000,
		beforeSend : function() {},
		success : function(data) {window.location.href = 'index?Closed';},
		error : function(){}
	});
}