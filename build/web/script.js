// Validación del formulario de registro
document.addEventListener('DOMContentLoaded', function() {
    const formRegistro = document.getElementById('formRegistro');
    
    if(formRegistro) {
        formRegistro.addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmar = document.getElementById('confirmar').value;
            
            if(password !== confirmar) {
                e.preventDefault();
                alert('Las contraseñas no coinciden');
                return false;
            }
            
            if(password.length < 6) {
                e.preventDefault();
                alert('La contraseña debe tener al menos 6 caracteres');
                return false;
            }
        });
    }
    
    // Validación del formulario de cifrado
    const formCifrar = document.getElementById('formCifrar');
    if(formCifrar) {
        formCifrar.addEventListener('submit', function(e) {
            const idUsuario = document.getElementById('idUsuario').value;
            const texto = document.getElementById('texto').value;
            
            if(!idUsuario || idUsuario <= 0) {
                e.preventDefault();
                alert('Por favor ingresa un ID de usuario válido');
                return false;
            }
            
            if(!texto.trim()) {
                e.preventDefault();
                alert('El mensaje no puede estar vacío');
                return false;
            }
        });
    }
});

// Función para mostrar mensajes de error o éxito
function mostrarMensaje(tipo, mensaje) {
    const mensajeDiv = document.createElement('div');
    mensajeDiv.className = `mensaje ${tipo}`;
    mensajeDiv.innerHTML = mensaje;
    
    const container = document.querySelector('.container');
    container.insertBefore(mensajeDiv, container.firstChild);
    
    setTimeout(() => {
        mensajeDiv.remove();
    }, 5000);
}

// Función para copiar texto al portapapeles
function copiarAlPortapapeles(texto) {
    navigator.clipboard.writeText(texto).then(() => {
        mostrarMensaje('exito', 'Texto copiado al portapapeles');
    }).catch(() => {
        mostrarMensaje('error', 'Error al copiar el texto');
    });
}