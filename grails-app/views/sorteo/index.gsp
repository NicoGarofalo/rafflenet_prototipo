<html>
    <head></head>
    <body>
        <h1>Holaaa</h1>
        <p>Listado de sorteos</p>
        
        ${sorteos}

        <div class="cardStack--cards">
            <g:each in="${sorteos}" var="sorteo">
                <div class="cardStack--card">
                    <img src="https://placeimg.com/600/300/people%22%3E"/>
                    <h3>${sorteo.estadistica.descripcion}</h3>
                    <p>${sorteo.descripcionPremio}</p>
                </div>
            </g:each>
        </div>
    
    </body>

</html>