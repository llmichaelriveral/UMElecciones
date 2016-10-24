
  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- Default to the left -->
    <strong>Copyright &copy; 2016 <a href="http://www.coxti.com" target="_blank">CoxTI Tecnologías de la Información</a>.</strong>.
  </footer>
<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="/resources/bootstrap/js/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/resources/bootstrap/js/bootstrap.min.js"></script>
<!-- Data Tables -->
<script src="/resources/dist/js/jquery.dataTables.min.js"></script>
<script src="/resources/dist/js/dataTables.bootstrap.min.js"></script>
<!-- Custom JS -->
<script src="/resources/dist/js/functions.js"></script>
<!-- AdminLTE App -->
<script src="/resources/dist/js/app.min.js"></script>
<!-- DateTime -->
<script src="/resources/dist/js/datepicker.full.js"></script>
<!-- Subir Archivos -->
<script src="/resources/dist/js/bootstrap-filestyle.min.js"></script>
<script type="text/javascript">
	$(function () {
		$("#tabla-elecciones").DataTable({
		      "paging": true,
		      "lengthChange": false,
		      "searching": false,
		      "ordering": true,
		      "info": true,
		      "autoWidth": false
		    });
	});
	$('#imagenEditarCandidato').filestyle();
	$('#imagenNuevoCandidato').filestyle();
	$('#fileAptos').filestyle();
</script>
<script>
$.datetimepicker.setLocale('es');

$('#fechaInicioNuevaEleccion').datetimepicker({
	dayOfWeekStart : 1,
	lang:'es',
	disabledDates:['1986/01/08','1986/01/09','1986/01/10'],
	startDate:	new Date()
});
$('#fechaFinNuevaEleccion').datetimepicker({
	dayOfWeekStart : 1,
	lang:'es',
	disabledDates:['1986/01/08','1986/01/09','1986/01/10'],
	startDate:	new Date()
});
$('#datetimepicker').datetimepicker({value:'2015/04/15 05:03',step:10});

</script>
</body>
</html>
