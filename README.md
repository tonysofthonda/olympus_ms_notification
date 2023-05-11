# olympus_ms_notification
Los requerimientos incluidos en este documento serán las funcionalidades que el Microservicio ofrecerá en la automatización de los flujos en 
HONDA una vez finalizado y entregado.
Como lo indica la Especificación de Requerimientos de Software determinará las restricciones, políticas de usuario 
y restricciones operacionales del sistema teniendo el siguiente alcance:
    
    • El microservicio deberá de siempre estar activo una vez iniciado.
    • El microservicio tendrá un archivo de propiedades XML con los siguientes datos (smtp, port, smtpsecure, smtpauth, 
    from, username, password, to, subject).
    • El microservicio para iniciar debera de recibir un mensaje.
    • El microservicio al momento de recibir un mensaje iniciara el proceso de funcionalidad.
    • El microservicio generar el body a enviar con el formato “{fecha} {time}, origen: {json.event.source}, 
    status: {json.event.status}, mensaje: {json.event.msg} y archivo:{json.event.file}”
    • Abrir el servidor de correos Abrir el servidor de correos con la información de conexión: host:{properties.smtp}, 
    port:{properties.port}, smtpsecurre:{properties.smtpsecure}, stmpauth:{properties.smtpauth}, username:{properties.username}, 
    password: {properties.password}
    • Debera validar si se puede conectar al servidor de correos.
    • Armara el correo electrónico con la estructura Armar el correo con los datos: from:{properties.from}, 
    to:{properties.to}, subject:{properties.subject}, body:{body}
    • Enviara el email con la estructura anterior.
    • Debera validar si se envio correctamente.
    • Cerrar el servidor de correo.
    • Cada proceso debera de enviar un mensaje a los microservicios ms.logevent y/o ms.notification según se indique en el diagrama de flujo.
    • El microservicio debera de disponer de un manejador de excepciones y notificar al microservicio
        ms.logevent y ms.notification enviando el error capturado en formato de texto.
    • En caso de errores en el flujo se debera de enviar el evento a los microservicios ms.logevent y/o ms.notification según estén definidos.

Este documento describe cada uno de los requerimientos que serán implementados en los casos de uso para el desarrollo del sistema.
