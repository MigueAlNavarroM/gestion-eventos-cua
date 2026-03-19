// 1. FUNCIÓN DE BÚSQUEDA (Corregida para IDs)
async function buscarInstructor() {
    const busqueda = document.getElementById("instructorInput").value.trim();
    if (!busqueda) return;

    ocultarSecciones();
    try {
        // Si es número, busca por ID directo
        if (!isNaN(busqueda)) {
            const resp = await fetch(`/instructores/${busqueda}`);
            if (!resp.ok) throw new Error("No existe");
            const ins = await resp.json();

            // Reutilizamos la lógica de la tabla para mostrar solo uno
            mostrarEnTabla([ins]);
        } else {
            // Aquí iría tu lógica de búsqueda por nombre si el backend la soporta
            alert("Búsqueda por nombre en proceso...");
        }
    } catch (e) {
        alert("El instructor con ID " + busqueda + " no existe.");
    }
}

// 2. LISTAR TODOS
async function listarTodos() {
    ocultarSecciones();
    try {
        const resp = await fetch("/instructores");
        const data = await resp.json();
        mostrarEnTabla(data);
    } catch (e) {
        alert("Error al cargar la lista.");
    }
}

// 3. FUNCIÓN PARA PINTAR LA TABLA (Vanessa ahora tendrá sus categorías)
function mostrarEnTabla(lista) {
    const body = document.getElementById("tablaBodyInstructores");
    body.innerHTML = "";
    document.getElementById("seccionTablaInstructores").classList.remove("d-none");

    lista.forEach(ins => {
        // Procesamos la lista de categorías que viene de Java
        let catStr = (ins.nombresCategorias && ins.nombresCategorias.length > 0)
            ? ins.nombresCategorias.join(", ")
            : '<span class="text-muted">Sin categorías</span>';

        body.innerHTML += `
            <tr>
                <td>${ins.idInstructor}</td>
                <td class="fw-bold">${ins.nombre} ${ins.apellidoPaterno}</td>
                <td>${ins.correo}</td>
                <td>${ins.telefono}</td>
                <td>${ins.especialidad}</td>
                <td class="text-center">
                    <span class="badge ${ins.activo ? 'bg-success' : 'bg-danger'}">
                        ${ins.activo ? 'Sí' : 'No'}
                    </span>
                </td>
                <td class="text-primary fw-bold">${catStr}</td>
            </tr>`;
    });
}

// 4. GUARDAR INSTRUCTOR
document.getElementById("formInstructor").onsubmit = async (e) => {
    e.preventDefault();
    const payload = {
        nombre: document.getElementById("insNombre").value,
        apellidoPaterno: document.getElementById("insPaterno").value,
        apellidoMaterno: document.getElementById("insMaterno").value,
        correo: document.getElementById("insCorreo").value,
        telefono: document.getElementById("insTelefono").value,
        especialidad: document.getElementById("insEspecialidad").value,
        bio: document.getElementById("insBio").value,
        activo: document.getElementById("insActivo").value === "true"
    };

    const resp = await fetch("/instructores", {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    });

    if (resp.ok) {
        alert("¡Guardado!");
        listarTodos();
    }
};

// 5. UTILIDADES
function mostrarFormularioInstructor() {
    ocultarSecciones();
    document.getElementById("seccionFormularioInstructor").classList.remove("d-none");
}

function ocultarSecciones() {
    document.getElementById("seccionTablaInstructores").classList.add("d-none");
    document.getElementById("seccionFormularioInstructor").classList.add("d-none");
}

// Detectar la tecla Enter en el campo de búsqueda
document.getElementById("instructorInput").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Evita que la página se recargue
        buscarInstructor();    // Llama a tu función de búsqueda
    }
});