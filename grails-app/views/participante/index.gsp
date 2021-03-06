<!doctype html>
<html lang="en">
    <head>
        <title>RaffleNet</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
		<div class="wrapper d-flex align-items-stretch">
			<nav id="sidebar">
	  			<div class="user-logo" style="display: block; margin: 0 auto; text-align:center;">
                    <asset:image style="align-self: center; margin-block: 2rem;" height='70%' width='70%' src="rafflenet_logo.png" alt="RaffleNet Logo"/>
	  			</div>
                <ul class="list-unstyled components mb-5">
                    <li class="active">
                        <a href="#"> Participar</a>
                    </li>
                    <li>
                        <a href="#"> Mis Sorteos</a>
                    </li>
                    <li>
                        <a href="#"> Cupones</a>
                    </li>
                    <li>
                        <a href="#"> Cerrar Sesion</a>
                    </li>
                </ul>
    	    </nav>

            <!-- Page Content  -->
            <div id="content" class="p-4 p-md-5 pt-5">
                <div style="align-items: center; justify-content: center;">
                    <g:each in="${sorteoActual}" var="sorteo">
                        <div 
                            <g:if test="${sorteo.id == 1}"> style="display: block;" </g:if> 
                            <g:else> style="display: none;" </g:else>
                            id="sorteoCard${sorteo.id}"
                        >
                            <div class="card" style="width: 25rem; box-shadow: 3px 3px 3px 3px rgba(0,0,0,0.5)">
                                <img class="card-img-top" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXBkFUDdOrf9y01ww3Y6pkOx5RUdoIdNVa3g&usqp=CAU" alt="Card image cap">     
                                <div class="card-body">
                                    <h5 class="card-title" style="text-align: center;"><strong>Sorteo Microsoft Surface Pro Versi??n 2021</strong></h5>
                                    <p class="card-text"  style="text-align: center;">${sorteo.descripcionPremio}</p>
                                    <a>m??s detalles...</a>
                                </div>
                            </div>
                            <div style="display:flex; justify-content: center; width: 25rem; align-items: center; margin-top: 2rem">
                                <button onClick="mostrarCard(${sorteo.id},${sorteo.id+1},${cantSorteos})">
                                    <i class="fa fa-times-circle" style="font-size: 4rem; color: #F24726;" ></i>
                                </button>
                                <h5 class="text-muted" style="padding-inline: 10%; font-size: 25px;" >Participar?</h5>
                                <button onClick="participar(${participante.id},${sorteo.id},${sorteo.id},${sorteo.id+1},${cantSorteos})">
                                    <i class="fa fa-check-circle" style="font-size: 4rem; color: #8FD14F;"></i>
                            </button>
                            </div>
                        </div>
                    </g:each>     
                    <div id="alertNoSorteos" style="display:none" class="alert alert-primary" role="alert">
                        NO HAY MAS SORTEOS PARA VOS
                    </div>
                </div>
            </div>
        </div>
    </body>
    <g:javascript>
        function mostrarCard (currentCardId,nextCardId,cantSorteos) {
            document.getElementById('sorteoCard' + currentCardId).style.display = "none";
            if(currentCardId == cantSorteos){
                document.getElementById('alertNoSorteos').style.display = "block";
            } else {
                document.getElementById('sorteoCard' + nextCardId).style.display = "block";
            }
        }

        function participar (usuarioId,sorteoId,currentCardId,nextCardId,cantSorteos) {
            var URL='/participante/participar';
            console.log(URL);
            $.ajax({
                url:URL,
                type:"POST",
                data:{"usuarioId": usuarioId,"sorteoId": sorteoId}
            });
            // mostrarCard(currentCardId,nextCardId,cantSorteos);
        }
    </g:javascript>
</html>
